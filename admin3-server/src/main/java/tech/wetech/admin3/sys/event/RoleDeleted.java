package tech.wetech.admin3.sys.event;

import tech.wetech.admin3.common.DomainEvent;
import tech.wetech.admin3.sys.model.Role;

/**
 * @author cjbi
 */
public record RoleDeleted(Role role) implements DomainEvent {
}
