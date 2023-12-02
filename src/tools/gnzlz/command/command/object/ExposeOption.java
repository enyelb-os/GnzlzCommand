package tools.gnzlz.command.command.object;

import tools.gnzlz.command.command.Command;

public class ExposeOption {

    /**
     * commandReference
     * @param <R> R
     * @param option option
     */
    public static <R> Command<?,R,?> commandReference(Option<R> option){
        return option.commandReference;
    }

    /**
     * value
     * @param option option
     */
    public static Object value(Option<?> option){
        return option.value;
    }

    /**
     * value
     * @param option option
     * @param <R> R
     * @param value value
     */
    public static <R> void value(Option<R> option, R value){
        if(option != null) {
            option.value = value;
        }
    }
}
