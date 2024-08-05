package tools.gnzlz.command.process.print.hidden;

import tools.gnzlz.command.result.*;

import java.io.IOException;
import java.util.ArrayList;

public class UtilPrint {

    /**
     * taps
     * @param taps taps
     */
    public static String taps(int taps) {
        if (taps < 0) return "";
        return "   ".repeat(taps);
    }

    /**
     * isMultiline
     * @param resultListCommand rlc
     */
    public static boolean isMultiline(ResultListCommand resultListCommand) {
        ArrayList<ResultCommand<?>> resultCommands = ExposeResultListCommand.resultCommands(resultListCommand);
        for (ResultCommand<?> resultCommand: resultCommands) {
            if (resultCommand.value() instanceof ResultListCommand) {
                boolean status = isMultiline((ResultListCommand) resultCommand.value());
                if (status) {
                    return true;
                }
            } else if (resultCommand.value() instanceof ResultArrayListCommand) {
                boolean status = isMultiline((ResultArrayListCommand) resultCommand.value());
                if (status) {
                    return true;
                }
            }
        }
        return resultCommands.size() > 1;
    }

    /**
     * isMultiline
     * @param resultArrayListCommand ralc
     */
    public static boolean isMultiline(ResultArrayListCommand resultArrayListCommand) {
        ArrayList<ResultListCommand> resultListCommands = ExposeResultArrayListCommand.resultCommands(resultArrayListCommand);
        for (ResultListCommand resultListCommand: resultListCommands) {
            boolean status = isMultiline(resultListCommand);
            if (status) {
                return true;
            }
        }
        return false;
    }

    /**
     * isAllItemBasic
     * @param resultCommands rc
     */
    public static boolean isAllItemBasic(ArrayList<ResultCommand<?>>  resultCommands) {
        for (ResultCommand<?> resultCommand: resultCommands) {
            if (resultCommand.value() instanceof ResultListCommand rlc) {
                if(!ExposeResultListCommand.resultCommands(rlc).isEmpty()) {
                    return false;
                }
            } else if(resultCommand.value() instanceof ResultArrayListCommand ralc) {
                if(!ExposeResultArrayListCommand.resultCommands(ralc).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * isAllItemBasic
     * @param resultListCommand rlc
     */
    public static boolean isAllItemBasic(ResultListCommand resultListCommand) {
        if (resultListCommand != null) {
            ArrayList<ResultCommand<?>> resultCommands = ExposeResultListCommand.resultCommands(resultListCommand);
            for (ResultCommand<?> resultCommand : resultCommands) {
                if (resultCommand.value() instanceof ResultListCommand rlc) {
                    if (!ExposeResultListCommand.resultCommands(rlc).isEmpty()) {
                        return false;
                    }
                } else if (resultCommand.value() instanceof ResultArrayListCommand ralc) {
                    if (!ExposeResultArrayListCommand.resultCommands(ralc).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * isOneItem
     * @param resultArrayListCommand ralc
     */
    public static boolean isOneItem(ResultArrayListCommand resultArrayListCommand) {
        if (resultArrayListCommand != null) {
            if(ExposeResultArrayListCommand.resultCommands(resultArrayListCommand).size() == 1) {
                return false;
            }
            for (ResultListCommand resultListCommand: ExposeResultArrayListCommand.resultCommands(resultArrayListCommand)) {
                if(!UtilPrint.isOneItem(resultListCommand)) {
                    return false;
                }
            }
            return !ExposeResultArrayListCommand.resultCommands(resultArrayListCommand).isEmpty();
        }
        return false;
    }

    /**
     * isOneItem
     * @param resultListCommand rlc
     */
    public static boolean isOneItem(ResultListCommand resultListCommand) {
        if (resultListCommand != null) {
            return ExposeResultListCommand.resultCommands(resultListCommand).size() == 1;
        }
        return false;
    }

    /**
     * isBasicItem
     * @param resultCommand rc
     */
    public static boolean isBasicItem(ResultCommand<?> resultCommand) {
        if(resultCommand != null) {
            return !(resultCommand.value() instanceof ResultListCommand) && !(resultCommand.value() instanceof ResultArrayListCommand);
        }
        return false;
    }

    /**
     * isNotBasicItem
     * @param resultCommand rc
     */
    public static boolean isNotBasicItem(ResultCommand<?> resultCommand) {
        return !UtilPrint.isBasicItem(resultCommand);
    }

    /**
     * isEmptyResultListCommand
     * @param listCommand lc
     */
    public static boolean isEmptyResultListCommand(ArrayList<ResultCommand<?>> listCommand){
        for (ResultCommand<?> resultCommand : listCommand) {
            if(resultCommand.value() != null) {
                return false;
            }
        }
        return true;
    }
}
