package tools.gnzlz.command.funtional;

import tools.gnzlz.command.result.ResultCommand;

@FunctionalInterface
public interface FunctionCommand {

    void run(ResultCommand command);
}
