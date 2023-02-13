package tech.wetech.admin3.sys.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.wetech.admin3.Admin3Properties;
import tech.wetech.admin3.Admin3Properties.Event;
import tech.wetech.admin3.common.CollectionUtils;
import tech.wetech.admin3.common.JsonUtils;
import tech.wetech.admin3.common.StringUtils;
import tech.wetech.admin3.sys.model.StoredEvent;
import tech.wetech.admin3.sys.repository.StoredEventRepository;
import tech.wetech.admin3.sys.service.dto.LogDTO;
import tech.wetech.admin3.sys.service.dto.PageDTO;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
@Service
public class LogService {

  private final StoredEventRepository storedEventRepository;

  private final Admin3Properties admin3Properties;

  public LogService(StoredEventRepository storedEventRepository, Admin3Properties admin3Properties) {
    this.storedEventRepository = storedEventRepository;
    this.admin3Properties = admin3Properties;
  }

  public PageDTO<LogDTO> findLogs(Set<String> typeNames, Pageable pageable) {
    Map<String, Event> eventProps = admin3Properties.getEvents();
    Page<StoredEvent> page = storedEventRepository.findByTypeNameInOrderByOccurredOnDesc(
      CollectionUtils.isEmpty(typeNames) ? eventProps.keySet() : typeNames,
      pageable
    );
    return new PageDTO<>(page.getContent().stream()
      .map(e -> new LogDTO(e.getId(),
          StringUtils.simpleRenderTemplate(eventProps.get(e.getTypeName()).getLogTemplate(), JsonUtils.parseToMap(e.getEventBody())),
          e.getEventBody(),
          e.getTypeName(),
          e.getOccurredOn(),
          e.getUser()
        )
      )
      .collect(Collectors.toList()), page.getTotalElements());
  }

  public void cleanLogs() {
    storedEventRepository.deleteAll();
  }
}
