package tech.wetech.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.wetech.admin.model.PageWrapper;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.dto.PermissionDTO;
import tech.wetech.admin.model.vo.PermissionVO;
import tech.wetech.admin.service.PermissionService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限
 *
 * @author cjbi
 */
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("no-pager")
    public Result<PageWrapper> queryPermissionList() {
        List<PermissionDTO> permissionDTOS = permissionService.queryPermissionTree();
        PageWrapper<PermissionVO> objectPageWrapper = new PageWrapper<>();
        objectPageWrapper.setData(permissionDTOS.stream().map(PermissionVO::new).collect(Collectors.toList()));
        return Result.success(objectPageWrapper);
    }

    @GetMapping("tree")
    public Result queryPermissionTree() {
        return Result.success(permissionService.queryPermissionTree());
    }

}
