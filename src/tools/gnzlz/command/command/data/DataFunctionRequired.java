package tools.gnzlz.command.command.data;

import tools.gnzlz.command.result.ResultListCommand;

public class DataFunctionRequired{
    public final ResultListCommand allList;
    public final ResultListCommand list;

    public DataFunctionRequired( ResultListCommand allList, ResultListCommand list) {
        this.allList = allList;
        this.list = list;
    }
}
