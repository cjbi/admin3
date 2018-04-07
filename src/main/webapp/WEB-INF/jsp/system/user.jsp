<%--
  Created by IntelliJ IDEA.
  User: cjbi
  Date: 2018/4/6
  Time: 7:43
  空页面.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!-- Content Header (Page header) -->
<section class="content-header" style="">
    <h1>
        用户管理
        <small>系统用户管理页面</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
        <li><a href="#">用户管理</a></li>
        <li class="active">系统用户管理</li>
    </ol>
</section>

<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <div class="box">
                <!-- /.box-header -->
                <div class="box-body">
                    <div class="btn-group btn-group-sm" id="toolbar">
                        <!-- Provides extra visual weight and identifies the primary action in a set of buttons -->
                        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon glyphicon-plus"></i> 新增</button>

                        <!-- Indicates caution should be taken with this action -->
                        <button type="button" class="btn btn-default"><i class="glyphicon glyphicon glyphicon-edit"></i> 修改</button>

                        <!-- Indicates a dangerous or potentially negative action -->
                        <button type="button" class="btn btn-default"><i class="glyphicon glyphicon-remove"></i> 删除</button>
                    </div>
                    <table id="table"></table>
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->

        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->
</section>
<!-- /.content -->

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加用户</h4>
            </div>
            <div class="modal-body">
                <form id="add">
                    <div class="form-group">
                        <label class="control-label"><span class="asterisk">*</span>用户名:</label>
                        <input type="text" class="form-control" name="username" placeholder="输入用户名" minlength="3" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label"><span class="asterisk">*</span>密码:</label>
                        <input type="password" class="form-control" name="password" placeholder="输入密码" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label"><span class="asterisk">*</span>确认密码:</label>
                        <input type="password" class="form-control" name="chkpassowrd" placeholder="确认密码" required>
                    </div>
                    <div class="form-group">
                        <label class="control-label"><span class="asterisk">*</span>所属组织:</label>
                        <input type="text" class="form-control" id="organizationName" name="organizationName" readonly required>
                        <input type="hidden" id="organizationId" name="organizationId" readonly required>
                    </div>
                    <div class="form-group">
                        <label class="control-label"><span class="asterisk">*</span>角色列表:</label>
                        <select name="roleIds" multiple class="form-control">
                            <c:forEach items="${roleList}" var="role">
                                <option value="${role.id}">${role.description}</option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-action="{type:'submit',form:'#add',url:'<%=request.getContextPath()%>/user/create',after:'onAfter'}">确定</button>
            </div>
        </div>
    </div>
</div>

<script>
    // 数据表格展开内容
    function detailFormatter(index, row) {
        var html = [];
        $.each(row, function (key, value) {
            html.push('<p><b>' + key + ':</b> ' + value + '</p>');
        });
        return html.join('');
    }
    var $table = $('#table');
    $(function () {
        // bootstrap table初始化
        // http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
        $table.bootstrapTable({
            url: path +'/user/list',
            columns: [
                {field: 'state', checkbox: true},
                {field: 'id', title: '编号', sortable: true, halign: 'left'},
                {field: 'username', title: '用户名', sortable: true, halign: 'left'},
                {field: 'organizationName', title: '所属组织', sortable: true, halign: 'left'},
                {field: 'roleNames', title: '角色列表', sortable: true, halign: 'left'},
                {
                    field: 'action',
                    title: '操作',
                    halign: 'center',
                    align: 'center',
                    formatter: 'actionFormatter',
                    events: 'actionEvents',
                    clickToSelect: false
                }
            ]
        }).on('all.bs.table', function (e, name, args) {
            $('[data-toggle="tooltip"]').tooltip();
            $('[data-toggle="popover"]').popover();
        });
    });

    function actionFormatter(value, row, index) {
        return [
            '<a class="edit ml10" href="javascript:void(0)" data-toggle="tooltip" title="Edit"><i class="glyphicon glyphicon-edit"></i></a>　',
            '<a class="remove ml10" href="javascript:void(0)" data-toggle="tooltip" title="Remove"><i class="glyphicon glyphicon-remove"></i></a>'
        ].join('');
    }

    window.actionEvents = {
        'click .like': function (e, value, row, index) {
            alert('You click like icon, row: ' + JSON.stringify(row));
            console.log(value, row, index);
        },
        'click .edit': function (e, value, row, index) {
            alert('You click edit icon, row: ' + JSON.stringify(row));
            console.log(value, row, index);
        },
        'click .remove': function (e, value, row, index) {
            alert('You click remove icon, row: ' + JSON.stringify(row));
            console.log(value, row, index);
        }
    };

    var setting = {
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: onClick
        }
    };

    var zNodes = [
        <c:forEach items="${organizationList}" var="o">
        <c:if test="${not o.rootNode}">
        {id:${o.id}, pId:${o.parentId}, name: "${o.name}"},
        </c:if>
        </c:forEach>
    ];

    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("tree"),
            nodes = zTree.getSelectedNodes(),
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
        $("#organizationId").val(id);
        $("#organizationName").val(name);
        $("#edit-organizationId").val(id);
        $("#edit-organizationName").val(name);
        hideMenu();
    }

    function showMenu() {
        var cityObj = $("#organizationName");
        var cityOffset = $("#organizationName").offset();
        $("#menuContent").css({left: cityOffset.left + "px", top: cityOffset.top + cityObj.outerHeight() - 60 + "px"}).slideDown("fast");

        $("body").bind("mousedown", onBodyDown);
    }

    function showMenuOfEdit() {
        var cityObj = $("#edit-organizationName");
        var cityOffset = $("#edit-organizationName").offset();
        $("#menuContent").css({left: cityOffset.left + "px", top: cityOffset.top + cityObj.outerHeight() - 60 + "px"}).slideDown("fast");

        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }

    function onBodyDown(event) {
        if (!(event.target.id == "organizationName" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
            hideMenu();
        }
    }

    $.fn.zTree.init($("#tree"), setting, zNodes);
    $("#organizationName").click(showMenu);
    $("#edit-organizationName").click(showMenuOfEdit);

    // 新增
    function createAction() {
        $.confirm({
            type: 'dark',
            animationSpeed: 300,
            title: '新增系统',
            content: $('#createDialog').html(),
            buttons: {
                confirm: {
                    text: '确认',
                    btnClass: 'waves-effect waves-button',
                    action: function () {
                        $.alert('确认');
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'waves-effect waves-button'
                }
            }
        });
    }

    // 编辑
    function updateAction() {
        var rows = $table.bootstrapTable('getSelections');
        if (rows.length == 0) {
            $.confirm({
                title: false,
                content: '请至少选择一条记录！',
                autoClose: 'cancel|3000',
                backgroundDismiss: true,
                buttons: {
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
        } else {
            $.confirm({
                type: 'blue',
                animationSpeed: 300,
                title: '编辑系统',
                content: $('#createDialog').html(),
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            $.alert('确认');
                        }
                    },
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
        }
    }

    // 删除
    function deleteAction() {
        var rows = $table.bootstrapTable('getSelections');
        if (rows.length == 0) {
            $.confirm({
                title: false,
                content: '请至少选择一条记录！',
                autoClose: 'cancel|3000',
                backgroundDismiss: true,
                buttons: {
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
        } else {
            $.confirm({
                type: 'red',
                animationSpeed: 300,
                title: false,
                content: '确认删除该系统吗？',
                buttons: {
                    confirm: {
                        text: '确认',
                        btnClass: 'waves-effect waves-button',
                        action: function () {
                            var ids = new Array();
                            for (var i in rows) {
                                ids.push(rows[i].systemId);
                            }
                            $.alert('删除：id=' + ids.join("-"));
                        }
                    },
                    cancel: {
                        text: '取消',
                        btnClass: 'waves-effect waves-button'
                    }
                }
            });
        }
    }
</script>