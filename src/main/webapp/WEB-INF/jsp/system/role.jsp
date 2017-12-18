<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="wetechfn" uri="http://wetech.tech/admin/tags/wetech-functions" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>
    ul.ztree {
        border: 1px solid #ccc;
        background-color: #fff;
        height: 200px;
        overflow-y: scroll;
        overflow-x: auto;
    }
</style>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding">
            <ol class="am-breadcrumb">
                <li><a href="#" class="am-icon-home">首页</a></li>
                <%--<li><a href="#">用户管理</a></li>--%>
                <li class="am-active">角色管理</li>
            </ol>
        </div>

        <div class="am-g am-btn-toolbar">
            <div class="am-u-sm-12 am-u-md-6">
                <div class="am-btn-group am-btn-group-xs">
                </div>
            </div>
            <div class="am-u-sm-12 am-u-md-3">
                <div class="am-form-group">
                    <%--<select data-am-selected="{btnSize: 'sm'}">
                        <option value="option1">全部</option>
                        &lt;%&ndash;<option value="option2">文章</option>
                        <option value="option3">合作文章</option>
                        <option value="option3">未审核</option>&ndash;%&gt;
                    </select>--%>
                </div>
            </div>
            <div class="am-u-sm-12 am-u-md-3">
                <div class="am-input-group am-input-group-sm">
                    <input type="text" name="keywords" id="keywords" placeholder="角色名称 / 角色描述" class="am-form-field">
                    <span class="am-input-group-btn">
                        <button class="am-btn am-btn-primary" onclick="$.mytables.reloadTable();" type="button">搜索</button>
                    </span>
                </div>
            </div>
        </div>

        <div class="am-u-sm-12">
            <table class="am-table am-table-striped am-table-hover table-main am-table-bordered" width="100%" id="example_role">
                <thead>
                <tr>
                    <th><input type="checkbox" class="am-checkbox-all"></th>
                    <th>角色名称</th>
                    <th>角色描述</th>
                    <th>拥有的资源</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <%-- footer start --%>
    <jsp:include page="footer.jsp"/>
    <%-- footer end --%>
</div>
<div id="add-dialog" class="admin-hide">
    <form class="am-form am-form-horizontal am-padding-top" id="add-form">
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">角色名<span class="asterisk">*</span></label>
            <div class="am-u-sm-10">
                <input type="text" name="role" placeholder="输入角色名" data-foolish-msg="请输入英文，至少3个字符" required>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">角色描述</label>
            <div class="am-u-sm-10">
                <input type="text" name="description" placeholder="输入角色描述"/>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">拥有的资源列表<span class="asterisk">*</span></label>
            <div class="am-u-sm-10">
                <input type="hidden" id="resourceIds" name="resourceIds" readonly required>
                <input type="text" id="resourceName" name="resourceNames" readonly required>
            </div>
        </div>
    </form>
</div>

<div id="edit-dialog" class="admin-hide">
    <form class="am-form am-form-horizontal am-padding-top" id="edit-form">
        <input type="hidden" name="id"/>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">角色名<span class="asterisk">*</span></label>
            <div class="am-u-sm-10">
                <input type="text" name="role" placeholder="输入用户名" minlength="3" data-foolish-msg="请输入英文，至少3个字符" required>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">角色描述</label>
            <div class="am-u-sm-10">
                <input type="text" name="description" placeholder="输入角色描述"/>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">拥有的资源列表<span class="asterisk">*</span></label>
            <div class="am-u-sm-10">
                <input type="hidden" id="edit-resourceIds" name="resourceIds" readonly required>
                <input type="text" id="edit-resourceName" name="resourceNames" readonly required>
            </div>
        </div>
    </form>
</div>
<div id="menuContent" class="menuContent" style="display:none;z-index:1989101600;position: absolute;">
    <ul id="tree" class="ztree" style="margin-top:0;"></ul>
</div>
<script type="text/javascript">
    $(function () {
        var pathURL = basePath + '/role/',
            listURL = pathURL + 'list',
            createURL = pathURL + 'create',
            updateURL = pathURL + 'update',
            deleteURL = pathURL + 'delete';

        var ajax = {
            'url': listURL,
            'data': function (data) {
                var keywords = $('#keywords').val();
                if (keywords) {
                    data.keywords = ($('#keywords').val());
                }
            }
        };

        var gridTable = [
            {
                'data': 'id',
                'sWidth': '2%',
                'fnCreatedCell': function (nTd, sData, oData, iRow, iCol) {
                    $(nTd).html('<input type="checkbox" class="am-checkbox-list" title="' + sData + '" value="' + sData + '">');
                }
            },
            {
                'data': 'role'
            },
            {
                'data': 'description'
            },
            {
                'data': 'resourceNames'
            },
            {
                'data': 'id',
                'mRender': function (data, type, full) {
                    return '<div class="am-btn-group am-btn-group-xs">\
                                 <shiro:hasPermission name="role:update">\
                                 <a href="javascript:update();" class="am-btn am-btn-primary am-btn-hollow"><span class="am-icon-pencil-square-o"></span> 编辑</button>\
                                 </shiro:hasPermission>\
                                 <shiro:hasPermission name="role:update">\
                                 <a href="javascript:del();" class="am-btn am-btn-danger am-btn-hollow"><span class="am-icon-trash-o"></span> 删除</button>\
                                 </shiro:hasPermission>\
                             </div>';
                }
            }
        ];
        //定义按钮
        var buttons = [
            <shiro:hasPermission name="role:create">
            {
                'text': '<span class="am-icon-plus"></span> 新增',
                'action': function (e, dt, node, config) {
                    create();
                }
            },
            </shiro:hasPermission>
            <shiro:hasPermission name="role:update">
            {
                'text': '<span class="am-icon-edit"></span> 修改',
                'action': function (e, dt, node, config) {
                    update();
                }
            },
            </shiro:hasPermission>
            <shiro:hasPermission name="role:delete">
            {
                'text': '<span class="am-icon-trash-o"></span> 删除',
                'action': function (e, dt, node, config) {
                    del();
                }
            }
            </shiro:hasPermission>
        ];


        var opts = {
            'ajax': ajax,
            'buttons': buttons,
            'columns': gridTable,
            'tableId': 'example_role'
        };
        var table = $.mytables.initTable(opts);

        /**
         * 新增
         */
        create = function () {
            var $form = $('#add-form');
            var opts = {
                title: '添加角色',
                yes: function (index, layero) {
                    if ($form.isFormValid()) {
                        $form.submit({
                            url: createURL,
                            callback: function (data) {
                                if (data.success) {
                                    $.mydialog.closeDialog(index);
                                    table.ajax.reload();
                                }
                            }
                        });
                    }
                },
                content: $('#add-dialog')
            };
            $.mytables.openDialog(opts);
        }

        /**
         * 修改
         */
        update = function () {
            var $form = $('#edit-form');
            var opts = {
                title: '修改角色',
                editable: true,
                yes: function (index, layero) {
                    if ($form.isFormValid()) {
                        $form.submit({
                            url: updateURL,
                            callback: function (data) {
                                if (data.success) {
                                    $.mydialog.closeDialog(index);
                                    table.ajax.reload();
                                }
                            }
                        });
                    }
                },
                content: $('#edit-dialog')
            };
            $.mytables.openDialog(opts);
        }

        /**
         * 删除
         */
        del = function () {
            $.mytables.batch(deleteURL, 'id', '删除');
        }

        var setting = {
            check: {
                enable: true,
                chkboxType: {"Y": "", "N": ""}
            },
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onCheck: onCheck
            }
        };

        var zNodes = [
            <c:forEach items="${resourceList}" var="r">
            <c:if test="${not r.rootNode}">
            {id:${r.id}, pId:${r.parentId}, name: "${r.name}", checked:${wetechfn:in(role.resourceIds, r.id)}},
            </c:if>
            </c:forEach>
        ];

        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("tree"),
                nodes = zTree.getCheckedNodes(true),
                id = "",
                name = "";
            nodes.sort(function compare(a, b) {
                return a.id - b.id;
            });
            for (var i = 0, l = nodes.length; i < l; i++) {
                id += nodes[i].id + ",";
                name += nodes[i].name + ",";
            }
            if (id.length > 0) id = id.substring(0, id.length - 1);
            if (name.length > 0) name = name.substring(0, name.length - 1);
            $("#resourceIds").val(id);
            $("#resourceName").val(name);
            $("#edit-resourceIds").val(id);
            $("#edit-resourceName").val(name);
            // hideMenu();
        }

        function showMenu() {
            var cityObj = $("#resourceName");
            var cityOffset = $("#resourceName").offset();
            $("#menuContent").css({left: cityOffset.left + "px", top: cityOffset.top + cityObj.outerHeight() - 60 + "px"}).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);
        }

        function showMenuOfEdit() {
            var cityObj = $("#edit-resourceName");
            var cityOffset = $("#edit-resourceName").offset();
            $("#menuContent").css({left: cityOffset.left + "px", top: cityOffset.top + cityObj.outerHeight() - 60 + "px"}).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);
        }

        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }

        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
                hideMenu();
            }
        }

        $.fn.zTree.init($("#tree"), setting, zNodes);
        $("#resourceName").click(showMenu);
        $("#edit-resourceName").click(showMenuOfEdit);
    });
</script>