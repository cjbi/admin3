package tech.wetech.admin3.sys.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.wetech.admin3.sys.model.Role;
import tech.wetech.admin3.sys.model.User;

/**
 * @author cjbi
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query("select distinct u FROM User u join u.roles r where r.id=:roleId")
  Page<User> findRoleUsers(Long roleId, Pageable pageable);

}
