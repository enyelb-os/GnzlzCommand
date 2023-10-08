package tools.gnzlz.command.command;

import tools.gnzlz.command.command.functional.FunctionRequiredCommand;
import tools.gnzlz.command.process.functional.FunctionInputProcess;
import tools.gnzlz.command.result.ResultCommand;
import tools.gnzlz.command.result.ResultListCommand;

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

    public static <R> R processValue(Command<?,R,?> command, Object processValue){
        return command.processValue(processValue);
    }

    public static <R> ResultCommand<R> process(Command<?,R,?> command, FunctionInputProcess inputProcess, ResultListCommand resultListCommand, ResultListCommand allResultListCommand){
        return command.process(inputProcess, resultListCommand, allResultListCommand);
    }

    public static <R> ResultCommand<R> args(Command<?,R,?> command, ResultListCommand resultListCommand, Object value){
        return command.args(resultListCommand, value);
    }
}
