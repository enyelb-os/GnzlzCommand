package tools.gnzlz.command.funtional;

import tools.gnzlz.command.result.object.ResultListCommand;

@FunctionalInterface
public interface FunctionRequiredCommand {

    boolean valid(ResultListCommand resultListCommand);
}
