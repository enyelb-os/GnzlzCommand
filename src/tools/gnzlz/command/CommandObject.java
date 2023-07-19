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
        return command.value;
    }

}
