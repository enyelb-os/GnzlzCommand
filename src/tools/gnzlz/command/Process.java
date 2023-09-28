package tools.gnzlz.command;

import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.*;
import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.funtional.PrintConsole;
import tools.gnzlz.command.result.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Process {

    /***************************************
     * Default methods
     ***************************************/

    private static final PrintConsole printConsole = System.out::print;

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

    private static String isDefault(Command<?,Object,?> command, ResultCommand<Object> resultCommand){
        String isDefault = isDefault(resultCommand.value());
        if(!isDefault.isEmpty()){
            return isDefault;
        }else if (ExposeCommand.value(command) instanceof Option) {
            return isDefault(ExposeOption.value((Option<?>) ExposeCommand.value(command)));
        } else {
            return isDefault(ExposeCommand.value(command));
        }
    }

    /***************************************
     * Default methods
     ***************************************/

    private static ResultCommand<?> resultCommandCreate(ArrayList<ResultCommand<?>> resultCommands, Command<?, ?, ?> command, Object value) {
        ResultCommand<?> resultCommand = resultCommand(resultCommands, command);
        if(resultCommand == null){
            resultCommand = ExposeCommand.createResultCommand((Command<?, Object, ?>) command, value);
            resultCommands.add(resultCommand);
        }
        ExposeResultCommand.value(resultCommand, value);
        return resultCommand;
    }

    /***************************************
     * Default methods
     ***************************************/

    private static ResultCommand<?> resultCommand(ArrayList<ResultCommand<?>> resultCommands, Command<?,?,?> command) {
        for (ResultCommand<?> resultCommand: resultCommands) {
            if(ExposeResultCommand.command(resultCommand) == command){
                return resultCommand;
            }
        }
        return null;
    }



    /***************************************
     * Default list options command reference
     ***************************************/

    private static void addOptionsCommandReference(Command<?,?,?> command, ResultListCommand resultListCommand){
        if(ExposeCommand.value(command) instanceof Option){
            Option<Object> option = ExposeCommand.value((Command<Option<Object>, ?, ?>) command);
            Command<?,Object,?> commandReference = ExposeOption.commandReference(option);
            if(commandReference != null){
                resultListCommand.listCommands((resultCommand->{
                    if(commandReference == ExposeResultCommand.command(resultCommand)){
                        option.options(ExposeResultCommand.value(resultCommand));
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
            for (Command<?,?,?> command: ExposeListCommand.commands(listCommand)) {
                if (code.charAt(0) == '-') {
                    option = code;
                    value = true;
                } else {
                    value = code;
                }
                for (String commandOption: ExposeCommand.commands(command)) {
                    if (option.equals(commandOption)) {
                        ResultCommand<?> resultCommand = resultCommandCreate(
                            ExposeResultListCommand.resultCommands(resultListCommand),
                            command,
                            code
                        );
                        ExposeResultCommand.assign(resultCommand, true);

                        ExposeResultCommand.value(resultCommand, value);

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
        ArrayList<ResultCommand<?>> resultCommands = ExposeResultListCommand.resultCommands(resultListCommand);

        for (Command<?,?,?> command: ExposeListCommand.commands(listCommand)) {

            ResultCommand<?> resultCommand = resultCommandCreate(resultCommands, command, null);

            if(!ExposeResultCommand.assign(resultCommand) && ExposeCommand.required(command).valid(allResultListCommand)) {

                addOptionsCommandReference(command, allResultListCommand);

                if(ExposeCommand.value(command) instanceof ArrayListCommand) {
                    ResultArrayListCommand resultArrayListCommand;
                    if(resultCommand.value() instanceof ResultArrayListCommand){
                        resultArrayListCommand = (ResultArrayListCommand) resultCommand.value();
                    } else {
                        resultArrayListCommand = ExposeResultArrayCommand.create();
                        ExposeResultCommand.value(resultCommand, resultArrayListCommand);
                    }

                    String line;
                    do {
                        Scanner in = new Scanner(System.in);

                        Print.printResultListCommand(console,resultCommands, printObjects, text);

                        Print.printMenuMultipleItem(console, command);

                        line = in.nextLine();

                        if(line.equals("1") || line.equalsIgnoreCase("add")) {
                            ResultListCommand resultListCommandNew = ExposeResultListCommand.create();
                            ExposeResultArrayCommand.addResultListCommand(
                                resultArrayListCommand,
                                Process.processQuestion(
                                    (ListCommand) ExposeCommand.value(command),
                                    console,
                                    printObjects,
                                    ExposeCommand.message(command).isEmpty() ? ExposeCommand.name(command) : ExposeCommand.message(command),
                                    resultListCommandNew,
                                    allResultListCommand
                                )
                            );
                        }
                    } while(!line.equals("0") && !line.equalsIgnoreCase("exit"));

                } else if(ExposeCommand.value(command) instanceof ListCommand) {

                    Print.printResultListCommand(console, resultCommands, printObjects, text);

                    ResultListCommand resultListCommandTemp;
                    if(resultCommand.value() instanceof ResultListCommand){
                        resultListCommandTemp = (ResultListCommand) resultCommand.value();
                    } else {
                        resultListCommandTemp = ExposeResultListCommand.create();
                    }

                    ExposeResultCommand.value(
                        resultCommand,
                        Process.processQuestion(
                            (ListCommand) ExposeCommand.value(command),
                            console,
                            printObjects,
                            ExposeCommand.message(command).isEmpty() ? ExposeCommand.name(command) : ExposeCommand.message(command),
                            resultListCommandTemp,
                            allResultListCommand
                         )
                    );

                } else {
                    do {

                        Scanner in = new Scanner(System.in);

                        Print.printResultListCommand(console, resultCommands, printObjects, text);

                        Print.printQuestion(console, command, resultCommand);

                       ExposeResultCommand.value(resultCommand, in.nextLine());

                        if (resultCommand.value() == null && ExposeCommand.value(command) != null) {
                            ExposeResultCommand.value(resultCommand, ExposeCommand.value(command));
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
        return Process.args(args, listCommand, ExposeResultListCommand.create());
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
        return Process.questions(listCommand, printConsole, ExposeResultListCommand.create());
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
        return Process.argsAndQuestions(args, listCommand, printConsole, ExposeResultListCommand.create());
    }
}
