package tools.gnzlz.command;

public class ArrayListCommand extends ListCommand {

    /***************************************
     * select listCommands
     ***************************************/

    public static ArrayListCommand create(){
        return new ArrayListCommand();
    }

    /***************************************
     * select listCommands
     ***************************************/

    public static ArrayListCommand create(Command ... command){
        return new ArrayListCommand().addCommand(command);
    }

    /***************************************
     * static
     ***************************************/

    public ArrayListCommand addCommand(Command command){
        super.addCommand(command);
        return this;
    }

    /***************************************
     * static
     ***************************************/

    public ArrayListCommand addCommand(Command ... names){
        if(names != null){
            for (Command name: names){
                super.addCommand(name);
            }
        }
        return this;
    }

}
