package tools.gnzlz.command.functional;

import tools.gnzlz.command.result.ResultListCommand;

import java.io.File;

@FunctionalInterface
public interface FunctionValidCommand {

    /**
     * valid
     * @param value value
     * @return r
     */
    boolean valid(Object value, FunctionSetError error, ResultListCommand allresultListCommand, ResultListCommand resultListCommand);

    /**
     * TRUE
     */
    FunctionValidCommand TRUE = (val, e, list, list2) -> true;

    /**
     * FILE
     */
    FunctionValidCommand FILE = (val, e, list, list2) -> {
        if (val instanceof String && new File(val.toString()).isFile()) {
            return true;
        }
        e.set("the value is not a file path");
        return false;
    };

    /**
     * FOLDER
     */
    FunctionValidCommand FOLDER = (val, e, list, list2) -> {
        if (val instanceof String && new File(val.toString()).isDirectory()) {
            return true;
        }
        e.set("the value is not a directory");
        return false;
    };

}
