package tools.gnzlz.command.type;

import tools.gnzlz.command.CommandBasic;

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
        if(value instanceof CommandString){
            return value.toString().equalsIgnoreCase("true") || value.toString().equals("1");
        } else if (value instanceof Boolean){
            return (Boolean) value;
        }
        return false;
    }

    /**
     * create
     * @param name name
     */
    public static CommandBoolean create(String name){
        return new CommandBoolean(name);
    }
}
