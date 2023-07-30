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

    public Command command(Command name){
        for (Command command: this.commands) {
            if(command.name.equals(name.name)){
                return command;
            }
        }
        this.commands.add(name);
        return name;
    }

    /***************************************
     * static
     ***************************************/

    public  <Type, C extends Command>  ListCommand addCommand(Command<Type, C> name){
        for (Command command: this.commands) {
            if(command.name.equals(name)){
                return this;
            }
        }
        this.commands.add(name);
        return this;
    }

    /***************************************
     * static
     ***************************************/

    public ListCommand addCommand(Command ... names){
        if(names != null){
            for (Command name: names) {
                this.addCommand(name);
            }
        }
        return this;
    }
}
