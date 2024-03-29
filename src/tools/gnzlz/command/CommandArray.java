package tools.gnzlz.command;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.ArrayListCommand;
import tools.gnzlz.command.command.object.ExposeListCommand;
import tools.gnzlz.command.command.object.ListCommand;
import tools.gnzlz.command.process.print.hidden.PrintCommand;
import tools.gnzlz.command.result.*;
import tools.gnzlz.command.utils.Util;
import tools.gnzlz.system.io.SystemIO;

public class CommandArray extends Command<ArrayListCommand, ResultArrayListCommand, CommandArray> {

    /**
     * CommandArray
     * @param name name
     */
     CommandArray(String name) {
        super(name);
     }

    /**
     * processValue
     * @param value value
     */
    @Override
    public ResultArrayListCommand processValue(Object value) {
        if(value instanceof ResultArrayListCommand) {
            return (ResultArrayListCommand) value;
        }
        return null;
    }

    /**
     * processArgs
     * @param resultListCommand r
     * @param object a
     */
    @Override
    protected void processArgs(ResultListCommand resultListCommand, Object object) {
        ResultCommand<ResultArrayListCommand> resultCommand = this.resultCommand(resultListCommand, () -> this.processValue(object));
        if (resultCommand == null) {
            return;
        }
        ResultArrayListCommand defaultValue = Util.firstNonNull(this.processValue(object), this.processValue(resultCommand.value()));
        ExposeResultCommand.value(resultCommand, defaultValue);
        ExposeResultCommand.assign(resultCommand, true);
    }

    /**
     * processQuestion
     * @param resultListCommand r
     * @param allResultListCommand allResultListCommand
     * @param resultArrayListCommand resultArrayListCommand
     */
    @Override
    protected void processQuestion(ResultListCommand resultListCommand, ResultListCommand allResultListCommand, ResultArrayListCommand resultArrayListCommand) {
        ResultCommand<ResultArrayListCommand> resultCommand = this.resultCommand(resultListCommand, ExposeResultArrayListCommand::create);
        if (resultCommand == null) {
            return;
        }
        ResultArrayListCommand resultListCommandCurrent = Util.firstNonNull(this.processValue(resultCommand.value()), ExposeResultArrayListCommand.create());

        String line;
        do {
            PrintCommand.printResultListCommand(allResultListCommand, "");
            PrintCommand.printMenuMultipleItem(this);

            line = SystemIO.INP.process().toString();

            if(line.equals("1") || line.equalsIgnoreCase("add")) {
                ResultListCommand resultListCommandNew = ExposeResultListCommand.create();
                ExposeResultArrayListCommand.addResultListCommand(resultListCommandCurrent, resultListCommandNew);
                ListCommand listCommand = ExposeCommand.value(this);
                for (Command<?,?,?> command: ExposeListCommand.commands(listCommand)) {
                    ExposeCommand.processQuestion(command, resultListCommandNew, allResultListCommand, resultListCommandCurrent);
                }

            }
        } while(!line.equals("0") && !line.equalsIgnoreCase("exit"));
    }

    /**
     * create
     * @param name name
     */
    public static CommandArray create(String name){
        return new CommandArray(name);
    }

    /**
     * array
     * @param value value
     */
    public CommandArray array(ArrayListCommand value) {
        return this.value(value);
    }

    /**
     * array
     * @param value value
     */
    public CommandArray array(Command<?,?,?> ... value) {
        return this.value(ArrayListCommand.create().addCommand(value));
    }
}
