package tools.gnzlz.command;

public class ParentGroupCommand {

    /***************************************
     * vars
     ***************************************/

    protected final GroupCommand parent;

    /***************************************
     * constructor
     ***************************************/

    ParentGroupCommand(ListCommand listCommand){
        this.parent = new GroupCommand("parent", true, false, null, listCommand);
    }

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
        this.parent.addGroup(groupCommands);
        return this;
    }

    /***************************************
     * add Command
     ***************************************/

    public ParentGroupCommand addCommand(Command ... commands) {
        parent.addCommand(commands);
        return this;
    }

    /***************************************
     * static add Command
     ***************************************/

    public Command command(String command) {
        return parent.command(command);
    }

    /***************************************
     * static
     ***************************************/

    public static ParentGroupCommand create(ListCommand listCommand) {
        return new ParentGroupCommand(listCommand);
    }

    public static ParentGroupCommand create() {
        return create(ListCommand.create());
    }

    /***************************************
     * static
     ***************************************/

    public ParentGroupCommand use(ListCommand listCommand, String ... commands) {
        parent.use(listCommand, commands);
        return this;
    }

    public ParentGroupCommand use(ListCommand listCommand) {
        parent.use(listCommand);
        return this;
    }

}
