package tech.wetech.admin.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.common.annotation.SystemLog;
import tech.wetech.admin.common.base.Result;
import tech.wetech.admin.model.system.Organization;
import tech.wetech.admin.model.system.OrganizationExample;
import tech.wetech.admin.service.system.OrganizationService;
import tech.wetech.admin.controller.base.BaseController;
import tech.wetech.admin.model.system.TreeDto;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController{

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    @RequiresPermissions("organization:view")
    public String toPage(Model model) {
        OrganizationExample example = new OrganizationExample();
        example.setOrderByClause("priority");
        model.addAttribute("organizationList", organizationService.find(example));
        return "system/organization";
    }

    @ResponseBody
    @RequestMapping("/tree")
    public List<TreeDto> findOrgTree(Long pId) {
        return organizationService.findOrgTree(pId);
    }

    @ResponseBody
    @RequiresPermissions("organization:view")
    @RequestMapping(value = "{id}/load", method = RequestMethod.POST)
    public Result load(@PathVariable Long id) {
        Organization organization = organizationService.findOne(id);
        return Result.Success(organization);
    }

    @ResponseBody
    @RequiresPermissions("organization:create")
    @SystemLog("组织管理创建组织")
    @PostMapping("/create")
    public Result create(@Valid Organization organization) {
        organizationService.createOrganization(organization);
        return Result.Success();
    }

    @ResponseBody
    @RequiresPermissions("organization:update")
    @SystemLog("组织管理更新组织")
    @PostMapping("/update")
    public Result update(@Valid Organization organization) {
        organizationService.updateOrganization(organization);
        return Result.Success();
    }

    @ResponseBody
    @RequiresPermissions("organization:delete")
    @SystemLog("组织管理删除组织")
    @PostMapping("/delete")
    public Result delete(Long id) {
        organizationService.deleteOrganization(id);
        return Result.Success();
    }

}
