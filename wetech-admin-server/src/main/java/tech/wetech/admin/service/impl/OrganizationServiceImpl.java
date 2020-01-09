package tech.wetech.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tech.wetech.admin.model.constant.Constants;
import tech.wetech.admin.service.BaseService;
import tech.wetech.admin.model.dto.TreeDTO;
import tech.wetech.admin.mapper.OrganizationMapper;
import tech.wetech.admin.model.entity.Organization;
import tech.wetech.admin.service.OrganizationService;
import tech.wetech.mybatis.example.Criteria;
import tech.wetech.mybatis.example.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationServiceImpl extends BaseService<Organization> implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    @Transactional
    public void createOrganization(Organization organization) {
        Organization parent = organizationMapper.createCriteria().andEqualTo(Organization::getParentId, organization.getParentId()).selectOne();
        organization.setParentIds(parent.makeSelfAsParentIds());
        organization.setAvailable(true);
        organizationMapper.insertSelective(organization);
    }

    @Override
    public List<TreeDTO> queryOrgTree(Long pId) {
        if (StringUtils.isEmpty(pId)) {
            pId = Constants.ORG_ROOT_ID;
        }
        List<TreeDTO> tds = new ArrayList<>();
        Example<Organization> example = Example.of(Organization.class);
        Criteria<Organization> criteria = example.createCriteria();
        criteria.andEqualTo(Organization::getParentId, pId);
        organizationMapper.selectByExample(example).forEach(o -> tds.add(new TreeDTO(o.getId(), o.getParentId(), o.getName(), Boolean.FALSE.equals(o.getLeaf()), o)));
        return tds;
    }

    @Override
    public List<Organization> queryAllWithExclude(Organization exclude) {
        Example<Organization> example = Example.of(Organization.class);
        example.createCriteria().andNotEqualTo(Organization::getId, exclude.getId()).andNotLike(Organization::getParentIds, exclude.makeSelfAsParentIds() + "%");
        return organizationMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public void move(Organization source, Organization target) {
        organizationMapper.updateByPrimaryKeySelective(target);
        organizationMapper.updateSalefParentIds(source.makeSelfAsParentIds());
    }
}
