package tech.wetech.admin;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.wetech.admin.controller.PermissionController;
import tech.wetech.admin.model.dto.PermissionTreeDTO;
import tech.wetech.admin.service.PermissionService;
import tech.wetech.admin.util.JSONUtil;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WetechAdminApplicationTests {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionController permissionController;

    @Test
    public void contextLoads() {
    }

    @Test
    public void queryPermissionTreeWithService() {
        List<PermissionTreeDTO> permissionTreeDTOS = permissionService.queryPermissionTree();
        log.info("返回的json为：{}", JSONUtil.toJSONString(permissionTreeDTOS));
    }

    @Test
    public void queryPermissionTreeWithController() {
//        Result<List<PermissionDTO>> listResult = permissionController.queryPermissionTree();
//        log.info("返回的json为：{}", JSONUtil.toJSON(listResult));
    }

}
