package tech.wetech.admin3.common.authz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

/**
 * <p>
 * Requires the current executor's Subject to imply a particular permission in
 * order to execute the annotated method.  If the executor's associated
 * Subject determines that the
 * executor does not imply the specified permission, the method will not be executed.
 * </p>
 *
 * <p>For example, this declaration:
 * <p/>
 * <code>&#64;RequiresPermissions( {"file:read", "write:aFile.txt"} )<br/>
 * void someMethod();</code>
 * <p/>
 * indicates the current user must be able to both <tt>read</tt> and <tt>write</tt>
 * to the file <tt>aFile.txt</tt> in order for the <tt>someMethod()</tt> to execute, otherwise
 * an Exception will be thrown.
 *
 * @since 0.1
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermissions {

  /**
   * The permission string which will be passed to {@link PermissionHelper#isPermitted(Set, String[], Logical)}
   * to determine if the user is allowed to invoke the code protected by this annotation.
   */
  String[] value();

  /**
   * The logical operation for the permission checks in case multiple roles are specified. AND is the default
   *
   * @since 1.1.0
   */
  Logical logical() default Logical.AND;

}
