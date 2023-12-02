package tools.gnzlz.command.result;

import java.util.ArrayList;

public class ExposeResultListCommand {

    /**
     * create
     */
    public static ResultListCommand create(){
        return new ResultListCommand();
    }

    /**
     * addResultCommand
     * @param resultListCommand resultListCommand
     * @param resultCommand resultCommand
     */
    public static void addResultCommand(ResultListCommand resultListCommand, ResultCommand<?> resultCommand){
        resultListCommand.add(resultCommand);
    }

    /**
     * resultCommands
     * @param resultListCommand resultListCommand
     */
    public static ArrayList<ResultCommand<?>> resultCommands(ResultListCommand resultListCommand){
        return resultListCommand.resultCommands;
    }
}
