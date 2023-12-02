package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.CommandBasic;

public class CommandInteger extends CommandBasic<Integer, CommandInteger> {

    /**
     * CommandInteger
     * @param name name
     */
    public CommandInteger(String name) {
        super(name);
    }

    /**
     * type
     */
    @Override
    protected String type() {
        return " int";
    }

    /**
     * processValue
     * @param value value
     */
    @Override
    protected Integer processValue(Object value) {
        if(value instanceof String str){
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException ignored) {}
        } else if(value instanceof Integer integer){
            return integer;
        }
        return null;
    }

    /**
     * create
     * @param name name
     */
    public static CommandInteger create(String name){
        return new CommandInteger(name);
    }
}
