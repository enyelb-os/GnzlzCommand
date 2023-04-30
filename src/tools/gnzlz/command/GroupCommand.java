package tools.gnzlz.command;

import java.util.ArrayList;

public class GroupCommand {

    /***************************************
     * static
     ***************************************/

    private static GroupCommand current = new GroupCommand("parent", true, 0, null);

    /***************************************
     * static
     ***************************************/

    private static GroupCommand parent = current;

    /***************************************
     * static
     ***************************************/

    private static String[] args = null;

    /***************************************
     * vars
     ***************************************/

    int loopCount = 0;

    /***************************************
     * vars
     ***************************************/

    private final int index;

    /***************************************
     * vars
     ***************************************/

    private final ArrayList<GroupCommand> internals = new ArrayList<GroupCommand>();

    /***************************************
     * vars
     ***************************************/

    private final FunctionGroupCommand functionGroupCommand;

    /***************************************
     * vars
     ***************************************/

    private boolean runDefault = true;

    /***************************************
     * vars
     ***************************************/

    private final String name;

    /***************************************
     * vars
     ***************************************/

    private final boolean isDefault;

    /***************************************
     * constructor
     ***************************************/

    private GroupCommand(String name, boolean hashcode, boolean isDefault, int index, FunctionGroupCommand functionGroupCommand){
        this.name = name + (hashcode ? this.hashCode() : "");
        this.isDefault = isDefault;
        this.index = index;
        this.functionGroupCommand = functionGroupCommand;
    }

    /***************************************
     * constructor
     ***************************************/

    private GroupCommand(String name, boolean hashcode, int index, FunctionGroupCommand functionGroupCommand){
        this(name, hashcode, false, index, functionGroupCommand);
    }

    /***************************************
     * constructor
     ***************************************/

    private GroupCommand(String name, int index, FunctionGroupCommand functionGroupCommand){
        this(name, false, false, index, functionGroupCommand);
    }

    /***************************************
     * get internal GroupCommand
     ***************************************/

    private boolean existsGroupCommand(String name) {
        for (GroupCommand groupCommand : internals) {
            if(groupCommand.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /***************************************
     * static
     ***************************************/

    public static GroupCommand use(String ... commands) {
        if(commands != null){
            for (String command : commands) {
                for (Command commandOld: Command.listCommands) {
                    if(commandOld.name.equals(command) || "all".equals(command)){
                        commandOld.groups(current.name);
                    }
                }
            }
        }
        return current;
    }

    public static GroupCommand use() {
        return use("all");
    }

    /***************************************
     * static
     ***************************************/

    public static GroupCommand command(String command, FunctionGroupCommand functionGroupCommand) {
        if(!current.existsGroupCommand(command)) {
            GroupCommand newGroupCommand = new GroupCommand(command, current.index + 1, functionGroupCommand);
            current.internals.add(newGroupCommand);
        }
        return current;
    }

    /***************************************
     * static
     ***************************************/

    public static GroupCommand command(FunctionGroupCommand functionGroupCommand) {
        if(current.loopCount == 0){
            GroupCommand newGroupCommand = new GroupCommand("default", true, true, current.index + 1, functionGroupCommand);
            current.internals.add(newGroupCommand);
        }
        return current;
    }

    /***************************************
     * static
     ***************************************/

    public static Command command(String command) {
        Command commandOld = Command.command(command);
        if(commandOld.isNew){
            commandOld.groups(current.name);
        } else if(!commandOld.groups.isEmpty()){
            commandOld.groups(current.name);
        }
        return commandOld;
    }

    /***************************************
     * static
     ***************************************/

    public static void process(String[] args) {
        GroupCommand.args = args;
        Process.listCommands.clear();
        GroupCommand.process(args, Process.listCommands);
    }

    /***************************************
     * static
     ***************************************/

    private static void process(String[] args, ArrayList<Command> listCommands) {
        current.runDefault = true;
        mergeLists(listCommands, listCommandsByGroup(current.name));
        for (GroupCommand groupCommand : current.internals){
            if((groupCommand.isDefault && current.runDefault) || args != null && args[groupCommand.index].equals(groupCommand.name)){
                GroupCommand previous = current;
                current.runDefault = groupCommand.isDefault;
                current = groupCommand;
                groupCommand.functionGroupCommand.run();
                process(args, listCommands);
                current = previous;
                groupCommand.loopCount++;
            }
        }
    }

    /***************************************
     * static
     ***************************************/

    private static boolean existsCommand(ArrayList<Command> listCommands, String name){
        if(listCommands != null){
            for (Command command : listCommands) {
                if(name.equals(command.name())){
                    return true;
                }
            }
        }
        return false;
    }

    /***************************************
     * static
     ***************************************/

    private static ArrayList<Command> mergeLists(ArrayList<Command> newArrayList, ArrayList<Command> ... listCommands){
        for (ArrayList<Command> list : listCommands) {
            for (Command command : list) {
                if (!existsCommand(newArrayList, command.name())) {
                    newArrayList.add(command);
                }
            }
        }

        return newArrayList;
    }

    /***************************************
     * static
     ***************************************/

    private static ArrayList<Command> listCommandsByGroup(String name){
        ArrayList<Command> newArrayList = new ArrayList<Command>();
        for (Command command : Command.listCommands) {
            if (!existsCommand(newArrayList, command.name())) {
                for (String group : command.groups) {
                    if(group.equals(name)) {
                        newArrayList.add(command);
                    }
                }
            }
        }
        return newArrayList;
    }
}
