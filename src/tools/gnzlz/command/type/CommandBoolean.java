package tools.gnzlz.command.type;

import tools.gnzlz.command.Command;

public class CommandBoolean extends Command<Boolean, CommandBoolean> {

    /***************************************
     * constructor
     * @param name
     **************************************
     */

    protected CommandBoolean(String name) {
        super(name);
    }

    /**
     * type
     *
     */

    @Override
    public String type() {
        return "(boolean)";
    }

    /**
     * converter
     *
     */

    @Override
    public Object valueProcess(Object value) {
        if(value instanceof String){
            return value.toString().equalsIgnoreCase("true") || value.toString().equals("1");
        } else if (value instanceof Boolean){
            return value;
        }
        return null;
    }

    /***************************************
     * static create
     * @param name
     ***************************************/

    public static CommandBoolean create(String name){
        return new CommandBoolean(name);
    }
}
