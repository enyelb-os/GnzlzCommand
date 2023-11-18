package tools.gnzlz.command.command.object;

import tools.gnzlz.command.command.Command;

public class ArrayListCommand extends ListCommand {

    /***************************************
     * select listCommands
     ***************************************/

    public static ArrayListCommand create(){
        return new ArrayListCommand();
    }

    /***************************************
     * select listCommands
     ***************************************/

    public static ArrayListCommand create(Command<?,?,?>... command) {
        return new ArrayListCommand().addCommand(command);
    }

    /***************************************
     * static
     ***************************************/

    public ArrayListCommand addCommand(Command<?,?,?> ... names) {
        super.addCommand(names);
        return this;
    }

}
