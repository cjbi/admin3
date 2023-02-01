package tech.wetech.admin3.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.wetech.admin3.sys.model.Organization;

import java.util.List;

/**
 * @author cjbi
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

  @Query("from Organization org where org.parent.id=:parentId")
  List<Organization> findByParentId(Long parentId);
}
