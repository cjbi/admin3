/**
 * author: cjbi
 * date: 2018/4/6
 * mail: cjbi@outlook.com
 */
/**
 * 常用的插件封装.
 * javaScript language version:ECMAScript 3
 * @author cjbi
 * @version 1.5.x-adminlte
 */
(function ($) {
    'use strict';
    var _my_admin_api = {
        /**
         * 如果参数是 false，它就会用 HTTP 头 If-Modified-Since 来检测服务器上的文档是否已改变,否则就绕过缓存刷新页面
         * @param forceget 是否绕过缓存
         */
        refresh: function (forceget) {
            window.location.reload(forceget);
        },
        //刷新表格
        /**
         * 刷新远程服务器数据，可以设置{silent: true}以静默方式刷新数据，并设置{url: newUrl}更改URL。 要提供特定于此请求的查询参数，请设置{query: {foo: 'bar'}}。
         */
        refreshTable: function () {
            $('.modal').modal('hide');
            //以静默方式刷新数据
            $('table').bootstrapTable('refresh', {silent: true});
        },
        refreshContent: function () {
            var u = window.location.href;
            var i = u.indexOf('#');
            if (i != -1) {
                var s = u.substr(i);
                if (s) {
                    $('.modal').modal('hide');
                    $('.modal-backdrop').remove();
                    $.myAdmin.loadContent(s);
                }
            }
        },
        /**
         * 加载content区域
         * @param href #锚点
         * @param callback 回调
         */
        loadContent: function (href, callback) {
            if(href) {
                if(href.startsWith('http')) {
                    window.open(href);
                    return;
                } else {
                    //重写url，定位content
                    window.history.pushState('', 0, href);
                }
            }
            var url = window.location.href;
            if (url.indexOf('#') > 0 && url.substr(url.indexOf('#') + 1).length > 0) {
                var s = url.indexOf('#');
                if (url.substr(s - 1, 1) === '/' || url.substr(s + 1, 1) === '/') {
                    url = url.replace('#', '');
                } else {
                    url = url.replace('#', '/');
                }
                $('#content-wrapper').load(url, function () {
                    //重新加载组件
                    reloadComponent();
                    callback && callback();
                });
            }

            var error = function (msg) {
                console.error(msg);
                $.myNotify.danger(msg);
            };
            //jQuery因为删除dom事件也会失效，所以页面需要注册下部分依赖jQuery的组件和事件
            var reloadComponent = function () {
                //自定义事件
                $('[data-action]').on('click', function (e) {
                    var str = $(this).attr('data-action');
                    try {
                        var obj = eval('(' + str + ')');
                    } catch (err) {
                        console.error(err);
                        error('[data-action]有误，请检查语法')
                    }
                    //前置事件
                    if (obj.before) {
                        eval(obj.before + '(obj)');
                    }
                    //前置事件
                    if (obj.before) {
                        eval(obj.before + '(obj)');
                    }
                    if (obj && obj.type) {
                        switch (obj.type) {
                            //提交
                            case 'submit':
                                if (obj.form && obj.url) {
                                    e.preventDefault();
                                    if (/^@{(.*?)}$/g.test(obj.url)) {
                                        obj.url = CONTEXT_PATH + obj.url.substring(2, obj.url.indexOf("}"))
                                    }
                                    $(obj.form).submit({url: obj.url},
                                        function (data) {
                                            if (obj.after) {
                                                eval(obj.after + '(obj,data)');
                                            }
                                        })
                                } else {
                                    error('[data-action]参数有误，请检查参数form和url是否存在');
                                }
                                break;
                            //编辑模式
                            case 'editable':
                                if (obj.form && obj.table) {
                                    var data = $(obj.table).bootstrapTable('getSelections');
                                    if (data.length === 0) {
                                        $.myNotify.warning('请选择一条记录');
                                        return false;
                                    } else if (data.length > 1) {
                                        $.myNotify.warning('最多可选一条记录');
                                        return false;
                                    }
                                    $(obj.form).fillForm(data[0]);
                                    if (obj.after) {
                                        eval(obj.after + '(obj,data[0])');
                                    }
                                    $(obj.form).validator('validate');
                                    //select2
                                    //Initialize Select2 Elements
                                    $('.select2').select2({width: '100%'});
                                } else {
                                    error('[data-action]参数有误，请检查参数form和table是否存在');
                                }
                                break;
                            //删除模式
                            case 'delete':
                                if (obj.form && obj.table) {
                                    var data = $(obj.table).bootstrapTable('getSelections');
                                    if (data.length === 0) {
                                        $.myNotify.warning('请选择一条记录');
                                        return false;
                                    }
                                    $('.records').html(data.length);
                                    var html = '';
                                    for (var i in data) {
                                        var id = eval('data[i].' + obj.idField);
                                        html += '<input type="hidden" name="' + obj.idField + '" value="' + id + '">';
                                    }
                                    $(obj.form).html(html);

                                } else {
                                    error('[data-action]参数有误，请检查参数form和table是否存在');
                                }
                                break;
                            default :
                                error('[data-action]参数有误，请检查参数type是否有误');
                        }
                    }
                });
                //模态框隐藏的事件
                $('.modal').on('hidden.bs.modal', function (e) {
                    $(this).find('form').clear();
                    $('table').bootstrapTable('uncheckAll');
                    //select2
                    //Initialize Select2 Elements
                    $('.select2').select2({width: '100%'});
                });
                //模态框调用事件
                $('.modal').on('show.bs.modal', function (e) {
                    // console.log('show.bs.modal触发了');
                    // $('form').validator('validate');
                    // return e.preventDefault() // 阻止模态框的展示
                })
                //bootstrap-table选择按钮事件
                $('table').on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function () {
                    var $table = $(this);
                    $('[data-action]').each(function (i) {
                        var str = $(this).attr('data-action');
                        try {
                            var obj = eval('(' + str + ')');
                        } catch (err) {
                            error('[data-action]有误，请检查语法')
                        }
                        if (obj && obj.type) {
                            switch (obj.type) {
                                case 'editable':
                                    $(this).prop('disabled', !($table.bootstrapTable('getSelections').length === 1));
                                    break;
                                case 'delete':
                                    $(this).prop('disabled', !$table.bootstrapTable('getSelections').length);
                                    break;
                            }
                        }
                        if (obj && obj.selection) {
                            switch (obj.selection) {
                                case 'single':
                                    $(this).prop('disabled', !($table.bootstrapTable('getSelections').length === 1));
                                    break;
                                case 'multi':
                                    $(this).prop('disabled', !$table.bootstrapTable('getSelections').length);
                                    break;
                            }
                        }
                    });
                });
                //表格事件
                $('table').on('all.bs.table', function (e, name, args) {
                    $('[data-toggle="tooltip"]').tooltip();
                    $('[data-toggle="popover"]').popover();

                });
                //初始化表单验证
                $('form').validator({'disable': false});
                //select2
                //Initialize Select2 Elements
                $('.select2').select2({width: '100%'});
            };
        }

    };
    var _my_action_api = {
        //刷新表格
        refreshTable: function (obj, data) {
            if (data.success) {
                $('.modal').modal('hide');
                //以静默方式刷新数据
                $('table').bootstrapTable('refresh', {silent: true});
            }
        },
        //刷新内容区域
        refreshContent: function (obj, data) {
            if (data.success) {
                var u = location.href;
                var i = u.indexOf('#');
                if (i != -1) {
                    var s = u.substr(i);
                    if (s) {
                        $('.modal').modal('hide');
                        $('.modal-backdrop').remove();
                        $.myAdmin.loadContent(s);
                    }
                }
            }
        },
    };

    //清除表单内容
    var _clear = function () {
        $(this).get(0).reset();
    };
    //处理异步验证结果
    var _is_form_valid = function () {
        //bootstrap-validator验证
        $(this).validator('validate');
        var flag = true, form = $(this)[0];
        for (var i = 0; i < form.length; i++) {
            if (!form[i].checkValidity()) {
                //自定义校验
                flag = false;
                break;
            }
            //ensure two fields match 确保两个字段匹配
            var match = $(form[i]).attr('data-match');
            if (match) {
                var val1 = $(match).val(),
                    val2 = $(form[i]).val();
                if (!(val1 === val2)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    var _fill_form = function (row) {
        var $form = $(this);
        $.each(row, function (key, value) {
            // 如果类型为单选框
            if ($form.find('[name="' + key + '"]').attr('type') == 'radio') {
                $form.find('[name="' + key + '"][value="' + value + '"]').prop('checked', true);
            } else if (typeof(value) === "boolean") {
                //布尔类型转换为数值0和1
                $('#editForm [name="' + key + '"]').val(value + 0);
            } else {
                $form.find("[name='" + key + "']").val(value);
            }
        })
        //select2
        //Initialize Select2 Elements
        $('.select2').select2({width: '100%'});
    }

    // url 链接地址
    var _submit = function (opts, callback) {
        var $form = $(this),
            _url = opts.url,
            _data = (opts.data || $form.serialize()),
            _dataType = (opts.dataType || 'json');
        if ($form.isFormValid($(this))) {
            $.ajax({
                type: 'post',
                url: _url,
                dataType: _dataType,
                data: _data,
                success: function (data) {
                    if (data.success) {
                        $.myNotify.success(data.msg);
                    } else {
                        $.myNotify.warning(data.msg);
                    }

                    callback && callback(data);
                },
                error: function (data) {
                    callback && callback(data);
                }
            });
        }
    };

    var _my_notify_api = {
        //成功
        success: function (message) {
            return $.notify({
                // options
                icon: 'glyphicon glyphicon-ok-sign',
                message: message
            }, {
                // settings
                type: 'success'
            });
        },
        //信息
        info: function (message) {
            return $.notify({
                // options
                icon: 'glyphicon glyphicon-info-sign',
                message: message
            }, {
                // settings
                type: 'info'
            });
        },
        //警告
        warning: function (message) {
            return $.notify({
                // options
                icon: 'glyphicon glyphicon-warning-sign',
                message: message
            }, {
                // settings
                type: 'warning'
            });
        },
        //错误
        danger: function (message) {
            return $.notify({
                // options
                icon: 'glyphicon glyphicon-remove-sign',
                message: message
            }, {
                // settings
                type: 'danger'
            });
        }

    }
    //处理异步验证结果
    $.fn.isFormValid = _is_form_valid;
    // 重置表单
    $.fn.clear = _clear;
    //提交
    $.fn.submit = _submit;
    //填充表单
    $.fn.fillForm = _fill_form;
    //admin核心api
    $.myAdmin = _my_admin_api;
    //[data-action]内置函数
    $.myAction = _my_action_api;
    //提示框
    $.myNotify = _my_notify_api;

})(jQuery);
//定义一些处理
(function ($) {
    //初始化sidebar
    $(document).ready(function () {
        $('.sidebar-menu').tree()
    });
// sidebar绑定事件
    $('li[class="treeview"]').on('click', function (e) {
        var href = $(this).find('a').attr('href');
        if (href && href != "#") {
            // $('#admin-offcanvas').find('a').removeClass('collapse-active');
            //选中
            // $(this).addClass('collapse-active');
            // localStorage.setItem("data_am_collapse", $(this).attr('data-am-collapse'));
            // 加载Content
            $.myAdmin.loadContent(href);
            e.preventDefault();
        }
    });

    // 动态高度
    function getHeight() {
        return $('.bootstrap-table').height();
    }


    //默认设置
    //bootstrap-notify默认设置
    $.notifyDefaults({
        icon_type: 'class',
        type: 'success',//默认类型
        allow_dismiss: true,//显示关闭
        showProgressbar: false,//显示通知条
        z_index: 1051,
        delay: 1500//延迟
    });
    //bootstrap-table默认设置
    $.extend($.fn.bootstrapTable.defaults, {
        height: getHeight(),
        striped: true,
        search: false,
        showPaginationSwitch: false,
        showRefresh: true,
        showToggle: false,
        showColumns: true,
        minimumCountColumns: 2,
        clickToSelect: true,
        detailView: true,
        detailFormatter: 'detailFormatter',
        pagination: true,
        paginationLoop: false,
        classes: 'table table-hover table-no-bordered',
        sidePagination: 'server',
        silentSort: false,
        smartDisplay: false,
        escape: true,
        searchOnEnterKey: true,
        idField: 'id',
        maintainSelected: true,
        toolbar: '#toolbar',
        queryParams: function (params) {
            var form = $(this.toolbar).find('form');
            var arr = form.serializeArray();
            for (var i in arr) {
                eval('params.' + arr[i].name + '="' + arr[i].value + '"');
            }
            return params;
        },
    });

    // 数据表格动态高度
    $(window).resize(function () {
        $('#table').bootstrapTable('resetView', {
            height: getHeight()
        });
    });

    //出错提示
    $(document).ajaxError(function (event, request, settings) {
        debugger;
        var responseJSON = JSON.parse(request.responseText), msg;
        if (responseJSON) {
            msg = responseJSON.msg;

        } else {
            msg = request.status + '  (' + request.statusText + ')';
        }
        $.myNotify.danger(msg);
    });
    // To make Pace works on Ajax calls
    $(document).ajaxStart(function () {
        Pace.restart()
    })
    // 加载Content
    $.myAdmin.loadContent();
})(jQuery);