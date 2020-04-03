package tech.wetech.admin.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.dto.PermissionDTO;
import tech.wetech.admin.model.vo.UserInfoVO;
import tech.wetech.admin.service.PermissionService;
import tech.wetech.admin.service.UserService;
import tech.wetech.admin.utils.JSONUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cjbi
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    private static final String DEFAULT_AVATAR = "/avatar2.jpg";

    @GetMapping("user/nav")
    public Result<List<Map<String, Object>>> getUserNav() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        Set<String> permissions = userService.queryPermissions(username);
        List<PermissionDTO> permissionDTOS = permissionService.queryMenus(permissions);
        List<Map<String, Object>> list = new ArrayList<>();
        for (PermissionDTO permission : permissionDTOS) {
            Map<String, Object> userNav = JSONUtil.toObject(permission.getConfig(), Map.class);
            userNav.put("id",permission.getId());
            userNav.put("parentId",permission.getParentId());
            list.add(userNav);
        }
        return Result.success(list);
    }

    @GetMapping("user/info")
    public Result<UserInfoVO> getUserInfo() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setName(username);
        userInfoVO.setAvatar(DEFAULT_AVATAR);
        userInfoVO.setPermissions(userService.queryPermissions(username));
        return Result.success(userInfoVO);
    }

//    @GetMapping("user/info")
//    public Result getUserInfo() {
//        Map role = JSON.parseObject("{\n" +
//            "    'id': 'admin',\n" +
//            "    'name': '管理员',\n" +
//            "    'describe': '拥有所有权限',\n" +
//            "    'status': 1,\n" +
//            "    'creatorId': 'system',\n" +
//            "    'createTime': 1497160610259,\n" +
//            "    'deleted': 0,\n" +
//            "    'permissions': [{\n" +
//            "      'roleId': 'admin',\n" +
//            "      'permissionId': 'dashboard',\n" +
//            "      'permissionName': '仪表盘',\n" +
//            "      'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
//            "      'actionEntitySet': [{\n" +
//            "        'action': 'add',\n" +
//            "        'describe': '新增',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'query',\n" +
//            "        'describe': '查询',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'get',\n" +
//            "        'describe': '详情',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'update',\n" +
//            "        'describe': '修改',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'delete',\n" +
//            "        'describe': '删除',\n" +
//            "        'defaultCheck': false\n" +
//            "      }],\n" +
//            "      'actionList': null,\n" +
//            "      'dataAccess': null\n" +
//            "    }, {\n" +
//            "      'roleId': 'admin',\n" +
//            "      'permissionId': 'exception',\n" +
//            "      'permissionName': '异常页面权限',\n" +
//            "      'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
//            "      'actionEntitySet': [{\n" +
//            "        'action': 'add',\n" +
//            "        'describe': '新增',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'query',\n" +
//            "        'describe': '查询',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'get',\n" +
//            "        'describe': '详情',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'update',\n" +
//            "        'describe': '修改',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'delete',\n" +
//            "        'describe': '删除',\n" +
//            "        'defaultCheck': false\n" +
//            "      }],\n" +
//            "      'actionList': null,\n" +
//            "      'dataAccess': null\n" +
//            "    }, {\n" +
//            "      'roleId': 'admin',\n" +
//            "      'permissionId': 'result',\n" +
//            "      'permissionName': '结果权限',\n" +
//            "      'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
//            "      'actionEntitySet': [{\n" +
//            "        'action': 'add',\n" +
//            "        'describe': '新增',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'query',\n" +
//            "        'describe': '查询',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'get',\n" +
//            "        'describe': '详情',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'update',\n" +
//            "        'describe': '修改',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'delete',\n" +
//            "        'describe': '删除',\n" +
//            "        'defaultCheck': false\n" +
//            "      }],\n" +
//            "      'actionList': null,\n" +
//            "      'dataAccess': null\n" +
//            "    }, {\n" +
//            "      'roleId': 'admin',\n" +
//            "      'permissionId': 'profile',\n" +
//            "      'permissionName': '详细页权限',\n" +
//            "      'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
//            "      'actionEntitySet': [{\n" +
//            "        'action': 'add',\n" +
//            "        'describe': '新增',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'query',\n" +
//            "        'describe': '查询',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'get',\n" +
//            "        'describe': '详情',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'update',\n" +
//            "        'describe': '修改',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'delete',\n" +
//            "        'describe': '删除',\n" +
//            "        'defaultCheck': false\n" +
//            "      }],\n" +
//            "      'actionList': null,\n" +
//            "      'dataAccess': null\n" +
//            "    }, {\n" +
//            "      'roleId': 'admin',\n" +
//            "      'permissionId': 'table',\n" +
//            "      'permissionName': '表格权限',\n" +
//            "      'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"import\",\"defaultCheck\":false,\"describe\":\"导入\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"}]',\n" +
//            "      'actionEntitySet': [{\n" +
//            "        'action': 'add',\n" +
//            "        'describe': '新增',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'import',\n" +
//            "        'describe': '导入',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'get',\n" +
//            "        'describe': '详情',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'update',\n" +
//            "        'describe': '修改',\n" +
//            "        'defaultCheck': false\n" +
//            "      }],\n" +
//            "      'actionList': null,\n" +
//            "      'dataAccess': null\n" +
//            "    }, {\n" +
//            "      'roleId': 'admin',\n" +
//            "      'permissionId': 'form',\n" +
//            "      'permissionName': '表单权限',\n" +
//            "      'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
//            "      'actionEntitySet': [{\n" +
//            "        'action': 'add',\n" +
//            "        'describe': '新增',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'get',\n" +
//            "        'describe': '详情',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'query',\n" +
//            "        'describe': '查询',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'update',\n" +
//            "        'describe': '修改',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'delete',\n" +
//            "        'describe': '删除',\n" +
//            "        'defaultCheck': false\n" +
//            "      }],\n" +
//            "      'actionList': null,\n" +
//            "      'dataAccess': null\n" +
//            "    }, {\n" +
//            "      'roleId': 'admin',\n" +
//            "      'permissionId': 'order',\n" +
//            "      'permissionName': '订单管理',\n" +
//            "      'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
//            "      'actionEntitySet': [{\n" +
//            "        'action': 'add',\n" +
//            "        'describe': '新增',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'query',\n" +
//            "        'describe': '查询',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'get',\n" +
//            "        'describe': '详情',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'update',\n" +
//            "        'describe': '修改',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'delete',\n" +
//            "        'describe': '删除',\n" +
//            "        'defaultCheck': false\n" +
//            "      }],\n" +
//            "      'actionList': null,\n" +
//            "      'dataAccess': null\n" +
//            "    }, {\n" +
//            "      'roleId': 'admin',\n" +
//            "      'permissionId': 'permission',\n" +
//            "      'permissionName': '权限管理',\n" +
//            "      'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
//            "      'actionEntitySet': [{\n" +
//            "        'action': 'add',\n" +
//            "        'describe': '新增',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'get',\n" +
//            "        'describe': '详情',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'update',\n" +
//            "        'describe': '修改',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'delete',\n" +
//            "        'describe': '删除',\n" +
//            "        'defaultCheck': false\n" +
//            "      }],\n" +
//            "      'actionList': null,\n" +
//            "      'dataAccess': null\n" +
//            "    }, {\n" +
//            "      'roleId': 'admin',\n" +
//            "      'permissionId': 'role',\n" +
//            "      'permissionName': '角色管理',\n" +
//            "      'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
//            "      'actionEntitySet': [{\n" +
//            "        'action': 'add',\n" +
//            "        'describe': '新增',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'get',\n" +
//            "        'describe': '详情',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'update',\n" +
//            "        'describe': '修改',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'delete',\n" +
//            "        'describe': '删除',\n" +
//            "        'defaultCheck': false\n" +
//            "      }],\n" +
//            "      'actionList': null,\n" +
//            "      'dataAccess': null\n" +
//            "    }, {\n" +
//            "      'roleId': 'admin',\n" +
//            "      'permissionId': 'table',\n" +
//            "      'permissionName': '桌子管理',\n" +
//            "      'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
//            "      'actionEntitySet': [{\n" +
//            "        'action': 'add',\n" +
//            "        'describe': '新增',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'get',\n" +
//            "        'describe': '详情',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'query',\n" +
//            "        'describe': '查询',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'update',\n" +
//            "        'describe': '修改',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'delete',\n" +
//            "        'describe': '删除',\n" +
//            "        'defaultCheck': false\n" +
//            "      }],\n" +
//            "      'actionList': null,\n" +
//            "      'dataAccess': null\n" +
//            "    }, {\n" +
//            "      'roleId': 'admin',\n" +
//            "      'permissionId': 'user',\n" +
//            "      'permissionName': '用户管理',\n" +
//            "      'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"import\",\"defaultCheck\":false,\"describe\":\"导入\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"},{\"action\":\"export\",\"defaultCheck\":false,\"describe\":\"导出\"}]',\n" +
//            "      'actionEntitySet': [{\n" +
//            "        'action': 'add',\n" +
//            "        'describe': '新增',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'import',\n" +
//            "        'describe': '导入',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'get',\n" +
//            "        'describe': '详情',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'update',\n" +
//            "        'describe': '修改',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'delete',\n" +
//            "        'describe': '删除',\n" +
//            "        'defaultCheck': false\n" +
//            "      }, {\n" +
//            "        'action': 'export',\n" +
//            "        'describe': '导出',\n" +
//            "        'defaultCheck': false\n" +
//            "      }],\n" +
//            "      'actionList': null,\n" +
//            "      'dataAccess': null\n" +
//            "    }]\n" +
//            "  }", Map.class);
//        Map map = JSON.parseObject("{\n" +
//            "    'id': '4291d7da9005377ec9aec4a71ea837f',\n" +
//            "    'name': '天野远子',\n" +
//            "    'username': 'admin',\n" +
//            "    'password': '',\n" +
//            "    'avatar': '/avatar2.jpg',\n" +
//            "    'status': 1,\n" +
//            "    'telephone': '',\n" +
//            "    'lastLoginIp': '27.154.74.117',\n" +
//            "    'lastLoginTime': 1534837621348,\n" +
//            "    'creatorId': 'admin',\n" +
//            "    'createTime': 1497160610259,\n" +
//            "    'merchantCode': 'TLif2btpzg079h15bk',\n" +
//            "    'deleted': 0,\n" +
//            "    'roleId': 'admin',\n" +
//            "    'role': {}\n" +
//            "  }", Map.class);
//        map.put("role", role);
//        List<Map> list = (List<Map>) role.get("permissions");
//        list.add(JSON.parseObject("{\n" +
//            "    'roleId': 'admin',\n" +
//            "    'permissionId': 'support',\n" +
//            "    'permissionName': '超级模块',\n" +
//            "    'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"import\",\"defaultCheck\":false,\"describe\":\"导入\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"},{\"action\":\"export\",\"defaultCheck\":false,\"describe\":\"导出\"}]',\n" +
//            "    'actionEntitySet': [{\n" +
//            "      'action': 'add',\n" +
//            "      'describe': '新增',\n" +
//            "      'defaultCheck': false\n" +
//            "    }, {\n" +
//            "      'action': 'import',\n" +
//            "      'describe': '导入',\n" +
//            "      'defaultCheck': false\n" +
//            "    }, {\n" +
//            "      'action': 'get',\n" +
//            "      'describe': '详情',\n" +
//            "      'defaultCheck': false\n" +
//            "    }, {\n" +
//            "      'action': 'update',\n" +
//            "      'describe': '修改',\n" +
//            "      'defaultCheck': false\n" +
//            "    }, {\n" +
//            "      'action': 'delete',\n" +
//            "      'describe': '删除',\n" +
//            "      'defaultCheck': false\n" +
//            "    }, {\n" +
//            "      'action': 'export',\n" +
//            "      'describe': '导出',\n" +
//            "      'defaultCheck': false\n" +
//            "    }],\n" +
//            "    'actionList': null,\n" +
//            "    'dataAccess': null\n" +
//            "  }", Map.class));
//        return Result.success(map);
//    }

}
