package tools.gnzlz.command;

import java.util.ArrayList;

public class Option<Type> {

    /***************************************
     * vars
     ***************************************/

    Type value;

    /***************************************
     * vars
     ***************************************/

    Class <Type> className;

    /***************************************
     * vars
     ***************************************/

    final ArrayList<Type> options;

    /***************************************
     * constructor
     ***************************************/

     Option(Class<Type> className){
        this.className = className;
        this.options = new ArrayList<Type>();
    }

    /***************************************
     * set
     ***************************************/

    public Option options(Type ... options) {
        this.validateAddOptions(options);
        return this;
    }

    /***************************************
     * set
     ***************************************/

    private Option type(Class<Type> className) {
        this.className = className;
        return this;
    }

    /***************************************
     * get
     ***************************************/

    public boolean valid(Type value) {
        for (Type option : options) {
            if(this.value instanceof String) {
                return ((String) option).equalsIgnoreCase(value.toString());
            }
            return option.equals(value);

        }
        return false;
    }

    /***************************************
     * get
     ***************************************/

    public boolean is(Type value) {
        if(this.value instanceof String){
            return ((String) this.value).equalsIgnoreCase(value.toString());
        }
        return this.value.equals(value);
    }

    /***************************************
     * get
     ***************************************/

    public Type value() {
        return this.value;
    }

    /***************************************
     * set
     ***************************************/

    public static <Type> Option option(Class<Type> className, Type ... options) {
        return new Option<Type>(className).options(options);
    }

    /***************************************
     * set
     ***************************************/

    public static Option option(String ... options) {
        return new Option<String>(String.class).options(options);
    }

    /***************************************
     * private
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

}
