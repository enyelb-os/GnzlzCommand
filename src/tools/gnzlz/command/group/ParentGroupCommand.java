package tools.gnzlz.command.group;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.object.ListCommand;

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
     * add Group
     ***************************************/

    public ParentGroupCommand addGroup(GroupCommand... groupCommands) {
        this.parent.addGroup(groupCommands);
        return this;
    }

    /***************************************
     * add Command
     ***************************************/

    public ParentGroupCommand addCommand(Command<?,?,?>... commands) {
        parent.addCommand(commands);
        return this;
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
