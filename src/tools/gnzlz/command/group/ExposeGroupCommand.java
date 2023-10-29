package tools.gnzlz.command.group;

import tools.gnzlz.command.command.object.ListCommand;

import java.util.ArrayList;

public class ExposeGroupCommand {

    public static ArrayList<GroupCommand> internals(GroupCommand groupCommand){
        return groupCommand.internals;
    }

    public static String name(GroupCommand groupCommand){
        return groupCommand.name;
    }

    public static ListCommand listCommand(GroupCommand groupCommand){
        return groupCommand.listCommand;
    }

    public static boolean isDefault(GroupCommand groupCommand){
        return groupCommand.isDefault;
    }
}
