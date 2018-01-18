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
                    <ul class="am-list am-list-border" id="tableNames" style="overflow-y: auto;height:600px;">
                        <c:forEach items="${tableNames}" var="tableName">
                            <li><a class="am-text-truncate" href="#">${tableName}</a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
        <div class="am-u-md-9 am-u-sm-12 am-u-end">
            <div class="am-panel am-panel-default">
                <div class="am-panel-hd">生成器配置</div>
                <div class="am-panel-bd">
                    <form class="am-form" id="generator-form" onsubmit="return false;">
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">表名<span class="asterisk">*</span></label>
                            <input type="text" name="tableName" placeholder="person" data-foolish-msg="请先从左侧选择数据库表" required readonly>
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">Java实体类名<span class="asterisk">*</span></label>
                            <div class="am-input-group">
                                <input type="text" class="am-form-field" name="modelName" placeholder="Person" required>
                                <span class="am-input-group-btn">
                                    <button class="am-btn am-btn-primary am-btn-hollow" id="columnCustom" type="button">定制列</button>
                                  </span>
                            </div>
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">模块名称<span class="asterisk">*</span></label>
                            <input type="text" name="moduleName" placeholder="person" data-foolish-msg="请填写模块名称" required>
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">实体类包名<span class="asterisk">*</span></label>
                            <input type="text" name="modelPackage" placeholder="com.example.model" required>
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">Dao接口包名<span class="asterisk">*</span></label>
                            <input type="text" name="daoPackage" placeholder="com.example.model" required>
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">Dao接口名称(选填)</label>
                            <input type="text" name="daoName" placeholder="PersonMapper">
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">映射XML文件包名<span class="asterisk">*</span></label>
                            <input type="text" name="mappingXMLPackage" placeholder="com.example.mapper" required>
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">映射XML文件存放目录<span class="asterisk">*</span></label>
                            <input type="text" name="mappingXMLTargetFolder" value="src/main/resources" required>
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">Service接口包名<span class="asterisk">*</span></label>
                            <input type="text" name="servicePackage" placeholder="com.example.service" required>
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">Service接口名称(选填)</label>
                            <input type="text" name="serviceName" placeholder="PersonService">
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">Service实现类包名<span class="asterisk">*</span></label>
                            <input type="text" name="serviceImplPackage" placeholder="com.example.service.impl" required>
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">Service实现类名称(选填)</label>
                            <input type="text" name="serviceImplName" placeholder="PersonServiceImpl">
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">Controller包名<span class="asterisk">*</span></label>
                            <input type="text" name="controllerPackage" placeholder="com.example.controller" required>
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">Controller名称(选填)</label>
                            <input type="text" name="controllerName" placeholder="PersonController">
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">jsp页面存放目录<span class="asterisk">*</span></label>
                            <div class="am-input-group">
                                <span class="am-input-group-label" style="font-size:0.5rem;">src/main/webapp/WEB-INF/jsp/</span>
                                <input type="text" name="jspTargetFolder" class="am-form-field" required>
                            </div>
                        </div>
                        <div class="am-form-group am-u-md-6">
                            <label class=" am-form-label">jsp页面名称(选填)</label>
                            <input type="text" name="jspName" placeholder="person">
                        </div>
                        <div class="am-form-group">
                            <div class="am-u-md-12 am-pagination-centered">
                                <label class="am-checkbox-inline">
                                    <input type="checkbox" name="comment" data-am-ucheck> 生成实体域注释(来自表注释)
                                </label>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox" name="useActualColumnNames" data-am-ucheck> 使用实际的列名
                                </label>
                                <label class="am-checkbox-inline">
                                    <input type="checkbox" name="useTableNameAlias" data-am-ucheck> XML中生成表的别名
                                </label>
                            </div>
                        </div>
                        <%----%>
                        <div class="am-u-md-end am-text-right am-padding">
                            <button type="text" id="submit" class="am-btn am-btn-primary am-radius">代码生成</button>
                            <button type="text" id="saveConfig" class="am-btn am-btn-warning am-radius">保存配置</button>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>

    <%-- footer start --%>
    <jsp:include page="footer.jsp"/>
    <%-- footer end --%>

</div>
<script type="text/javascript">
    <%-- Synchronous XMLHttpRequest on the main thread is deprecated because of its detrimental effects to the end user's experience. For more help, check https://xhr.spec.whatwg.org/.  --%>
    $.getScript("<%=request.getContextPath()%>/static/js/common/base64.js");
    $(function () {
        init();
        //左侧表格点击事件
        $('#tableNames').find('a').on('click', function (e) {
            var $form = $('#generator-form');
            $('#tableNames').find('a').removeClass('am-active');
            $(this).addClass('am-active');
            e.preventDefault();
            var tableName = $(this).html();
            var modelName = Utils.string.formatHump(tableName, '_');//转换为驼峰命名
            modelName = modelName.substring(0, 1).toUpperCase() + modelName.substring(1);//首字母大写
            $form.find('input[name="tableName"]').val(tableName);
            $form.find('input[name="modelName"]').val(modelName);
        });

        function init() {
            var $form = $('#generator-form');
            var json = localStorage.getItem("generatorConfig");
            var config = JSON.parse(json);
            debugger;
            for(var key in config) {
                $form.find('input[name="'+key+'"]').val(config[key]);
            }
        }

        function generatorConfig($form) {
            var config = {};
            $form.serializeArray().forEach(function (currentValue, index, array) {
                var key = currentValue.name;
                var value = currentValue.value;
                switch (value) {
                    case 'on' :
                        value = true;
                        break;
                    case 'off' :
                        value = false;
                        break;
                }
                eval('config.' + key + '=value;');
            });
            return config;
        }

        $('#columnCustom').on('click', function(e) {
            $.mydialog.msg('功能完善中。。。', $.mydialog.dialog_type.msg.warn);
        });

        //保存配置到 Local Storage
        $('#saveConfig').on('click', function(e) {
            $.mydialog.confirm('当前操作会覆盖以前的配置，是否继续？',{icon: 3,title: '系统提示',}, function(index){
                var $form = $('#generator-form');
                var json = JSON.stringify(generatorConfig($form));
                localStorage.setItem('generatorConfig',json);
                $.mydialog.msg('保存成功', $.mydialog.dialog_type.msg.info);
                $.mydialog.closeDialog(index);
            });
        });

        //点击代码生成事件
        $('#submit').on('click', function (e) {
            var $form = $('#generator-form');
            if ($form.isFormValid()) {
                var json = JSON.stringify(generatorConfig($form));
                var base64 = base64encode(json);
                var url = basePath + '/generator/code.zip?p=' + base64;
                window.open(url);
            }
        });

    });
</script>