package tech.wetech.admin3.sys.service.dto;

import tech.wetech.admin3.sys.model.StorageFile;

import java.time.LocalDateTime;

/**
 * @author cjbi
 */
public class StorageFileDTO extends StorageFile {

  private String key;

  /**
   * 文件名
   */
  private String name;

  /**
   * 文件类型
   */
  private String type;

  /**
   * 文件大小
   */
  private Long size;

  private String createUser;

  private LocalDateTime createTime;

  private String storageId;

  private String url;

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public void setKey(String key) {
    this.key = key;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public void setType(String type) {
    this.type = type;
  }

  @Override
  public Long getSize() {
    return size;
  }

  @Override
  public void setSize(Long size) {
    this.size = size;
  }

  @Override
  public String getCreateUser() {
    return createUser;
  }

  @Override
  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  @Override
  public LocalDateTime getCreateTime() {
    return createTime;
  }

  @Override
  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  @Override
  public String getStorageId() {
    return storageId;
  }

  @Override
  public void setStorageId(String storageId) {
    this.storageId = storageId;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
