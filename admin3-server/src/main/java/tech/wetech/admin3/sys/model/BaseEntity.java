package tech.wetech.admin3.sys.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

/**
 * Base class for an entity, as explained in the book "Domain Driven Design".
 * All entities in this project have an identity attribute with type Long and
 * name id. Inspired by the DDD Sample project.
 *
 * @author Christoph Knabe
 * @author cjbi
 * @see <a href=
 * "https://github.com/citerus/dddsample-core/blob/master/src/main/java/se/citerus/dddsample/domain/shared/Entity.java">Entity
 * in the DDD Sample</a>
 * @since 2017-03-06
 */
@MappedSuperclass
public abstract class BaseEntity {

  /**
   * This identity field has the wrapper class type Long so that an entity which
   * has not been saved is recognizable by a null identity.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Returns the identity of this entity object.
   *
   * @return the identity of this entity object
   */
  public Long getId() {
    return id;
  }

  @Override
  public boolean equals(final Object object) {
    if (!(object instanceof BaseEntity)) {
      return false;
    }
    if (!getClass().equals(object.getClass())) {
      return false;
    }
    final BaseEntity that = (BaseEntity) object;
    _checkIdentity(this);
    _checkIdentity(that);
    return this.id.equals(that.getId());
  }

  /**
   * Checks the passed entity, if it has an identity. It gets an identity only by
   * saving.
   *
   * @param entity the entity to be checked
   * @throws IllegalStateException the passed entity does not have the identity
   *                               attribute set.
   */
  private void _checkIdentity(final BaseEntity entity) {
    if (entity.getId() == null) {
      throw new IllegalStateException("Comparison identity missing in entity: " + entity);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getId());
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + "<" + getId() + ">";
  }

}
