package tools.gnzlz.command.init;

import tools.gnzlz.command.Command;
import tools.gnzlz.command.result.ExposeResultCommand;
import tools.gnzlz.command.result.ResultCommand;

public class InitCommand<Type> {

    /**
     * resultCommand
     */
    ResultCommand<Type> resultCommand;

    /**
     * InitCommand
     * @param command command
     * @param value value
     */
     InitCommand(Command<?,Type,?> command, Type value){
        this.resultCommand = ExposeResultCommand.create(command, value);
     }

    /**
     * create
     * @param command command
     * @param value value
     */
    public static <Type> InitCommand<Type> create(Command<?,Type,?> command, Type value){
        return new InitCommand<Type>(command, value);
    }
}
