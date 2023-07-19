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

     Class<?> className;

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

    boolean isNew = true;

    /***************************************
     * vars
     ***************************************/

    Object value;

    /***************************************
     * vars
     ***************************************/

    final CommandObject resultCommand;

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
        this.required = false;
        this.message = "";
        this.resultCommand = new CommandObject(this);
        this.commands = new ArrayList<String>();
        this.internalCommands = ListCommand.create();
        this.className = String.class;
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

    public Command required() {
        this.required = true;
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command type(Class<?> type) {
        this.className = type;
        return this;
    }

    /***************************************
     * set
     ***************************************/

    private Command valueInternal(Object value) {
        if(value != null){
            this.className = value.getClass();
        }
        this.value = value;
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command value(int value) {
        return valueInternal(value);
    }
    public Command value(String value) {
        return valueInternal(value);
    }
    public Command value(double value) {
        return valueInternal(value);
    }
    public Command value(boolean value) {
        return valueInternal(value);
    }
    public Command value(Option value) {
        return valueInternal(value);
    }
    public Command value(ListCommand value) {
        return valueInternal(value);
    }
    public Command value(ArrayListCommand value) {
        return valueInternal(value);
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
