package tools.gnzlz.command.result.object;

import java.util.ArrayList;

public class ResultArrayListCommand {

    /***************************************
     * vars
     ***************************************/

    protected ArrayList<ResultListCommand> resultListCommands;

    /***************************************
     * constructor
     ***************************************/
    ResultArrayListCommand(ArrayList<ResultListCommand> resultListCommands){
        this.resultListCommands = resultListCommands;
    }

    /***************************************
     * select listCommands
     ***************************************/

    public static ResultArrayListCommand create(ArrayList<ResultListCommand> resultListCommands){
        return new ResultArrayListCommand(resultListCommands);
    }

    /***************************************
     * select listCommands
     ***************************************/

    public static ResultArrayListCommand create(){
        return new ResultArrayListCommand(new ArrayList<ResultListCommand>());
    }

    /***************************************
     * add
     ***************************************/

    public ResultArrayListCommand add(ResultListCommand resultListCommand){
        resultListCommands.add(resultListCommand);
        return this;
    }
}
