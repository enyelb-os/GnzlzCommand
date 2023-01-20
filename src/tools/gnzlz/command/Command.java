package tools.gnzlz.command;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Command {

    /***************************************
     * Default list Commands
     ***************************************/

    static ArrayList<Command> baseListCommands = new ArrayList<Command>();
    static ArrayList<Command> listCommands = baseListCommands;
    public static ListCommand listCommand = new ListCommand();



    /***************************************
     * vars
     ***************************************/

    private ArrayList<String> commands;
    private String name;
    private Object value;
    private boolean assign;
    private boolean optional;
    private String message;

    /***************************************
     * constructor
     ***************************************/

    Command(String name, Object value, String message, String ... commands){
        this.commands = new ArrayList<String>();
        this.name = name;
        this.validateAddCommands(commands);
        this.value = value;
        this.assign = false;
        this.optional = message.isEmpty();
        this.message = message;
    }

    /***************************************
     * constructor
     ***************************************/
    Command(String name){
        this(name, null, "", null);
    }

    /***************************************
     * get
     ***************************************/

    public String name() {
        return name;
    }

    /***************************************
     * get
     ***************************************/

    public Object value() {
        return value;
    }

    /***************************************
     * set
     ***************************************/

    public Command value(Object value) {
        this.value = value;
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command required(String message) {
        this.message = message;
        this.optional = message.isEmpty();
        return this;
    }

    /***************************************
     * set
     ***************************************/

    public Command commands(String ... commands) {
        this.validateAddCommands(commands);
        return this;
    }

    /***************************************
     * private
     ***************************************/

    private void validateAddCommands(String ... commands){
        if(commands == null){
            return;
        }
        for (String commandName : commands) {
            boolean exist = false;
            for (String commandNameOld: this.commands) {
                if (commandNameOld.equals(commandName)) {
                    exist = true;
                }
            }
            if(!exist){
                this.commands.add(commandName);
            }
        }

        for (Command command: baseListCommands) {
            if(!command.name.equals(name)) {
                for (String commandName : commands) {
                    for (String commandNameOld: command.commands) {
                        if (commandNameOld.equals(commandName)) {
                            throw new RuntimeException("command duplicate : " + command.name + "." + commandName + " == " + name + "." + commandName);
                        }
                    }
                }
            }
        }
    }

    /***************************************
     * static
     ***************************************/

    public static void command(String name, Object value, String required,  String ... commands){
        boolean newCommand = true;
        for (Command command: baseListCommands) {
            if(command.name.equals(name)){
                newCommand = false;
                command.value = value;
                command.validateAddCommands(commands);
                break;
            }
        }
        if(newCommand){
            baseListCommands.add(new Command(name,value, required,  commands));
        }
    }

    /***************************************
     * static
     ***************************************/

    public static Command command(String name){
        for (Command command: baseListCommands) {
            if(command.name.equals(name)){
                return command;
            }
        }
        Command command = new Command(name);
        baseListCommands.add(command);
        return command;
    }

    /***************************************
     * static
     ***************************************/

    public static ListCommand process(String[] args){
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
                            if(command.value instanceof Integer){
                                command.value = Integer.parseInt(code);
                            } else {
                                command.value = code;
                            }
                            command.assign = true;
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
                        } else {
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
