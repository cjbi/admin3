package tech.wetech.admin3.infra.storage;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.wetech.admin3.common.StringUtils;
import tech.wetech.admin3.sys.exception.StorageException;
import tech.wetech.admin3.sys.model.StorageConfig;
import tech.wetech.admin3.sys.model.StorageFile;
import tech.wetech.admin3.sys.repository.StorageConfigRepository;
import tech.wetech.admin3.sys.repository.StorageFileRepository;
import tech.wetech.admin3.sys.service.StorageService;

import java.io.InputStream;
import java.util.List;

import static tech.wetech.admin3.common.CommonResultStatus.FAIL;

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
  public StorageConfig getConfig(Long id) {
    return storageConfigRepository.findById(id)
      .orElseThrow(() -> new StorageException(FAIL, "存储配置不存在"));
  }

  @Override
  @Transactional
  public StorageConfig createConfig(String name, StorageConfig.Type type, String accessKey, String endpoint, String bucketName, String address, String storagePath) {
    return storageConfigRepository.save(buildConfig(null, name, type, accessKey, endpoint, bucketName, address));
  }

  private StorageConfig buildConfig(Long id, String name, StorageConfig.Type type, String accessKey, String endpoint, String bucketName, String address) {
    StorageConfig storageConfig = new StorageConfig();
    storageConfig.setId(id);
    storageConfig.setName(name);
    storageConfig.setIsDefault(false);
    storageConfig.setType(type);
    storageConfig.setAccessKey(name);
    storageConfig.setSecretKey(accessKey);
    storageConfig.setEndpoint(endpoint);
    storageConfig.setBucketName(bucketName);
    storageConfig.setAddress(address);
    storageConfig.setStoragePath(endpoint);
    return storageConfig;
  }

  @Override
  @Transactional
  public StorageConfig updateConfig(Long id, String name, StorageConfig.Type type, String accessKey, String endpoint, String bucketName, String address, String storagePath) {
    return storageConfigRepository.save(buildConfig(id, name, type, accessKey, endpoint, bucketName, address));
  }

  @Override
  @Transactional
  public void deleteConfig(StorageConfig storageConfig) {
    if (storageConfig.isDefault()) {
      throw new StorageException(FAIL, "不能删除默认配置");
    }
    storageConfigRepository.delete(storageConfig);
  }

  @Override
  @Transactional
  public void markAsDefault(StorageConfig storageConfig) {
    storageConfig.setIsDefault(true);
    List<StorageConfig> configList = findConfigList();
    for (StorageConfig record : configList) {
      if (record.equals(storageConfig)) {
        record.setIsDefault(true);
      } else {
        record.setIsDefault(false);
      }
    }
    storageConfigRepository.saveAll(configList);
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

  private String generateKey(String filename) {
    int index = filename.lastIndexOf('.');
    String suffix = filename.substring(index);

    String key = null;
    StorageFile storageFile = null;

    do {
      key = StringUtils.getRandomString(20) + suffix;
      storageFile = storageFileRepository.getByKey(key);
    } while (storageFile != null);
    return key;
  }

  @Override
  public void delete(String key) {
    getStorage().delete(key);
    storageFileRepository.deleteByKey(key);
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
