<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="wetechfn" uri="http://wetech.tech/admin/tags/wetech-functions" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style type="text/css">
    div#rMenu {
        position: absolute;
        visibility: hidden;
        padding: 2px;
        font-size: 14px;
    }

    div#rMenu ul {
        min-width: 50px;
    }

    .ztree li {
        padding: 0;
        margin: 0;
        list-style: none;
        line-height: 20px;
        text-align: left;
        white-space: nowrap
    }

    .ztree li span {
        margin-right: 2px;
        font-weight: lighter;
        font-size: 14px;
        color: #000;
        font-family: inherit;
    }
</style>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding">
            <ol class="am-breadcrumb">
                <li><a href="#" class="am-icon-home">首页</a></li>
                <%--<li><a href="#">用户管理</a></li>--%>
                <li class="am-active">组织机构树管理</li>
            </ol>
        </div>
        <div class="am-u-md-3 am-u-sm-12">
            <div class="am-panel am-panel-default">
                <div class="am-panel-hd">组织机构树</div>
                <div class="am-panel-bd" style="overflow-x: auto;">
                    <ul id="tree" class="ztree context-menu-one btn btn-neutral"></ul>
                </div>
            </div>
            <div class="am-panel am-panel-default">
                <div class="am-panel-bd">
                    <p>
                        <span class="am-icon-bookmark"></span> 提示
                    </p>
                    <p>鼠标单击右键操作树节点 : )</p>
                </div>
            </div>
        </div>
        <div class="am-u-md-7 am-u-sm-12 am-u-end">
            <div class="am-panel am-panel-default">
                <div class="am-panel-hd">组织机构树管理</div>
                <div class="am-panel-bd">
                    <form class="am-form am-form-horizontal" onsubmit="return false;" id="edit-form">
                        <input type="hidden" name="id"/>
                        <input type="hidden" name="available"/>
                        <input type="hidden" name="parentId"/>
                        <input type="hidden" name="parentIds"/>
                        <div class="am-form-group">
                            <label class="am-u-sm-3 am-form-label">组织机构名称<span class="asterisk">*</span></label>
                            <div class="am-u-sm-9">
                                <input type="text" name="name" placeholder="组织机构的名称" data-foolish-msg="请输入组织机构的名称" required>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-3 am-form-label">序号</label>
                            <div class="am-u-sm-9">
                                <input type="text" name="priority" placeholder="节点的序号">
                            </div>
                        </div>
                        <shiro:hasPermission name="organization:update">
                            <div class="am-form-group">
                                <div class="am-u-sm-end am-text-right">
                                    <button type="submit" class="am-btn am-btn-primary am-radius">保存</button>
                                    <button type="reset" class="am-btn am-btn-warning am-radius">重置</button>
                                </div>
                            </div>
                        </shiro:hasPermission>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <%-- footer start --%>
    <jsp:include page="footer.jsp"/>
    <%-- footer end --%>
</div>
<%-- 隐藏的模态框 --%>
<div id="rAdd-dialog" class="admin-hide">
    <form class="am-form am-form-horizontal am-padding-top am-padding-bottom-0" id="add-form">
        <input type="hidden" name="parentId" required/>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">上级节点</label>
            <div class="am-u-sm-10">
                <input type="text" name="parentName" placeholder="上级节点不存在" readonly required/>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">节点名称<span class="asterisk">*</span></label>
            <div class="am-u-sm-10">
                <input type="text" name="name" placeholder="节点的名称" data-foolish-msg="请输入节点的名称" required>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">排序</label>
            <div class="am-u-sm-10">
                <input type="text" name="priority" placeholder="节点的序号">
            </div>
        </div>
    </form>
</div>
<%-- 隐藏的右键菜单 --%>
<div id="rMenu">
    <ul class="am-dropdown-content">
        <shiro:hasPermission name="organization:create">
            <li><a href="#" id="rAdd-chi">添加子节点</a></li>
        </shiro:hasPermission>
        <shiro:hasPermission name="organization:delete">
            <li><a href="#" id="rDel">删除节点</a></li>
        </shiro:hasPermission>
    </ul>
</div>
<script type="text/javascript">
    $(function () {

        var pathURL = basePath + '/organization/',
            createURL = pathURL + 'create',
            updateURL = pathURL + 'update';

        var rMenu = $('#rMenu'),
            setting = {
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    asyncError: zTreeOnAsyncError, // 加载错误的fun
                    beforeClick: beforeClick, // 捕获单击节点之前的事件回调函数
                    onRightClick: OnRightClick
                }
            },
            zNodes = [
                <c:forEach items="${organizationList}" var="o">
                {id:${o.id}, pId:${o.parentId}, name: "${o.name}", parentIds: "${o.parentIds}", available: ${o.available},priority: '${o.priority}', open:${o.rootNode}},
                </c:forEach>
            ];

        $(document).ready(function () {
            $.fn.zTree.init($("#tree"), setting, zNodes);
        });

        // 加载错误的fun
        function zTreeOnAsyncError(event, treeId, treeNode) {
            alert('数据加载失败!');
        }

        // 点击之后的动作
        function beforeClick(treeId, treeNode) {
            // 销毁表单验证
            $('#edit-form').validator('destroy');
            // 取消被选中状态
            $('#edit-form [type="radio"]').removeAttr('checked');
            // 将值赋给编辑表单
            $.each(treeNode, function (key, value) {
                if ($('#edit-form [name="' + key + '"]').attr('type') == 'radio') {
                    $('#edit-form [name="' + key + '"][value="' + value + '"]').prop('checked', true);
                } else {
                    $('#edit-form [name="' + key + '"]').val(value);
                }
            });
            $('#edit-form [name="parentId"]').val(treeNode.pId ? treeNode.pId : "0");
        }

        //  右键触发事件
        // 在ztree上的右击事件
        function OnRightClick(event, treeId, treeNode) {
            // 是否叶子节点
            try {
                // 在这里运行代码
                showRMenu(event.clientX, event.clientY, treeNode.id, treeNode.name, treeNode.pId, treeNode);
            } catch (err) {
                // 在这里处理错误
                console.log(err);
            }
        }

        // 显示右键菜单
        function showRMenu(x, y, id, pName, pId, treeNode) {
            $('#add-form [name="parentId"]').val(id);
            $('#add-form [name="parentName"]').val(pName);
            $('#rMenu ul').show();
            // 是否父id为0
            if (treeNode.isParent) {
                $('#rDel').hide();
            } else {
                $('#rDel').show();
            }

            rMenu.css({
                "top": y - 60 + "px",
                "left": x + "px",
                "visibility": "visible"
            }); // 设置右键菜单的位置、可见
            $("body").bind("mousedown", onBodyMouseDown);
        }

        // 隐藏右键菜单
        function hideRMenu() {
            if (rMenu)
                rMenu.css({
                    "visibility": "hidden"
                }); // 设置右键菜单不可见
            $("body").unbind("mousedown", onBodyMouseDown);
        }

        // 鼠标按下事件
        function onBodyMouseDown(event) {
            if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
                rMenu.css({
                    "visibility": "hidden"
                });
            }
        }

        //  rDel删除节点
        $('#rDel').click(function () {
            var id = $('#add-form [name="parentId"]').val();
            $.mydialog.confirm('确定要删除 [' + $('#add-form [name="parentName"]').val() + '] 吗？', {
                icon: 3,
                title: '系统提示',
                yes: function (index, layero) {
                    // 发送异步请求
                    $.ajax({
                        type: 'post',
                        url: pathURL + id + '/delete',
                        dataType: 'json',
                        success: function (data) {
                            if (data.success == false) {
                                layer.msg(data.msg, {
                                    time: '2000',
                                    icon: 0
                                });
                                return;
                            }
                            layer.msg(data.msg, {
                                time: '2000',
                                icon: 6
                            });
                            $.myadmin.loadContent('#organization');
//                            var treeObj = $.fn.zTree.getZTreeObj("tree");
//                            var nodes = treeObj.getSelectedNodes();
//                            treeObj.reAsyncChildNodes(null, "refresh");
                        },
                        error: function (data) {
                            layer.msg('操作失败', {
                                time: 2000,
                                icon: 5
                            });
                        }
                    });
                }
            });
        });

        //  新增节点的模态框表单提交
        $('#rAdd-chi').click(function () {
            var $form = $('#add-form');
            var opts = {
                title: '添加节点',
                yes: function (index, layero) {
                    if ($form.isFormValid()) {
                        $form.submit({
                            url: createURL,
                            callback: function (data) {
                                if (data.success == true) {
                                    $.mydialog.closeDialog(index);
                                    $.myadmin.loadContent('#organization');
//                                    var treeObj = $.fn.zTree.getZTreeObj("tree");
//                                    var nodes = treeObj.getSelectedNodes();
//                                    treeObj.reAsyncChildNodes(null, "refresh");
                                }
                            }
                        });
                    }
                }
            };
            $('#rAdd-dialog').openDialog(opts);
        });

        //  右边编辑表单提交
        $('#edit-form [type="submit"]').on('click', function () {
            var $form = $('#edit-form');
            if ($form.isFormValid()) {
                $form.submit({
                    url: updateURL,
                    callback: function (data) {
                        $.myadmin.loadContent('#organization');
                    }
                });
            }
        });
    });
</script>