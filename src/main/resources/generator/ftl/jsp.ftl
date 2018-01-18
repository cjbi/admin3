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
                <li class="am-active am-title">新页面</li>
            </ol>
        </div>

        <div class="am-g am-btn-toolbar">
            <div class="am-u-sm-12 am-u-md-6">
                <div class="am-btn-group am-btn-group-xs">
                </div>
            </div>
            <div class="am-u-sm-12 am-u-md-3">
                <div class="am-form-group">
                </div>
            </div>
            <div class="am-u-sm-12 am-u-md-3">
                <div class="am-input-group am-input-group-sm">
                    <input type="text" name="keywords" id="keywords" placeholder="根据条件模糊匹配" class="am-form-field">
                    <span class="am-input-group-btn">
                        <button class="am-btn am-btn-primary" onclick="$.mytables.reloadTable();" type="button">搜索</button>
                    </span>
                </div>
            </div>
        </div>

        <div class="am-u-sm-12">
            <table class="am-table am-table-striped  am-table-hover table-main am-table-bordered" width="100%" id="example_${tableClass.lowerCaseName}">
                <thead>
                <tr>
                    <th><#if tableClass.pkFields??><span style="display: none;">${tableClass.pkFields[0].remarks}</span></#if>&nbsp;</th>
                <#if tableClass.baseFields??>
                <#list tableClass.baseFields as field>
                    <th>${field.remarks}</th>
                </#list>
                </#if>
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
            <#if tableClass.pkFields??>{
                'data': '${tableClass.pkFields[0].fieldName}',
            </#if>
                'width': '2%',
                'fnCreatedCell': function (nTd, sData, oData, iRow, iCol) {
                    $(nTd).html('');
                }
            }<#if tableClass.baseFields??><#list tableClass.baseFields as field>,
            {
                'data': '${field.fieldName}' // ${field.remarks}
            }
        </#list></#if>
        ];
        var opts = {
            'ajax': ajax,
            'columns': columns,
            'tableId': 'example_${tableClass.lowerCaseName}'
        };

        var table = $.mytables.initTable(opts);

    });
</script>