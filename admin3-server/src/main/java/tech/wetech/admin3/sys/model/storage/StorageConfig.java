package tech.wetech.admin3.sys.model.storage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import tech.wetech.admin3.sys.model.BaseEntity;

/**
 * @author cjbi
 */
@Entity
public class StorageConfig extends BaseEntity {

  private String name;

  private Type type;

  @Column(name = "is_default")
  private boolean aDefault;

  private String accessKey;

  private String secretKey;

  private String endpoint;

  private String bucketName;

  private String address;

  private String storagePath;

  public enum Type {
    LOCAL, S3
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isDefault() {
    return aDefault;
  }

  public void setDefault(boolean aDefault) {
    this.aDefault = aDefault;
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
}
