package tools.gnzlz.command;

public class ResultCommand {

    /***************************************
     * vars
     ***************************************/

    boolean assign;

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

    public ResultCommand(Command command, Object value){
        this.command = command;
        this.value = value;
        this.assign = false;
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
     * get
     ***************************************/

    ResultCommand value(Object value){
        Object newValue = null;
        if(value != null) {
            newValue = command.valueProcess(value);
        }

        if(newValue != null) {
            this.value = newValue;
        }
        return this;
    }

}
