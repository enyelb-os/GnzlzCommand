package tools.gnzlz.command.result;

import java.util.ArrayList;

public class ExposeResultListCommand {

    public static ResultListCommand create(){
        return new ResultListCommand();
    }

    public static void addResultCommand(ResultListCommand resultListCommand, ResultCommand<?> resultCommand){
        resultListCommand.add(resultCommand);
    }

    public static ArrayList<ResultCommand<?>> resultCommands(ResultListCommand resultListCommand){
        return resultListCommand.resultCommands;
    }
}
