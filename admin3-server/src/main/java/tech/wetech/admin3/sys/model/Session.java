package tech.wetech.admin3.sys.model;

import jakarta.persistence.*;
import tech.wetech.admin3.common.JsonUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会话
 *
 * @author cjbi
 */
@Entity
public class Session extends BaseEntity {

  @Column(nullable = false)
  private String token;
  @ManyToOne
  private UserCredential credential;
  @Column(nullable = false)
  private LocalDateTime expireTime;
  @Column(nullable = false)
  private LocalDateTime lastLoginTime;

  private LocalDateTime lastModifiedTime;

  @Lob
  @Column(length = Integer.MAX_VALUE)
  private String data;

  @Transient
  private boolean active;


  public static Session of(Long id, String token, UserCredential credential, Serializable data, LocalDateTime expireTime) {
    Session authSession = new Session();
    authSession.setId(id);
    authSession.setToken(token);
    authSession.setCredential(credential);
    authSession.setExpireTime(expireTime);
    authSession.setLastLoginTime(LocalDateTime.now());
    authSession.setActive(true);
    authSession.setData(JsonUtils.stringify(data));
    return authSession;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UserCredential getCredential() {
    return credential;
  }

  public void setCredential(UserCredential credential) {
    this.credential = credential;
  }

  public LocalDateTime getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(LocalDateTime expireTime) {
    this.expireTime = expireTime;
  }

  public LocalDateTime getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(LocalDateTime lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public LocalDateTime getLastModifiedTime() {
    return lastModifiedTime;
  }
}
