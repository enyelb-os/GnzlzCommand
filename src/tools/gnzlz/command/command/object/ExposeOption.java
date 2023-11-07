package tools.gnzlz.command.command.object;

import tools.gnzlz.command.command.Command;

public class ExposeOption {

    public static <R> Command<?,R,?> commandReference(Option<R> option){
        return option.commandReference;
    }

    public static Object value(Option<?> option){
        return option.value;
    }

    public static <R> void value(Option<R> option, R value){
        if(option != null) {
            option.value = value;
        }
    }
}
