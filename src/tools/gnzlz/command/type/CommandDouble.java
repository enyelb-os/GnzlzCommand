package tools.gnzlz.command.type;

import tools.gnzlz.command.CommandBasic;

public class CommandDouble extends CommandBasic<Double, CommandDouble> {

    /**
     * CommandDouble
     * @param name name
     */
    public CommandDouble(String name) {
        super(name);
    }

    /**
     * type
     */
    @Override
    protected String type() {
        return " number";
    }

    /**
     * processValue
     * @param value value
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

    /**
     * create
     * @param name name
     */
    public static CommandDouble create(String name){
        return new CommandDouble(name);
    }
}
