package tech.wetech.admin.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.common.annotation.SystemLog;
import tech.wetech.admin.common.base.Result;
import tech.wetech.admin.controller.base.BaseController;
import tech.wetech.admin.model.system.Resource;
import tech.wetech.admin.service.system.ResourceService;
import tk.mybatis.mapper.weekend.Weekend;

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
    @GetMapping
    public String toPage(Model model) {
        Weekend weekend = Weekend.of(Resource.class);
        weekend.setOrderByClause("priority");
        model.addAttribute("resourceList", resourceService.find(weekend));
        return "system/resource";
    }

    @ResponseBody
    @RequiresPermissions("resource:create")
    @SystemLog("资源管理创建资源")
    @PostMapping("/create")
    public Result create(@Valid Resource resource) {
        resourceService.createResource(resource);
        return Result.Success();
    }

    @ResponseBody
    @RequiresPermissions("resource:update")
    @SystemLog("资源管理更新资源")
    @PostMapping("/update")
    public Result update(@Valid Resource resource) {
        resourceService.updateResource(resource);
        return Result.Success();
    }

    @ResponseBody
    @RequiresPermissions("resource:delete")
    @SystemLog("资源管理删除资源")
    @PostMapping("/delete")
    public Result delete(@RequestParam("id") Long id) {
        resourceService.deleteResource(id);
        return Result.Success();
    }

}
