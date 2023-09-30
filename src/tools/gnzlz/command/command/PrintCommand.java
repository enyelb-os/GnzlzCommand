package tools.gnzlz.command.command;

import tools.gnzlz.command.command.object.ExposeOption;
import tools.gnzlz.command.command.object.Option;
import tools.gnzlz.command.process.Process;
import tools.gnzlz.command.result.*;

import java.io.IOException;
import java.util.ArrayList;

public class PrintCommand {

    /**
     * Default methods
     */

    public static int separator = 70;

    /**
     * Default methods
     */

    public void clearConsole(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ignored) {}
    }

    /**
     * Default methods
     */

    public String taps(int taps) {
        StringBuilder staps = new StringBuilder();
        for (int i = 0; i < taps; i++) {
            staps.append("   ");
        }
        return staps.toString();
    }

    /**
     * Default methods
     */

     public void printMenuMultipleItem(Command<?,?,?> command){
         Process.FUNCTION_OUTPUT_PROCESS_RESULT.println(" 1. Add item");
         Process.FUNCTION_OUTPUT_PROCESS_RESULT.println(" 0. Exit continue");
         Process.FUNCTION_OUTPUT_PROCESS_RESULT.println("");
         Process.FUNCTION_OUTPUT_PROCESS_RESULT.printTitle(ExposeCommand.message(command), separator);
         Process.FUNCTION_OUTPUT_PROCESS_RESULT.println("");
         Process.FUNCTION_OUTPUT_PROCESS_RESULT.print("Choose an option?: ");
    }

    /**
     * Default methods
     */

    private boolean isEmptyResultListCommand(ArrayList<ResultCommand<?>> listCommand){
        for (ResultCommand<?> resultCommand : listCommand) {
            if(resultCommand.value() != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Default methods
     */

     public void printResultListCommand(ResultListCommand resultListCommand, String text){
        ArrayList<ResultCommand<?>> resultCommands = ExposeResultListCommand.resultCommands(resultListCommand);
        clearConsole();
        Process.FUNCTION_OUTPUT_PROCESS_RESULT.println();
        Process.FUNCTION_OUTPUT_PROCESS_RESULT.printTitle(text, separator);
        Process.FUNCTION_OUTPUT_PROCESS_RESULT.println();
        if (isEmptyResultListCommand(resultCommands)) {
            Process.FUNCTION_OUTPUT_PROCESS_RESULT.println("List: Empty");
        } else {
            printResultListCommand(resultCommands, 0,true);
        }
        Process.FUNCTION_OUTPUT_PROCESS_RESULT.println();
        Process.FUNCTION_OUTPUT_PROCESS_RESULT.printSeparator(separator);
        Process.FUNCTION_OUTPUT_PROCESS_RESULT.println();
    }

    /**
     * Default methods
     */

    private boolean isMultiline(ResultListCommand resultListCommand) {
        ArrayList<ResultCommand<?>> resultCommands = ExposeResultListCommand.resultCommands(resultListCommand);
        for (ResultCommand<?> resultCommand: resultCommands) {
            if (resultCommand.value() instanceof ResultListCommand) {
                boolean status = isMultiline((ResultListCommand) resultCommand.value());
                if (status) {
                    return true;
                }
            } else if (resultCommand.value() instanceof ResultArrayListCommand) {
                boolean status = isMultiline((ResultArrayListCommand) resultCommand.value());
                if (status) {
                    return true;
                }
            }
        }
        return resultCommands.size() > 1;
    }

    /**
     * Default methods
     */

    private boolean isMultiline(ResultArrayListCommand resultArrayListCommand) {
        ArrayList<ResultListCommand> resultListCommands = ExposeResultArrayCommand.resultCommands(resultArrayListCommand);
        for (ResultListCommand resultListCommand: resultListCommands) {
            boolean status = isMultiline(resultListCommand);
            if (status) {
                return true;
            }
        }
        return false;
    }

    /**
     * Default methods
     */

    public void printResultListCommand(ArrayList<ResultCommand<?>> listCommand, int index, boolean ln){
        for (ResultCommand<?> resultCommand: listCommand) {

            if (resultCommand.value() instanceof ResultListCommand) {
                Process.FUNCTION_OUTPUT_PROCESS_RESULT.println(taps(index) + resultCommand.name() + ": ");
                printResultListCommand(ExposeResultListCommand.resultCommands((ResultListCommand) resultCommand.value()), index+1, true);
            } else if (resultCommand.value() instanceof ResultArrayListCommand resultArrayListCommand) {
                ArrayList<ResultListCommand> resultArrayListCommands = ExposeResultArrayCommand.resultCommands(resultArrayListCommand);
                if (!resultArrayListCommands.isEmpty()) {

                    int item = 1;
                    boolean isMultiLine = isMultiline(resultArrayListCommand);
                    Process.FUNCTION_OUTPUT_PROCESS_RESULT.print(taps(index) + resultCommand.name() + ": ".concat(isMultiLine ? System.lineSeparator() : ""));
                    for (ResultListCommand resultListCommand: resultArrayListCommands) {
                        if(isMultiLine) {
                            Process.FUNCTION_OUTPUT_PROCESS_RESULT.println(taps(index+1) + "item "+item+": ");
                        }
                        printResultListCommand(ExposeResultListCommand.resultCommands(resultListCommand), index + (isMultiLine ? 2 : 1), isMultiLine);
                        Process.FUNCTION_OUTPUT_PROCESS_RESULT.print(isMultiLine ? "" : (resultArrayListCommands.size() == item ? System.lineSeparator() : ","));
                        item++;

                    }
                }
            } else if(resultCommand.value() != null){
                Process.FUNCTION_OUTPUT_PROCESS_RESULT.print(( ln ? taps(index) + resultCommand.name() + ": " : "") + resultCommand.value() + ( ln ? System.lineSeparator() : ""));
            }
        }
    }

    /**
     * Default methods
     */

     public void printQuestion(Command<?,?,?> command, ResultCommand<?> resultCommand){
        Process.FUNCTION_OUTPUT_PROCESS_QUESTION.print(ExposeCommand.message(command) /*+ command.type()*/ + isDefault(command, resultCommand) + ": ");
    }

    /**
     * Default methods
     */

    private String isDefault(Object value){
        if ((value instanceof Integer || value instanceof Double || value instanceof Boolean || value instanceof String)) {
            return " (Default: " + value.toString() +")";
        } else if (value instanceof Option){
            if(ExposeOption.value((Option<?>) value) != null){
                return isDefault(ExposeOption.value((Option<?>) value));
            }
        }
        return "";
    }

    /**
     * Default methods
     */

    private String isDefault(Command<?,?,?> command, ResultCommand<?> resultCommand){
        String isDefault = isDefault(resultCommand.value());
        if(!isDefault.isEmpty()){
            return isDefault;
        }else if (ExposeCommand.value(command) instanceof Option) {
            return isDefault(ExposeOption.value((Option<?>) ExposeCommand.value(command)));
        } else {
            return isDefault(ExposeCommand.value(command));
        }
    }
}
