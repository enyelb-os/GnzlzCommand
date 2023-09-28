package tools.gnzlz.command.init;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.result.ExposeResultCommand;
import tools.gnzlz.command.result.ResultCommand;

public class InitCommand<Type> {

    /**
     * vars
     */


    protected ResultCommand<Type> resultCommand;

    /**
     * constructor
     * @param command command
     * @param value value
     */

     protected InitCommand(Command<?,Type,?> command, Type value){
        this.resultCommand = ExposeResultCommand.create(command, value);
     }

    /**
     * create
     */

    public static <Type> InitCommand<Type> create(Command<?,Type,?> command, Type value){
        return new InitCommand<Type>(command, value);
    }
}
