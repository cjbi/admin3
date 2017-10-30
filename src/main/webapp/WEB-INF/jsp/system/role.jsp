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

        <div class="am-g">
            <div class="am-u-sm-12 am-u-md-6">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <shiro:hasPermission name="role:create">
                            <button type="button" class="am-btn am-btn-default am-btn-primary" onclick="create();"><span class="am-icon-plus"></span> 新增</button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="role:update">
                            <button type="button" class="am-btn am-btn-default am-btn-primary" onclick="update();"><span class="am-icon-edit"></span> 修改</button>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="role:delete">
                            <button type="button" class="am-btn am-btn-default am-btn-primary" onclick="del();"><span class="am-icon-trash-o"></span> 删除</button>
                        </shiro:hasPermission>
                        <button type="button" class="am-btn am-btn-default am-btn-primary" onclick="reset();"><span class="am-icon-refresh"></span> 重置</button>
                    </div>
                </div>
            </div>
            <div class="am-u-sm-12 am-u-md-3">
                <div class="am-form-group">
                    <select data-am-selected="{btnSize: 'sm'}">
                        <option value="option1">全部</option>
                        <option value="option2">文章</option>
                        <option value="option3">合作文章</option>
                        <option value="option3">未审核</option>
                    </select>
                </div>
            </div>
            <div class="am-u-sm-12 am-u-md-3">
                <div class="am-input-group am-input-group-sm">
                    <input type="text" class="am-form-field">
                    <span class="am-input-group-btn">
    <button class="am-btn am-btn-primary" type="button">搜索</button>
    </span>
                </div>
            </div>
        </div>

        <div class="am-u-sm-12">
            <table class="am-table am-table-striped am-table-hover table-main am-table-bordered am-text-nowrap" width="100%" id="example_role">
                <thead>
                <tr>
                    <th><input type="checkbox" id='checkAll'></th>
                    <th>角色名称</th>
                    <th>角色描述</th>
                    <th>拥有的资源</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <%-- footer start --%>
    <jsp:include page="footer.jsp"/>
    <%-- footer end --%>
</div>
<div id="add-dialog">
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

<div id="edit-dialog">
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

        var gridTable = [
            {
                'data': 'id',
                'sWidth': '2%',
                'fnCreatedCell': function (nTd, sData, oData, iRow, iCol) {
                    $(nTd).html('<input type="checkbox" name="checkList" title="' + sData + '" value="' + sData + '">');
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
            }
        ];
        var table = initDatatables(listURL, gridTable,'','','example_role');

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
                                if (data.success == true) {
                                    $.closeDialog(index);
                                    table.ajax.reload();
                                }
                            }
                        });
                    }
                }
            };
            $('#add-dialog').openDialog(opts);
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
                                if (data.success == true) {
                                    $.closeDialog(index);
                                    table.ajax.reload();
                                }
                            }
                        });
                    }
                }
            };
            $('#edit-dialog').openDialog(opts);
        }

        del = function () {
            deleteBatch(deleteURL, 'id');
        }

        /**
         * 重置
         */
        reset = function () {
            loadContent('#role');
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