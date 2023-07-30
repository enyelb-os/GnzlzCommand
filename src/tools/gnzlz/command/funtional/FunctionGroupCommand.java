package tools.gnzlz.command.funtional;

import tools.gnzlz.command.ResultListCommand;

@FunctionalInterface
public interface FunctionGroupCommand {

    void run(ResultListCommand resultListCommand);
}
