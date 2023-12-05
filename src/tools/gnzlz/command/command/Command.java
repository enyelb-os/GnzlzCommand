package tools.gnzlz.command.command;

import tools.gnzlz.command.command.functional.FunctionRequiredCommand;
import tools.gnzlz.command.command.functional.hidden.FunctionCreateObject;
import tools.gnzlz.command.command.functional.FunctionSetError;
import tools.gnzlz.command.command.functional.FunctionValidCommand;
import tools.gnzlz.command.result.*;

import java.util.ArrayList;

public abstract class Command<Type, R, C extends Command<?, ?, ?>> {

    /**
     * name
     */
    final String name;

    /**
     * required
     */
    FunctionRequiredCommand required;

    /**
     * required
     */
    FunctionValidCommand valid;

    /**
     * message
     */
    String message;

    /**
     * error
     */
    final ArrayList<String> errors;

    /**
     * setError
     */
    final FunctionSetError error;

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
        this.required = FunctionRequiredCommand.FALSE;
        this.valid = FunctionValidCommand.TRUE;
        this.message = "";
        this.commands = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.error = this.errors::add;
    }

    /**
     * set required
     * @param required required
     */
    public C required(boolean required) {
        this.required = required ? FunctionRequiredCommand.TRUE : FunctionRequiredCommand.FALSE;
        return (C) this;
    }

    /**
     * set required
     * @param required required
     */
    public C required(FunctionRequiredCommand required) {
        if (required != null) {
            this.required = required;
        }
        return (C) this;
    }

    /**
     * set required
     */
    public C required() {
        this.required = FunctionRequiredCommand.TRUE;
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
     * set valid
     * @param valid valid
     */
    public C valid(FunctionValidCommand valid) {
        if (valid != null) {
            this.valid = valid;
        }
        return (C) this;
    }

    /**
     * processQuestion
     * @param resultListCommand r
     * @param allResultListCommand a
     */
    protected abstract void processQuestion(ResultListCommand resultListCommand, ResultListCommand allResultListCommand, ResultArrayListCommand resultArrayListCommand);

    /**
     * processArgs
     * @param resultListCommand r
     * @param value v
     */
    protected abstract void processArgs(ResultListCommand resultListCommand, Object value);

    /**
     * validValue
     * @param value value
     */
    protected abstract R processValue(Object value);

}
