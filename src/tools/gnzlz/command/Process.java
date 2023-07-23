package tools.gnzlz.command;

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

    private static void printMenuMultipleItem(PrintConsole console,Command command){
        console.println(" 1. Add item");
        console.println(" 0. Exit continue");
        console.println("");
        console.printTitle(command.message, separator);
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
                printResultListCommand(console, listCommand, 0);
            }
            console.println();
            console.printSeparator(separator);
            console.println();
        }
    }

    /***************************************
     * Default methods
     ***************************************/

    private static void printResultListCommand(PrintConsole console, ArrayList<ResultCommand> listCommand, int index){
        for (ResultCommand resultCommand: listCommand) {
            if(resultCommand.value() instanceof ResultListCommand){
                console.println(taps(index) + resultCommand.name() + ": ");
                printResultListCommand(console, ((ResultListCommand) resultCommand.value()).resultCommands, index+1);

            } else if(resultCommand.value() instanceof ArrayList){
                console.println(taps(index) + resultCommand.name() + ": ");
                int item = 1;
                for (ResultListCommand resultListCommand:(ArrayList<ResultListCommand>) resultCommand.value()) {
                    console.println(taps(index+1) + "item "+item+": ");
                    printResultListCommand(console, resultListCommand.resultCommands, index+2);
                    item++;
                }

            } else if(resultCommand.value() != null){
                console.println(taps(index) + resultCommand.name() + ": " + resultCommand.value());
            }
        }
    }

    /***************************************
     * Default methods
     ***************************************/

    private static void printQuestion(PrintConsole console,Command command){

        console.print(command.message + type(command) + isDefault(command) + ": ");

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

    private static String isDefault(Command command){
        if (command.value instanceof Option) {
            return isDefault(((Option) command.value).value);
        } else {
            return isDefault(command.value);
        }
    }

    /***************************************
     * Default methods
     ***************************************/

    private static String typeValue(Option value){
        String type = "(";
        for (int i = 0; i < value.options.size(); i++) {
            type += (i !=0 ? "|": "") + value.options.get(i);
        }
        type += ")";
        return type;
    }

    /***************************************
     * Default methods
     ***************************************/
    private static String type(Command command){
        if (command.className == Integer.class) {
            return " (int)";
        } else if (command.className == Double.class) {
            return " (float)";
        } else if (command.className == Boolean.class) {
            return " (bool)";
        } else if (command.className == Option.class) {
            return " (Options) " + typeValue((Option) command.value);
        } else {
            return "";
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
     * Default list Commands
     ***************************************/

    public static ResultListCommand process(String[] args, ListCommand listCommand) {
        return Process.process(args, listCommand, printConsole, true);
    }

    /***************************************
     * Default list Commands
     ***************************************/

    private static ResultListCommand process(String[] args, ListCommand listCommand, PrintConsole console, boolean printObjects){
        return Process.process(args, listCommand, console, printObjects, "Objects");
    }

    /***************************************
     * Default list Commands
     ***************************************/

    private static ResultListCommand process(String[] args, ListCommand listCommand, PrintConsole console, boolean printObjects, String text){
        ArrayList<ResultCommand> resultCommands = new ArrayList<ResultCommand>();
        String option = "";
        Object value = null;
        for (String code: args) {
            for (Command command: listCommand.commands) {
                if (code.substring(0, 1).equals("-")) {
                    option = code;
                    value = true;
                } else {
                    value = code;
                }
                for (String commandOption: command.commands) {
                    if (option.equals(commandOption)) {
                        resultCommandCreate(resultCommands, command, code).value(value);
                        break;
                    }
                }
            }
        }
        for (Command command: listCommand.commands) {
            ResultCommand resultCommand = resultCommandCreate(resultCommands, command, null);
            if(resultCommand.value() == null && command.required) {

                if(command.value instanceof ArrayListCommand) {

                    ArrayList<ResultListCommand> commands = new ArrayList<ResultListCommand>();

                    resultCommand.value(commands);

                    String line;
                    do {
                        Scanner in = new Scanner(System.in);

                        printResultListCommand(console,resultCommands, printObjects, text);

                        printMenuMultipleItem(console, command);

                        line = in.nextLine();

                        if(line.equals("1") || line.equalsIgnoreCase("add")) {
                            commands.add(Process.process(args, (ListCommand) command.value, printConsole, printObjects, command.message.isEmpty() ? command.name : command.message));
                        }
                    } while(!line.equals("0") && !line.equalsIgnoreCase("exit"));

                } else if(command.value instanceof ListCommand) {

                    printResultListCommand(console,resultCommands, printObjects, text);

                    ResultListCommand resultListCommand = Process.process(args, (ListCommand) command.value, printConsole, printObjects, command.message.isEmpty() ? command.name : command.message);

                    resultCommand.value(resultListCommand);

                } else {
                    do {

                        Scanner in = new Scanner(System.in);

                        printResultListCommand(console,resultCommands, printObjects, text);

                        printQuestion(console, command);

                        resultCommand.value(in.nextLine());

                        if (resultCommand.value() == null && command.resultCommand.value() != null) {
                            resultCommand.value(command.resultCommand.value());
                        }

                    } while (resultCommand.value() == null);
                }
            }
        }
        return ResultListCommand.create(resultCommands);
    }
}
