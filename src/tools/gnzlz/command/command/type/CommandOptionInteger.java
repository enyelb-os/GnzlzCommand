package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.object.Option;

public class CommandOptionInteger extends Command<Option<Integer>, Integer, CommandOptionInteger> {

    /***************************************
     * constructor
     * @param name name
     **************************************
     */

    public CommandOptionInteger(String name) {
        super(name);
    }

    /***************************************
     * static create
     * @param name name
     ***************************************/

    public static CommandOptionInteger create(String name){
        return new CommandOptionInteger(name);
    }

    @Override
    public Integer valueProcess(Object value) {
        if(value instanceof Integer){
            if(this.value.valid((Integer) value)){
                return (Integer) value;
            }
        }
        return null;
    }

    /***************************************
     * set option
     * @param value value
     ***************************************/

    public CommandOptionInteger option(Command<?,Integer,?> value) {
        return this.value(Option.integer().reference(value));
    }

    /***************************************
     * set option
     * @param value value
     ***************************************/

    public CommandOptionInteger option(Integer ... value) {
        return this.value(Option.integer(value));
    }
}
