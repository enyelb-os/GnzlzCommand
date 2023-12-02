package tools.gnzlz.command.process.print.hidden;

import tools.gnzlz.system.ansi.Color;
import tools.gnzlz.system.io.SystemIO;
import tools.gnzlz.command.Command;
import tools.gnzlz.command.ExposeCommand;
import tools.gnzlz.command.result.*;

import java.util.ArrayList;

public class PrintCommand {

    /**
     * printMenuMultipleItem
     * @param command command
     */
     public static void printMenuMultipleItem(Command<?,?,?> command){
         SystemIO.OUT.println(" 1.- " + Color.CYAN.print("Add item"));
         SystemIO.OUT.println(" 0.- " + Color.CYAN.print("Exit continue"));
         SystemIO.OUT.println("");
         SystemIO.OUT.title(ExposeCommand.message(command), UtilPrint.separator, "=");
         SystemIO.OUT.println("");
         SystemIO.OUT.print(Color.GREEN.print("Choose an option?: "));
    }

    /**
     * printResultListCommand
     * @param resultListCommand rlc
     * @param text text
     */
     public static void printResultListCommand(ResultListCommand resultListCommand, String text){
        ArrayList<ResultCommand<?>> resultCommands = ExposeResultListCommand.resultCommands(resultListCommand);
        UtilPrint.clearConsole();
        SystemIO.OUT.ln();
        SystemIO.OUT.title(text, UtilPrint.separator, "=");
        SystemIO.OUT.ln();
        if (UtilPrint.isEmptyResultListCommand(resultCommands)) {
            SystemIO.OUT.println("List: Empty");
        } else {
            printResultListCommand(resultCommands, 0);
        }
        SystemIO.OUT.ln();
        SystemIO.OUT.repeat(UtilPrint.separator, "=");
        SystemIO.OUT.ln();
    }

    /**
     * printResultListCommand
     * @param listCommand lc
     * @param index index
     */
    public static void printResultListCommand(ArrayList<ResultCommand<?>> listCommand, int index){
        listCommand.forEach((resultCommand -> {
            SystemIO.OUT.print(resultCommand.print(index));
            SystemIO.OUT.ln();
        }));
    }

    /**
     * printQuestion
     * @param messages messages
     * @param type type
     * @param defaultValue defaultValue
     */
    public static void printQuestion(String messages, String type, String defaultValue){
         if (!type.isEmpty()) {
             SystemIO.OUT.print("Type:" + Color.CYAN.print(type) + (!defaultValue.isEmpty() ? " | " : ""));
         }
         if (!defaultValue.isEmpty()) {
             SystemIO.OUT.print("Default: " +  Color.RED.print(defaultValue));
         }

         if (!type.isEmpty() || !defaultValue.isEmpty()) {
             SystemIO.OUT.ln();
         }
         SystemIO.OUT.print(Color.GREEN.print(messages  +  ": "));
    }
}
