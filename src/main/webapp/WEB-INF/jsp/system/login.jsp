<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!doctype html>
<html class="no-js fixed-layout">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Amaze UI Admin index Examples</title>
    <meta name="description" content="这是一个 index 页面">
    <meta name="keywords" content="index">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/static/i/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="<%=request.getContextPath()%>/static/i/app-icon72x72@2x.png">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/amazeui.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/admin.css"/>
</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
    以获得更好的体验！</p>
<![endif]-->
<div class="login-cover">
    <div class="login-info am-cf">
        <form action="" method="post">
            <div class="login-info-form">
                <div class="login-info-form-content">
                    <div class="am-g">
                        <div class="am-u-lg-12">
                            <div class="login-logo">
                                <img src="<%=request.getContextPath()%>/static/i/logo-off.png" alt="">
                            </div>
                        </div>
                        <div class="am-u-lg-12">
                            <div class="am-input-group">
                                <span class="am-input-group-label"><i class="am-icon-user am-icon-fw"></i></span>
                                <input type="text" name="username" value="<shiro:principal/>" class="am-form-field" placeholder="Username">
                            </div>
                        </div>
                        <div class="am-u-lg-12">
                            <div class="am-input-group">
                                <span class="am-input-group-label"><i class="am-icon-key am-icon-fw"></i></span>
                                <input type="password" name="password" class="am-form-field" placeholder="PassWord">
                            </div>
                        </div>
                    </div>
                    <div class="am-g">
                        <div class="am-u-lg-12">
                            <div style="color:red;">${error}</div>
                        </div>
                    </div>
                    <div class="am-g am-margin-top-lg">
                        <div class="am-u-md-6 am-u-lg-6">
                            <span class="am-u-md-4 am-u-lg-4" style="font-size: 12px; white-space: nowrap; padding-top: 6px;padding-left: 0px;">自动登陆</span>
                            <span class="am-u-md-8 am-u-lg-8">
                                <label class="am-switch">
                                    <input name="rememberMe" type="checkbox">
                                    <span class="am-switch-checkbox"></span>
                                </label>
                            </span>
                        </div>
                        <div class="am-u-md-6 am-u-lg-6">
                            <button type="submit" class="am-btn am-btn-primary am-btn-block">登陆</button>
                        </div>
                    </div>
                    <div class="am-u-lg-12">
                        <div class="login-line">
                        <span class="login-line-font">
                            第三方登陆
                        </span>
                        </div>
                    </div>
                    <div class="am-u-md-12 am-u-lg-12 login-consociation">
                        <div class="am-u-sm-4 am-u-md-4 am-u-lg-4"><a href="##" class="am-icon-btn am-primary am-icon-twitter am-center"></a></div>
                        <div class="am-u-sm-4 am-u-lg-4"><a href="##" class="am-icon-btn am-success  am-icon-facebook  am-center"></a></div>
                        <div class="am-u-sm-4 am-u-lg-4"><a href="##" class="am-icon-btn am-danger am-icon-youtube  am-center"></a></div>
                    </div>
                </div>
            </div>
        </form>
        <div class="login-info-cover">
            <div class="login-info-cover-title">
                Amaze UI 3.0
                <div class="login-info-cover-title-small">欢迎使用 Amaze UI 3.0 New Style</div>
                <div class="login-info-cover-line"></div>
            </div>

        </div>
    </div>
</div>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="<%=request.getContextPath()%>/static/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=request.getContextPath()%>/static/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="<%=request.getContextPath()%>/static/js/amazeui.js"></script>
</body>
</html>

