package tools.gnzlz.command.process.print.hidden;

import tools.gnzlz.system.ansi.Color;
import tools.gnzlz.system.io.SystemIO;
import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.result.*;
import tools.gnzlz.system.text.TextIO;

import java.util.ArrayList;

public class PrintCommand {

    /**
     * printMenuMultipleItem
     * @param command command
     */
     public static void printMenuMultipleItem(Command<?,?,?> command){
         SystemIO.OUT.println(Color.BLACK.print(Color.WHITE, TextIO.center(ExposeCommand.message(command))));
         SystemIO.OUT.println(Color.BLACK.print(Color.WHITE,"(1)") + Color.CYAN.print(" Add item"));
         SystemIO.OUT.println(Color.BLACK.print(Color.WHITE,"(0)") + Color.RED.print(" Exit continue"));
         SystemIO.OUT.println(Color.BLACK.print(Color.WHITE, TextIO.repeat(" ")));
         SystemIO.OUT.print(Color.GREEN.print("Choose an option?: "));
    }

    /**
     * printResultListCommand
     * @param resultListCommand rlc
     * @param text text
     */
     public static void printResultListCommand(ResultListCommand resultListCommand, String text){
        ArrayList<ResultCommand<?>> resultCommands = ExposeResultListCommand.resultCommands(resultListCommand);
        SystemIO.OUT.clearConsole();
        SystemIO.OUT.println(Color.BLACK.print(Color.WHITE, TextIO.center(text)));
        if (UtilPrint.isEmptyResultListCommand(resultCommands)) {
            SystemIO.OUT.println("List: Empty");
        } else {
            printResultListCommand(resultCommands, 0);
        }
        SystemIO.OUT.println(Color.BLACK.print(Color.WHITE,TextIO.repeat(" ")));
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
    public static void printQuestion(String messages, String type, Object defaultValue, String[] errors){
        defaultValue = defaultValue == null ? "" : defaultValue;
        for (String error: errors) {
            SystemIO.OUT.println(Color.RED.print(error));
        }
        if (!type.isEmpty()) {
            SystemIO.OUT.print("Type:" + Color.CYAN.print(type) + (!defaultValue.toString().isEmpty() ? " | " : ""));
        }
        if (!defaultValue.toString().isEmpty()) {
            SystemIO.OUT.print("Default: " +  Color.RED.print(defaultValue.toString()));
        }

        if (!type.isEmpty() || !defaultValue.toString().isEmpty()) {
            SystemIO.OUT.ln();
        }
        SystemIO.OUT.print(Color.GREEN.print(messages  +  ": "));
    }
}
