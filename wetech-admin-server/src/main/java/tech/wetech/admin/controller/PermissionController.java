package tech.wetech.admin.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.wetech.admin.model.Result;

/**
 * 权限
 *
 * @author cjbi
 */
@RestController
@RequestMapping("permission")
public class PermissionController {

    @GetMapping("no-pager")
    public Result getPermissionNoPager() {
        String json = "[{\n" +
            "    'id': 'marketing',\n" +
            "    'name': '营销管理',\n" +
            "    'describe': null,\n" +
            "    'status': 1,\n" +
            "    'actionData': '[{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "    'sptDaTypes': null,\n" +
            "    'optionalFields': null,\n" +
            "    'parents': null,\n" +
            "    'type': null,\n" +
            "    'deleted': 0,\n" +
            "    'actions': [\n" +
            "      'add',\n" +
            "      'query',\n" +
            "      'get',\n" +
            "      'update',\n" +
            "      'delete'\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    'id': 'member',\n" +
            "    'name': '会员管理',\n" +
            "    'describe': null,\n" +
            "    'status': 1,\n" +
            "    'actionData': '[{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "    'sptDaTypes': null,\n" +
            "    'optionalFields': '[]',\n" +
            "    'parents': null,\n" +
            "    'type': 'default',\n" +
            "    'deleted': 0,\n" +
            "    'actions': [\n" +
            "      'add',\n" +
            "      'query',\n" +
            "      'get',\n" +
            "      'update',\n" +
            "      'delete'\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    'id': 'menu',\n" +
            "    'name': '菜单管理',\n" +
            "    'describe': null,\n" +
            "    'status': 1,\n" +
            "    'actionData': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"import\",\"defaultCheck\":false,\"describe\":\"导入\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"}]',\n" +
            "    'sptDaTypes': null,\n" +
            "    'optionalFields': '[]',\n" +
            "    'parents': null,\n" +
            "    'type': 'default',\n" +
            "    'deleted': 0,\n" +
            "    'actions': [\n" +
            "      'add',\n" +
            "      'import',\n" +
            "      'get',\n" +
            "      'update'\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    'id': 'order',\n" +
            "    'name': '订单管理',\n" +
            "    'describe': null,\n" +
            "    'status': 1,\n" +
            "    'actionData': '[{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "    'sptDaTypes': null,\n" +
            "    'optionalFields': '[]',\n" +
            "    'parents': null,\n" +
            "    'type': 'default',\n" +
            "    'deleted': 0,\n" +
            "    'actions': [\n" +
            "      'add',\n" +
            "      'query',\n" +
            "      'get',\n" +
            "      'update',\n" +
            "      'delete'\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    'id': 'permission',\n" +
            "    'name': '权限管理',\n" +
            "    'describe': null,\n" +
            "    'status': 1,\n" +
            "    'actionData': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "    'sptDaTypes': null,\n" +
            "    'optionalFields': '[]',\n" +
            "    'parents': null,\n" +
            "    'type': 'default',\n" +
            "    'deleted': 0,\n" +
            "    'actions': [\n" +
            "      'add',\n" +
            "      'get',\n" +
            "      'update',\n" +
            "      'delete'\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    'id': 'role',\n" +
            "    'name': '角色管理',\n" +
            "    'describe': null,\n" +
            "    'status': 1,\n" +
            "    'actionData': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"}]',\n" +
            "    'sptDaTypes': null,\n" +
            "    'optionalFields': '[]',\n" +
            "    'parents': null,\n" +
            "    'type': 'default',\n" +
            "    'deleted': 0,\n" +
            "    'actions': [\n" +
            "      'add',\n" +
            "      'get',\n" +
            "      'update',\n" +
            "      'delete'\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    'id': 'test',\n" +
            "    'name': '测试权限',\n" +
            "    'describe': null,\n" +
            "    'status': 1,\n" +
            "    'actionData': '[{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"}]',\n" +
            "    'sptDaTypes': null,\n" +
            "    'optionalFields': '[]',\n" +
            "    'parents': null,\n" +
            "    'type': 'default',\n" +
            "    'deleted': 0,\n" +
            "    'actions': [\n" +
            "      'add',\n" +
            "      'get'\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    'id': 'user',\n" +
            "    'name': '用户管理',\n" +
            "    'describe': null,\n" +
            "    'status': 1,\n" +
            "    'actionData': '[{\"action\":\"query\",\"defaultCheck\":false,\"describe\":\"查询\"},{\"action\":\"get\",\"defaultCheck\":false,\"describe\":\"详情\"},{\"action\":\"add\",\"defaultCheck\":false,\"describe\":\"新增\"},{\"action\":\"update\",\"defaultCheck\":false,\"describe\":\"修改\"},{\"action\":\"delete\",\"defaultCheck\":false,\"describe\":\"删除\"},{\"action\":\"import\",\"defaultCheck\":false,\"describe\":\"导入\"},{\"action\":\"export\",\"defaultCheck\":false,\"describe\":\"导出\"}]',\n" +
            "    'sptDaTypes': null,\n" +
            "    'optionalFields': '[]',\n" +
            "    'parents': null,\n" +
            "    'type': 'default',\n" +
            "    'deleted': 0,\n" +
            "    'actions': [\n" +
            "      'add',\n" +
            "      'get'\n" +
            "    ]\n" +
            "  }\n" +
            "  ]";
        return Result.success();
    }

}
