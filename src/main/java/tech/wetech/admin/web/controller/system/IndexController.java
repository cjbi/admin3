package tech.wetech.admin.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.wetech.admin.common.utils.Constants;
import tech.wetech.admin.model.system.Resource;
import tech.wetech.admin.model.system.User;
import tech.wetech.admin.service.system.ResourceService;
import tech.wetech.admin.service.system.UserService;
import tech.wetech.admin.web.bind.annotation.CurrentUser;

@Controller
public class IndexController{

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(@CurrentUser User loginUser, Model model) {
        Set<String> permissions = userService.findPermissions(loginUser.getUsername());
        List<Resource> menus = resourceService.findMenus(permissions);
        StringBuilder dom = new StringBuilder();
        List<Resource>  menusTrees = getMenuTree(menus,Constants.MENU_ROOT_ID,dom);
        model.addAttribute("menuTree", dom);
        return "index";
    }

    private List<Resource> getMenuTree(List<Resource> source, Long pid, StringBuilder dom) {
        List<Resource> target = getChildResourceByPid(source, pid);
        for (Resource resource : target) {
            String angleRight = resource.getUrl().equals("#")?"<i class=\"am-icon-angle-right am-fr am-margin-right\"></i>": "",
                    href = resource.getUrl().equals("#")?"": "href=\"" + resource.getUrl() + "\"",
                    icon = StringUtils.isEmpty(resource.getIcon())?"am-icon-file": resource.getIcon();


            dom.append("<li class=\"am-panel\">");
                dom.append("<a "+href+" class=\"am-cf\" data-am-collapse=\"{'parent': '#collapase-nav-"+pid+"', 'target': '#collapase-nav-"+resource.getId()+"'}\">");
                    dom.append("<span class=\"am-list-ico "+icon+" am-margin-left-sm\"></span>"+resource.getName()+angleRight);
                dom.append("</a>");
                dom.append("<ul class=\"am-list am-collapse admin-sidebar-sub\" id=\"collapase-nav-"+resource.getId()+"\">");
            resource.setChildren(getMenuTree(source, resource.getId(), dom));
                dom.append("</ul>");
            dom.append("</li>");
        }
        return target;
    }

    private List<Resource> getChildResourceByPid(List<Resource> source, Long pid) {
        List<Resource> children = new ArrayList<>();
        for (Resource resource : source) {
            if (pid.equals(resource.getParentId())) {
                children.add(resource);
            }
        }
        return children;
    }

}
