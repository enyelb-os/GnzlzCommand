package tools.gnzlz.command;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.ExposeOption;
import tools.gnzlz.command.command.object.Option;
import tools.gnzlz.command.funtional.PrintConsole;
import tools.gnzlz.command.result.ResultCommand;
import tools.gnzlz.command.result.ExposeResultListCommand;
import tools.gnzlz.command.result.ResultListCommand;

import java.io.IOException;
import java.util.ArrayList;

public class Print {

    /***************************************
     * Default methods
     ***************************************/

    public static int separator = 70;

    /***************************************
     * Default methods
     ***************************************/

     static void clearConsole(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ignored) {}
    }

    /***************************************
     * Default methods
     ***************************************/

     static String taps(int taps) {
        StringBuilder staps = new StringBuilder();
        for (int i = 0; i < taps; i++) {
            staps.append("   ");
        }
        return staps.toString();
    }

    /***************************************
     * Default methods
     ***************************************/

     static void printMenuMultipleItem(PrintConsole console, Command<?,?,?> command){
        console.println(" 1. Add item");
        console.println(" 0. Exit continue");
        console.println("");
        console.printTitle(ExposeCommand.message(command), separator);
        console.println("");
        console.print("Choose an option?: ");
    }

    /***************************************
     * Default methods
     ***************************************/

    private static boolean isEmptyResultListCommand(ArrayList<ResultCommand<?>> listCommand){
        for (ResultCommand<?> resultCommand : listCommand) {
            if(resultCommand.value() != null) {
                return false;
            }
        }
        return true;
    }

    /***************************************
     * Default methods
     ***************************************/

     static void printResultListCommand(PrintConsole console, ArrayList<ResultCommand<?>> listCommand, boolean printObjects, String text){
        if(printObjects) {
            clearConsole();
            console.println();
            console.printTitle(text, separator);
            console.println();
            if (isEmptyResultListCommand(listCommand)) {
                console.println("List: Empty");
            } else {
                printResultListCommand(console, listCommand, 0, true);
            }
            console.println();
            console.printSeparator(separator);
            console.println();
        }
    }

    /***************************************
     * Default methods
     ***************************************/

    static void printResultListCommand(PrintConsole console, ArrayList<ResultCommand<?>> listCommand, int index, boolean ln){
        for (ResultCommand<?> resultCommand: listCommand) {
            if(resultCommand.value() instanceof ResultListCommand){
                console.println(taps(index) + resultCommand.name() + ": ");
                printResultListCommand(console, ExposeResultListCommand.resultCommands((ResultListCommand) resultCommand.value()), index+1, true);

            } else if(resultCommand.value() instanceof ArrayList){
                ArrayList<ResultListCommand> resultListCommands = (ArrayList<ResultListCommand>) resultCommand.value();
                if(!resultListCommands.isEmpty()){
                    console.print(taps(index) + resultCommand.name() + ": ");
                    int item = 1;
                    for (ResultListCommand resultListCommand: resultListCommands) {
                        if(ExposeResultListCommand.resultCommands(resultListCommand).size() == 1 &&
                                (!(ExposeResultListCommand.resultCommands(resultListCommand).get(0).value() instanceof ArrayList) ||
                                        !(ExposeResultListCommand.resultCommands(resultListCommand).get(0).value() instanceof ResultListCommand))) {

                            printResultListCommand(console, ExposeResultListCommand.resultCommands(resultListCommand), index+1, false);

                            if(resultListCommands.size() == item){
                                console.println();
                            } else {
                                console.print(",");
                            }
                        } else {
                            if(item == 1){
                                console.println();
                            }
                            if(resultListCommands.size() == 1) {
                                printResultListCommand(console, ExposeResultListCommand.resultCommands(resultListCommand), index+1, true);
                            } else {
                                console.println(taps(index+1) + "item "+item+": ");
                                printResultListCommand(console, ExposeResultListCommand.resultCommands(resultListCommand), index+2, true);
                            }

                        }

                        item++;

                    }
                }

            } else if(resultCommand.value() != null){
                if(ln) {
                    console.println(taps(index) + resultCommand.name() + ": " + resultCommand.value());
                } else {
                    console.print("" + resultCommand.value());
                }
            }
        }
    }

    /***************************************
     * Default methods
     ***************************************/

     static void printQuestion(PrintConsole console,Command<?,?,?> command, ResultCommand<?> resultCommand){

        console.print(ExposeCommand.message(command) /*+ command.type()*/ + isDefault(command, resultCommand) + ": ");

    }

    /***************************************
     * Default methods
     ***************************************/

    private static String isDefault(Object value){
        if ((value instanceof Integer || value instanceof Double || value instanceof Boolean || value instanceof String)) {
            return " (Default: " + value.toString() +")";
        } else if (value instanceof Option){
            if(ExposeOption.value((Option<?>) value) != null){
                return isDefault(ExposeOption.value((Option<?>) value));
            }
        }
        return "";
    }

    /***************************************
     * Default methods
     ***************************************/

    private static String isDefault(Command<?,?,?> command, ResultCommand<?> resultCommand){
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
