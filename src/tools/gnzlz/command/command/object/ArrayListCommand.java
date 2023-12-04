package tools.gnzlz.command.command.object;

import tools.gnzlz.command.command.Command;

public class ArrayListCommand extends ListCommand {

    /**
     * create
     */
    public static ArrayListCommand create(){
        return new ArrayListCommand();
    }

    /**
     * create
     * @param command command
     */
    public static ArrayListCommand create(Command<?,?,?>... command) {
        return new ArrayListCommand().addCommand(command);
    }

    /**
     * addCommand
     * @param commands commands
     */
    public ArrayListCommand addCommand(Command<?,?,?> ... commands) {
        super.addCommand(commands);
        return this;
    }
}
