package tools.gnzlz.command.type;

import tools.gnzlz.command.Command;
import tools.gnzlz.command.ListCommand;
import tools.gnzlz.command.ResultListCommand;

public class CommandList extends Command<ListCommand, CommandList> {

    /***************************************
     * constructor
     * @param name
     **************************************
     */

    protected CommandList(String name) {
        super(name);
    }

    /**
     * type
     *
     */

    @Override
    public String type() {
        return "";
    }

    /**
     * converter
     *
     */

    @Override
    public Object valueProcess(Object value) {
        if(value instanceof ResultListCommand) {
            return value;
        }
        return null;
    }

    /***************************************
     * static create
     * @param name
     ***************************************/

    public static CommandList create(String name){
        return new CommandList(name);
    }

    /***************************************
     * set list
     * @param value
     ***************************************/

    public CommandList list(ListCommand value) {
        return this.value(value);
    }

    /***************************************
     * set list
     * @param value
     ***************************************/

    public CommandList list(Command ... value) {
        return this.value(ListCommand.create().addCommand(value));
    }
}
