package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.object.ArrayListCommand;
import tools.gnzlz.command.result.object.ResultArrayListCommand;

public class CommandArray extends Command<ArrayListCommand, ResultArrayListCommand, CommandArray> {

    /***************************************
     * constructor
     * @param name name
     **************************************
     */

     CommandArray(String name) {
        super(name);
     }

    /**
     * converter
     *
     */

    @Override
    public ResultArrayListCommand valueProcess(Object value) {
        if(value instanceof ResultArrayListCommand) {
            return (ResultArrayListCommand) value;
        }
        return null;
    }


    /***************************************
     * static create
     * @param name name
     ***************************************/

    public static CommandArray create(String name){
        return new CommandArray(name);
    }

    /***************************************
     * set array
     * @param value value
     ***************************************/

    public CommandArray array(ArrayListCommand value) {
        return this.value(value);
    }

    /***************************************
     * set array
     * @param value value
     ***************************************/

    public CommandArray array(Command<?,?,?> ... value) {
        return this.value(ArrayListCommand.create().addCommand(value));
    }
}
