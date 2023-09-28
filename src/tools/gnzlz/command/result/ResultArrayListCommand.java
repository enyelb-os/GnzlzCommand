package tools.gnzlz.command.result;

import java.util.ArrayList;

public class ResultArrayListCommand {

    /**
     * vars
     */

    protected ArrayList<ResultListCommand> resultListCommands;

    /**
     * constructor
     */
    protected ResultArrayListCommand(){
        this.resultListCommands = new ArrayList<ResultListCommand>();
    }

    /**
     * add
     * @param resultListCommand resultListCommand
     */

    protected ResultArrayListCommand add(ResultListCommand resultListCommand){
        resultListCommands.add(resultListCommand);
        return this;
    }
}
