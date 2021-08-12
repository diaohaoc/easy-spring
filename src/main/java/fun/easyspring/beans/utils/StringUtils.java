package fun.easyspring.beans.utils;

import java.util.Collection;

/**
 * Create by DiaoHao on 2021/7/16 16:31
 */
public class StringUtils {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    public StringUtils() {
    }

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }

    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static boolean hasLength(String str) {
        return str != null && str.length() > 0;
    }

    public static String[] toStringArray(Collection<String> collection) {
        return !collection.isEmpty() ? collection.toArray(EMPTY_STRING_ARRAY) : EMPTY_STRING_ARRAY;
    }

}
