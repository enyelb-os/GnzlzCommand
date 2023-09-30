package tools.gnzlz.command.functional;

import tools.gnzlz.command.result.ResultCommand;

@FunctionalInterface
public interface FunctionCommand {

    void run(ResultCommand command);
}
