package tools.gnzlz.command;

import java.util.ArrayList;

public class GroupCommand {

    static GroupCommand current;
    static int position = 0;

    static {
        GroupCommand.current = new GroupCommand("");
        GroupCommand.current.listCommands = Command.listCommands;
    }

    /***************************************
     * vars
     ***************************************/

    ArrayList<Command> listCommands = new ArrayList<Command>();
    ArrayList<Command> listCommandsDefault = new ArrayList<Command>();

    /***************************************
     * vars
     ***************************************/

    private GroupCommand parentGroupCommand;

    /***************************************
     * vars
     ***************************************/

    private boolean runDefault = true;

    /***************************************
     * vars
     ***************************************/
    private String name;

    /***************************************
     * constructor
     ***************************************/
    GroupCommand(String name){
        this.name = name;
    }

    /***************************************
     * static
     ***************************************/

    public static GroupCommand command(String command, String[] args, ForGroupCommand groupCommand){
        GroupCommand gcommand = new GroupCommand(command);
        if(args.length > position && !command.equals("")){
            if(args[position].equals(command)){
                if(GroupCommand.current != null){
                    GroupCommand.current.runDefault = false;
                }
                position++;
                gcommand.parentGroupCommand = GroupCommand.current;
                GroupCommand.current = gcommand;
                groupCommand.run();
                position --;
                GroupCommand.current = gcommand.parentGroupCommand;
            }
        }
        return gcommand;
    }

    /***************************************
     * static
     ***************************************/

    public static GroupCommand command(String[] args, ForGroupCommand groupCommand){
        GroupCommand gcommand = new GroupCommand("");
        if(GroupCommand.current != null && GroupCommand.current.runDefault) {
            gcommand.parentGroupCommand = GroupCommand.current;
            GroupCommand.current = gcommand;
            groupCommand.run();
            GroupCommand.current = gcommand.parentGroupCommand;
        }
        return gcommand;
    }

    /***************************************
     * static
     ***************************************/

    public static Command command(String command){
        return Command.command(command);
    }


    /***************************************
     * static
     ***************************************/

    public static void commands(String ... commands){
        if(GroupCommand.current != null){
            for (Command command : Command.listCommands) {
                for (String commandName: commands) {
                    if(commandName.equals(command.name())){
                        GroupCommand.validateCommands(GroupCommand.current.listCommands, command);
                    }
                }
            }
        }
    }

    /***************************************
     * static
     ***************************************/

    public static void defaults(String ... defaults){
        if(GroupCommand.current != null){
            for (Command command : Command.listCommands) {
                for (String commandName: defaults) {
                    if(commandName.equals(command.name())){
                        GroupCommand.validateCommands(GroupCommand.current.listCommandsDefault, command);
                    }
                }
            }
        }
    }

    /***************************************
     * static
     ***************************************/

    static boolean validateCommands(ArrayList<Command> listCommands, Command command){
        if(GroupCommand.current != null){
            for (Command command1 : listCommands ) {
                if(command.name().equals(command1.name())){
                    return false;
                }
            }
        }
        listCommands.add(command);
        return true;
    }
}
