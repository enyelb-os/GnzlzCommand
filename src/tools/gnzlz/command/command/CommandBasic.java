package tools.gnzlz.command.command;

import tools.gnzlz.command.command.data.DataFunctionRequired;
import tools.gnzlz.command.command.data.DataFunctionValid;
import tools.gnzlz.command.process.print.hidden.PrintCommand;
import tools.gnzlz.command.result.ExposeResultCommand;
import tools.gnzlz.command.result.ResultArrayListCommand;
import tools.gnzlz.command.result.ResultCommand;
import tools.gnzlz.command.result.ResultListCommand;
import tools.gnzlz.command.utils.Util;
import tools.gnzlz.system.io.SystemIO;

public abstract class CommandBasic<Type, C extends Command<?,?,?>> extends Command<Type, Type, C> {

    /**
     * CommandBasic
     * @param name name
     */
    protected CommandBasic(String name) {
        super(name);
    }

    /**
     * type
     */
    protected abstract String type();

    /**
     * processArgs
     * @param resultListCommand r
     * @param object a
     */
    @Override
    protected void processArgs(ResultListCommand resultListCommand, Object object) {
        ResultCommand<Type> resultCommand = this.resultCommand(resultListCommand, () -> processValue(object));
        Type defaultValue = Util.firstNonNull(this.processValue(object),this.processValue(resultCommand.value()), this.value);
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
        final ResultCommand<Type> resultCommand = this.resultCommand(resultListCommand, () -> this.value);
        if (!ExposeResultCommand.assign(resultCommand)) {
            if (resultCommand.value() == null ) {
                ExposeResultCommand.value(resultCommand, this.value);
            }
            var data = new DataFunctionRequired(allResultListCommand, resultListCommand);
            if (ExposeCommand.required(this).valid(data)) {
                boolean repeat;
                do {
                    PrintCommand.printResultListCommand(allResultListCommand, "");
                    PrintCommand.printQuestion(ExposeCommand.message(this), this.type(), resultCommand.value(), ExposeCommand.error(this));
                    Object process = SystemIO.INP.process();
                    boolean valid = ExposeCommand.valid(this).valid(new DataFunctionValid(process, ExposeCommand.functionError(this), allResultListCommand, resultListCommand, resultArrayListCommand));
                    if (valid) {
                        ExposeResultCommand.value(resultCommand, this.processValue(process));
                    }
                    repeat = (process != null && !process.toString().isEmpty()) && !valid;
                } while (resultCommand.value() == null && ExposeCommand.required(this).valid(data) || repeat);
            }
        }
    }
}
