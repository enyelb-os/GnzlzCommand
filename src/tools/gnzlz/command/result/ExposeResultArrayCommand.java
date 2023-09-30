package tools.gnzlz.command.result;

import java.util.ArrayList;

public class ExposeResultArrayCommand {


    public static ResultArrayListCommand create(){
        return new ResultArrayListCommand();
    }

    public static void addResultListCommand(ResultArrayListCommand resultArrayListCommand, ResultListCommand resultListCommand){
        resultArrayListCommand.add(resultListCommand);
    }

    public static ArrayList<ResultListCommand> resultCommands(ResultArrayListCommand resultArrayListCommand){
        return resultArrayListCommand.resultListCommands;
    }

}
