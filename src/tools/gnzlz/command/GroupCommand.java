package tools.gnzlz.command;

import java.util.ArrayList;

public class GroupCommand {

    /***************************************
     * statics
     ***************************************/

    static ArrayList<Command> baseListCommands = new ArrayList<Command>();

    static GroupCommand current = null;
    static String[] args = null;
    static int index = 0;
    private static ArrayList<GroupCommand> primarys = new ArrayList<GroupCommand>();

    /***************************************
     * vars
     ***************************************/

    ArrayList<Command> listCommands = new ArrayList<Command>();

    /***************************************
     * vars
     ***************************************/



    /***************************************
     * vars
     ***************************************/

    private FunctionGroupCommand functionGroupCommand;

    /***************************************
     * vars
     ***************************************/

    private boolean runDefault = true;

    /***************************************
     * vars
     ***************************************/
    private String name;

    /***************************************
     * vars
     ***************************************/
    private boolean isDefault;

    /***************************************
     * constructor
     ***************************************/

    private GroupCommand(String name){
        this.name = name;
        this.isDefault = false;
    }

    /***************************************
     * constructor
     ***************************************/

    private GroupCommand(){
        this.name = "";
        this.isDefault = true;
    }

    /***************************************
     * static
     ***************************************/

    public static GroupCommand command(String command, FunctionGroupCommand functionGroupCommand){
        GroupCommand groupCommand = new GroupCommand(command);
        if(GroupCommand.current == null) {
            groupCommand.functionGroupCommand = functionGroupCommand;
            GroupCommand.primarys.add(groupCommand);
        } else {
            if(GroupCommand.args != null && GroupCommand.args[GroupCommand.index].equals(command)){
                GroupCommand previous = groupCommand.current;
                GroupCommand.current.runDefault = false;
                groupCommand.listCommands = GroupCommand.current.listCommands;
                groupCommand.current = groupCommand;
                GroupCommand.index++;
                functionGroupCommand.run();
                GroupCommand.current = previous;
                GroupCommand.index--;

            }
        }
        return groupCommand;
    }

    /***************************************
     * static
     ***************************************/

    public static GroupCommand command(FunctionGroupCommand functionGroupCommand){
        GroupCommand groupCommand = new GroupCommand();
        if(GroupCommand.current == null) {
            groupCommand.functionGroupCommand = functionGroupCommand;
            GroupCommand.primarys.add(groupCommand);
        } else {
            if(GroupCommand.args != null && GroupCommand.current.runDefault){
                GroupCommand previous = GroupCommand.current;
                groupCommand.listCommands = GroupCommand.current.listCommands;
                GroupCommand.current = groupCommand;
                GroupCommand.index++;
                functionGroupCommand.run();
                GroupCommand.current = previous;
                GroupCommand.index--;

            }
        }
        return groupCommand;
    }

    /***************************************
     * static
     ***************************************/

    public static Command command(String command){
        if(GroupCommand.current != null){
            return GroupCommand.validateCommands(GroupCommand.current.listCommands, command);
        } else{
            return GroupCommand.validateCommands(GroupCommand.baseListCommands, command);
        }
    }

    /***************************************
     * static
     ***************************************/

    public static void use(String ... commands){
        ArrayList<Command> commandArrayList = GroupCommand.baseListCommands;
        if(GroupCommand.current != null){
            commandArrayList = GroupCommand.current.listCommands;
        }

        for (Command command : Command.baseListCommands) {
            for (String commandName: commands) {
                if(existCommand(commandArrayList, commandName) == null){
                    commandArrayList.add(command);
                }
            }
        }
    }

    /***************************************
     * static
     ***************************************/

    public static void useAllCommands(){
        for (Command command : Command.baseListCommands) {
            if(existCommand(GroupCommand.baseListCommands, command.name()) == null){
                GroupCommand.baseListCommands.add(command);
            }
        }
    }

    /***************************************
     * static
     ***************************************/

    public static void process(String[] args){
        GroupCommand.args = args;
        boolean runDefault = true;
        for (GroupCommand groupCommand : primarys){
            if((groupCommand.isDefault && runDefault) || GroupCommand.args[GroupCommand.index].equals(groupCommand.name)){
                runDefault = false;
                groupCommand.listCommands = GroupCommand.copyLists(GroupCommand.baseListCommands.isEmpty() ? Command.baseListCommands : GroupCommand.baseListCommands);
                Command.listCommands = groupCommand.listCommands;
                GroupCommand.current = groupCommand;
                GroupCommand.index++;
                groupCommand.functionGroupCommand.run();
                Command.listCommands = Command.baseListCommands;
                GroupCommand.current = null;
                GroupCommand.index--;
            }
        }
    }

    /***************************************
     * static
     ***************************************/

    private static Command existCommand(ArrayList<Command> listCommands, String name){
        if(listCommands != null){
            for (Command command : listCommands) {
                if(name.equals(command.name())){
                    return command;
                }
            }
        }
        return null;
    }

    /***************************************
     * static
     ***************************************/

    private static Command validateCommands(ArrayList<Command> listCommands, String name){
        Command command = existCommand(listCommands, name);
        if(command == null){
            command = new Command(name);
            listCommands.add(command);
        }
        return command;
    }

    /***************************************
     * static
     ***************************************/

    private static ArrayList<Command> copyLists(ArrayList<Command> ... listCommands){
        ArrayList<Command> newArrayList = new ArrayList<Command>();
        for (ArrayList<Command> list : listCommands) {
            for (Command command : list) {
                if (existCommand(newArrayList, command.name()) == null) {
                    newArrayList.add(command);
                }
            }
        }
        return newArrayList;
    }
}
