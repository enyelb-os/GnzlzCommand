package tools.gnzlz.command.group;

import tools.gnzlz.command.command.object.ListCommand;

import java.util.ArrayList;

public class ExposeGroupCommand {

    /**
     * internals
     * @param groupCommand groupCommand
     */
    public static ArrayList<GroupCommand> internals(GroupCommand groupCommand){
        return groupCommand.internals;
    }

    /**
     * name
     * @param groupCommand groupCommand
     */
    public static String name(GroupCommand groupCommand){
        return groupCommand.name;
    }

    /**
     * listCommand
     * @param groupCommand groupCommand
     */
    public static ListCommand listCommand(GroupCommand groupCommand){
        return groupCommand.listCommand;
    }

    /**
     * isDefault
     * @param groupCommand groupCommand
     */
    public static boolean isDefault(GroupCommand groupCommand){
        return groupCommand.isDefault;
    }
}
