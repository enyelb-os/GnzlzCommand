package tools.gnzlz.command.process.functional;

@FunctionalInterface
public interface FunctionOutputProcess {

    void print(String text);

    default void print(boolean validate, String text){
        if (validate) {
            this.print(text);
        }
    }

    /**
     * Default println
     * @param text text
     */

    default void println(String text){
        print(text + System.lineSeparator());
    }

    default void println(boolean validate, String text){
        if (validate) {
            this.println(text);
        }
    }

    /**
     * Default println
     */

    default void println(){
        print(System.lineSeparator());
    }

    default void println(boolean validate){
        if (validate) {
            this.println();
        }
    }

    /**
     * Default print separator
     * @param n n
     */

    default void printSeparator(int n){
        println("=".repeat(n));
    }

    /**
     * Default print center
     * @param text text
     * @param n n
     */

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

    /**
     * Default print center
     * @param text text
     * @param n n
     */

    default void printTitle(String text, int n){
        printSeparator(n);
        printCenter(text, n);
        printSeparator(n);
    }
}
