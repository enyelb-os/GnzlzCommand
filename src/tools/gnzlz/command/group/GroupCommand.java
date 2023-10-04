package tools.gnzlz.command.group;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.ExposeListCommand;
import tools.gnzlz.command.command.object.ListCommand;
import tools.gnzlz.command.functional.FunctionGroupCommand;
import tools.gnzlz.command.init.InitListCommand;
import tools.gnzlz.command.process.Process;
import tools.gnzlz.command.result.ResultListCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GroupCommand {

    /**
     * vars
     */

    protected final ArrayList<GroupCommand> internals = new ArrayList<GroupCommand>();

    /**
     * vars
     */

    private FunctionGroupCommand functionGroupCommand;

    /**
     * vars
     */

    protected ListCommand listCommand;

    /**
     * vars
     */

    private boolean runDefault = true;

    /**
     * vars
     */

    protected final String name;

    /**
     * vars
     */

    private final boolean isDefault;

    /**
     * constructor
     */

    protected GroupCommand(String name, boolean hashcode, boolean isDefault, FunctionGroupCommand functionGroupCommand, ListCommand listCommand){
        this.name = name + (hashcode ? this.hashCode() : "");
        this.isDefault = isDefault;
        this.functionGroupCommand = functionGroupCommand;
        this.listCommand = listCommand;
    }

    /**
     * get internal GroupCommand
     */

    private boolean existsGroupCommand(String name) {
        for (GroupCommand groupCommand : internals) {
            if(groupCommand.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * add Group
     */

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

    /**
     * execute
     */

    public GroupCommand execute(FunctionGroupCommand functionGroupCommand) {
        this.functionGroupCommand = functionGroupCommand;
        return this;
    }

    /**
     * add Command
     */

    public GroupCommand addCommand(Command<?,?,?>... commands) {
        if(commands != null) {
            Arrays.stream(commands).filter(Objects::nonNull).forEach(command -> listCommand.command(command));
        }
        return this;
    }

    /**
     * static
     */

    public static GroupCommand create(String command) {
        return new GroupCommand(command, false, false, null, ListCommand.create());
    }

    /**
     * static
     */

    public static GroupCommand create() {
        return new GroupCommand("default", true, true, null, ListCommand.create());
    }

    /**
     * static
     */

    public static ParentGroupCommand parent(ListCommand listCommand) {
        return ParentGroupCommand.create(listCommand);
    }

    /**
     * static
     */

    public static ParentGroupCommand parent() {
        return ParentGroupCommand.create();
    }

    /**
     * static
     */

    public GroupCommand use(ListCommand listCommand,String ... commands) {
        if(commands != null){
            for (String command : commands) {
                ExposeListCommand.commands(listCommand).stream().filter(commandOld -> ExposeCommand.name(commandOld).equals(command) || "all".equals(command)).filter(commandOld -> this.listCommand != listCommand).forEach(commandOld -> this.listCommand.command(commandOld));
            }
        }
        return this;
    }

    /**
     * static
     */

    public GroupCommand use(ListCommand listCommand) {
        return use(listCommand,"all");
    }

    /**
     * static
     */

    public static ResultListCommand process(String[] args, ListCommand listCommand, GroupCommand ... groupCommands) {
        return process(args, ParentGroupCommand.create(listCommand).addGroup(groupCommands));
    }

    public static ResultListCommand process(String[] args, GroupCommand ... groupCommands) {
        return process(args, ParentGroupCommand.create().addGroup(groupCommands));
    }

    public static ResultListCommand process(String[] args, ParentGroupCommand parentGroupCommand) {
        return GroupCommand.process(
            args, InitListCommand.create(),
            parentGroupCommand.parent.listCommand, parentGroupCommand.parent, 0
        );
    }

    /**
     * static
     */

    private static ResultListCommand process(String[] args, InitListCommand resultListCommandOld, ListCommand listCommand, GroupCommand current, int index) {
        current.runDefault = true;
        boolean isFoundCommand = false;

        ResultListCommand resultListCommand = Process.argsAndQuestions(args, listCommand, resultListCommandOld);

        for (GroupCommand groupCommand : current.internals){
            if((groupCommand.isDefault && current.runDefault) || args != null && args.length > index && args[index].equals(groupCommand.name)) {
                current.runDefault = groupCommand.isDefault;

                if (groupCommand.functionGroupCommand != null) {
                    groupCommand.functionGroupCommand.run(resultListCommand);
                }

                process(args, resultListCommandOld, groupCommand.listCommand, groupCommand, index+1);
                isFoundCommand = true;
            }
        }
        if(!isFoundCommand && !current.internals.isEmpty()) {
            printHelp(current);
        }
        return resultListCommand;
    }

    /**
     * static
     */

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

        StringBuilder commandRequireNew = new StringBuilder(commandRequire);
        ExposeListCommand.commands(groupCommand.listCommand).forEach(require -> {
            if (!commandRequireNew.isEmpty()) {
                commandRequireNew.append(", ");
            }
            commandRequireNew.append(ExposeCommand.name(require));
        });

        if(existsDefault) {
            System.out.println(taps(taps) + "- " + commandRequireNew);
        }

        for (GroupCommand command : groupCommand.internals) {
            if(!command.isDefault) {
                System.out.println(taps(taps) + command.name + ":");
                printHelp(command, taps + 1, commandRequireNew.toString());
            }
        }

        if(groupCommand.internals.isEmpty()){
            System.out.println(taps(taps) + "- " + commandRequireNew);
        }
    }

    private static String taps(int taps) {
        StringBuilder staps = new StringBuilder();
        for (int i = 0; i < taps; i++) {
            staps.append("\t");
        }
        return staps.toString();
    }
}
