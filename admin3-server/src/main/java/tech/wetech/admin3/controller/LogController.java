package tech.wetech.admin3.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.wetech.admin3.sys.service.LogService;
import tech.wetech.admin3.sys.service.dto.LogDTO;
import tech.wetech.admin3.sys.service.dto.PageDTO;

/**
 * @author cjbi
 */
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/logs")
public class LogController {

  private final LogService logService;

  public LogController(LogService logService) {
    this.logService = logService;
  }

  @GetMapping
  public ResponseEntity<PageDTO<LogDTO>> findLogs(Pageable pageable) {
    return ResponseEntity.ok(logService.findLogs(pageable));
  }


  /**
   * 清空日志
   * @return
   */
  @DeleteMapping
  public ResponseEntity<Void> cleanLogs(){
    logService.cleanLogs();
    return ResponseEntity.noContent().build();
  }

}
