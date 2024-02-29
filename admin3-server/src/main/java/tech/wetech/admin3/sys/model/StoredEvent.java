package tech.wetech.admin3.sys.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

/**
 * @author cjbi
 */
@Entity
public class StoredEvent extends BaseEntity {

  @Lob
  @Column(length = Integer.MAX_VALUE)
  private String eventBody;
  private LocalDateTime occurredOn;
  private String typeName;
  @ManyToOne
  private User user;

  public String getEventBody() {
    return eventBody;
  }

  public void setEventBody(String eventBody) {
    this.eventBody = eventBody;
  }

  public LocalDateTime getOccurredOn() {
    return occurredOn;
  }

  public void setOccurredOn(LocalDateTime occurredOn) {
    this.occurredOn = occurredOn;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
