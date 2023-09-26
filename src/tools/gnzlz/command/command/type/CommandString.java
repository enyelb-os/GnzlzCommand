package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;

public class CommandString extends Command<String, String, CommandString> {

    /***************************************
     * constructor
     * @param name name
     **************************************
     */

    public CommandString(String name) {
        super(name);
    }


    /**
     * converter
     *
     */

    @Override
    public String valueProcess(Object value) {
        if(value instanceof String){
            return ((String) value).isEmpty() ? null : value.toString();
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
