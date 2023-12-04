package tools.gnzlz.command;

import tools.gnzlz.command.functional.FunctionRequiredCommand;
import tools.gnzlz.command.functional.FunctionSetError;
import tools.gnzlz.command.functional.FunctionValidCommand;
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
     * valid
     * @param command command
     */
    public static FunctionValidCommand valid(Command<?,?,?> command) {
        return command.valid;
    }

    /**
     * error
     * @param command command
     */
    public static String[] error(Command<?,?,?> command) {
        String[] array = new String[command.errors.size()];
        for (int i = 0; i < command.errors.size() ; i++) {
            array[i] = command.errors.get(i);
        }
        command.errors.clear();
        return array;
    }

    /**
     * functionError
     * @param command command
     */
    public static FunctionSetError functionError(Command<?,?,?> command) {
        return command.error;
    }

    /**
     * error
     * @param command command
     * @param error error
     */
    public static void error(Command<?,?,?> command, String error) {
        command.error.set(error);
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
