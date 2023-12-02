package tools.gnzlz.command.object;

import tools.gnzlz.command.Command;

import java.util.ArrayList;

public class ExposeListCommand {


    /**
     * commands
     * @param listCommand listCommand
     */
    public static ArrayList<Command<?,?,?>> commands(ListCommand listCommand){
        return listCommand.commands;
    }
}
