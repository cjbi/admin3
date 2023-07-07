package tech.wetech.admin3.sys.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author cjbi
 */
@Entity
public class Organization extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Type type;

  @ManyToOne(fetch = FetchType.LAZY)
  private Organization parent;

  private String parentIds;

  @OrderBy("id")
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
  private Set<Organization> children = new LinkedHashSet<>();

  public String makeSelfAsParentIds() {
    return getParentIds() + getId() + "/";
  }

  public String getFullName() {
    return concatOrgName(this);
  }

  private String concatOrgName(Organization org) {
    if (org.getParent() != null) {
      return concatOrgName(org.getParent()).concat("-").concat(org.getName());
    }
    return org.getName();
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Organization getParent() {
    return parent;
  }

  public void setParent(Organization parent) {
    this.parent = parent;
  }

  public String getParentIds() {
    return parentIds;
  }

  public void setParentIds(String parentIds) {
    this.parentIds = parentIds;
  }

  public Set<Organization> getChildren() {
    return children;
  }

  public void setChildren(Set<Organization> children) {
    this.children = children;
  }

  public enum Type {
    /**
     * 部门
     */
    DEPARTMENT,
    /**
     * 岗位
     */
    JOB
  }
}
