/**
 * DataTables buttons integration for  Amaze UI 2.x, This requires Amaze UI 2.x and DataTables 1.10 or newer.
 * Created by cjbi on 2017/12/18.
 */
(function( factory ){
    if ( typeof define === 'function' && define.amd ) {
        // AMD
        define( ['jquery', 'datatables.net-bs4', 'datatables.net-buttons'], function ( $ ) {
            return factory( $, window, document );
        } );
    }
    else if ( typeof exports === 'object' ) {
        // CommonJS
        module.exports = function (root, $) {
            if ( ! root ) {
                root = window;
            }

            if ( ! $ || ! $.fn.dataTable ) {
                $ = require('datatables.net-bs4')(root, $).$;
            }

            if ( ! $.fn.dataTable.Buttons ) {
                require('datatables.net-buttons')(root, $);
            }

            return factory( $, root, root.document );
        };
    }
    else {
        // Browser
        factory( jQuery, window, document );
    }
}(function( $, window, document, undefined ) {
    'use strict';
    var DataTable = $.fn.dataTable;

    $.extend( true, DataTable.Buttons.defaults, {
        dom: {
            container: {
                className: 'am-btn-group am-btn-group-xs'
            },
            button: {
                className: 'am-btn am-btn-default am-btn-primary'
            },
            collection: {
                tag: 'div',
                className: 'dt-button-collection dropdown-menu',
                button: {
                    tag: 'a',
                    className: 'dt-button dropdown-item',
                    active: 'active',
                    disabled: 'disabled'
                }
            }
        }
    } );

    DataTable.ext.buttons.collection.className += ' dt-dropdown-toggle';

    return DataTable.Buttons;
}));
