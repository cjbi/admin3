package tech.wetech.admin3.common.authz;

import tech.wetech.admin3.common.CollectionUtils;
import tech.wetech.admin3.common.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cjbi
 */
public class PermissionHelper {


  public static boolean isPermitted(Set<String> permissions, String[] value, Logical logical) {
    if (logical == Logical.AND) {
      boolean allMatch = true;
      for (String permission : value) {
        if (!hasPermission(permissions, permission)) {
          allMatch = false;
          break;
        }
      }
      return allMatch;
    } else if (logical == Logical.OR) {
      boolean anyMatch = false;
      for (String permission : value) {
        if (hasPermission(permissions, permission)) {
          anyMatch = true;
          break;
        }
      }
      return anyMatch;
    }
    return false;
  }

  /**
   * 判断是否有对应权限
   *
   * @param permissions
   * @param permission
   * @return
   */
  public static boolean hasPermission(Set<String> permissions, String permission) {
    if (StringUtils.isEmpty(permission)) {
      return true;
    }
    for (String p : permissions) {
      Permission p1 = new Permission(p);
      Permission p2 = new Permission(permission);
      if (p1.implies(p2) || p2.implies(p1)) {
        return true;
      }
    }
    return false;
  }

  protected static class Permission {

    protected static final String WILDCARD_TOKEN = "*";
    protected static final String PART_DIVIDER_TOKEN = ":";
    protected static final String SUBPART_DIVIDER_TOKEN = ",";
    protected static final boolean DEFAULT_CASE_SENSITIVE = false;

    private List<Set<String>> parts;

    protected Permission() {
    }

    public Permission(String wildcardString) {
      this(wildcardString, DEFAULT_CASE_SENSITIVE);
    }

    public Permission(String wildcardString, boolean caseSensitive) {
      setParts(wildcardString, caseSensitive);
    }

    protected void setParts(String wildcardString) {
      setParts(wildcardString, DEFAULT_CASE_SENSITIVE);
    }

    protected void setParts(String wildcardString, boolean caseSensitive) {
      if (wildcardString == null || wildcardString.trim().length() == 0) {
        throw new IllegalArgumentException("Wildcard string cannot be null or empty. Make sure permission strings are properly formatted.");
      }

      wildcardString = wildcardString.trim();

      List<String> parts = CollectionUtils.asList(wildcardString.split(PART_DIVIDER_TOKEN));

      this.parts = new ArrayList<Set<String>>();
      for (String part : parts) {
        Set<String> subparts = CollectionUtils.asSet(part.split(SUBPART_DIVIDER_TOKEN));
        if (!caseSensitive) {
          subparts = lowercase(subparts);
        }
        if (subparts.isEmpty()) {
          throw new IllegalArgumentException("Wildcard string cannot contain parts with only dividers. Make sure permission strings are properly formatted.");
        }
        this.parts.add(subparts);
      }

      if (this.parts.isEmpty()) {
        throw new IllegalArgumentException("Wildcard string cannot contain only dividers. Make sure permission strings are properly formatted.");
      }
    }

    private Set<String> lowercase(Set<String> subparts) {
      Set<String> lowerCasedSubparts = new LinkedHashSet<String>(subparts.size());
      for (String subpart : subparts) {
        lowerCasedSubparts.add(subpart.toLowerCase());
      }
      return lowerCasedSubparts;
    }

    protected List<Set<String>> getParts() {
      return this.parts;
    }


    public boolean implies(Permission p) {
      // By default only supports comparisons with other WildcardPermissions
      if (!(p instanceof Permission)) {
        return false;
      }
      Permission wp = (Permission) p;

      List<Set<String>> otherParts = wp.getParts();

      int i = 0;
      for (Set<String> otherPart : otherParts) {
        // If this permission has less parts than the other permission, everything after the number of parts contained
        // in this permission is automatically implied, so return true
        if (getParts().size() - 1 < i) {
          return true;
        } else {
          Set<String> part = getParts().get(i);
          if (!part.contains(WILDCARD_TOKEN) && !part.containsAll(otherPart)) {
            return false;
          }
          i++;
        }
      }
      // If this permission has more parts than the other parts, only imply it if all of the other parts are wildcards
      for (; i < getParts().size(); i++) {
        Set<String> part = getParts().get(i);
        if (!part.contains(WILDCARD_TOKEN)) {
          return false;
        }
      }
      return true;
    }
  }

}
