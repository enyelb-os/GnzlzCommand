package tools.gnzlz.command.init.functional;

import tools.gnzlz.command.init.InitListCommand;

@FunctionalInterface
public interface FunctionInitListCommand<Type> {

    void function(InitListCommand initListCommand, Type object);
}
