package tech.wetech.admin.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tech.wetech.admin.web.controller.base.BaseController;

/**
 * 代码生成器控制层
 * <p>
 * Created by cjbi on 2018/1/8.
 */
@Controller
@RequestMapping("/generator")
public class GeneratorController extends BaseController{

    @RequestMapping(method = RequestMethod.GET)
    public String toPage(Model model) {
        return "system/generator";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download")
    public void download() {

    }

}
