package tools.gnzlz.command.ansi;

public enum Color {

    /**
     * BLACK
     */
    BLACK(Command.BLACK,Command.BLACK_BG),

    /**
     * RED
     */
    RED(Command.RED,Command.RED_BG),

    /**
     * GREEN
     */
    GREEN(Command.GREEN,Command.GREEN_BG),

    /**
     * YELLOW
     */
    YELLOW(Command.YELLOW,Command.YELLOW_BG),

    /**
     * BLUE
     */
    BLUE(Command.BLUE,Command.BLUE_BG),

    /**
     * PURPLE
     */
    PURPLE(Command.PURPLE,Command.PURPLE_BG),

    /**
     * CYAN
     */
    CYAN(Command.CYAN,Command.CYAN_BG),

    /**
     * WHITE
     */
    WHITE(Command.WHITE,Command.WHITE_BG);

    /**
     * BLACK
     */
    private final Command foreground;

    /**
     * BLACK
     */
    private final Command background;

    /**
     * Color
     * @param foreground fore
     * @param background back
     */
    Color(Command foreground, Command background){
        this.foreground = foreground;
        this.background = background;
    }

    /**
     * print
     * @param text text
     */
    public String print(Object text) {
        return foreground.toString().concat(text.toString()).concat(Command.RESET.toString());
    }

    /**
     * print
     * @param background b
     * @param text t
     */
    public String print(Color background, Object text) {
        return background.background.toString().concat(this.print(text));
    }

    /**
     * set
     * @param background back
     */
    public String set(Color background) {
        return background.background.toString().concat(foreground.toString());
    }
}