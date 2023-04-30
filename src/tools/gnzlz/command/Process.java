package tools.gnzlz.command;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Process {

    /***************************************
     * Default list Commands
     ***************************************/
    static ArrayList<Command> listCommands = new ArrayList<>();
    public static ListCommand listCommand = new ListCommand();

    public static ListCommand process(String[] args){
        return process(args, listCommands.isEmpty() ? Command.listCommands : listCommands);
    }

    private static ListCommand process(String[] args, ArrayList<Command> listCommands){
        listCommand.listCommands = listCommands;
        String option = "";
        for (String code: args) {
            for (Command command: listCommands) {
                if (code.substring(0, 1).equals("-")) {
                    option = code;
                    for (String commandOption: command.commands) {
                        if (option.equals(commandOption) && command.value instanceof Boolean) {
                            command.value = true;
                            command.assign = true;
                            break;
                        }
                    }
                } else {
                    for (String commandOption: command.commands) {
                        if (option.equals(commandOption)) {
                            if (command.value instanceof Value) {
                                ((Value)command.value).value = code;
                                command.assign = ((Value)command.value).valid();
                            } else if (command.value instanceof Integer){
                                command.value = Integer.parseInt(code);
                                command.assign = true;
                            } else {
                                command.value = code;
                                command.assign = true;
                            }

                            break;
                        }
                    }
                }
            }
        }
        for (Command command: listCommands) {
            if(!command.assign && !command.optional){
                String type = "";
                boolean error = false;
                do {
                    Scanner in = new Scanner(System.in);
                    if (command.value instanceof Integer) {
                        type = "(int)";
                    } else if (command.value instanceof Double) {
                        type = "(float)";
                    } else if (command.value instanceof Boolean) {
                        type = "(bool)";
                    } else if (command.value instanceof Value) {
                        Value value = ((Value) command.value);
                        type = "(Options) (";
                        for (int i = 0; i < value.options.size(); i++) {
                            type += (i !=0 ? "|": "") + value.options.get(i);
                        }
                        type += ")";
                    } else {
                        type = "";
                    }
                    System.out.print(command.message + " " + type + ": ");
                    try {
                        if (command.value instanceof Integer) {
                            command.value = in.nextInt();
                        } else if (command.value instanceof Double) {
                            command.value = in.nextDouble();
                        } else if (command.value instanceof Boolean) {
                            command.value = in.nextBoolean();
                        } else if (command.value instanceof Value) {
                            Value value = ((Value) command.value);
                            value.value = in.next();

                            if(!value.valid()){
                                error = true;
                            }
                        }else {
                            command.value = in.next();
                        }
                    } catch (InputMismatchException exception) {
                        error = true;
                    }

                }while (error == true);
            }
        }
        return listCommand;
    }
}
