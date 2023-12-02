package tools.gnzlz.command;

import tools.gnzlz.command.object.ExposeOption;
import tools.gnzlz.command.object.Option;
import tools.gnzlz.command.process.print.hidden.PrintCommand;
import tools.gnzlz.command.result.ExposeResultCommand;
import tools.gnzlz.command.result.ResultCommand;
import tools.gnzlz.command.result.ResultListCommand;
import tools.gnzlz.command.utils.Util;
import tools.gnzlz.system.io.SystemIO;

public abstract class CommandOption<Type, C> extends Command<Option<Type>, Type, CommandOption<Type, C>> {


    /**
     * CommandOption
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
     * set option
     * @param value value
     */
    public C valueOption(Type value){
        ExposeOption.value(this.value, value);
        return (C) this;
    }

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
    protected ResultCommand<Type> processArgs(ResultListCommand resultListCommand, Object object) {
        ResultCommand<Type> resultCommand = this.resultCommand(resultListCommand, () -> processValue(object));
        Type defaultValue = Util.firstNonNull(this.processValue(object),this.processValue(resultCommand.value()), this.value.value());
        ExposeResultCommand.value(resultCommand, defaultValue);
        return resultCommand;
    }

    /**
     * process
     * @param resultListCommand r
     * @param allResultListCommand allResultListCommand
     */
    @Override
    protected ResultCommand<Type> processQuestion(ResultListCommand resultListCommand, ResultListCommand allResultListCommand) {
        final ResultCommand<Type> resultCommand = this.resultCommand(resultListCommand, this.value::value);
        if (!ExposeResultCommand.assign(resultCommand)) {
            this.addOptionsCommandReference(allResultListCommand);
            if (resultCommand.value() == null ) {
                ExposeResultCommand.value(resultCommand, this.value.value());
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
