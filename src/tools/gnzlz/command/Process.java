package tools.gnzlz.command;

import tools.gnzlz.command.funtional.PrintConsole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Process {

    /***************************************
     * Default methods
     ***************************************/

    public static int separator = 70;

    /***************************************
     * Default methods
     ***************************************/

    private static PrintConsole printConsole = (text -> {
        System.out.print(text);
    });

    /***************************************
     * Default methods
     ***************************************/

    public static void clearConsole(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ex) {}
    }

    /***************************************
     * Default methods
     ***************************************/

    private static String taps(int taps) {
        String staps = "";
        for (int i = 0; i < taps; i++) {
            staps += "   ";
        }
        return staps;
    }

    /***************************************
     * Default methods
     ***************************************/

    private static void printMenuMultipleItem(PrintConsole console, Command command){
        console.println(" 1. Add item");
        console.println(" 0. Exit continue");
        console.println("");
        console.printTitle(command.resultCommand.message(), separator);
        console.println("");
        console.print("Choose an option?: ");
    }

    /***************************************
     * Default methods
     ***************************************/

    private static boolean isEmptyResultListCommand(ArrayList<ResultCommand> listCommand){
        for (ResultCommand resultCommand : listCommand) {
            if(resultCommand.value() != null) {
                return false;
            }
        }
        return true;
    }

    /***************************************
     * Default methods
     ***************************************/

    private static void printResultListCommand(PrintConsole console, ArrayList<ResultCommand> listCommand, boolean printObjects, String text){
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

    private static void printResultListCommand(PrintConsole console, ArrayList<ResultCommand> listCommand, int index, boolean ln){
        for (ResultCommand resultCommand: listCommand) {
            if(resultCommand.value() instanceof ResultListCommand){
                console.println(taps(index) + resultCommand.name() + ": ");
                printResultListCommand(console, ((ResultListCommand) resultCommand.value()).resultCommands, index+1, true);

            } else if(resultCommand.value() instanceof ArrayList){
                ArrayList<ResultListCommand> resultListCommands = (ArrayList<ResultListCommand>) resultCommand.value();
                if(!resultListCommands.isEmpty()){
                    console.print(taps(index) + resultCommand.name() + ": ");
                    int item = 1;
                    for (ResultListCommand resultListCommand: resultListCommands) {
                        if(resultListCommand.resultCommands.size() == 1 &&
                                (!(resultListCommand.resultCommands.get(0).value() instanceof ArrayList) ||
                                 !(resultListCommand.resultCommands.get(0).value() instanceof ResultListCommand))) {

                            printResultListCommand(console, resultListCommand.resultCommands, index+1, false);

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
                                printResultListCommand(console, resultListCommand.resultCommands, index+1, true);
                            } else {
                                console.println(taps(index+1) + "item "+item+": ");
                                printResultListCommand(console, resultListCommand.resultCommands, index+2, true);
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

    private static void printQuestion(PrintConsole console,Command command, ResultCommand resultCommand){

        console.print(command.message + command.type() + isDefault(command, resultCommand) + ": ");

    }

    /***************************************
     * Default methods
     ***************************************/

    private static String isDefault(Object value){
        if (value != null && (value instanceof Integer || value instanceof Double || value instanceof Boolean || value instanceof String)) {
            return " (Default: " + value.toString() +")";
        } else if (value instanceof Option){
            if(((Option) value).value != null){
                return isDefault(((Option) value).value);
            }
        }
        return "";
    }

    /***************************************
     * Default methods
     ***************************************/

    private static String isDefault(Command command, ResultCommand resultCommand){
        String isDefault = isDefault(resultCommand.value());
        if(!isDefault.isEmpty()){
            return isDefault;
        }else if (command.value instanceof Option) {
            return isDefault(((Option) command.value).value);
        } else {
            return isDefault(command.value);
        }
    }

    /***************************************
     * Default methods
     ***************************************/

    private static ResultCommand resultCommandCreate(ArrayList<ResultCommand> resultCommands, Command command, Object value) {
        ResultCommand resultCommand = resultCommand(resultCommands, command);
        if(resultCommand == null){
            resultCommand = new ResultCommand(command, value);
            resultCommands.add(resultCommand);
        }
        resultCommand.value(value);
        return resultCommand;
    }

    /***************************************
     * Default methods
     ***************************************/

    private static ResultCommand resultCommand(ArrayList<ResultCommand> resultCommands, Command command) {
        for (ResultCommand resultCommand: resultCommands) {
            if(resultCommand.command == command){
                return resultCommand;
            }
        }
        return null;
    }



    /***************************************
     * Default list options command reference
     ***************************************/

    private static void addOptionsCommandReference(Command command, ResultListCommand resultListCommand){
        if(command.value instanceof Option){
            if(((Option<?>) command.value).commandReference != null){
                resultListCommand.listCommands((resultCommand->{
                    if(((Option<?>) command.value).commandReference == resultCommand.command){
                        ((Option<Object>) command.value).options(resultCommand.value);
                    }
                }));
            }
        }
    }



    /***************************************
     * Process args
     * @param args
     * @param listCommand
     * @param resultListCommand
     *
     ***************************************/

    private static ResultListCommand processArgs(String[] args, ListCommand listCommand, ResultListCommand resultListCommand){
        String option = "";
        Object value = null;
        for (String code: args) {
            for (Command<?,?> command: listCommand.commands) {
                if (code.substring(0, 1).equals("-")) {
                    option = code;
                    value = true;
                } else {
                    value = code;
                }
                for (String commandOption: command.commands) {
                    if (option.equals(commandOption)) {
                        resultCommandCreate(resultListCommand.resultCommands, command, code).value(value).assign = true;
                        break;
                    }
                }
            }
        }
        return resultListCommand;
    }

    /***************************************
     * Process Question
     * @param listCommand
     * @param console
     * @param printObjects
     * @param text
     * @param resultListCommand
     * @param allResultListCommand
     *
     ***************************************/

    private static ResultListCommand processQuestion(ListCommand listCommand, PrintConsole console, boolean printObjects, String text, ResultListCommand resultListCommand, ResultListCommand allResultListCommand){
        /**
         * list
         */
        ArrayList<ResultCommand> resultCommands = resultListCommand.resultCommands;

        for (Command command: listCommand.commands) {

            ResultCommand resultCommand = resultCommandCreate(resultCommands, command, null);

            if(!resultCommand.assign && command.required.valid(allResultListCommand)) {

                addOptionsCommandReference(command, allResultListCommand);

                if(command.value instanceof ArrayListCommand) {

                    ArrayList<ResultListCommand> commands;
                    if(resultCommand.value() instanceof ArrayList){
                        commands = (ArrayList<ResultListCommand>) resultCommand.value();
                    } else {
                        commands = new ArrayList<ResultListCommand>();
                        resultCommand.value(commands);
                    }

                    String line;
                    do {
                        Scanner in = new Scanner(System.in);

                        printResultListCommand(console,resultCommands, printObjects, text);

                        printMenuMultipleItem(console, command);

                        line = in.nextLine();

                        if(line.equals("1") || line.equalsIgnoreCase("add")) {
                            ResultListCommand resultListCommandNew = ResultListCommand.create(new ArrayList<ResultCommand>());
                            commands.add(Process.processQuestion((ListCommand) command.value, printConsole, printObjects, command.message.isEmpty() ? command.name : command.message, resultListCommandNew, allResultListCommand));
                        }
                    } while(!line.equals("0") && !line.equalsIgnoreCase("exit"));

                } else if(command.value instanceof ListCommand) {

                    printResultListCommand(console, resultCommands, printObjects, text);

                    ResultListCommand resultListCommandTemp;
                    if(resultCommand.value() instanceof ResultListCommand){
                        resultListCommandTemp = (ResultListCommand) resultCommand.value();
                    } else {
                        resultListCommandTemp = ResultListCommand.create(new ArrayList<ResultCommand>());
                    }

                     resultListCommandTemp = Process.processQuestion((ListCommand) command.value, printConsole, printObjects, command.message.isEmpty() ? command.name : command.message, resultListCommandTemp, allResultListCommand);

                    resultCommand.value(resultListCommandTemp);

                } else {
                    do {

                        Scanner in = new Scanner(System.in);

                        printResultListCommand(console, resultCommands, printObjects, text);

                        printQuestion(console, command, resultCommand);

                        resultCommand.value(in.nextLine());

                        if (resultCommand.value() == null && command.resultCommand.value() != null) {
                            resultCommand.value(command.resultCommand.value());
                        }

                    } while (resultCommand.value() == null);
                }
            }
        }
        return resultListCommand;
    }

    /***************************************
     * @param args
     * @param listCommand
     * @param resultListCommand
     ***************************************/

    public static ResultListCommand args(String[] args, ListCommand listCommand, ResultListCommand resultListCommand) {
        return Process.processArgs(args, listCommand, resultListCommand);
    }

    /***************************************
     * @param args
     * @param listCommand
     ***************************************/

    public static ResultListCommand args(String[] args, ListCommand listCommand) {
        return Process.args(args, listCommand, ResultListCommand.create(new ArrayList<ResultCommand>()));
    }

    /***************************************
     * @param listCommand
     * @param console
     * @param resultListCommand
     ***************************************/

    public static ResultListCommand questions(ListCommand listCommand, PrintConsole console, ResultListCommand resultListCommand) {
        return Process.processQuestion(listCommand, console, true, "Objects", resultListCommand, resultListCommand);
    }

    /***************************************
     * @param listCommand
     * @param resultListCommand
     *
     ***************************************/

    public static ResultListCommand questions(ListCommand listCommand, ResultListCommand resultListCommand) {
        return Process.questions(listCommand, printConsole, resultListCommand);
    }

    /***************************************
     * @param listCommand
     *
     ***************************************/

    public static ResultListCommand questions(ListCommand listCommand) {
        return Process.questions(listCommand, printConsole, ResultListCommand.create(new ArrayList<ResultCommand>()));
    }

    /***************************************
     * @param listCommand
     * @param console
     * @param resultListCommand
     ***************************************/

    public static ResultListCommand argsAndQuestions(String[] args, ListCommand listCommand, PrintConsole console, ResultListCommand resultListCommand) {
        Process.processArgs(args, listCommand, resultListCommand);
        return Process.processQuestion(listCommand, console, true, "Objects", resultListCommand, resultListCommand);
    }

    /***************************************
     * @param listCommand
     * @param resultListCommand
     *
     ***************************************/

    public static ResultListCommand argsAndQuestions(String[] args, ListCommand listCommand, ResultListCommand resultListCommand) {
        return Process.argsAndQuestions(args, listCommand, printConsole, resultListCommand);
    }

    /**
     * @param listCommand
     *
     */

    public static ResultListCommand argsAndQuestions(String[] args, ListCommand listCommand) {
        return Process.argsAndQuestions(args, listCommand, printConsole, ResultListCommand.create(new ArrayList<ResultCommand>()));
    }
}
