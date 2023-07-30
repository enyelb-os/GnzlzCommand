package tools.gnzlz.command;

public class CommandObject {

    /***************************************
     * vars
     ***************************************/

    protected Command command;

    /***************************************
     * constructor
     ***************************************/
    CommandObject(Command command){
        this.command = command;
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
        if(command.value instanceof Option){
            return ((Option) command.value).value();
        }
        return command.value;
    }

    /***************************************
     * get
     ***************************************/

    public String message(){
        return command.message;
    }

}
