package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.CommandOption;
import tools.gnzlz.command.command.object.Option;

public class CommandOptionInteger extends CommandOption<Integer, CommandOptionInteger> {

    /***************************************
     * constructor
     * @param name name
     **************************************
     */

    public CommandOptionInteger(String name) {
        super(name);
    }

    /**
     * type
     */

    @Override
    public String type() {
        return this.value.toString() ;
    }

    /***************************************
     * static create
     * @param name name
     ***************************************/

    public static CommandOptionInteger create(String name){
        return new CommandOptionInteger(name);
    }

    @Override
    protected Integer processValue(Object value) {
        if(value instanceof Integer integer){
            if(this.value.valid(integer)){
                return integer;
            }
        }
        return null;
    }

    /***************************************
     * set option
     * @param value value
     ***************************************/

    @Override
    public CommandOptionInteger option(Command<?,Integer,?> value) {
        this.value(Option.integer().reference(value));
        return this;
    }

    /***************************************
     * set option
     * @param value value
     ***************************************/

    @Override
    public CommandOptionInteger option(Integer ... value) {
        this.value(Option.integer(value));
        return this;
    }
}
