package tools.gnzlz.command.type;

import tools.gnzlz.command.Command;
import tools.gnzlz.command.CommandOption;
import tools.gnzlz.command.ExposeCommand;
import tools.gnzlz.command.object.Option;

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
