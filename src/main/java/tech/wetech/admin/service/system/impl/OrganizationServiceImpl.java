package tech.wetech.admin.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.wetech.admin.mapper.system.OrganizationMapper;
import tech.wetech.admin.model.system.Organization;
import tech.wetech.admin.model.system.OrganizationExample;
import tech.wetech.admin.service.system.OrganizationService;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public int createOrganization(Organization organization) {
        Organization parent = findOne(organization.getParentId());
        organization.setParentIds(parent.makeSelfAsParentIds());
        organization.setAvailable(true);
        return organizationMapper.insert(organization);
    }

    @Override
    public int updateOrganization(Organization organization) {
        return organizationMapper.updateByPrimaryKeySelective(organization);
    }

    @Override
    public void deleteOrganization(Long organizationId) {
        organizationMapper.deleteByPrimaryKey(organizationId);
    }

    @Override
    public Organization findOne(Long organizationId) {
        return organizationMapper.selectByPrimaryKey(organizationId);
    }

    @Override
    public List<Organization> findAll() {
        return organizationMapper.selectByExample(new OrganizationExample());
    }

    @Override
    public List<Organization> findAllWithExclude(Organization excludeOraganization) {
        OrganizationExample example = new OrganizationExample();
        example.or().andIdNotEqualTo(excludeOraganization.getId()).andParentIdsNotLike(excludeOraganization.makeSelfAsParentIds() + "%");
        return organizationMapper.selectByExample(example);
    }

    @Override
    public void move(Organization source, Organization target) {
        OrganizationExample moveSourceExample = new OrganizationExample();
        moveSourceExample.or().andIdEqualTo(source.getId());
        organizationMapper.updateByExampleSelective(target, moveSourceExample);
        organizationMapper.updateSalefParentIds(source.makeSelfAsParentIds());
    }
}
