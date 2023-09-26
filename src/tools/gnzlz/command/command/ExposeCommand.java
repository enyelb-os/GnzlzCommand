package tools.gnzlz.command.command;

import tools.gnzlz.command.funtional.FunctionRequiredCommand;
import tools.gnzlz.command.result.ResultCommand;

import java.util.ArrayList;

public class ExposeCommand {

    public static String name(Command<?,?,?> command){
        return command.name;
    }

    public static <T> T value(Command<T,?,?> command){
        return command.value;
    }

    public static String message(Command<?,?,?> command){
        return command.message;
    }

    public static ArrayList<String> commands(Command<?,?,?> command){
        return command.commands;
    }

    public static FunctionRequiredCommand required(Command<?,?,?> command){
        return command.required;
    }

    public static <R> ResultCommand<R> createResultCommand(Command<?,R,?> command, R object){
        return command.createResultCommand(object);
    }
}
