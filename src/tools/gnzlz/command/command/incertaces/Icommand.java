package tools.gnzlz.command.command.incertaces;

public interface Icommand<T,R,C> {
    /***************************************
     * abstract
     ***************************************/

     R valueProcess(Object value);
}
