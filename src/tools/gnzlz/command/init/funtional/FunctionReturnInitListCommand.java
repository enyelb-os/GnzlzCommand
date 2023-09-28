package tools.gnzlz.command.init.funtional;

import tools.gnzlz.command.init.InitListCommand;

@FunctionalInterface
public interface FunctionReturnInitListCommand<Type> {

    InitListCommand function(Type object);
}
