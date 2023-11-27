package tools.gnzlz.command.group;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.ExposeCommand;
import tools.gnzlz.command.command.object.ExposeListCommand;
import tools.gnzlz.command.command.object.ListCommand;
import tools.gnzlz.command.functional.FunctionGroupCommand;
import tools.gnzlz.command.init.InitListCommand;
import tools.gnzlz.command.process.Process;
import tools.gnzlz.command.process.SystemIO;
import tools.gnzlz.command.process.print.PrintGroupCommand;
import tools.gnzlz.command.result.ResultListCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GroupCommand {

    /**
     * internals
     */
    protected final ArrayList<GroupCommand> internals = new ArrayList<GroupCommand>();

    /**
     * functionGroupCommand
     */
    private FunctionGroupCommand functionGroupCommand;

    /**
     * listCommand
     */
    protected ListCommand listCommand;

    /**
     * runDefault
     */
    private boolean runDefault = true;

    /**
     * name
     */
    protected final String name;

    /**
     * isDefault
     */
    protected final boolean isDefault;

    /**
     * GroupCommand
     * @param name name
     * @param hashcode hashcode
     * @param isDefault isDefault
     * @param functionGroupCommand functionGroupCommand
     * @param listCommand listCommand
     */
    protected GroupCommand(String name, boolean hashcode, boolean isDefault, FunctionGroupCommand functionGroupCommand, ListCommand listCommand){
        this.name = name + (hashcode ? this.hashCode() : "");
        this.isDefault = isDefault;
        this.functionGroupCommand = functionGroupCommand;
        this.listCommand = listCommand;
    }

    /**
     * existsGroupCommand
     * @param name name
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
     * addGroup
     * @param groupCommands groupCommands
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
     * @param functionGroupCommand functionGroupCommand
     */
    public GroupCommand execute(FunctionGroupCommand functionGroupCommand) {
        this.functionGroupCommand = functionGroupCommand;
        return this;
    }

    /**
     * addCommand
     * @param commands commands
     */
    public GroupCommand addCommand(Command<?,?,?>... commands) {
        if(commands != null) {
            Arrays.stream(commands).filter(Objects::nonNull).forEach(command -> listCommand.command(command));
        }
        return this;
    }

    /**
     * create
     * @param command command
     */
    public static GroupCommand create(String command) {
        return new GroupCommand(command, false, false, null, ListCommand.create());
    }

    /**
     * create
     */
    public static GroupCommand create() {
        return new GroupCommand("default", true, true, null, ListCommand.create());
    }

    /**
     * parent
     * @param listCommand listCommand
     */
    public static ParentGroupCommand parent(ListCommand listCommand) {
        return ParentGroupCommand.create(listCommand);
    }

    /**
     * parent
     */
    public static ParentGroupCommand parent() {
        return ParentGroupCommand.create();
    }

    /**
     * use
     * @param listCommand listCommand
     * @param commands commands
     */
    public GroupCommand use(ListCommand listCommand, String ... commands) {
        if(commands != null){
            for (String command : commands) {
                ExposeListCommand.commands(listCommand).stream().filter(commandOld -> ExposeCommand.name(commandOld).equals(command) || "all".equals(command)).filter(commandOld -> this.listCommand != listCommand).forEach(commandOld -> this.listCommand.command(commandOld));
            }
        }
        return this;
    }

    /**
     * use
     * @param listCommand listCommand
     */
    public GroupCommand use(ListCommand listCommand) {
        return use(listCommand,"all");
    }

    /**
     * process
     * @param args args
     * @param listCommand listCommand
     * @param groupCommands groupCommands
     */
    public static ResultListCommand process(String[] args, ListCommand listCommand, GroupCommand ... groupCommands) {
        return GroupCommand.process(args, ParentGroupCommand.create(listCommand).addGroup(groupCommands));
    }

    /**
     * process
     * @param args args
     * @param groupCommands groupCommands
     */
    public static ResultListCommand process(String[] args, GroupCommand ... groupCommands) {
        return GroupCommand.process(args, ParentGroupCommand.create().addGroup(groupCommands));
    }

    /**
     * process
     * @param args args
     * @param parentGroupCommand parentGroupCommand
     */
    public static ResultListCommand process(String[] args, ParentGroupCommand parentGroupCommand) {
        return GroupCommand.process(new ArrayList<>(Arrays.asList(args)), parentGroupCommand);
    }

    /**
     * process
     * @param args args
     * @param listCommand listCommand
     * @param groupCommands groupCommands
     */
    public static ResultListCommand process(ArrayList<String> args, ListCommand listCommand, GroupCommand ... groupCommands) {
        return GroupCommand.process(args, ParentGroupCommand.create(listCommand).addGroup(groupCommands));
    }

    /**
     * process
     * @param args args
     * @param groupCommands groupCommands
     */
    public static ResultListCommand process(ArrayList<String> args, GroupCommand ... groupCommands) {
        return GroupCommand.process(args, ParentGroupCommand.create().addGroup(groupCommands));
    }

    /**
     * process
     * @param args args
     * @param parentGroupCommand parentGroupCommand
     */
    public static ResultListCommand process(ArrayList<String> args, ParentGroupCommand parentGroupCommand) {
        return GroupCommand.process(
                args, InitListCommand.create(),
                parentGroupCommand.parent.listCommand, parentGroupCommand.parent, 0
        );
    }

    /**
     * static
     */

    private static ResultListCommand process(ArrayList<String> args, InitListCommand resultListCommandOld, ListCommand listCommand, GroupCommand current, int index) {
        current.runDefault = true;
        boolean isFoundCommand = false;

        ResultListCommand resultListCommand = Process.argsAndQuestions(args, listCommand, resultListCommandOld);

        for (GroupCommand groupCommand : current.internals){
            if((groupCommand.isDefault && current.runDefault) || args.size() > index && args.get(index).equals(groupCommand.name)) {
                if (args.size() > index && args.get(index).equals(groupCommand.name)) {
                    args.remove(index);
                    if (index > 0) {
                        index--;
                    }
                }

                current.runDefault = groupCommand.isDefault;

                process(args, resultListCommandOld, groupCommand.listCommand, groupCommand, index);

                if (groupCommand.functionGroupCommand != null) {
                    groupCommand.functionGroupCommand.run(args, resultListCommand);
                }
                isFoundCommand = true;
            }
        }
        if (!isFoundCommand && !current.internals.isEmpty()) {
            boolean exit = false;
            do {
                PrintGroupCommand.printOptions(current);
                String value = (String) SystemIO.INP.process();
                if (!value.isBlank()) {
                    String[] values = value.split(" ");
                    if (values[0].equalsIgnoreCase("-h") || values[0].equalsIgnoreCase("help") || values[0].equalsIgnoreCase("--help")) {
                        PrintGroupCommand.printHelp(current);
                    } else {
                        ArrayList<String> array = new ArrayList<>(Arrays.asList(values));
                        for (GroupCommand groupCommand: current.internals) {
                            if (groupCommand.name.equals(array.get(0))) {
                                array.remove(index);
                                if (groupCommand.functionGroupCommand != null) {
                                    groupCommand.functionGroupCommand.run(array, resultListCommand);
                                }
                                process(array, resultListCommandOld, groupCommand.listCommand, groupCommand, 0);
                                exit = true;
                                break;
                            }
                        }
                    }
                }
            } while (!exit);
        }
        return resultListCommand;
    }
}
