package tools.gnzlz.command.process;

import tools.gnzlz.command.ansi.Color;
import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.process.functional.FunctionOutputProcess;
import tools.gnzlz.command.process.utils.UtilPrint;
import tools.gnzlz.command.result.*;

import java.io.IOException;
import java.util.ArrayList;

public class PrintCommand {

    static FunctionOutputProcess CONSOLE = System.out::print;

    /**
     * Default methods
     */

    public static boolean isPrintable = true;

    /**
     * Default methods
     */

    public static int separator = 70;

    /**
     * Default methods
     */

    public void clearConsole(){
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ignored) {}
    }

    /**
     * Default methods
     */

    public static String taps(int taps) {
        if (taps < 0) return "";
        return "   ".repeat(taps);
    }

    /**
     * Default methods
     */

     public void printMenuMultipleItem(Command<?,?,?> command){
         CONSOLE.println(" 1.- " + Color.CYAN.print("Add item"));
         CONSOLE.println(" 0.- " + Color.CYAN.print("Exit continue"));
         CONSOLE.println("");
         CONSOLE.printTitle(ExposeCommand.message(command), separator);
         CONSOLE.println("");
         CONSOLE.print(Color.GREEN.print("Choose an option?: "));
    }

    /**
     * Default methods
     */

     public void printResultListCommand(ResultListCommand resultListCommand, String text){
        ArrayList<ResultCommand<?>> resultCommands = ExposeResultListCommand.resultCommands(resultListCommand);
        clearConsole();
        CONSOLE.println();
        CONSOLE.printTitle(text, separator);
        CONSOLE.println();
        if (UtilPrint.isEmptyResultListCommand(resultCommands)) {
            CONSOLE.println("List: Empty");
        } else {
            printResultListCommand(resultCommands, 0,false);
        }
        CONSOLE.println();
        CONSOLE.printSeparator(separator);
        CONSOLE.println();
    }

    /**
     * Default methods
     */

    private final static boolean styleReduce = true;

    public void printResultListCommand(ArrayList<ResultCommand<?>> listCommand, int index, boolean isOneItemList){
        final int[] indexItems = {0, 0}; // allItems, basicItems

        boolean isReduce = styleReduce && index != 0;
        boolean isAllItemBasics = UtilPrint.isAllItemBasic(listCommand);
        boolean showName = !isOneItemList;

        listCommand.stream().forEach((resultCommand -> {
            CONSOLE.print(resultCommand.print(index));
            CONSOLE.println();
        }));

        //CONSOLE.print((isReduce || isAllItemBasics) && !isOneItemList,Color.YELLOW.print("["));

        listCommand.stream().filter(UtilPrint::isBasicItem).forEach(resultCommand -> {



            if(resultCommand.value() != null) {

                int newIndex = (isAllItemBasics || isReduce) ? 0 : index;
                newIndex = (!isAllItemBasics && isReduce && indexItems[1] == 0) ? index : newIndex;

                //CONSOLE.println((!isAllItemBasics && isReduce && indexItems[1] == 0) || (!isReduce && !isAllItemBasics));
                //CONSOLE.print((isAllItemBasics || isReduce) && indexItems[1] != 0 , Color.YELLOW.print(", "));
                //CONSOLE.print(showName,taps(newIndex) + Color.PURPLE.print(resultCommand.name() + ": ") );

                if (resultCommand.value() instanceof Number) {
                    //CONSOLE.print(Color.GREEN.print(resultCommand.value()));
                } else {
                    //CONSOLE.print(Color.BLUE.print(resultCommand.value()));
                }
                indexItems[1]++;
            }
            indexItems[0]++;
        });

        //CONSOLE.println(!isAllItemBasics && !isOneItemList);

        listCommand.stream().filter(UtilPrint::isNotBasicItem).forEach(resultCommand -> {

            boolean isOneItemListNew = UtilPrint.isOneItemList(resultCommand);

            if (resultCommand.value() instanceof ResultListCommand) {

                //CONSOLE.println(taps(index) + resultCommand.name() + ": ");
                //printResultListCommand(ExposeResultListCommand.resultCommands((ResultListCommand) resultCommand.value()), index+1, isOneItemListNew);

            } else if (resultCommand.value() instanceof ResultArrayListCommand resultArrayListCommand) {

                ArrayList<ResultListCommand> resultArrayListCommands = ExposeResultArrayListCommand.resultCommands(resultArrayListCommand);
                if (!resultArrayListCommands.isEmpty()) {
                    int item = 1;
                    boolean isMultiLine = UtilPrint.isMultiline(resultArrayListCommand);
                    boolean isOneItem = UtilPrint.isOneItem(resultArrayListCommand);
                    int newIndex = index + (isMultiLine && !isOneItem ? 2 : 1);

                    //CONSOLE.print(taps(index) + Color.PURPLE.print(resultCommand.name() + ": " ));
                    //CONSOLE.println(!isOneItem && !isOneItemListNew);
                    //CONSOLE.print(isOneItemListNew,Color.YELLOW.print("["));
                    for (ResultListCommand resultListCommand: resultArrayListCommands) {
                        //CONSOLE.print(isOneItemListNew && item != 1,Color.YELLOW.print(", "));
                        //
                        //printResultListCommand(ExposeResultListCommand.resultCommands(resultListCommand), newIndex, isOneItemListNew);
                    }
                    //CONSOLE.print(isOneItemListNew,Color.YELLOW.print("]"));
                    //CONSOLE.println(isOneItemListNew && styleReduce);
                    //CONSOLE.print(isOneItemListNew && styleReduce,taps(index-1) + ColorFG.YELLOW + "]" + ColorFG.RESET);
                }
            }
            indexItems[0]++;
        });

        //if((isAllItemBasics && indexItems[0] > 0 && indexItems[0] == listCommand.size()) && !isOneItemList) CONSOLE.print(Color.YELLOW.print("]"));
        //if(((showName && !isAllItemBasics) || (isAllItemBasics && indexItems[0] == listCommand.size())) && !isOneItemList) CONSOLE.println();
    }

    /**
     * Default methods
     */

    public void printQuestion(String messages, String type, String defaultValue){
         if (!type.isEmpty()) {
             CONSOLE.print("Type:" + Color.CYAN.print(type) + (!defaultValue.isEmpty() ? " | " : ""));
         }
         if (!defaultValue.isEmpty()) {
             CONSOLE.print("Default: " +  Color.RED.print(defaultValue));
         }

         if (!type.isEmpty() || !defaultValue.isEmpty()) {
             CONSOLE.println();
         }
         CONSOLE.print(Color.GREEN.print(messages  +  ": "));
    }
}
