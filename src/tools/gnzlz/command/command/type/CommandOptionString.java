package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.CommandOption;
import tools.gnzlz.command.command.object.Option;

public class CommandOptionString extends CommandOption<String, CommandOptionString> {

    /**
     * constructor
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
    public String type() {
        return this.value.toString();
    }

    /**
     * static create
     * @param name name
     */

    public static CommandOptionString create(String name){
        return new CommandOptionString(name);
    }

    /**
     * type
     *
     */

    @Override
    protected String processValue(Object value) {
        if(this.value.valid(value.toString())){
            return value.toString();
        }
        return null;
    }

    /**
     * set option
     * @param value value
     */

    @Override
    public CommandOptionString option(Command<?,String,?> value) {
        this.value(Option.string().reference(value));
        return this;
    }

    /**
     * set option
     * @param value value
     */

    @Override
    public CommandOptionString option(String ... value) {
        this.value(Option.string(value));
        return this;
    }
}
