package tools.gnzlz.command.type;

import tools.gnzlz.command.ArrayListCommand;
import tools.gnzlz.command.Command;

import java.util.ArrayList;

public class CommandArray extends Command<ArrayListCommand, CommandArray> {

    /***************************************
     * constructor
     * @param name
     **************************************
     */

    protected CommandArray(String name) {
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
        if(value instanceof ArrayList) {
            return value;
        }
        return null;
    }


    /***************************************
     * static create
     * @param name
     ***************************************/

    public static CommandArray create(String name){
        return new CommandArray(name);
    }

    /***************************************
     * set array
     * @param value
     ***************************************/

    public CommandArray array(ArrayListCommand value) {
        return this.value(value);
    }

    /***************************************
     * set array
     * @param value
     ***************************************/

    public CommandArray array(Command ... value) {
        return this.value(ArrayListCommand.create().addCommand(value));
    }
}
