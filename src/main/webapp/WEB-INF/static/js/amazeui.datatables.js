/*! DataTables Amaze UI 2.x integration v0.0.6 dt@1.10.11 */

/**
 * DataTables integration for  Amaze UI 2.x. This requires  Amaze UI 2.x and
 * DataTables 1.10 or newer.
 */

'use strict';

// var $ = require('jquery');

// @see https://www.datatables.net/download/npm
var DataTable = $.fn.dataTable;

// language
// https://github.com/DataTables/Plugins/blob/master/i18n/Chinese.lang
DataTable.defaults.oLanguage = {
    sProcessing:   "处理中...",
    sLengthMenu:   "显示 _MENU_ 项结果",
    sZeroRecords:  "没有匹配结果",
    sInfo:         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
    sInfoEmpty:    "显示第 0 至 0 项结果，共 0 项",
    sInfoFiltered: "(由 _MAX_ 项结果过滤)",
    sInfoPostFix:  "",
    sSearch:       "搜索：",
    sUrl:          "",
    sEmptyTable:     "表中数据为空",
    sLoadingRecords: "载入中...",
    // old, obsolete, using sThousands instead
    sInfoThousands:  ",",
    sThousands:  ",",
    oPaginate: {
        sFirst:    "首页",
        sPrevious: "上页",
        sNext:     "下页",
        sLast:     "末页"
    },
    oAria: {
        sSortAscending:  ": 以升序排列此列",
        sSortDescending: ": 以降序排列此列"
    }
};

// Set the defaults for DataTables initialisation
// https://datatables.net/reference/option/dom
$.extend(true, DataTable.defaults, {
    dom: "<'am-g am-datatable-hd'<'am-u-sm-6'l><'am-u-sm-6'f>>" +
    "<'am-g'<'am-u-sm-12'tr>>" +
    "<'am-g am-datatable-footer'<'am-u-sm-5'i><'am-u-sm-7'p>>",
    renderer: 'amazeui'
});


/* Default class modification */
$.extend(DataTable.ext.classes, {
  /* Features */
    sWrapper: 'dataTables_wrapper am-datatable am-form-inline dt-amazeui',
    sFilter: 'dataTables_filter am-datatable-filter',
    sFilterInput: 'am-form-field am-input-sm',
    sInfo: 'dataTables_info am-datatable-info',
    sPaging: 'dataTables_paginate paging_', /* Note that the type is postfixed */
    sLength: 'dataTables_length am-form-group am-datatable-length',
    sLengthSelect: 'am-form-select am-input-sm',
    sProcessing: "dataTables_processing",

  /* Sorting */
    sSortAsc: "sorting_asc",
    sSortDesc: "sorting_desc",
    sSortable: "sorting", /* Sortable in both directions */
    sSortableAsc: "sorting_asc_disabled",
    sSortableDesc: "sorting_desc_disabled",
    sSortableNone: "sorting_disabled",
    sSortColumn: "sorting_" /* Note that an int is postfixed for the sorting order */
});



/* Amaze UI paging button renderer */
DataTable.ext.renderer.pageButton.amazeui = function(settings, host, idx, buttons, page, pages) {
    var api = new DataTable.Api(settings);
    var classes = settings.oClasses;
    var lang = settings.oLanguage.oPaginate;
    var btnDisplay, btnClass, counter = 0;

    var attach = function(container, buttons) {
        var i, ien, node, button;
        var clickHandler = function(e) {
            e.preventDefault();
            if (!$(e.currentTarget).hasClass('am-disabled')) {
                api.page(e.data.action).draw(false);
            }
        };

        for (i = 0, ien = buttons.length; i < ien; i++) {
            button = buttons[i];

            if ($.isArray(button)) {
                attach(container, button);
            }
            else {
                btnDisplay = '';
                btnClass = '';

                switch (button) {
                    case 'ellipsis':
                        btnDisplay = '&hellip;';
                        btnClass = 'am-disabled';
                        break;

                    case 'first':
                        btnDisplay = lang.sFirst;
                        btnClass = button + (page > 0 ?
                                '' : ' am-disabled');
                        break;

                    case 'previous':
                        btnDisplay = lang.sPrevious;
                        btnClass = button + (page > 0 ?
                                '' : ' am-disabled');
                        break;

                    case 'next':
                        btnDisplay = lang.sNext;
                        btnClass = button + (page < pages - 1 ?
                                '' : ' am-disabled');
                        break;

                    case 'last':
                        btnDisplay = lang.sLast;
                        btnClass = button + (page < pages - 1 ?
                                '' : ' am-disabled');
                        break;

                    default:
                        btnDisplay = button + 1;
                        btnClass = page === button ?
                            'am-active' : '';
                        break;
                }

                if (btnDisplay) {
                    node = $('<li>', {
                        'class': classes.sPageButton + ' ' + btnClass,
                        'id': idx === 0 && typeof button === 'string' ?
                            settings.sTableId + '_' + button :
                            null
                    })
                        .append($('<a>', {
                                'href': '#',
                                'aria-controls': settings.sTableId,
                                'data-dt-idx': counter,
                                'tabindex': settings.iTabIndex
                            })
                                .html(btnDisplay)
                        )
                        .appendTo(container);

                    settings.oApi._fnBindAction(
                        node, {action: button}, clickHandler
                    );

                    counter++;
                }
            }
        }
    };

    // IE9 throws an 'unknown error' if document.activeElement is used
    // inside an iframe or frame.
    var activeEl;

    try {
        // Because this approach is destroying and recreating the paging
        // elements, focus is lost on the select button which is bad for
        // accessibility. So we want to restore focus once the draw has
        // completed
        activeEl = $(document.activeElement).data('dt-idx');
    } catch (e) {
    }

    attach(
        $(host).empty().
        html('<ul class="am-datatable-pager am-pagination am-pagination-right am-text-sm"/>').
        children('ul'),
        buttons
    );

    if (activeEl) {
        $(host).find('[data-dt-idx=' + activeEl + ']').focus();
    }
};


/*
 * TableTools Amaze UI compatibility
 * Required TableTools 2.1+
 */
if (DataTable.TableTools) {
    // Set the classes that TableTools uses to something suitable for Bootstrap
    $.extend(true, DataTable.TableTools.classes, {
        container: "DTTT am-btn-group",
        buttons: {
            normal: "am-btn am-btn-default",
            disabled: "am-disabled"
        },
        collection: {
            container: "DTTT_dropdown dropdown-menu",
            buttons: {
                normal: "",
                disabled: "am-disabled"
            }
        },
        print: {
            info: "DTTT_print_info"
        },
        select: {
            row: "am-active"
        }
    });

    // Have the collection use a bootstrap compatible drop down
    $.extend(true, DataTable.TableTools.DEFAULTS.oTags, {
        collection: {
            container: "ul",
            button: "li",
            liner: "a"
        }
    });
}

// module.exports = DataTable;
