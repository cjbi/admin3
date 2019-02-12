package tech.wetech.admin.modules.system.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.wetech.admin.core.utils.Result;
import tech.wetech.admin.modules.base.query.PageQuery;
import tech.wetech.admin.modules.base.web.BaseCrudController;
import tech.wetech.admin.modules.system.po.Log;
/**
 * @author cjbi
 * @date 2017/12/16
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseCrudController<Log> {

    @RequiresPermissions("log:view")
    @GetMapping
    public String logPage(Model model) {
        return "system/log";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("log:view")
    @Override
    public Result queryList(Log log, PageQuery pageQuery) {
        return super.queryList(log, pageQuery);
    }

}
