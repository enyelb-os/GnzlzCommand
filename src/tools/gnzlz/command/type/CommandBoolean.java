package tools.gnzlz.command.type;

import tools.gnzlz.command.CommandBasic;
import tools.gnzlz.command.ExposeCommand;

public class CommandBoolean extends CommandBasic<Boolean, CommandBoolean> {

    /**
     * CommandBoolean
     * @param name name
     */
    public CommandBoolean(String name) {
        super(name);
    }

    /**
     * type
     */
    @Override
    protected String type() {
        return " bool";
    }

    /**
     * processValue
     * @param value value
     */
    @Override
    protected Boolean processValue(Object value) {
        if(value instanceof String s && !s.isEmpty()){
            return s.equalsIgnoreCase("true") || s.equals("1");
        } else if (value instanceof Boolean b){
            return b;
        }
        ExposeCommand.error(this, "value is not boolean");
        return null;
    }

    /**
     * create
     * @param name name
     */
    public static CommandBoolean create(String name){
        return new CommandBoolean(name);
    }
}
