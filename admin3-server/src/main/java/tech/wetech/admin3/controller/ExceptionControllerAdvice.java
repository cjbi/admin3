package tech.wetech.admin3.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.wetech.admin3.common.BusinessException;
import tech.wetech.admin3.common.CommonResultStatus;
import tech.wetech.admin3.common.ResultStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
@SecurityRequirement(name = "bearerAuth")
@RestControllerAdvice
public class ExceptionControllerAdvice {

  private final Map<ResultStatus, HttpStatus> codeMap = new HashMap<>() {{
    put(CommonResultStatus.FAIL, HttpStatus.BAD_REQUEST);
    put(CommonResultStatus.PARAM_ERROR, HttpStatus.BAD_REQUEST);
    put(CommonResultStatus.RECORD_NOT_EXIST, HttpStatus.NOT_FOUND);
    put(CommonResultStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
    put(CommonResultStatus.FORBIDDEN, HttpStatus.FORBIDDEN);
  }};

  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleDefaultErrorView(Exception ex, HttpServletRequest request) {
    log.error("Handle exception, message={}, requestUrl={}", ex.getMessage(), request.getRequestURI(), ex);
    Map<String, Object> body = new HashMap<>();
    body.put("code", CommonResultStatus.SERVER_ERROR.getCode());
    body.put("message", ex.getMessage());
    body.put("success", false);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
    Map<String, Object> body = Map.of("code", ex.getStatus().getCode(), "message", ex.getMessage(), "success", false);
    return ResponseEntity.status(codeMap.getOrDefault(ex.getStatus(), HttpStatus.BAD_REQUEST)).body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();
    e.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    Map<String, Object> body = getErrorsMap(errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
    ConstraintViolationException e) {
    Map<String, String> errors = new HashMap<>();
    e.getConstraintViolations().forEach(error -> {
      String property = error.getPropertyPath().toString();
      String errorMessage = error.getMessage();
      errors.put(property, errorMessage);
    });
    Map<String, Object> body = getErrorsMap(errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  private Map<String, Object> getErrorsMap(Map<String, String> fieldErrors) {
    Map<String, Object> body = new HashMap<>();
    body.put("code", CommonResultStatus.PARAM_ERROR.getCode());
    body.put("message", fieldErrors.entrySet().stream()
      .map(m -> m.getKey() + " " + m.getValue())
      .collect(Collectors.joining(", "))
    );
    body.put("errors", fieldErrors);
    body.put("success", false);
    return body;
  }


}

