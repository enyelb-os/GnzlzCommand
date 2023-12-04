package tools.gnzlz.command.command.data;

import tools.gnzlz.command.command.functional.FunctionSetError;
import tools.gnzlz.command.result.ResultListCommand;

public class DataFunctionValid {

    public final Object value;
    public final FunctionSetError error;
    public final ResultListCommand allList;
    public final ResultListCommand list;

    public DataFunctionValid(Object value, FunctionSetError error, ResultListCommand allList, ResultListCommand list) {
        this.value = value;
        this.error = error;
        this.allList = allList;
        this.list = list;
    }
}
