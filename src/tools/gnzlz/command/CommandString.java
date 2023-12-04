package tools.gnzlz.command;

import tools.gnzlz.command.command.CommandBasic;
import tools.gnzlz.command.command.ExposeCommand;

public class CommandString extends CommandBasic<String, CommandString> {

    /**
     * CommandString
     * @param name name
     */
    public CommandString(String name) {
        super(name);
    }

    /**
     * type
     */
    @Override
    protected String type() {
        return " string";
    }

    /**
     * processValue
     * @param value value
     */
    @Override
    protected String processValue(Object value) {
        if(value instanceof String str){
            return str.isEmpty() ? null : str;
        }
        ExposeCommand.error(this, "value is empty");
        return null;
    }

    /**
     * create
     * @param name name
     */
    public static CommandString create(String name){
        return new CommandString(name);
    }
}
