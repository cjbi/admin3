package tech.wetech.admin.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.wetech.admin.common.Constants;
import tech.wetech.admin.mapper.system.OrganizationMapper;
import tech.wetech.admin.model.system.Organization;
import tech.wetech.admin.model.system.TreeDto;
import tech.wetech.admin.service.system.OrganizationService;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.ArrayList;
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
        return organizationMapper.insertSelective(organization);
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
        return organizationMapper.selectAll();
    }

    @Override
    public List<Organization> find(Weekend weekend) {
        return organizationMapper.selectByExample(weekend);
    }

    @Override
    public List<TreeDto> findOrgTree(Long pId) {
        if (StringUtils.isEmpty(pId)) {
            pId = Constants.ORG_ROOT_ID;
        }
        List<TreeDto> tds = new ArrayList<>();
        Weekend weekend = Weekend.of(Organization.class);
        WeekendCriteria<Organization, Object> criteria = weekend.weekendCriteria();
        criteria.andEqualTo(Organization::getParentId, pId);
        organizationMapper.selectByExample(weekend).forEach(o -> tds.add(new TreeDto(o.getId(), o.getParentId(), o.getName(), Boolean.FALSE.equals(o.getLeaf()), o)));
        return tds;
    }

    @Override
    public List<Organization> findAllWithExclude(Organization exclude) {
        Weekend weekend = Weekend.of(Organization.class);
        WeekendCriteria<Organization, Object> criteria = weekend.weekendCriteria();
        criteria.andNotEqualTo(Organization::getId, exclude.getId()).andNotLike(Organization::getParentIds, exclude.makeSelfAsParentIds() + "%");
        return organizationMapper.selectByExample(weekend);
    }

    @Override
    public void move(Organization source, Organization target) {
        organizationMapper.updateByPrimaryKeySelective(target);
        organizationMapper.updateSalefParentIds(source.makeSelfAsParentIds());
    }
}
