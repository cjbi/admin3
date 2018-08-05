package tech.wetech.admin.modules.system.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.wetech.admin.core.utils.BaseController;
import tech.wetech.admin.core.utils.PageResultSet;
import tech.wetech.admin.modules.system.po.Log;
import tech.wetech.admin.modules.system.query.LogQuery;
import tech.wetech.admin.modules.system.service.LogService;

/**
 *
 * @author cjbi
 * @date 2017/12/16
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController{

    @Autowired
    private LogService logService;

    @RequiresPermissions("log:view")
    @GetMapping
    public String page(Model model) {
        return "system/log";
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("log:view")
    public PageResultSet<Log> list(LogQuery log) {
        return logService.findByPage(log);
    }

}
