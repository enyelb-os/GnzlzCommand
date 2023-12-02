package tools.gnzlz.command.group.functional;

import tools.gnzlz.command.result.ResultListCommand;

import java.util.ArrayList;
import java.util.Arrays;

@FunctionalInterface
public interface FunctionGroupCommand {

    void run(ArrayList<String> arrayList, ResultListCommand resultListCommand);

    default void run(String[] args, ResultListCommand resultListCommand){
        run(new ArrayList<>(Arrays.asList(args)), resultListCommand);
    }
}
