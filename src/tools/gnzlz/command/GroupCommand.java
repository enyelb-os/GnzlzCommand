package tools.gnzlz.command;

import java.util.ArrayList;

public class GroupCommand {

    /***************************************
     * vars
     ***************************************/

    protected final ArrayList<GroupCommand> internals = new ArrayList<GroupCommand>();

    /***************************************
     * vars
     ***************************************/

    private FunctionGroupCommand functionGroupCommand;

    /***************************************
     * vars
     ***************************************/

    protected ListCommand listCommand;

    /***************************************
     * vars
     ***************************************/

    private boolean runDefault = true;

    /***************************************
     * vars
     ***************************************/

    protected final String name;

    /***************************************
     * vars
     ***************************************/

    private final boolean isDefault;

    /***************************************
     * constructor
     ***************************************/

    protected GroupCommand(String name, boolean hashcode, boolean isDefault, FunctionGroupCommand functionGroupCommand, ListCommand listCommand){
        this.name = name + (hashcode ? this.hashCode() : "");
        this.isDefault = isDefault;
        this.functionGroupCommand = functionGroupCommand;
        this.listCommand = listCommand;
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
     * add Group
     ***************************************/

    public GroupCommand addGroup(GroupCommand ... groupCommands) {
        if(groupCommands != null) {
            for (GroupCommand groupCommand: groupCommands) {
                if(groupCommand != null && !this.existsGroupCommand(groupCommand.name)) {
                    this.internals.add(groupCommand);
                }
            }
        }
        return this;
    }

    /***************************************
     * execute
     ***************************************/

    public GroupCommand execute(FunctionGroupCommand functionGroupCommand) {
        this.functionGroupCommand = functionGroupCommand;
        return this;
    }

    /***************************************
     * add Command
     ***************************************/

    public GroupCommand addCommand(Command ... commands) {
        if(commands != null) {
            for (Command  command: commands) {
                if(command != null) {
                    listCommand.command(command);
                }
            }
        }
        return this;
    }

    /***************************************
     * static add Command
     ***************************************/

    public Command command(String command) {
        return listCommand.command(command);
    }

    /***************************************
     * static
     ***************************************/

    public static GroupCommand create(String command) {
        return new GroupCommand(command, false, false, null, ListCommand.create());
    }

    /***************************************
     * static
     ***************************************/

    public static GroupCommand create() {
        return new GroupCommand("default", true, true, null, ListCommand.create());
    }

    /***************************************
     * static
     ***************************************/

    public static ParentGroupCommand parent(ListCommand listCommand) {
        return ParentGroupCommand.create(listCommand);
    }

    /***************************************
     * static
     ***************************************/

    public static ParentGroupCommand parent() {
        return ParentGroupCommand.create();
    }

    /***************************************
     * static
     ***************************************/

    public GroupCommand use(ListCommand listCommand,String ... commands) {
        if(commands != null){
            for (String command : commands) {
                for (Command commandOld: listCommand.commands) {
                    if(commandOld.name.equals(command) || "all".equals(command)){
                        if(this.listCommand != listCommand){
                            this.listCommand.command(commandOld);
                        }
                    }
                }
            }
        }
        return this;
    }

    /***************************************
     * static
     ***************************************/

    public GroupCommand use(ListCommand listCommand) {
        return use(listCommand,"all");
    }

    /***************************************
     * static
     ***************************************/

    public static void process(String[] args, ListCommand listCommand, GroupCommand ... groupCommands) {
        process(args, ParentGroupCommand.create(listCommand).addGroup(groupCommands));
    }

    public static void process(String[] args, GroupCommand ... groupCommands) {
        process(args, ParentGroupCommand.create().addGroup(groupCommands));
    }

    public static void process(String[] args, ParentGroupCommand parentGroupCommand) {
        GroupCommand.process(
            args, ResultListCommand.create(new ArrayList<ResultCommand>()),
            parentGroupCommand.parent.listCommand, parentGroupCommand.parent, 0
        );
    }

    /***************************************
     * static
     ***************************************/

    private static ResultListCommand process(String[] args, ResultListCommand resultListCommandOld, ListCommand listCommand, GroupCommand current, int index) {
        current.runDefault = true;
        //mergeLists(listCommand.commands, current.listCommand.commands);
        boolean isFoundCommand = false;
        //mergeLists(resultListCommand.resultCommands)


        mergeLists(resultListCommandOld.resultCommands, Process.process(args, listCommand).resultCommands);
        mergeLists(resultListCommandOld.resultCommands, Process.process(args, current.listCommand).resultCommands);


        for (GroupCommand groupCommand : current.internals){
            if((groupCommand.isDefault && current.runDefault) || args != null && args[index].equals(groupCommand.name)) {
                current.runDefault = groupCommand.isDefault;

                mergeLists(resultListCommandOld.resultCommands, Process.process(args, groupCommand.listCommand).resultCommands);

                if (groupCommand.functionGroupCommand != null) {
                    groupCommand.functionGroupCommand.run(resultListCommandOld);
                }

                process(args, resultListCommandOld, groupCommand.listCommand, groupCommand, index+1);
                isFoundCommand = true;
            }
        }
        if(!isFoundCommand && !current.internals.isEmpty()) {
            printHelp(current);
        }
        return resultListCommandOld;
    }

    /***************************************
     * static
     ***************************************/

    private static void printHelp(GroupCommand groupCommand) {
        System.out.println("Help");
        printHelp(groupCommand, 0, "");
    }

    private static void printHelp(GroupCommand groupCommand, int taps, String commandRequire) {
        boolean existsDefault = false;
        for (GroupCommand command : groupCommand.internals) {
            if(command.isDefault) {
                existsDefault = true;
                break;
            }
        }

        String commandRequireNew = commandRequire;
        for (Command require : groupCommand.listCommand.commands) {
            if(!commandRequireNew.isEmpty()) {
                commandRequireNew += ", ";
            }
            commandRequireNew += require.name;
        }

        if(existsDefault) {
            System.out.println(taps(taps) + "- " + commandRequireNew);
        }

        for (GroupCommand command : groupCommand.internals) {
            if(!command.isDefault) {
                System.out.println(taps(taps) + command.name + ":");
                printHelp(command, taps + 1, commandRequireNew);
            }
        }

        if(groupCommand.internals.isEmpty()){
            System.out.println(taps(taps) + "- " + commandRequireNew);
        }
    }

    private static String taps(int taps) {
        String staps = "";
        for (int i = 0; i < taps; i++) {
            staps += "\t";
        }
        return staps;
    }

    /***************************************
     * static
     ***************************************/

    private static boolean existsCommand(ArrayList<ResultCommand> listCommands, String name){
        if(listCommands != null){
            for (ResultCommand command : listCommands) {
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

    private static ArrayList<ResultCommand> mergeLists(ArrayList<ResultCommand> newArrayList, ArrayList<ResultCommand> ... listCommands){
        for (ArrayList<ResultCommand> list : listCommands) {
            for (ResultCommand command : list) {
                if (!existsCommand(newArrayList, command.name())) {
                    newArrayList.add(command);
                }
            }
        }

        return newArrayList;
    }
}
