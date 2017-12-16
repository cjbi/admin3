package tech.wetech.admin.web.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tech.wetech.admin.model.system.LogWithBLOBs;
import tech.wetech.admin.service.system.LogService;
import tech.wetech.admin.web.dto.DataTableModel;

import javax.xml.crypto.Data;

/**
 * 日志控制器
 * Created by cjbi on 2017/12/16.
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @RequiresPermissions("log:view")
    @RequestMapping(method = RequestMethod.GET)
    public String toPage(Model model) {
        return "system/log";
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("role:view")
    public DataTableModel<LogWithBLOBs> list(DataTableModel<LogWithBLOBs> model) {
        logService.list(model);
        return model;
    }

}
