package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;

public class CommandInteger extends Command<Integer, Integer, CommandInteger> {

    /***************************************
     * constructor
     * @param name name
     **************************************
     */

    public CommandInteger(String name) {
        super(name);
    }

    /**
     * converter
     *
     */

    @Override
    public Integer valueProcess(Object value) {
        if(value instanceof String){
            try {
                return Integer.parseInt(String.valueOf(value));
            } catch (NumberFormatException ignored) {}
        } else if(value instanceof Integer){
            return (Integer) value;
        }
        return null;
    }


    /***************************************
     * static create
     * @param name name
     ***************************************/

    public static CommandInteger create(String name){
        return new CommandInteger(name);
    }
}
