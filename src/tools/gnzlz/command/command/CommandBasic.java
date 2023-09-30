package tools.gnzlz.command.command;

import tools.gnzlz.command.process.functional.FunctionIsQuestion;
import tools.gnzlz.command.process.functional.FunctionVoid;
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
     * constructor
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
     * @param isQuestion name
     * @param resultListCommand r
     * @param allResultListCommand allResultListCommand
     */

    @Override
    protected ResultCommand<Type> process(FunctionIsQuestion isQuestion, ResultListCommand resultListCommand, ResultListCommand allResultListCommand) {
        final ResultCommand<Type> resultCommand = this.resultCommand(resultListCommand, () -> this.value);
        if (resultCommand == null || ExposeResultCommand.assign(resultCommand)) {
            return resultCommand;
        }
        FunctionVoid function = () -> {
            Print.printResultListCommand(resultListCommand, "");
            Print.printQuestion(this, resultCommand);
        };
        Type defaultValue = Util.firstNonNull(this.processValue(resultCommand.value()), this.value);
        ExposeResultCommand.value(resultCommand,isQuestion.process(this::processValue, this, resultListCommand, defaultValue, function));

        return resultCommand;
    }
}
