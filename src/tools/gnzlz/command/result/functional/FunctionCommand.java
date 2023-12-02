package tools.gnzlz.command.result.functional;

import tools.gnzlz.command.result.ResultCommand;

@FunctionalInterface
public interface FunctionCommand {

    void run(ResultCommand command);
}
