package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.CommandOption;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.Option;

public class CommandOptionString extends CommandOption<String, CommandOptionString> {

    /**
     * CommandOptionString
     * @param name name
     *
     */
    public CommandOptionString(String name) {
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
     *  create
     * @param name name
     */
    public static CommandOptionString create(String name){
        return new CommandOptionString(name);
    }

    /**
     * processValue
     * @param value value
     */
    @Override
    protected String processValue(Object value) {
        if(value != null && ExposeCommand.value(this).valid(value.toString())){
            return value.toString();
        }
        return null;
    }

    /**
     * option
     * @param value value
     */
    @Override
    public CommandOptionString option(Command<?,String,?> value) {
        this.value(Option.string().reference(value));
        return this;
    }

    /**
     * option
     * @param value value
     */
    @Override
    public CommandOptionString option(String ... value) {
        this.value(Option.string(value));
        return this;
    }
}
