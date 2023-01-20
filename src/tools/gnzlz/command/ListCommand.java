package tools.gnzlz.command;

import java.util.ArrayList;

public class ListCommand {

    public Object get(String name){
        for (Command command: Command.listCommands) {
            if(command.name().equals(name)){
                return command.value();
            }
        }
        return null;
    }

    public String string(String name){
        for (Command command: Command.listCommands) {
            if(command.name().equals(name)){
                return command.value().toString();
            }
        }
        return "";
    }

    public int integer(String name){
        for (Command command: Command.listCommands) {
            if(command.name().equals(name) && command.value() instanceof Integer){
                return (int) command.value();
            }
        }
        return -1;
    }

    public void listCommands(FunctionCommand functionCommand){
        for (Command command: Command.listCommands) {
            functionCommand.run(command);
        }
    }
}
