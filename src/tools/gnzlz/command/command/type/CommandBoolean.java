package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.CommandBasic;

public class CommandBoolean extends CommandBasic<Boolean, CommandBoolean> {

    /***************************************
     * constructor
     * @param name name
     **************************************
     */

    public CommandBoolean(String name) {
        super(name);
    }

    /**
     * converter
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

    /***************************************
     * static create
     * @param name name
     ***************************************/

    public static CommandBoolean create(String name){
        return new CommandBoolean(name);
    }
}
