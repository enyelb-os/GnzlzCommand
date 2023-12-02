package tools.gnzlz.command.process;

import tools.gnzlz.command.Command;
import tools.gnzlz.command.ExposeCommand;
import tools.gnzlz.command.object.ExposeListCommand;
import tools.gnzlz.command.object.ListCommand;
import tools.gnzlz.command.type.CommandBoolean;
import tools.gnzlz.command.init.ExposeInitListCommand;
import tools.gnzlz.command.init.InitListCommand;
import tools.gnzlz.command.result.ExposeResultCommand;
import tools.gnzlz.command.result.ResultCommand;
import tools.gnzlz.command.result.ResultListCommand;
import tools.gnzlz.system.io.SystemIO;
import tools.gnzlz.system.io.functional.FunctionInputProcess;
import tools.gnzlz.system.io.functional.FunctionOutputProcess;

import java.util.ArrayList;
import java.util.Arrays;

public class Process {

    /**
     * Process args
     * @param args a
     * @param listCommand l
     * @param resultListCommand r
     *
     */
    private static ResultListCommand processArgs(ArrayList<String> args, ListCommand listCommand, ResultListCommand resultListCommand){
        String option = "";
        Object value;
        boolean isAssign;
        if (args != null) {
            for (int i = 0; i < args.size() ; i++) {
                String code = args.get(i);
                isAssign = false;
                if (code.charAt(0) == '-') {
                    option = code;
                    if (args.size() <= i+1 || args.get(i+1).charAt(0) == '-') {
                        isAssign = true;
                    }
                    value = null;
                } else {
                    isAssign = true;
                    value = code;
                }
                for (Command<?,?,?> command: ExposeListCommand.commands(listCommand)) {
                    for (String commandOption: ExposeCommand.commands(command)) {
                        if (option.equals(commandOption) && isAssign) {
                            if (value == null) {
                                if (command instanceof CommandBoolean) {
                                    value = true;
                                } else {
                                    value = "";
                                }
                            } else {
                                args.remove(i);
                                i--;
                            }
                            ResultCommand<?> resultCommand = ExposeCommand.processArgs(command, resultListCommand, value);
                            ExposeResultCommand.assign(resultCommand, true);
                            args.remove(i);
                            i--;
                            break;
                        }
                    }
                }
            }
        }
        return resultListCommand;
    }

    /**
     * Process Question
     * @param listCommand l
     * @param resultListCommand a
     * @param allResultListCommand a
     */
    private static ResultListCommand processQuestion(ListCommand listCommand, ResultListCommand resultListCommand, ResultListCommand allResultListCommand) {

        for (Command<?,?,?> command: ExposeListCommand.commands(listCommand)) {
            ExposeCommand.processQuestion(command, resultListCommand, allResultListCommand);
        }
        return resultListCommand;
    }

    /**
     * args
     * @param args args
     * @param listCommand l
     * @param initListCommand i
     */
    public static ResultListCommand args(ArrayList<String> args, ListCommand listCommand, InitListCommand initListCommand) {
        return Process.processArgs(args, listCommand, ExposeInitListCommand.resultListCommand(initListCommand));
    }

    /**
     * args
     * @param args args
     * @param listCommand l
     */
    public static ResultListCommand args(ArrayList<String> args, ListCommand listCommand) {
        return Process.args(args, listCommand, InitListCommand.create());
    }

    /**
     * args
     * @param args args
     * @param listCommand l
     * @param initListCommand i
     */
    public static ResultListCommand args(String[] args, ListCommand listCommand, InitListCommand initListCommand) {
        return Process.processArgs(new ArrayList<>(Arrays.asList(args)), listCommand, ExposeInitListCommand.resultListCommand(initListCommand));
    }

    /**
     * args
     * @param args args
     * @param listCommand l
     */
    public static ResultListCommand args(String[] args, ListCommand listCommand) {
        return Process.args(args, listCommand, InitListCommand.create());
    }

    /**
     * questions
     * @param listCommand l
     * @param initListCommand i
     */
    public static ResultListCommand questions(ListCommand listCommand, InitListCommand initListCommand) {
        ResultListCommand resultListCommand = ExposeInitListCommand.resultListCommand(initListCommand);
        return Process.processQuestion(listCommand,  resultListCommand, resultListCommand);
    }

    /**
     * questions
     * @param listCommand l
     */
    public static ResultListCommand questions(ListCommand listCommand) {
        return Process.questions(listCommand, InitListCommand.create());
    }

    /**
     * argsAndQuestions
     * @param args args
     * @param listCommand l
     * @param initListCommand i
     */
    public static ResultListCommand argsAndQuestions(String[] args, ListCommand listCommand, InitListCommand initListCommand) {
        ResultListCommand defaultResultListCommand = ExposeInitListCommand.resultListCommand(initListCommand);
        ResultListCommand newResultListCommand = Process.processArgs(new ArrayList<>(Arrays.asList(args)), listCommand, defaultResultListCommand);
        return Process.processQuestion(listCommand, newResultListCommand, newResultListCommand);
    }

    /**
     * argsAndQuestions
     * @param args args
     * @param listCommand listCommand
     */
    public static ResultListCommand argsAndQuestions(String[] args, ListCommand listCommand) {
        return Process.argsAndQuestions(args, listCommand, InitListCommand.create());
    }

    /**
     * argsAndQuestions
     * @param args args
     * @param listCommand l
     * @param initListCommand i
     */
    public static ResultListCommand argsAndQuestions(ArrayList<String> args, ListCommand listCommand, InitListCommand initListCommand) {
        ResultListCommand defaultResultListCommand = ExposeInitListCommand.resultListCommand(initListCommand);
        ResultListCommand newResultListCommand = Process.processArgs(args, listCommand, defaultResultListCommand);
        return Process.processQuestion(listCommand, newResultListCommand, newResultListCommand);
    }

    /**
     * argsAndQuestions
     * @param args args
     * @param listCommand listCommand
     */
    public static ResultListCommand argsAndQuestions(ArrayList<String> args, ListCommand listCommand) {
        return Process.argsAndQuestions(args, listCommand, InitListCommand.create());
    }

    /**
     * console
     * @param console console
     */
    public static void console(FunctionOutputProcess console) {
        SystemIO.OUT = console;
    }

    /**
     * console
     * @param console console
     */
    public static void console(FunctionInputProcess console) {
        SystemIO.INP = console;
    }
}