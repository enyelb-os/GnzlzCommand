package tools.gnzlz.command.type;

import tools.gnzlz.command.Command;
import tools.gnzlz.command.Option;

public class CommandOption extends Command<Option, CommandOption> {

    /***************************************
     * constructor
     * @param name
     **************************************
     */

    protected CommandOption(String name) {
        super(name);
    }

    /***************************************
     * static create
     * @param name
     ***************************************/

    public static CommandOption create(String name){
        return new CommandOption(name);
    }

    /**
     * type
     *
     */

    @Override
    public String type() {
        return "(Option)" + value.toString();
    }

    @Override
    public Object valueProcess(Object value) {
        if(this.value.valid(value)){
            return value;
        }
        return null;
    }

    /***************************************
     * set option
     * @param value
     ***************************************/

    public CommandOption option(Command value) {
        return this.value(Option.string().reference(value));
    }

    /***************************************
     * set option
     * @param value
     ***************************************/

    public CommandOption option(String ... value) {
        return this.value(Option.string(value));
    }

    /***************************************
     * set option
     * @param value
     ***************************************/

    public CommandOption option(Integer ... value) {
        return this.value(Option.integer(value));
    }
}
