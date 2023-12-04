package tools.gnzlz.command.command.functional;

import tools.gnzlz.command.command.data.DataFunctionRequired;

@FunctionalInterface
public interface FunctionRequiredCommand {
    /**
     * valid
     * @param data data
     * @return r
     */
    boolean valid(DataFunctionRequired data);

    /**
     * TRUE
     */
    FunctionRequiredCommand TRUE = (data) -> true;

    /**
     * FALSE
     */
    FunctionRequiredCommand FALSE = (data) -> false;
}
