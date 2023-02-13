package tech.wetech.admin3.sys.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.wetech.admin3.Admin3Properties;
import tech.wetech.admin3.common.JsonUtils;
import tech.wetech.admin3.common.StringUtils;
import tech.wetech.admin3.sys.model.StoredEvent;
import tech.wetech.admin3.sys.repository.StoredEventRepository;
import tech.wetech.admin3.sys.service.dto.LogDTO;
import tech.wetech.admin3.sys.service.dto.PageDTO;

import java.util.Map;
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

  public PageDTO<LogDTO> findLogs(Pageable pageable) {
    Map<String, String> templates = admin3Properties.getLogTemplates();
    Page<StoredEvent> page = storedEventRepository.findByTypeNameInOrderByOccurredOnDesc(templates.keySet(), pageable);
    return new PageDTO<>(page.getContent().stream()
      .map(e -> new LogDTO(e.getId(),
        StringUtils.simpleRenderTemplate(templates.get(e.getTypeName()), JsonUtils.parseToMap(e.getEventBody())),
        e.getTypeName(),
        e.getOccurredOn())
      )
      .collect(Collectors.toList()), page.getTotalElements());
  }

  public void cleanLogs() {
    storedEventRepository.deleteAll();
  }
}
