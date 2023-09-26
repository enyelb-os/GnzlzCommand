package tools.gnzlz.command.command.object;

import tools.gnzlz.command.command.Command;

import java.util.ArrayList;

public class ExposeListCommand {
    public static ArrayList<Command<?,?,?>> commands(ListCommand listCommand){
        return listCommand.commands;
    }
}
