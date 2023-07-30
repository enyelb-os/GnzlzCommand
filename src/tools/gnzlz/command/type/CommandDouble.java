package tools.gnzlz.command.type;

import tools.gnzlz.command.Command;

public class CommandDouble extends Command<Double, CommandDouble> {

    /***************************************
     * constructor
     * @param name
     **************************************
     */

    protected CommandDouble(String name) {
        super(name);
    }

    /**
     * type
     *
     */

    @Override
    public String type() {
        return "(number)";
    }

    /**
     * converter
     *
     */

    @Override
    public Object valueProcess(Object value) {
        if(value instanceof String){
            try {
                return Double.parseDouble(String.valueOf(value));
            } catch (NumberFormatException exception) {}
        } else if (value instanceof Double){
            return value;
        }
        return null;
    }

    /***************************************
     * static create
     * @param name
     ***************************************/

    public static CommandDouble create(String name){
        return new CommandDouble(name);
    }
}
