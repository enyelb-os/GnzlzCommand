package tools.gnzlz.command.process.utils;

import tools.gnzlz.command.result.*;

import java.util.ArrayList;

public class UtilPrint {

    /**
     * Default methods
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
     * Default methods
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
     * Default methods
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
     * Default methods
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
     * Default methods
     */

    public static boolean isOneItemList(ResultCommand<?>  resultCommand) {
        boolean status =  false;
        //for (ResultCommand<?> resultCommand: resultCommands) {
            if (resultCommand.value() instanceof ResultListCommand rlc) {
                status = true;
                if(ExposeResultListCommand.resultCommands(rlc).size() > 1) {
                    return false;
                }
            } else if(resultCommand.value() instanceof ResultArrayListCommand ralc) {
                status = true;
                for (ResultListCommand resultListCommand : ExposeResultArrayListCommand.resultCommands(ralc)){
                    if(ExposeResultListCommand.resultCommands(resultListCommand).size() > 1) {
                        return false;
                    }
                }
            }
        //}
        return status;
    }

    /**
     * Default methods
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
     * Default methods
     */

    public static boolean isOneItem(ResultListCommand resultListCommand) {
        if (resultListCommand != null) {
            return ExposeResultListCommand.resultCommands(resultListCommand).size() == 1;
        }
        return false;
    }

    /**
     * Default methods
     */

    public static boolean isBasicItem(ResultCommand<?> resultCommand) {
        if(resultCommand != null) {
            return !(resultCommand.value() instanceof ResultListCommand) && !(resultCommand.value() instanceof ResultArrayListCommand);
        }
        return false;
    }

    /**
     * Default methods
     */

    public static boolean isNotBasicItem(ResultCommand<?> resultCommand) {
        return !UtilPrint.isBasicItem(resultCommand);
    }

    /**
     * Default methods
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
