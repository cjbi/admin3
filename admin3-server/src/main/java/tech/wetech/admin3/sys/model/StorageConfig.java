package tech.wetech.admin3.sys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import tech.wetech.admin3.common.Constants;
import tech.wetech.admin3.common.SessionItemHolder;
import tech.wetech.admin3.common.StringUtils;
import tech.wetech.admin3.sys.service.dto.UserinfoDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cjbi
 */
@Entity
public class StorageConfig extends BaseEntity {

  private String storageId;

  private String name;

  private Type type;

  @Column(name = "is_default")
  private Boolean isDefault;

  private String accessKey;

  private String secretKey;

  private String endpoint;

  private String bucketName;

  private String address;

  private String storagePath;

  private String createUser;

  private LocalDateTime createTime;

  @PrePersist
  protected void onCreate() {
    createTime = LocalDateTime.now();
    UserinfoDTO userInfo = (UserinfoDTO) SessionItemHolder.getItem(Constants.SESSION_CURRENT_USER);
    createUser = userInfo.username();
  }

  public enum Type {
    LOCAL, S3, OSS, OBS
  }

  public String getStorageId() {
    return storageId;
  }

  public void setStorageId(String storageId) {
    this.storageId = storageId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isDefault() {
    return isDefault != null && isDefault;
  }

  public Boolean getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Boolean aDefault) {
    isDefault = aDefault;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public String getBucketName() {
    return bucketName;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getStoragePath() {
    return storagePath;
  }

  public void setStoragePath(String storagePath) {
    this.storagePath = storagePath;
  }


  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public String getAccessKeyWithEnv() {
    return renderTemplate(accessKey);
  }

  public String getSecretKeyWithEnv() {
    return renderTemplate(secretKey);
  }

  public String getEndpointWithEnv() {
    return renderTemplate(endpoint);
  }

  public String getBucketNameWithEnv() {
    return renderTemplate(bucketName);
  }

  public String getAddressWithEnv() {
    return renderTemplate(address);
  }

  public String getStoragePathWithEnv() {
    return renderTemplate(storagePath);
  }

  private String renderTemplate(String template) {
    Map<Object, Object> attributes = new HashMap<>();
    attributes.putAll(System.getenv());
    attributes.putAll(System.getProperties());
    return StringUtils.simpleRenderTemplate(template, attributes);
  }

}
