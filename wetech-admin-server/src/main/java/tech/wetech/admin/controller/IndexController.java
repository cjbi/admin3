package tech.wetech.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.constant.Constants;
import tech.wetech.admin.model.dto.PermissionTreeDTO;
import tech.wetech.admin.model.vo.UserInfoVO;
import tech.wetech.admin.service.PermissionService;
import tech.wetech.admin.service.UserService;
import tech.wetech.admin.util.JSONUtil;
import tech.wetech.admin.util.SpringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cjbi
 */
@Slf4j
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping("user/nav")
    public Result<List<Map<String, Object>>> getUserNav() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        Set<String> permissions = userService.queryPermissions(username);
        List<PermissionTreeDTO> permissionTreeDTOS = permissionService.queryMenus(permissions);
        List<Map<String, Object>> list = new ArrayList<>();
        for (PermissionTreeDTO permission : permissionTreeDTOS) {
            try {
                Map<String, Object> userNav = JSONUtil.toObject(permission.getConfig(), Map.class);
                userNav.put("id", permission.getId());
                userNav.put("parentId", permission.getParentId());
                list.add(userNav);
            } catch (Exception e) {
                log.warn("菜单【{}】路由配置有误，不展示此菜单", permission.getName());
            }
        }
        return Result.success(list);
    }

    @GetMapping("user/info")
    public Result<UserInfoVO> getUserInfo() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setName(username);
        userInfoVO.setAvatar(Constants.DEFAULT_AVATAR);
        userInfoVO.setPermissions(userService.queryPermissions(username));
        return Result.success(userInfoVO);
    }

    @GetMapping("datasource/initialize")
    public Result initializeDatasource() {
        DataSource dataSource = SpringUtils.getBean(DataSource.class);
        ResourceLoader loader = new DefaultResourceLoader();
        Resource schema = loader.getResource("classpath:schema.sql");
        Resource data = loader.getResource("classpath:data.sql");
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(schema, data);
        populator.execute(dataSource);
        return Result.success();
    }

}
