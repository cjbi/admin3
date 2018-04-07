<%--
  Created by IntelliJ IDEA.
  User: cjbi
  Date: 2017/9/29
  Time: 0:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path;
%>
<!doctype html>
<html class="no-js fixed-layout">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Wetech Admin</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/static/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="<%=request.getContextPath()%>/static/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Wetech Admin"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/amazeui.min.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/amazeui.datatables.min.css"/>
    <%--<link rel="stylesheet" href="<%=request.getContextPath()%>/static/libs/Buttons-1.5.0/css/buttons.dataTables.min.css"/>--%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/libs/Select-1.2.4/css/select.dataTables.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/amazeui.datatables.select.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/libs/zTree_v3/css/metroStyle/metroStyle.css">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/amazeui.datatables.buttons.css"/>
   <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/admin.css">
</head>
<body>
<%-- header start --%>
<jsp:include page="header.jsp"/>
<%-- header end --%>
<div class="am-cf admin-main">
    <%-- sidebar start --%>
    <jsp:include page="sidebar.jsp"/>
    <%-- sidebar end --%>
    <%-- content start --%>
     <content id="admin-content">
        <div class="admin-content">
            <div class="admin-content-body">
                <div class="am-cf am-padding">
                    <ol class="am-breadcrumb">
                        <li><a href="#" class="am-icon-home">首页</a></li>
                        <%--<li><a href="#">用户管理</a></li>--%>
                        <li class="am-active">没有内容</li>
                    </ol>
                </div>
            </div>
            <%-- footer start --%>
            <jsp:include page="footer.jsp"/>
            <%-- footer end --%>
        </div>
    </content>
    <%-- content end --%>
</div>
<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
<input id="basePath" type="hidden" value="<%=basePath%>"/>
<!--[if lt IE 9]>
<script src="//libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="//cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="//cdn.amazeui.org/amazeui/2.7.2/js/amazeui.ie8polyfill.js"></script>
<![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="<%=request.getContextPath()%>/static/js/amazeui.js"></script>

<script src="<%=request.getContextPath()%>/static/libs/DataTables-1.10.16/media/js/jquery.dataTables.min.js"></script>

<script src="<%=request.getContextPath()%>/static/libs/Buttons-1.5.0/js/dataTables.buttons.min.js"></script>
<script src="<%=request.getContextPath()%>/static/libs/Buttons-1.5.0/js/buttons.colVis.min.js"></script>
<script src="<%=request.getContextPath()%>/static/libs/jszip-3.1.5/dist/jszip.min.js"></script>
<script src="<%=request.getContextPath()%>/static/libs/Buttons-1.5.0/js/buttons.html5.min.js"></script>
<script src="<%=request.getContextPath()%>/static/libs/Buttons-1.5.0/js/buttons.print.min.js"></script>

<script src="<%=request.getContextPath()%>/static/libs/Select-1.2.4/js/dataTables.select.js"></script>

<script src="<%=request.getContextPath()%>/static/libs/layer-v3.1.0/layer/layer.js"></script>
<script src="<%=request.getContextPath()%>/static/libs/zTree_v3/js/jquery.ztree.all.min.js"></script>

<script src="<%=request.getContextPath()%>/static/js/amazeui.datatables.js"></script>
<script src="<%=request.getContextPath()%>/static/js/amazeui.datatables.buttons.js"></script>
<script src="<%=request.getContextPath()%>/static/js/common/utils.js"></script>
<script src="<%=request.getContextPath()%>/static/js/admin.js"></script>
</body>
</html>
