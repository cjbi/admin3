package tech.wetech.admin3.sys.service.dto;

import java.time.LocalDateTime;

/**
 * @author cjbi
 */
public record LogDTO(Long id, String content, String typeName, LocalDateTime occurredOn) {
}
