package tools.gnzlz.command;

public class ResultCommand {

    /***************************************
     * vars
     ***************************************/

    protected Command command;

    /***************************************
     * constructor
     ***************************************/
    ResultCommand(Command command){
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
