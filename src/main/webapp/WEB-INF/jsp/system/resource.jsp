<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="wetechfn" uri="http://wetech.tech/admin/tags/wetech-functions" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style type="text/css">
    /*按钮*/
    .icon_div {
        display: inline-block;
        height: 25px;
        width: 35px;
    <%--background: url(<%=request.getContextPath()%>/static/img/f_icon.png) no-repeat 12px -127px;--%>
    }

    .icon_div a {
        display: inline-block;
        width: 27px;
        height: 20px;
        cursor: pointer;
    }

    /*end--按钮*/

    /*ztree表格*/
    .ztree {
        padding: 0;
        border: 1px solid #ccc;
    }

    .ztree li a {
        vertical-align: middle;
        height: 30px;
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
        width: 20%;
        line-height: 35px;
        border-top: 1px solid #ccc;
        border-left: 1px solid #eeeeee;
        text-align: center;
        display: inline-block;
        box-sizing: border-box;
        color: #6c6c6c;
        font-family: "Segoe UI", "Lucida Grande", Helvetica, Arial, "Microsoft YaHei", "Droid Sans", "Source Han Sans", "Hiragino Sans GB", "Hiragino Sans GB W3", "FontAwesome", sans-serif;
        font-size: 12px;
        overflow: hidden;
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

    /*end--ztree表格*/
</style>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding">
            <ol class="am-breadcrumb">
                <li><a href="#" class="am-icon-home">首页</a></li>
                <%--<li><a href="#">用户管理</a></li>--%>
                <li class="am-active">资源管理</li>
            </ol>
        </div>

        <div class="am-g">
            <div class="am-u-sm-12 am-u-md-6">
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <%--<button type="button" class="am-btn am-btn-default am-btn-primary" onclick="create();"><span class="am-icon-plus"></span> 新增</button>
                        <button type="button" class="am-btn am-btn-default am-btn-primary" onclick="update();"><span class="am-icon-edit"></span> 修改</button>
                        <button type="button" class="am-btn am-btn-default am-btn-primary" onclick="del();"><span class="am-icon-trash-o"></span> 删除</button>--%>
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
            <div id="tableMain">
                <ul id="dataTree" class="ztree">

                </ul>
            </div>
        </div>
    </div>

    <%-- footer start --%>
    <jsp:include page="footer.jsp"/>
    <%-- footer end --%>
</div>
<div id="add-dialog">
    <form class="am-form am-form-horizontal am-padding-top am-padding-bottom-0" id="add-form">
        <input type="hidden" name="parentId"/>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">上级节点<span class="asterisk">*</span></label>
            <div class="am-u-sm-10">
                <input type="text" name="parentName" placeholder="上级节点不存在" readonly required/>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">资源名称<span class="asterisk">*</span></label>
            <div class="am-u-sm-10">
                <input type="text" name="name" placeholder="资源的名称"  data-foolish-msg="请输入资源的名称" required>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">资源类型<span class="asterisk">*</span></label>
            <div class="am-u-sm-10">
                <select name="type">
                    <c:forEach items="${types}" var="type">
                        <option value="${type}">${type.info}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">URL路径</label>
            <div class="am-u-sm-10">
                <input type="text" name="url" placeholder="资源的URL路径">
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">权限字符串</label>
            <div class="am-u-sm-10">
                <input type="text" name="permission" placeholder="权限控制字符串">
            </div>
        </div>
    </form>
</div>
<div id="edit-dialog">
    <form class="am-form am-form-horizontal am-padding-top am-padding-bottom-0" id="edit-form">
        <input type="hidden" name="id"/>
        <input type="hidden" name="available"/>
        <input type="hidden" name="parentId"/>
        <input type="hidden" name="parentIds"/>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">资源名称<span class="asterisk">*</span></label>
            <div class="am-u-sm-10">
                <input type="text" name="name" placeholder="资源的名称"  data-foolish-msg="请输入资源的名称" required>
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">资源类型<span class="asterisk">*</span></label>
            <div class="am-u-sm-6">
                <select name="type">
                    <c:forEach items="${types}" var="type">
                        <option value="${type}">${type.info}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="am-u-sm-4"></div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">URL路径</label>
            <div class="am-u-sm-10">
                <input type="text" name="url" placeholder="资源的URL路径">
            </div>
        </div>
        <div class="am-form-group">
            <label class="am-u-sm-2 am-form-label">权限字符串</label>
            <div class="am-u-sm-10">
                <input type="text" name="permission" placeholder="权限控制字符串">
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">

    $(function () {

        var pathURL = basePath + '/resource/',
            createURL = pathURL + 'create',
            updateURL = pathURL + 'update';

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
                open:${resource.rootNode}
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
            aObj.append('<div class="diy swich"></div>');
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
            editStr += '<div class="diy">' + formatHandle(treeNode) + '</div>';
            aObj.append(editStr);
        }

        /**
         * 查询数据
         */
        function query() {
            //初始化列表
            //初始化树
            $.fn.zTree.init($("#dataTree"), setting, zNodes);
            //添加表头
            var li_head = ' <li class="head"><a><div class="diy">名称</div><div class="diy">类型</div><div class="diy">URL路径</div>' +
                '<div class="diy">权限字符串</div><div class="diy">操作</div></a></li>';
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
            var htmlStr = '<div class="am-btn-group">';
            <shiro:hasPermission name="resource:create">
            if (treeNode.type != 'button') {
                htmlStr += '<button type="button" onclick="createChild(\'' + treeNode.tId + '\');" class="am-btn am-btn-primary am-radius"><span class="am-icon-plus"></span> 添加子节点</button>';
            }
            </shiro:hasPermission>
            <shiro:hasPermission name="resource:update">
            htmlStr += '<button type="button" onclick="update(\'' + treeNode.tId + '\');" class="am-btn am-btn-warning am-radius"><span class="am-icon-edit"></span> 修改</button>';
            </shiro:hasPermission>
            <shiro:hasPermission name="resource:delete">
            if (treeNode.isParent != true) {
                htmlStr += '<button type="button" onclick="del(\'' + treeNode.tId + '\');" class="am-btn am-btn-danger am-radius"><span class="am-icon-trash-o"></span> 删除</button>';
            }
            </shiro:hasPermission>
            return htmlStr + '</div>';
        }

        //初始化数据
        query();


        createChild = function (tId) {
            var treeObj = $.fn.zTree.getZTreeObj("dataTree");
            var node = treeObj.getNodeByTId(tId);
            $('#add-form [name="parentId"]').val(node.id);
            $('#add-form [name="parentName"]').val(node.name);
            var $form = $('#add-form');
            var opts = {
                title: '添加资源',
                yes: function (index, layero) {
                    if ($form.isFormValid()) {
                        $form.submit({
                            url:createURL,
                            callback:function(data) {
                                if(data.success == true) {
                                    $.closeDialog(index);
                                    loadContent('#resource');
//                                    var treeObj = $.fn.zTree.getZTreeObj("tree");
//                                    var nodes = treeObj.getSelectedNodes();
//                                    treeObj.reAsyncChildNodes(null, "refresh");
                                }
                            }
                        });
                    }
                }
            };
            $('#add-dialog').openDialog(opts);
        };

        update = function (tId) {
            var treeObj = $.fn.zTree.getZTreeObj("dataTree");
            var node = treeObj.getNodeByTId(tId);
            // 将值赋给编辑表单
            $.each(node, function (key, value) {
                if ($('#edit-form [name="' + key + '"]').attr('type') == 'radio') {
                    $('#edit-form [name="' + key + '"][value="' + value + '"]').prop('checked', true);
                } else {
                    $('#edit-form [name="' + key + '"]').val(value);
                }
            });
            $('#edit-form [name="parentId"]').val(node.pId ? node.pId : "0");
            $('#edit-form [name="url"]').val(node.rUrl);
            var $form = $('#edit-form');
            var opts = {
                title: '修改资源',
                yes: function (index, layero) {
                    if ($form.isFormValid()) {
                        $form.submit({
                            url: updateURL,
                            callback: function (data) {
                                if (data.success == true) {
                                    $.closeDialog(index);
                                    loadContent('#resource');
                                }
                            }
                        });
                    }
                }
            };
            $('#edit-dialog').openDialog(opts);
        };

        del = function (tId) {
            var treeObj = $.fn.zTree.getZTreeObj("dataTree");
            var node = treeObj.getNodeByTId(tId);
            $.confirm('确定要删除 [' + node.name + '] 吗？', {
                icon: 3,
                title: '系统提示',
                yes: function (index, layero) {
                    // 发送异步请求
                    $.ajax({
                        type: 'post',
                        url: pathURL + node.id + '/delete',
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
                            loadContent('#resource');
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
        };

        /**
         * 重置
         */
        reset = function () {
            loadContent('#resource');
        }

    })
</script>