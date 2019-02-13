package tech.wetech.admin.modules.system.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.wetech.admin.core.utils.BaseController;
import tech.wetech.admin.core.utils.Constants;
import tech.wetech.admin.modules.system.dto.ResourceDto;
import tech.wetech.admin.modules.system.service.ResourceService;
import tech.wetech.admin.modules.system.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author cjbi
 */
@Controller
public class HomeController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String showLoginForm(HttpServletRequest req, Model model) {
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        logger.info("begin to login");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            error = "登陆失败次数过多";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "system/login";
    }

    @GetMapping("/")
    public String index(Model model) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        Set<String> permissions = userService.queryPermissions(username);
        List<ResourceDto> menus = resourceService.findMenus(permissions);
        StringBuilder dom = new StringBuilder();
        getMenuTree(menus, Constants.MENU_ROOT_ID, dom);
        model.addAttribute(Constants.MENU_TREE, dom);
        return "base/main";
    }

    private List<ResourceDto> getMenuTree(List<ResourceDto> source, Long pid, StringBuilder dom) {
        List<ResourceDto> target = getChildResourceByPId(source, pid);
        target.forEach(res -> {
            dom.append("<li class='treeview'>");
            dom.append("<a href='" + res.getUrl() + "'>");
            dom.append("<i class='" + res.getIcon() + "'></i>");
            dom.append("<span>" + res.getName() + "</span>");
            if (Constants.SHARP.equals(res.getUrl())) {
                dom.append("<span class='pull-right-container'><i class='fa fa-angle-left pull-right'></i> </span>");
            }
            dom.append("</a>");
            dom.append("<ul class='treeview-menu'>");
            res.setChildren(getMenuTree(source, res.getId(), dom));
            dom.append("</ul>");
            dom.append("</li>");
        });
        return target;
    }

    private List<ResourceDto> getChildResourceByPId(List<ResourceDto> source, Long pId) {
        List<ResourceDto> child = new ArrayList<>();
        source.forEach(res -> {
            if (pId.equals(res.getParentId())) {
                child.add(res);
            }
        });
        return child;
    }
}
