/**
 * author: cjbi
 * date: 2016/9/20
 * mail: cjbi@outlook.com
 */
var basePath = $('#basePath').val();

/**
 * 常用的插件封装.
 * @author cjbi
 * @version 1.1.0
 */
(function ($) {
    /*'use strict';*/
    var tableName = 'example';
    /**
     * 封装datatables、layer弹出层，简化操作
     * @type {{initTable: jQuery.mytables.initTable, getTable: jQuery.mytables.getTable, reloadTable: jQuery.mytables.reloadTable, getSelectedData: jQuery.mytables.getSelectedData, fillEditFormData: jQuery.mytables.fillEditFormData, deleteBatch: jQuery.mytables.deleteBatch, openDialog: jQuery.mytables.openDialog}}
     */
    $.mytables = {
        /**
         * 初始化datatables
         * @param ajax
         * @param gridTable
         * @param ServerParams
         * @param initComplete
         * @param tableNames
         * @returns {jQuery}
         */
        initTable: function (ajax, gridTable, _tableName, drawCallbackJson, initComplete) {
            tableName = (_tableName || tableName);
            var table = $('#' + tableName).DataTable({
                    'aLengthMenu': [3,10, 15, 20, 40, 60],
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
                    'scrollX': true,
                    'ajax': ajax,
                    'destroy': true,//初始化一个新的Datatables，如果已经存在，则销毁（配置和数据），成为一个全新的Datatables实例
                    "dom": '<"am-g am-g-collapse"rt<"am-padding-top"<"am-datatable-hd am-u-sm-4"l><"am-u-sm-4 am-text-center"i><"am-u-sm-4"p>><"clear">>',
                    // "dom" : '<"am-g am-g-collapse"<"am-g
                    // am-datatable-hd"<"am-u-sm-6"<"#btnPlugin">><"am-u-sm-4"<"#regexPlugin">><"am-u-sm-2"f>>rt<<"am-datatable-hd
                    // am-u-sm-4"l><"am-u-sm-4"i><"am-u-sm-4"p>><"clear">>',
                    'responsive': true,
                    'columns': gridTable,
                    "drawCallback": function (settings) {
                        if (drawCallbackJson)
                            drawCallbackJson(this.api().context[0].json);
                    },
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
                }),
                /**
                 * checkbox全选,必须用prop方法设置
                 */
                checkAll = function () {
                    if ($('input[class="am-checkbox-all"]').is(':checked')) {
                        $('input[class="am-checkbox-list"]').parent().parent().addClass('am-active');
                        $('input[class="am-checkbox-list"]').prop('checked', true);
                    } else {
                        $('input[class="am-checkbox-list"]').parent().parent().removeClass('am-active');
                        $('input[class="am-checkbox-list"]').prop('checked', false);
                    }
                },
                rowActive = function () {
                    $('td input[type="checkbox"]').each(function () {
                        if ($(this).is(':checked')) {
                            $(this).parent().parent().addClass('am-active');
                        } else {
                            $(this).parent().parent().removeClass('am-active');
                        }
                    });
                },
                rowOperation = function ($this) {
                    var $checkboxAll = $this.parent().parent().parent().parent().find('td input[type="checkbox"]'),
                        $checkboxRow = $this.parent().parent().parent().find('td input[type="checkbox"]');
                    $checkboxAll.prop('checked', false);
                    $checkboxRow.prop('checked', true);
                    rowActive();
                };
            // checkbox全选
            $('#' + tableName+'_wrapper').on('click', 'th input[type="checkbox"]', function () {
                checkAll();
            });
            // 选中行触发事件
            $('#' + tableName+'_wrapper').on('click', 'td input[type="checkbox"]', function () {
                rowActive();
            });
            //选中行操作事件
            $('#' + tableName+'_wrapper').on('click', '.am-btn', function () {
                rowOperation($(this));
            });
            return table;
        },
        /**
         * 获得表格
         * @param _tableName
         */
        getTable: function (_tableName) {
            return $('#' + (_tableName || tableName)).DataTable()
        },
        /**
         * 重新加载数据源获取数据（不能指定新的数据源）
         * @param _tableName
         */
        reloadTable: function (_tableName) {
            $('#' + (_tableName || tableName)).DataTable().ajax.reload();
        },
        /**
         * 返回选中的行
         * @param _tableName
         */
        getSelectedData: function (_tableName) {
            var table = $('#' + (_tableName || tableName)).DataTable();
            return table.rows('.am-active').data()[0];
        },
        /**
         * 填充表单
         * @param _tableName
         * @returns {boolean}
         */
        fillEditFormData: function (_tableName) {
            // 将值填充到表单中
            var table = $('#' + ((_tableName || tableName))).DataTable(),
                rowLength = table.rows('.am-active').data().length;
            if (rowLength == 0) {
                $.mydialog.msg('请选择一条记录！', $.mydialog.dialog_type.msg.warn);
                return false;
            } else if (rowLength > 1) {
                $.mydialog.msg('最多可选一条记录！', $.mydialog.dialog_type.msg.warn);
                return false;
            }
            var data = table.rows('.am-active').data()[0];
            $.each(data, function (key, value) {
                // 如果类型为单选框
                if ($('#edit-form [name="' + key + '"]').attr('type') == 'radio') {
                    $('#edit-form [name="' + key + '"][value="' + value + '"]').prop('checked', true);
                } else {
                    $("#edit-form [name='" + key + "']").val(value);
                }

            });
            return true;
        },
        /**
         * 批量删除
         * @param url 链接地址
         * @param pk 主键
         * @param msg 信息
         * @param _tableName
         * @returns {boolean}
         */
        batch: function (url, pk, _msg, _tableName) {
            var msg = (_msg || '操作');
            var table = $('#' + (_tableName || tableName)).DataTable(),
                rowData = {},
                array = [],
                dictType = table.rows('.am-active').data(),
                str = $('#' + (_tableName || tableName) + ' tbody tr[class="even am-active"]').length + $('#' + (_tableName || tableName) + ' tbody tr[class="odd am-active"]').length;

            if (dictType[0] == undefined) {
                $.mydialog.msg('请选择一条记录！', $.mydialog.dialog_type.msg.warn);
                return false;
            }
            function obj(tkey, tval) { // 动态生成类变量方法
                this[tkey] = tval;
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
                $.mydialog.msg('请选择一条记录！', $.mydialog.dialog_type.msg.warn);
            } else {
                $.mydialog.confirm('确定要' + msg + '这' + str + '条数据吗？', {
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
                                if (data.success) {
                                    $.mydialog.msg(data.msg, $.mydialog.dialog_type.msg.info);
                                } else {
                                    $.mydialog.msg(data.msg, $.mydialog.dialog_type.msg.warn);
                                }
                                $.mydialog.closeDialog(index);
                                table.ajax.reload();
                            },
                            error: function (data) {
                                $.mydialog.msg('操作失败', $.mydialog.dialog_type.msg.error);
                            }
                        });
                    }
                });
            }
        },
        /**
         * layer封装
         * @param opts 配置参数
         */
        openDialog: function (opts) {
            var content = (opts.content || function () {
                    $.mydialog.msg('请定义参数content', $.mydialog.dialog_type.msg.error);
                } ),
                _title = $.extend({}, opts ? (opts.title || {}) : {}),
                _yes = $.extend({}, opts ? (opts.yes || function (index, layero) {
                    $.mydialog.msg('请定义参数yes', $.mydialog.dialog_type.msg.error);
                }) : {}),
                $form = content.children("form"),
                _end = $.extend(function (index, layero) {
                    // 无论是确认还是取消，只要层被销毁了，end都会执行
                    // 重置表单
                    $form.clear();
                    // 销毁表格验证
                    $form.validator('destroy');
                }, opts ? (opts.yes || {}) : {}),
                editable = (opts.editable || false),
                before = (opts.before || function () {
                }),
                after = (opts.after || function () {
                }),
                defaultOpts = $.extend({
                    title: _title,
                    type: 1,
                    /*
                     * shift : 5, moveType : 1,
                     */
                    // 此参数开启最大化最小化
                    // maxmin: true,
                    area: ['600px', 'auto'],
                    content: content,
                    btn: ['确定', '关闭'],
                    // 按钮一【确定】的回调
                    // index 当前层索引 layero 当前层DOM对象
                    yes: _yes,
                    end: _end,
                    cancel: function (index) {
                        // 按钮二【关闭】和右上角关闭的回调
                    },
                    // 层弹出后的成功回调方法
                    success: after //function(layero, index){}
                }, opts || {});
            if (editable) {
                var success = this.fillEditFormData();
                if (!success) return;
            }
            before(this.getSelectedData());
            $.mydialog.openDialog(defaultOpts);
        }
    };

    /**
     * admin相关方法封装
     * @type {{refresh: jQuery.myadmin.refresh, loadContent: jQuery.myadmin.loadContent}}
     */
    $.myadmin = {

        /**
         * 如果参数是 false，它就会用 HTTP 头 If-Modified-Since 来检测服务器上的文档是否已改变,否则就绕过缓存刷新页面
         * @param forceget 是否绕过缓存
         */
        refresh: function (forceget) {
            window.location.reload(forceget);
        },
        /**
         * 加载content区域
         * @param href #锚点
         * @param callback 回调
         */
        loadContent: function loadContent(href, callback) {
            //重写url，定位 admin-content
            history.pushState('', 0, href);
            var url = location.href;
            // document.title ='测试';
            if (url.indexOf('#') > 0 && url.substr(url.indexOf('#') + 1).length > 0) {
                url = url.replace("#", "/");
                $('#admin-content').load(url, function () {
                    reloadComponent();
                    if (callback)
                        callback();
                });
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
                if ($('#vld-tooltip').length == 0) {
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
        }
    };

    $.mydialog = {
        dialog_self: null,
        openDialog: function (options) {
            return layer.open(options);
        },
        alert: function (content, options, yes) {
            return layer.alert(content, options, yes)
        },

        confirm: function (content, options, yes, cancel) {
            return layer.confirm(content, options, yes, cancel);
        },

        closeDialog: function (index) {
            return layer.close(index);
        },

        closeAll: function (type) {
            return layer.closeAll(type);
        },

        prompt: function (options, yes) {
            return layer.prompt(options, yes);
        },

        msg: function (content, options, end) {
            return layer.msg(content, options, end);
        },

        photos: function (options) {
            return layer.photos(options);
        },
        //定义内部常量
        dialog_type: {
            msg: {
                //正常
                info: {
                    time: '2000',
                    icon: 6
                },
                //错误
                error: {
                    time: 2000,
                    icon: 5
                },
                //警告
                warn: {
                    time: '2000',
                    icon: 0
                }
            }
        }
    };

    //文本对话框
    $.fn.openDialog = function (opts) {
        var $form = $(this).children("form"),
            _title = $.extend({}, opts ? (opts.title || {}) : {}),
            _yes = $.extend({}, opts ? (opts.yes || function (index, layero) {
                $.mydialog.msg('请定义参数yes');
            }) : {}),
            _end = $.extend(function (index, layero) {
                // 无论是确认还是取消，只要层被销毁了，end都会执行
                // 重置表单
                $form.clear();
                // 销毁表格验证
                $form.validator('destroy');
            }, opts ? (opts.yes || {}) : {}),
            editable = (opts.editable || false),
            before = (opts.before || function () {
            }),
            after = (opts.after || function () {
            }),
            defaultOpts = $.extend({
                title: _title,
                type: 1,
                /*
                 * shift : 5, moveType : 1,
                 */
                // 此参数开启最大化最小化
                // maxmin: true,
                area: ['600px', 'auto'],
                content: $(this),
                btn: ['确定', '关闭'],
                // 按钮一【确定】的回调
                // index 当前层索引 layero 当前层DOM对象
                yes: _yes,
                end: _end,
                cancel: function (index) {
                    // 按钮二【关闭】和右上角关闭的回调
                },
                // 层弹出后的成功回调方法
                success: after //function(layero, index){}
            }, opts || {});
        if (editable) {
            var success = initFormData();
            if (!success) return;
        }
        before();
        $.mydialog.openDialog(defaultOpts);
    };
    // 重置表单
    $.fn.clear = function () {
        $(this).get(0).reset();
    };
    /**
     * 异步表单序列化提交
     * $('#form').submit({
     *      url:'http://www.baidu.com',
     *      callback:function() {
     *
     * })
     * @param opts
     */
    // url 链接地址
    $.fn.submit = function (opts, callback) {
        var $form = $(this),
            _url = opts.url,
            _data = (opts.data || $form.serialize()),
            _dataType = (opts.dataType || 'json'),
            callback = (opts.callback || function () {
            });
        $.ajax({
            type: 'post',
            url: _url,
            dataType: _dataType,
            data: _data,
            success: function (data) {
                var options = data.success ? $.mydialog.dialog_type.msg.info : $.mydialog.dialog_type.msg.error;
                $.mydialog.msg(data.msg, options);
                callback(data);
            },
            error: function (data) {
                callback(data);
            }
        });
    };
    //处理异步验证结果
    $.fn.isFormValid = function () {
        var $form = $(this);
        return $form.validator('isFormValid');
    }
})(jQuery);


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

        var $showText = $('.admin-show-text');
        $('#admin-show-sidebar').on('click', function () {
            $('#admin-offcanvas').css({
                display: function (index, value) {
                    if (value == 'block') {
                        $showText.text('显示菜单');
                        return 'none';
                    } else {
                        $showText.text('隐藏菜单');
                        return 'block';
                    }
                }
            });
        });
    });

    // sidebar绑定事件
    $('a[class="am-cf"]').on('click', function (e) {
        var href = $(this).attr('href');
        if (href && href != "#") {
            // 加载Content
            $.myadmin.loadContent(href);
            e.preventDefault();
        }
    });

    //出错提示
    $(document).ajaxError(function (event, request, settings) {
        debugger;
        var responseJSON = request.responseJSON,
            msg;
        if (responseJSON) {
            msg = responseJSON.msg;

        } else {
            msg = request.status + '  (' + request.statusText + ')';
        }
        $.mydialog.alert(msg, {
            title: '出错',
            icon: 5,
            closeBtn: 0, // 关闭滚动条
            scrollbar: false
            // 屏蔽浏览器滚动条
            // 动画类型
        });
    });

    // 加载Content
    $.myadmin.loadContent();

    // 加载进度条动画
    $(document).ajaxStart(function () {
        $.AMUI.progress.start();
    });
    $(document).ajaxStop(function () {
        $.AMUI.progress.done();
    });

})(jQuery);
// 相当于定义了一个参数为$的匿名函数，并且将jQuery作为参数来调用这个匿名函数
//var $ = 123;
//(function($){
//    console.log($("p"));//$仍能正常使用
//})(jQuery)