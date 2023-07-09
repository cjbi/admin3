package tech.wetech.admin3.sys.model;

import jakarta.persistence.*;
import tech.wetech.admin3.common.Constants;
import tech.wetech.admin3.common.SessionItemHolder;
import tech.wetech.admin3.sys.service.dto.UserinfoDTO;

import java.time.LocalDateTime;

/**
 * @author cjbi
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"key"})})
public class StorageFile extends BaseEntity {

  /**
   * 文件的唯一索引
   */
  @Column(name = "`key`")
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

  /**
   * 文件访问链接
   */
  private String url;

  private String createUser;

  private LocalDateTime createTime;

  @PrePersist
  protected void onCreate() {
    createTime = LocalDateTime.now();
    UserinfoDTO userInfo = (UserinfoDTO) SessionItemHolder.getItem(Constants.SESSION_CURRENT_USER);
    createUser = userInfo.username();
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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
}
