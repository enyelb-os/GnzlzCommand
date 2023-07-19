package tools.gnzlz.command;

import java.util.ArrayList;

public class ResultListCommand {

    /***************************************
     * vars
     ***************************************/

    protected ArrayList<ResultCommand> resultCommands;

    /***************************************
     * constructor
     ***************************************/
    ResultListCommand(ArrayList<ResultCommand> resultCommands){
        this.resultCommands = resultCommands;
    }

    /***************************************
     * select listCommands
     ***************************************/

    static ResultListCommand create(ArrayList<ResultCommand> resultCommands){
        return new ResultListCommand(resultCommands);
    }

    /***************************************
     * method
     ***************************************/

    public Object get(String name){
        for (ResultCommand command: resultCommands) {
            if(command.name().equals(name)){
                return command.value();
            }
        }
        return null;
    }

    /***************************************
     * method
     ***************************************/
    public Option value(String name){
        for (ResultCommand command: resultCommands) {
            if(command.value() != null){
                return (Option) command.command.value;
            }
        }
        return null;
    }

    /***************************************
     * method
     ***************************************/

    public String string(String name){
        for (ResultCommand command: resultCommands) {
            if(command.name().equals(name)){
                return command.value().toString();
            }
        }
        return "";
    }

    /***************************************
     * method
     ***************************************/

    public boolean bool(String name){
        for (ResultCommand command: resultCommands) {
            if(command.name().equals(name)){
                if(command.value() instanceof String)
                return command.value().toString().equals("1") || command.value().toString().equalsIgnoreCase("true");
            } else if(command.value() instanceof Boolean) {
                return (boolean) command.value();
            }
        }
        return false;
    }

    /***************************************
     * method
     ***************************************/

    public int integer(String name){
        for (ResultCommand command: resultCommands) {
            if(command.name().equals(name) && command.value() instanceof Integer){
                return (int) command.value();
            }
        }
        return -1;
    }

    /***************************************
     * method
     ***************************************/

    public ArrayList<ResultListCommand> arrayResultListCommand(String name){
        for (ResultCommand command: resultCommands) {
            if(command.name().equals(name)){
                if(command.value() instanceof ArrayList){
                    return ((ArrayList<ResultListCommand>) command.value());
                }
            }
        }
        return new ArrayList<ResultListCommand>();
    }

    /***************************************
     * method
     ***************************************/

    public ResultListCommand resultListCommand(String name){
        for (ResultCommand command: resultCommands) {
            if(command.name().equals(name)){
                if(command.value() instanceof ArrayList){
                    return ((ResultListCommand) command.value());
                }
            }
        }
        return new ResultListCommand(new ArrayList<ResultCommand>());
    }

    /***************************************
     * method
     ***************************************/

    private void listCommands(ArrayList<ResultCommand> resultCommands, FunctionCommand functionCommand){
        for (ResultCommand command: resultCommands) {
            if(command.value() instanceof ResultListCommand) {
                listCommands(((ResultListCommand) command.value()).resultCommands, functionCommand);
            } else if(command.value() instanceof ArrayList) {
                for (Object o: (ArrayList) command.value()) {
                    if(o instanceof ResultListCommand) {
                        listCommands(((ResultListCommand) o).resultCommands, functionCommand);
                    }
                }
            } else {
                functionCommand.run(command);
            }
        }
    }

    /***************************************
     * method
     ***************************************/

    public void listCommands(FunctionCommand functionCommand){
        listCommands(resultCommands,functionCommand);
    }
}
