package tools.gnzlz.command.result;

import tools.gnzlz.system.ansi.Color;
import tools.gnzlz.command.result.functional.FunctionCommand;
import tools.gnzlz.command.process.print.hidden.UtilPrint;
import tools.gnzlz.command.result.interfaces.hidden.PrintResult;

import java.util.ArrayList;

public class ResultListCommand implements PrintResult {

    /**
     * parentResultArrayListCommand
     */
    protected ResultArrayListCommand parentResultArrayListCommand;


    /**
     * parentResultCommand
     */
    protected ResultCommand<?> parentResultCommand;

    /**
     * resultCommands
     */
    protected final ArrayList<ResultCommand<?>> resultCommands;

    /**
     * ResultListCommand
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
       resultCommand.parentResultListCommand = this;
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
                if(command.value() instanceof ResultArrayListCommand ralc){
                    return ExposeResultArrayListCommand.resultCommands(ralc);
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * list
     * @param name name
     */
    public ResultListCommand list(String name){
        for (ResultCommand<?> command: resultCommands) {
            if(command.name().equals(name)){
                if(command.value() instanceof ResultListCommand resultListCommand){
                    return resultListCommand;
                }
            }
        }
        return new ResultListCommand();
    }

    /**
     * listCommands
     * @param resultCommands rc
     * @param functionCommand fc
     */
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
     * listCommands
     * @param functionCommand fc
     */
    public void listCommands(FunctionCommand functionCommand){
        listCommands(resultCommands,functionCommand);
    }

    /**
     * isChildrenEmpty
     * @param resultCommand rc
     */
    private boolean isChildrenEmpty(ResultCommand<?> resultCommand){
        if(resultCommand.value instanceof ResultListCommand rlc) {
            return rlc.resultCommands.isEmpty();
        } else if (resultCommand.value instanceof ResultArrayListCommand ralc){
            return ralc.resultListCommands.isEmpty();
        }
        return false;
    }

    /**
     * print
     * @param index index
     */
    @Override
    public String print(int index) {
        StringBuilder s = new StringBuilder();
        final int[] item = {0, this.resultCommands.size()};
        boolean allBasicItem = UtilPrint.isAllItemBasic(this.resultCommands);
        boolean isOneItem = UtilPrint.isOneItem(this.parentResultArrayListCommand);
        if(!isOneItem) {
            s.append(Color.YELLOW.print("["));
        }
        this.resultCommands.stream().filter(UtilPrint::isBasicItem).forEach(resultCommand -> {
            if (!allBasicItem && !isOneItem && item[0] == 0) {
                s.append(System.lineSeparator()).append(UtilPrint.taps(index + 1));
            } else if( item[0] != 0) {
                s.append(Color.YELLOW.print(", "));
            }
            s.append(resultCommand.print(index + 1));
            item[0]++;
        });

        this.resultCommands.stream().filter(UtilPrint::isNotBasicItem).forEach(resultCommand -> {
            if (item[0] == item[1] - 1 && !isChildrenEmpty(resultCommand)) {
                s.append(System.lineSeparator()).append(UtilPrint.taps(index + 1));
            } else if (item[0] != 0){
                s.append(Color.YELLOW.print(", "));
            }
            s.append(resultCommand.print(index + 1));
            item[0]++;
        });
        if(!isOneItem) {
            if(!allBasicItem) {
                s.append(System.lineSeparator()).append(UtilPrint.taps(index));
            }
            s.append(Color.YELLOW.print("]"));
        }
        return s.toString();
    }
}
