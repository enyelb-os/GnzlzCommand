package tools.gnzlz.command;

import tools.gnzlz.command.funtional.FunctionRequiredCommand;

import java.util.ArrayList;

public abstract class Command<Type, C> {

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

    boolean isNew = true;

    /***************************************
     * vars
     ***************************************/

    protected Type value;

    /***************************************
     * vars
     ***************************************/

    public final CommandObject resultCommand;

    /***************************************
     * vars
     ***************************************/

    final ArrayList<String> commands;

    /***************************************
     * constructor
     * @param name
     ***************************************/

    protected Command(String name){
        this.name = name;
        this.required = FALSE;
        this.message = "";
        this.resultCommand = new CommandObject(this);
        this.commands = new ArrayList<String>();
    }

    /***************************************
     * set required
     * @param required
     ***************************************/

    public C required(boolean required) {
        this.required = required ? TRUE : FALSE;
        return (C) this;
    }

    /***************************************
     * set required
     * @param required
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
     * @param value
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
     * @param commandName
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
     * @param commands
     ***************************************/

    private void validateAddCommands(String ... commands){
        if(commands != null){
            for (String commandName : commands) {
                validateAddCommand(commandName);
            }
        }
    }


    /***************************************
     * abstract
     ***************************************/

    public abstract String type();

    /***************************************
     * abstract
     ***************************************/

    public abstract Object valueProcess(Object value);
}
