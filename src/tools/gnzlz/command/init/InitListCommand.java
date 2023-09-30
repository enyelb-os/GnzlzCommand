package tools.gnzlz.command.init;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.init.functional.FunctionInitListCommand;
import tools.gnzlz.command.init.functional.FunctionReturnInitListCommand;
import tools.gnzlz.command.result.*;

import java.util.ArrayList;

public class InitListCommand {

    /**
     * vars
     */

    protected final ResultListCommand resultListCommand;

    /**
     * constructor
     */

    protected InitListCommand(){
        this.resultListCommand = ExposeResultListCommand.create();
    }

    /**
     * create
     */

    public static InitListCommand create(){
        return new InitListCommand();
    }

    /**
     * addCommand
     * @param initCommand initCommand
     */

    public InitListCommand addCommand(InitCommand<?> initCommand){
        ExposeResultListCommand.addResultCommand(resultListCommand, initCommand.resultCommand);
        return this;
    }

    /**
     * addCommand
     * @param command command
     * @param value value
     */

    public <Type> InitListCommand addCommand(Command<?,Type,?> command, Type value){
        ExposeResultListCommand.addResultCommand(
            resultListCommand,
            ExposeResultCommand.create(command, value)
        );
        return this;
    }

    /**
     * addCommand
     * @param command command
     * @param initListCommand value
     */

    public InitListCommand addCommand(Command<?,ResultListCommand,?> command, InitListCommand initListCommand){
        ExposeResultListCommand.addResultCommand(
            resultListCommand,
            ExposeResultCommand.create(command, initListCommand.resultListCommand)
        );
        return this;
    }

    /**
     * addCommand
     * @param command command
     * @param initArrayListCommand value
     */

    public InitListCommand addCommand(Command<?,ResultArrayListCommand,?> command, InitArrayListCommand initArrayListCommand){
        ExposeResultListCommand.addResultCommand(
            resultListCommand,
            ExposeResultCommand.create(command, initArrayListCommand.resultArrayListCommand)
        );
        return this;
    }

    /**
     * addCommand
     * @param command command
     * @param list list
     * @param functionInitListCommand f
     * @param <Type> type
     */

    public <Type> InitListCommand addCommand(Command<?,ResultArrayListCommand,?> command, ArrayList<Type> list, FunctionInitListCommand<Type> functionInitListCommand){
        if(!list.isEmpty()) {
            ResultArrayListCommand resultArrayListCommand = ExposeResultArrayCommand.create();
            list.forEach((e) -> {
                InitListCommand initListCommand = new InitListCommand();
                functionInitListCommand.function(initListCommand, e);
                ExposeResultArrayCommand.addResultListCommand(resultArrayListCommand, initListCommand.resultListCommand);
            });
            ExposeResultListCommand.addResultCommand(
                resultListCommand,
                ExposeResultCommand.create(command, resultArrayListCommand)
            );
        }
        return this;
    }


    /**
     * addCommand
     * @param command command
     * @param list list
     * @param functionReturnInitListCommand f
     * @param <Type> type
     */

    public <Type> InitListCommand addCommand(Command<?,ResultArrayListCommand,?> command, ArrayList<Type> list, FunctionReturnInitListCommand<Type> functionReturnInitListCommand){
        if(!list.isEmpty()) {
            ResultArrayListCommand resultArrayListCommand = ExposeResultArrayCommand.create();
            list.forEach((e) -> {
                InitListCommand initListCommand = functionReturnInitListCommand.function(e);
                ExposeResultArrayCommand.addResultListCommand(resultArrayListCommand, initListCommand.resultListCommand);
            });
            ExposeResultListCommand.addResultCommand(
                resultListCommand,
                ExposeResultCommand.create(command, resultArrayListCommand)
            );
        }
        return this;
    }
}
