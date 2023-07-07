package tech.wetech.admin3.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.wetech.admin3.sys.model.storage.StorageFile;
import tech.wetech.admin3.sys.service.StorageService;

import java.io.IOException;

/**
 * @author cjbi
 */
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/storage")
public class StorageController {

  private final StorageService storageService;

  public StorageController(StorageService storageService) {
    this.storageService = storageService;
  }

  @PostMapping("/upload")
  public ResponseEntity<UploadResponse> upload(MultipartFile file) throws IOException {
    String originalFilename = file.getOriginalFilename();
    String url = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
    return ResponseEntity.ok(new UploadResponse(url));
  }

  @GetMapping("/fetch/{key:.+}")
  public ResponseEntity<Resource> fetch(@PathVariable String key) {
    if (key == null) {
      return ResponseEntity.notFound().build();
    }
    if (key.contains("../")) {
      return ResponseEntity.badRequest().build();
    }
    StorageFile storageFile = storageService.getByKey(key);
    String type = storageFile.getType();
    MediaType mediaType = MediaType.parseMediaType(type);
    Resource resource = storageService.loadAsResource(key);
    if (resource == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().contentType(mediaType).body(resource);
  }

  @GetMapping("/download/{key:.+}")
  public ResponseEntity<Resource> download(@PathVariable String key) {
    if (key == null) {
      return ResponseEntity.notFound().build();
    }
    if (key.contains("../")) {
      return ResponseEntity.badRequest().build();
    }
    StorageFile storageFile = storageService.getByKey(key);
    String type = storageFile.getType();
    MediaType mediaType = MediaType.parseMediaType(type);
    Resource file = storageService.loadAsResource(key);
    if (file == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().contentType(mediaType)
      .header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"")
      .body(file);
  }


  @DeleteMapping("/files/{key:.+}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    storageService.delete(key);
    return ResponseEntity.noContent().build();
  }

  record UploadResponse(String url) {

  }

}
