package tools.gnzlz.command;

import java.util.ArrayList;

public class Command {

    /***************************************
     * vars
     ***************************************/

    final String name;

    /***************************************
     * vars
     ***************************************/

    boolean assign;

    /***************************************
     * vars
     ***************************************/

    boolean required;

    /***************************************
     * vars
     ***************************************/

    String message;

    /***************************************
     * vars
     ***************************************/

    boolean isArray = false;

    /***************************************
     * vars
     ***************************************/

    boolean isNew = true;

    /***************************************
     * vars
     ***************************************/

    Object value;

    /***************************************
     * vars
     ***************************************/

    final ResultCommand resultCommand;

    /***************************************
     * vars
     ***************************************/

    final ArrayList<String> commands;

    /***************************************
     * vars
     ***************************************/

    final ListCommand internalCommands;

    /***************************************
     * constructor
     ***************************************/

    protected Command(String name){
        this.name = name;
        this.assign = false;
        this.required = true;
        this.message = "";
        this.resultCommand = new ResultCommand(this);
        this.commands = new ArrayList<String>();
        this.internalCommands = ListCommand.create();
    }

    /***************************************
     * static
     ***************************************/

    public static Command create(String name){
        return new Command(name);
    }

    /***************************************
     * set
     ***************************************/

    public Command required(boolean required) {
        this.required = required;
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command value(Object value) {
        this.value = value;
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command message(String message) {
        this.message = message;
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command commands(String ... commands) {
        this.validateAddCommands(commands);
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command internal(Command ... commands) {
        //this.validateAddInternalCommands(null,commands);
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command internal(String ... commands) {
        //this.validateAddInternalCommands(null,commands);
        return this;
    }

    /***************************************
     * private
     ***************************************/

    private void validateAddCommand(String commandName){
        for (String commandNameOld: this.commands) {
            if (commandNameOld.equals(commandName)) {
                return;
            }
        }
        this.commands.add(commandName);
    }

    private void validateAddCommands(String ... commands){
        if(commands != null){
            for (String commandName : commands) {
                validateAddCommand(commandName);
            }

            //debe ir a otro lugar
            /*for (Command command: listCommand.commands) {
                if(!command.name.equals(name)) {
                    for (String commandName : commands) {
                        for (String commandNameOld: command.commands) {
                            if (commandNameOld.equals(commandName)) {
                                throw new RuntimeException("command duplicate : " + command.name + "." + commandName + " == " + name + "." + commandName);
                            }
                        }
                    }
                }
            }*/
        }
    }

}
