package tools.gnzlz.command.process.print.hidden;

import tools.gnzlz.system.ansi.Color;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.ExposeListCommand;
import tools.gnzlz.command.group.ExposeGroupCommand;
import tools.gnzlz.command.group.GroupCommand;
import tools.gnzlz.system.io.SystemIO;

public class PrintGroupCommand {

    /**
     * printHelp
     * @param groupCommand groupCommand
     */
    public static void printHelp(GroupCommand groupCommand) {
        printHelp(groupCommand, 0, "");
    }

    /**
     * printHelp
     * @param groupCommand gc
     * @param taps t
     * @param commandRequire cr
     */
    private static void printHelp(GroupCommand groupCommand, int taps, String commandRequire) {
        boolean existsDefault = false;
        for (GroupCommand command : ExposeGroupCommand.internals(groupCommand)) {
            if(ExposeGroupCommand.isDefault(command)) {
                existsDefault = true;
                break;
            }
        }

        StringBuilder commandRequireNew = new StringBuilder(commandRequire);
        ExposeListCommand.commands(ExposeGroupCommand.listCommand(groupCommand)).forEach(require -> {
            if (!commandRequireNew.isEmpty()) {
                commandRequireNew.append(", ");
            }
            commandRequireNew.append(ExposeCommand.name(require));
        });

        if(existsDefault) {
            SystemIO.OUT.println(UtilPrint.taps(taps) + "- " + commandRequireNew);
        }

        for (GroupCommand command : ExposeGroupCommand.internals(groupCommand)) {
            if(!ExposeGroupCommand.isDefault(command)) {
                SystemIO.OUT.println(UtilPrint.taps(taps) + ExposeGroupCommand.name(command) + ":");
                printHelp(command, taps + 1, commandRequireNew.toString());
            }
        }

        if(ExposeGroupCommand.internals(groupCommand).isEmpty()){
            SystemIO.OUT.println(UtilPrint.taps(taps) + "- " + commandRequireNew);
        }
    }


    /**
     * printOptions
     * @param current GroupCommand
     */
    public static void printOptions(GroupCommand current){
        StringBuilder str = new StringBuilder();
        for (GroupCommand groupCommand : ExposeGroupCommand.internals(current)){
            if (!str.isEmpty()) {
                str.append(" | ");
            }
            str.append(ExposeGroupCommand.name(groupCommand));
        }
        UtilPrint.clearConsole();
        SystemIO.OUT.print(Color.GREEN.print("Options"));
        SystemIO.OUT.print(" (");
        SystemIO.OUT.print(str.toString());
        SystemIO.OUT.print(")");
        SystemIO.OUT.ln();
        SystemIO.OUT.print(Color.GREEN.print("Choose an option?: "));
    }
}
