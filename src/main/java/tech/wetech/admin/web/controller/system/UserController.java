package tech.wetech.admin.web.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.wetech.admin.model.system.User;
import tech.wetech.admin.service.system.OrganizationService;
import tech.wetech.admin.service.system.RoleService;
import tech.wetech.admin.service.system.UserService;
import tech.wetech.admin.web.controller.base.BaseController;
import tech.wetech.admin.web.dto.DataTableMap;
import tech.wetech.admin.web.dto.JsonResult;
import tech.wetech.admin.web.dto.Page;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("user:view")
    public String toPage(Model model) {
        setCommonData(model);
        return "system/user";
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("user:view")
    public Object list(Page page) {
        userService.listByPage(page);
        return DataTableMap.getMapData(page);
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @RequiresPermissions("user:create")
    public JsonResult create(@Valid  User user) {
        userService.createUser(user);
        return this.renderSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("user:update")
    public JsonResult update(User user) {
        userService.updateUser(user);
        return this.renderSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequiresPermissions("user:delete")
    public JsonResult delete(Long[] ids) {
        for(Long id: ids) {
            userService.deleteUser(id);
        }
        return this.renderSuccess();
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/change/password", method = RequestMethod.POST)
    public JsonResult changePassword(@PathVariable("id") Long id, String newPassword) {
        userService.changePassword(id, newPassword);
        return this.renderSuccess();
    }

    private void setCommonData(Model model) {
        model.addAttribute("organizationList", organizationService.findAll());
        model.addAttribute("roleList", roleService.findAll());
    }

}
