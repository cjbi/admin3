<#assign dateTime = .now>
package ${package};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import ${servicePackage}.${serviceName};
import ${tableClass.fullClassName};
import ${tableClass.fullClassName}Example;
import tech.wetech.admin.web.controller.base.BaseController;
import tech.wetech.admin.web.dto.JsonResult;

/**
*
* Created by wetech-generator on ${dateTime?date}.
* @author
*/
@Controller
@RequestMapping("/${tableClass.variableName}")
public class ${controllerName} extends BaseController {

    @Autowired
    private ${serviceName} ${serviceName?uncap_first};

    @RequestMapping(method = RequestMethod.GET)
    public String toPage(Model model) {
        return "${moduleName}/${jspName}";
    }

    @ResponseBody
    @RequestMapping("/list")
    public DataTableModel<${tableClass.shortClassName}> list(DataTableModel<${tableClass.shortClassName}> model) {
                ${serviceName?uncap_first}.findByPage(model);
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public JsonResult create(${tableClass.shortClassName} ${tableClass.variableName}) {
        ${tableClass.variableName}Service.create${tableClass.shortClassName}(${tableClass.variableName});
        return this.renderSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResult update(@Valid ${tableClass.shortClassName} ${tableClass.variableName}) {
        ${serviceName?uncap_first}.update${tableClass.shortClassName}(${tableClass.variableName});
        return this.renderSuccess();
    }

    @ResponseBody
    @RequestMapping(value = "{id}/delete", method = RequestMethod.POST)
    public JsonResult delete(@PathVariable("id") Long id) {
        ${serviceName?uncap_first}.delete${tableClass.shortClassName}(id);
        return this.renderSuccess();
    }

}
