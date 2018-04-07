/**
 * author: cjbi
 * date: 2018/4/6
 * mail: cjbi@outlook.com
 */
/**
 * 常用的插件封装.
 * @author cjbi
 * @version 1.5.x-adminlte
 */
(function ($) {
    'use strict';
    var tableId = 'table';


    var _my_adimin_api = {
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
        loadContent: function (href, callback) {
            if (href) {
                //重写url，定位content
                history.pushState('', 0, href);
            }
            var url = location.href;
            if (url.indexOf('#') > 0 && url.substr(url.indexOf('#') + 1).length > 0) {
                var s = url.indexOf('#');
                if (url.substr(s - 1, 1) === '/'||url.substr(s + 1, 1) === '/') {
                    url = url.replace('#', '');
                } else {
                    url = url.replace("#", "/");
                }
                $('#content-wrapper').load(url, function () {
                    //重新加载组件
                    reloadComponent();
                    if (callback) {
                        callback();
                    }
                });
            }

            /**
             * jQuery因为删除dom事件也会失效，所以页面需要注册下部分依赖jQuery的组件
             */
            function reloadComponent() {
                //自定义事件
                $('[data-action]').on('click', function (event) {
                    debugger;
                    var str = $(this).attr('data-action');
                    var obj = eval('(' + str + ')');
                    if (obj && obj.type) {
                        if (obj.form && obj.url) {
                            switch (obj.type) {
                                //提交
                                case 'submit': {
                                    $(obj.form).submit({url: obj.url},
                                        function (data) {
                                            if (obj.after) {
                                                eval(obj.after + '(data)');
                                            }
                                        })
                                }
                            }
                        }
                    }
                });
            }


        }

    };

    var _clear = function () {
        $(this).get(0).reset();
    };

    // url 链接地址
    var _submit = function (opts, callback) {
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
                if (data.success) {
                    $.myNotify.success(data.msg);
                } else {
                    $.myNotify.warning(data.msg);
                }
                callback(data);
            },
            error: function (data) {
                callback(data);
            }
        });
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

    // 重置表单
    $.fn.clear = _clear;
    //提交
    $.fn.submit = _submit;

    //admin核心api
    $.myAdmin = _my_adimin_api;
    //提示框
    $.myNotify = _my_notify_api;

})(jQuery);
//定义一些处理
(function($){
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
        return $(window).height() - 170;
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
        search: true,
        searchOnEnterKey: true,
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
        //silentSort: false,
        smartDisplay: false,
        idField: 'id',
        sortName: 'id',
        sortOrder: 'desc',
        escape: true,
        searchOnEnterKey: true,
        idField: 'id',
        maintainSelected: true,
        toolbar: '#toolbar'
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
        var responseJSON = request.responseJSON, msg;
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