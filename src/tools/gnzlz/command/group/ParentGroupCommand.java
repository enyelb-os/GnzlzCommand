package tools.gnzlz.command.group;

import tools.gnzlz.command.command.Command;
import tools.gnzlz.command.command.object.ListCommand;

public class ParentGroupCommand {

    /**
     * parent
     */
    final GroupCommand parent;

    /**
     * ParentGroupCommand
     * @param listCommand l
     */
    ParentGroupCommand(ListCommand listCommand){
        this.parent = new GroupCommand("parent", true, false, null, listCommand);
    }

    /**
     * addGroup
     * @param groupCommands gc
     */
    public ParentGroupCommand addGroup(GroupCommand ... groupCommands) {
        this.parent.addGroup(groupCommands);
        return this;
    }

    /**
     * addCommand
     * @param commands c
     */
    public ParentGroupCommand addCommand(Command<?,?,?> ... commands) {
        parent.addCommand(commands);
        return this;
    }

    /**
     * create
     */
    public static ParentGroupCommand create(ListCommand listCommand) {
        return new ParentGroupCommand(listCommand);
    }

    /**
     * create
     */
    public static ParentGroupCommand create() {
        return create(ListCommand.create());
    }

    /**
     * use
     * @param listCommand lc
     * @param commands c
     */
    public ParentGroupCommand use(ListCommand listCommand, String ... commands) {
        parent.use(listCommand, commands);
        return this;
    }

    /**
     * use
     * @param listCommand lc
     */
    public ParentGroupCommand use(ListCommand listCommand) {
        parent.use(listCommand);
        return this;
    }
}
