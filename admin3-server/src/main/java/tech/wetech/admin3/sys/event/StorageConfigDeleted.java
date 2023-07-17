package tech.wetech.admin3.sys.event;

import tech.wetech.admin3.common.DomainEvent;
import tech.wetech.admin3.sys.model.StorageConfig;

/**
 * @author cjbi
 */
public record StorageConfigDeleted(StorageConfig config) implements DomainEvent {
}
