package tech.wetech.admin3.sys.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

/**
 * @author cjbi
 */
@Entity
public class StoredEvent extends EntityBase {

    @Lob
    @Column(length = Integer.MAX_VALUE)
    private String eventBody;
    private LocalDateTime occurredOn;
    private String typeName;

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
}
