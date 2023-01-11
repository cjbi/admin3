package tech.wetech.admin3.sys.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.wetech.admin3.sys.model.Organization;
import tech.wetech.admin3.sys.model.User;

/**
 * @author cjbi
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User user where user.organization=:organization or user.organization.parentIds like concat(:orgParentIds, '%')")
    Page<User> findOrgUsers(Pageable pageable, Organization organization, String orgParentIds);

    @Query("select count(user.id) from User user where user.organization=:organization or user.organization.parentIds like concat(:orgParentIds, '%')")
    long countOrgUsers(String orgParentIds);
}
