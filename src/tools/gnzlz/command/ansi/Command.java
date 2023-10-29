package tools.gnzlz.command.ansi;

import tools.gnzlz.command.process.print.PrintCommand;

public enum Command {

    RESET("\u001B[0m",""),
    BLACK("\u001B[30m",""), BLACK_BG("\u001B[40m",""),
    RED("\u001B[31m",""),  RED_BG("\u001B[41m",""),
    GREEN("\u001B[32m",""), GREEN_BG("\u001B[42m",""),
    YELLOW("\u001B[33m",""), YELLOW_BG("\u001B[43m",""),
    BLUE("\u001B[34m",""), BLUE_BG("\u001B[44m",""),
    PURPLE("\u001B[35m",""), PURPLE_BG("\u001B[45m",""),
    CYAN("\u001B[36m",""),  CYAN_BG("\u001B[46m",""),
    WHITE("\u001B[37m",""), WHITE_BG("\u001B[47m","");

    protected final String cmd;
    protected final String terminal;

    private Command(String cmd, String terminal){
        this.cmd = cmd;
        this.terminal = terminal;
    }

    @Override
    public String toString() {
        if (System.getProperty("os.name").contains("Windows")) {
            return cmd;
        } else {
            return terminal;
        }
    }
}
