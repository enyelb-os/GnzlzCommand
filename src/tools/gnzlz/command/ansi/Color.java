package tools.gnzlz.command.ansi;

public enum Color {

    BLACK(Command.BLACK,Command.BLACK_BG),
    RED(Command.RED,Command.RED_BG),
    GREEN(Command.GREEN,Command.GREEN_BG),
    YELLOW(Command.YELLOW,Command.YELLOW_BG),
    BLUE(Command.BLUE,Command.BLUE_BG),
    PURPLE(Command.PURPLE,Command.PURPLE_BG),
    CYAN(Command.CYAN,Command.CYAN_BG),
    WHITE(Command.WHITE,Command.WHITE_BG);

    protected final Command foreground;
    protected final Command background;

    private Color(Command foreground, Command background){
        this.foreground = foreground;
        this.background = background;
    }

    public String print(Object text) {
        return foreground.toString().concat(text.toString()).concat(Command.RESET.toString());
    }


    public String print(Color background,Object text) {
        return background.background.toString().concat(this.print(text));
    }

    public String setFG() {
        return foreground.toString();
    }

    public String setBG() {
        return background.toString();
    }

    public String set(Color background) {
        return background.background.toString().concat(foreground.toString());
    }
}