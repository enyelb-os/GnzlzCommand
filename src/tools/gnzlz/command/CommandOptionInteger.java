package tools.gnzlz.command;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.CommandOption;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.Option;

public class CommandOptionInteger extends CommandOption<Integer, CommandOptionInteger> {

    /**
     * CommandOptionInteger
     * @param name name
     */
    public CommandOptionInteger(String name) {
        super(name);
    }

    /**
     * type
     */
    @Override
    protected String type() {
        if (ExposeCommand.value(this) != null) {
            return ExposeCommand.value(this).toString();
        }
        return " int";
    }

    /**
     * create
     * @param name name
     */
    public static CommandOptionInteger create(String name){
        return new CommandOptionInteger(name);
    }

    /**
     * processValue
     * @param value value
     */
    @Override
    protected Integer processValue(Object value) {
        if(value instanceof Integer integer){
            if(ExposeCommand.value(this).valid(integer)){
                return integer;
            }
        }
        ExposeCommand.error(this, "value is not valid");
        return null;
    }

    /**
     * option
     * @param value value
     */
    @Override
    public CommandOptionInteger option(Command<?,Integer,?> value) {
        this.value(Option.integer().reference(value));
        return this;
    }

    /**
     * option
     * @param value value
     */
    @Override
    public CommandOptionInteger option(Integer ... value) {
        this.value(Option.integer(value));
        return this;
    }
}
