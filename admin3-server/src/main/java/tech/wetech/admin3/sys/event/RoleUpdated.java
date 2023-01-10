package tech.wetech.admin3.sys.event;

import tech.wetech.admin3.common.DomainEvent;
import tech.wetech.admin3.sys.model.Role;

/**
 * @author cjbi
 */
public record RoleUpdated(Role role) implements DomainEvent {
}
