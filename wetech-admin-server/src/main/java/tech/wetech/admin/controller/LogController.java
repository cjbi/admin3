package tech.wetech.admin.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.model.PageWrapper;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.entity.Log;
import tech.wetech.admin.model.query.PageQuery;
import tech.wetech.admin.service.LogService;
import tech.wetech.mybatis.domain.Page;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author cjbi
 * @date 2017/12/16
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @RequiresPermissions("log:view")
    @GetMapping
    public String logPage(Model model) {
        return "system/log";
    }

    @Autowired
    private LogService logService;

    @GetMapping("/list")
    @ApiOperation("分页查询数据")
    @ResponseBody
    public Result queryList(Log entity, PageQuery pageQuery) {
        List<Log> list = logService.queryList(entity, pageQuery);
        return Result.success(new PageWrapper((Page) list));
    }

    @GetMapping("/{id}")
    @ApiOperation("查询单条数据")
    @ResponseBody
    public Result query(@NotNull @PathVariable("id") Object id) {
        return Result.success(logService.queryById(id));
    }

    @PostMapping("/create")
    @ApiOperation("新增数据")
    @ResponseBody
    public Result create(@Validated Log entity) {
        logService.create(entity);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新数据")
    @ResponseBody
    public Result update(@Validated Log entity) {
        logService.updateNotNull(entity);
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除数据")
    @ResponseBody
    public Result delete(@NotNull @RequestParam("id") Long id) {
        logService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/delete-batch")
    @ApiOperation("删除多条数据")
    public Result deleteBatchByIds(@NotNull @RequestParam("id") Long[] ids) {
        Stream.of(ids).parallel().forEach(logService::deleteById);
        return Result.success();
    }

}
