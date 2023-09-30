package tools.gnzlz.command.utils;

import java.util.Arrays;
import java.util.List;

public class Util {

    public static <T> T firstNonNull(List<T> params) {
        if (params != null) {
            for (T param : params) {
                if (param != null) {
                    return param;
                }
            }
        }
        return null;
    }

    public static <T> T firstNonNull(T a, T b) {
        return firstNonNull(Arrays.asList(a,b));
    }

    public static <T> T firstNonNull(T a, T b, T c) {
        return firstNonNull(Arrays.asList(a,b,c));
    }
}
