package tools.gnzlz.command.command;

import tools.gnzlz.command.command.object.ExposeOption;
import tools.gnzlz.command.command.object.Option;
import tools.gnzlz.command.process.functional.FunctionInputProcess;
import tools.gnzlz.command.result.ExposeResultCommand;
import tools.gnzlz.command.result.ResultCommand;
import tools.gnzlz.command.result.ResultListCommand;
import tools.gnzlz.command.utils.Util;

public abstract class CommandOption<Type, C> extends Command<Option<Type>, Type, CommandOption<Type, C>> {


    /**
     * constructor
     * @param name name
     */
    protected CommandOption(String name) {
        super(name);
    }

    /**
     * set option
     * @param value value
     */

    public abstract C option(Command<?,Type,?> value);

    /**
     * set option
     * @param value value
     */

    public abstract C option(Type ... value);

    /**
     * type
     */

    protected abstract String type();

    /**
     * addOptionsCommandReference
     * @param resultListCommand value
     */

    private void addOptionsCommandReference(ResultListCommand resultListCommand){
        if(this.value != null){
            Command<?,Type,?> commandReference = ExposeOption.commandReference(this.value);
            if(commandReference != null){
                resultListCommand.listCommands((resultCommand->{
                    if(commandReference == ExposeResultCommand.command(resultCommand)){
                        this.value.options((Type) ExposeResultCommand.value(resultCommand));
                    }
                }));
            }
        }
    }

    /**
     * constructor
     * @param resultListCommand r
     * @param object a
     */

    @Override
    protected ResultCommand<Type> args(ResultListCommand resultListCommand, Object object) {
        ResultCommand<Type> resultCommand = this.resultCommand(resultListCommand, () -> processValue(object));
        Type defaultValue = Util.firstNonNull(this.processValue(object),this.processValue(resultCommand.value()), this.value.value());
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
        final ResultCommand<Type> resultCommand = this.resultCommand(resultListCommand, () -> this.value.value());

        if (!ExposeResultCommand.assign(resultCommand)) {
            this.addOptionsCommandReference(allResultListCommand);
            if (resultCommand.value() == null ) {
                ExposeResultCommand.value(resultCommand, this.value.value());
            }
            do {
                Print.printResultListCommand(allResultListCommand, "");
                Print.printQuestion(this.message, this.type(), resultCommand.value() != null ? resultCommand.value().toString() : "");
                ExposeResultCommand.value(resultCommand, this.processValue(inputProcess.process()));
            } while (resultCommand.value() == null && this.required.valid(resultListCommand));
        }

        return resultCommand;
    }
}
