package tools.gnzlz.command;

public class ParentGroupCommand {

    /***************************************
     * vars
     ***************************************/

    protected final GroupCommand parent = new GroupCommand("parent", true, false, null);


    /***************************************
     * get internal GroupCommand
     ***************************************/
    protected boolean existsGroupCommand(String name) {
        for (GroupCommand groupCommand : parent.internals) {
            if(groupCommand.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /***************************************
     * add Group
     ***************************************/

    public ParentGroupCommand addGroup(GroupCommand... groupCommands) {
        if(groupCommands != null) {
            for (GroupCommand groupCommand: groupCommands) {
                if(groupCommand != null && !this.existsGroupCommand(groupCommand.name)) {
                    this.parent.internals.add(groupCommand);
                }
            }
        }
        return this;
    }

    /***************************************
     * add Command
     ***************************************/

    public ParentGroupCommand addCommand(Command ... commands) {
        if(commands != null) {
            for (Command  command: commands) {
                if(command != null) {
                    command.groups(this.parent.name);
                }
            }
        }
        return this;
    }

    /***************************************
     * static add Command
     ***************************************/

    public static Command command(String command) {
        return Command.command(command);
    }

    /***************************************
     * static
     ***************************************/

    public static ParentGroupCommand create() {
        return new ParentGroupCommand();
    }

    /***************************************
     * static
     ***************************************/

    public ParentGroupCommand use(String ... commands) {
        if(commands != null){
            for (String command : commands) {
                for (Command commandOld: Command.listCommands) {
                    if(commandOld.name.equals(command) || "all".equals(command)){
                        commandOld.groups(this.parent.name);
                    }
                }
            }
        }
        return this;
    }

    public ParentGroupCommand use() {
        return use("all");
    }

}
