package tech.wetech.admin3.sys.service;

import org.springframework.core.io.Resource;
import tech.wetech.admin3.sys.model.storage.StorageConfig;
import tech.wetech.admin3.sys.model.storage.StorageFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author cjbi
 */
public interface StorageService {

  List<StorageConfig> findConfigList();

  StorageConfig createConfig(StorageConfig storageConfig);

  StorageConfig updateConfig(StorageConfig storageConfig);

  void deleteConfig(StorageConfig storageConfig);

  String store(InputStream inputStream, long contentLength, String contentType, String filename);

  void delete(String key);

  StorageFile getByKey(String key);

  Resource loadAsResource(String key);
}
