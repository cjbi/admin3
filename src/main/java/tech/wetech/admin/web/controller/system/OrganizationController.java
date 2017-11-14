package tech.wetech.admin.web.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.wetech.admin.model.system.Organization;
import tech.wetech.admin.model.system.OrganizationExample;
import tech.wetech.admin.service.system.OrganizationService;
import tech.wetech.admin.web.controller.base.BaseController;
import tech.wetech.admin.web.dto.JsonResult;

import javax.validation.Valid;

@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController{

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("organization:view")
    public String toPage(Model model) {
        OrganizationExample example = new OrganizationExample();
        example.setOrderByClause("priority");
        model.addAttribute("organizationList", organizationService.find(example));
        return "system/organization";
    }

    @ResponseBody
    @RequiresPermissions("organization:view")
    @RequestMapping(value = "{id}/load", method = RequestMethod.POST)
    public JsonResult load(@PathVariable Long id) {
        Organization organization = organizationService.findOne(id);
        return this.renderSuccess(organization);
    }

    @ResponseBody
    @RequiresPermissions("organization:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JsonResult create(@Valid Organization organization) {
        organizationService.createOrganization(organization);
        return this.renderSuccess();
    }

    @ResponseBody
    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResult update(@Valid Organization organization) {
        organizationService.updateOrganization(organization);
        return this.renderSuccess();
    }

    @ResponseBody
    @RequiresPermissions("organization:delete")
    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
    public JsonResult delete(@PathVariable("id") Long id) {
        organizationService.deleteOrganization(id);
        return this.renderSuccess();
    }

}
