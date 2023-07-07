package tech.wetech.admin3.sys.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.DETACH;

/**
 * 角色
 *
 * @author cjbi
 */
@Entity
public class Role extends BaseEntity {

  @Column(unique = true, nullable = false)
  private String name;

  private String description;

  private Boolean available = Boolean.FALSE;

  @ManyToMany(fetch = FetchType.LAZY, cascade = DETACH)
  @JoinTable(name = "role_resource",
    joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "resource_id", referencedColumnName = "id"))
  private Set<Resource> resources = new LinkedHashSet<>();

  @ManyToMany(fetch = FetchType.LAZY, cascade = DETACH)
  @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
  private Set<User> users = new LinkedHashSet<>();

  public Role() {

  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Boolean getAvailable() {
    return available;
  }


  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setAvailable(Boolean available) {
    this.available = available;
  }

  public Set<Resource> getResources() {
    return resources;
  }

  public void setResources(Set<Resource> resources) {
    this.resources = resources;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }
}
