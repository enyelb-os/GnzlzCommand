package tools.gnzlz.command.init;

import tools.gnzlz.command.result.ResultListCommand;

public class ExposeInitListCommand {

    /**
     * internals
     * @param initListCommand initListCommand
     */
    public static ResultListCommand resultListCommand(InitListCommand initListCommand){
        return initListCommand.resultListCommand;
    }
}
