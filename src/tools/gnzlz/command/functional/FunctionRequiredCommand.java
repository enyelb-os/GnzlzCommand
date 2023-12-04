package tools.gnzlz.command.functional;

import tools.gnzlz.command.result.ResultListCommand;

@FunctionalInterface
public interface FunctionRequiredCommand {
    /**
     * valid
     * @param allResultListCommand arlc
     * @param resultListCommand rlc
     * @return r
     */
    boolean valid(ResultListCommand allResultListCommand, ResultListCommand resultListCommand);

    /**
     * TRUE
     */
    FunctionRequiredCommand TRUE = (allList, list) -> true;

    /**
     * FALSE
     */
    FunctionRequiredCommand FALSE = (allList, list) -> false;
}
