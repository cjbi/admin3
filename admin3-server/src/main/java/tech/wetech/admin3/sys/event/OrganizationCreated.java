package tech.wetech.admin3.sys.event;

import tech.wetech.admin3.common.DomainEvent;
import tech.wetech.admin3.sys.model.Organization;

/**
 * @author cjbi
 */
public record OrganizationCreated(Organization organization) implements DomainEvent {
}
