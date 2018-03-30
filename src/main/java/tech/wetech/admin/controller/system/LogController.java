package tech.wetech.admin.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.wetech.admin.common.base.Page;
import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.model.system.LogWithBLOBs;
import tech.wetech.admin.service.system.LogService;
import tech.wetech.admin.controller.base.BaseController;

/**
 * 日志控制器 Created by cjbi on 2017/12/16.
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
    public PageResultSet<LogWithBLOBs> list(Page page) {
        logService.findByPage(page);
        return logService.findByPage(page);
    }

}
