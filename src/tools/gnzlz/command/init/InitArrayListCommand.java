package tools.gnzlz.command.init;

import tools.gnzlz.command.init.functional.FunctionInitListCommand;
import tools.gnzlz.command.init.functional.FunctionReturnInitListCommand;
import tools.gnzlz.command.result.ExposeResultArrayCommand;
import tools.gnzlz.command.result.ResultArrayListCommand;

import java.util.ArrayList;

public class InitArrayListCommand {

    /**
     * vars
     */

    protected ResultArrayListCommand resultArrayListCommand;

    /**
     * constructor
     */

    protected InitArrayListCommand(){
        this.resultArrayListCommand = ExposeResultArrayCommand.create();
    }

    /**
     * create
     */

    public static InitArrayListCommand create(){
        return new InitArrayListCommand();
    }

    /**
     * add
     * @param initListCommand resultListCommand
     */

    public InitArrayListCommand addListCommand(InitListCommand initListCommand){
        ExposeResultArrayCommand.addResultListCommand(resultArrayListCommand, initListCommand.resultListCommand);
        return this;
    }

    /**
     * add
     * @param list list
     * @param functionInitListCommand f
     * @param <Type> type
     */

    public <Type> InitArrayListCommand addListCommand(ArrayList<Type> list, FunctionInitListCommand<Type> functionInitListCommand){
        if(!list.isEmpty()) {
            list.forEach((e) -> {
                InitListCommand initListCommand = new InitListCommand();
                functionInitListCommand.function(initListCommand, e);
                ExposeResultArrayCommand.addResultListCommand(resultArrayListCommand, initListCommand.resultListCommand);
            });
        }
        return this;
    }

    /**
     * add
     * @param list list
     * @param functionReturnInitListCommand f
     * @param <Type> type
     */

    public <Type> InitArrayListCommand addListCommand(ArrayList<Type> list, FunctionReturnInitListCommand<Type> functionReturnInitListCommand){
        if(!list.isEmpty()) {
            list.forEach((e) -> {
                InitListCommand initListCommand = functionReturnInitListCommand.function(e);
                ExposeResultArrayCommand.addResultListCommand(resultArrayListCommand, initListCommand.resultListCommand);
            });
        }
        return this;
    }


}
