package tech.wetech.admin.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.wetech.admin.core.base.PageResultSet;
import tech.wetech.admin.controller.BaseController;
import tech.wetech.admin.model.system.entity.Log;
import tech.wetech.admin.model.system.request.LogQueryDto;
import tech.wetech.admin.service.system.LogService;

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
    public String toPage(Model model) {
        return "system/log";
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("log:view")
    public PageResultSet<Log> list(LogQueryDto log) {
        return logService.findByPage(log);
    }

}
