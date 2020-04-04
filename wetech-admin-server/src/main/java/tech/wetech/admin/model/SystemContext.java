package tech.wetech.admin.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cjbi
 */
public class SystemContext {

    private static final ThreadLocal<Map<String, Object>> store = ThreadLocal.withInitial(() -> new HashMap<>(16));

    public static void putThreadCache(String key, Object value) {
        store.get().put(key, value);
    }

    public static <T> T getThreadCache(String key, Class<T> t) {
        return (T) store.get().get(key);
    }


}
