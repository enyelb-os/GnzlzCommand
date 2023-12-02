package tools.gnzlz.command.command;

import tools.gnzlz.command.process.print.PrintCommand;
import tools.gnzlz.command.result.ExposeResultCommand;
import tools.gnzlz.command.result.ResultCommand;
import tools.gnzlz.command.result.ResultListCommand;
import tools.gnzlz.command.utils.Util;
import tools.gnzlz.system.io.SystemIO;
import tools.gnzlz.system.io.functional.FunctionInputProcess;

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
            if (ExposeCommand.required(this).valid(resultListCommand)) {
                do {
                    PrintCommand.printResultListCommand(allResultListCommand, "");
                    PrintCommand.printQuestion(ExposeCommand.message(this), this.type(), resultCommand.value() != null ? resultCommand.value().toString() : "");
                    ExposeResultCommand.value(resultCommand, this.processValue(SystemIO.INP.process()));
                } while (resultCommand.value() == null && ExposeCommand.required(this).valid(resultListCommand));
            }
        }
        return resultCommand;
    }
}
