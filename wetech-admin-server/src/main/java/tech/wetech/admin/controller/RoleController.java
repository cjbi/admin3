package tech.wetech.admin.controller;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.aspect.SystemLog;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.entity.Role;
import tech.wetech.admin.service.ResourceService;
import tech.wetech.admin.service.RoleService;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * @author cjbi
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    @RequiresPermissions("role:view")
    public Result queryRoleList() {
        String json = "{\n" +
            "    'data': [{\n" +
            "      'id': 'admin',\n" +
            "      'name': '管理员',\n" +
            "      'describe': '拥有所有权限',\n" +
            "      'status': 1,\n" +
            "      'creatorId': 'system',\n" +
            "      'createTime': 1497160610259,\n" +
            "      'deleted': 0,\n" +
            "      'permissions': [{\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'comment',\n" +
            "        'permissionName': '评论管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'query',\n" +
            "          'describe': '查询',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'delete',\n" +
            "          'describe': '删除',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'member',\n" +
            "        'permissionName': '会员管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'query',\n" +
            "          'describe': '查询',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'delete',\n" +
            "          'describe': '删除',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'menu',\n" +
            "        'permissionName': '菜单管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"import\",\"defaultCheck\":false,\"describe\":\"导入\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'import',\n" +
            "          'describe': '导入',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'order',\n" +
            "        'permissionName': '订单管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'query',\n" +
            "          'describe': '查询',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'delete',\n" +
            "          'describe': '删除',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'permission',\n" +
            "        'permissionName': '权限管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'delete',\n" +
            "          'describe': '删除',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'role',\n" +
            "        'permissionName': '角色管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'delete',\n" +
            "          'describe': '删除',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'test',\n" +
            "        'permissionName': '测试权限',\n" +
            "        'actions': '[]',\n" +
            "        'actionEntitySet': [],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'user',\n" +
            "        'permissionName': '用户管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"import\",\"defaultCheck\":false,\"describe\":\"导入\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"},{\"action\":\"export\",\"defaultCheck\":false,\"describe\":\"导出\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'import',\n" +
            "          'describe': '导入',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'delete',\n" +
            "          'describe': '删除',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'export',\n" +
            "          'describe': '导出',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      'id': 'svip',\n" +
            "      'name': 'SVIP',\n" +
            "      'describe': '超级会员',\n" +
            "      'status': 1,\n" +
            "      'creatorId': 'system',\n" +
            "      'createTime': 1532417744846,\n" +
            "      'deleted': 0,\n" +
            "      'permissions': [{\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'comment',\n" +
            "        'permissionName': '评论管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'query',\n" +
            "          'describe': '查询',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'delete',\n" +
            "          'describe': '删除',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'member',\n" +
            "        'permissionName': '会员管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'query',\n" +
            "          'describe': '查询',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'menu',\n" +
            "        'permissionName': '菜单管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"import\",\"defaultCheck\":false,\"describe\":\"导入\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'import',\n" +
            "          'describe': '导入',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'order',\n" +
            "        'permissionName': '订单管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'query',\n" +
            "          'describe': '查询',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'permission',\n" +
            "        'permissionName': '权限管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'role',\n" +
            "        'permissionName': '角色管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'delete',\n" +
            "          'describe': '删除',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'test',\n" +
            "        'permissionName': '测试权限',\n" +
            "        'actions': '[]',\n" +
            "        'actionEntitySet': [],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'admin',\n" +
            "        'permissionId': 'user',\n" +
            "        'permissionName': '用户管理',\n" +
            "        'actions': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"import\",\"defaultCheck\":false,\"describe\":\"导入\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"},{\"action\":\"export\",\"defaultCheck\":false,\"describe\":\"导出\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'add',\n" +
            "          'describe': '新增',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'import',\n" +
            "          'describe': '导入',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'update',\n" +
            "          'describe': '修改',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      'id': 'user',\n" +
            "      'name': '普通会员',\n" +
            "      'describe': '普通用户，只能查询',\n" +
            "      'status': 1,\n" +
            "      'creatorId': 'system',\n" +
            "      'createTime': 1497160610259,\n" +
            "      'deleted': 0,\n" +
            "      'permissions': [{\n" +
            "        'roleId': 'user',\n" +
            "        'permissionId': 'comment',\n" +
            "        'permissionName': '评论管理',\n" +
            "        'actions': '[{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'query',\n" +
            "          'describe': '查询',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "\n" +
            "      {\n" +
            "        'roleId': 'user',\n" +
            "        'permissionId': 'marketing',\n" +
            "        'permissionName': '营销管理',\n" +
            "        'actions': '[]',\n" +
            "        'actionEntitySet': [],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'user',\n" +
            "        'permissionId': 'member',\n" +
            "        'permissionName': '会员管理',\n" +
            "        'actions': '[{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'query',\n" +
            "          'describe': '查询',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'user',\n" +
            "        'permissionId': 'menu',\n" +
            "        'permissionName': '菜单管理',\n" +
            "        'actions': '[]',\n" +
            "        'actionEntitySet': [],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "\n" +
            "      {\n" +
            "        'roleId': 'user',\n" +
            "        'permissionId': 'order',\n" +
            "        'permissionName': '订单管理',\n" +
            "        'actions': '[{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"}]',\n" +
            "        'actionEntitySet': [{\n" +
            "          'action': 'query',\n" +
            "          'describe': '查询',\n" +
            "          'defaultCheck': false\n" +
            "        },\n" +
            "        {\n" +
            "          'action': 'get',\n" +
            "          'describe': '详情',\n" +
            "          'defaultCheck': false\n" +
            "        }\n" +
            "        ],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'user',\n" +
            "        'permissionId': 'permission',\n" +
            "        'permissionName': '权限管理',\n" +
            "        'actions': '[]',\n" +
            "        'actionEntitySet': [],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'user',\n" +
            "        'permissionId': 'role',\n" +
            "        'permissionName': '角色管理',\n" +
            "        'actions': '[]',\n" +
            "        'actionEntitySet': [],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "\n" +
            "      {\n" +
            "        'roleId': 'user',\n" +
            "        'permissionId': 'test',\n" +
            "        'permissionName': '测试权限',\n" +
            "        'actions': '[]',\n" +
            "        'actionEntitySet': [],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      },\n" +
            "      {\n" +
            "        'roleId': 'user',\n" +
            "        'permissionId': 'user',\n" +
            "        'permissionName': '用户管理',\n" +
            "        'actions': '[]',\n" +
            "        'actionEntitySet': [],\n" +
            "        'actionList': null,\n" +
            "        'dataAccess': null\n" +
            "      }\n" +
            "      ]\n" +
            "    }\n" +
            "    ],\n" +
            "    'pageSize': 10,\n" +
            "    'pageNo': 0,\n" +
            "    'totalPage': 1,\n" +
            "    'totalCount': 5\n" +
            "  }";
        return Result.success(JSON.parseObject(json));
    }

    @RequiresPermissions("role:create")
    @SystemLog("角色管理创建角色")
    @PostMapping
    public Result create(@Validated Role role) {
        roleService.create(role);
        return Result.success();
    }

    @RequiresPermissions("role:update")
    @SystemLog("角色管理更新角色")
    @PutMapping
    public Result update(@Validated Role role) {
        roleService.updateNotNull(role);
        return Result.success();
    }

    @RequiresPermissions("role:delete")
    @SystemLog("角色管理删除角色")
    @DeleteMapping
    public Result deleteBatchByIds(@NotNull @RequestParam("id") Long[] ids) {
        Arrays.stream(ids).forEach(roleService::deleteById);
        return Result.success();
    }

}
