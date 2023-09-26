package tools.gnzlz.command.command.object;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;

import java.util.ArrayList;

public class ListCommand {

    /***************************************
     * vars
     ***************************************/
     final ArrayList<Command<?,?,?>> commands;

    /***************************************
     * constructor
     ***************************************/

     ListCommand(){
        commands = new ArrayList<Command<?,?,?>>();
     }

    /***************************************
     * select listCommands
     ***************************************/

    public static ListCommand create(){
        return new ListCommand();
    }

    public Command<?,?,?> command(Command<?,?,?> name){
        for (Command<?,?,?> command: this.commands) {
            if(ExposeCommand.name(command).equals(ExposeCommand.name(name))){
                return command;
            }
        }
        this.commands.add(name);
        return name;
    }

    /***************************************
     * static
     ***************************************/

    public ListCommand addCommand(Command<?,?,?> name){
        for (Command<?,?,?> command: this.commands) {
            if(ExposeCommand.name(command).equals(ExposeCommand.name(name))){
                return this;
            }
        }
        this.commands.add(name);
        return this;
    }

    /***************************************
     * static
     ***************************************/

    public ListCommand addCommand(Command<?,?,?> ... names){
        if(names != null){
            for (Command<?,?,?> name: names) {
                this.addCommand(name);
            }
        }
        return this;
    }
}
