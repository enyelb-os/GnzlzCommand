package tools.gnzlz.command.result;

import tools.gnzlz.command.functional.FunctionCommand;

import java.util.ArrayList;

public class ResultListCommand {

    /**
     * vars
     */

    protected final ArrayList<ResultCommand<?>> resultCommands;

    /**
     * constructor
     */

    protected ResultListCommand(){
        this.resultCommands = new ArrayList<ResultCommand<?>>();
    }

    /**
     * add
     * @param resultCommand resultCommand
     */

    protected ResultListCommand add(ResultCommand<?> resultCommand){
       for (ResultCommand<?> command: resultCommands) {
            if(command.command() == resultCommand.command()){
                return this;
            }
       }
       resultCommands.add(resultCommand);
       return this;
    }

    /**
     * get
     * @param name name
     */

    public Object get(String name){
        for (ResultCommand<?> command: resultCommands) {
            if(command.name().equals(name)){
                return command.value();
            }
        }
        return null;
    }

    /**
     * string
     * @param name name
     */

    public String string(String name){
        for (ResultCommand<?> command: resultCommands) {
            if(command.name().equals(name)){
                return command.value().toString();
            }
        }
        return "";
    }

    /**
     * bool
     * @param name name
     */

    public boolean bool(String name){
        for (ResultCommand<?> command: resultCommands) {
            if(command.name().equals(name)){
                if(command.value() instanceof String){
                    return command.value().toString().equals("1") || command.value().toString().equalsIgnoreCase("true");
                } else if(command.value() instanceof Boolean) {
                    return (boolean) command.value();
                }
            }
        }
        return false;
    }

    /**
     * integer
     * @param name name
     */

    public int integer(String name){
        for (ResultCommand<?> command: resultCommands) {
            if(command.name().equals(name) && command.value() instanceof Integer){
                return (int) command.value();
            }
        }
        return -1;
    }

    /**
     * array
     * @param name name
     */

    public ArrayList<ResultListCommand> array(String name){
        for (ResultCommand<?> command: resultCommands) {
            if(command.name().equals(name)){
                if(command.value() instanceof ArrayList){
                    ArrayList<ResultListCommand> value = (ArrayList<ResultListCommand>) command.value();
                    return value;
                }
            }
        }
        return new ArrayList<ResultListCommand>();
    }

    /**
     * list
     * @param name name
     */

    public ResultListCommand list(String name){
        for (ResultCommand<?> command: resultCommands) {
            if(command.name().equals(name)){
                if(command.value() instanceof ArrayList){
                    return ((ResultListCommand) command.value());
                }
            }
        }
        return new ResultListCommand();
    }

    /**
     * method
     **/

    private void listCommands(ArrayList<ResultCommand<?>> resultCommands, FunctionCommand functionCommand){
        for (ResultCommand<?> command: resultCommands) {
            if(command.value() instanceof ResultListCommand) {
                listCommands(((ResultListCommand) command.value()).resultCommands, functionCommand);
            } else if(command.value() instanceof ArrayList) {
                for (Object o: (ArrayList<?>) command.value()) {
                    if(o instanceof ResultListCommand) {
                        listCommands(((ResultListCommand) o).resultCommands, functionCommand);
                    }
                }
            } else {
                functionCommand.run(command);
            }
        }
    }

    /**
     * method
     **/

    public void listCommands(FunctionCommand functionCommand){
        listCommands(resultCommands,functionCommand);
    }
}
