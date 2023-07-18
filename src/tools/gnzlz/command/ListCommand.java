package tools.gnzlz.command;

import java.util.ArrayList;

public class ListCommand {

    /***************************************
     * vars
     ***************************************/
    final ArrayList<Command> commands;

    /***************************************
     * constructor
     ***************************************/
     ListCommand(){
        commands = new ArrayList<Command>();
     }

    /***************************************
     * select listCommands
     ***************************************/

    public static ListCommand create(){
        return new ListCommand();
    }

    /***************************************
     * method
     ***************************************/

    public void listCommands(FunctionCommand functionCommand){
        for (Command command: commands) {
            functionCommand.run(command.resultCommand);
        }
    }

    /***************************************
     * static
     ***************************************/

    public Command command(String name){
        for (Command command: this.commands) {
            if(command.name.equals(name)){
                command.isNew = false;
                return command;
            }
        }
        Command command = Command.create(name);
        this.commands.add(command);
        return command;
    }

    public Command command(Command name){
        for (Command command: this.commands) {
            if(command.name.equals(name.name)){
                return command;
            }
        }
        this.commands.add(name);
        return name;
    }
}
