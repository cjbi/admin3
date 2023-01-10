package tech.wetech.admin3.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.wetech.admin3.sys.model.StoredEvent;

/**
 * @author cjbi
 */
@Repository
public interface StoredEventRepository extends JpaRepository<StoredEvent, Long> {
}
