package tools.gnzlz.command;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.ExposeListCommand;
import tools.gnzlz.command.command.object.ListCommand;
import tools.gnzlz.command.result.*;
import tools.gnzlz.command.utils.Util;

public class CommandList extends Command<ListCommand, ResultListCommand, CommandList> {

    /**
     * CommandList
     * @param name name
     */
    public CommandList(String name) {
        super(name);
    }


    /**
     * processValue
     * @param value value
     */
    @Override
    public ResultListCommand processValue(Object value) {
        if(value instanceof ResultListCommand) {
            return (ResultListCommand) value;
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
        ResultCommand<ResultListCommand> resultCommand = this.resultCommand(resultListCommand, () ->  this.processValue(object));
        ResultListCommand defaultValue = Util.firstNonNull(this.processValue(object), this.processValue(resultCommand.value()));
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
        ResultCommand<ResultListCommand> resultCommand = this.resultCommand(resultListCommand, ExposeResultListCommand::create);
        if (resultCommand == null) {
            return;
        }
        ResultListCommand resultListCommandCurrent = Util.firstNonNull(this.processValue(resultCommand.value()), ExposeResultListCommand.create());
        ListCommand listCommand = ExposeCommand.value(this);
        for (Command<?,?,?> command: ExposeListCommand.commands(listCommand)) {
            ExposeCommand.processQuestion(command, resultListCommandCurrent, allResultListCommand, resultArrayListCommand);
        }
    }

    /**
     * create
     * @param name name
     */
    public static CommandList create(String name){
        return new CommandList(name);
    }

    /**
     * list
     * @param value name
     */
    public CommandList list(ListCommand value) {
        return this.value(value);
    }

    /**
     * list
     * @param value name
     */
    public CommandList list(Command<?,?,?> ... value) {
        return this.value(ListCommand.create().addCommand(value));
    }
}
