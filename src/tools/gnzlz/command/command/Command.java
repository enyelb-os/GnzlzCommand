package tools.gnzlz.command.command;

import tools.gnzlz.command.command.incertaces.Icommand;
import tools.gnzlz.command.funtional.FunctionRequiredCommand;
import tools.gnzlz.command.result.ExposeResultCommand;
import tools.gnzlz.command.result.ResultCommand;

import java.util.ArrayList;

public abstract class Command<Type, R, C extends Command<?, ?, ?>> implements Icommand<Type, R, C> {

    /***************************************
     * vars
     ***************************************/

    final String name;

    /***************************************
     * vars
     ***************************************/

    final static FunctionRequiredCommand TRUE = (list) -> true;
    final static FunctionRequiredCommand FALSE = (list) -> false;

    /***************************************
     * vars
     ***************************************/

    FunctionRequiredCommand required;

    /***************************************
     * vars
     ***************************************/

    String message;

    /***************************************
     * vars
     ***************************************/

    protected Type value;

    /***************************************
     * vars
     ***************************************/

    final ArrayList<String> commands;

    /***************************************
     * constructor
     * @param name name
     ***************************************/

    protected Command(String name){
        this.name = name;
        this.required = FALSE;
        this.message = "";
        this.commands = new ArrayList<String>();
    }

    /***************************************
     * set required
     * @param required required
     ***************************************/

    public C required(boolean required) {
        this.required = required ? TRUE : FALSE;
        return (C) this;
    }

    /***************************************
     * set required
     * @param required required
     ***************************************/

    public C required(FunctionRequiredCommand required) {
        this.required = required;
        return (C) this;
    }

    /***************************************
     * set required
     ***************************************/

    public C required() {
        this.required = TRUE;
        return (C) this;
    }

    /***************************************
     * set value
     * @param value value
     ***************************************/

    public C value(Type value) {
        this.value = value;
        return (C) this;
    }

    /***************************************
     * set
     ***************************************/

    public C message(String message) {
        this.message = message;
        return (C) this;
    }

    /***************************************
     * set
     ***************************************/

    public C commands(String ... commands) {
        this.validateAddCommands(commands);
        return (C) this;
    }

    /***************************************
     * valid exists name args
     * @param commandName commandName
     ***************************************/

    private void validateAddCommand(String commandName){
        for (String commandNameOld: this.commands) {
            if (commandNameOld.equals(commandName)) {
                return;
            }
        }
        this.commands.add(commandName);
    }

    /***************************************
     * valid list command exists name args
     * @param commands commands
     ***************************************/

    private void validateAddCommands(String ... commands){
        if(commands != null){
            for (String commandName : commands) {
                validateAddCommand(commandName);
            }
        }
    }

    /***************************************
     * create
     ***************************************/

    protected ResultCommand<R> createResultCommand(R object){
        return ExposeResultCommand.create(this, object);
    }

}
