package tools.gnzlz.command.type;

import tools.gnzlz.command.Command;

public class CommandString extends Command<String, CommandString> {

    /***************************************
     * constructor
     * @param name
     **************************************
     */

    protected CommandString(String name) {
        super(name);
    }

    /**
     * type
     *
     */

    @Override
    public String type() {
        return "";
    }

    /**
     * converter
     *
     */

    @Override
    public Object valueProcess(Object value) {
        if(value instanceof String){
            return ((String) value).isEmpty() ? null : value;
        }
        return null;
    }

    /***************************************
     * static create
     * @param name
     ***************************************/

    public static CommandString create(String name){
        return new CommandString(name);
    }
}
