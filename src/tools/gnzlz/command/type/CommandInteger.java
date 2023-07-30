package tools.gnzlz.command.type;

import tools.gnzlz.command.Command;

public class CommandInteger extends Command<Integer, CommandInteger> {

    /***************************************
     * constructor
     * @param name
     **************************************
     */

    protected CommandInteger(String name) {
        super(name);
    }

    /**
     * type
     *
     */

    @Override
    public String type() {
        return "(int)";
    }

    /**
     * converter
     *
     */

    @Override
    public Object valueProcess(Object value) {
        if(value instanceof String){
            try {
                return Integer.parseInt(String.valueOf(value));
            } catch (NumberFormatException exception) {}
        } else if(value instanceof Integer){
            return value;
        }
        return null;
    }


    /***************************************
     * static create
     * @param name
     ***************************************/

    public static CommandInteger create(String name){
        return new CommandInteger(name);
    }
}
