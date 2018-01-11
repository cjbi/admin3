<%--
  Created by IntelliJ IDEA.
  User: cjbi
  Date: 2018/1/8
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="wetechfn" uri="http://wetech.tech/admin/tags/wetech-functions" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .am-list-border > li, .am-list-bordered > li {
        border-width: 0px;
    }
</style>
<div class="admin-content">
    <div class="admin-content-body">
        <div class="am-cf am-padding">
            <ol class="am-breadcrumb">
                <li><a href="#" class="am-icon-home">首页</a></li>
                <li class="am-active">代码生成器</li>
            </ol>
        </div>
        <div class="am-u-md-3 am-u-sm-12">
            <div class="am-panel am-panel-default">
                <div class="am-panel-hd">选择表</div>
                <div class="am-panel-bd">
                    <ul class="am-list am-list-border" style="overflow-y: auto;height:600px;">
                        <li><a class="am-text-truncate" href="#">aaaaaaaaaaaaaaaaaaaaa</a></li>
                        <li><a class="am-text-truncate am-active" href="#">bbbbbbbbbbbbbbbbbb</a></li>
                        <li><a class="am-text-truncate" href="#">cccccccccccccccccccc</a></li>
                        <li><a class="am-text-truncate" href="#">dddddddddddddddddddd</a></li>
                        <li><a class="am-text-truncate" href="#">dddddddddddddddddddd</a></li>
                        <li><a class="am-text-truncate" href="#">dddddddddddddddddddd</a></li>
                        <li><a class="am-text-truncate" href="#">dddddddddddddddddddd</a></li>
                        <li><a class="am-text-truncate" href="#">dddddddddddddddddddd</a></li>
                        <li><a class="am-text-truncate" href="#">dddddddddddddddddddd</a></li>
                        <li><a class="am-text-truncate" href="#">dddddddddddddddddddd</a></li>
                        <li><a class="am-text-truncate" href="#">dddddddddddddddddddd</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="am-u-md-9 am-u-sm-12 am-u-end">
            <div class="am-panel am-panel-default">
                <div class="am-panel-hd">生成器配置</div>
                <div class="am-panel-bd">
                    <form class="am-form am-form-horizontal" onsubmit="return false;" id="edit-form">
                        <input type="hidden" name="id"/>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label">表名</label>
                            <div class="am-u-sm-6 am-u-end">
                                <input type="text" name="name" value="test_table_name" placeholder="表的名称" required readonly>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label am-text-truncate">Java实体类名</label>
                            <div class="am-u-sm-7">
                                <input type="text" min="0" name="priority" placeholder="节点的序号">
                            </div>
                            <div class="am-u-sm-3">
                                <button type="button" class="am-btn am-btn-default">定制列</button>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label am-text-truncate">实体类包名</label>
                            <div class="am-u-sm-4 am-u-end">
                                <input type="text" name="name" placeholder="com.example.model" placeholder="primary key,such as id">
                            </div>
                            <label class="am-u-sm-2 am-form-label am-text-truncate">存放目录</label>
                            <div class="am-u-sm-4 am-u-end">
                                <input type="text" name="name" placeholder="primary key,such as id">
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label am-text-truncate">Dao接口包名</label>
                            <div class="am-u-sm-4 am-u-end">
                                <input type="text" name="name" placeholder="com.example.model" placeholder="primary key,such as id">
                            </div>
                            <label class="am-u-sm-2 am-form-label am-text-truncate">存放目录</label>
                            <div class="am-u-sm-4 am-u-end">
                                <input type="text" name="name" placeholder="primary key,such as id">
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label am-text-truncate">Dao接口名称(选填)</label>
                            <div class="am-u-sm-4 am-u-end">
                                <input type="text" name="name" placeholder="com.example.model" placeholder="primary key,such as id">
                            </div>
                        </div>
                        <div class="am-form-group">
                            <label class="am-u-sm-2 am-form-label am-text-truncate">映射XML文件包名</label>
                            <div class="am-u-sm-4 am-u-end">
                                <input type="text" name="name" placeholder="com.example.model" placeholder="primary key,such as id">
                            </div>
                            <label class="am-u-sm-2 am-form-label am-text-truncate">存放目录</label>
                            <div class="am-u-sm-4 am-u-end">
                                <input type="text" name="name" placeholder="primary key,such as id">
                            </div>
                        </div>
                        <div class="am-form-group">
                            <div class="am-u-sm-12 am-pagination-centered">
                                <label class="am-checkbox-inline">
                                    <input type="checkbox"  value="" data-am-ucheck> 分页
                                </label>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox"  value="" data-am-ucheck> 生成实体域注释(来自表注释)
                                </label>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox"  value="" data-am-ucheck> 使用实际的列名
                                </label>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox"  value="" data-am-ucheck> XML中生成表的别名
                                </label>
                            </div>
                        </div>
                        <div class="am-form-group">
                            <div class="am-u-sm-end am-text-right">
                                <button type="submit" class="am-btn am-btn-primary am-radius">代码生成</button>
                                <button type="reset" class="am-btn am-btn-warning am-radius">保存配置</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>