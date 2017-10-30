package tech.wetech.admin.web.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import tech.wetech.admin.common.utils.Constants;
import tech.wetech.admin.model.system.User;
import tech.wetech.admin.model.system.UserExample;
import tech.wetech.admin.service.system.UserService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
public class SysUserFilter extends PathMatchingFilter {

    @Autowired
    private UserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(username);
        List<User> list = userService.selectByExample(example);
        request.setAttribute(Constants.CURRENT_USER, !list.isEmpty() ? list.get(0) : new User());
        return true;
    }
}
