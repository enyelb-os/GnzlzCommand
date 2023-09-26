package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;

public class CommandBoolean extends Command<Boolean, Boolean, CommandBoolean> {

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
    public Boolean valueProcess(Object value) {
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
