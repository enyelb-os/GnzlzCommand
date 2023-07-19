package tools.gnzlz.command;

import java.util.ArrayList;
import java.util.Scanner;

public class Process {

    private static PrintConsole printConsole = (text -> {
        System.out.println(text);
    });

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
        return Process.process(args, listCommand, printConsole);
    }
    /***************************************
     * Default list Commands
     ***************************************/

    public static ResultListCommand process(String[] args, ListCommand listCommand, PrintConsole console){
        ArrayList<ResultCommand> resultCommands = new ArrayList<ResultCommand>();
        String option = "";
        for (String code: args) {
            for (Command command: listCommand.commands) {
                if (code.substring(0, 1).equals("-")) {
                    option = code;
                    for (String commandOption: command.commands) {
                        if (option.equals(commandOption)) {
                            resultCommandCreate(resultCommands, command, code).value(true);
                            break;
                        }
                    }
                } else {
                    for (String commandOption: command.commands) {
                        if (option.equals(commandOption)) {
                            resultCommandCreate(resultCommands, command, code).value(code);
                            break;
                        }
                    }
                }
            }
        }
        for (Command command: listCommand.commands) {
            ResultCommand resultCommand = resultCommandCreate(resultCommands, command, null);
            if(resultCommand.value() == null && command.required) {
                if(command.value instanceof ArrayListCommand) {

                    ArrayList<ResultListCommand> commands = new ArrayList<ResultListCommand>();
                    boolean exit;
                    do {
                        exit = false;

                        Scanner in = new Scanner(System.in);


                        console.print("******************");
                        console.print("  Menu");
                        console.print("******************");
                        console.print("");
                        console.print(" " + command.message);
                        console.print("");
                        console.print(" 1. Add item");
                        console.print(" 2. Show items");
                        console.print(" 0. Exit continue");
                        console.print("");
                        console.print("Choose an option?: ");

                        String line = in.nextLine();
                        if(line.equals("1") || line.equalsIgnoreCase("add")) {
                            ResultListCommand resultListCommand = process(args, (ListCommand) command.value);
                            commands.add(resultListCommand);
                        } else if(line.equals("2") || line.equalsIgnoreCase("show")) {

                        } else if(line.equals("0") || line.equalsIgnoreCase("exit")) {
                            exit = true;
                        }
                    } while(!exit);

                    resultCommand.value(commands);

                } else if(command.value instanceof ListCommand) {

                    ResultListCommand resultListCommand = process(args, (ListCommand) command.value);
                    resultCommand.value(resultListCommand);

                } else {

                    boolean error;
                    do {
                        error = false;
                        Scanner in = new Scanner(System.in);
                        console.print(command.message + type(command) + isDefault(command) + ": ");
                        resultCommand.value(in.nextLine());
                        if (resultCommand.value() == null && command.value == null) {
                            error = true;
                        } else if (resultCommand.value() == null) {
                            resultCommand.value(command.value);
                        }
                    } while (error);

                }
            }
        }
        return ResultListCommand.create(resultCommands);
    }
}
