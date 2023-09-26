package tools.gnzlz.command.result.object;

import tools.gnzlz.command.result.ResultCommand;

import java.util.ArrayList;

public class ExposeResultListCommand {

    public static ArrayList<ResultCommand<?>> resultCommands(ResultListCommand resultListCommand){
        return resultListCommand.resultCommands;
    }

}
