package tools.gnzlz.command;

public class ArrayListCommand extends ListCommand {

    /***************************************
     * select listCommands
     ***************************************/

    public static ArrayListCommand create(){
        return new ArrayListCommand();
    }

    /***************************************
     * static
     ***************************************/

    public ArrayListCommand addCommand(Command name){
        super.addCommand(name);
        return this;
    }

}
