package tools.gnzlz.command.result;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;

public class ResultCommand<Type> {

    /**
     * vars
     */

    protected boolean assign;

    /**
     * vars
     */

    protected Command<?,Type,?> command;

    /**
     * vars
     */

    protected Type value;

    /**
     * constructor
     * @param command command
     * @param value value
     */

     protected ResultCommand(Command<?,Type,?> command, Type value){
        this.command = command;
        this.value = value;
        this.assign = false;
     }

    /**
     * name
     */

    public String name(){
        return ExposeCommand.name(command);
    }

    /**
     * value
     */

    public Object value(){
        return value;
    }

    /**
     * command
     */

    public Command<?,Type,?> command(){
        return command;
    }

    /**
     * value
     * @param value value
     */

    protected ResultCommand<Type> value(Object value){
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
