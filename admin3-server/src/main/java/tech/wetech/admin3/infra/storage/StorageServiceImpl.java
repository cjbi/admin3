package tech.wetech.admin3.infra.storage;

import org.springframework.beans.BeanUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.wetech.admin3.common.DomainEventPublisher;
import tech.wetech.admin3.common.NanoId;
import tech.wetech.admin3.sys.event.StorageConfigCreated;
import tech.wetech.admin3.sys.event.StorageConfigDeleted;
import tech.wetech.admin3.sys.event.StorageConfigMarkedAsDefault;
import tech.wetech.admin3.sys.event.StorageConfigUpdated;
import tech.wetech.admin3.sys.exception.StorageException;
import tech.wetech.admin3.sys.model.StorageConfig;
import tech.wetech.admin3.sys.model.StorageFile;
import tech.wetech.admin3.sys.repository.StorageConfigRepository;
import tech.wetech.admin3.sys.repository.StorageFileRepository;
import tech.wetech.admin3.sys.service.StorageService;
import tech.wetech.admin3.sys.service.dto.StorageFileDTO;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

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
  public StorageConfig createConfig(String name, StorageConfig.Type type, String endpoint, String accessKey, String secretKey, String bucketName, String address, String storagePath) {
    StorageConfig config = storageConfigRepository.save(buildConfig(null, name, type, endpoint, bucketName, accessKey, secretKey, address, storagePath));
    DomainEventPublisher.instance().publish(new StorageConfigCreated(config));
    return config;
  }

  private StorageConfig buildConfig(Long id, String name, StorageConfig.Type type, String endpoint, String bucketName, String accessKey, String secretKey, String address, String storagePath) {
    StorageConfig storageConfig;
    if (id != null) {
      storageConfig = getConfig(id);
      storageConfig.setId(id);
    } else {
      storageConfig = new StorageConfig();
      storageConfig.setStorageId(NanoId.randomNanoId());
    }
    storageConfig.setName(name);
    storageConfig.setType(type);
    storageConfig.setAccessKey(accessKey);
    storageConfig.setSecretKey(secretKey);
    storageConfig.setEndpoint(endpoint);
    storageConfig.setBucketName(bucketName);
    storageConfig.setAddress(address);
    storageConfig.setStoragePath(storagePath);
    return storageConfig;
  }

  @Override
  @Transactional
  public StorageConfig updateConfig(Long id, String name, StorageConfig.Type type, String endpoint, String accessKey, String secretKey, String bucketName, String address, String storagePath) {
    StorageConfig config = storageConfigRepository.save(buildConfig(id, name, type, endpoint, bucketName, accessKey, secretKey, address, storagePath));
    DomainEventPublisher.instance().publish(new StorageConfigUpdated(config));
    return config;
  }

  @Override
  @Transactional
  public void deleteConfig(StorageConfig storageConfig) {
    if (storageConfig.isDefault()) {
      throw new StorageException(FAIL, "不能删除默认配置");
    }
    storageConfigRepository.delete(storageConfig);
    DomainEventPublisher.instance().publish(new StorageConfigDeleted(storageConfig));
  }

  @Override
  @Transactional
  public void markAsDefault(StorageConfig storageConfig) {
    storageConfig.setIsDefault(true);
    List<StorageConfig> configList = findConfigList();
    for (StorageConfig record : configList) {
      record.setIsDefault(record.equals(storageConfig));
    }
    storageConfigRepository.saveAll(configList);
    DomainEventPublisher.instance().publish(new StorageConfigMarkedAsDefault(storageConfig));
  }


  @Override
  @Transactional
  public StorageFileDTO store(String storageId, InputStream inputStream, long contentLength, String contentType, String filename) {
    String key = generateKey(filename);
    Storage storage;
    if (storageId != null) {
      storage = getStorage(storageId);
    } else {
      storage = getStorage();
    }
    storage.store(inputStream, contentLength, contentType, key);
    StorageFile storageFile = new StorageFile();
    storageFile.setKey(key);
    storageFile.setName(filename);
    storageFile.setType(contentType);
    storageFile.setSize(contentLength);
    storageFile.setStorageId(storage.getId());
    storageFile = storageFileRepository.save(storageFile);
    String url = storage.getUrl(key);
    StorageFileDTO dto = new StorageFileDTO();
    BeanUtils.copyProperties(storageFile, dto);
    dto.setUrl(url);
    return dto;
  }


  private Storage getStorage() {
    StorageConfig config = storageConfigRepository.getDefaultConfig();
    if (config.getType() == StorageConfig.Type.LOCAL) {
      return new LocalStorage(config);
    }
    return new S3Storage(config);
  }

  private Storage getStorage(String storageId) {
    StorageConfig config = storageConfigRepository.getByStorageId(storageId);
    if (config.getType() == StorageConfig.Type.LOCAL) {
      return new LocalStorage(config);
    }
    return new S3Storage(config);
  }

  private String generateKey(String filename) {
    String key = null;
    StorageFile storageFile = null;
    do {
      key = getRandomString(5) + "_" + filename;
      storageFile = storageFileRepository.getByKey(key);
    } while (storageFile != null);
    return key;
  }

  public String getRandomString(Integer num) {
    String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < num; i++) {
      int number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }

  @Override
  public void delete(String key) {
    StorageFile storageFile = storageFileRepository.getByKey(key);
    getStorage(storageFile.getStorageId()).delete(key);
    storageFileRepository.delete(storageFile);
  }

  @Override
  public StorageFile getByKey(String key) {
    return storageFileRepository.getByKey(key);
  }

  @Override
  public Resource loadAsResource(String key) {
    StorageFile file = storageFileRepository.getByKey(key);
    Storage storage = getStorage(file.getStorageId());
    InputStream is = storage.getFileContent(key);
    return new InputStreamResource(is);
  }

}
