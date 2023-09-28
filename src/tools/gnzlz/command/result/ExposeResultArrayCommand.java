package tools.gnzlz.command.result;

public class ExposeResultArrayCommand {


    public static ResultArrayListCommand create(){
        return new ResultArrayListCommand();
    }

    public static void addResultListCommand(ResultArrayListCommand resultArrayListCommand, ResultListCommand resultListCommand){
        resultArrayListCommand.add(resultListCommand);
    }

}
