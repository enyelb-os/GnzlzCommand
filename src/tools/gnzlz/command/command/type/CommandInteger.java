package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.CommandBasic;

public class CommandInteger extends CommandBasic<Integer, CommandInteger> {

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
    protected Integer processValue(Object value) {
        if(value instanceof String str){
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException ignored) {}
        } else if(value instanceof Integer integer){
            return integer;
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
