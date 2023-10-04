package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.CommandBasic;

public class CommandDouble extends CommandBasic<Double, CommandDouble> {

    /***************************************
     * constructor
     * @param name name
     **************************************
     */

    public CommandDouble(String name) {
        super(name);
    }

    /**
     * type
     */

    @Override
    public String type() {
        return " number";
    }

    /**
     * converter
     *
     */

    @Override
    protected Double processValue(Object value) {
        if(value instanceof String str){
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException ignored) {}
        } else if (value instanceof Double doubl){
            return doubl;
        }
        return null;
    }

    /***************************************
     * static create
     * @param name name
     ***************************************/

    public static CommandDouble create(String name){
        return new CommandDouble(name);
    }
}
