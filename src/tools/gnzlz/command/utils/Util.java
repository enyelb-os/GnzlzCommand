package tools.gnzlz.command.utils;

import java.util.Arrays;
import java.util.List;

public class Util {

    /**
     * firstNonNull
     * @param <T> T
     * @param params params
     */
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

    /**
     * firstNonNull
     * @param <T> T
     * @param a a
     * @param b b
     */
    public static <T> T firstNonNull(T a, T b) {
        return firstNonNull(Arrays.asList(a,b));
    }

    /**
     * firstNonNull
     * @param <T> T
     * @param a a
     * @param b b
     * @param c c
     */
    public static <T> T firstNonNull(T a, T b, T c) {
        return firstNonNull(Arrays.asList(a,b,c));
    }
}
