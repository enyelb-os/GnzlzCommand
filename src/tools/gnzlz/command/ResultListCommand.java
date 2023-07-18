package tools.gnzlz.command;

public class ResultListCommand {

    /***************************************
     * vars
     ***************************************/

    protected ListCommand listCommand;

    /***************************************
     * constructor
     ***************************************/
    ResultListCommand(ListCommand listCommand){
        this.listCommand = listCommand;
    }

    /***************************************
     * select listCommands
     ***************************************/

    static ResultListCommand create(ListCommand listCommand){
        return new ResultListCommand(listCommand);
    }

    /***************************************
     * method
     ***************************************/

    public Object get(String name){
        for (Command command: listCommand.commands) {
            if(command.resultCommand.name().equals(name)){
                return command.resultCommand.value();
            }
        }
        return null;
    }

    /***************************************
     * method
     ***************************************/
    public Value value(String name){
        for (Command command: listCommand.commands) {
            if(command.resultCommand.value() != null && command.resultCommand.value() instanceof Value && command.resultCommand.name().equals(name)){
                return (Value) command.resultCommand.value();
            }
        }
        return null;
    }

    /***************************************
     * method
     ***************************************/

    public String string(String name){
        for (Command command: listCommand.commands) {
            if(command.resultCommand.name().equals(name)){
                return command.resultCommand.value().toString();
            }
        }
        return "";
    }

    /***************************************
     * method
     ***************************************/

    public int integer(String name){
        for (Command command: listCommand.commands) {
            if(command.resultCommand.name().equals(name) && command.resultCommand.value() instanceof Integer){
                return (int) command.resultCommand.value();
            }
        }
        return -1;
    }

    /***************************************
     * method
     ***************************************/

    public void listCommands(FunctionCommand functionCommand){
        listCommand.listCommands(functionCommand);
    }
}
