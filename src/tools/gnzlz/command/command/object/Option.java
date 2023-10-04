package tools.gnzlz.command.command.object;

import tools.gnzlz.command.command.Command;

import java.util.ArrayList;

public class Option<Type> {

    /***************************************
     * command reference
     ***************************************/
    Command<?,Type,?> commandReference;

    /***************************************
     * Value
     ***************************************/

    Type value;

    /***************************************
     * list to options
     ***************************************/

    final ArrayList<Type> options;

    /***************************************
     * constructor
     ***************************************/

     Option(){
        this.options = new ArrayList<Type>();
    }

    /***************************************
     * validate and add list options
     ***************************************/

    private void validateAddOptions(Type ... options){
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

    /***************************************
     * set Options
     ***************************************/

    public Option<Type> options(Type ... options) {
        this.validateAddOptions(options);
        return this;
    }

    /***************************************
     * set reference command to list
     ***************************************/

    public Option<Type> reference(Command<?,Type,?> commandReference) {
        this.commandReference = commandReference;
        return this;
    }

    /***************************************
     * get valid option
     ***************************************/

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

    /***************************************
     * get value
     ***************************************/

    public Type value() {
        return this.value;
    }

    /**
     * toString
     *
     */

    @Override
    public String toString(){
        String type = "(";
        for (int i = 0; i < options.size(); i++) {
            type += (i !=0 ? "|": "") + options.get(i);
        }
        type += ")";
        return type;
    }

    /***************************************
     * statics constructors
     ***************************************/

    public static Option<String> string(String ... options) {
        return new Option<String>().options(options);
    }

    /***************************************
     * statics constructors
     ***************************************/

    public static Option<Integer> integer(Integer ... options) {
        return new Option<Integer>().options(options);
    }

}
