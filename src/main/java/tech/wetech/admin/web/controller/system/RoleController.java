package tech.wetech.admin.web.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.wetech.admin.common.utils.DataTableMap;
import tech.wetech.admin.model.system.Role;
import tech.wetech.admin.service.system.ResourceService;
import tech.wetech.admin.service.system.RoleService;
import tech.wetech.admin.web.controller.base.BaseController;
import tech.wetech.admin.web.dto.JsonResult;
import tech.wetech.admin.web.dto.Page;

import javax.validation.Valid;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("organization:view")
    public String toPage(Model model) {
        setCommonData(model);
        return "system/role";
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("organization:view")
    public Object list(Page page) {
        roleService.listByPage(page);
        return DataTableMap.getMapData(page);
    }

    @ResponseBody
    @RequiresPermissions("role:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JsonResult create(@Valid Role role) {
        roleService.createRole(role);
        return this.renderSuccess();
    }

    @ResponseBody
    @RequiresPermissions("role:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResult update(@Valid Role role) {
        roleService.updateRole(role);
        return this.renderSuccess();
    }

    @ResponseBody
    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JsonResult delete(Long[] ids) {
        for(Long id : ids) {
            roleService.deleteRole(id);
        }
        return this.renderSuccess();
    }

    private void setCommonData(Model model) {
        model.addAttribute("resourceList", resourceService.findAll());
    }

}
