<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="wetechfn" uri="http://wetech.tech/admin/tags/wetech-functions" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding">
            <ol class="am-breadcrumb">
                <li><a href="#" class="am-icon-home">首页</a></li>
                <%--<li><a href="#">用户管理</a></li>--%>
                <li class="am-active am-title">日志管理</li>
            </ol>
        </div>

        <div class="am-g am-btn-toolbar">
            <div class="am-u-sm-12 am-u-md-6">
                <div class="am-btn-group am-btn-group-xs">
                </div>
            </div>
            <div class="am-u-sm-12 am-u-md-3">
                <div class="am-form-group">
                    <%-- <select data-am-selected="{btnSize: 'sm'}">
                         <option value="option1">全部</option>
                         &lt;%&ndash;<option value="option2">文章</option>
                         <option value="option3">合作文章</option>
                         <option value="option3">未审核</option>&ndash;%&gt;
                     </select>--%>
                </div>
            </div>
            <div class="am-u-sm-12 am-u-md-3">
                <div class="am-input-group am-input-group-sm">
                    <input type="text" name="keywords" id="keywords" placeholder="用户名 / IP / 请求方法 / 执行方法 / 执行描述 / 状态" class="am-form-field">
                    <span class="am-input-group-btn">
                        <button class="am-btn am-btn-primary" onclick="$.mytables.reloadTable();" type="button">搜索</button>
                    </span>
                </div>
            </div>
        </div>

        <div class="am-u-sm-12">
            <table class="am-table am-table-striped  am-table-hover table-main am-table-bordered<%-- am-text-nowrap--%>" width="100%" id="example_log">
                <thead>
                <tr>
                    <th><span style="display: none;">ID</span>&nbsp;</th>
                    <th>用户</th>
                    <th>IP</th>
                    <th>操作描述</th>
                    <th>状态</th>
                    <th>时间</th>
                    <th>执行方法</th>
                    <th>耗时</th>
                    <th>请求方法</th>
                    <th>请求URL</th>
                    <th>参数</th>
                    <th>返回值</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <%-- footer start --%>
    <jsp:include page="footer.jsp"/>
    <%-- footer end --%>
</div>
<script type="text/javascript">
    $(function () {
        var pathURL = basePath + '/log/',
            listURL = pathURL + 'list';

        var ajax = {
            'url': listURL,
            'data': function (data) {
                var keywords = $('#keywords').val();
                if (keywords) {
                    data.keywords = ($('#keywords').val());
                }
            }
        };

        var columns = [
            {
                'data': 'id',
                'width': '2%',
                'fnCreatedCell': function (nTd, sData, oData, iRow, iCol) {
                    $(nTd).html('');
                }
            },
            {
                'data': 'username'
            },
            {
                'data': 'ip'
            },
            {
                'data': 'execDesc'
            },
            {
                'data': 'status'
            },
            {
                'data': 'createTime',
                'render': function (data, type, full) {
                    return Utils.dateFormat.formatTimestamp(data, 'yyyy-MM-dd hh:mm:ss');
                }
            },
            {
                'data': 'execMethod',
                'visible': false
            },
            {
                'data': 'execTime',
                'render': function (data, type, full) {
                    return data + ' ms';
                },
                'visible': false
            },
            {
                'data': 'reqMethod',
                'visible': false
            },
            {
                'data': 'reqUri',
                'visible': false
            },
            {
                'data': 'args',
                'visible': false
            },
            {
                'data': 'returnVal',
                'visible': false
            }
        ];
        var opts = {
            'ajax': ajax,
            'columns': columns,
            'tableId': 'example_log'
        };

        var table = $.mytables.initTable(opts);

    });
</script>