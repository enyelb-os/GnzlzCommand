package tools.gnzlz.command.result;

import tools.gnzlz.command.command.Command;

public class ExposeResultCommand {

    /**
     * create
     * @param <Type> type
     * @param command Command
     * @param type type
     */
    public static <Type> ResultCommand<Type> create(Command<?,Type,?> command, Type type){
        return new ResultCommand<>(command, type);
    }

    /**
     * command
     * @param <R> R
     * @param resultCommand resultCommand
     */
    public static <R> Command<?,R,?> command(ResultCommand<R> resultCommand){
        return resultCommand.command;
    }

    /**
     * value
     * @param resultCommand resultCommand
     */
    public static Object value(ResultCommand<?> resultCommand){
        return resultCommand.value;
    }

    /**
     * value
     * @param <R> R
     * @param resultCommand resultCommand
     * @param value value
     */
    public static <R> void value(ResultCommand<R> resultCommand, R value){
        resultCommand.value(value);
    }

    /**
     * assign
     * @param resultCommand resultCommand
     */
    public static boolean assign(ResultCommand<?> resultCommand){
        return resultCommand.assign;
    }

    /**
     * assign
     * @param resultCommand resultCommand
     * @param assign assign
     */
    public static void assign(ResultCommand<?> resultCommand, boolean assign){
        resultCommand.assign = assign;
    }
}
