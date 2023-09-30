package tools.gnzlz.command.process;

import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.*;
import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.process.functional.*;
import tools.gnzlz.command.init.ExposeInitListCommand;
import tools.gnzlz.command.init.InitListCommand;
import tools.gnzlz.command.result.*;
import tools.gnzlz.command.utils.Util;

import java.util.Scanner;

public class Process {

    /**
     * vars
     */

    private static final FunctionIsQuestion FUNCTION_IS_QUESTION = new FunctionIsQuestion() {
        @Override
        public <R> R process(FunctionRunProcess<R> process, Command<?, R, ?> command, ResultListCommand resultListCommand, R defaultValue, FunctionVoid function) {
            R value = null;
            do {
                function.execute();
                Object object = FUNCTION_INPUT_PROCESS.process();
                value = Util.firstNonNull(process.run(object), defaultValue);
            } while (value == null && !ExposeCommand.required(command).valid(resultListCommand));

            return value;
        }
    };
    /**
     * vars
     */

    private static final FunctionIsQuestion FUNCTION_IS_NOT_QUESTION = new FunctionIsQuestion() {
        @Override
        public <R> R process(FunctionRunProcess<R> process, Command<?,R,?> command, ResultListCommand allResultListCommand, R defaultValue, FunctionVoid function) {
            function.execute();
            return Util.firstNonNull(process.run(FUNCTION_INPUT_PROCESS.process()), defaultValue);
        }
    };

    /**
     * vars
     */

    private static final FunctionInputProcess FUNCTION_INPUT_PROCESS_CONSOLE = new Scanner(System.in)::nextLine;

    /**
     * vars
     */

    public static FunctionInputProcess FUNCTION_INPUT_PROCESS = FUNCTION_INPUT_PROCESS_CONSOLE;

    /**
     * vars
     */

    private static final FunctionOutputProcess FUNCTION_OUTPUT_PROCESS_CONSOLE = System.out::print;

    /**
     * vars
     */

    public static FunctionOutputProcess FUNCTION_OUTPUT_PROCESS_QUESTION = FUNCTION_OUTPUT_PROCESS_CONSOLE;

    /**
     * vars
     */

    public static FunctionOutputProcess FUNCTION_OUTPUT_PROCESS_RESULT = FUNCTION_OUTPUT_PROCESS_CONSOLE;

    /**
     * isDefault
     * @param value value
     */

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

    /**
     * isDefault
     * @param command command
     * @param resultCommand resultCommand
     */

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

    /**
     * addOptionsCommandReference: Default list options command reference
     * @param command command
     * @param resultListCommand resultListCommand
     */

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

            ExposeCommand.process(command, FUNCTION_IS_QUESTION, resultListCommand, allResultListCommand);

            //ResultCommand<?> resultCommand = resultCommandCreate(resultListCommand, defaultResultListCommand, command, null);


            //if(!ExposeResultCommand.assign(resultCommand) && ExposeCommand.required(command).valid(allResultListCommand)) {
                /*Process.addOptionsCommandReference(command, resultListCommand);
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

                        Print.printResultListCommand(resultListCommand, text);

                        Print.printMenuMultipleItem(command);

                        line = in.nextLine();

                        if(line.equals("1") || line.equalsIgnoreCase("add")) {
                            ResultListCommand resultListCommandNew = ExposeResultListCommand.create();
                            ExposeResultArrayCommand.addResultListCommand(
                                resultArrayListCommand,
                                Process.processQuestion(
                                    (ListCommand) ExposeCommand.value(command),
                                    resultCommand,
                                    defaultResultListCommand,
                                    resultListCommandNew,
                                    allResultListCommand
                                )
                            );
                        }
                    } while(!line.equals("0") && !line.equalsIgnoreCase("exit"));*/

                //} else if(ExposeCommand.value(command) instanceof ListCommand) {

                    /*Print.printResultListCommand(resultListCommand, text);

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
                            resultCommand,
                            defaultResultListCommand,
                            ExposeResultListCommand.create(),
                            allResultListCommand
                         )
                    );*/

               // } else {


                    /*do {

                        Scanner in = new Scanner(System.in);

                        Print.printResultListCommand(resultListCommand, text);

                        Print.printQuestion(command, resultCommand);

                        ExposeResultCommand.value(resultCommand, in.nextLine());

                        if (resultCommand.value() == null && ExposeCommand.value(command) != null) {
                            ExposeResultCommand.value(resultCommand, ExposeCommand.value(command));
                        }

                    } while (resultCommand.value() == null);*/
              //  }
            //}
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
}