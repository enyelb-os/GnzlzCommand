package tools.gnzlz.command.process;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.ExposeListCommand;
import tools.gnzlz.command.command.object.ListCommand;
import tools.gnzlz.command.init.ExposeInitListCommand;
import tools.gnzlz.command.init.InitListCommand;
import tools.gnzlz.command.process.functional.FunctionInputProcess;
import tools.gnzlz.command.process.functional.FunctionOutputProcess;
import tools.gnzlz.command.result.ExposeResultCommand;
import tools.gnzlz.command.result.ResultCommand;
import tools.gnzlz.command.result.ResultListCommand;

public class Process {

    /**
     * Process args
     * @param args a
     * @param listCommand l
     * @param resultListCommand r
     *
     */

    private static ResultListCommand processArgs(String[] args, ListCommand listCommand, ResultListCommand resultListCommand){
        String option = "";
        Object value = null;
        if (args != null) {
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
                            ResultCommand<?> resultCommand = ExposeCommand.args(command, resultListCommand, value);
                            ExposeResultCommand.assign(resultCommand, true);
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
            ExposeCommand.process(command, SystemIO.INP, resultListCommand, allResultListCommand);
        }
        return resultListCommand;
    }

    /**
     * args
     * @param args args
     * @param listCommand l
     * @param initListCommand i
     */

    public static ResultListCommand args(String[] args, ListCommand listCommand, InitListCommand initListCommand) {
        return Process.processArgs(args, listCommand, ExposeInitListCommand.resultListCommand(initListCommand));
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
     * @param listCommand l
     * @param initListCommand i
     */

    public static ResultListCommand argsAndQuestions(String[] args, ListCommand listCommand, InitListCommand initListCommand) {
        ResultListCommand defaultResultListCommand = ExposeInitListCommand.resultListCommand(initListCommand);
        ResultListCommand newResultListCommand = Process.processArgs(args, listCommand, defaultResultListCommand);
        return Process.processQuestion(listCommand, newResultListCommand, newResultListCommand);
    }

    /**
     * argsAndQuestions
     * @param listCommand listCommand
     */

    public static ResultListCommand argsAndQuestions(String[] args, ListCommand listCommand) {
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