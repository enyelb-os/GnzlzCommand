package tools.gnzlz.command.result;

import tools.gnzlz.system.ansi.Color;
import tools.gnzlz.command.process.print.UtilPrint;
import tools.gnzlz.command.result.interfaces.PrintResult;

import java.util.ArrayList;

public class ResultArrayListCommand implements PrintResult {

    /**
     * vars
     */

    protected ResultCommand<?> parentResultCommand;

    /**
     * vars
     */

    protected ArrayList<ResultListCommand> resultListCommands;

    /**
     * constructor
     */
    protected ResultArrayListCommand(){
        this.resultListCommands = new ArrayList<ResultListCommand>();
    }

    /**
     * add
     * @param resultListCommand resultListCommand
     */

    protected ResultArrayListCommand add(ResultListCommand resultListCommand){
        resultListCommand.parentResultArrayListCommand = this;
        resultListCommands.add(resultListCommand);
        return this;
    }

    /**
     * print
     */

    @Override
    public String print(int index) {
        StringBuilder s = new StringBuilder();
        int item = 1;
        boolean isOneItem = this.resultListCommands.size() == 1;
        boolean isOneItemChildren = UtilPrint.isOneItem(this);
        if (!isOneItem || isOneItemChildren) {
            s.append(Color.YELLOW.print("["));
        }
        for (ResultListCommand resultListCommand : this.resultListCommands) {
            if( item != 1 && isOneItemChildren) {
                s.append(Color.YELLOW.print(", "));
            }
            if (!isOneItem && !isOneItemChildren) {
                s.append(System.lineSeparator()).append(UtilPrint.taps(index)).append(Color.BLACK.print(Color.WHITE, "[" + item + "]")).append(": ");
            }
            s.append(resultListCommand.print(index));
            item++;
        }
        if (!this.resultListCommands.isEmpty() && !isOneItem && !isOneItemChildren) {
            s.append(System.lineSeparator()).append(UtilPrint.taps(index-1));
        }
        if (!isOneItem || isOneItemChildren) {
            s.append(Color.YELLOW.print("]")) ;
        }
        return s.toString();
    }
}
