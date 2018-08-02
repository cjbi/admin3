package tech.wetech.admin.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.common.annotation.SystemLog;
import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.common.base.Result;
import tech.wetech.admin.model.system.entity.Group;
import tech.wetech.admin.service.system.GroupService;

import javax.validation.Valid;
import java.util.Arrays;

/**
 * @author cjbi
 */
@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public String page(Model model) {
        setCommonData(model);
        return "system/group";
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageResultSet<Group> list(Group group) {
        return groupService.findByPage(group);
    }

    @ResponseBody
    @PostMapping("/create")
    @SystemLog("用户管理创建用户组")
    public Result create(@Valid Group group) {
        groupService.createGroup(group);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/update")
    @SystemLog("用户管理更新用户组")
    public Result update(@Valid Group group) {
        groupService.updateGroup(group);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/delete")
    @SystemLog("用户管理删除用户组")
    public Result delete(@RequestParam("id") Long[] ids) {
        Arrays.asList(ids).forEach(id -> groupService.deleteGroup(id));
        return Result.success();
    }

    private void setCommonData(Model model) {
        model.addAttribute("groupTypeList", Group.GroupType.values());
    }

}
