package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;

public class CommandDouble extends Command<Double, Double, CommandDouble> {

    /***************************************
     * constructor
     * @param name name
     **************************************
     */

    public CommandDouble(String name) {
        super(name);
    }

    /**
     * converter
     *
     */

    @Override
    public Double valueProcess(Object value) {
        if(value instanceof String){
            try {
                return Double.parseDouble(String.valueOf(value));
            } catch (NumberFormatException ignored) {}
        } else if (value instanceof Double){
            return (Double) value;
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
