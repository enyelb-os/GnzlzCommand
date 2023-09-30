package tools.gnzlz.command.process.functional;

@FunctionalInterface
public interface FunctionRunProcess<R> {

    R run(Object value);
}
