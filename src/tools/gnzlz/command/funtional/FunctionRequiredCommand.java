package tools.gnzlz.command.funtional;

import tools.gnzlz.command.ResultListCommand;

@FunctionalInterface
public interface FunctionRequiredCommand {

    boolean valid(ResultListCommand resultListCommand);
}
