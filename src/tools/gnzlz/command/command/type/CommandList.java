package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.object.ListCommand;
import tools.gnzlz.command.result.ResultListCommand;

public class CommandList extends Command<ListCommand, ResultListCommand, CommandList> {

    /***************************************
     * constructor
     * @param name name
     **************************************
     */

    public CommandList(String name) {
        super(name);
    }


    /**
     * converter
     *
     */

    @Override
    public ResultListCommand valueProcess(Object value) {
        if(value instanceof ResultListCommand) {
            return (ResultListCommand) value;
        }
        return null;
    }

    /***************************************
     * static create
     * @param name name
     ***************************************/

    public static CommandList create(String name){
        return new CommandList(name);
    }

    /***************************************
     * set list
     * @param value name
     ***************************************/

    public CommandList list(ListCommand value) {
        return this.value(value);
    }

    /***************************************
     * set list
     * @param value name
     ***************************************/

    public CommandList list(Command<?,?,?> ... value) {
        return this.value(ListCommand.create().addCommand(value));
    }
}
