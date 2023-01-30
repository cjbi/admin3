package tech.wetech.admin3.common;

import java.util.*;

/**
 * Static helper class for use dealing with Collections.
 *
 * @since 0.9
 */
public class CollectionUtils {

  //TODO - complete JavaDoc

  public static <E> Set<E> asSet(E... elements) {
    if (elements == null || elements.length == 0) {
      return Collections.emptySet();
    }

    if (elements.length == 1) {
      return Collections.singleton(elements[0]);
    }

    LinkedHashSet<E> set = new LinkedHashSet<E>(elements.length * 4 / 3 + 1);
    Collections.addAll(set, elements);
    return set;
  }

  /**
   * Returns {@code true} if the specified {@code Collection} is {@code null} or {@link Collection#isEmpty empty},
   * {@code false} otherwise.
   *
   * @param c the collection to check
   * @return {@code true} if the specified {@code Collection} is {@code null} or {@link Collection#isEmpty empty},
   * {@code false} otherwise.
   * @since 1.0
   */
  public static boolean isEmpty(Collection c) {
    return c == null || c.isEmpty();
  }

  /**
   * Returns {@code true} if the specified {@code Map} is {@code null} or {@link Map#isEmpty empty},
   * {@code false} otherwise.
   *
   * @param m the {@code Map} to check
   * @return {@code true} if the specified {@code Map} is {@code null} or {@link Map#isEmpty empty},
   * {@code false} otherwise.
   * @since 1.0
   */
  public static boolean isEmpty(Map m) {
    return m == null || m.isEmpty();
  }

  /**
   * Returns the size of the specified collection or {@code 0} if the collection is {@code null}.
   *
   * @param c the collection to check
   * @return the size of the specified collection or {@code 0} if the collection is {@code null}.
   * @since 1.2
   */
  public static int size(Collection c) {
    return c != null ? c.size() : 0;
  }

  /**
   * Returns the size of the specified map or {@code 0} if the map is {@code null}.
   *
   * @param m the map to check
   * @return the size of the specified map or {@code 0} if the map is {@code null}.
   * @since 1.2
   */
  public static int size(Map m) {
    return m != null ? m.size() : 0;
  }

  public static <E> List<E> asList(E... elements) {
    if (elements == null || elements.length == 0) {
      return Collections.emptyList();
    }

    // Integer overflow does not occur when a large array is passed in because the list array already exists
    return Arrays.asList(elements);
  }

    /*public static <E> Deque<E> asDeque(E... elements) {
        if (elements == null || elements.length == 0) {
            return new ArrayDeque<E>();
        }
        // Avoid integer overflow when a large array is passed in
        int capacity = computeListCapacity(elements.length);
        ArrayDeque<E> deque = new ArrayDeque<E>(capacity);
        Collections.addAll(deque, elements);
        return deque;
    }*/

  static int computeListCapacity(int arraySize) {
    return (int) Math.min(5L + arraySize + (arraySize / 10), Integer.MAX_VALUE);
  }
}
