package tech.wetech.admin3.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.wetech.admin3.common.authz.RequiresPermissions;
import tech.wetech.admin3.sys.model.StorageConfig;
import tech.wetech.admin3.sys.model.StorageConfig.Type;
import tech.wetech.admin3.sys.model.StorageFile;
import tech.wetech.admin3.sys.service.StorageService;
import tech.wetech.admin3.sys.service.dto.StorageFileDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

  @GetMapping("/configs")
  @RequiresPermissions("storage:view")
  public ResponseEntity<List<StorageConfig>> findConfigList() {
    List<StorageConfig> configList = storageService.findConfigList();
    return ResponseEntity.ok(configList);
  }

  @PostMapping("/configs")
  @RequiresPermissions("storage:create")
  public ResponseEntity<StorageConfig> createStorageConfig(@RequestBody StorageConfigRequest request) {
    StorageConfig config = storageService.createConfig(
      request.name(),
      request.type(),
      request.endpoint(),
      request.accessKey(),
      request.secretKey(),
      request.bucketName(),
      request.address(),
      request.storagePath()
    );
    return new ResponseEntity<>(config, HttpStatus.CREATED);
  }

  @PutMapping("/configs/{id}")
  @RequiresPermissions("storage:update")
  public ResponseEntity<StorageConfig> updateStorageConfig(@PathVariable("id") Long id, @RequestBody StorageConfigRequest request) {
    StorageConfig config = storageService.updateConfig(
      id,
      request.name(),
      request.type(),
      request.endpoint(),
      request.accessKey(),
      request.secretKey(),
      request.bucketName(),
      request.address(),
      request.storagePath()
    );
    return ResponseEntity.ok(config);
  }

  @DeleteMapping("/configs/{id}")
  @RequiresPermissions("storage:delete")
  public ResponseEntity<Void> deleteStorageConfig(@PathVariable Long id) {
    StorageConfig config = storageService.getConfig(id);
    storageService.deleteConfig(config);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/configs/{id}:markAsDefault")
  @RequiresPermissions("storage:markAsDefault")
  public ResponseEntity<Void> markAsDefaultStorageConfig(@PathVariable Long id) {
    StorageConfig config = storageService.getConfig(id);
    storageService.markAsDefault(config);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/upload")
  public ResponseEntity<List<StorageFileDTO>> upload(@RequestParam(value = "storageId", required = false) String storageId,
                                                     @RequestParam("files") MultipartFile[] files) throws IOException {
    List<StorageFileDTO> responses = new ArrayList<>();
    for (MultipartFile file : files) {
      String originalFilename = file.getOriginalFilename();
      StorageFileDTO storageFile = storageService.store(storageId, file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
      responses.add(storageFile);
    }
    return ResponseEntity.ok(responses);
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
    if (storageFile == null) {
      return ResponseEntity.notFound().build();
    }
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
    if (storageFile == null) {
      return ResponseEntity.notFound().build();
    }
    String type = storageFile.getType();
    MediaType mediaType = MediaType.parseMediaType(type);
    Resource file = storageService.loadAsResource(key);
    if (file == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().contentType(mediaType)
      .header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + storageFile.getName() + "\"")
      .body(file);
  }


  @DeleteMapping("/files/{key:.+}")
  public ResponseEntity<Void> delete(@PathVariable String key) {
    storageService.delete(key);
    return ResponseEntity.noContent().build();
  }

  record StorageConfigRequest(String name,
                              Type type,
                              String endpoint,
                              String accessKey,
                              String secretKey,
                              String bucketName,
                              String address,
                              String storagePath
  ) {

  }

}
