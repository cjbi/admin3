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
    var tableId = 'example';
    /**
     * 封装datatables、layer弹出层，简化操作
     * @type {{initTable: jQuery.mytables.initTable, selectEvent: jQuery.mytables.selectEvent, getTable: jQuery.mytables.getTable, reloadTable: jQuery.mytables.reloadTable, getSelectedData: jQuery.mytables.getSelectedData, fillEditFormData: jQuery.mytables.fillEditFormData, batch: jQuery.mytables.batch, openDialog: jQuery.mytables.openDialog}}
     */
    $.mytables = {
        /**
         * 初始化datatables
         * @param ajax
         * @param columns
         * @param ServerParams
         * @param initComplete
         * @param tableNames
         * @returns {jQuery}
         */
        initTable: function (opts) {
            tableId = (opts.tableId || tableId);
            var defaults = {
                'aLengthMenu': [5, 10, 15, 20, 40, 60, 100],
                'searching': false,// 开启搜索框
                'lengthChange': true,
                'paging': true,// 开启表格分页
                'bProcessing': true,
                'bServerSide': true,
                'bAutoWidth': true,
                'sort': 'position',
                'deferRender': true,// 延迟渲染
                'bStateSave': true, // 刷新时保存表格状态
                'iDisplayLength': 10,
                'iDisplayStart': 0,
                'ordering': false,// 全局禁用排序
                'scrollX': true,
                /*'ajax': ajax,*/
                'destroy': true,//初始化一个新的Datatables，如果已经存在，则销毁（配置和数据），成为一个全新的Datatables实例
                "dom": '<"am-g am-g-collapse"rt<"am-padding-top"<"am-datatable-hd am-u-sm-4"l><"am-u-sm-4 am-text-center"i><"am-u-sm-4"p>><"clear">>',
                'buttons': [{
                    'extend': 'collection',
                    'text': '<span class="am-icon-sign-out"></span> 导出',
                    'postfixButtons': [{
                        'extend': 'copyHtml5',
                        'text': '<span class="am-icon-copy"></span> 复制'
                    }, {
                        'extend': 'excelHtml5',
                        'text': '<span class="am-icon-file-excel-o"></span> Excel'
                    }, {
                        'extend': 'csvHtml5',
                        'text': '<span class="am-icon-file-text-o"></span> CSV'
                    }, {
                        'extend': 'print',
                        'text': '<span class="am-icon-print"></span> 打印',
                        'exportOptions': {
                            'columns': ':visible'
                        }
                    }]
                }, {
                    'extend': 'colvis',
                    'postfixButtons': [{
                        'extend': 'colvisRestore',
                        'text': '<span class="am-icon-undo"></span> 撤销更改',

                    }],
                    'text': '<span class="am-icon-columns"></span> 显示/隐藏列',
                    'columnText': function (dt, idx, title) {
                        if (idx == 0) return '<span class="am-icon-check-square-o"></span> 复选框';
                        return title;
                    }
                }, {
                    'text': '<span class="am-icon-refresh"></span> 重置',
                    'action': function (e, dt, node, config) {
                        //清空所有input框
                        $('.am-btn-toolbar').find('input').val('');
                        //刷新表格
                        table.ajax.reload();
                    }
                }],
                'columnDefs': [{
                    'orderable': false,
                    'className': 'select-checkbox',
                    'targets': 0
                }],
                'order': [[1, 'asc']],
                'select': {
                    'style': 'multi+shift',
                    /*'selector': 'td:first-child',*/
                    'className': 'am-active'
                },
                'responsive': true,
                /* 'columns': columns,*/
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
                    },
                    'select': {
                        'rows': {
                            '_': '选了 %d 行数据',
                            '0': '单击选定行数据',
                            '1': '已经选了 1 行'
                        }
                    },
                    'buttons': {
                        'copyTitle': '复制到剪贴板',
                        'copyKeys': '输入 <i>Ctrl</i> 或者 <i>\u2318</i> + <i>C</i> 去复制这个表格的数据<br>到你的系统剪贴板.<br><br>' + '要取消, 单击这个消息 或者 按下 Esc键.',
                        'copySuccess': {
                            '_': '已经复制 %d 行数据到剪贴板'
                        }
                    }
                },
                drawCallback: function (settings) {
                    var $row = $('#' + tableId + '_wrapper').find('thead tr:first-child');
                    //清除多余的样式
                    $row.removeClass('am-active');
                },
                initComplete: function (settings) {
                    var $btnGroup = $('.am-btn-toolbar').find('.am-btn-group');
                    //添加按钮
                    $btnGroup.append(table.buttons().container().find('button'));
                }
            };
            opts.buttons = (opts.buttons || []).concat(defaults.buttons);//追加按钮
            opts = $.extend(defaults, opts);//扩展配置参数
            var table = $('#' + tableId).DataTable(opts);
            // checkbox全选
            $('#' + tableId + '_wrapper').on('click', 'thead tr:first-child th.select-checkbox:first-child', function () {
                var $row = $('#' + tableId + '_wrapper').find('thead tr:first-child');
                if ($row.hasClass('am-active')) {
                    $row.removeClass('am-active');
                    table.rows().deselect();
                } else {
                    $row.addClass('am-active');
                    table.rows().select();
                }
            });
            return table;
        },
        /**
         * 按钮启用禁用监听事件
         * @param callback 回调函数
         * @param _tableId //表格id
         */
        selectEvent: function (callback, _tableId) {
            var table = $('#' + ((_tableId || tableId))).DataTable();
            // debugger;
            table.on('draw.dtSelect.dt select.dtSelect.dt deselect.dtSelect.dt info.dt', function (event) {
                var table = $('#' + ((_tableId || tableId))).DataTable();
                var data = table.rows({selected: true}).data();
                callback(data, event);
            })
        },
        /**
         * 获得表格
         * @param _tableId 表格id
         */
        getTable: function (_tableId) {
            return $('#' + (_tableId || tableId)).DataTable()
        },
        /**
         * 重新加载数据源获取数据（不能指定新的数据源）
         * @param _tableId
         */
        reloadTable: function (_tableId) {
            $('#' + (_tableId || tableId)).DataTable().ajax.reload();
        },
        /**
         * 返回选中的行
         * @param _tableId
         */
        getSelectedData: function (_tableId) {
            var table = $('#' + (_tableId || tableId)).DataTable();
            return table.rows('.am-active').data()[0];
        },
        /**
         * 填充表单
         * @param _tableId
         * @returns {boolean}
         */
        fillEditFormData: function (_tableId, $form) {
            // 将值填充到表单中
            var table = $('#' + ((_tableId || tableId))).DataTable(),
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
                if ($form.find('[name="' + key + '"]').attr('type') == 'radio') {
                    $form.find('[name="' + key + '"][value="' + value + '"]').prop('checked', true);
                } else {
                    $form.find("[name='" + key + "']").val(value);
                }

            });
            return true;
        },
        /**
         * 批量删除
         * @param url 链接地址
         * @param pk 主键
         * @param msg 信息
         * @param _tableId
         * @returns {boolean}
         */
        batch: function (url, pk, _msg, _tableId) {
            var msg = (_msg || '操作');
            var table = $('#' + (_tableId || tableId)).DataTable(),
                rowData = {},
                array = [],
                dictType = table.rows('.am-active').data(),
                str = $('#' + (_tableId || tableId) + ' tbody tr[class="even am-active"]').length + $('#' + (_tableId || tableId) + ' tbody tr[class="odd am-active"]').length;

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
                var success = this.fillEditFormData(tableId, $form);
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
            if (href)
                history.pushState('', 0, href);
            var url = location.href;
            // document.title ='测试';
            if (url.indexOf('#') > 0 && url.substr(url.indexOf('#') + 1).length > 0) {
                url = url.replace("#", "/");
                $('#admin-content').load(url, function () {

                    setTitle();//设置标题
                    reloadComponent();//重新加载组件
                    if (callback)
                        callback();
                });
            }

            /**
             * 设置标题
             */
            function setTitle() {
                //取得标题
                var title = ($('.am-title:first').text() || 'Wetech Admin');
                //设置标题
                document.title = title;
            }

            /**
             * jQuery因为删除dom事件也会失效，所以页面需要注册下部分依赖jQuery的组件
             */
            function reloadComponent() {
                // 重新注册amaze ui下拉组件
                $('[data-am-selected]').selected();
                $('[data-am-ucheck]').uCheck();
                initInputTooltip();
                initCollapse();
            }

            function collapseOpen(target, parent) {
                var $parent = $(parent);
                if ($parent.length > 0) {
                    $parent.collapse('open');
                    var $collapse = $parent.siblings('a');
                    if ($collapse.length > 0) {
                        var data_am_collapse = $collapse.attr('data-am-collapse');
                        var collapse = JSON.parse(data_am_collapse.replace(/'/g, "\""));
                        collapseOpen(collapse.target, collapse.parent);
                    }
                }
            }

            function initCollapse() {
                var data_am_collapse = localStorage.getItem('data_am_collapse');
                var $collapse = $('[data-am-collapse="' + data_am_collapse + '"]');
                if (!$collapse.hasClass('collapse-active'))
                    $collapse.addClass('collapse-active');
                var collapse = JSON.parse(data_am_collapse.replace(/'/g, "\""));
                collapseOpen(collapse.target, collapse.parent);
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
            $('#admin-offcanvas').find('a').removeClass('collapse-active');
            //选中
            $(this).addClass('collapse-active');
            localStorage.setItem("data_am_collapse", $(this).attr('data-am-collapse'));
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