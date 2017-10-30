/**
 * 常用的插件封装.
 * @author cjbi
 */
(function ($) {

    $.extend({
        dialog_self: null,
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
    });
    //文本对话框
    $.fn.openDialog = function (opts) {
        var $form = $(this).children("form"),
            _title = $.extend({}, opts ? (opts.title || {}) : {}),
            _yes = $.extend({}, opts ? (opts.yes || function (index, layero) {
                $.msg('请定义参数yes');
            }) : {}),
            _end = $.extend(function (index, layero) {
                // 无论是确认还是取消，只要层被销毁了，end都会执行
                // 重置表单
                $form.clear();
                // 销毁表格验证
                $form.validator('destroy');
            }, opts ? (opts.yes || {}) : {}),
            editable = (opts.editable || false),
            before = (opts.before || function () {}),
            after = (opts.after || function () {}),
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
        if (editable == true) {
            var success = initFormData();
            if (!success) return;
        }
        before(getSelectedData());
        layer.open(defaultOpts);
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
            callback = (opts.callback || function () {});
        $.ajax({
            type: 'post',
            url: _url,
            dataType: _dataType,
            data: _data,
            success: function (data) {
                layer.msg(data.msg, {
                    time: '2000',
                    icon: data.success == true ? 6 : 5
                });
                callback(data);
            },
            error: function (data) {
                /*layer.msg('操作失败', {
                 time: 2000,
                 icon: 5
                 });*/
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
// 相当于定义了一个参数为$的匿名函数，并且将jQuery作为参数来调用这个匿名函数
//var $ = 123;
//(function($){
//    console.log($("p"));//$仍能正常使用
//})(jQuery)