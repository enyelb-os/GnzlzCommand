package tools.gnzlz.command;

public class InternalCommand {

    /***************************************
     * vars
     ***************************************/

    public final String option;

    /***************************************
     * vars
     ***************************************/

    public final ICommand command;

    /***************************************
     * constructor
     ***************************************/

    InternalCommand(String option, ICommand command){
        this.command = command;
        this.option = option;
    }
}
