package tech.wetech.admin.modules.base.web;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.core.utils.Result;
import tech.wetech.admin.modules.base.query.PageQuery;
import tech.wetech.admin.modules.base.service.IService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author cjbi@outlook.com
 */
@Validated
public abstract class BaseCrudController<T> extends BaseController {

    @Autowired
    protected IService<T> service;

    @GetMapping("/list")
    @ApiOperation("分页查询数据")
    @ResponseBody
    public Result queryList(T entity, PageQuery pageQuery) {
        List<T> list = service.queryList(entity, pageQuery);
        return Result.success(service.queryList(entity, pageQuery))
                .addExtraIfTrue(pageQuery.isCountSql(), "total", ((Page) list).getTotal());
    }

    @GetMapping("/{id}")
    @ApiOperation("查询单条数据")
    @ResponseBody
    public Result query(@NotNull @PathVariable("id") Object id) {
        return Result.success(service.queryById(id));
    }

    @PostMapping("/create")
    @ApiOperation("新增数据")
    @ResponseBody
    public Result create(@Validated T entity) {
        service.create(entity);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("更新数据")
    @ResponseBody
    public Result update(@Validated T entity) {
        service.updateNotNull(entity);
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("删除数据")
    @ResponseBody
    public Result delete(@NotNull @RequestParam("id") Object id) {
        service.deleteById(id);
        return Result.success();
    }

    @PostMapping("/delete-batch")
    @ApiOperation("删除多条数据")
    public Result deleteBatchByIds(@NotNull @RequestParam("id")  Object[] ids) {
        Stream.of(ids).parallel().forEach(id -> service.deleteById(id));
        return Result.success();
    }

}
