package tools.gnzlz.command.result;

import tools.gnzlz.system.ansi.Color;
import tools.gnzlz.command.Command;
import tools.gnzlz.command.ExposeCommand;
import tools.gnzlz.command.process.print.hidden.UtilPrint;
import tools.gnzlz.command.result.interfaces.hidden.PrintResult;

public class ResultCommand<Type> implements PrintResult {

    /**
     * print
     */
    protected ResultListCommand parentResultListCommand;


    /**
     * assign
     */
    protected boolean assign;

    /**
     * command
     */
    protected Command<?,Type,?> command;

    /**
     * value
     */
    protected Type value;

    /**
     * ResultCommand
     * @param command command
     * @param value value
     */
     protected ResultCommand(Command<?,Type,?> command, Type value){
        this.command = command;
        this.value = value;
        this.assign = false;
     }

    /**
     * name
     */
    public String name(){
        return ExposeCommand.name(command);
    }

    /**
     * value
     */
    public Object value(){
        return value;
    }

    /**
     * command
     */
    public Command<?,Type,?> command(){
        return command;
    }

    /**
     * value
     * @param value value
     */
    protected ResultCommand<Type> value(Type value){
        if(value instanceof ResultListCommand rlc) {
            rlc.parentResultCommand = this;
        } else if(value instanceof ResultArrayListCommand ralc) {
            ralc.parentResultCommand = this;
        }
        if(value != null) {
            this.value = value;
        }
        return this;
    }

    /**
     * isOneItem
     */
    private boolean isOneItem(){
        if (parentResultListCommand != null) {
            return UtilPrint.isOneItem(parentResultListCommand.parentResultArrayListCommand);
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
        //boolean allBasicItem = UtilPrint.isAllItemBasic(this.parentResultListCommand);
        boolean isOneItemParent = this.isOneItem();
        if(!isOneItemParent ) {
            s.append(UtilPrint.taps(0)).append(Color.PURPLE.print(this.name() + ": "));
        }

        if (this.value instanceof ResultListCommand rlc) {
            s.append(rlc.print(index + 1));
        } else if (this.value instanceof ResultArrayListCommand ralc) {
            s.append(ralc.print(index + 1));
        } else {
            if (this.value instanceof Number) {
                s.append(Color.GREEN.print(this.value));
            } else if (this.value instanceof Boolean) {
                s.append(Color.CYAN.print(this.value));
            } else {
                Object value = this.value == null ? "" : this.value;
                s.append(Color.BLUE.print("\"" + value + "\""));
            }
        }
        return s.toString();
    }
}
