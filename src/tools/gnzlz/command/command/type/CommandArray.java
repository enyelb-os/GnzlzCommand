package tools.gnzlz.command.command.type;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.ArrayListCommand;
import tools.gnzlz.command.command.object.ExposeListCommand;
import tools.gnzlz.command.command.object.ListCommand;
import tools.gnzlz.command.process.functional.FunctionIsQuestion;
import tools.gnzlz.command.result.*;
import tools.gnzlz.command.utils.Util;

import java.util.Scanner;

public class CommandArray extends Command<ArrayListCommand, ResultArrayListCommand, CommandArray> {

    /***************************************
     * constructor
     * @param name name
     **************************************
     */

     CommandArray(String name) {
        super(name);
     }

    /**
     * converter
     *
     */

    @Override
    public ResultArrayListCommand processValue(Object value) {
        if(value instanceof ResultArrayListCommand) {
            return (ResultArrayListCommand) value;
        }
        return null;
    }

    /**
     * constructor
     * @param resultListCommand r
     * @param object a
     */

    @Override
    protected ResultCommand<ResultArrayListCommand> args(ResultListCommand resultListCommand, Object object) {
        ResultCommand<ResultArrayListCommand> resultCommand = this.resultCommand(resultListCommand, () -> this.processValue(object));
        if (resultCommand == null) {
            return null;
        }
        ResultArrayListCommand defaultValue = Util.firstNonNull(this.processValue(object), this.processValue(resultCommand.value()));
        ExposeResultCommand.value(resultCommand, defaultValue);
        return resultCommand;
    }

    /**
     * constructor
     * @param isQuestion name
     * @param resultListCommand r
     * @param allResultListCommand allResultListCommand
     */

    @Override
    protected ResultCommand<ResultArrayListCommand> process(FunctionIsQuestion isQuestion, ResultListCommand resultListCommand, ResultListCommand allResultListCommand) {
        ResultCommand<ResultArrayListCommand> resultCommand = this.resultCommand(resultListCommand, ExposeResultArrayCommand::create);
        if (resultCommand == null) {
            return null;
        }
        ResultArrayListCommand resultListCommandCurrent = Util.firstNonNull(this.processValue(resultCommand.value()), ExposeResultArrayCommand.create());

        String line;
        do {


            Print.printResultListCommand(resultListCommand, "");

            Print.printMenuMultipleItem(this);

            Scanner in = new Scanner(System.in);
            line = in.nextLine();

            if(line.equals("1") || line.equalsIgnoreCase("add")) {
                ResultListCommand resultListCommandNew = ExposeResultListCommand.create();
                ListCommand listCommand = this.value;
                for (Command<?,?,?> command: ExposeListCommand.commands(listCommand)) {
                    ExposeCommand.process(command, isQuestion, resultListCommandNew, allResultListCommand);
                }
                ExposeResultArrayCommand.addResultListCommand(resultListCommandCurrent, resultListCommandNew);
            }
        } while(!line.equals("0") && !line.equalsIgnoreCase("exit"));

        return resultCommand;
    }


    /***************************************
     * static create
     * @param name name
     ***************************************/

    public static CommandArray create(String name){
        return new CommandArray(name);
    }

    /***************************************
     * set array
     * @param value value
     ***************************************/

    public CommandArray array(ArrayListCommand value) {
        return this.value(value);
    }

    /***************************************
     * set array
     * @param value value
     ***************************************/

    public CommandArray array(Command<?,?,?> ... value) {
        return this.value(ArrayListCommand.create().addCommand(value));
    }
}
