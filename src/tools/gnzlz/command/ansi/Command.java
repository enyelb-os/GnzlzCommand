package tools.gnzlz.command.ansi;

import tools.gnzlz.command.process.print.PrintCommand;

public enum Command {

    /**
     * RESET
     */
    RESET("\u001B[0m",""),

    /**
     * BLACK
     */
    BLACK("\u001B[30m",""), BLACK_BG("\u001B[40m",""),

    /**
     * RED
     */
    RED("\u001B[31m",""),  RED_BG("\u001B[41m",""),

    /**
     * GREEN
     */
    GREEN("\u001B[32m",""), GREEN_BG("\u001B[42m",""),

    /**
     * YELLOW
     */
    YELLOW("\u001B[33m",""), YELLOW_BG("\u001B[43m",""),

    /**
     * BLUE
     */
    BLUE("\u001B[34m",""), BLUE_BG("\u001B[44m",""),

    /**
     * PURPLE
     */
    PURPLE("\u001B[35m",""), PURPLE_BG("\u001B[45m",""),

    /**
     * CYAN
     */
    CYAN("\u001B[36m",""),  CYAN_BG("\u001B[46m",""),

    /**
     * WHITE
     */
    WHITE("\u001B[37m",""), WHITE_BG("\u001B[47m","");

    /**
     * cmd
     */
    private final String cmd;

    /**
     * terminal
     */
    private final String terminal;

    /**
     * Command
     * @param cmd cmd
     * @param terminal terminal
     */
    Command(String cmd, String terminal){
        this.cmd = cmd;
        this.terminal = terminal;
    }

    /**
     * toString
     */
    @Override
    public String toString() {
        if (System.getProperty("os.name").contains("Windows")) {
            return cmd;
        } else {
            return terminal;
        }
    }
}
