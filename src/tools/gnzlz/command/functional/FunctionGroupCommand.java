package tools.gnzlz.command.functional;

import tools.gnzlz.command.result.ResultListCommand;

@FunctionalInterface
public interface FunctionGroupCommand {

    void run(String[] args, ResultListCommand resultListCommand);
}
