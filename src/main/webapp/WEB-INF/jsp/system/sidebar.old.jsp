<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
        <ul id="collapase-nav-1" class="am-list admin-sidebar-list">
            <li class="am-list-title">日常使用</li>
            <li class="am-panel">
                <a href="admin-index.html"><i class="am-list-ico am-icon-home am-margin-left-sm"></i> 首页</a>
            </li>

            <li class="am-panel">
                <a data-am-collapse="{parent: '#collapase-nav-1', target: '#user-nav-0'}">
                    <i class="am-list-ico am-icon-file am-margin-left-sm"></i> 用户管理 <i class="am-icon-angle-right am-fr am-margin-right"></i>
                </a>
                <ul class="am-list am-collapse admin-sidebar-sub" id="user-nav-0">
                    <li><a href="#user" class="am-cf"><i class="am-list-ico am-icon-check am-margin-left-sm"></i> 系统用户 </a></li>
                </ul>
            </li>

            <li class="am-panel">
                <a data-am-collapse="{parent: '#collapase-nav-1', target: '#user-nav'}">
                    <i class="am-list-ico am-icon-file am-margin-left-sm"></i> 页面 <i class="am-icon-angle-right am-fr am-margin-right"></i>
                </a>
                <ul class="am-list am-collapse admin-sidebar-sub" id="user-nav">
                    <li><a href="admin-form.html"><i class="am-list-ico am-icon-check am-margin-left-sm"></i> 表单 </a></li>
                    <li><a href="admin-table.html"><i class="am-list-ico am-icon-table am-margin-left-sm"></i> 表格 </a></li>
                </ul>
            </li>

            <li class="am-list-title">其他分类</li>
            <li class="am-panel">
                <a href="admin-gallery.html">
                    <i class="am-list-ico am-icon-th am-margin-left-sm"></i> 相册 </i>
                </a>
            </li>
            <li class="am-panel">
                <a href="admin-404.html">
                    <i class="am-list-ico am-icon-bug am-margin-left-sm"></i> 404页面 </i>
                </a>
            </li>
            <li class="am-panel">
                <a href="login.html">
                    <i class="am-list-ico am-icon-key am-margin-left-sm"></i> 登陆</i>
                </a>
            </li>
            <li class="am-list-title">层级菜单</li>
            <li class="am-panel">
                <a data-am-collapse="{parent: '#collapase-nav-1', target: '#user-nav-9'}">
                    <i class="am-list-ico am-icon-file am-margin-left-sm"></i> 一级菜单 <i class="am-icon-angle-right am-fr am-margin-right"></i>
                </a>
                <ul class="am-list am-collapse admin-sidebar-sub" id="user-nav-9">
                    <li class="am-panel">
                        <a data-am-collapse="{parent: '#user-nav-9', target: '#user-nav-11'}">
                            <i class="am-list-ico am-icon-file am-margin-left-sm"></i> 二级菜单 <i class="am-icon-angle-right am-fr am-margin-right"></i>
                        </a>
                        <ul class="am-list am-collapse admin-sidebar-sub " id="user-nav-11">
                            <li class="am-panel">
                                <a data-am-collapse="{parent: '#user-nav-11',target: '#user-nav-111'}">
                                    <i class="am-list-ico am-icon-file am-margin-left-sm"></i> 三级菜单 <i class="am-icon-angle-right am-fr am-margin-right"></i>
                                </a>
                                <ul class="am-list am-collapse admin-sidebar-sub" id="user-nav-111">
                                    <li class="am-panel"><a href="#user"><i class="am-list-ico am-icon-check am-margin-left-sm"></i> 四级菜单 </a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li class="am-panel">
                        <a data-am-collapse="{parent: '#user-nav-9', target: '#user-nav-12'}">
                            <i class="am-list-ico am-icon-file am-margin-left-sm"></i> 二级菜单2 <i class="am-icon-angle-right am-fr am-margin-right"></i>
                        </a>
                        <ul class="am-list am-collapse admin-sidebar-sub " id="user-nav-12">
                            <li class="am-panel">
                                <a data-am-collapse="{parent: '#user-nav-11',target: '#user-nav-112'}">
                                    <i class="am-list-ico am-icon-file am-margin-left-sm"></i> 三级菜单2 <i class="am-icon-angle-right am-fr am-margin-right"></i>
                                </a>
                                <ul class="am-list am-collapse admin-sidebar-sub" id="user-nav-112">
                                    <li class="am-panel"><a href="#user"><i class="am-list-ico am-icon-check am-margin-left-sm"></i> 四级菜单2 </a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>