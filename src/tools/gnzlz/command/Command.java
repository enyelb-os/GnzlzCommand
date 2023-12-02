package tools.gnzlz.command;

import tools.gnzlz.command.functional.FunctionRequiredCommand;
import tools.gnzlz.command.functional.FunctionCreateObject;
import tools.gnzlz.command.result.ExposeResultCommand;
import tools.gnzlz.command.result.ExposeResultListCommand;
import tools.gnzlz.command.result.ResultCommand;
import tools.gnzlz.command.result.ResultListCommand;

import java.util.ArrayList;

public abstract class Command<Type, R, C extends Command<?, ?, ?>> {

    /**
     * name
     */
    final String name;

    /**
     * TRUE
     */
    final static FunctionRequiredCommand TRUE = (list) -> true;

    /**
     * FALSE
     */
    final static FunctionRequiredCommand FALSE = (list) -> false;

    /**
     * required
     */
    FunctionRequiredCommand required;

    /**
     * message
     */
    String message;

    /**
     * value
     */
    Type value;

    /**
     * commands
     */
    final ArrayList<String> commands;

    /**
     * constructor
     * @param name name
     */
    protected Command(String name){
        this.name = name;
        this.required = FALSE;
        this.message = "";
        this.commands = new ArrayList<>();
    }

    /**
     * set required
     * @param required required
     */
    public C required(boolean required) {
        this.required = required ? TRUE : FALSE;
        return (C) this;
    }

    /**
     * set required
     * @param required required
     */
    public C required(FunctionRequiredCommand required) {
        this.required = required;
        return (C) this;
    }

    /**
     * set required
     */
    public C required() {
        this.required = TRUE;
        return (C) this;
    }

    /**
     * set value
     * @param value value
     */
    public C value(Type value) {
        this.value = value;
        return (C) this;
    }

    /**
     * message
     * @param message m
     */
    public C message(String message) {
        this.message = message;
        return (C) this;
    }

    /**
     * commands
     * @param commands c
     */
    public C commands(String ... commands) {
        this.addCommands(commands);
        return (C) this;
    }

    /**
     * valid exists name args
     * @param commandName commandName
     */
    private void addCommand(String commandName){
        for (String commandNameOld: this.commands) {
            if (commandNameOld.equals(commandName)) {
                return;
            }
        }
        this.commands.add(commandName);
    }

    /**
     * valid list command exists name args
     * @param commands commands
     */
    private void addCommands(String ... commands){
        if(commands != null){
            for (String commandName : commands) {
                addCommand(commandName);
            }
        }
    }

    /**
     * resultCommand
     * @param resultCommands re
     * @param createObject c
     */
    protected ResultCommand<R> resultCommand(ResultListCommand resultCommands, FunctionCreateObject<R> createObject) {
        for (ResultCommand<?> resultCommand: ExposeResultListCommand.resultCommands(resultCommands)) {
            if (ExposeResultCommand.command(resultCommand) == this) {
                return (ResultCommand<R>) resultCommand;
            }
        }
        ResultCommand<R> resultCommand = createResultCommand(createObject.create());
        ExposeResultListCommand.addResultCommand(resultCommands, resultCommand);
        return resultCommand;
    }

    /**
     * createResultCommand
     * @param object object
     */
    protected ResultCommand<R> createResultCommand(R object){
        return ExposeResultCommand.create(this, object);
    }

    /**
     * valid
     * @param value value
     */
    public boolean valid(Object value) {
        return processValue(value) != null;
    }

    /**
     * processQuestion
     * @param resultListCommand r
     * @param allResultListCommand a
     */
    protected abstract ResultCommand<R> processQuestion(ResultListCommand resultListCommand, ResultListCommand allResultListCommand);

    /**
     * processArgs
     * @param resultListCommand r
     * @param value v
     */
    protected abstract ResultCommand<R> processArgs(ResultListCommand resultListCommand, Object value);

    /**
     * validValue
     * @param value value
     */
    protected abstract R processValue(Object value);

}