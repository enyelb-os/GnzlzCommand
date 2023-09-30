package tools.gnzlz.command.functional;

import tools.gnzlz.command.init.InitListCommand;

@FunctionalInterface
public interface FunctionGroupCommand {

    void run(InitListCommand resultListCommand);
}
