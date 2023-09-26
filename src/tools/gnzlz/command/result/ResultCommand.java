package tools.gnzlz.command.result;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;

public class ResultCommand<Type extends Object> {

    /***************************************
     * vars
     ***************************************/

    boolean assign;

    /***************************************
     * vars
     ***************************************/

    protected Command<?,Type,?> command;

    /***************************************
     * vars
     ***************************************/

    protected Type value;

    /***************************************
     * constructor
     ***************************************/

     public ResultCommand(Command<?,Type,?> command, Type value){
        this.command = command;
        this.value = value;
        this.assign = false;
     }

    /***************************************
     * get
     ***************************************/

    public String name(){
        return ExposeCommand.name(command);
    }

    /***************************************
     * get
     ***************************************/

    public Object value(){
        return value;
    }

    /***************************************
     * get
     ***************************************/

    public Command<?,Type,?> command(){
        return command;
    }

    /***************************************
     * get
     ***************************************/

    public ResultCommand<Type> value(Object value){
        Type newValue = null;
        if(value != null) {
            newValue = command.valueProcess(value);
        }

        if(newValue != null) {
            this.value = newValue;
        }
        return this;
    }

}
