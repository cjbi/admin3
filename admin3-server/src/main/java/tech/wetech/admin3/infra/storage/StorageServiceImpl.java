package tech.wetech.admin3.infra.storage;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.wetech.admin3.common.StringUtils;
import tech.wetech.admin3.sys.model.storage.StorageConfig;
import tech.wetech.admin3.sys.model.storage.StorageFile;
import tech.wetech.admin3.sys.repository.StorageConfigRepository;
import tech.wetech.admin3.sys.repository.StorageFileRepository;
import tech.wetech.admin3.sys.service.StorageService;

import java.io.InputStream;
import java.util.List;

/**
 * @author cjbi
 */
@Service
public class StorageServiceImpl implements StorageService {

  private final StorageConfigRepository storageConfigRepository;
  private final StorageFileRepository storageFileRepository;

  public StorageServiceImpl(StorageConfigRepository storageConfigRepository, StorageFileRepository storageFileRepository) {
    this.storageConfigRepository = storageConfigRepository;
    this.storageFileRepository = storageFileRepository;
  }

  @Override
  public List<StorageConfig> findConfigList() {
    return storageConfigRepository.findAll();
  }

  @Override
  public StorageConfig createConfig(StorageConfig storageConfig) {
    storageConfigRepository.save(storageConfig);
    return storageConfigRepository.save(storageConfig);
  }

  @Override
  public StorageConfig updateConfig(StorageConfig storageConfig) {
    return storageConfigRepository.save(storageConfig);
  }

  @Override
  public void deleteConfig(StorageConfig storageConfig) {
    storageConfigRepository.delete(storageConfig);
  }

  @Override
  @Transactional
  public String store(InputStream inputStream, long contentLength, String contentType, String filename) {
    String key = generateKey(filename);
    Storage storage = getStorage();
    storage.store(inputStream, contentLength, contentType, key);
    String url = getStorage().getUrl(key);
    StorageFile storageFile = new StorageFile();
    storageFile.setKey(key);
    storageFile.setName(filename);
    storageFile.setType(contentType);
    storageFile.setSize(contentLength);
    storageFile.setUrl(url);
    storageFileRepository.save(storageFile);
    return url;
  }


  private Storage getStorage() {
    StorageConfig config = storageConfigRepository.getDefaultConfig();
    if (config.getType() == StorageConfig.Type.LOCAL) {
      return new LocalStorage(config);
    }
    return new S3Storage(config);
  }

  private String generateKey(String originalFilename) {
    int index = originalFilename.lastIndexOf('.');
    String suffix = originalFilename.substring(index);

    String key = null;
    StorageFile storageFile = null;

    do {
      key = StringUtils.getRandomString(20) + suffix;
      storageFile = storageFileRepository.getByKey(key);
    } while (storageFile != null);
    return key;
  }

  @Override
  public void delete(String keyName) {
    getStorage().delete(keyName);
  }

  @Override
  public StorageFile getByKey(String key) {
    return storageFileRepository.getByKey(key);
  }

  @Override
  public Resource loadAsResource(String key) {
    return getStorage().loadAsResource(key);
  }

}
