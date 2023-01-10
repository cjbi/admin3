package tech.wetech.admin3.sys.event;

import tech.wetech.admin3.common.DomainEvent;
import tech.wetech.admin3.sys.model.Resource;

/**
 * @author cjbi
 */
public record ResourceUpdated(Resource resource) implements DomainEvent {
}
