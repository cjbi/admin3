package tech.wetech.admin3.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.wetech.admin3.sys.model.Organization;

/**
 * @author cjbi
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {


}
