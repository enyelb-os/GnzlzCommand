package tools.gnzlz.command.result;

import java.util.ArrayList;

public class ExposeResultArrayListCommand {

    /**
     * create
     */
    public static ResultArrayListCommand create(){
        return new ResultArrayListCommand();
    }

    /**
     * addResultListCommand
     * @param resultListCommand rcl
     * @param resultArrayListCommand ralc
     */
    public static void addResultListCommand(ResultArrayListCommand resultArrayListCommand, ResultListCommand resultListCommand){
        resultArrayListCommand.add(resultListCommand);
    }

    /**
     * resultCommands
     * @param resultArrayListCommand ralc
     */
    public static ArrayList<ResultListCommand> resultCommands(ResultArrayListCommand resultArrayListCommand){
        return resultArrayListCommand.resultListCommands;
    }

}
