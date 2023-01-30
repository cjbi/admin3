package tech.wetech.admin3.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cjbi
 */
public class SessionItemHolder {

  private SessionItemHolder() {
  }

  private static final ThreadLocal<Map<String, Object>> store = InheritableThreadLocal.withInitial(HashMap::new);

  public static void setItem(String key, Object obj) {
    store.get().put(key, obj);
  }

  public static final Object getItem(String key) {
    return store.get().get(key);
  }

  public static final void clear() {
    store.remove();
  }

}
