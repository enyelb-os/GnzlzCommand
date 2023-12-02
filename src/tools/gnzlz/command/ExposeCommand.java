package tools.gnzlz.command;

import tools.gnzlz.command.functional.FunctionRequiredCommand;
import tools.gnzlz.command.result.ResultCommand;
import tools.gnzlz.command.result.ResultListCommand;

import java.util.ArrayList;

public class ExposeCommand {

    /**
     * name
     * @param command command
     */
    public static String name(Command<?,?,?> command){
        return command.name;
    }

    /**
     * value
     * @param <T> T
     * @param command command
     */
    public static <T> T value(Command<T,?,?> command){
        return command.value;
    }

    /**
     * message
     * @param command command
     */
    public static String message(Command<?,?,?> command){
        return command.message;
    }

    /**
     * commands
     * @param command command
     */
    public static ArrayList<String> commands(Command<?,?,?> command){
        return command.commands;
    }

    /**
     * required
     * @param command command
     */
    public static FunctionRequiredCommand required(Command<?,?,?> command){
        return command.required;
    }

    /**
     * processValue
     * @param <R> R
     * @param command command
     * @param processValue processValue
     */
    public static <R> R processValue(Command<?,R,?> command, Object processValue){
        return command.processValue(processValue);
    }

    /**
     * processArgs
     * @param <R> R
     * @param command command
     * @param resultListCommand rlc
     * @param value value
     */
    public static <R> ResultCommand<R> processArgs(Command<?,R,?> command, ResultListCommand resultListCommand, Object value){
        return command.processArgs(resultListCommand, value);
    }

    /**
     * processArgs
     * @param <R> R
     * @param command command
     * @param resultListCommand rlc
     */
    public static <R> void processQuestion(Command<?,R,?> command, ResultListCommand resultListCommand, ResultListCommand allResultListCommand){
        command.processQuestion(resultListCommand, allResultListCommand);
    }
}
