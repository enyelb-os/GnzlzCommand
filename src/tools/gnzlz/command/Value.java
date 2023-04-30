package tools.gnzlz.command;

import java.util.ArrayList;

public class Value {

    /***************************************
     * vars
     ***************************************/

    String value;

    /***************************************
     * vars
     ***************************************/

    final ArrayList<String> options;

    /***************************************
     * constructor
     ***************************************/

    Value(){
        this.options = new ArrayList<String>();
    }

    /***************************************
     * set
     ***************************************/

    public Value options(String ... options) {
        this.validateAddOptions(options);
        return this;
    }

    /***************************************
     * get
     ***************************************/

    public boolean valid() {
        for (String option : options) {
            if(option.equalsIgnoreCase(value)){
                value = option;
                return true;
            }
        }
        return false;
    }

    /***************************************
     * set
     ***************************************/

    public static Value option(String ... options) {
        return new Value().options(options);
    }

    /***************************************
     * private
     ***************************************/

    private void validateAddOptions(String ... options){
        if(options != null){
            for (String optionName : options) {
                for (String optionNameOld: this.options) {
                    if (optionNameOld.equals(optionName)) {
                        return;
                    }
                }
                this.options.add(optionName);
            }
        }
    }

}
