package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.object.Option;

public class CommandOptionString extends Command<Option<String>, String, CommandOptionString> {

    /**
     * constructor
     * @param name name
     *
     */

    public CommandOptionString(String name) {
        super(name);
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
    public String valueProcess(Object value) {
        if(this.value.valid(value.toString())){
            return value.toString();
        }
        return null;
    }

    /**
     * set option
     * @param value value
     */

    public CommandOptionString option(Command<?,String,?> value) {
        return this.value(Option.string().reference(value));
    }

    /**
     * set option
     * @param value value
     */

    public CommandOptionString option(String ... value) {
        return this.value(Option.string(value));
    }
}
