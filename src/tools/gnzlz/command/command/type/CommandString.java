package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.CommandBasic;

public class CommandString extends CommandBasic<String, CommandString> {

    /**
     * constructor
     * @param name name
     */

    public CommandString(String name) {
        super(name);
    }

    /**
     * type
     */

    @Override
    public String type() {
        return " string";
    }


    /**
     * converter
     * @param value value
     */

    @Override
    protected String processValue(Object value) {
        if(value instanceof String str){
            return str.isEmpty() ? null : str;
        }
        return null;
    }

    /**
     * static create
     * @param name name
     */

    public static CommandString create(String name){
        return new CommandString(name);
    }
}
