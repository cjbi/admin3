package tech.wetech.admin.modules.system.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.core.utils.BaseController;
import tech.wetech.admin.core.annotation.SystemLog;
import tech.wetech.admin.core.utils.Result;
import tech.wetech.admin.modules.system.po.Resource;
import tech.wetech.admin.modules.system.enums.ResourceType;
import tech.wetech.admin.modules.system.service.ResourceService;
import tk.mybatis.mapper.weekend.Weekend;

import javax.validation.Valid;

/**
 * @author cjbi
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController{

    @Autowired
    private ResourceService resourceService;

    @ModelAttribute("types")
    public ResourceType[] resourceTypes() {
        return ResourceType.values();
    }

    @RequiresPermissions("resource:view")
    @GetMapping
    public String page(Model model) {
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
        return Result.success();
    }

    @ResponseBody
    @RequiresPermissions("resource:update")
    @SystemLog("资源管理更新资源")
    @PostMapping("/update")
    public Result update(@Valid Resource resource) {
        resourceService.updateResource(resource);
        return Result.success();
    }

    @ResponseBody
    @RequiresPermissions("resource:delete")
    @SystemLog("资源管理删除资源")
    @PostMapping("/delete")
    public Result delete(@RequestParam("id") Long id) {
        resourceService.deleteResource(id);
        return Result.success();
    }

}
