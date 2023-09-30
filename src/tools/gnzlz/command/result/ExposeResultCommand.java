package tools.gnzlz.command.result;

import tools.gnzlz.command.command.Command;

public class ExposeResultCommand {

    public static <Type> ResultCommand<Type> create(Command<?,Type,?> command, Type type){
        return new ResultCommand<Type>(command, type);
    }

    public static Command<?,?,?> command(ResultCommand<?> resultCommand){
        return resultCommand.command;
    }

    public static Object value(ResultCommand<?> resultCommand){
        return resultCommand.value;
    }

    public static <R> void value(ResultCommand<R> resultCommand, R value){
        resultCommand.value(value);
    }

    public static boolean assign(ResultCommand<?> resultCommand){
        return resultCommand.assign;
    }

    public static void assign(ResultCommand<?> resultCommand, boolean assign){
        resultCommand.assign = assign;
    }
}
