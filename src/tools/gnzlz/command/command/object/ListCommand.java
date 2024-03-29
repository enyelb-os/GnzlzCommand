package tools.gnzlz.command.command.object;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;

import java.util.ArrayList;

public class ListCommand {

    /**
     * commands
     */
     final ArrayList<Command<?,?,?>> commands;

    /**
     * ListCommand
     */
     ListCommand(){
        commands = new ArrayList<>();
     }

    /**
     * create
     */
    public static ListCommand create(){
        return new ListCommand();
    }

    /**
     * command
     * @param command command
     */
    public Command<?,?,?> command(Command<?,?,?> command){
        for (Command<?,?,?> commandOld: this.commands) {
            if(ExposeCommand.name(commandOld).equals(ExposeCommand.name(command))){
                return commandOld;
            }
        }
        this.commands.add(command);
        return command;
    }

    /**
     * addCommand
     * @param command command
     */
    public ListCommand addCommand(Command<?,?,?> command){
        for (Command<?,?,?> commandOld: this.commands) {
            if(ExposeCommand.name(commandOld).equals(ExposeCommand.name(command))){
                return this;
            }
        }
        this.commands.add(command);
        return this;
    }

    /**
     * addCommand
     * @param commands commands
     */
    public ListCommand addCommand(Command<?,?,?> ... commands){
        if(commands != null){
            for (Command<?,?,?> name: commands) {
                this.addCommand(name);
            }
        }
        return this;
    }
}
