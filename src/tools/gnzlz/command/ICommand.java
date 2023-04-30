package tools.gnzlz.command;

public abstract class ICommand<Type extends ICommand> {

    /***************************************
     * vars
     ***************************************/

    final String name;

    /***************************************
     * vars
     ***************************************/

    Object value;

    /***************************************
     * vars
     ***************************************/

    boolean assign;

    /***************************************
     * constructor
     ***************************************/

    ICommand(String name){
        this.name = name;
    }

    /***************************************
     * get
     ***************************************/

    public String name() {
        return name;
    }

    /***************************************
     * get
     ***************************************/

    public Object value() {
        return value;
    }

    /***************************************
     * set
     ***************************************/

    public Type value(Object value) {
        this.value = value;
        return (Type) this;
    }
}
