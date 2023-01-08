package tech.wetech.admin3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.wetech.admin3.model.Role;
import tech.wetech.admin3.model.User;

import java.util.Set;

/**
 * @author cjbi
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User user")
    Page<User> findUsers(Pageable pageable, Long roleId);

}
