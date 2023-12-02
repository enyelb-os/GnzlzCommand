package tools.gnzlz.command.object;

import tools.gnzlz.command.Command;

import java.util.ArrayList;

public class Option<Type> {

    /**
     * command reference
     */
    Command<?,Type,?> commandReference;

    /**
     * Value
     */
    Type value;

    /**
     * list to options
     */
    final ArrayList<Type> options;

    /**
     * Option
     */
     Option(){
        this.options = new ArrayList<Type>();
    }

    /**
     * validate and add list options
     * @param options options
     */
    @SafeVarargs
    private void addOptions(Type ... options){
        if(options != null){
            for (Type optionName : options) {
                for (Type optionNameOld: this.options) {
                    if (optionNameOld.equals(optionName)) {
                        return;
                    }
                }
                this.options.add(optionName);
            }
        }
    }

    /**
     * set Options
     * @param options options
     */
    @SafeVarargs
    public final Option<Type> options(Type... options) {
        this.addOptions(options);
        return this;
    }

    /**
     * set reference command to list
     * @param commandReference commandReference
     */
    public Option<Type> reference(Command<?,Type,?> commandReference) {
        this.commandReference = commandReference;
        return this;
    }

    /**
     * get valid option
     * @param value value
     */
    public boolean valid(Type value) {
        for (Type option : options) {
            if(this.value instanceof String str) {
                if(((String) option).equalsIgnoreCase(value.toString())){
                    return true;
                }
            }
            if(option.equals(value)){
                return true;
            }
        }
        return false;
    }

    /**
     * get value
     */
    public Type value() {
        return this.value;
    }

    /**
     * toString
     */
    @Override
    public String toString(){
        StringBuilder type = new StringBuilder("(");
        for (int i = 0; i < options.size(); i++) {
            type.append(i != 0 ? "|" : "").append(options.get(i));
        }
        type.append(")");
        return type.toString();
    }

    /**
     * string
     * @param options options
     */
    public static Option<String> string(String ... options) {
        return new Option<String>().options(options);
    }

    /**
     * integer
     * @param options options
     */
    public static Option<Integer> integer(Integer ... options) {
        return new Option<Integer>().options(options);
    }

}
