package tools.gnzlz.command.funtional;

@FunctionalInterface
public interface PrintConsole {

    void print(String text);

    /***************************************
     * Default println
     ***************************************/

    default void println(String text){
        print(text + System.lineSeparator());
    }

    /***************************************
     * Default println
     ***************************************/

    default void println(){
        print(System.lineSeparator());
    }

    /***************************************
     * Default print separator
     ***************************************/

    default void printSeparator(int n){
        String s = "";
        for (int i = 0; i < n ; i++) {
            s += "=";
        }
        println(s);
    }

    /***************************************
     * Default print center
     ***************************************/

    default void printCenter(String text, int n){
        String s = "";
        for (int i = 0; i < n ; i++) {
            s += " ";
            if((s.length() * 2) + text.length() > n){
                break;
            }
        }
        println(s + text);
    }

    /***************************************
     * Default print center
     ***************************************/

    default void printTitle(String text, int n){
        printSeparator(n);
        printCenter(text, n);
        printSeparator(n);
    }
}
