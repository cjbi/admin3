package tech.wetech.admin.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.config.aspect.SystemLog;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.entity.Resource;
import tech.wetech.admin.model.enumeration.ResourceType;
import tech.wetech.admin.service.ResourceService;

/**
 * @author cjbi
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @ModelAttribute("/types")
    public ResourceType[] resourceTypes() {
        return ResourceType.values();
    }

    @RequiresPermissions("resource:view")
    @GetMapping
    public String resourcePage(Model model) {
        model.addAttribute("resourceList", resourceService.findOrderByPriority());
        return "system/resource";
    }

    @ResponseBody
    @RequiresPermissions("resource:create")
    @SystemLog("资源管理创建资源")
    @PostMapping("/create")
    public Result create(@Validated Resource resource) {
        resourceService.createResource(resource);
        return Result.success();
    }

    @ResponseBody
    @RequiresPermissions("resource:update")
    @SystemLog("资源管理更新资源")
    @PostMapping("/update")
    public Result update(@Validated Resource resource) {
        resourceService.updateNotNull(resource);
        return Result.success();
    }

    @ResponseBody
    @RequiresPermissions("resource:delete")
    @SystemLog("资源管理删除资源")
    @PostMapping("/delete")
    public Result delete(@RequestParam("id") Long id) {
        resourceService.deleteById(id);
        return Result.success();
    }

}
