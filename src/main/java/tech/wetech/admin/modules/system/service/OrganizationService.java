package tech.wetech.admin.modules.system.service;

import tech.wetech.admin.modules.system.po.Organization;
import tech.wetech.admin.modules.system.dto.TreeDto;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.List;

/**
 * @author cjbi
 */
public interface OrganizationService extends IService<Organization> {

    void createOrganization(Organization organization);

    List<TreeDto> queryOrgTree(Long pId);

    List<Organization> queryAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
