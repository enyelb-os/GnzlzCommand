package tools.gnzlz.command.command.functional;

import tools.gnzlz.command.result.ResultListCommand;

@FunctionalInterface
public interface FunctionRequiredCommand {

    boolean valid(ResultListCommand resultListCommand);
}