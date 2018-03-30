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
        getMenuTree(menus,Constants.MENU_ROOT_ID,dom);
        model.addAttribute(Constants.MENU_TREE, dom);
        return "index";
    }

    private List<Resource> getMenuTree(List<Resource> source, Long pid, StringBuilder dom) {
        List<Resource> target = getChildResourceByPid(source, pid);
        target.forEach(res ->  {
            String angleRight = "#".equals(res.getUrl())?"<span class=\"am-icon-angle-right am-fr am-margin-right\"></span>": "",
                    href = "#".equals(res.getUrl())?"": "href=\"" + res.getUrl() + "\"",
                    icon = StringUtils.isEmpty(res.getIcon())?"am-icon-file": res.getIcon();

            dom.append("<li class=\"am-panel\">");
                dom.append("<a "+href+" class=\"am-cf\" data-am-collapse=\"{'parent': '#collapase-nav-"+pid+"', 'target': '#collapase-nav-"+res.getId()+"'}\">");
                    dom.append("<span class=\"am-list-ico "+icon+" am-margin-left-sm\"></span>"+res.getName()+angleRight);
                dom.append("</a>");
                dom.append("<ul class=\"am-list am-collapse admin-sidebar-sub\" id=\"collapase-nav-"+res.getId()+"\">");
            res.setChildren(getMenuTree(source, res.getId(), dom));
                dom.append("</ul>");
            dom.append("</li>");
        });
        return target;
    }

    private List<Resource> getChildResourceByPid(List<Resource> source, Long pid) {
        List<Resource> child = new ArrayList<>();
        source.forEach(res -> {
            if (pid.equals(res.getParentId())) {
                child.add(res);
            }
        });
        return child;
    }

}
