package tools.gnzlz.command.command;

import tools.gnzlz.command.process.functional.FunctionInputProcess;
import tools.gnzlz.command.process.print.PrintCommand;
import tools.gnzlz.command.result.ExposeResultCommand;
import tools.gnzlz.command.result.ResultCommand;
import tools.gnzlz.command.result.ResultListCommand;
import tools.gnzlz.command.utils.Util;

public abstract class CommandBasic<Type, C extends Command<?,?,?>> extends Command<Type, Type, C> {

    /**
     * constructor
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
     * args
     * @param resultListCommand r
     * @param object a
     */

    @Override
    protected ResultCommand<Type> args(ResultListCommand resultListCommand, Object object) {
        ResultCommand<Type> resultCommand = this.resultCommand(resultListCommand, () -> processValue(object));
        Type defaultValue = Util.firstNonNull(this.processValue(object),this.processValue(resultCommand.value()), this.value);
        ExposeResultCommand.value(resultCommand, defaultValue);
        return resultCommand;
    }

    /**
     * process
     * @param inputProcess name
     * @param resultListCommand r
     * @param allResultListCommand allResultListCommand
     */

    @Override
    protected ResultCommand<Type> process(FunctionInputProcess inputProcess, ResultListCommand resultListCommand, ResultListCommand allResultListCommand) {
        final ResultCommand<Type> resultCommand = this.resultCommand(resultListCommand, () -> this.value);
        if (!ExposeResultCommand.assign(resultCommand)) {
            if (resultCommand.value() == null ) {
                ExposeResultCommand.value(resultCommand, this.value);
            }
            do {
                PrintCommand.printResultListCommand(allResultListCommand, "");
                PrintCommand.printQuestion(this.message, this.type(), resultCommand.value() != null ? resultCommand.value().toString() : "");
                ExposeResultCommand.value(resultCommand, this.processValue(inputProcess.process()));
            } while (resultCommand.value() == null && this.required.valid(resultListCommand));
        }
        return resultCommand;
    }
}
