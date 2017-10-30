/**
 * author: cjbi
 * date: 2016/9/20
 * mail: cjbi@outlook.com
 *  datatables 公共js封装
 *  注意： jq 1.8 以后 使用prop选固定元素，attr不能选固有元素
 */
var tableName = 'example',
    basePath = $('#basePath').val();
/**
 * 初始化datatables
 * @param ajax
 * @param gridTable
 * @param ServerParams
 * @param initComplete
 * @param tableNames
 * @returns {jQuery|*}
 */
function initDatatables(ajax, gridTable, ServerParams, initComplete, tableNames) {
    tableName = tableNames == undefined ? 'example' : tableNames;
    table = $('#' + tableName).DataTable({
        'aLengthMenu': [10, 15, 20, 40, 60],
        'searching': false,// 开启搜索框
        'lengthChange': true,
        'paging': true,// 开启表格分页
        'bProcessing': true,
        'bServerSide': true,
        'bAutoWidth': true,
        'sort': 'position',
        'deferRender': true,// 延迟渲染
        'bStateSave': true, // 刷新时保存表格状态
        'iDisplayLength': 15,
        'iDisplayStart': 0,
        'ordering': false,// 全局禁用排序
        'scrollX': false,
        'ajax': ajax,
//		表格开启scrollX row会覆盖bProcessing样式，算是个BUG，"am-padding am-padding-horizontal-0"
        "dom": '<"am-g am-g-collapse"rt<<"am-datatable-hd am-u-sm-4"l><"am-u-sm-4 am-text-center"i><"am-u-sm-4"p>><"clear">>',
        // "dom" : '<"am-g am-g-collapse"<"am-g
        // am-datatable-hd"<"am-u-sm-6"<"#btnPlugin">><"am-u-sm-4"<"#regexPlugin">><"am-u-sm-2"f>>rt<<"am-datatable-hd
        // am-u-sm-4"l><"am-u-sm-4"i><"am-u-sm-4"p>><"clear">>',
        'responsive': true,
        'columns': gridTable,
        "fnServerData": ServerParams,
        'oLanguage': { // 国际化配置
            'sProcessing': '正在获取数据，请稍后...',
            // 'sLengthMenu' : ' 显示 _MENU_ 项结果',
            'sZeroRecords': '没有找到数据',
            // 显示第 1 至 10 项结果，共 12 项
            'sInfo': '显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项',
            'sInfoEmpty': '记录数为0',
            'sInfoFiltered': '(全部记录数 _MAX_ 条)',
            'sInfoPostFix': '',
            'sSearch': '搜索:',
            'sUrl': '',
            'oPaginate': {
                'sFirst': '第一页',
                'sPrevious': '«',
                'sNext': '»',
                'sLast': '最后一页'
            }
        },
        initComplete: initComplete
    });
    return table;
}

/**
 * checkbox全选,必须用prop方法设置
 */
function checkAll() {
    if ($('input[id="checkAll"]').is(':checked')) {
        $('input[name="checkList"]').parent().parent().addClass('selected');
        $('input[name="checkList"]').prop('checked', true);
    } else {
        $('input[name="checkList"]').parent().parent().removeClass('selected');
        $('input[name="checkList"]').prop('checked', false);
    }
};

/**
 * 删除数据，url：请求地址，pk：主键
 * @param url
 * @param pk
 * @returns {boolean}
 */
function deleteBatch(url, pk) {
    var table = $('#' + tableName).DataTable();
    var rowData = {};
    var array = [];
    var dictType = table.rows('.selected').data();
    var str = $('#' + tableName + ' tbody tr[class="even selected"]').length + $('#' + tableName + ' tbody tr[class="odd selected"]').length;
    if (dictType[0] == undefined) {
        layer.msg('请选择一条记录！', {
            time: '2000',
            icon: 0
        });
        return false;
    }
    $.each(dictType[0], function (key, value) {
        key = new obj(key, []);
        array.push(key);
    });
    for (var i = 0; i < dictType.length; i++) {
        $.each(dictType[i], function (key, value) {
            for (var j = 0; j < array.length; j++) {
                $.each(array[j], function (key2, value2) {
                    if (key == key2) {
                        value2.push(value);
                    }
                });
            }
        });
    }
    $.each(array, function (key, value) {
        $.each(value, function (key2, value2) {
            rowData[key2] = value2;
        });
    });
    if (str == 0) {
        layer.msg('请选择一条记录！', {
            time: '2000',
            icon: 0
        });
    } else {
        layer.confirm('确定要删除这' + str + '条数据吗？', {
            icon: 3,
            title: '系统提示',
            yes: function (index, layero) {
                // 从rowData中获得主键id数组
                $.ajax({
                    type: 'post',
                    url: url,
                    dataType: 'json',
                    data: 'ids=' + rowData[pk].join(),
                    success: function (data) {
                        if (data.success == false) {
                            layer.msg(data.msg, {
                                time: '2000',
                                icon: 0
                            });
                        } else {
                            layer.msg(data.msg, {
                                time: '2000',
                                icon: 6
                            });
                        }
                        layer.close(index);
                        table.ajax.reload();
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
    }
};

/**
 * 返回选中的行
 */
function getSelectedData() {
    var table = $('#' + tableName).DataTable();
    return table.rows('.selected').data()[0];
}

/**
 * 将值填充到表单中(修改表单回显)
 * @returns {boolean}
 */
function initFormData() {// 将值填充到表单中
    var table = $('#' + tableName).DataTable();
    var rowLength = table.rows('.selected').data().length;
    if (rowLength == 0) {
        layer.msg('请选择一条记录！', {
            time: '2000',
            icon: 0
        });
        return false;
    } else if (rowLength > 1) {
        layer.msg('最多可选一条记录！', {
            time: '2000',
            icon: 0
        });
        return false;
    }
    var data = table.rows('.selected').data()[0];
    $.each(data, function (key, value) {
        // 如果类型为单选框
        if ($('#edit-form [name="' + key + '"]').attr('type') == 'radio') {
            $('#edit-form [name="' + key + '"][value="' + value + '"]').prop('checked', true);
        } else {
            $("#edit-form [name='" + key + "']").val(value);
        }

    });
    return true;
};

function obj(tkey, tval) { // 动态生成类变量方法
    this[tkey] = tval;
}

// checkbox全选
$('div').on('click', 'th input[type="checkbox"]', function () {
    checkAll();
});

// 选中行触发事件
$('div').on('click', 'td input[type="checkbox"]', function () {
    rowActive();
});

rowActive = function () {
    $('input[name="checkList"]').each(function () {
        if ($(this).is(':checked')) {
            $(this).parent().parent().addClass('selected');
        } else {
            $(this).parent().parent().removeClass('selected');
        }
    });
};

/**
 * 查询数据
 */
function reloadDatatables() {
    $('#example').DataTable().ajax.reload();
};

/**
 *
 * @param href #锚点
 * @param callback 回调
 */
function loadContent(href, callback) {
    //重写url，定位 admin-content
    history.pushState('', 0, href);
    var url = location.href;
    // document.title ='测试';
    if (url.indexOf('#') > 0 && url.substr(url.indexOf('#') + 1).length > 0) {
        url = url.replace("#", "/");
        $('#admin-content').load(url, function () {
            reloadComponent();
            if (callback != undefined)
                callback();

        });
    }
}

/**
 * jQuery因为删除dom事件也会失效，所以页面需要注册下部分依赖jQuery的组件
 */
function reloadComponent() {
    // 重新注册amaze ui下拉组件
    $('[data-am-selected]').selected();
    initInputTooltip();
}

function initInputTooltip() {
    //重新注册验证tooltip事件
    var $form = $('form');
    var $tooltip = $('#vld-tooltip');
    if($('#vld-tooltip').length==0) {
        $tooltip = $('<div id="vld-tooltip">提示信息！</div>');
        $tooltip.appendTo(document.body);
    }
    $form.on('focusin focusout', '.am-form-error input', function (e) {
        if (e.type === 'focusin') {
            var $this = $(this);
            var offset = $this.offset();
            var msg = $this.data('foolishMsg') || $form.validator('getValidationMessage', $this.data('validity'));
            $tooltip.text(msg).show().css({
                left: offset.left + 10,
                top: offset.top + $(this).outerHeight() + 10
            });
        } else {
            $tooltip.hide();
        }
    });
    //关闭tooltip临时解决方案
    $form.on('focusin focusout', '.am-form-success input', function (e) {
        $tooltip.hide();
    });
}

(function ($) {
    'use strict';

    $(function () {
        var $fullText = $('.admin-fullText');
        $('#admin-fullscreen').on('click', function () {
            $.AMUI.fullscreen.toggle();
        });

        $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function () {
            $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
        });
    });

    $.ajaxCheck = function (data) {
        if (data.result)
            return true;
        else {
            alert(data.msg);
            return false;
        }
    };

    // sidebar绑定事件
    $('a[class="am-cf"]').on('click', function (e) {
        var href = $(this).attr('href');
        if (href != undefined && href != "" && href != "#") {
            // 加载Content
            loadContent(href);
            e.preventDefault();
        }
    });

    //出错提示
    $(document).ajaxError(function(event, request, settings) {
        debugger;
        layer.alert(request.status + '  (' + request.statusText + ')', {
            title : '出错',
            icon : 5,
            closeBtn : 0, // 关闭滚动条
            scrollbar : false
            // 屏蔽浏览器滚动条
            // 动画类型
        });
    });

    // 加载Content
    loadContent();

    /*
     * $.ajaxSetup({ cache : true });
     */

    // 加载进度条动画
    $(document).ajaxStart(function() {
        $.AMUI.progress.start();
    });
    $(document).ajaxStop(function() {
        $.AMUI.progress.done();
    });

})(jQuery);


