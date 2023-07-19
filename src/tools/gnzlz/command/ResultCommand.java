package tools.gnzlz.command;

import java.util.ArrayList;

public class ResultCommand {

    /***************************************
     * vars
     ***************************************/

    protected Command command;

    /***************************************
     * vars
     ***************************************/

    protected Object value;

    /***************************************
     * constructor
     ***************************************/
    ResultCommand(Command command, Object value){
        this.command = command;
        this.value = value;
    }

    /***************************************
     * get
     ***************************************/

    public String name(){
        return command.name;
    }

    /***************************************
     * get
     ***************************************/

    public Object value(){
        return value;
    }

    /***************************************
     * set
     ***************************************/

    private static Object valueIsValid(Option value, String line) {
        Object typeValue = converterString(value.className, value.value, line);
        if(value.valid(typeValue)) {
            return typeValue;
        }
        return null;
    }

    /***************************************
     * set
     ***************************************/
    private static Object converterString(Class className, Object value, String line) {
        try {
            if (className == Integer.class) {
                return Integer.parseInt(line);
            } else if (className == Double.class) {
                return Double.parseDouble(line);
            } else if (className == Boolean.class) {
                return line.equalsIgnoreCase("true") || line.equals("1");
            } else if(className == String.class){
                return line.isEmpty() ? null : line;
            } else if (className == Option.class) {
                return valueIsValid((Option) value, line);
            }
        } catch (NumberFormatException exception) {
            return null;
        }
        return null;
    }

    /***************************************
     * get
     ***************************************/

    ResultCommand value(Object value){
        Object newValue = null;
        if(value instanceof String){
            newValue = converterString(command.className, command.value, (String) value);
        } else if(value != null && command.className == value.getClass()){
            newValue = value;
        } else if(value instanceof ResultListCommand || value instanceof ArrayList){
            newValue = value;
        }

        if(newValue != null) {
            this.value = newValue;
        }
        return this;
    }

}
