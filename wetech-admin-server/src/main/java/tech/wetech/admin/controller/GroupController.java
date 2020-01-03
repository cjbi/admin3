package tech.wetech.admin.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.annotation.SystemLog;
import tech.wetech.admin.model.query.PageQuery;
import tech.wetech.admin.utils.Result;
import tech.wetech.admin.model.enums.GroupType;
import tech.wetech.admin.model.entity.Group;
import tech.wetech.admin.service.GroupService;
import tech.wetech.mybatis.domain.Page;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * @author cjbi
 */
@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public String groupPage(Model model) {
        model.addAttribute("groupTypeList", GroupType.values());
        return "system/group";
    }


    @ResponseBody
    @PostMapping("/delete-batch")
    @SystemLog("用户管理删除用户组")
    public Result deleteBatchByIds(@NotNull @RequestParam("id") Long[] ids) {
        Arrays.stream(ids).forEach(groupService::deleteById);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("分页查询数据")
    @ResponseBody
    public Result queryList(Group entity, PageQuery pageQuery) {
        List<Group> list = groupService.queryList(entity, pageQuery);
        return Result.success(list)
                .addExtraIfTrue(pageQuery.isCountSql(), "total", ((Page) list).getTotal());
    }

    @GetMapping("/{id}")
    @ApiOperation("查询单条数据")
    @ResponseBody
    public Result query(@NotNull @PathVariable("id") Object id) {
        return Result.success(groupService.queryById(id));
    }

    @PostMapping("/create")
    @ApiOperation("新增数据")
    @ResponseBody
    public Result create(@Validated Group entity) {
        groupService.create(entity);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新数据")
    @ResponseBody
    public Result update(@Validated Group entity) {
        groupService.updateNotNull(entity);
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除数据")
    @ResponseBody
    public Result delete(@NotNull @RequestParam("id") Long id) {
        groupService.deleteById(id);
        return Result.success();
    }

}
