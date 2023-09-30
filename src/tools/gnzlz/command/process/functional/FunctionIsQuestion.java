package tools.gnzlz.command.process.functional;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.result.ResultListCommand;

@FunctionalInterface
public interface FunctionIsQuestion {

    <R> R process(FunctionRunProcess<R> process, Command<?,R,?> command, ResultListCommand resultListCommand, R defaultValue, FunctionVoid function);
}
