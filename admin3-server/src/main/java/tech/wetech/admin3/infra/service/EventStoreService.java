package tech.wetech.admin3.infra.service;

import org.springframework.stereotype.Service;
import tech.wetech.admin3.common.*;
import tech.wetech.admin3.sys.model.StoredEvent;
import tech.wetech.admin3.sys.repository.StoredEventRepository;
import tech.wetech.admin3.sys.repository.UserRepository;
import tech.wetech.admin3.sys.service.dto.UserinfoDTO;

/**
 * @author cjbi
 */
@Service
public class EventStoreService implements EventStore {

  private final StoredEventRepository storedEventRepository;
  private final UserRepository userRepository;

  public EventStoreService(StoredEventRepository storedEventRepository, UserRepository userRepository) {
    this.storedEventRepository = storedEventRepository;
    this.userRepository = userRepository;
  }

  @Override
  public void append(DomainEvent aDomainEvent) {
    StoredEvent storedEvent = new StoredEvent();
    storedEvent.setEventBody(JsonUtils.stringify(aDomainEvent));
    storedEvent.setOccurredOn(aDomainEvent.occurredOn());
    storedEvent.setTypeName(aDomainEvent.getClass().getTypeName());
    UserinfoDTO userInfo = (UserinfoDTO) SessionItemHolder.getItem(Constants.SESSION_CURRENT_USER);
    if (userInfo != null) {
      storedEvent.setUser(userRepository.getReferenceById(userInfo.userId()));
    }
    storedEventRepository.save(storedEvent);
  }

}
