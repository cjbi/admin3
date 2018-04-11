<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>管理控制台 | Wetech Admin</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bower_components/font-awesome/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/bower_components/Ionicons/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/skins/_all-skins.min.css">
    <!-- Pace style -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/plugins/pace/pace.min.css">
    <!-- zTree style -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/plugins/zTree_v3-3.5.33/css/metroStyle/metroStyle.css">
    <!-- Bootstrap-table -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/plugins/bootstrap-table/dist/bootstrap-table.min.css">
    <!-- Animate -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/plugins/animate.css-3.5.2/animate.min.css">
    <!-- chosen -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/plugins/chosen_v1.8.3/chosen.min.css">
    <!-- 自定义样式 -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/admin.css">


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
        var path = '<%=request.getContextPath()%>';
    </script>
    <!-- Google Font -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition skin-blue sidebar-mini fixed">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="header.jsp" %>

    <%@include file="main-sidebar.jsp" %>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" id="content-wrapper">
        <%--<%@include file="content-wrapper.jsp" %>--%>
    </div>
    <!-- /.content-wrapper -->

    <%@include file="footer.jsp"%>

    <%@ include file="control-sidebar.jsp" %>

    <!-- Add the sidebar's background. This div must be placed
         immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->
<!-- jQuery 3 -->
<script src="<%=request.getContextPath()%>/static/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<%=request.getContextPath()%>/static/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="<%=request.getContextPath()%>/static/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=request.getContextPath()%>/static/bower_components/fastclick/lib/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="<%=request.getContextPath()%>/static/js/adminlte.min.js"></script>
<!-- PACE -->
<script src="<%=request.getContextPath()%>/static/bower_components/PACE/pace.min.js"></script>
<!-- zTree script -->
<script src="<%=request.getContextPath()%>/static/plugins/zTree_v3-3.5.33/js/jquery.ztree.all.min.js"></script>
<!-- Bootstrap-table -->
<script src="<%=request.getContextPath()%>/static/plugins/bootstrap-table/dist/bootstrap-table.min.js"></script>
<script src="<%=request.getContextPath()%>/static/plugins/bootstrap-table/dist/locale/bootstrap-table-zh-CN.js"></script>
<!-- Bootstrap-notify-3.1.3 -->
<script src="<%=request.getContextPath()%>/static/plugins/bootstrap-notify-3.1.3/dist/bootstrap-notify.min.js"></script>
<!-- Bootstrap-validator-0.11.9 -->
<script src="<%=request.getContextPath()%>/static/plugins/bootstrap-validator-0.11.9/dist/validator.min.js"></script>
<!--chosen -->
<script src="<%=request.getContextPath()%>/static/plugins/chosen_v1.8.3/chosen.jquery.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="<%=request.getContextPath()%>/static/js/demo.js"></script>

<script src="<%=request.getContextPath()%>/static/js/admin.js"></script>
<script src="<%=request.getContextPath()%>/static/js/common/utils.js"></script>
</body>
</html>

