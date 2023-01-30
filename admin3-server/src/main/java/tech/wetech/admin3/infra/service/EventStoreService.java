package tech.wetech.admin3.infra.service;

import org.springframework.stereotype.Service;
import tech.wetech.admin3.common.DomainEvent;
import tech.wetech.admin3.common.EventStore;
import tech.wetech.admin3.common.JsonUtils;
import tech.wetech.admin3.sys.model.StoredEvent;
import tech.wetech.admin3.sys.repository.StoredEventRepository;

/**
 * @author cjbi
 */
@Service
public class EventStoreService implements EventStore {

  private final StoredEventRepository storedEventRepository;

  public EventStoreService(StoredEventRepository storedEventRepository) {
    this.storedEventRepository = storedEventRepository;
  }

  @Override
  public void append(DomainEvent aDomainEvent) {
    StoredEvent storedEvent = new StoredEvent();
    storedEvent.setEventBody(JsonUtils.stringify(aDomainEvent));
    storedEvent.setOccurredOn(aDomainEvent.occurredOn());
    storedEvent.setTypeName(aDomainEvent.getClass().getTypeName());
    storedEventRepository.save(storedEvent);
  }

}
