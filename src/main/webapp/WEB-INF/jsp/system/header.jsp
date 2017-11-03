<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
以获得更好的体验！</p>
<![endif]-->
<header class="am-topbar am-topbar-inverse">
    <h1 class="am-topbar-brand">
        <a href="#" class="am-text-ir"></a>
    </h1>

    <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-secondary am-show-sm-only" data-am-collapse="{target: '#doc-topbar-collapse-2'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

    <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse-2">
        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
            <li><a href="javascript:;"><span class="am-mark"><span class="am-icon-bell"></span> 信息<sup>99+</sup></span></a></li>
            <li class="am-dropdown" data-am-dropdown>
                <a  class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                    <span class="am-mark"><span class="am-icon-calendar-check-o"></span> 进度<sup>12</sup></span>
                    <span class="am-icon-caret-down"></span>
                </a>
                <ul class="am-dropdown-content">
                    <li class="am-dropdown-header">进度列表</li>
                    <li><a href="#">Amaze UI</a></li>
                    <li class="am-active"><a href="#">Amaze UI Touch</a></li>
                    <li><a href="#">Amaze UI React</a></li>
                </ul>
            </li>
            <li class="am-hide-sm-only"><a href="javascript:;" id="admin-show-sidebar"><span class="am-icon-bars"></span> <span
                    class="admin-show-text">隐藏侧边栏</span></a></li>
            <li class="am-hide-sm-only"><a href="javascript:;" id="admin-fullscreen"><span class="am-icon-arrows-alt"></span> <span
                    class="admin-fullText">开启全屏</span></a></li>
            <li class="am-dropdown" data-am-dropdown>
                <a  class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
                    <img class="admin-user-ico" src="<%=request.getContextPath()%>/static/img/user.png" alt=""><shiro:principal/>
                    <span class="am-icon-caret-down"></span>
                </a>
                <ul class="am-dropdown-content">
                    <li class="am-dropdown-header">个人资料</li>
                    <li><a href="#">资料设置</a></li>
                    <li class="am-active"><a href="#">个人中心</a></li>
                </ul>
            </li>
            <li><a href="<%=request.getContextPath()%>/logout"><span class="am-icon-close"></span> 退出</a></li>
        </ul>
    </div>
</header>