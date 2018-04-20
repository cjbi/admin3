<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="wetechfn" uri="http://wetech.tech/admin/tags/wetech-functions" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style type="text/css">
    /*按钮*/
    .icon_div a {
        display: inline-block;
        width: 27px;
        height: 20px;
        cursor: pointer;
    }

    /*end--按钮*/

    /*ztree表格*/
    .ztree * {
        font-family: 'Glyphicons Halflings', 'FontAwesome';
    }

    .panel {
        padding: 0;
        border-color:#ccc;
        min-width: 800px;
    }

    /*.ztree {
        padding: 0;
        border: 1px solid #ccc;
        min-width: 800px;
    }*/

    .ztree li a {
        vertical-align: middle;
        height: 30px;
        color: #3c8dbc;
    }

    .ztree li > a {
        width: 100%;
    }

    .ztree li > a,
    .ztree li a.curSelectedNode {
        padding-top: 0px;
        background: none;
        height: auto;
        border: none;
        cursor: default;
        opacity: 1;
    }

    .ztree li ul {
        padding-left: 0px
    }

    .ztree div.diy span {
        vertical-align: middle;
    }

    .ztree div.diy {
        height: 100%;
        width: 11.72%;
        line-height: 35px;
        border-top: 1px solid #eee;
        border-left: 1px solid #eee;
        text-align: center;
        display: inline-block;
        box-sizing: border-box;
        color: #6c6c6c;
        font-family: "Segoe UI", "Lucida Grande", Helvetica, Arial, "Microsoft YaHei", "Droid Sans", "Source Han Sans", "Hiragino Sans GB", "Hiragino Sans GB W3", "FontAwesome", sans-serif;
        font-size: 12px;
        /*超出部分省略号*/
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .ztree div.diy:first-child {
        text-align: left;
        text-indent: 10px;
        border-left: none;
    }

    .ztree .head {
        background: #f8f8f8;
    }

    .ztree li {
        line-height: 0px;
    }

    .ztree .head div.diy {
        border-top: none;
        /*border-right: 1px solid #CDD2D4;*/
        /*color: #fff;*/
        font-family: "Microsoft YaHei";
        font-size: 14px;
    }

    span[treenode_ico] {
        position: relative;
        top: 8px;
    }

    /* Hover effect */
    .ztree li:hover > a {
        background-color: #f0f0f0;
    }

    .btn-group {
        text-align: center;
        line-height: 25px;
    }

    .btn-group > a {
        padding-left: 10px;
    }
    .box-body {
        overflow: auto;
    }

    /*end--ztree表格*/
</style>
<!-- Content Header (Page header) -->
<section class="content-header" style="">
    <h1>
        资源管理
        <small>资源管理的页面</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> 主页</a></li>
        <li><a href="#">资源管理</a></li>
        <li class="active">资源管理</li>
    </ol>
</section>

<!-- Main content -->
<section class="content">
    <div class="row">
        <div class="col-xs-12">
            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <div class="btn-group-sm" role="group">
                        <button type="button" class="btn btn-default" name="refresh" title="刷新" onclick="javascript:$.myAdmin.refreshContent();">
                            <i class="glyphicon glyphicon-refresh icon-refresh"></i>
                            刷新
                        </button>
                    </div>

                </div>
                <div class="box-body">
                    <div id="tableMain">
                        <ul id="dataTree" class="ztree panel">

                        </ul>
                    </div>
                </div>
                <!-- /.box-body -->
            </div>
        </div>
    </div>
    <!-- /.box -->
</section>

<!-- add Modal -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="addModalLabel">添加角色</h4>
            </div>
            <div class="modal-body">
                <form id="addForm" onsubmit="return false;">
                    <input type="hidden" name="parentId" required/>
                    <div class="form-group">
                        <label class="control-label" for="parentName">上级节点:</label>
                        <input type="text" class="form-control" id="parentName" name="parentName" placeholder="上级节点不存在" readonly required/>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="name"><span class="asterisk">*</span>资源名称:</label>
                        <input type="text" class="form-control" name="name" id="name" placeholder="资源的名称" required>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="type"><span class="asterisk">*</span>资源类型:</label>
                        <select class="form-control" name="type" id="type">
                            <c:forEach items="${types}" var="type">
                                <option value="${type}">${type.info}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="leaf"><span class="asterisk">*</span>叶子节点:</label>
                        <select class="form-control" name="leaf" id="leaf">
                            <option value="0" selected>否</option>
                            <option value="1">是</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="url">URL路径:</label>
                        <input type="text" class="form-control" name="url" id="url" placeholder="资源的URL路径，‘#’+资源名，或者站外地址">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="permission">权限字符串:</label>
                        <input type="text" class="form-control" name="permission" id="permission" placeholder="权限控制字符串，不填说明不做权限控制">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="icon">图标:</label>
                        <input type="text" class="form-control" name="icon" id="icon" placeholder="支持 Glyphicons 和 FontAwesome 图标，只支持菜单">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="priority">排序:</label>
                        <input type="number" class="form-control" name="priority" id="priority" placeholder="资源的排序">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" form="addForm" class="btn btn-primary"
                        data-action="{type:'submit',form:'#addForm',url:'<%=request.getContextPath()%>/resource/create',after:'$.myAction.refreshContent'}">
                    确定
                </button>
            </div>
        </div>
    </div>
</div>

<!-- edit Modal -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="editModalLabel">添加角色</h4>
            </div>
            <div class="modal-body">
                <form id="editForm" onsubmit="return false;">
                    <input type="hidden" name="id" required/>
                    <input type="hidden" name="available" required/>
                    <input type="hidden" name="parentId" required/>
                    <input type="hidden" name="parentIds" required/>
                    <div class="form-group">
                        <label class="control-label" for="name"><span class="asterisk">*</span>资源名称:</label>
                        <input type="text" class="form-control" name="name" id="editName" placeholder="资源的名称" required>
                        <div class="help-block with-errors"></div>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="editType"><span class="asterisk">*</span>资源类型:</label>
                        <select class="form-control" name="type" id="editType">
                            <c:forEach items="${types}" var="type">
                                <option value="${type}">${type.info}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="editLeaf"><span class="asterisk">*</span>叶子节点:</label>
                        <select class="form-control" name="leaf" id="editLeaf">
                            <option value="0" selected>否</option>
                            <option value="1">是</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="editUrl">URL路径:</label>
                        <input type="text" class="form-control" name="url" id="editUrl" placeholder="资源的URL路径，‘#’+资源名，或者站外地址">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="editPermission">权限字符串:</label>
                        <input type="text" class="form-control" name="permission" id="editPermission" placeholder="权限控制字符串，不填说明不做权限控制">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="editIcon">图标:</label>
                        <input type="text" class="form-control" name="icon" id="editIcon" placeholder="支持 Glyphicons 和 FontAwesome 图标，只支持菜单">
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="editPriority">排序:</label>
                        <input type="number" class="form-control" name="priority" id="editPriority" placeholder="资源的排序">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" form="editForm" class="btn btn-primary"
                        data-action="{type:'submit',form:'#editForm',url:'<%=request.getContextPath()%>/resource/update',after:'$.myAction.refreshContent'}">
                    确定
                </button>
            </div>
        </div>
    </div>
</div>

<!-- delete modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteSmallModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="deleteSmallModalLabel">删除资源</h4>
            </div>
            <div class="modal-body">
                <form id="deleteForm">
                    <input type="hidden" name="id">
                    <input type="hidden" name="parentId">
                </form>
                确定要删除该资源?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" form="deleteForm" class="btn btn-primary"
                        data-action="{type:'submit',form:'#deleteForm',url:'<%=request.getContextPath()%>/resource/delete',after:'$.myAction.refreshContent'}">
                    确定
                </button>
            </div>
        </div>
    </div>
</div>

<!-- /.content -->
<script type="text/javascript">
    var like, createChild, update, remove;
    $(function () {
        like = function(tId) {
            var treeObj = $.fn.zTree.getZTreeObj("dataTree");
            var node = treeObj.getNodeByTId(tId);
            console.log(node);
            alert(JSON.stringify(node));
        }
        createChild = function (tId) {
            var treeObj = $.fn.zTree.getZTreeObj("dataTree");
            var node = treeObj.getNodeByTId(tId);
            $('#addForm [name="parentId"]').val(node.id);
            $('#addForm [name="parentName"]').val(node.name);
            $('#addModal').modal('show');
        };

        update = function (tId) {
            var treeObj = $.fn.zTree.getZTreeObj("dataTree");
            var node = treeObj.getNodeByTId(tId);
            // 将值赋给编辑表单
            $('#editForm').fillForm(node);
            $('#editForm [name="parentId"]').val(node.pId ? node.pId : "0");
            $('#editForm [name="url"]').val(node.rUrl);
            $('#editForm [name="icon"]').val(node.rIcon);
            $(this).validator('validate');
            $('#editModal').modal('show');
        };

        remove = function (tId) {
            var treeObj = $.fn.zTree.getZTreeObj("dataTree");
            var node = treeObj.getNodeByTId(tId);
            debugger;
            $('#deleteForm').fillForm(node);
            $('#deleteForm [name="parentId"]').val(node.pId ? node.pId : "0");
            $('#deleteModal').modal('show');
        };

        var zNodes = [
            <c:forEach items="${resourceList}" var="resource">
            {
                id:${resource.id},
                pId:${resource.parentId},
                name: "${resource.name}",
                parentIds: "${resource.parentIds}",
                type: "${resource.type}",
                typeName: "${resource.type.info}",
                rUrl: "${resource.url}",
                permission: "${resource.permission}",
                available: ${resource.available},
                open:${resource.rootNode},
                rIcon: "${resource.icon}",
                priority: "${resource.priority}",
            },
            </c:forEach>
        ];
        var setting = {
            view: {
                showLine: false,
                showIcon: true,
                addDiyDom: addDiyDom,
                txtSelectedEnable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };

        /**
         * 自定义DOM节点
         */
        function addDiyDom(treeId, treeNode) {
            var spaceWidth = 15;
            var liObj = $("#" + treeNode.tId);
            var aObj = $("#" + treeNode.tId + "_a");
            var switchObj = $("#" + treeNode.tId + "_switch");
            var icoObj = $("#" + treeNode.tId + "_ico");
            var spanObj = $("#" + treeNode.tId + "_span");
            aObj.attr('title', '');
            aObj.append('<div class="diy switch" style="width: 30%;"></div>');
            var div = $(liObj).find('div').eq(0);
            switchObj.remove();
            spanObj.remove();
            icoObj.remove();
            div.append(switchObj);
            div.append(icoObj);
            div.append(spanObj);
            var spaceStr = "<span style='height:1px;display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
            switchObj.before(spaceStr);
            var editStr = '';
            editStr += '<div class="diy">' + (treeNode.typeName || '&nbsp;') + '</div>';
            editStr += '<div class="diy">' + (treeNode.rUrl || '&nbsp;') + '</div>';
            editStr += '<div class="diy">' + (treeNode.permission || '&nbsp;') + '</div>';
            editStr += '<div class="diy"><span class="' + (treeNode.rIcon || '&nbsp;') + '"></span></div>';
            editStr += '<div class="diy">' + (treeNode.priority || '&nbsp;') + '</div>';
            editStr += '<div class="diy">' + formatHandle(treeNode) + '</div>';
            aObj.append(editStr);
            $('[data-toggle="tooltip"]').tooltip({container: 'body'});
            $('[data-toggle="popover"]').popover();
        }

        /**
         * 查询数据
         */
        function query() {
            //初始化列表
            //初始化树
            $.fn.zTree.init($("#dataTree"), setting, zNodes);
            //添加表头
            var li_head = ' <li class="head"><a><div class="diy" style="width: 30%;">名称</div><div class="diy">类型</div><div class="diy">URL路径</div>' +
                '<div class="diy">权限字符串</div><div class="diy">图标</div><div class="diy">序号</div><div class="diy">操作</div></a></li>';
            var rows = $("#dataTree").find('li');
            if (rows.length > 0) {
                rows.eq(0).before(li_head)
            } else {
                $("#dataTree").append(li_head);
                $("#dataTree").append('<li ><div style="text-align: center;line-height: 30px;" >无符合条件数据</div></li>')
            }
        }

        /**
         * 根据权限展示功能按钮
         * @param treeNode
         * @returns {string}
         */
        function formatHandle(treeNode) {
            var htmlStr = '<div class="btn-group">';
            htmlStr = '<a class="like" href="javascript:void(0)" data-toggle="tooltip" title="查看" onclick="like(\'' + treeNode.tId + '\');"><i class="glyphicon glyphicon-heart"></i></a>　';
            <shiro:hasPermission name="resource:create">
            if (treeNode.type != 'button') {
                htmlStr += '<a class="add ml10" href="javascript:void(0)" data-toggle="tooltip" title="" data-original-title="新增" onclick="createChild(\'' + treeNode.tId + '\');"><i class="glyphicon glyphicon-plus"></i></a>　';
            }
            </shiro:hasPermission>
            <shiro:hasPermission name="resource:update">
            htmlStr += '<a class="edit ml10" href="javascript:void(0)" data-toggle="tooltip" title="" data-original-title="修改" onclick="update(\'' + treeNode.tId + '\');"><i class="glyphicon glyphicon-edit"></i></a>　';
            </shiro:hasPermission>
            <shiro:hasPermission name="resource:delete">
            if (treeNode.isParent != true) {
                htmlStr += '<a class="delete ml10" href="javascript:void(0)" data-toggle="tooltip" title="" data-original-title="删除" onclick="remove(\'' + treeNode.tId + '\');"><i class="glyphicon glyphicon-remove"></i></a>　';
            }
            </shiro:hasPermission>
            return htmlStr + '</div>';
        }

        //初始化数据
        query();

    })
</script>