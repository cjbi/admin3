package tech.wetech.admin.web.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import tech.wetech.admin.annotation.SystemLog;
import tech.wetech.admin.model.system.Resource;
import tech.wetech.admin.model.system.ResourceExample;
import tech.wetech.admin.service.system.ResourceService;
import tech.wetech.admin.web.controller.base.BaseController;
import tech.wetech.admin.web.dto.JsonResult;

import javax.validation.Valid;

@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController{

    @Autowired
    private ResourceService resourceService;

    @ModelAttribute("types")
    public Resource.ResourceType[] resourceTypes() {
        return Resource.ResourceType.values();
    }

    @RequiresPermissions("resource:view")
    @RequestMapping(method = RequestMethod.GET)
    public String toPage(Model model) {
        ResourceExample example = new ResourceExample();
        example.setOrderByClause("priority");
        model.addAttribute("resourceList", resourceService.find(example));
        return "system/resource";
    }

    @ResponseBody
    @RequiresPermissions("resource:create")
    @SystemLog("资源管理创建资源")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JsonResult create(@Valid Resource resource) {
        resourceService.createResource(resource);
        return this.renderSuccess();
    }

    @ResponseBody
    @RequiresPermissions("resource:update")
    @SystemLog("资源管理更新资源")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResult update(@Valid Resource resource) {
        resourceService.updateResource(resource);
        return this.renderSuccess();
    }

    @ResponseBody
    @RequiresPermissions("resource:delete")
    @SystemLog("资源管理删除资源")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public JsonResult delete(@PathVariable("id") Long id) {
        resourceService.deleteResource(id);
        return this.renderSuccess();
    }

}
