package tools.gnzlz.command.init.funtional;

import tools.gnzlz.command.init.InitListCommand;

@FunctionalInterface
public interface FunctionInitListCommand<Type> {

    void function(InitListCommand initListCommand, Type object);
}
