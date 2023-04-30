package tools.gnzlz.command;

import java.util.ArrayList;

public class Command extends ICommand<Command>{

    /***************************************
     * Default list Commands
     ***************************************/

    static ArrayList<Command> listCommands = new ArrayList<Command>();

    /***************************************
     * vars
     ***************************************/

    final ArrayList<String> commands;

    /***************************************
     * vars
     ***************************************/

    final ArrayList<String> groups;

    /***************************************
     * vars
     ***************************************/

    final ArrayList<InternalCommand> internalCommands;

    /***************************************
     * vars
     ***************************************/

    boolean isNew = true;

    /***************************************
     * vars
     ***************************************/

    boolean optional;

    /***************************************
     * vars
     ***************************************/

    String message;

    /***************************************
     * constructor
     ***************************************/

    Command(String name){
        super(name);
        this.commands = new ArrayList<String>();
        this.groups = new ArrayList<String>();
        this.assign = false;
        this.optional = true;
        this.message = "";
        this.internalCommands = new ArrayList<InternalCommand>();
    }

    /***************************************
     * set
     ***************************************/

    public Command required(String message) {
        this.message = message;
        this.optional = message.isEmpty();
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

    public Command groups(String ... groups) {
        this.validateAddGroups(groups);
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command option(String option, String ... commands) {
        if(value instanceof Value){
            ((Value) value).options(option);
        }
        this.validateAddInternalCommands(option,commands);
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command option(String option, Command ... commands) {

        if(value instanceof Value){
            ((Value) value).options(option);
        }
        this.validateAddInternalCommands(option,commands);
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command internal(Command ... commands) {
        this.validateAddInternalCommands(null,commands);
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command internal(String ... commands) {
        this.validateAddInternalCommands(null,commands);
        return this;
    }

    /***************************************
     * private
     ***************************************/

    private void validateAddInternalCommand(String option,String commandName){
        for (InternalCommand internalCommand: this.internalCommands) {
            if ( internalCommand.command.name.equals(commandName) && (
                (internalCommand.option != null && internalCommand.option.equals(option)) ||
                (internalCommand.option == null && option == null)
            )) {
                return;
            }
        }
        this.commands.add(commandName);
    }

    private void validateAddInternalCommands(String option, String ... commands){
        if(commands != null){
            for (String commandName : commands) {
                validateAddInternalCommand(option, commandName);
            }
        }
    }

    private void validateAddInternalCommands(String option, Command ... commands){
        if(commands != null){
            for (Command command : commands) {
                validateAddInternalCommand(option, command.name());
            }
        }
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
            for (Command command: Command.listCommands) {
                if(!command.name.equals(name)) {
                    for (String commandName : commands) {
                        for (String commandNameOld: command.commands) {
                            if (commandNameOld.equals(commandName)) {
                                throw new RuntimeException("command duplicate : " + command.name + "." + commandName + " == " + name + "." + commandName);
                            }
                        }
                    }
                }
            }
        }
    }

    /***************************************
     * private
     ***************************************/

    private void validateAddGroup(String group){
        for (String groupOld: this.groups) {
            if (groupOld.equals(group)) {
                return;
            }
        }
        this.groups.add(group);
    }

    private void validateAddGroups(String ... groups){
        if(groups != null){
            for (String groupName : groups) {
                validateAddGroup(groupName);
            }
        }
    }

    /***************************************
     * static
     ***************************************/

    public static Command command(String name){
        for (Command command: Command.listCommands) {
            if(command.name.equals(name)){
                command.isNew = false;
                return command;
            }
        }
        Command command = new Command(name);
        Command.listCommands.add(command);
        return command;
    }
}
