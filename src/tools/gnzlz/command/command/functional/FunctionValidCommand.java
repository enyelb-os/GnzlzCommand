package tools.gnzlz.command.command.functional;

import tools.gnzlz.command.command.data.DataFunctionValid;

import java.io.File;

@FunctionalInterface
public interface FunctionValidCommand {

    /**
     * valid
     * @param data data
     * @return r
     */
    boolean valid(DataFunctionValid data);

    /**
     * TRUE
     */
    FunctionValidCommand TRUE = (data) -> true;

    /**
     * FILE
     */
    FunctionValidCommand FILE = (data) -> {
        if (data.value instanceof String && new File(data.value.toString()).isFile()) {
            return true;
        }
        data.error.set("the value is not a file path");
        return false;
    };

    /**
     * FOLDER
     */
    FunctionValidCommand FOLDER = (data) -> {
        if (data.value instanceof String && new File(data.value.toString()).isDirectory()) {
            return true;
        }
        data.error.set("the value is not a directory");
        return false;
    };

}
