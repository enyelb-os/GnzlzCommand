package tools.gnzlz.command;

import tools.gnzlz.command.process.print.hidden.PrintCommand;
import tools.gnzlz.command.result.ExposeResultCommand;
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
    protected ResultCommand<Type> processArgs(ResultListCommand resultListCommand, Object object) {
        ResultCommand<Type> resultCommand = this.resultCommand(resultListCommand, () -> processValue(object));
        Type defaultValue = Util.firstNonNull(this.processValue(object),this.processValue(resultCommand.value()), this.value);
        ExposeResultCommand.value(resultCommand, defaultValue);
        return resultCommand;
    }

    /**
     * processQuestion
     * @param resultListCommand r
     * @param allResultListCommand allResultListCommand
     */
    @Override
    protected ResultCommand<Type> processQuestion(ResultListCommand resultListCommand, ResultListCommand allResultListCommand) {
        final ResultCommand<Type> resultCommand = this.resultCommand(resultListCommand, () -> this.value);
        if (!ExposeResultCommand.assign(resultCommand)) {
            if (resultCommand.value() == null ) {
                ExposeResultCommand.value(resultCommand, this.value);
            }
            if (ExposeCommand.required(this).valid(allResultListCommand, resultListCommand)) {
                boolean repeat;
                do {
                    PrintCommand.printResultListCommand(allResultListCommand, "");
                    PrintCommand.printQuestion(ExposeCommand.message(this), this.type(), resultCommand.value(), ExposeCommand.error(this));
                    Object process = SystemIO.INP.process();
                    boolean valid = ExposeCommand.valid(this).valid(process, ExposeCommand.functionError(this), allResultListCommand, resultListCommand);
                    if (valid) {
                        ExposeResultCommand.value(resultCommand, this.processValue(process));
                    }
                    repeat = (process != null && !process.toString().isEmpty()) && !valid;
                } while (resultCommand.value() == null && ExposeCommand.required(this).valid(allResultListCommand, resultListCommand) || repeat);
            }
        }
        return resultCommand;
    }
}
