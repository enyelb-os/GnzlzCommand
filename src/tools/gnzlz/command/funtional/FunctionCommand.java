package tools.gnzlz.command.funtional;

import tools.gnzlz.command.ResultCommand;

@FunctionalInterface
public interface FunctionCommand {

    void run(ResultCommand command);
}
