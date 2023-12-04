package tools.gnzlz.command.type;

import tools.gnzlz.command.CommandBasic;
import tools.gnzlz.command.ExposeCommand;

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
        ExposeCommand.error(this, "value is not integer");
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
