/*! Amaze UI v3.0.0-alpha.beta | by Amaze UI Team | (c) 2017 AllMobilize, Inc. | Licensed under GPL | 2017-03-06T14:24:38+0800 */ 
(function webpackUniversalModuleDefinition(root, factory) {
	if(typeof exports === 'object' && typeof module === 'object')
		module.exports = factory(require("jquery"));
	else if(typeof define === 'function' && define.amd)
		define(["jquery"], factory);
	else if(typeof exports === 'object')
		exports["AMUI"] = factory(require("jquery"));
	else
		root["AMUI"] = factory(root["jQuery"]);
})(this, function(__WEBPACK_EXTERNAL_MODULE_1__) {
return /******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	__webpack_require__(3);
	__webpack_require__(4);
	__webpack_require__(5);
	__webpack_require__(6);
	__webpack_require__(7);
	__webpack_require__(8);
	__webpack_require__(9);
	__webpack_require__(10);
	__webpack_require__(11);
	__webpack_require__(12);
	__webpack_require__(13);
	__webpack_require__(14);
	__webpack_require__(15);
	__webpack_require__(16);
	__webpack_require__(17);
	__webpack_require__(18);
	__webpack_require__(19);
	__webpack_require__(21);
	__webpack_require__(22);
	__webpack_require__(20);
	__webpack_require__(23);
	__webpack_require__(24);
	__webpack_require__(25);
	__webpack_require__(26);
	__webpack_require__(27);
	__webpack_require__(28);
	__webpack_require__(29);
	__webpack_require__(30);
	__webpack_require__(31);

	module.exports = $.AMUI = UI;


/***/ },
/* 1 */
/***/ function(module, exports) {

	module.exports = __WEBPACK_EXTERNAL_MODULE_1__;

/***/ },
/* 2 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);

	if (typeof $ === 'undefined') {
	  throw new Error('Amaze UI 3.x requires jQuery');
	}

	var UI = $.AMUI || {};
	var $win = $(window);
	var doc = window.document;
	var $html = $('html');

	UI.VERSION = '3.0.0-alpha.beta';

	UI.support = {};

	UI.support.transition = (function() {
	  var transitionEnd = (function() {
	    // https://developer.mozilla.org/en-US/docs/Web/Events/transitionend#Browser_compatibility
	    var element = doc.body || doc.documentElement;
	    var transEndEventNames = {
	      WebkitTransition: 'webkitTransitionEnd',
	      MozTransition: 'transitionend',
	      OTransition: 'oTransitionEnd otransitionend',
	      transition: 'transitionend'
	    };

	    for (var name in transEndEventNames) {
	      if (element.style[name] !== undefined) {
	        return transEndEventNames[name];
	      }
	    }
	  })();

	  return transitionEnd && {end: transitionEnd};
	})();

	UI.support.animation = (function() {
	  var animationEnd = (function() {
	    var element = doc.body || doc.documentElement;
	    var animEndEventNames = {
	      WebkitAnimation: 'webkitAnimationEnd',
	      MozAnimation: 'animationend',
	      OAnimation: 'oAnimationEnd oanimationend',
	      animation: 'animationend'
	    };

	    for (var name in animEndEventNames) {
	      if (element.style[name] !== undefined) {
	        return animEndEventNames[name];
	      }
	    }
	  })();

	  return animationEnd && {end: animationEnd};
	})();

	/* eslint-disable dot-notation */
	UI.support.touch = (
	('ontouchstart' in window &&
	navigator.userAgent.toLowerCase().match(/mobile|tablet/)) ||
	(window.DocumentTouch && document instanceof window.DocumentTouch) ||
	(window.navigator['msPointerEnabled'] &&
	window.navigator['msMaxTouchPoints'] > 0) || // IE 10
	(window.navigator['pointerEnabled'] &&
	window.navigator['maxTouchPoints'] > 0) || // IE >=11
	false);
	/* eslint-enable dot-notation */

	// https://developer.mozilla.org/zh-CN/docs/DOM/MutationObserver
	UI.support.mutationobserver = (window.MutationObserver ||
	window.WebKitMutationObserver || null);

	// https://github.com/Modernizr/Modernizr/blob/924c7611c170ef2dc502582e5079507aff61e388/feature-detects/forms/validation.js#L20
	UI.support.formValidation = (typeof document.createElement('form').
	  checkValidity === 'function');

	UI.utils = {};

	/**
	 * Debounce function
	 *
	 * @param {function} func  Function to be debounced
	 * @param {number} wait Function execution threshold in milliseconds
	 * @param {bool} immediate  Whether the function should be called at
	 *                          the beginning of the delay instead of the
	 *                          end. Default is false.
	 * @description Executes a function when it stops being invoked for n seconds
	 * @see  _.debounce() http://underscorejs.org
	 */
	UI.utils.debounce = function(func, wait, immediate) {
	  var timeout;
	  return function() {
	    var context = this;
	    var args = arguments;
	    var later = function() {
	      timeout = null;
	      if (!immediate) {
	        func.apply(context, args);
	      }
	    };
	    var callNow = immediate && !timeout;

	    clearTimeout(timeout);
	    timeout = setTimeout(later, wait);

	    if (callNow) {
	      func.apply(context, args);
	    }
	  };
	};

	UI.utils.isInView = function(element, options) {
	  var $element = $(element);
	  var visible = !!($element.width() || $element.height()) &&
	    $element.css('display') !== 'none';

	  if (!visible) {
	    return false;
	  }

	  var windowLeft = $win.scrollLeft();
	  var windowTop = $win.scrollTop();
	  var offset = $element.offset();
	  var left = offset.left;
	  var top = offset.top;

	  options = $.extend({topOffset: 0, leftOffset: 0}, options);

	  return (top + $element.height() >= windowTop &&
	  top - options.topOffset <= windowTop + $win.height() &&
	  left + $element.width() >= windowLeft &&
	  left - options.leftOffset <= windowLeft + $win.width());
	};

	UI.utils.parseOptions = UI.utils.options = function(string) {
	  if ($.isPlainObject(string)) {
	    return string;
	  }

	  var start = (string ? string.indexOf('{') : -1);
	  var options = {};

	  if (start != -1) {
	    try {
	      options = (new Function('',
	        'var json = ' + string.substr(start) +
	        '; return JSON.parse(JSON.stringify(json));'))();
	    } catch (e) {
	    }
	  }

	  return options;
	};

	UI.utils.generateGUID = function(namespace) {
	  var uid = namespace + '-' || 'am-';

	  do {
	    uid += Math.random().toString(36).substring(2, 7);
	  } while (document.getElementById(uid));

	  return uid;
	};

	// @see https://davidwalsh.name/get-absolute-url
	UI.utils.getAbsoluteUrl = (function() {
	  var a;

	  return function(url) {
	    if (!a) {
	      a = document.createElement('a');
	    }

	    a.href = url;

	    return a.href;
	  };
	})();

	/**
	 * Plugin AMUI Component to jQuery
	 *
	 * @param {String} name - plugin name
	 * @param {Function} Component - plugin constructor
	 * @param {Object} [pluginOption]
	 * @param {String} pluginOption.dataOptions
	 * @param {Function} pluginOption.methodCall - custom method call
	 * @param {Function} pluginOption.before
	 * @param {Function} pluginOption.after
	 * @since v2.4.1
	 */
	UI.plugin = function UIPlugin(name, Component, pluginOption) {
	  var old = $.fn[name];
	  pluginOption = pluginOption || {};

	  $.fn[name] = function(option) {
	    var allArgs = Array.prototype.slice.call(arguments, 0);
	    var args = allArgs.slice(1);
	    var propReturn;
	    var $set = this.each(function() {
	      var $this = $(this);
	      var dataName = 'amui.' + name;
	      var dataOptionsName = pluginOption.dataOptions || ('data-am-' + name);
	      var instance = $this.data(dataName);
	      var options = $.extend({},
	        UI.utils.parseOptions($this.attr(dataOptionsName)),
	        typeof option === 'object' && option);

	      if (!instance && option === 'destroy') {
	        return;
	      }

	      if (!instance) {
	        $this.data(dataName, (instance = new Component(this, options)));
	      }

	      // custom method call
	      if (pluginOption.methodCall) {
	        pluginOption.methodCall.call($this, allArgs, instance);
	      } else {
	        // before method call
	        pluginOption.before &&
	        pluginOption.before.call($this, allArgs, instance);

	        if (typeof option === 'string') {
	          propReturn = typeof instance[option] === 'function' ?
	            instance[option].apply(instance, args) : instance[option];
	        }

	        // after method call
	        pluginOption.after && pluginOption.after.call($this, allArgs, instance);
	      }
	    });

	    return (propReturn === undefined) ? $set : propReturn;
	  };

	  $.fn[name].Constructor = Component;

	  // no conflict
	  $.fn[name].noConflict = function() {
	    $.fn[name] = old;
	    return this;
	  };

	  UI[name] = Component;
	};

	// http://blog.alexmaccaw.com/css-transitions
	$.fn.emulateTransitionEnd = function(duration) {
	  var called = false;
	  var $el = this;

	  $(this).one(UI.support.transition.end, function() {
	    called = true;
	  });

	  var callback = function() {
	    if (!called) {
	      $($el).trigger(UI.support.transition.end);
	    }
	    $el.transitionEndTimmer = undefined;
	  };
	  this.transitionEndTimmer = setTimeout(callback, duration);
	  return this;
	};

	$.fn.redraw = function() {
	  return this.each(function() {
	    /* eslint-disable */
	    var redraw = this.offsetHeight;
	    /* eslint-enable */
	  });
	};

	$.fn.transitionEnd = function(callback) {
	  var endEvent = UI.support.transition.end;
	  var dom = this;

	  function fireCallBack(e) {
	    callback.call(this, e);
	    endEvent && dom.off(endEvent, fireCallBack);
	  }

	  if (callback && endEvent) {
	    dom.on(endEvent, fireCallBack);
	  }

	  return this;
	};

	$.fn.removeClassRegEx = function() {
	  return this.each(function(regex) {
	    var classes = $(this).attr('class');

	    if (!classes || !regex) {
	      return false;
	    }

	    var classArray = [];
	    classes = classes.split(' ');

	    for (var i = 0, len = classes.length; i < len; i++) {
	      if (!classes[i].match(regex)) {
	        classArray.push(classes[i]);
	      }
	    }

	    $(this).attr('class', classArray.join(' '));
	  });
	};

	//
	$.fn.alterClass = function(removals, additions) {
	  var self = this;

	  if (removals.indexOf('*') === -1) {
	    // Use native jQuery methods if there is no wildcard matching
	    self.removeClass(removals);
	    return !additions ? self : self.addClass(additions);
	  }

	  var classPattern = new RegExp('\\s' +
	  removals.
	    replace(/\*/g, '[A-Za-z0-9-_]+').
	    split(' ').
	    join('\\s|\\s') +
	  '\\s', 'g');

	  self.each(function(i, it) {
	    var cn = ' ' + it.className + ' ';
	    while (classPattern.test(cn)) {
	      cn = cn.replace(classPattern, ' ');
	    }
	    it.className = $.trim(cn);
	  });

	  return !additions ? self : self.addClass(additions);
	};

	// handle multiple browsers for requestAnimationFrame()
	// http://www.paulirish.com/2011/requestanimationframe-for-smart-animating/
	// https://github.com/gnarf/jquery-requestAnimationFrame
	UI.utils.rAF = (function() {
	  return window.requestAnimationFrame ||
	    window.webkitRequestAnimationFrame ||
	    window.mozRequestAnimationFrame ||
	    window.oRequestAnimationFrame ||
	      // if all else fails, use setTimeout
	    function(callback) {
	      return window.setTimeout(callback, 1000 / 60); // shoot for 60 fps
	    };
	})();

	// handle multiple browsers for cancelAnimationFrame()
	UI.utils.cancelAF = (function() {
	  return window.cancelAnimationFrame ||
	    window.webkitCancelAnimationFrame ||
	    window.mozCancelAnimationFrame ||
	    window.oCancelAnimationFrame ||
	    function(id) {
	      window.clearTimeout(id);
	    };
	})();

	// via http://davidwalsh.name/detect-scrollbar-width
	UI.utils.measureScrollbar = function() {
	  if (document.body.clientWidth >= window.innerWidth) {
	    return 0;
	  }

	  // if ($html.width() >= window.innerWidth) return;
	  // var scrollbarWidth = window.innerWidth - $html.width();
	  var $measure = $('<div ' +
	  'style="width: 100px;height: 100px;overflow: scroll;' +
	  'position: absolute;top: -9999px;"></div>');

	  $(document.body).append($measure);

	  var scrollbarWidth = $measure[0].offsetWidth - $measure[0].clientWidth;

	  $measure.remove();

	  return scrollbarWidth;
	};

	UI.utils.imageLoader = function($image, callback) {
	  function loaded() {
	    callback($image[0]);
	  }

	  function bindLoad() {
	    this.one('load', loaded);
	    if (/MSIE (\d+\.\d+);/.test(navigator.userAgent)) {
	      var src = this.attr('src');
	      var param = src.match(/\?/) ? '&' : '?';

	      param += 'random=' + (new Date()).getTime();
	      this.attr('src', src + param);
	    }
	  }

	  if (!$image.attr('src')) {
	    loaded();
	    return;
	  }

	  if ($image[0].complete || $image[0].readyState === 4) {
	    loaded();
	  } else {
	    bindLoad.call($image);
	  }
	};

	/**
	 * @see https://github.com/cho45/micro-template.js
	 * (c) cho45 http://cho45.github.com/mit-license
	 */
	UI.template = function(id, data) {
	  var me = UI.template;

	  if (!me.cache[id]) {
	    me.cache[id] = (function() {
	      var name = id;
	      var string = /^[\w\-]+$/.test(id) ?
	        me.get(id) : (name = 'template(string)', id); // no warnings

	      var line = 1;
	      /* eslint-disable max-len, quotes */
	      var body = ('try { ' + (me.variable ?
	      'var ' + me.variable + ' = this.stash;' : 'with (this.stash) { ') +
	      "this.ret += '" +
	      string.
	        replace(/<%/g, '\x11').replace(/%>/g, '\x13'). // if you want other tag, just edit this line
	        replace(/'(?![^\x11\x13]+?\x13)/g, '\\x27').
	        replace(/^\s*|\s*$/g, '').
	        replace(/\n/g, function() {
	          return "';\nthis.line = " + (++line) + "; this.ret += '\\n";
	        }).
	        replace(/\x11-(.+?)\x13/g, "' + ($1) + '").
	        replace(/\x11=(.+?)\x13/g, "' + this.escapeHTML($1) + '").
	        replace(/\x11(.+?)\x13/g, "'; $1; this.ret += '") +
	      "'; " + (me.variable ? "" : "}") + "return this.ret;" +
	      "} catch (e) { throw 'TemplateError: ' + e + ' (on " + name +
	      "' + ' line ' + this.line + ')'; } " +
	      "//# sourceURL=" + name + "\n" // source map
	      ).replace(/this\.ret \+= '';/g, '');
	      /* eslint-enable max-len, quotes */
	      var func = new Function(body);
	      var map = {
	        '&': '&amp;',
	        '<': '&lt;',
	        '>': '&gt;',
	        '\x22': '&#x22;',
	        '\x27': '&#x27;'
	      };
	      var escapeHTML = function(string) {
	        return ('' + string).replace(/[&<>\'\"]/g, function(_) {
	          return map[_];
	        });
	      };

	      return function(stash) {
	        return func.call(me.context = {
	          escapeHTML: escapeHTML,
	          line: 1,
	          ret: '',
	          stash: stash
	        });
	      };
	    })();
	  }

	  return data ? me.cache[id](data) : me.cache[id];
	};

	UI.template.cache = {};

	UI.template.get = function(id) {
	  if (id) {
	    var element = document.getElementById(id);
	    return element && element.innerHTML || '';
	  }
	};

	// Dom mutation watchers
	UI.DOMWatchers = [];
	UI.DOMReady = false;
	UI.ready = function(callback) {
	  UI.DOMWatchers.push(callback);
	  if (UI.DOMReady) {
	    // console.log('Ready call');
	    callback(document);
	  }
	};

	UI.DOMObserve = function(elements, options, callback) {
	  var Observer = UI.support.mutationobserver;
	  if (!Observer) {
	    return;
	  }

	  options = $.isPlainObject(options) ?
	    options : {childList: true, subtree: true};

	  callback = typeof callback === 'function' && callback || function() {
	  };

	  $(elements).each(function() {
	    var element = this;
	    var $element = $(element);

	    if ($element.data('am.observer')) {
	      return;
	    }

	    try {
	      var observer = new Observer(UI.utils.debounce(
	        function(mutations, instance) {
	          callback.call(element, mutations, instance);
	          // trigger this event manually if MutationObserver not supported
	          $element.trigger('changed.dom.amui');
	        }, 50));

	      observer.observe(element, options);

	      $element.data('am.observer', observer);
	    } catch (e) {
	    }
	  });
	};

	$.fn.DOMObserve = function(options, callback) {
	  return this.each(function() {
	    /* eslint-disable new-cap */
	    UI.DOMObserve(this, options, callback);
	    /* eslint-enable new-cap */
	  });
	};

	if (UI.support.touch) {
	  $html.addClass('am-touch');
	}

	$(document).on('changed.dom.amui', function(e) {
	  var element = e.target;

	  // TODO: just call changed element's watcher
	  //       every watcher callback should have a key
	  //       use like this: <div data-am-observe='key1, key2'>
	  //       get keys via $(element).data('amObserve')
	  //       call functions store with these keys
	  $.each(UI.DOMWatchers, function(i, watcher) {
	    watcher(element);
	  });
	});

	$(function() {
	  var $body = $(document.body);

	  UI.DOMReady = true;

	  // Run default init
	  $.each(UI.DOMWatchers, function(i, watcher) {
	    watcher(document);
	  });

	  // watches DOM
	  /* eslint-disable new-cap */
	  UI.DOMObserve('[data-am-observe]');
	  /* eslint-enable */

	  $html.removeClass('no-js').addClass('js');

	  UI.support.animation && $html.addClass('cssanimations');

	  // iOS standalone mode
	  if (window.navigator.standalone) {
	    $html.addClass('am-standalone');
	  }

	  $('.am-topbar-fixed-top').length &&
	  $body.addClass('am-with-topbar-fixed-top');

	  $('.am-topbar-fixed-bottom').length &&
	  $body.addClass('am-with-topbar-fixed-bottom');

	  // Remove responsive classes in .am-layout
	  var $layout = $('.am-layout');
	  $layout.find('[class*="md-block-grid"]').alterClass('md-block-grid-*');
	  $layout.find('[class*="lg-block-grid"]').alterClass('lg-block-grid');

	  // widgets not in .am-layout
	  $('[data-am-widget]').each(function() {
	    var $widget = $(this);
	    // console.log($widget.parents('.am-layout').length)
	    if ($widget.parents('.am-layout').length === 0) {
	      $widget.addClass('am-no-layout');
	    }
	  });
	});

	module.exports = UI;


/***/ },
/* 3 */
/***/ function(module, exports, __webpack_require__) {

	/*! Hammer.JS - v2.0.8 - 2016-04-22
	 * http://hammerjs.github.io/
	 *
	 * Copyright (c) 2016 Jorik Tangelder;
	 * Licensed under the MIT license */

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	var VENDOR_PREFIXES = ['', 'webkit', 'Moz', 'MS', 'ms', 'o'];
	var TEST_ELEMENT = document.createElement('div');

	var TYPE_FUNCTION = 'function';

	var round = Math.round;
	var abs = Math.abs;
	var now = Date.now;

	/**
	 * set a timeout with a given scope
	 * @param {Function} fn
	 * @param {Number} timeout
	 * @param {Object} context
	 * @returns {number}
	 */
	function setTimeoutContext(fn, timeout, context) {
	  return setTimeout(bindFn(fn, context), timeout);
	}

	/**
	 * if the argument is an array, we want to execute the fn on each entry
	 * if it aint an array we don't want to do a thing.
	 * this is used by all the methods that accept a single and array argument.
	 * @param {*|Array} arg
	 * @param {String} fn
	 * @param {Object} [context]
	 * @returns {Boolean}
	 */
	function invokeArrayArg(arg, fn, context) {
	  if (Array.isArray(arg)) {
	    each(arg, context[fn], context);
	    return true;
	  }
	  return false;
	}

	/**
	 * walk objects and arrays
	 * @param {Object} obj
	 * @param {Function} iterator
	 * @param {Object} context
	 */
	function each(obj, iterator, context) {
	  var i;

	  if (!obj) {
	    return;
	  }

	  if (obj.forEach) {
	    obj.forEach(iterator, context);
	  } else if (obj.length !== undefined) {
	    i = 0;
	    while (i < obj.length) {
	      iterator.call(context, obj[i], i, obj);
	      i++;
	    }
	  } else {
	    for (i in obj) {
	      obj.hasOwnProperty(i) && iterator.call(context, obj[i], i, obj);
	    }
	  }
	}

	/**
	 * wrap a method with a deprecation warning and stack trace
	 * @param {Function} method
	 * @param {String} name
	 * @param {String} message
	 * @returns {Function} A new function wrapping the supplied method.
	 */
	function deprecate(method, name, message) {
	  var deprecationMessage = 'DEPRECATED METHOD: ' + name + '\n' + message + ' AT \n';
	  return function() {
	    var e = new Error('get-stack-trace');
	    var stack = e && e.stack ? e.stack.replace(/^[^\(]+?[\n$]/gm, '')
	      .replace(/^\s+at\s+/gm, '')
	      .replace(/^Object.<anonymous>\s*\(/gm, '{anonymous}()@') : 'Unknown Stack Trace';

	    var log = window.console && (window.console.warn || window.console.log);
	    if (log) {
	      log.call(window.console, deprecationMessage, stack);
	    }
	    return method.apply(this, arguments);
	  };
	}

	/**
	 * extend object.
	 * means that properties in dest will be overwritten by the ones in src.
	 * @param {Object} target
	 * @param {...Object} objects_to_assign
	 * @returns {Object} target
	 */
	var assign;
	if (typeof Object.assign !== 'function') {
	  assign = function assign(target) {
	    if (target === undefined || target === null) {
	      throw new TypeError('Cannot convert undefined or null to object');
	    }

	    var output = Object(target);
	    for (var index = 1; index < arguments.length; index++) {
	      var source = arguments[index];
	      if (source !== undefined && source !== null) {
	        for (var nextKey in source) {
	          if (source.hasOwnProperty(nextKey)) {
	            output[nextKey] = source[nextKey];
	          }
	        }
	      }
	    }
	    return output;
	  };
	} else {
	  assign = Object.assign;
	}

	/**
	 * extend object.
	 * means that properties in dest will be overwritten by the ones in src.
	 * @param {Object} dest
	 * @param {Object} src
	 * @param {Boolean} [merge=false]
	 * @returns {Object} dest
	 */
	var extend = deprecate(function extend(dest, src, merge) {
	  var keys = Object.keys(src);
	  var i = 0;
	  while (i < keys.length) {
	    if (!merge || (merge && dest[keys[i]] === undefined)) {
	      dest[keys[i]] = src[keys[i]];
	    }
	    i++;
	  }
	  return dest;
	}, 'extend', 'Use `assign`.');

	/**
	 * merge the values from src in the dest.
	 * means that properties that exist in dest will not be overwritten by src
	 * @param {Object} dest
	 * @param {Object} src
	 * @returns {Object} dest
	 */
	var merge = deprecate(function merge(dest, src) {
	  return extend(dest, src, true);
	}, 'merge', 'Use `assign`.');

	/**
	 * simple class inheritance
	 * @param {Function} child
	 * @param {Function} base
	 * @param {Object} [properties]
	 */
	function inherit(child, base, properties) {
	  var baseP = base.prototype,
	    childP;

	  childP = child.prototype = Object.create(baseP);
	  childP.constructor = child;
	  childP._super = baseP;

	  if (properties) {
	    assign(childP, properties);
	  }
	}

	/**
	 * simple function bind
	 * @param {Function} fn
	 * @param {Object} context
	 * @returns {Function}
	 */
	function bindFn(fn, context) {
	  return function boundFn() {
	    return fn.apply(context, arguments);
	  };
	}

	/**
	 * let a boolean value also be a function that must return a boolean
	 * this first item in args will be used as the context
	 * @param {Boolean|Function} val
	 * @param {Array} [args]
	 * @returns {Boolean}
	 */
	function boolOrFn(val, args) {
	  if (typeof val == TYPE_FUNCTION) {
	    return val.apply(args ? args[0] || undefined : undefined, args);
	  }
	  return val;
	}

	/**
	 * use the val2 when val1 is undefined
	 * @param {*} val1
	 * @param {*} val2
	 * @returns {*}
	 */
	function ifUndefined(val1, val2) {
	  return (val1 === undefined) ? val2 : val1;
	}

	/**
	 * addEventListener with multiple events at once
	 * @param {EventTarget} target
	 * @param {String} types
	 * @param {Function} handler
	 */
	function addEventListeners(target, types, handler) {
	  each(splitStr(types), function(type) {
	    target.addEventListener(type, handler, false);
	  });
	}

	/**
	 * removeEventListener with multiple events at once
	 * @param {EventTarget} target
	 * @param {String} types
	 * @param {Function} handler
	 */
	function removeEventListeners(target, types, handler) {
	  each(splitStr(types), function(type) {
	    target.removeEventListener(type, handler, false);
	  });
	}

	/**
	 * find if a node is in the given parent
	 * @method hasParent
	 * @param {HTMLElement} node
	 * @param {HTMLElement} parent
	 * @return {Boolean} found
	 */
	function hasParent(node, parent) {
	  while (node) {
	    if (node == parent) {
	      return true;
	    }
	    node = node.parentNode;
	  }
	  return false;
	}

	/**
	 * small indexOf wrapper
	 * @param {String} str
	 * @param {String} find
	 * @returns {Boolean} found
	 */
	function inStr(str, find) {
	  return str.indexOf(find) > -1;
	}

	/**
	 * split string on whitespace
	 * @param {String} str
	 * @returns {Array} words
	 */
	function splitStr(str) {
	  return str.trim().split(/\s+/g);
	}

	/**
	 * find if a array contains the object using indexOf or a simple polyFill
	 * @param {Array} src
	 * @param {String} find
	 * @param {String} [findByKey]
	 * @return {Boolean|Number} false when not found, or the index
	 */
	function inArray(src, find, findByKey) {
	  if (src.indexOf && !findByKey) {
	    return src.indexOf(find);
	  } else {
	    var i = 0;
	    while (i < src.length) {
	      if ((findByKey && src[i][findByKey] == find) || (!findByKey && src[i] === find)) {
	        return i;
	      }
	      i++;
	    }
	    return -1;
	  }
	}

	/**
	 * convert array-like objects to real arrays
	 * @param {Object} obj
	 * @returns {Array}
	 */
	function toArray(obj) {
	  return Array.prototype.slice.call(obj, 0);
	}

	/**
	 * unique array with objects based on a key (like 'id') or just by the array's value
	 * @param {Array} src [{id:1},{id:2},{id:1}]
	 * @param {String} [key]
	 * @param {Boolean} [sort=False]
	 * @returns {Array} [{id:1},{id:2}]
	 */
	function uniqueArray(src, key, sort) {
	  var results = [];
	  var values = [];
	  var i = 0;

	  while (i < src.length) {
	    var val = key ? src[i][key] : src[i];
	    if (inArray(values, val) < 0) {
	      results.push(src[i]);
	    }
	    values[i] = val;
	    i++;
	  }

	  if (sort) {
	    if (!key) {
	      results = results.sort();
	    } else {
	      results = results.sort(function sortUniqueArray(a, b) {
	        return a[key] > b[key];
	      });
	    }
	  }

	  return results;
	}

	/**
	 * get the prefixed property
	 * @param {Object} obj
	 * @param {String} property
	 * @returns {String|Undefined} prefixed
	 */
	function prefixed(obj, property) {
	  var prefix, prop;
	  var camelProp = property[0].toUpperCase() + property.slice(1);

	  var i = 0;
	  while (i < VENDOR_PREFIXES.length) {
	    prefix = VENDOR_PREFIXES[i];
	    prop = (prefix) ? prefix + camelProp : property;

	    if (prop in obj) {
	      return prop;
	    }
	    i++;
	  }
	  return undefined;
	}

	/**
	 * get a unique id
	 * @returns {number} uniqueId
	 */
	var _uniqueId = 1;
	function uniqueId() {
	  return _uniqueId++;
	}

	/**
	 * get the window object of an element
	 * @param {HTMLElement} element
	 * @returns {DocumentView|Window}
	 */
	function getWindowForElement(element) {
	  var doc = element.ownerDocument || element;
	  return (doc.defaultView || doc.parentWindow || window);
	}

	var MOBILE_REGEX = /mobile|tablet|ip(ad|hone|od)|android/i;

	var SUPPORT_TOUCH = ('ontouchstart' in window);
	var SUPPORT_POINTER_EVENTS = prefixed(window, 'PointerEvent') !== undefined;
	var SUPPORT_ONLY_TOUCH = SUPPORT_TOUCH && MOBILE_REGEX.test(navigator.userAgent);

	var INPUT_TYPE_TOUCH = 'touch';
	var INPUT_TYPE_PEN = 'pen';
	var INPUT_TYPE_MOUSE = 'mouse';
	var INPUT_TYPE_KINECT = 'kinect';

	var COMPUTE_INTERVAL = 25;

	var INPUT_START = 1;
	var INPUT_MOVE = 2;
	var INPUT_END = 4;
	var INPUT_CANCEL = 8;

	var DIRECTION_NONE = 1;
	var DIRECTION_LEFT = 2;
	var DIRECTION_RIGHT = 4;
	var DIRECTION_UP = 8;
	var DIRECTION_DOWN = 16;

	var DIRECTION_HORIZONTAL = DIRECTION_LEFT | DIRECTION_RIGHT;
	var DIRECTION_VERTICAL = DIRECTION_UP | DIRECTION_DOWN;
	var DIRECTION_ALL = DIRECTION_HORIZONTAL | DIRECTION_VERTICAL;

	var PROPS_XY = ['x', 'y'];
	var PROPS_CLIENT_XY = ['clientX', 'clientY'];

	/**
	 * create new input type manager
	 * @param {Manager} manager
	 * @param {Function} callback
	 * @returns {Input}
	 * @constructor
	 */
	function Input(manager, callback) {
	  var self = this;
	  this.manager = manager;
	  this.callback = callback;
	  this.element = manager.element;
	  this.target = manager.options.inputTarget;

	  // smaller wrapper around the handler, for the scope and the enabled state of the manager,
	  // so when disabled the input events are completely bypassed.
	  this.domHandler = function(ev) {
	    if (boolOrFn(manager.options.enable, [manager])) {
	      self.handler(ev);
	    }
	  };

	  this.init();

	}

	Input.prototype = {
	  /**
	   * should handle the inputEvent data and trigger the callback
	   * @virtual
	   */
	  handler: function() { },

	  /**
	   * bind the events
	   */
	  init: function() {
	    this.evEl && addEventListeners(this.element, this.evEl, this.domHandler);
	    this.evTarget && addEventListeners(this.target, this.evTarget, this.domHandler);
	    this.evWin && addEventListeners(getWindowForElement(this.element), this.evWin, this.domHandler);
	  },

	  /**
	   * unbind the events
	   */
	  destroy: function() {
	    this.evEl && removeEventListeners(this.element, this.evEl, this.domHandler);
	    this.evTarget && removeEventListeners(this.target, this.evTarget, this.domHandler);
	    this.evWin && removeEventListeners(getWindowForElement(this.element), this.evWin, this.domHandler);
	  }
	};

	/**
	 * create new input type manager
	 * called by the Manager constructor
	 * @param {Hammer} manager
	 * @returns {Input}
	 */
	function createInputInstance(manager) {
	  var Type;
	  var inputClass = manager.options.inputClass;

	  if (inputClass) {
	    Type = inputClass;
	  } else if (SUPPORT_POINTER_EVENTS) {
	    Type = PointerEventInput;
	  } else if (SUPPORT_ONLY_TOUCH) {
	    Type = TouchInput;
	  } else if (!SUPPORT_TOUCH) {
	    Type = MouseInput;
	  } else {
	    Type = TouchMouseInput;
	  }
	  return new (Type)(manager, inputHandler);
	}

	/**
	 * handle input events
	 * @param {Manager} manager
	 * @param {String} eventType
	 * @param {Object} input
	 */
	function inputHandler(manager, eventType, input) {
	  var pointersLen = input.pointers.length;
	  var changedPointersLen = input.changedPointers.length;
	  var isFirst = (eventType & INPUT_START && (pointersLen - changedPointersLen === 0));
	  var isFinal = (eventType & (INPUT_END | INPUT_CANCEL) && (pointersLen - changedPointersLen === 0));

	  input.isFirst = !!isFirst;
	  input.isFinal = !!isFinal;

	  if (isFirst) {
	    manager.session = {};
	  }

	  // source event is the normalized value of the domEvents
	  // like 'touchstart, mouseup, pointerdown'
	  input.eventType = eventType;

	  // compute scale, rotation etc
	  computeInputData(manager, input);

	  // emit secret event
	  manager.emit('hammer.input', input);

	  manager.recognize(input);
	  manager.session.prevInput = input;
	}

	/**
	 * extend the data with some usable properties like scale, rotate, velocity etc
	 * @param {Object} manager
	 * @param {Object} input
	 */
	function computeInputData(manager, input) {
	  var session = manager.session;
	  var pointers = input.pointers;
	  var pointersLength = pointers.length;

	  // store the first input to calculate the distance and direction
	  if (!session.firstInput) {
	    session.firstInput = simpleCloneInputData(input);
	  }

	  // to compute scale and rotation we need to store the multiple touches
	  if (pointersLength > 1 && !session.firstMultiple) {
	    session.firstMultiple = simpleCloneInputData(input);
	  } else if (pointersLength === 1) {
	    session.firstMultiple = false;
	  }

	  var firstInput = session.firstInput;
	  var firstMultiple = session.firstMultiple;
	  var offsetCenter = firstMultiple ? firstMultiple.center : firstInput.center;

	  var center = input.center = getCenter(pointers);
	  input.timeStamp = now();
	  input.deltaTime = input.timeStamp - firstInput.timeStamp;

	  input.angle = getAngle(offsetCenter, center);
	  input.distance = getDistance(offsetCenter, center);

	  computeDeltaXY(session, input);
	  input.offsetDirection = getDirection(input.deltaX, input.deltaY);

	  var overallVelocity = getVelocity(input.deltaTime, input.deltaX, input.deltaY);
	  input.overallVelocityX = overallVelocity.x;
	  input.overallVelocityY = overallVelocity.y;
	  input.overallVelocity = (abs(overallVelocity.x) > abs(overallVelocity.y)) ? overallVelocity.x : overallVelocity.y;

	  input.scale = firstMultiple ? getScale(firstMultiple.pointers, pointers) : 1;
	  input.rotation = firstMultiple ? getRotation(firstMultiple.pointers, pointers) : 0;

	  input.maxPointers = !session.prevInput ? input.pointers.length : ((input.pointers.length >
	  session.prevInput.maxPointers) ? input.pointers.length : session.prevInput.maxPointers);

	  computeIntervalInputData(session, input);

	  // find the correct target
	  var target = manager.element;
	  if (hasParent(input.srcEvent.target, target)) {
	    target = input.srcEvent.target;
	  }
	  input.target = target;
	}

	function computeDeltaXY(session, input) {
	  var center = input.center;
	  var offset = session.offsetDelta || {};
	  var prevDelta = session.prevDelta || {};
	  var prevInput = session.prevInput || {};

	  if (input.eventType === INPUT_START || prevInput.eventType === INPUT_END) {
	    prevDelta = session.prevDelta = {
	      x: prevInput.deltaX || 0,
	      y: prevInput.deltaY || 0
	    };

	    offset = session.offsetDelta = {
	      x: center.x,
	      y: center.y
	    };
	  }

	  input.deltaX = prevDelta.x + (center.x - offset.x);
	  input.deltaY = prevDelta.y + (center.y - offset.y);
	}

	/**
	 * velocity is calculated every x ms
	 * @param {Object} session
	 * @param {Object} input
	 */
	function computeIntervalInputData(session, input) {
	  var last = session.lastInterval || input,
	    deltaTime = input.timeStamp - last.timeStamp,
	    velocity, velocityX, velocityY, direction;

	  if (input.eventType != INPUT_CANCEL && (deltaTime > COMPUTE_INTERVAL || last.velocity === undefined)) {
	    var deltaX = input.deltaX - last.deltaX;
	    var deltaY = input.deltaY - last.deltaY;

	    var v = getVelocity(deltaTime, deltaX, deltaY);
	    velocityX = v.x;
	    velocityY = v.y;
	    velocity = (abs(v.x) > abs(v.y)) ? v.x : v.y;
	    direction = getDirection(deltaX, deltaY);

	    session.lastInterval = input;
	  } else {
	    // use latest velocity info if it doesn't overtake a minimum period
	    velocity = last.velocity;
	    velocityX = last.velocityX;
	    velocityY = last.velocityY;
	    direction = last.direction;
	  }

	  input.velocity = velocity;
	  input.velocityX = velocityX;
	  input.velocityY = velocityY;
	  input.direction = direction;
	}

	/**
	 * create a simple clone from the input used for storage of firstInput and firstMultiple
	 * @param {Object} input
	 * @returns {Object} clonedInputData
	 */
	function simpleCloneInputData(input) {
	  // make a simple copy of the pointers because we will get a reference if we don't
	  // we only need clientXY for the calculations
	  var pointers = [];
	  var i = 0;
	  while (i < input.pointers.length) {
	    pointers[i] = {
	      clientX: round(input.pointers[i].clientX),
	      clientY: round(input.pointers[i].clientY)
	    };
	    i++;
	  }

	  return {
	    timeStamp: now(),
	    pointers: pointers,
	    center: getCenter(pointers),
	    deltaX: input.deltaX,
	    deltaY: input.deltaY
	  };
	}

	/**
	 * get the center of all the pointers
	 * @param {Array} pointers
	 * @return {Object} center contains `x` and `y` properties
	 */
	function getCenter(pointers) {
	  var pointersLength = pointers.length;

	  // no need to loop when only one touch
	  if (pointersLength === 1) {
	    return {
	      x: round(pointers[0].clientX),
	      y: round(pointers[0].clientY)
	    };
	  }

	  var x = 0, y = 0, i = 0;
	  while (i < pointersLength) {
	    x += pointers[i].clientX;
	    y += pointers[i].clientY;
	    i++;
	  }

	  return {
	    x: round(x / pointersLength),
	    y: round(y / pointersLength)
	  };
	}

	/**
	 * calculate the velocity between two points. unit is in px per ms.
	 * @param {Number} deltaTime
	 * @param {Number} x
	 * @param {Number} y
	 * @return {Object} velocity `x` and `y`
	 */
	function getVelocity(deltaTime, x, y) {
	  return {
	    x: x / deltaTime || 0,
	    y: y / deltaTime || 0
	  };
	}

	/**
	 * get the direction between two points
	 * @param {Number} x
	 * @param {Number} y
	 * @return {Number} direction
	 */
	function getDirection(x, y) {
	  if (x === y) {
	    return DIRECTION_NONE;
	  }

	  if (abs(x) >= abs(y)) {
	    return x < 0 ? DIRECTION_LEFT : DIRECTION_RIGHT;
	  }
	  return y < 0 ? DIRECTION_UP : DIRECTION_DOWN;
	}

	/**
	 * calculate the absolute distance between two points
	 * @param {Object} p1 {x, y}
	 * @param {Object} p2 {x, y}
	 * @param {Array} [props] containing x and y keys
	 * @return {Number} distance
	 */
	function getDistance(p1, p2, props) {
	  if (!props) {
	    props = PROPS_XY;
	  }
	  var x = p2[props[0]] - p1[props[0]],
	    y = p2[props[1]] - p1[props[1]];

	  return Math.sqrt((x * x) + (y * y));
	}

	/**
	 * calculate the angle between two coordinates
	 * @param {Object} p1
	 * @param {Object} p2
	 * @param {Array} [props] containing x and y keys
	 * @return {Number} angle
	 */
	function getAngle(p1, p2, props) {
	  if (!props) {
	    props = PROPS_XY;
	  }
	  var x = p2[props[0]] - p1[props[0]],
	    y = p2[props[1]] - p1[props[1]];
	  return Math.atan2(y, x) * 180 / Math.PI;
	}

	/**
	 * calculate the rotation degrees between two pointersets
	 * @param {Array} start array of pointers
	 * @param {Array} end array of pointers
	 * @return {Number} rotation
	 */
	function getRotation(start, end) {
	  return getAngle(end[1], end[0], PROPS_CLIENT_XY) + getAngle(start[1], start[0], PROPS_CLIENT_XY);
	}

	/**
	 * calculate the scale factor between two pointersets
	 * no scale is 1, and goes down to 0 when pinched together, and bigger when pinched out
	 * @param {Array} start array of pointers
	 * @param {Array} end array of pointers
	 * @return {Number} scale
	 */
	function getScale(start, end) {
	  return getDistance(end[0], end[1], PROPS_CLIENT_XY) / getDistance(start[0], start[1], PROPS_CLIENT_XY);
	}

	var MOUSE_INPUT_MAP = {
	  mousedown: INPUT_START,
	  mousemove: INPUT_MOVE,
	  mouseup: INPUT_END
	};

	var MOUSE_ELEMENT_EVENTS = 'mousedown';
	var MOUSE_WINDOW_EVENTS = 'mousemove mouseup';

	/**
	 * Mouse events input
	 * @constructor
	 * @extends Input
	 */
	function MouseInput() {
	  this.evEl = MOUSE_ELEMENT_EVENTS;
	  this.evWin = MOUSE_WINDOW_EVENTS;

	  this.pressed = false; // mousedown state

	  Input.apply(this, arguments);
	}

	inherit(MouseInput, Input, {
	  /**
	   * handle mouse events
	   * @param {Object} ev
	   */
	  handler: function MEhandler(ev) {
	    var eventType = MOUSE_INPUT_MAP[ev.type];

	    // on start we want to have the left mouse button down
	    if (eventType & INPUT_START && ev.button === 0) {
	      this.pressed = true;
	    }

	    if (eventType & INPUT_MOVE && ev.which !== 1) {
	      eventType = INPUT_END;
	    }

	    // mouse must be down
	    if (!this.pressed) {
	      return;
	    }

	    if (eventType & INPUT_END) {
	      this.pressed = false;
	    }

	    this.callback(this.manager, eventType, {
	      pointers: [ev],
	      changedPointers: [ev],
	      pointerType: INPUT_TYPE_MOUSE,
	      srcEvent: ev
	    });
	  }
	});

	var POINTER_INPUT_MAP = {
	  pointerdown: INPUT_START,
	  pointermove: INPUT_MOVE,
	  pointerup: INPUT_END,
	  pointercancel: INPUT_CANCEL,
	  pointerout: INPUT_CANCEL
	};

	// in IE10 the pointer types is defined as an enum
	var IE10_POINTER_TYPE_ENUM = {
	  2: INPUT_TYPE_TOUCH,
	  3: INPUT_TYPE_PEN,
	  4: INPUT_TYPE_MOUSE,
	  5: INPUT_TYPE_KINECT // see https://twitter.com/jacobrossi/status/480596438489890816
	};

	var POINTER_ELEMENT_EVENTS = 'pointerdown';
	var POINTER_WINDOW_EVENTS = 'pointermove pointerup pointercancel';

	// IE10 has prefixed support, and case-sensitive
	if (window.MSPointerEvent && !window.PointerEvent) {
	  POINTER_ELEMENT_EVENTS = 'MSPointerDown';
	  POINTER_WINDOW_EVENTS = 'MSPointerMove MSPointerUp MSPointerCancel';
	}

	/**
	 * Pointer events input
	 * @constructor
	 * @extends Input
	 */
	function PointerEventInput() {
	  this.evEl = POINTER_ELEMENT_EVENTS;
	  this.evWin = POINTER_WINDOW_EVENTS;

	  Input.apply(this, arguments);

	  this.store = (this.manager.session.pointerEvents = []);
	}

	inherit(PointerEventInput, Input, {
	  /**
	   * handle mouse events
	   * @param {Object} ev
	   */
	  handler: function PEhandler(ev) {
	    var store = this.store;
	    var removePointer = false;

	    var eventTypeNormalized = ev.type.toLowerCase().replace('ms', '');
	    var eventType = POINTER_INPUT_MAP[eventTypeNormalized];
	    var pointerType = IE10_POINTER_TYPE_ENUM[ev.pointerType] || ev.pointerType;

	    var isTouch = (pointerType == INPUT_TYPE_TOUCH);

	    // get index of the event in the store
	    var storeIndex = inArray(store, ev.pointerId, 'pointerId');

	    // start and mouse must be down
	    if (eventType & INPUT_START && (ev.button === 0 || isTouch)) {
	      if (storeIndex < 0) {
	        store.push(ev);
	        storeIndex = store.length - 1;
	      }
	    } else if (eventType & (INPUT_END | INPUT_CANCEL)) {
	      removePointer = true;
	    }

	    // it not found, so the pointer hasn't been down (so it's probably a hover)
	    if (storeIndex < 0) {
	      return;
	    }

	    // update the event in the store
	    store[storeIndex] = ev;

	    this.callback(this.manager, eventType, {
	      pointers: store,
	      changedPointers: [ev],
	      pointerType: pointerType,
	      srcEvent: ev
	    });

	    if (removePointer) {
	      // remove from the store
	      store.splice(storeIndex, 1);
	    }
	  }
	});

	var SINGLE_TOUCH_INPUT_MAP = {
	  touchstart: INPUT_START,
	  touchmove: INPUT_MOVE,
	  touchend: INPUT_END,
	  touchcancel: INPUT_CANCEL
	};

	var SINGLE_TOUCH_TARGET_EVENTS = 'touchstart';
	var SINGLE_TOUCH_WINDOW_EVENTS = 'touchstart touchmove touchend touchcancel';

	/**
	 * Touch events input
	 * @constructor
	 * @extends Input
	 */
	function SingleTouchInput() {
	  this.evTarget = SINGLE_TOUCH_TARGET_EVENTS;
	  this.evWin = SINGLE_TOUCH_WINDOW_EVENTS;
	  this.started = false;

	  Input.apply(this, arguments);
	}

	inherit(SingleTouchInput, Input, {
	  handler: function TEhandler(ev) {
	    var type = SINGLE_TOUCH_INPUT_MAP[ev.type];

	    // should we handle the touch events?
	    if (type === INPUT_START) {
	      this.started = true;
	    }

	    if (!this.started) {
	      return;
	    }

	    var touches = normalizeSingleTouches.call(this, ev, type);

	    // when done, reset the started state
	    if (type & (INPUT_END | INPUT_CANCEL) && touches[0].length - touches[1].length === 0) {
	      this.started = false;
	    }

	    this.callback(this.manager, type, {
	      pointers: touches[0],
	      changedPointers: touches[1],
	      pointerType: INPUT_TYPE_TOUCH,
	      srcEvent: ev
	    });
	  }
	});

	/**
	 * @this {TouchInput}
	 * @param {Object} ev
	 * @param {Number} type flag
	 * @returns {undefined|Array} [all, changed]
	 */
	function normalizeSingleTouches(ev, type) {
	  var all = toArray(ev.touches);
	  var changed = toArray(ev.changedTouches);

	  if (type & (INPUT_END | INPUT_CANCEL)) {
	    all = uniqueArray(all.concat(changed), 'identifier', true);
	  }

	  return [all, changed];
	}

	var TOUCH_INPUT_MAP = {
	  touchstart: INPUT_START,
	  touchmove: INPUT_MOVE,
	  touchend: INPUT_END,
	  touchcancel: INPUT_CANCEL
	};

	var TOUCH_TARGET_EVENTS = 'touchstart touchmove touchend touchcancel';

	/**
	 * Multi-user touch events input
	 * @constructor
	 * @extends Input
	 */
	function TouchInput() {
	  this.evTarget = TOUCH_TARGET_EVENTS;
	  this.targetIds = {};

	  Input.apply(this, arguments);
	}

	inherit(TouchInput, Input, {
	  handler: function MTEhandler(ev) {
	    var type = TOUCH_INPUT_MAP[ev.type];
	    var touches = getTouches.call(this, ev, type);
	    if (!touches) {
	      return;
	    }

	    this.callback(this.manager, type, {
	      pointers: touches[0],
	      changedPointers: touches[1],
	      pointerType: INPUT_TYPE_TOUCH,
	      srcEvent: ev
	    });
	  }
	});

	/**
	 * @this {TouchInput}
	 * @param {Object} ev
	 * @param {Number} type flag
	 * @returns {undefined|Array} [all, changed]
	 */
	function getTouches(ev, type) {
	  var allTouches = toArray(ev.touches);
	  var targetIds = this.targetIds;

	  // when there is only one touch, the process can be simplified
	  if (type & (INPUT_START | INPUT_MOVE) && allTouches.length === 1) {
	    targetIds[allTouches[0].identifier] = true;
	    return [allTouches, allTouches];
	  }

	  var i,
	    targetTouches,
	    changedTouches = toArray(ev.changedTouches),
	    changedTargetTouches = [],
	    target = this.target;

	  // get target touches from touches
	  targetTouches = allTouches.filter(function(touch) {
	    return hasParent(touch.target, target);
	  });

	  // collect touches
	  if (type === INPUT_START) {
	    i = 0;
	    while (i < targetTouches.length) {
	      targetIds[targetTouches[i].identifier] = true;
	      i++;
	    }
	  }

	  // filter changed touches to only contain touches that exist in the collected target ids
	  i = 0;
	  while (i < changedTouches.length) {
	    if (targetIds[changedTouches[i].identifier]) {
	      changedTargetTouches.push(changedTouches[i]);
	    }

	    // cleanup removed touches
	    if (type & (INPUT_END | INPUT_CANCEL)) {
	      delete targetIds[changedTouches[i].identifier];
	    }
	    i++;
	  }

	  if (!changedTargetTouches.length) {
	    return;
	  }

	  return [
	    // merge targetTouches with changedTargetTouches so it contains ALL touches, including 'end' and 'cancel'
	    uniqueArray(targetTouches.concat(changedTargetTouches), 'identifier', true),
	    changedTargetTouches
	  ];
	}

	/**
	 * Combined touch and mouse input
	 *
	 * Touch has a higher priority then mouse, and while touching no mouse events are allowed.
	 * This because touch devices also emit mouse events while doing a touch.
	 *
	 * @constructor
	 * @extends Input
	 */

	var DEDUP_TIMEOUT = 2500;
	var DEDUP_DISTANCE = 25;

	function TouchMouseInput() {
	  Input.apply(this, arguments);

	  var handler = bindFn(this.handler, this);
	  this.touch = new TouchInput(this.manager, handler);
	  this.mouse = new MouseInput(this.manager, handler);

	  this.primaryTouch = null;
	  this.lastTouches = [];
	}

	inherit(TouchMouseInput, Input, {
	  /**
	   * handle mouse and touch events
	   * @param {Hammer} manager
	   * @param {String} inputEvent
	   * @param {Object} inputData
	   */
	  handler: function TMEhandler(manager, inputEvent, inputData) {
	    var isTouch = (inputData.pointerType == INPUT_TYPE_TOUCH),
	      isMouse = (inputData.pointerType == INPUT_TYPE_MOUSE);

	    if (isMouse && inputData.sourceCapabilities && inputData.sourceCapabilities.firesTouchEvents) {
	      return;
	    }

	    // when we're in a touch event, record touches to  de-dupe synthetic mouse event
	    if (isTouch) {
	      recordTouches.call(this, inputEvent, inputData);
	    } else if (isMouse && isSyntheticEvent.call(this, inputData)) {
	      return;
	    }

	    this.callback(manager, inputEvent, inputData);
	  },

	  /**
	   * remove the event listeners
	   */
	  destroy: function destroy() {
	    this.touch.destroy();
	    this.mouse.destroy();
	  }
	});

	function recordTouches(eventType, eventData) {
	  if (eventType & INPUT_START) {
	    this.primaryTouch = eventData.changedPointers[0].identifier;
	    setLastTouch.call(this, eventData);
	  } else if (eventType & (INPUT_END | INPUT_CANCEL)) {
	    setLastTouch.call(this, eventData);
	  }
	}

	function setLastTouch(eventData) {
	  var touch = eventData.changedPointers[0];

	  if (touch.identifier === this.primaryTouch) {
	    var lastTouch = {x: touch.clientX, y: touch.clientY};
	    this.lastTouches.push(lastTouch);
	    var lts = this.lastTouches;
	    var removeLastTouch = function() {
	      var i = lts.indexOf(lastTouch);
	      if (i > -1) {
	        lts.splice(i, 1);
	      }
	    };
	    setTimeout(removeLastTouch, DEDUP_TIMEOUT);
	  }
	}

	function isSyntheticEvent(eventData) {
	  var x = eventData.srcEvent.clientX, y = eventData.srcEvent.clientY;
	  for (var i = 0; i < this.lastTouches.length; i++) {
	    var t = this.lastTouches[i];
	    var dx = Math.abs(x - t.x), dy = Math.abs(y - t.y);
	    if (dx <= DEDUP_DISTANCE && dy <= DEDUP_DISTANCE) {
	      return true;
	    }
	  }
	  return false;
	}

	var PREFIXED_TOUCH_ACTION = prefixed(TEST_ELEMENT.style, 'touchAction');
	var NATIVE_TOUCH_ACTION = PREFIXED_TOUCH_ACTION !== undefined;

	// magical touchAction value
	var TOUCH_ACTION_COMPUTE = 'compute';
	var TOUCH_ACTION_AUTO = 'auto';
	var TOUCH_ACTION_MANIPULATION = 'manipulation'; // not implemented
	var TOUCH_ACTION_NONE = 'none';
	var TOUCH_ACTION_PAN_X = 'pan-x';
	var TOUCH_ACTION_PAN_Y = 'pan-y';
	var TOUCH_ACTION_MAP = getTouchActionProps();

	/**
	 * Touch Action
	 * sets the touchAction property or uses the js alternative
	 * @param {Manager} manager
	 * @param {String} value
	 * @constructor
	 */
	function TouchAction(manager, value) {
	  this.manager = manager;
	  this.set(value);
	}

	TouchAction.prototype = {
	  /**
	   * set the touchAction value on the element or enable the polyfill
	   * @param {String} value
	   */
	  set: function(value) {
	    // find out the touch-action by the event handlers
	    if (value == TOUCH_ACTION_COMPUTE) {
	      value = this.compute();
	    }

	    if (NATIVE_TOUCH_ACTION && this.manager.element.style && TOUCH_ACTION_MAP[value]) {
	      this.manager.element.style[PREFIXED_TOUCH_ACTION] = value;
	    }
	    this.actions = value.toLowerCase().trim();
	  },

	  /**
	   * just re-set the touchAction value
	   */
	  update: function() {
	    this.set(this.manager.options.touchAction);
	  },

	  /**
	   * compute the value for the touchAction property based on the recognizer's settings
	   * @returns {String} value
	   */
	  compute: function() {
	    var actions = [];
	    each(this.manager.recognizers, function(recognizer) {
	      if (boolOrFn(recognizer.options.enable, [recognizer])) {
	        actions = actions.concat(recognizer.getTouchAction());
	      }
	    });
	    return cleanTouchActions(actions.join(' '));
	  },

	  /**
	   * this method is called on each input cycle and provides the preventing of the browser behavior
	   * @param {Object} input
	   */
	  preventDefaults: function(input) {
	    var srcEvent = input.srcEvent;
	    var direction = input.offsetDirection;

	    // if the touch action did prevented once this session
	    if (this.manager.session.prevented) {
	      srcEvent.preventDefault();
	      return;
	    }

	    var actions = this.actions;
	    var hasNone = inStr(actions, TOUCH_ACTION_NONE) && !TOUCH_ACTION_MAP[TOUCH_ACTION_NONE];
	    var hasPanY = inStr(actions, TOUCH_ACTION_PAN_Y) && !TOUCH_ACTION_MAP[TOUCH_ACTION_PAN_Y];
	    var hasPanX = inStr(actions, TOUCH_ACTION_PAN_X) && !TOUCH_ACTION_MAP[TOUCH_ACTION_PAN_X];

	    if (hasNone) {
	      //do not prevent defaults if this is a tap gesture

	      var isTapPointer = input.pointers.length === 1;
	      var isTapMovement = input.distance < 2;
	      var isTapTouchTime = input.deltaTime < 250;

	      if (isTapPointer && isTapMovement && isTapTouchTime) {
	        return;
	      }
	    }

	    if (hasPanX && hasPanY) {
	      // `pan-x pan-y` means browser handles all scrolling/panning, do not prevent
	      return;
	    }

	    if (hasNone ||
	      (hasPanY && direction & DIRECTION_HORIZONTAL) ||
	      (hasPanX && direction & DIRECTION_VERTICAL)) {
	      return this.preventSrc(srcEvent);
	    }
	  },

	  /**
	   * call preventDefault to prevent the browser's default behavior (scrolling in most cases)
	   * @param {Object} srcEvent
	   */
	  preventSrc: function(srcEvent) {
	    this.manager.session.prevented = true;
	    srcEvent.preventDefault();
	  }
	};

	/**
	 * when the touchActions are collected they are not a valid value, so we need to clean things up. *
	 * @param {String} actions
	 * @returns {*}
	 */
	function cleanTouchActions(actions) {
	  // none
	  if (inStr(actions, TOUCH_ACTION_NONE)) {
	    return TOUCH_ACTION_NONE;
	  }

	  var hasPanX = inStr(actions, TOUCH_ACTION_PAN_X);
	  var hasPanY = inStr(actions, TOUCH_ACTION_PAN_Y);

	  // if both pan-x and pan-y are set (different recognizers
	  // for different directions, e.g. horizontal pan but vertical swipe?)
	  // we need none (as otherwise with pan-x pan-y combined none of these
	  // recognizers will work, since the browser would handle all panning
	  if (hasPanX && hasPanY) {
	    return TOUCH_ACTION_NONE;
	  }

	  // pan-x OR pan-y
	  if (hasPanX || hasPanY) {
	    return hasPanX ? TOUCH_ACTION_PAN_X : TOUCH_ACTION_PAN_Y;
	  }

	  // manipulation
	  if (inStr(actions, TOUCH_ACTION_MANIPULATION)) {
	    return TOUCH_ACTION_MANIPULATION;
	  }

	  return TOUCH_ACTION_AUTO;
	}

	function getTouchActionProps() {
	  if (!NATIVE_TOUCH_ACTION) {
	    return false;
	  }
	  var touchMap = {};
	  var cssSupports = window.CSS && window.CSS.supports;
	  ['auto', 'manipulation', 'pan-y', 'pan-x', 'pan-x pan-y', 'none'].forEach(function(val) {

	    // If css.supports is not supported but there is native touch-action assume it supports
	    // all values. This is the case for IE 10 and 11.
	    touchMap[val] = cssSupports ? window.CSS.supports('touch-action', val) : true;
	  });
	  return touchMap;
	}

	/**
	 * Recognizer flow explained; *
	 * All recognizers have the initial state of POSSIBLE when a input session starts.
	 * The definition of a input session is from the first input until the last input, with all it's movement in it. *
	 * Example session for mouse-input: mousedown -> mousemove -> mouseup
	 *
	 * On each recognizing cycle (see Manager.recognize) the .recognize() method is executed
	 * which determines with state it should be.
	 *
	 * If the recognizer has the state FAILED, CANCELLED or RECOGNIZED (equals ENDED), it is reset to
	 * POSSIBLE to give it another change on the next cycle.
	 *
	 *               Possible
	 *                  |
	 *            +-----+---------------+
	 *            |                     |
	 *      +-----+-----+               |
	 *      |           |               |
	 *   Failed      Cancelled          |
	 *                          +-------+------+
	 *                          |              |
	 *                      Recognized       Began
	 *                                         |
	 *                                      Changed
	 *                                         |
	 *                                  Ended/Recognized
	 */
	var STATE_POSSIBLE = 1;
	var STATE_BEGAN = 2;
	var STATE_CHANGED = 4;
	var STATE_ENDED = 8;
	var STATE_RECOGNIZED = STATE_ENDED;
	var STATE_CANCELLED = 16;
	var STATE_FAILED = 32;

	/**
	 * Recognizer
	 * Every recognizer needs to extend from this class.
	 * @constructor
	 * @param {Object} options
	 */
	function Recognizer(options) {
	  this.options = assign({}, this.defaults, options || {});

	  this.id = uniqueId();

	  this.manager = null;

	  // default is enable true
	  this.options.enable = ifUndefined(this.options.enable, true);

	  this.state = STATE_POSSIBLE;

	  this.simultaneous = {};
	  this.requireFail = [];
	}

	Recognizer.prototype = {
	  /**
	   * @virtual
	   * @type {Object}
	   */
	  defaults: {},

	  /**
	   * set options
	   * @param {Object} options
	   * @return {Recognizer}
	   */
	  set: function(options) {
	    assign(this.options, options);

	    // also update the touchAction, in case something changed about the directions/enabled state
	    this.manager && this.manager.touchAction.update();
	    return this;
	  },

	  /**
	   * recognize simultaneous with an other recognizer.
	   * @param {Recognizer} otherRecognizer
	   * @returns {Recognizer} this
	   */
	  recognizeWith: function(otherRecognizer) {
	    if (invokeArrayArg(otherRecognizer, 'recognizeWith', this)) {
	      return this;
	    }

	    var simultaneous = this.simultaneous;
	    otherRecognizer = getRecognizerByNameIfManager(otherRecognizer, this);
	    if (!simultaneous[otherRecognizer.id]) {
	      simultaneous[otherRecognizer.id] = otherRecognizer;
	      otherRecognizer.recognizeWith(this);
	    }
	    return this;
	  },

	  /**
	   * drop the simultaneous link. it doesnt remove the link on the other recognizer.
	   * @param {Recognizer} otherRecognizer
	   * @returns {Recognizer} this
	   */
	  dropRecognizeWith: function(otherRecognizer) {
	    if (invokeArrayArg(otherRecognizer, 'dropRecognizeWith', this)) {
	      return this;
	    }

	    otherRecognizer = getRecognizerByNameIfManager(otherRecognizer, this);
	    delete this.simultaneous[otherRecognizer.id];
	    return this;
	  },

	  /**
	   * recognizer can only run when an other is failing
	   * @param {Recognizer} otherRecognizer
	   * @returns {Recognizer} this
	   */
	  requireFailure: function(otherRecognizer) {
	    if (invokeArrayArg(otherRecognizer, 'requireFailure', this)) {
	      return this;
	    }

	    var requireFail = this.requireFail;
	    otherRecognizer = getRecognizerByNameIfManager(otherRecognizer, this);
	    if (inArray(requireFail, otherRecognizer) === -1) {
	      requireFail.push(otherRecognizer);
	      otherRecognizer.requireFailure(this);
	    }
	    return this;
	  },

	  /**
	   * drop the requireFailure link. it does not remove the link on the other recognizer.
	   * @param {Recognizer} otherRecognizer
	   * @returns {Recognizer} this
	   */
	  dropRequireFailure: function(otherRecognizer) {
	    if (invokeArrayArg(otherRecognizer, 'dropRequireFailure', this)) {
	      return this;
	    }

	    otherRecognizer = getRecognizerByNameIfManager(otherRecognizer, this);
	    var index = inArray(this.requireFail, otherRecognizer);
	    if (index > -1) {
	      this.requireFail.splice(index, 1);
	    }
	    return this;
	  },

	  /**
	   * has require failures boolean
	   * @returns {boolean}
	   */
	  hasRequireFailures: function() {
	    return this.requireFail.length > 0;
	  },

	  /**
	   * if the recognizer can recognize simultaneous with an other recognizer
	   * @param {Recognizer} otherRecognizer
	   * @returns {Boolean}
	   */
	  canRecognizeWith: function(otherRecognizer) {
	    return !!this.simultaneous[otherRecognizer.id];
	  },

	  /**
	   * You should use `tryEmit` instead of `emit` directly to check
	   * that all the needed recognizers has failed before emitting.
	   * @param {Object} input
	   */
	  emit: function(input) {
	    var self = this;
	    var state = this.state;

	    function emit(event) {
	      self.manager.emit(event, input);
	    }

	    // 'panstart' and 'panmove'
	    if (state < STATE_ENDED) {
	      emit(self.options.event + stateStr(state));
	    }

	    emit(self.options.event); // simple 'eventName' events

	    if (input.additionalEvent) { // additional event(panleft, panright, pinchin, pinchout...)
	      emit(input.additionalEvent);
	    }

	    // panend and pancancel
	    if (state >= STATE_ENDED) {
	      emit(self.options.event + stateStr(state));
	    }
	  },

	  /**
	   * Check that all the require failure recognizers has failed,
	   * if true, it emits a gesture event,
	   * otherwise, setup the state to FAILED.
	   * @param {Object} input
	   */
	  tryEmit: function(input) {
	    if (this.canEmit()) {
	      return this.emit(input);
	    }
	    // it's failing anyway
	    this.state = STATE_FAILED;
	  },

	  /**
	   * can we emit?
	   * @returns {boolean}
	   */
	  canEmit: function() {
	    var i = 0;
	    while (i < this.requireFail.length) {
	      if (!(this.requireFail[i].state & (STATE_FAILED | STATE_POSSIBLE))) {
	        return false;
	      }
	      i++;
	    }
	    return true;
	  },

	  /**
	   * update the recognizer
	   * @param {Object} inputData
	   */
	  recognize: function(inputData) {
	    // make a new copy of the inputData
	    // so we can change the inputData without messing up the other recognizers
	    var inputDataClone = assign({}, inputData);

	    // is is enabled and allow recognizing?
	    if (!boolOrFn(this.options.enable, [this, inputDataClone])) {
	      this.reset();
	      this.state = STATE_FAILED;
	      return;
	    }

	    // reset when we've reached the end
	    if (this.state & (STATE_RECOGNIZED | STATE_CANCELLED | STATE_FAILED)) {
	      this.state = STATE_POSSIBLE;
	    }

	    this.state = this.process(inputDataClone);

	    // the recognizer has recognized a gesture
	    // so trigger an event
	    if (this.state & (STATE_BEGAN | STATE_CHANGED | STATE_ENDED | STATE_CANCELLED)) {
	      this.tryEmit(inputDataClone);
	    }
	  },

	  /**
	   * return the state of the recognizer
	   * the actual recognizing happens in this method
	   * @virtual
	   * @param {Object} inputData
	   * @returns {Const} STATE
	   */
	  process: function(inputData) { }, // jshint ignore:line

	  /**
	   * return the preferred touch-action
	   * @virtual
	   * @returns {Array}
	   */
	  getTouchAction: function() { },

	  /**
	   * called when the gesture isn't allowed to recognize
	   * like when another is being recognized or it is disabled
	   * @virtual
	   */
	  reset: function() { }
	};

	/**
	 * get a usable string, used as event postfix
	 * @param {Const} state
	 * @returns {String} state
	 */
	function stateStr(state) {
	  if (state & STATE_CANCELLED) {
	    return 'cancel';
	  } else if (state & STATE_ENDED) {
	    return 'end';
	  } else if (state & STATE_CHANGED) {
	    return 'move';
	  } else if (state & STATE_BEGAN) {
	    return 'start';
	  }
	  return '';
	}

	/**
	 * direction cons to string
	 * @param {Const} direction
	 * @returns {String}
	 */
	function directionStr(direction) {
	  if (direction == DIRECTION_DOWN) {
	    return 'down';
	  } else if (direction == DIRECTION_UP) {
	    return 'up';
	  } else if (direction == DIRECTION_LEFT) {
	    return 'left';
	  } else if (direction == DIRECTION_RIGHT) {
	    return 'right';
	  }
	  return '';
	}

	/**
	 * get a recognizer by name if it is bound to a manager
	 * @param {Recognizer|String} otherRecognizer
	 * @param {Recognizer} recognizer
	 * @returns {Recognizer}
	 */
	function getRecognizerByNameIfManager(otherRecognizer, recognizer) {
	  var manager = recognizer.manager;
	  if (manager) {
	    return manager.get(otherRecognizer);
	  }
	  return otherRecognizer;
	}

	/**
	 * This recognizer is just used as a base for the simple attribute recognizers.
	 * @constructor
	 * @extends Recognizer
	 */
	function AttrRecognizer() {
	  Recognizer.apply(this, arguments);
	}

	inherit(AttrRecognizer, Recognizer, {
	  /**
	   * @namespace
	   * @memberof AttrRecognizer
	   */
	  defaults: {
	    /**
	     * @type {Number}
	     * @default 1
	     */
	    pointers: 1
	  },

	  /**
	   * Used to check if it the recognizer receives valid input, like input.distance > 10.
	   * @memberof AttrRecognizer
	   * @param {Object} input
	   * @returns {Boolean} recognized
	   */
	  attrTest: function(input) {
	    var optionPointers = this.options.pointers;
	    return optionPointers === 0 || input.pointers.length === optionPointers;
	  },

	  /**
	   * Process the input and return the state for the recognizer
	   * @memberof AttrRecognizer
	   * @param {Object} input
	   * @returns {*} State
	   */
	  process: function(input) {
	    var state = this.state;
	    var eventType = input.eventType;

	    var isRecognized = state & (STATE_BEGAN | STATE_CHANGED);
	    var isValid = this.attrTest(input);

	    // on cancel input and we've recognized before, return STATE_CANCELLED
	    if (isRecognized && (eventType & INPUT_CANCEL || !isValid)) {
	      return state | STATE_CANCELLED;
	    } else if (isRecognized || isValid) {
	      if (eventType & INPUT_END) {
	        return state | STATE_ENDED;
	      } else if (!(state & STATE_BEGAN)) {
	        return STATE_BEGAN;
	      }
	      return state | STATE_CHANGED;
	    }
	    return STATE_FAILED;
	  }
	});

	/**
	 * Pan
	 * Recognized when the pointer is down and moved in the allowed direction.
	 * @constructor
	 * @extends AttrRecognizer
	 */
	function PanRecognizer() {
	  AttrRecognizer.apply(this, arguments);

	  this.pX = null;
	  this.pY = null;
	}

	inherit(PanRecognizer, AttrRecognizer, {
	  /**
	   * @namespace
	   * @memberof PanRecognizer
	   */
	  defaults: {
	    event: 'pan',
	    threshold: 10,
	    pointers: 1,
	    direction: DIRECTION_ALL
	  },

	  getTouchAction: function() {
	    var direction = this.options.direction;
	    var actions = [];
	    if (direction & DIRECTION_HORIZONTAL) {
	      actions.push(TOUCH_ACTION_PAN_Y);
	    }
	    if (direction & DIRECTION_VERTICAL) {
	      actions.push(TOUCH_ACTION_PAN_X);
	    }
	    return actions;
	  },

	  directionTest: function(input) {
	    var options = this.options;
	    var hasMoved = true;
	    var distance = input.distance;
	    var direction = input.direction;
	    var x = input.deltaX;
	    var y = input.deltaY;

	    // lock to axis?
	    if (!(direction & options.direction)) {
	      if (options.direction & DIRECTION_HORIZONTAL) {
	        direction = (x === 0) ? DIRECTION_NONE : (x < 0) ? DIRECTION_LEFT : DIRECTION_RIGHT;
	        hasMoved = x != this.pX;
	        distance = Math.abs(input.deltaX);
	      } else {
	        direction = (y === 0) ? DIRECTION_NONE : (y < 0) ? DIRECTION_UP : DIRECTION_DOWN;
	        hasMoved = y != this.pY;
	        distance = Math.abs(input.deltaY);
	      }
	    }
	    input.direction = direction;
	    return hasMoved && distance > options.threshold && direction & options.direction;
	  },

	  attrTest: function(input) {
	    return AttrRecognizer.prototype.attrTest.call(this, input) &&
	      (this.state & STATE_BEGAN || (!(this.state & STATE_BEGAN) && this.directionTest(input)));
	  },

	  emit: function(input) {

	    this.pX = input.deltaX;
	    this.pY = input.deltaY;

	    var direction = directionStr(input.direction);

	    if (direction) {
	      input.additionalEvent = this.options.event + direction;
	    }
	    this._super.emit.call(this, input);
	  }
	});

	/**
	 * Pinch
	 * Recognized when two or more pointers are moving toward (zoom-in) or away from each other (zoom-out).
	 * @constructor
	 * @extends AttrRecognizer
	 */
	function PinchRecognizer() {
	  AttrRecognizer.apply(this, arguments);
	}

	inherit(PinchRecognizer, AttrRecognizer, {
	  /**
	   * @namespace
	   * @memberof PinchRecognizer
	   */
	  defaults: {
	    event: 'pinch',
	    threshold: 0,
	    pointers: 2
	  },

	  getTouchAction: function() {
	    return [TOUCH_ACTION_NONE];
	  },

	  attrTest: function(input) {
	    return this._super.attrTest.call(this, input) &&
	      (Math.abs(input.scale - 1) > this.options.threshold || this.state & STATE_BEGAN);
	  },

	  emit: function(input) {
	    if (input.scale !== 1) {
	      var inOut = input.scale < 1 ? 'in' : 'out';
	      input.additionalEvent = this.options.event + inOut;
	    }
	    this._super.emit.call(this, input);
	  }
	});

	/**
	 * Press
	 * Recognized when the pointer is down for x ms without any movement.
	 * @constructor
	 * @extends Recognizer
	 */
	function PressRecognizer() {
	  Recognizer.apply(this, arguments);

	  this._timer = null;
	  this._input = null;
	}

	inherit(PressRecognizer, Recognizer, {
	  /**
	   * @namespace
	   * @memberof PressRecognizer
	   */
	  defaults: {
	    event: 'press',
	    pointers: 1,
	    time: 251, // minimal time of the pointer to be pressed
	    threshold: 9 // a minimal movement is ok, but keep it low
	  },

	  getTouchAction: function() {
	    return [TOUCH_ACTION_AUTO];
	  },

	  process: function(input) {
	    var options = this.options;
	    var validPointers = input.pointers.length === options.pointers;
	    var validMovement = input.distance < options.threshold;
	    var validTime = input.deltaTime > options.time;

	    this._input = input;

	    // we only allow little movement
	    // and we've reached an end event, so a tap is possible
	    if (!validMovement || !validPointers || (input.eventType & (INPUT_END | INPUT_CANCEL) && !validTime)) {
	      this.reset();
	    } else if (input.eventType & INPUT_START) {
	      this.reset();
	      this._timer = setTimeoutContext(function() {
	        this.state = STATE_RECOGNIZED;
	        this.tryEmit();
	      }, options.time, this);
	    } else if (input.eventType & INPUT_END) {
	      return STATE_RECOGNIZED;
	    }
	    return STATE_FAILED;
	  },

	  reset: function() {
	    clearTimeout(this._timer);
	  },

	  emit: function(input) {
	    if (this.state !== STATE_RECOGNIZED) {
	      return;
	    }

	    if (input && (input.eventType & INPUT_END)) {
	      this.manager.emit(this.options.event + 'up', input);
	    } else {
	      this._input.timeStamp = now();
	      this.manager.emit(this.options.event, this._input);
	    }
	  }
	});

	/**
	 * Rotate
	 * Recognized when two or more pointer are moving in a circular motion.
	 * @constructor
	 * @extends AttrRecognizer
	 */
	function RotateRecognizer() {
	  AttrRecognizer.apply(this, arguments);
	}

	inherit(RotateRecognizer, AttrRecognizer, {
	  /**
	   * @namespace
	   * @memberof RotateRecognizer
	   */
	  defaults: {
	    event: 'rotate',
	    threshold: 0,
	    pointers: 2
	  },

	  getTouchAction: function() {
	    return [TOUCH_ACTION_NONE];
	  },

	  attrTest: function(input) {
	    return this._super.attrTest.call(this, input) &&
	      (Math.abs(input.rotation) > this.options.threshold || this.state & STATE_BEGAN);
	  }
	});

	/**
	 * Swipe
	 * Recognized when the pointer is moving fast (velocity), with enough distance in the allowed direction.
	 * @constructor
	 * @extends AttrRecognizer
	 */
	function SwipeRecognizer() {
	  AttrRecognizer.apply(this, arguments);
	}

	inherit(SwipeRecognizer, AttrRecognizer, {
	  /**
	   * @namespace
	   * @memberof SwipeRecognizer
	   */
	  defaults: {
	    event: 'swipe',
	    threshold: 10,
	    velocity: 0.3,
	    direction: DIRECTION_HORIZONTAL | DIRECTION_VERTICAL,
	    pointers: 1
	  },

	  getTouchAction: function() {
	    return PanRecognizer.prototype.getTouchAction.call(this);
	  },

	  attrTest: function(input) {
	    var direction = this.options.direction;
	    var velocity;

	    if (direction & (DIRECTION_HORIZONTAL | DIRECTION_VERTICAL)) {
	      velocity = input.overallVelocity;
	    } else if (direction & DIRECTION_HORIZONTAL) {
	      velocity = input.overallVelocityX;
	    } else if (direction & DIRECTION_VERTICAL) {
	      velocity = input.overallVelocityY;
	    }

	    return this._super.attrTest.call(this, input) &&
	      direction & input.offsetDirection &&
	      input.distance > this.options.threshold &&
	      input.maxPointers == this.options.pointers &&
	      abs(velocity) > this.options.velocity && input.eventType & INPUT_END;
	  },

	  emit: function(input) {
	    var direction = directionStr(input.offsetDirection);
	    if (direction) {
	      this.manager.emit(this.options.event + direction, input);
	    }

	    this.manager.emit(this.options.event, input);
	  }
	});

	/**
	 * A tap is ecognized when the pointer is doing a small tap/click. Multiple taps are recognized if they occur
	 * between the given interval and position. The delay option can be used to recognize multi-taps without firing
	 * a single tap.
	 *
	 * The eventData from the emitted event contains the property `tapCount`, which contains the amount of
	 * multi-taps being recognized.
	 * @constructor
	 * @extends Recognizer
	 */
	function TapRecognizer() {
	  Recognizer.apply(this, arguments);

	  // previous time and center,
	  // used for tap counting
	  this.pTime = false;
	  this.pCenter = false;

	  this._timer = null;
	  this._input = null;
	  this.count = 0;
	}

	inherit(TapRecognizer, Recognizer, {
	  /**
	   * @namespace
	   * @memberof PinchRecognizer
	   */
	  defaults: {
	    event: 'tap',
	    pointers: 1,
	    taps: 1,
	    interval: 300, // max time between the multi-tap taps
	    time: 250, // max time of the pointer to be down (like finger on the screen)
	    threshold: 9, // a minimal movement is ok, but keep it low
	    posThreshold: 10 // a multi-tap can be a bit off the initial position
	  },

	  getTouchAction: function() {
	    return [TOUCH_ACTION_MANIPULATION];
	  },

	  process: function(input) {
	    var options = this.options;

	    var validPointers = input.pointers.length === options.pointers;
	    var validMovement = input.distance < options.threshold;
	    var validTouchTime = input.deltaTime < options.time;

	    this.reset();

	    if ((input.eventType & INPUT_START) && (this.count === 0)) {
	      return this.failTimeout();
	    }

	    // we only allow little movement
	    // and we've reached an end event, so a tap is possible
	    if (validMovement && validTouchTime && validPointers) {
	      if (input.eventType != INPUT_END) {
	        return this.failTimeout();
	      }

	      var validInterval = this.pTime ? (input.timeStamp - this.pTime < options.interval) : true;
	      var validMultiTap = !this.pCenter || getDistance(this.pCenter, input.center) < options.posThreshold;

	      this.pTime = input.timeStamp;
	      this.pCenter = input.center;

	      if (!validMultiTap || !validInterval) {
	        this.count = 1;
	      } else {
	        this.count += 1;
	      }

	      this._input = input;

	      // if tap count matches we have recognized it,
	      // else it has began recognizing...
	      var tapCount = this.count % options.taps;
	      if (tapCount === 0) {
	        // no failing requirements, immediately trigger the tap event
	        // or wait as long as the multitap interval to trigger
	        if (!this.hasRequireFailures()) {
	          return STATE_RECOGNIZED;
	        } else {
	          this._timer = setTimeoutContext(function() {
	            this.state = STATE_RECOGNIZED;
	            this.tryEmit();
	          }, options.interval, this);
	          return STATE_BEGAN;
	        }
	      }
	    }
	    return STATE_FAILED;
	  },

	  failTimeout: function() {
	    this._timer = setTimeoutContext(function() {
	      this.state = STATE_FAILED;
	    }, this.options.interval, this);
	    return STATE_FAILED;
	  },

	  reset: function() {
	    clearTimeout(this._timer);
	  },

	  emit: function() {
	    if (this.state == STATE_RECOGNIZED) {
	      this._input.tapCount = this.count;
	      this.manager.emit(this.options.event, this._input);
	    }
	  }
	});

	/**
	 * Simple way to create a manager with a default set of recognizers.
	 * @param {HTMLElement} element
	 * @param {Object} [options]
	 * @constructor
	 */
	function Hammer(element, options) {
	  options = options || {};
	  options.recognizers = ifUndefined(options.recognizers, Hammer.defaults.preset);
	  return new Manager(element, options);
	}

	/**
	 * @const {string}
	 */
	Hammer.VERSION = '2.0.7';

	/**
	 * default settings
	 * @namespace
	 */
	Hammer.defaults = {
	  /**
	   * set if DOM events are being triggered.
	   * But this is slower and unused by simple implementations, so disabled by default.
	   * @type {Boolean}
	   * @default false
	   */
	  domEvents: false,

	  /**
	   * The value for the touchAction property/fallback.
	   * When set to `compute` it will magically set the correct value based on the added recognizers.
	   * @type {String}
	   * @default compute
	   */
	  touchAction: TOUCH_ACTION_COMPUTE,

	  /**
	   * @type {Boolean}
	   * @default true
	   */
	  enable: true,

	  /**
	   * EXPERIMENTAL FEATURE -- can be removed/changed
	   * Change the parent input target element.
	   * If Null, then it is being set the to main element.
	   * @type {Null|EventTarget}
	   * @default null
	   */
	  inputTarget: null,

	  /**
	   * force an input class
	   * @type {Null|Function}
	   * @default null
	   */
	  inputClass: null,

	  /**
	   * Default recognizer setup when calling `Hammer()`
	   * When creating a new Manager these will be skipped.
	   * @type {Array}
	   */
	  preset: [
	    // RecognizerClass, options, [recognizeWith, ...], [requireFailure, ...]
	    [RotateRecognizer, {enable: false}],
	    [PinchRecognizer, {enable: false}, ['rotate']],
	    [SwipeRecognizer, {direction: DIRECTION_HORIZONTAL}],
	    [PanRecognizer, {direction: DIRECTION_HORIZONTAL}, ['swipe']],
	    [TapRecognizer],
	    [TapRecognizer, {event: 'doubletap', taps: 2}, ['tap']],
	    [PressRecognizer]
	  ],

	  /**
	   * Some CSS properties can be used to improve the working of Hammer.
	   * Add them to this method and they will be set when creating a new Manager.
	   * @namespace
	   */
	  cssProps: {
	    /**
	     * Disables text selection to improve the dragging gesture. Mainly for desktop browsers.
	     * @type {String}
	     * @default 'none'
	     */
	    userSelect: 'none',

	    /**
	     * Disable the Windows Phone grippers when pressing an element.
	     * @type {String}
	     * @default 'none'
	     */
	    touchSelect: 'none',

	    /**
	     * Disables the default callout shown when you touch and hold a touch target.
	     * On iOS, when you touch and hold a touch target such as a link, Safari displays
	     * a callout containing information about the link. This property allows you to disable that callout.
	     * @type {String}
	     * @default 'none'
	     */
	    touchCallout: 'none',

	    /**
	     * Specifies whether zooming is enabled. Used by IE10>
	     * @type {String}
	     * @default 'none'
	     */
	    contentZooming: 'none',

	    /**
	     * Specifies that an entire element should be draggable instead of its contents. Mainly for desktop browsers.
	     * @type {String}
	     * @default 'none'
	     */
	    userDrag: 'none',

	    /**
	     * Overrides the highlight color shown when the user taps a link or a JavaScript
	     * clickable element in iOS. This property obeys the alpha value, if specified.
	     * @type {String}
	     * @default 'rgba(0,0,0,0)'
	     */
	    tapHighlightColor: 'rgba(0,0,0,0)'
	  }
	};

	var STOP = 1;
	var FORCED_STOP = 2;

	/**
	 * Manager
	 * @param {HTMLElement} element
	 * @param {Object} [options]
	 * @constructor
	 */
	function Manager(element, options) {
	  this.options = assign({}, Hammer.defaults, options || {});

	  this.options.inputTarget = this.options.inputTarget || element;

	  this.handlers = {};
	  this.session = {};
	  this.recognizers = [];
	  this.oldCssProps = {};

	  this.element = element;
	  this.input = createInputInstance(this);
	  this.touchAction = new TouchAction(this, this.options.touchAction);

	  toggleCssProps(this, true);

	  each(this.options.recognizers, function(item) {
	    var recognizer = this.add(new (item[0])(item[1]));
	    item[2] && recognizer.recognizeWith(item[2]);
	    item[3] && recognizer.requireFailure(item[3]);
	  }, this);
	}

	Manager.prototype = {
	  /**
	   * set options
	   * @param {Object} options
	   * @returns {Manager}
	   */
	  set: function(options) {
	    assign(this.options, options);

	    // Options that need a little more setup
	    if (options.touchAction) {
	      this.touchAction.update();
	    }
	    if (options.inputTarget) {
	      // Clean up existing event listeners and reinitialize
	      this.input.destroy();
	      this.input.target = options.inputTarget;
	      this.input.init();
	    }
	    return this;
	  },

	  /**
	   * stop recognizing for this session.
	   * This session will be discarded, when a new [input]start event is fired.
	   * When forced, the recognizer cycle is stopped immediately.
	   * @param {Boolean} [force]
	   */
	  stop: function(force) {
	    this.session.stopped = force ? FORCED_STOP : STOP;
	  },

	  /**
	   * run the recognizers!
	   * called by the inputHandler function on every movement of the pointers (touches)
	   * it walks through all the recognizers and tries to detect the gesture that is being made
	   * @param {Object} inputData
	   */
	  recognize: function(inputData) {
	    var session = this.session;
	    if (session.stopped) {
	      return;
	    }

	    // run the touch-action polyfill
	    this.touchAction.preventDefaults(inputData);

	    var recognizer;
	    var recognizers = this.recognizers;

	    // this holds the recognizer that is being recognized.
	    // so the recognizer's state needs to be BEGAN, CHANGED, ENDED or RECOGNIZED
	    // if no recognizer is detecting a thing, it is set to `null`
	    var curRecognizer = session.curRecognizer;

	    // reset when the last recognizer is recognized
	    // or when we're in a new session
	    if (!curRecognizer || (curRecognizer && curRecognizer.state & STATE_RECOGNIZED)) {
	      curRecognizer = session.curRecognizer = null;
	    }

	    var i = 0;
	    while (i < recognizers.length) {
	      recognizer = recognizers[i];

	      // find out if we are allowed try to recognize the input for this one.
	      // 1.   allow if the session is NOT forced stopped (see the .stop() method)
	      // 2.   allow if we still haven't recognized a gesture in this session, or the this recognizer is the one
	      //      that is being recognized.
	      // 3.   allow if the recognizer is allowed to run simultaneous with the current recognized recognizer.
	      //      this can be setup with the `recognizeWith()` method on the recognizer.
	      if (session.stopped !== FORCED_STOP && ( // 1
	        !curRecognizer || recognizer == curRecognizer || // 2
	        recognizer.canRecognizeWith(curRecognizer))) { // 3
	        recognizer.recognize(inputData);
	      } else {
	        recognizer.reset();
	      }

	      // if the recognizer has been recognizing the input as a valid gesture, we want to store this one as the
	      // current active recognizer. but only if we don't already have an active recognizer
	      if (!curRecognizer && recognizer.state & (STATE_BEGAN | STATE_CHANGED | STATE_ENDED)) {
	        curRecognizer = session.curRecognizer = recognizer;
	      }
	      i++;
	    }
	  },

	  /**
	   * get a recognizer by its event name.
	   * @param {Recognizer|String} recognizer
	   * @returns {Recognizer|Null}
	   */
	  get: function(recognizer) {
	    if (recognizer instanceof Recognizer) {
	      return recognizer;
	    }

	    var recognizers = this.recognizers;
	    for (var i = 0; i < recognizers.length; i++) {
	      if (recognizers[i].options.event == recognizer) {
	        return recognizers[i];
	      }
	    }
	    return null;
	  },

	  /**
	   * add a recognizer to the manager
	   * existing recognizers with the same event name will be removed
	   * @param {Recognizer} recognizer
	   * @returns {Recognizer|Manager}
	   */
	  add: function(recognizer) {
	    if (invokeArrayArg(recognizer, 'add', this)) {
	      return this;
	    }

	    // remove existing
	    var existing = this.get(recognizer.options.event);
	    if (existing) {
	      this.remove(existing);
	    }

	    this.recognizers.push(recognizer);
	    recognizer.manager = this;

	    this.touchAction.update();
	    return recognizer;
	  },

	  /**
	   * remove a recognizer by name or instance
	   * @param {Recognizer|String} recognizer
	   * @returns {Manager}
	   */
	  remove: function(recognizer) {
	    if (invokeArrayArg(recognizer, 'remove', this)) {
	      return this;
	    }

	    recognizer = this.get(recognizer);

	    // let's make sure this recognizer exists
	    if (recognizer) {
	      var recognizers = this.recognizers;
	      var index = inArray(recognizers, recognizer);

	      if (index !== -1) {
	        recognizers.splice(index, 1);
	        this.touchAction.update();
	      }
	    }

	    return this;
	  },

	  /**
	   * bind event
	   * @param {String} events
	   * @param {Function} handler
	   * @returns {EventEmitter} this
	   */
	  on: function(events, handler) {
	    if (events === undefined) {
	      return;
	    }
	    if (handler === undefined) {
	      return;
	    }

	    var handlers = this.handlers;
	    each(splitStr(events), function(event) {
	      handlers[event] = handlers[event] || [];
	      handlers[event].push(handler);
	    });
	    return this;
	  },

	  /**
	   * unbind event, leave emit blank to remove all handlers
	   * @param {String} events
	   * @param {Function} [handler]
	   * @returns {EventEmitter} this
	   */
	  off: function(events, handler) {
	    if (events === undefined) {
	      return;
	    }

	    var handlers = this.handlers;
	    each(splitStr(events), function(event) {
	      if (!handler) {
	        delete handlers[event];
	      } else {
	        handlers[event] && handlers[event].splice(inArray(handlers[event], handler), 1);
	      }
	    });
	    return this;
	  },

	  /**
	   * emit event to the listeners
	   * @param {String} event
	   * @param {Object} data
	   */
	  emit: function(event, data) {
	    // we also want to trigger dom events
	    if (this.options.domEvents) {
	      triggerDomEvent(event, data);
	    }

	    // no handlers, so skip it all
	    var handlers = this.handlers[event] && this.handlers[event].slice();
	    if (!handlers || !handlers.length) {
	      return;
	    }

	    data.type = event;
	    data.preventDefault = function() {
	      data.srcEvent.preventDefault();
	    };

	    var i = 0;
	    while (i < handlers.length) {
	      handlers[i](data);
	      i++;
	    }
	  },

	  /**
	   * destroy the manager and unbinds all events
	   * it doesn't unbind dom events, that is the user own responsibility
	   */
	  destroy: function() {
	    this.element && toggleCssProps(this, false);

	    this.handlers = {};
	    this.session = {};
	    this.input.destroy();
	    this.element = null;
	  }
	};

	/**
	 * add/remove the css properties as defined in manager.options.cssProps
	 * @param {Manager} manager
	 * @param {Boolean} add
	 */
	function toggleCssProps(manager, add) {
	  var element = manager.element;
	  if (!element.style) {
	    return;
	  }
	  var prop;
	  each(manager.options.cssProps, function(value, name) {
	    prop = prefixed(element.style, name);
	    if (add) {
	      manager.oldCssProps[prop] = element.style[prop];
	      element.style[prop] = value;
	    } else {
	      element.style[prop] = manager.oldCssProps[prop] || '';
	    }
	  });
	  if (!add) {
	    manager.oldCssProps = {};
	  }
	}

	/**
	 * trigger dom event
	 * @param {String} event
	 * @param {Object} data
	 */
	function triggerDomEvent(event, data) {
	  var gestureEvent = document.createEvent('Event');
	  gestureEvent.initEvent(event, true, true);
	  gestureEvent.gesture = data;
	  data.target.dispatchEvent(gestureEvent);
	}

	assign(Hammer, {
	  INPUT_START: INPUT_START,
	  INPUT_MOVE: INPUT_MOVE,
	  INPUT_END: INPUT_END,
	  INPUT_CANCEL: INPUT_CANCEL,

	  STATE_POSSIBLE: STATE_POSSIBLE,
	  STATE_BEGAN: STATE_BEGAN,
	  STATE_CHANGED: STATE_CHANGED,
	  STATE_ENDED: STATE_ENDED,
	  STATE_RECOGNIZED: STATE_RECOGNIZED,
	  STATE_CANCELLED: STATE_CANCELLED,
	  STATE_FAILED: STATE_FAILED,

	  DIRECTION_NONE: DIRECTION_NONE,
	  DIRECTION_LEFT: DIRECTION_LEFT,
	  DIRECTION_RIGHT: DIRECTION_RIGHT,
	  DIRECTION_UP: DIRECTION_UP,
	  DIRECTION_DOWN: DIRECTION_DOWN,
	  DIRECTION_HORIZONTAL: DIRECTION_HORIZONTAL,
	  DIRECTION_VERTICAL: DIRECTION_VERTICAL,
	  DIRECTION_ALL: DIRECTION_ALL,

	  Manager: Manager,
	  Input: Input,
	  TouchAction: TouchAction,

	  TouchInput: TouchInput,
	  MouseInput: MouseInput,
	  PointerEventInput: PointerEventInput,
	  TouchMouseInput: TouchMouseInput,
	  SingleTouchInput: SingleTouchInput,

	  Recognizer: Recognizer,
	  AttrRecognizer: AttrRecognizer,
	  Tap: TapRecognizer,
	  Pan: PanRecognizer,
	  Swipe: SwipeRecognizer,
	  Pinch: PinchRecognizer,
	  Rotate: RotateRecognizer,
	  Press: PressRecognizer,

	  on: addEventListeners,
	  off: removeEventListeners,
	  each: each,
	  merge: merge,
	  extend: extend,
	  assign: assign,
	  inherit: inherit,
	  bindFn: bindFn,
	  prefixed: prefixed
	});

	// jquery.hammer.js
	// This jQuery plugin is just a small wrapper around the Hammer() class.
	// It also extends the Manager.emit method by triggering jQuery events.
	// $(element).hammer(options).bind("pan", myPanHandler);
	// The Hammer instance is stored at $element.data("hammer").
	// https://github.com/hammerjs/jquery.hammer.js

	(function($, Hammer) {
	  function hammerify(el, options) {
	    var $el = $(el);
	    if (!$el.data('hammer')) {
	      $el.data('hammer', new Hammer($el[0], options));
	    }
	  }

	  $.fn.hammer = function(options) {
	    return this.each(function() {
	      hammerify(this, options);
	    });
	  };

	  // extend the emit method to also trigger jQuery events
	  Hammer.Manager.prototype.emit = (function(originalEmit) {
	    return function(type, data) {
	      originalEmit.call(this, type, data);
	      $(this.element).trigger({
	        type: type,
	        gesture: data
	      });
	    };
	  })(Hammer.Manager.prototype.emit);
	})($, Hammer);

	module.exports = UI.Hammer = Hammer;


/***/ },
/* 4 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	/**
	 * @via https://github.com/Minwe/bootstrap/blob/master/js/alert.js
	 * @copyright Copyright 2013 Twitter, Inc.
	 * @license Apache 2.0
	 */

	// Alert Class
	// NOTE: removeElement option is unavailable now
	var Alert = function(element, options) {
	  var _this = this;
	  this.options = $.extend({}, Alert.DEFAULTS, options);
	  this.$element = $(element);

	  this.$element
	    .addClass('am-fade am-in')
	    .on('click.alert.amui', '.am-close', function() {
	      _this.close();
	    });
	};

	Alert.DEFAULTS = {
	  removeElement: true
	};

	Alert.prototype.close = function() {
	  var $element = this.$element;

	  $element.trigger('close.alert.amui').removeClass('am-in');

	  function processAlert() {
	    $element.trigger('closed.alert.amui').remove();
	  }

	  UI.support.transition && $element.hasClass('am-fade') ?
	    $element
	      .one(UI.support.transition.end, processAlert)
	      .emulateTransitionEnd(200) :
	    processAlert();
	};

	// plugin
	UI.plugin('alert', Alert);

	// Init code
	$(document).on('click.alert.amui.data-api', '[data-am-alert]', function(e) {
	  var $target = $(e.target);
	  $target.is('.am-close') && $(this).alert('close');
	});

	module.exports = Alert;


/***/ },
/* 5 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	/**
	 * @via https://github.com/twbs/bootstrap/blob/master/js/button.js
	 * @copyright (c) 2011-2014 Twitter, Inc
	 * @license The MIT License
	 */

	var Button = function(element, options) {
	  this.$element = $(element);
	  this.options = $.extend({}, Button.DEFAULTS, options);
	  this.isLoading = false;
	  this.hasSpinner = false;
	};

	Button.DEFAULTS = {
	  loadingText: 'loading...',
	  disabledClassName: 'am-disabled',
	  activeClassName: 'am-active',
	  spinner: undefined
	};

	Button.prototype.setState = function(state, stateText) {
	  var $element = this.$element;
	  var disabled = 'disabled';
	  var data = $element.data();
	  var options = this.options;
	  var val = $element.is('input') ? 'val' : 'html';
	  var stateClassName = 'am-btn-' + state + ' ' + options.disabledClassName;

	  state += 'Text';

	  if (!options.resetText) {
	    options.resetText = $element[val]();
	  }

	  // add spinner for element with html()
	  if (UI.support.animation && options.spinner &&
	    val === 'html' && !this.hasSpinner) {
	    options.loadingText = '<span class="am-icon-' + options.spinner +
	      ' am-icon-spin"></span>' + options.loadingText;

	    this.hasSpinner = true;
	  }

	  stateText = stateText ||
	    (data[state] === undefined ? options[state] : data[state]);

	  $element[val](stateText);

	  // push to event loop to allow forms to submit
	  setTimeout($.proxy(function() {
	    // TODO: add stateClass for other states
	    if (state === 'loadingText') {
	      $element.addClass(stateClassName).attr(disabled, disabled);
	      this.isLoading = true;
	    } else if (this.isLoading) {
	      $element.removeClass(stateClassName).removeAttr(disabled);
	      this.isLoading = false;
	    }
	  }, this), 0);
	};

	Button.prototype.toggle = function() {
	  var changed = true;
	  var $element = this.$element;
	  var $parent = this.$element.parent('[class*="am-btn-group"]');
	  var activeClassName = Button.DEFAULTS.activeClassName;

	  if ($parent.length) {
	    var $input = this.$element.find('input');

	    if ($input.prop('type') == 'radio') {
	      if ($input.prop('checked') && $element.hasClass(activeClassName)) {
	        changed = false;
	      } else {
	        $parent.find('.' + activeClassName).removeClass(activeClassName);
	      }
	    }

	    if (changed) {
	      $input.prop('checked',
	        !$element.hasClass(activeClassName)).trigger('change');
	    }
	  }

	  if (changed) {
	    $element.toggleClass(activeClassName);
	    if (!$element.hasClass(activeClassName)) {
	      $element.blur();
	    }
	  }
	};

	UI.plugin('button', Button, {
	  dataOptions: 'data-am-loading',
	  methodCall: function(args, instance) {
	    if (args[0] === 'toggle') {
	      instance.toggle();
	    } else if (typeof args[0] === 'string') {
	      instance.setState.apply(instance, args);
	    }
	  }
	});

	// Init code
	$(document).on('click.button.amui.data-api', '[data-am-button]', function(e) {
	  e.preventDefault();
	  var $btn = $(e.target);

	  if (!$btn.hasClass('am-btn')) {
	    $btn = $btn.closest('.am-btn');
	  }

	  $btn.button('toggle');
	});

	UI.ready(function(context) {
	  $('[data-am-loading]', context).button();

	  // resolves #866
	  $('[data-am-button]', context).find('input:checked').each(function() {
	    $(this).parent('label').addClass(Button.DEFAULTS.activeClassName);
	  });
	});

	module.exports = UI.button = Button;


/***/ },
/* 6 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	/**
	 * Cascader
	 */

	var Cascader = function(element, options) {
	  this.options = $.extend({}, Cascader.DEFAULTS, options);
	  this.$element = $(element);
	  if ($.fn.selected && this.options.selectStyle) {
	    this.isSelected = true;
	  }
	  this.init();
	};

	Cascader.DEFAULTS = {
	  data: null,
	  defaultValue: null, // default select option
	  selectStyle: true, // if apply selected style
	  selectOption: null, // selected plugin options
	};

	Cascader.prototype.init = function() {
	  var options = this.options;
	  var $ele = this.$element;
	  var defaultVal = this.options.defaultValue;

	  this.selectListDOM = []; // Select DOM has been rendered
	  this.selectIndex = []; // Selects's index
	  this.selectValue = []; // Slectes's value

	  if (defaultVal) {
	    var i = this.arrayTreeIndex(options.data, defaultVal[0]);
	    this.create(options.data, i);
	    this.createDefault();
	  } else {
	    this.create(options.data)
	  }

	  this.selectEvent();
	};

	/**
	 * [render DOM and update data]
	 * @param  {[type]} array   [data to be rendered]
	 * @param  {[number]} eqIndex [if eqIndex, set select option]
	 */
	Cascader.prototype.create = function(data, eqIndex) {
	  var $ele = this.$element;
	  var dom = this.generateSelect(data);
	  var index = eqIndex || 0;

	  $ele.append(dom);
	  if ($.fn.selected) {
	    if (this.options.selectOption) {
	      dom.selected(this.options.selectOption);
	    } else{
	      dom.selected();
	    }

	    // select option
	    if (eqIndex) {
	      dom.find('option').eq(index).attr('selected', true);
	      dom.trigger('changed.selected.amui');
	    }
	  };

	  // store data
	  this.selectListDOM.push(dom);
	  this.selectValue.push(data[index].value);
	  this.selectIndex.push(index);
	};

	Cascader.prototype.createSelect = function(array, optionIndex) {
	  var data = this.getData(array);

	  if (!data) {
	    return true;
	  }

	  this.create(data, optionIndex);

	  // if sub-select's length is 1, then create it
	  if (data[0].children && data[0].children.length === 1) {
	    this.create(data[0].children)
	  }
	}

	Cascader.prototype.createDefault = function() {
	  var _this = this;
	  var data = this.options.data.slice();
	  var defaultVal = this.options.defaultValue;
	  var position = 0;
	  var indexList = [];
	  var indexListArray = [];

	  // loop defaultValue to get defaultvalue's index in data
	  defaultVal.forEach(function(val) {
	    position = _this.arrayTreeIndex(data, val);
	    indexList.push(position);
	    indexListArray.push(indexList.slice());
	    data = data[position].children;
	  });

	  indexListArray.pop();

	  indexListArray.forEach(function(array, i) {
	    _this.createSelect(array, indexList[i+1]);
	  })
	};

	/**
	 * [get index matched value from array-tree]
	 * @param  {[array]} array [array-tree data]
	 * @param  {[string]} val   [matched data]
	 * @return {[number]}       [index]
	 */
	Cascader.prototype.arrayTreeIndex = function(array, val) {
	  var ii = 0;
	  array.forEach(function(item, i) {
	    if (item.value === val) {
	      ii = i;
	      return
	    }
	  })
	  return ii;
	}

	Cascader.prototype.generateSelect = function(data) {
	  var uuid = UI.utils.generateGUID('am-cascader');
	  var $option = '';
	  data.forEach(function(item) {
	    $option += '<option label=' + item.label + ' value=' + item.value + '>' + item.label + '</option>';
	  });

	  var $select = $('<select id=' + uuid + '></select>');
	  $select.append($option);
	  return $select;
	};

	/**
	 * [get matched value form array-tree]
	 * @param  {[array]} data  [index list array]
	 * @return {[array]}       [matched value]
	 */
	Cascader.prototype.getData = function(array) {
	  var x = null;
	  var status = true;
	  var data = this.options.data;

	  array.forEach(function(value) {
	    if ((x || data)[value].children) {
	      x = (x || data)[value].children;
	    } else {
	      x = false;
	      return;
	    }
	  });

	  return x;
	};

	Cascader.prototype.selectEvent = function() {
	  var _this = this;

	  this.$element.on('change', 'select', function(){
	    var optIndex = this.selectedIndex;
	    var seIndex = _this.$element.find('select').index(this);

	    // clear old data and add new data
	    var clear = _this.selectListDOM.splice(seIndex + 1);
	    _this.selectIndex[seIndex] = optIndex;
	    _this.selectIndex.splice(seIndex + 1);
	    _this.selectValue[seIndex] = $(this).val();
	    _this.selectValue.splice(seIndex + 1);

	    // remove unmatched dom
	    clear.forEach(function(old) {
	      old.selected('destroy');
	      old.remove();
	    });

	    _this.createSelect(_this.selectIndex);

	    _this.$element.trigger('change.cascader.amui');
	  })
	};

	Cascader.prototype.get = function() {
	  return this.selectValue;
	}

	UI.plugin('cascader', Cascader);

	UI.ready(function(context) {
	  $('[data-am-cascader]', context).cascader();
	});

	module.exports = Cascader;


/***/ },
/* 7 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	/**
	 * @via https://github.com/twbs/bootstrap/blob/master/js/collapse.js
	 * @copyright (c) 2011-2014 Twitter, Inc
	 * @license The MIT License
	 */

	var Collapse = function(element, options) {
	  this.$element = $(element);
	  this.$arrow = this.$element.prev().find('[class*="am-icon-"]');
	  this.options = $.extend({}, Collapse.DEFAULTS, options);
	  this.transitioning = null;


	  if (this.options.parent) {
	    this.$parent = $(this.options.parent);
	  }

	  if (this.options.toggle) {
	    this.toggle();
	  }
	};

	Collapse.DEFAULTS = {
	  toggle: true
	};

	Collapse.prototype.open = function() {
	  if (this.transitioning || this.$element.hasClass('am-in')) {
	    return;
	  }

	  if (this.$arrow) {
	    this.$arrow.addClass('am-panel-hd-arrow');
	  }

	  var startEvent = $.Event('open.collapse.amui');
	  this.$element.trigger(startEvent);

	  if (startEvent.isDefaultPrevented()) {
	    return;
	  }

	  var actives = this.$parent && this.$parent.find('> .am-panel > .am-in');

	  if (actives && actives.length) {
	    var hasData = actives.data('amui.collapse');

	    if (hasData && hasData.transitioning) {
	      return;
	    }

	    Plugin.call(actives, 'close');

	    hasData || actives.data('amui.collapse', null);
	  }

	  this.$element
	    .removeClass('am-collapse')
	    .addClass('am-collapsing').height(0);

	  this.transitioning = 1;

	  var complete = function() {
	    this.$element
	      .removeClass('am-collapsing')
	      .addClass('am-collapse am-in')
	      .height('')
	      .trigger('opened.collapse.amui');
	    this.transitioning = 0;
	  };

	  if (!UI.support.transition) {
	    return complete.call(this);
	  }

	  var scrollHeight = this.$element[0].scrollHeight;

	  this.$element
	    .one(UI.support.transition.end, $.proxy(complete, this))
	    .emulateTransitionEnd(300)
	    .css({height: scrollHeight}); // 当折叠的容器有 padding 时，如果用 height() 只能设置内容的宽度
	};

	Collapse.prototype.close = function() {
	  if (this.transitioning || !this.$element.hasClass('am-in')) {
	    return;
	  }

	  if (this.$arrow) {
	    this.$arrow.removeClass('am-panel-hd-arrow');
	  }

	  var startEvent = $.Event('close.collapse.amui');
	  this.$element.trigger(startEvent);

	  if (startEvent.isDefaultPrevented()) {
	    return;
	  }

	  this.$element.height(this.$element.height()).redraw();

	  this.$element.addClass('am-collapsing').
	    removeClass('am-collapse am-in');

	  this.transitioning = 1;

	  var complete = function() {
	    this.transitioning = 0;
	    this.$element
	      .trigger('closed.collapse.amui')
	      .removeClass('am-collapsing')
	      .addClass('am-collapse');
	    // css({height: '0'});
	  };

	  if (!UI.support.transition) {
	    return complete.call(this);
	  }

	  this.$element.height(0)
	    .one(UI.support.transition.end, $.proxy(complete, this))
	    .emulateTransitionEnd(300);
	};

	Collapse.prototype.toggle = function() {
	  this[this.$element.hasClass('am-in') ? 'close' : 'open']();
	};

	// Collapse Plugin
	function Plugin(option) {
	  return this.each(function() {
	    var $this = $(this);
	    var data = $this.data('amui.collapse');
	    var options = $.extend({}, Collapse.DEFAULTS,
	      UI.utils.options($this.attr('data-am-collapse')),
	      typeof option == 'object' && option);

	    if (!data && options.toggle && option === 'open') {
	      option = !option;
	    }

	    if (!data) {
	      $this.data('amui.collapse', (data = new Collapse(this, options)));
	    }

	    if (typeof option == 'string') {
	      data[option]();
	    }
	  });
	}

	$.fn.collapse = Plugin;

	// Init code
	$(document).on('click.collapse.amui.data-api', '[data-am-collapse]',
	  function(e) {
	    var href;
	    var $this = $(this);
	    var options = UI.utils.options($this.attr('data-am-collapse'));
	    var target = options.target ||
	      e.preventDefault() ||
	      (href = $this.attr('href')) &&
	      href.replace(/.*(?=#[^\s]+$)/, '');
	    var $target = $(target);
	    var data = $target.data('amui.collapse');
	    var option = data ? 'toggle' : options;
	    var parent = options.parent;
	    var $parent = parent && $(parent);

	    if (!data || !data.transitioning) {
	      if ($parent) {
	        // '[data-am-collapse*="{parent: \'' + parent + '"]
	        $parent.find('[data-am-collapse]').not($this).addClass('am-collapsed');
	      }

	      $this[$target.hasClass('am-in') ?
	        'addClass' : 'removeClass']('am-collapsed');
	    }

	    Plugin.call($target, option);
	  });

	module.exports = UI.collapse = Collapse;

	// TODO: 更好的 target 选择方式
	//       折叠的容器必须没有 border/padding 才能正常处理，否则动画会有一些小问题
	//       寻找更好的未知高度 transition 动画解决方案，max-height 之类的就算了


/***/ },
/* 8 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	var $doc = $(document);

	/**
	 * bootstrap-datepicker.js
	 * @via http://www.eyecon.ro/bootstrap-datepicker
	 * @license http://www.apache.org/licenses/LICENSE-2.0
	 */
	var Datepicker = function(element, options) {
	  this.$element = $(element);
	  this.options = $.extend({}, Datepicker.DEFAULTS, options);
	  this.format = DPGlobal.parseFormat(this.options.format);

	  this.$element.data('date', this.options.date);
	  this.language = this.getLocale(this.options.locale);
	  this.theme = this.options.theme;
	  this.$picker = $(DPGlobal.template).appendTo('body').on({
	    click: $.proxy(this.click, this)
	    // mousedown: $.proxy(this.mousedown, this)
	  });

	  this.isInput = this.$element.is('input');
	  this.component = this.$element.is('.am-datepicker-date') ?
	    this.$element.find('.am-datepicker-add-on') : false;

	  if (this.isInput) {
	    this.$element.on({
	      'click.datepicker.amui': $.proxy(this.open, this),
	      // blur: $.proxy(this.close, this),
	      'keyup.datepicker.amui': $.proxy(this.update, this)
	    });
	  } else {
	    if (this.component) {
	      this.component.on('click.datepicker.amui', $.proxy(this.open, this));
	    } else {
	      this.$element.on('click.datepicker.amui', $.proxy(this.open, this));
	    }
	  }

	  this.minViewMode = this.options.minViewMode;

	  if (typeof this.minViewMode === 'string') {
	    switch (this.minViewMode) {
	      case 'months':
	        this.minViewMode = 1;
	        break;
	      case 'years':
	        this.minViewMode = 2;
	        break;
	      default:
	        this.minViewMode = 0;
	        break;
	    }
	  }

	  this.viewMode = this.options.viewMode;

	  if (typeof this.viewMode === 'string') {
	    switch (this.viewMode) {
	      case 'months':
	        this.viewMode = 1;
	        break;
	      case 'years':
	        this.viewMode = 2;
	        break;
	      default:
	        this.viewMode = 0;
	        break;
	    }
	  }

	  this.startViewMode = this.viewMode;
	  this.weekStart = ((this.options.weekStart ||
	  Datepicker.locales[this.language].weekStart || 0) % 7);
	  this.weekEnd = ((this.weekStart + 6) % 7);
	  this.onRender = this.options.onRender;

	  this.setTheme();
	  this.fillDow();
	  this.fillMonths();
	  this.update();
	  this.showMode();
	};

	Datepicker.DEFAULTS = {
	  locale: 'zh_CN',
	  format: 'yyyy-mm-dd',
	  weekStart: undefined,
	  viewMode: 0,
	  minViewMode: 0,
	  date: '',
	  theme: '',
	  autoClose: 1,
	  onRender: function(date) {
	    return '';
	  }
	};

	Datepicker.prototype.open = function(e) {
	  this.$picker.show();
	  this.height = this.component ?
	    this.component.outerHeight() : this.$element.outerHeight();

	  this.place();
	  $(window).on('resize.datepicker.amui', $.proxy(this.place, this));
	  if (e) {
	    e.stopPropagation();
	    e.preventDefault();
	  }
	  var that = this;
	  $doc.on('mousedown.datapicker.amui touchstart.datepicker.amui', function(ev) {
	    if ($(ev.target).closest('.am-datepicker').length === 0) {
	      that.close();
	    }
	  });
	  this.$element.trigger({
	    type: 'open.datepicker.amui',
	    date: this.date
	  });
	};

	Datepicker.prototype.close = function() {
	  this.$picker.hide();
	  $(window).off('resize.datepicker.amui', this.place);
	  this.viewMode = this.startViewMode;
	  this.showMode();
	  if (!this.isInput) {
	    $(document).off('mousedown.datapicker.amui touchstart.datepicker.amui',
	      this.close);
	  }
	  // this.set();
	  this.$element.trigger({
	    type: 'close.datepicker.amui',
	    date: this.date
	  });
	};

	Datepicker.prototype.set = function() {
	  var formatted = DPGlobal.formatDate(this.date, this.format);
	  var $input;

	  if (!this.isInput) {
	    if (this.component) {
	      $input = this.$element.find('input').attr('value', formatted);
	    }

	    this.$element.data('date', formatted);
	  } else {
	    $input = this.$element.attr('value', formatted);
	  }

	  // fixes https://github.com/amazeui/amazeui/issues/711
	  $input && $input.trigger('change');
	};

	Datepicker.prototype.setValue = function(newDate) {
	  if (typeof newDate === 'string') {
	    this.date = DPGlobal.parseDate(newDate, this.format);
	  } else {
	    this.date = new Date(newDate);
	  }
	  this.set();

	  this.viewDate = new Date(this.date.getFullYear(),
	    this.date.getMonth(), 1, 0, 0, 0, 0);

	  this.fill();
	};

	Datepicker.prototype.place = function() {
	  var offset = this.component ?
	    this.component.offset() : this.$element.offset();
	  var $width = this.component ?
	    this.component.width() : this.$element.width();
	  var top = offset.top + this.height;
	  var left = offset.left;
	  var right = $doc.width() - offset.left - $width;
	  var isOutView = this.isOutView();

	  this.$picker.removeClass('am-datepicker-right');
	  this.$picker.removeClass('am-datepicker-up');

	  if ($doc.width() > 640) {
	    if (isOutView.outRight) {
	      this.$picker.addClass('am-datepicker-right');
	      this.$picker.css({
	        top: top,
	        left: 'auto',
	        right: right
	      });
	      return;
	    }
	    if (isOutView.outBottom) {
	      this.$picker.addClass('am-datepicker-up');
	      top = offset.top - this.$picker.outerHeight(true);
	    }
	  } else {
	    left = 0;
	  }

	  this.$picker.css({
	    top: top,
	    left: left
	  });
	};

	Datepicker.prototype.update = function(newDate) {
	  this.date = DPGlobal.parseDate(
	    typeof newDate === 'string' ? newDate : (this.isInput ?
	      this.$element.prop('value') : this.$element.data('date')),
	    this.format
	  );
	  this.viewDate = new Date(this.date.getFullYear(),
	    this.date.getMonth(), 1, 0, 0, 0, 0);
	  this.fill();
	};

	// Days of week
	Datepicker.prototype.fillDow = function() {
	  var dowCount = this.weekStart;
	  var html = '<tr>';
	  while (dowCount < this.weekStart + 7) {
	    // NOTE: do % then add 1
	    html += '<th class="am-datepicker-dow">' +
	    Datepicker.locales[this.language].daysMin[(dowCount++) % 7] +
	    '</th>';
	  }
	  html += '</tr>';

	  this.$picker.find('.am-datepicker-days thead').append(html);
	};

	Datepicker.prototype.fillMonths = function() {
	  var html = '';
	  var i = 0;
	  while (i < 12) {
	    html += '<span class="am-datepicker-month">' +
	    Datepicker.locales[this.language].monthsShort[i++] + '</span>';
	  }
	  this.$picker.find('.am-datepicker-months td').append(html);
	};

	Datepicker.prototype.fill = function() {
	  var d = new Date(this.viewDate);
	  var year = d.getFullYear();
	  var month = d.getMonth();
	  var currentDate = this.date.valueOf();

	  var prevMonth = new Date(year, month - 1, 28, 0, 0, 0, 0);
	  var day = DPGlobal
	    .getDaysInMonth(prevMonth.getFullYear(), prevMonth.getMonth());

	  var daysSelect = this.$picker
	    .find('.am-datepicker-days .am-datepicker-select');

	  if (this.language === 'zh_CN') {
	    daysSelect.text(year + Datepicker.locales[this.language].year[0] +
	    ' ' + Datepicker.locales[this.language].months[month]);
	  } else {
	    daysSelect.text(Datepicker.locales[this.language].months[month] +
	    ' ' + year);
	  }

	  prevMonth.setDate(day);
	  prevMonth.setDate(day - (prevMonth.getDay() - this.weekStart + 7) % 7);

	  var nextMonth = new Date(prevMonth);
	  nextMonth.setDate(nextMonth.getDate() + 42);
	  nextMonth = nextMonth.valueOf();
	  var html = [];

	  var className;
	  var prevY;
	  var prevM;

	  while (prevMonth.valueOf() < nextMonth) {
	    if (prevMonth.getDay() === this.weekStart) {
	      html.push('<tr>');
	    }

	    className = this.onRender(prevMonth, 0);
	    prevY = prevMonth.getFullYear();
	    prevM = prevMonth.getMonth();

	    if ((prevM < month && prevY === year) || prevY < year) {
	      className += ' am-datepicker-old';
	    } else if ((prevM > month && prevY === year) || prevY > year) {
	      className += ' am-datepicker-new';
	    }

	    if (prevMonth.valueOf() === currentDate) {
	      className += ' am-active';
	    }
	    html.push('<td class="am-datepicker-day ' +
	    className + '">' + prevMonth.getDate() + '</td>');

	    if (prevMonth.getDay() === this.weekEnd) {
	      html.push('</tr>');
	    }

	    prevMonth.setDate(prevMonth.getDate() + 1);
	  }

	  this.$picker.find('.am-datepicker-days tbody')
	    .empty().append(html.join(''));

	  var currentYear = this.date.getFullYear();
	  var months = this.$picker.find('.am-datepicker-months')
	    .find('.am-datepicker-select')
	    .text(year);
	  months = months.end()
	    .find('span').removeClass('am-active').removeClass('am-disabled');

	  var monthLen = 0;

	  while (monthLen < 12) {
	    if (this.onRender(d.setFullYear(year, monthLen), 1)) {
	      months.eq(monthLen).addClass('am-disabled');
	    }
	    monthLen++;
	  }

	  if (currentYear === year) {
	    months.eq(this.date.getMonth())
	        .removeClass('am-disabled')
	        .addClass('am-active');
	  }

	  html = '';
	  year = parseInt(year / 10, 10) * 10;
	  var yearCont = this.$picker
	    .find('.am-datepicker-years')
	    .find('.am-datepicker-select')
	    .text(year + '-' + (year + 9))
	    .end()
	    .find('td');
	  var yearClassName;
	  // fixes https://github.com/amazeui/amazeui/issues/770
	  // maybe not need now
	  var viewDate = new Date(this.viewDate);

	  year -= 1;

	  for (var i = -1; i < 11; i++) {
	    yearClassName = this.onRender(viewDate.setFullYear(year), 2);
	    html += '<span class="' + yearClassName + '' +
	    (i === -1 || i === 10 ? ' am-datepicker-old' : '') +
	    (currentYear === year ? ' am-active' : '') + '">' + year + '</span>';
	    year += 1;
	  }
	  yearCont.html(html);
	};

	Datepicker.prototype.click = function(event) {
	  event.stopPropagation();
	  event.preventDefault();
	  var month;
	  var year;
	  var $dayActive = this.$picker.find('.am-datepicker-days').find('.am-active');
	  var $months = this.$picker.find('.am-datepicker-months');
	  var $monthIndex = $months.find('.am-active').index();

	  var $target = $(event.target).closest('span, td, th');
	  if ($target.length === 1) {
	    switch ($target[0].nodeName.toLowerCase()) {
	      case 'th':
	        switch ($target[0].className) {
	          case 'am-datepicker-switch':
	            this.showMode(1);
	            break;
	          case 'am-datepicker-prev':
	          case 'am-datepicker-next':
	            this.viewDate['set' + DPGlobal.modes[this.viewMode].navFnc].call(
	              this.viewDate,
	              this.viewDate
	                ['get' + DPGlobal.modes[this.viewMode].navFnc]
	                .call(this.viewDate) +
	              DPGlobal.modes[this.viewMode].navStep *
	              ($target[0].className === 'am-datepicker-prev' ? -1 : 1)
	            );
	            this.fill();
	            this.set();
	            break;
	        }
	        break;
	      case 'span':
	        if ($target.is('.am-disabled')) {
	          return;
	        }

	        if ($target.is('.am-datepicker-month')) {
	          month = $target.parent().find('span').index($target);

	          if ($target.is('.am-active')) {
	            this.viewDate.setMonth(month, $dayActive.text());
	          } else {
	            this.viewDate.setMonth(month);
	          }
	          this.chooseMonth = true;

	        } else {
	          year = parseInt($target.text(), 10) || 0;
	          if ($target.is('.am-active')) {
	            this.viewDate.setFullYear(year, $monthIndex, $dayActive.text());
	          } else {
	            this.viewDate.setFullYear(year);
	          }
	          this.chooseYear = true;

	        }

	        if (this.viewMode !== 0) {
	          this.date = new Date(this.viewDate);
	          this.$element.trigger({
	            type: 'changeDate.datepicker.amui',
	            date: this.date,
	            viewMode: DPGlobal.modes[this.viewMode].clsName
	          });
	        }

	        this.showMode(-1);
	        this.fill();
	        this.set();

	        var timeArr = this.format.parts;
	        if (((timeArr.length === 2 && this.chooseMonth) || (timeArr[1] === '' && this.chooseYear)) && this.options.autoClose) {
	          this.close();
	          this.chooseMonth = false;
	          this.chooseYear = false;
	        }

	        break;
	      case 'td':
	        if ($target.is('.am-datepicker-day') && !$target.is('.am-disabled')) {
	          var day = parseInt($target.text(), 10) || 1;
	          month = this.viewDate.getMonth();
	          if ($target.is('.am-datepicker-old')) {
	            month -= 1;
	          } else if ($target.is('.am-datepicker-new')) {
	            month += 1;
	          }
	          year = this.viewDate.getFullYear();
	          this.date = new Date(year, month, day, 0, 0, 0, 0);
	          this.viewDate = new Date(year, month, Math.min(28, day), 0, 0, 0, 0);
	          this.fill();
	          this.set();
	          this.$element.trigger({
	            type: 'changeDate.datepicker.amui',
	            date: this.date,
	            viewMode: DPGlobal.modes[this.viewMode].clsName
	          });

	          this.options.autoClose && this.close();
	        }
	        break;
	    }
	  }
	};

	Datepicker.prototype.mousedown = function(event) {
	  event.stopPropagation();
	  event.preventDefault();
	};

	Datepicker.prototype.showMode = function(dir) {
	  if (dir) {
	    this.viewMode = Math.max(this.minViewMode,
	      Math.min(2, this.viewMode + dir));
	  }

	  this.$picker.find('>div').hide().
	    filter('.am-datepicker-' + DPGlobal.modes[this.viewMode].clsName).show();
	};

	Datepicker.prototype.isOutView = function() {
	  var offset = this.component ?
	    this.component.offset() : this.$element.offset();
	  var isOutView = {
	    outRight: false,
	    outBottom: false
	  };
	  var $picker = this.$picker;
	  var width = offset.left + $picker.outerWidth(true);
	  var height = offset.top + $picker.outerHeight(true) +
	    this.$element.innerHeight();

	  if (width > $doc.width()) {
	    isOutView.outRight = true;
	  }
	  if (height > $doc.height()) {
	    isOutView.outBottom = true;
	  }
	  return isOutView;
	};

	Datepicker.prototype.getLocale = function(locale) {
	  if (!locale) {
	    locale = navigator.language && navigator.language.split('-');
	    locale[1] = locale[1].toUpperCase();
	    locale = locale.join('_');
	  }

	  if (!Datepicker.locales[locale]) {
	    locale = 'en_US';
	  }
	  return locale;
	};

	Datepicker.prototype.setTheme = function() {
	  if (this.theme) {
	    this.$picker.addClass('am-datepicker-' + this.theme);
	  }
	};

	// Datepicker locales
	Datepicker.locales = {
	  en_US: {
	    days: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday',
	      'Friday', 'Saturday'],
	    daysShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
	    daysMin: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
	    months: ['January', 'February', 'March', 'April', 'May', 'June',
	      'July', 'August', 'September', 'October', 'November', 'December'],
	    monthsShort: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
	      'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
	    weekStart: 0
	  },
	  zh_CN: {
	    days: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
	    daysShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
	    daysMin: ['日', '一', '二', '三', '四', '五', '六'],
	    months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月',
	      '八月', '九月', '十月', '十一月', '十二月'],
	    monthsShort: ['一月', '二月', '三月', '四月', '五月', '六月',
	      '七月', '八月', '九月', '十月', '十一月', '十二月'],
	    weekStart: 1,
	    year: ['年']
	  }
	};

	var DPGlobal = {
	  modes: [
	    {
	      clsName: 'days',
	      navFnc: 'Month',
	      navStep: 1
	    },
	    {
	      clsName: 'months',
	      navFnc: 'FullYear',
	      navStep: 1
	    },
	    {
	      clsName: 'years',
	      navFnc: 'FullYear',
	      navStep: 10
	    }
	  ],

	  isLeapYear: function(year) {
	    return (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0));
	  },

	  getDaysInMonth: function(year, month) {
	    return [31, (DPGlobal.isLeapYear(year) ? 29 : 28),
	      31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month];
	  },

	  parseFormat: function(format) {
	    var separator = format.match(/[.\/\-\s].*?/);
	    var parts = format.split(/\W+/);

	    if (!separator || !parts || parts.length === 0) {
	      throw new Error('Invalid date format.');
	    }

	    return {
	      separator: separator,
	      parts: parts
	    };
	  },

	  parseDate: function(date, format) {
	    var parts = date.split(format.separator);
	    var val;
	    date = new Date();

	    date.setHours(0);
	    date.setMinutes(0);
	    date.setSeconds(0);
	    date.setMilliseconds(0);

	    if (parts.length === format.parts.length) {
	      var year = date.getFullYear();
	      var day = date.getDate();
	      var month = date.getMonth();

	      for (var i = 0, cnt = format.parts.length; i < cnt; i++) {
	        val = parseInt(parts[i], 10) || 1;
	        switch (format.parts[i]) {
	          case 'dd':
	          case 'd':
	            day = val;
	            date.setDate(val);
	            break;
	          case 'mm':
	          case 'm':
	            month = val - 1;
	            date.setMonth(val - 1);
	            break;
	          case 'yy':
	            year = 2000 + val;
	            date.setFullYear(2000 + val);
	            break;
	          case 'yyyy':
	            year = val;
	            date.setFullYear(val);
	            break;
	        }
	      }
	      date = new Date(year, month, day, 0, 0, 0);
	    }
	    return date;
	  },

	  formatDate: function(date, format) {
	    var val = {
	      d: date.getDate(),
	      m: date.getMonth() + 1,
	      yy: date.getFullYear().toString().substring(2),
	      yyyy: date.getFullYear()
	    };
	    var dateArray = [];

	    val.dd = (val.d < 10 ? '0' : '') + val.d;
	    val.mm = (val.m < 10 ? '0' : '') + val.m;

	    for (var i = 0, cnt = format.parts.length; i < cnt; i++) {
	      dateArray.push(val[format.parts[i]]);
	    }
	    return dateArray.join(format.separator);
	  },

	  headTemplate: '<thead>' +
	  '<tr class="am-datepicker-header">' +
	  '<th class="am-datepicker-prev">' +
	  '<i class="am-datepicker-prev-icon"></i></th>' +
	  '<th colspan="5" class="am-datepicker-switch">' +
	  '<div class="am-datepicker-select"></div></th>' +
	  '<th class="am-datepicker-next"><i class="am-datepicker-next-icon"></i>' +
	  '</th></tr></thead>',

	  contTemplate: '<tbody><tr><td colspan="7"></td></tr></tbody>'
	};

	DPGlobal.template = '<div class="am-datepicker am-datepicker-dropdown">' +
	'<div class="am-datepicker-caret"></div>' +
	'<div class="am-datepicker-days">' +
	'<table class="am-datepicker-table">' +
	DPGlobal.headTemplate +
	'<tbody></tbody>' +
	'</table>' +
	'</div>' +
	'<div class="am-datepicker-months">' +
	'<table class="am-datepicker-table">' +
	DPGlobal.headTemplate +
	DPGlobal.contTemplate +
	'</table>' +
	'</div>' +
	'<div class="am-datepicker-years">' +
	'<table class="am-datepicker-table">' +
	DPGlobal.headTemplate +
	DPGlobal.contTemplate +
	'</table>' +
	'</div>' +
	'</div>';

	// jQuery plugin
	UI.plugin('datepicker', Datepicker);

	// Init code
	UI.ready(function(context) {
	  $('[data-am-datepicker]').datepicker();
	});

	module.exports = UI.datepicker = Datepicker;

	// TODO: 1. 载入动画
	//       2. less 优化


/***/ },
/* 9 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	var $doc = $(document);
	var transition = UI.support.transition;

	var Dimmer = function() {
	  this.id = UI.utils.generateGUID('am-dimmer');
	  this.$element = $(Dimmer.DEFAULTS.tpl, {
	    id: this.id
	  });

	  this.inited = false;
	  this.scrollbarWidth = 0;
	  this.$used = $([]);
	};

	Dimmer.DEFAULTS = {
	  tpl: '<div class="am-dimmer" data-am-dimmer></div>'
	};

	Dimmer.prototype.init = function() {
	  if (!this.inited) {
	    $(document.body).append(this.$element);
	    this.inited = true;
	    $doc.trigger('init.dimmer.amui');
	    this.$element.on('touchmove.dimmer.amui', function(e) {
	      e.preventDefault();
	    });
	  }

	  return this;
	};

	Dimmer.prototype.open = function(relatedElement) {
	  if (!this.inited) {
	    this.init();
	  }

	  var $element = this.$element;

	  // 用于多重调用
	  if (relatedElement) {
	    this.$used = this.$used.add($(relatedElement));
	  }

	  this.checkScrollbar().setScrollbar();

	  $element.show().trigger('open.dimmer.amui');

	  transition && $element.off(transition.end);

	  setTimeout(function() {
	    $element.addClass('am-active');
	  }, 0);

	  return this;
	};

	Dimmer.prototype.close = function(relatedElement, force) {
	  this.$used = this.$used.not($(relatedElement));

	  if (!force && this.$used.length) {
	    return this;
	  }

	  var $element = this.$element;

	  $element.removeClass('am-active').trigger('close.dimmer.amui');

	  function complete() {
	    $element.hide();
	    this.resetScrollbar();
	  }

	  transition ? $element.one(transition.end, $.proxy(complete, this)).emulateTransitionEnd(1000) : complete.call(this);

	  return this;
	};

	Dimmer.prototype.checkScrollbar = function() {
	  this.scrollbarWidth = UI.utils.measureScrollbar();

	  return this;
	};

	Dimmer.prototype.setScrollbar = function() {
	  var $body = $(document.body);
	  var bodyPaddingRight = parseInt(($body.css('padding-right') || 0), 10);

	  if (this.scrollbarWidth) {
	    $body.css('padding-right', bodyPaddingRight + this.scrollbarWidth);
	  }

	  $body.addClass('am-dimmer-active');

	  return this;
	};

	Dimmer.prototype.resetScrollbar = function() {
	  $(document.body).css('padding-right', '').removeClass('am-dimmer-active');

	  return this;
	};

	module.exports = UI.dimmer = new Dimmer();


/***/ },
/* 10 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	var animation = UI.support.animation;

	/**
	 * @via https://github.com/Minwe/bootstrap/blob/master/js/dropdown.js
	 * @copyright (c) 2011-2014 Twitter, Inc
	 * @license The MIT License
	 */

	// var toggle = '[data-am-dropdown] > .am-dropdown-toggle';

	var Dropdown = function(element, options) {
	  this.options = $.extend({}, Dropdown.DEFAULTS, options);

	  options = this.options;

	  this.$element = $(element);
	  this.$toggle = this.$element.find(options.selector.toggle);
	  this.$dropdown = this.$element.find(options.selector.dropdown);
	  this.$boundary = (options.boundary === window) ? $(window) :
	    this.$element.closest(options.boundary);
	  this.$justify = (options.justify && $(options.justify).length &&
	  $(options.justify)) || undefined;

	  !this.$boundary.length && (this.$boundary = $(window));

	  this.active = this.$element.hasClass('am-active') ? true : false;
	  this.animating = null;

	  this.events();
	};

	Dropdown.DEFAULTS = {
	  animation: 'am-animation-slide-top-fixed',
	  boundary: window,
	  justify: undefined,
	  selector: {
	    dropdown: '.am-dropdown-content',
	    toggle: '.am-dropdown-toggle'
	  },
	  trigger: 'click'
	};

	Dropdown.prototype.toggle = function() {
	  this.clear();

	  if (this.animating) {
	    return;
	  }

	  this[this.active ? 'close' : 'open']();
	};

	Dropdown.prototype.open = function(e) {
	  var $toggle = this.$toggle;
	  var $element = this.$element;
	  var $dropdown = this.$dropdown;

	  if ($toggle.is('.am-disabled, :disabled')) {
	    return;
	  }

	  if (this.active) {
	    return;
	  }

	  $element.trigger('open.dropdown.amui').addClass('am-active');

	  $toggle.trigger('focus');

	  this.checkDimensions(e);

	  var complete = $.proxy(function() {
	    $element.trigger('opened.dropdown.amui');
	    this.active = true;
	    this.animating = 0;
	  }, this);

	  if (animation) {
	    this.animating = 1;
	    $dropdown.addClass(this.options.animation).
	      on(animation.end + '.open.dropdown.amui', $.proxy(function() {
	        complete();
	        $dropdown.removeClass(this.options.animation);
	      }, this));
	  } else {
	    complete();
	  }
	};

	Dropdown.prototype.close = function() {
	  if (!this.active) {
	    return;
	  }

	  // fix #165
	  // var animationName = this.options.animation + ' am-animation-reverse';
	  var animationName = 'am-dropdown-animation';
	  var $element = this.$element;
	  var $dropdown = this.$dropdown;

	  $element.trigger('close.dropdown.amui');

	  var complete = $.proxy(function complete() {
	    $element.
	      removeClass('am-active').
	      trigger('closed.dropdown.amui');
	    this.active = false;
	    this.animating = 0;
	    this.$toggle.blur();
	  }, this);

	  if (animation) {
	    $dropdown.removeClass(this.options.animation);
	    $dropdown.addClass(animationName);
	    this.animating = 1;
	    // animation
	    $dropdown.one(animation.end + '.close.dropdown.amui', function() {
	      $dropdown.removeClass(animationName);
	      complete();
	    });
	  } else {
	    complete();
	  }
	};

	Dropdown.prototype.enable = function() {
	  this.$toggle.prop('disabled', false);
	},

	Dropdown.prototype.disable = function() {
	  this.$toggle.prop('disabled', true);
	},

	Dropdown.prototype.checkDimensions = function(e) {
	  if (!this.$dropdown.length) {
	    return;
	  }

	  var $dropdown = this.$dropdown;

	  // @see #873
	  if (e && e.offset) {
	    $dropdown.offset(e.offset);
	  }

	  var offset = $dropdown.offset();
	  var width = $dropdown.outerWidth();
	  var boundaryWidth = this.$boundary.width();
	  var boundaryOffset = $.isWindow(this.boundary) && this.$boundary.offset() ?
	    this.$boundary.offset().left : 0;

	  if (this.$justify) {
	    // jQuery.fn.width() is really...
	    $dropdown.css({'min-width': this.$justify.css('width')});
	  }

	  if ((width + (offset.left - boundaryOffset)) > boundaryWidth) {
	    this.$element.addClass('am-dropdown-flip');
	  }
	};

	Dropdown.prototype.clear = function() {
	  $('[data-am-dropdown]').not(this.$element).each(function() {
	    var data = $(this).data('amui.dropdown');
	    data && data.close();
	  });
	};

	Dropdown.prototype.events = function() {
	  var eventNS = 'dropdown.amui';
	  var triggers = this.options.trigger.split(' ');
	  var $toggle = this.$toggle;

	  $toggle.on('click.' + eventNS, $.proxy(function(e) {
	    e.preventDefault();
	    this.toggle();
	  }, this));

	  for (var i = triggers.length; i--;) {
	    var trigger = triggers[i];

	    if (trigger === 'click') {
	      $toggle.on('click.' + eventNS, $.proxy(this.toggle, this))
	    }

	    if (trigger === 'focus' || trigger === 'hover') {
	      var eventIn  = trigger == 'hover' ? 'mouseenter' : 'focusin';
	      var eventOut = trigger == 'hover' ? 'mouseleave' : 'focusout';

	      this.$element.on(eventIn + '.' + eventNS, $.proxy(this.open, this)).on(eventOut + '.' + eventNS, $.proxy(this.close, this));
	    }
	   };

	  $(document).on('keydown.dropdown.amui', $.proxy(function(e) {
	    e.keyCode === 27 && this.active && this.close();
	  }, this)).on('click.outer.dropdown.amui', $.proxy(function(e) {
	    // var $target = $(e.target);

	    if (this.active &&
	      (this.$element[0] === e.target || !this.$element.find(e.target).length)) {
	      this.close();
	    }
	  }, this));
	};

	// Dropdown Plugin
	UI.plugin('dropdown', Dropdown);

	// Init code
	UI.ready(function(context) {
	  $('[data-am-dropdown]', context).dropdown();
	});

	$(document).on('click.dropdown.amui.data-api', '.am-dropdown form',
	  function(e) {
	    e.stopPropagation();
	  });

	module.exports = UI.dropdown = Dropdown;

	// TODO: 1. 处理链接 focus
	//       2. 增加 mouseenter / mouseleave 选项
	//       3. 宽度适应


/***/ },
/* 11 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	var InputNumber = function(element, options) {
	  this.options = $.extend({}, InputNumber.DEFAULTS, options);

	  options = this.options;

	  this.$element = $(element);

	  this.init();
	};

	InputNumber.DEFAULTS = {
	  set: 1,
	  min: 0,
	  max: Infinity,
	  step: 1,
	  size: 148,
	  disabled: false,
	  controls: true
	};

	InputNumber.prototype.init = function(){
	  // Input value
	  this.set();
	  this.$element.wrap('<div class="am-inputnumber-wrap"></div>');
	  this.$elementWrap = this.$element.parent('.am-inputnumber-wrap');

	  // Inputnumber Size
	  this.size();

	  // Click Increase Number
	  this.increase();

	  // Click Decrease Number
	  this.decrease();

	  // CheckVlues Is Max
	  this.checkValue();

	  // Disabled Input-Number
	  if (this.options.disabled ||  this.$element.attr('disabled')){
	    this.$elementWrap.addClass('am-disabled');
	    this.$elementWrap.children().off();
	    this.$element.attr('disabled', 'disabled');
	  }
	};

	// Inputnumber Get
	InputNumber.prototype.get = function(){
	  return this.$element.val();
	};

	// Inputnumber Set
	InputNumber.prototype.set = function(value){
	  var content = value || this.options.set;
	  this.$element.val(content);
	};

	// Inputnumber Size
	InputNumber.prototype.size = function(){
	  var _this = this;

	  if (_this.options.size < 0){
	    return false;
	  }

	  if (_this.options.size != 148){
	    this.$elementWrap.css('width', _this.options.size);
	  }
	};

	// Inputnumber Increase
	InputNumber.prototype.increase = function(){
	  var _this = this;

	  // Is Controls
	  _this.controls();

	  _this.$elementWrap
	  .prepend('<span class="am-inputnumber-increase am-icon-plus"></span>')
	  .find('.am-inputnumber-increase').on('click',function(){
	    var inputValue = _this.count('+');

	    if (inputValue <= _this.options.max){
	      _this.$element.val(inputValue);
	      $(this).siblings().removeClass('am-disabled');

	      if (inputValue + _this.options.step > _this.options.max){
	        $(this).addClass('am-disabled');
	      }
	    } else {
	      return;
	    };
	  });
	};

	// Inputnumber Decrease
	InputNumber.prototype.decrease = function(){
	  var _this = this;

	  // Is Controls
	  _this.controls();

	  _this.$elementWrap
	  .prepend('<span class="am-inputnumber-decrease am-icon-minus"></span>')
	  .find('.am-inputnumber-decrease').on('click',function(){
	    var inputValue = _this.count('-');

	    if (inputValue >= _this.options.min){
	      _this.$element.val(inputValue);
	      $(this).siblings().removeClass('am-disabled');

	      if (inputValue - _this.options.step < _this.options.min){
	        $(this).addClass('am-disabled');
	      }
	    } else {
	      return;
	    };
	  });
	};

	InputNumber.prototype.controls = function(){
	  if (!this.options.controls){
	    return false;
	  }
	};

	InputNumber.prototype.checkValue = function(){
	  var _this = this;

	  _this.$element.on('input', function(){
	    if ($(this).val() > _this.options.max) {
	      _this.set(_this.options.max);
	    }
	  });
	};

	InputNumber.prototype.count = function(type){
	  var inputValue = Number(this.$element.val()) * 10;
	  var optionsValue = this.options.step * 10;

	  if (type == '+'){
	    return (inputValue + optionsValue) / 10;
	  } else if (type == '-'){
	    return (inputValue - optionsValue) / 10;
	  }
	};

	// Inputnumber Plugin
	UI.plugin('inputnumber', InputNumber);

	// Init code
	UI.ready(function(context) {
	  $('[data-am-inputnumber]', context).inputnumber();
	});




/***/ },
/* 12 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	var dimmer = __webpack_require__(9);
	var $doc = $(document);
	var supportTransition = UI.support.transition;

	/**
	 * @reference https://github.com/nolimits4web/Framework7/blob/master/src/js/modals.js
	 * @license https://github.com/nolimits4web/Framework7/blob/master/LICENSE
	 */

	var Modal = function(element, options) {
	  this.options = $.extend({}, Modal.DEFAULTS, options || {});
	  this.$element = $(element);
	  this.$dialog = this.$element.find('.am-modal-dialog');

	  if (!this.$element.attr('id')) {
	    this.$element.attr('id', UI.utils.generateGUID('am-modal'));
	  }

	  this.isPopup = this.$element.hasClass('am-popup');
	  this.isActions = this.$element.hasClass('am-modal-actions');
	  this.isPrompt = this.$element.hasClass('am-modal-prompt');
	  this.isLoading = this.$element.hasClass('am-modal-loading');
	  this.active = this.transitioning = this.relatedTarget = null;
	  this.dimmer = this.options.dimmer ? dimmer : {
	    open: function() {
	    },
	    close: function() {
	    }
	  };

	  this.events();
	};

	Modal.DEFAULTS = {
	  className: {
	    active: 'am-modal-active',
	    out: 'am-modal-out'
	  },
	  selector: {
	    modal: '.am-modal',
	    active: '.am-modal-active'
	  },
	  closeViaDimmer: true,
	  cancelable: true,
	  onConfirm: function() {
	  },
	  onCancel: function() {
	  },
	  closeOnCancel: true,
	  closeOnConfirm: true,
	  dimmer: true,
	  height: undefined,
	  width: undefined,
	  duration: 200, // must equal the CSS transition duration
	  transitionEnd: supportTransition && supportTransition.end + '.modal.amui'
	};

	Modal.prototype.toggle = function(relatedTarget) {
	  return this.active ? this.close() : this.open(relatedTarget);
	};

	Modal.prototype.open = function(relatedTarget) {
	  var $element = this.$element;
	  var options = this.options;
	  var isPopup = this.isPopup;
	  var width = options.width;
	  var height = options.height;
	  var style = {};

	  if (this.active) {
	    return;
	  }

	  if (!this.$element.length) {
	    return;
	  }

	  // callback hook
	  relatedTarget && (this.relatedTarget = relatedTarget);

	  // 判断如果还在动画，就先触发之前的closed事件
	  if (this.transitioning) {
	    clearTimeout($element.transitionEndTimmer);
	    $element.transitionEndTimmer = null;
	    $element.trigger(options.transitionEnd)
	      .off(options.transitionEnd);
	  }

	  isPopup && this.$element.show();

	  this.active = true;

	  $element.trigger($.Event('open.modal.amui', {relatedTarget: relatedTarget}));

	  this.dimmer.open($element);

	  $element.show().redraw();

	  // apply Modal width/height if set
	  if (!isPopup && !this.isActions) {
	    if (width) {
	      style.width = parseInt(width, 10) + 'px';
	    }

	    if (height) {
	      style.height = parseInt(height, 10) + 'px';
	    }

	    this.$dialog.css(style);
	  }

	  $element
	    .removeClass(options.className.out)
	    .addClass(options.className.active);

	  this.transitioning = 1;

	  var complete = function() {
	    $element.trigger($.Event('opened.modal.amui', {
	      relatedTarget: relatedTarget
	    }));
	    this.transitioning = 0;

	    // Prompt auto focus
	    if (this.isPrompt) {
	      this.$dialog.find('input').eq(0).focus();
	    }
	  };

	  if (!supportTransition) {
	    return complete.call(this);
	  }

	  $element
	    .one(options.transitionEnd, $.proxy(complete, this))
	    .emulateTransitionEnd(options.duration);
	};

	Modal.prototype.close = function(relatedTarget) {
	  if (!this.active) {
	    return;
	  }

	  var $element = this.$element;
	  var options = this.options;
	  var isPopup = this.isPopup;

	  // 判断如果还在动画，就先触发之前的opened事件
	  if (this.transitioning) {
	    clearTimeout($element.transitionEndTimmer);
	    $element.transitionEndTimmer = null;
	    $element.trigger(options.transitionEnd).off(options.transitionEnd);
	    this.dimmer.close($element, true);
	  }

	  this.$element.trigger($.Event('close.modal.amui', {
	    relatedTarget: relatedTarget
	  }));

	  this.transitioning = 1;

	  var complete = function() {
	    $element.trigger('closed.modal.amui');
	    isPopup && $element.removeClass(options.className.out);
	    $element.hide();
	    this.transitioning = 0;
	    this.active = false;
	  };

	  $element.removeClass(options.className.active)
	    .addClass(options.className.out);
	  // 不强制关闭 Dimmer，以便多个 Modal 可以共享 Dimmer
	  this.dimmer.close($element, false);

	  if (!supportTransition) {
	    return complete.call(this);
	  }

	  $element.one(options.transitionEnd, $.proxy(complete, this))
	    .emulateTransitionEnd(options.duration);
	};

	Modal.prototype.events = function() {
	  var _this = this;
	  var options = this.options;
	  var $element = this.$element;
	  var $dimmer = this.dimmer.$element;
	  var $ipt = $element.find('.am-modal-prompt-input');
	  var $confirm = $element.find('[data-am-modal-confirm]');
	  var $cancel = $element.find('[data-am-modal-cancel]');
	  var getData = function() {
	    var data = [];
	    $ipt.each(function() {
	      data.push($(this).val());
	    });

	    return (data.length === 0) ? undefined :
	      ((data.length === 1) ? data[0] : data);
	  };

	  // close via Esc key
	  if (this.options.cancelable) {
	    $element.on('keyup.modal.amui', function(e) {
	      if (_this.active && e.which === 27) {
	        $element.trigger('cancel.modal.amui');
	        _this.close();
	      }
	    });
	  }

	  // Close Modal when dimmer clicked
	  if (this.options.dimmer && this.options.closeViaDimmer && !this.isLoading) {
	    $dimmer.on('click.dimmer.modal.amui', function() {
	      _this.close();
	    });
	  }

	  // Close Modal when button clicked
	  $element.on(
	    'click.close.modal.amui',
	    '[data-am-modal-close], .am-modal-btn',
	    function(e) {
	      e.preventDefault();
	      var $this = $(this);

	      if ($this.is($confirm)) {
	        options.closeOnConfirm && _this.close();
	      } else if ($this.is($cancel)) {
	        options.closeOnCancel && _this.close();
	      } else {
	        _this.close();
	      }
	    }
	  )
	    // trigger dimmer click event if non-dialog area clicked
	    // fixes #882 caused by https://github.com/amazeui/amazeui/commit/b6be7719681193f1c4cb04af89cb9fd9f4422163
	    .on('click', function(e) {
	      // fixes #900
	      // e.stopPropagation();
	      $(e.target).is($element) && $dimmer.trigger('click.dimmer.modal.amui');
	    });

	  $confirm.on('click.confirm.modal.amui',
	    function() {
	      $element.trigger($.Event('confirm.modal.amui', {
	        trigger: this
	      }));
	    });

	  $cancel.on('click.cancel.modal.amui', function() {
	    $element.trigger($.Event('cancel.modal.amui', {
	      trigger: this
	    }));
	  });

	  $element.on('confirm.modal.amui', function(e) {
	    e.data = getData();
	    _this.options.onConfirm.call(_this, e);
	  }).on('cancel.modal.amui', function(e) {
	    e.data = getData();
	    _this.options.onCancel.call(_this, e);
	  });
	};

	function Plugin(option, relatedTarget) {
	  return this.each(function() {
	    var $this = $(this);
	    var data = $this.data('amui.modal');
	    var options = typeof option == 'object' && option;

	    if (!data) {
	      $this.data('amui.modal', (data = new Modal(this, options)));
	    }

	    if (typeof option == 'string') {
	      data[option] && data[option](relatedTarget);
	    } else {
	      data.toggle(option && option.relatedTarget || undefined);
	    }
	  });
	}

	$.fn.modal = Plugin;

	// Init
	$doc.on('click.modal.amui.data-api', '[data-am-modal]', function() {
	  var $this = $(this);
	  var options = UI.utils.parseOptions($this.attr('data-am-modal'));
	  var $target = $(options.target ||
	    (this.href && this.href.replace(/.*(?=#[^\s]+$)/, '')));
	  var option = $target.data('amui.modal') ? 'toggle' : options;

	  Plugin.call($target, option, this);
	});

	module.exports = UI.modal = Modal;


/***/ },
/* 13 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	__webpack_require__(3);

	var $win = $(window);
	var $doc = $(document);
	var scrollPos;

	/**
	 * @via https://github.com/uikit/uikit/blob/master/src/js/offcanvas.js
	 * @license https://github.com/uikit/uikit/blob/master/LICENSE.md
	 */

	var OffCanvas = function(element, options) {
	  this.$element = $(element);
	  this.options = $.extend({}, OffCanvas.DEFAULTS, options);
	  this.active = null;
	  this.bindEvents();
	};

	OffCanvas.DEFAULTS = {
	  duration: 300,
	  effect: 'overlay' // {push|overlay}, push is too expensive
	};

	OffCanvas.prototype.open = function(relatedElement) {
	  var _this = this;
	  var $element = this.$element;

	  if (!$element.length || $element.hasClass('am-active')) {
	    return;
	  }

	  var effect = this.options.effect;
	  var $html = $('html');
	  var $body = $('body');
	  var $bar = $element.find('.am-offcanvas-bar').first();
	  var dir = $bar.hasClass('am-offcanvas-bar-flip') ? -1 : 1;

	  $bar.addClass('am-offcanvas-bar-' + effect);

	  scrollPos = {x: window.scrollX, y: window.scrollY};

	  $element.addClass('am-active');

	  $body.css({
	    width: window.innerWidth,
	    height: $win.height()
	  }).addClass('am-offcanvas-page');

	  if (effect !== 'overlay') {
	    $body.css({
	      'margin-left': $bar.outerWidth() * dir
	    }).width(); // force redraw
	  }

	  $html.css('margin-top', scrollPos.y * -1);

	  setTimeout(function() {
	    $bar.addClass('am-offcanvas-bar-active').width();
	  }, 0);

	  $element.trigger('open.offcanvas.amui');

	  this.active = 1;

	  // Close OffCanvas when none content area clicked
	  $element.on('click.offcanvas.amui', function(e) {
	    var $target = $(e.target);

	    if ($target.hasClass('am-offcanvas-bar')) {
	      return;
	    }

	    if ($target.parents('.am-offcanvas-bar').first().length) {
	      return;
	    }

	    // https://developer.mozilla.org/zh-CN/docs/DOM/event.stopImmediatePropagation
	    e.stopImmediatePropagation();

	    _this.close();
	  });

	  $html.on('keydown.offcanvas.amui', function(e) {
	    (e.keyCode === 27) && _this.close();
	  });
	};

	OffCanvas.prototype.close = function(relatedElement) {
	  var _this = this;
	  var $html = $('html');
	  var $body = $('body');
	  var $element = this.$element;
	  var $bar = $element.find('.am-offcanvas-bar').first();

	  if (!$element.length || !this.active || !$element.hasClass('am-active')) {
	    return;
	  }

	  $element.trigger('close.offcanvas.amui');

	  function complete() {
	    $body
	      .removeClass('am-offcanvas-page')
	      .css({
	        width: '',
	        height: '',
	        'margin-left': '',
	        'margin-right': ''
	      });
	    $element.removeClass('am-active');
	    $bar.removeClass('am-offcanvas-bar-active');
	    $html.css('margin-top', '');
	    window.scrollTo(scrollPos.x, scrollPos.y);
	    $element.trigger('closed.offcanvas.amui');
	    _this.active = 0;
	  }

	  if (UI.support.transition) {
	    setTimeout(function() {
	      $bar.removeClass('am-offcanvas-bar-active');
	    }, 0);

	    $body.css('margin-left', '').one(UI.support.transition.end, function() {
	      complete();
	    }).emulateTransitionEnd(this.options.duration);
	  } else {
	    complete();
	  }

	  $element.off('click.offcanvas.amui');
	  $html.off('.offcanvas.amui');
	};

	OffCanvas.prototype.bindEvents = function() {
	  var _this = this;
	  $doc.on('click.offcanvas.amui', '[data-am-dismiss="offcanvas"]', function(e) {
	      e.preventDefault();
	      _this.close();
	    });

	  $win.on('resize.offcanvas.amui orientationchange.offcanvas.amui',
	    function() {
	      _this.active && _this.close();
	    });

	  this.$element.hammer().on('swipeleft swipeleft', function(e) {
	    e.preventDefault();
	    _this.close();
	  });

	  return this;
	};

	function Plugin(option, relatedElement) {
	  var args = Array.prototype.slice.call(arguments, 1);

	  return this.each(function() {
	    var $this = $(this);
	    var data = $this.data('amui.offcanvas');
	    var options = $.extend({}, typeof option == 'object' && option);

	    if (!data) {
	      $this.data('amui.offcanvas', (data = new OffCanvas(this, options)));
	      (!option || typeof option == 'object') && data.open(relatedElement);
	    }

	    if (typeof option == 'string') {
	      data[option] && data[option].apply(data, args);
	    }
	  });
	}

	$.fn.offCanvas = Plugin;

	// Init code
	$doc.on('click.offcanvas.amui', '[data-am-offcanvas]', function(e) {
	  e.preventDefault();
	  var $this = $(this);
	  var options = UI.utils.parseOptions($this.data('amOffcanvas'));
	  var $target = $(options.target ||
	  (this.href && this.href.replace(/.*(?=#[^\s]+$)/, '')));
	  var option = $target.data('amui.offcanvas') ? 'open' : options;

	  Plugin.call($target, option, this);
	});

	module.exports = UI.offcanvas = OffCanvas;

	// TODO: 优化动画效果
	// http://dbushell.github.io/Responsive-Off-Canvas-Menu/step4.html


/***/ },
/* 14 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	var requestAnimationFrame = UI.utils.rAF;

	/**
	 * @via https://github.com/manuelstofer/pinchzoom/blob/master/src/pinchzoom.js
	 * @license the MIT License.
	 */

	var definePinchZoom = function($) {

	  /**
	   * Pinch zoom using jQuery
	   * @version 0.0.2
	   * @author Manuel Stofer <mst@rtp.ch>
	   * @param el
	   * @param options
	   * @constructor
	   */
	  var PinchZoom = function(el, options) {
	      this.el = $(el);
	      this.zoomFactor = 1;
	      this.lastScale = 1;
	      this.offset = {
	        x: 0,
	        y: 0
	      };
	      this.options = $.extend({}, this.defaults, options);
	      this.setupMarkup();
	      this.bindEvents();
	      this.update();
	      // default enable.
	      this.enable();

	    },
	    sum = function(a, b) {
	      return a + b;
	    },
	    isCloseTo = function(value, expected) {
	      return value > expected - 0.01 && value < expected + 0.01;
	    };

	  PinchZoom.prototype = {

	    defaults: {
	      tapZoomFactor: 2,
	      zoomOutFactor: 1.3,
	      animationDuration: 300,
	      maxZoom: 4,
	      minZoom: 0.5,
	      lockDragAxis: false,
	      use2d: true,
	      zoomStartEventName: 'pz_zoomstart',
	      zoomEndEventName: 'pz_zoomend',
	      dragStartEventName: 'pz_dragstart',
	      dragEndEventName: 'pz_dragend',
	      doubleTapEventName: 'pz_doubletap'
	    },

	    /**
	     * Event handler for 'dragstart'
	     * @param event
	     */
	    handleDragStart: function(event) {
	      this.el.trigger(this.options.dragStartEventName);
	      this.stopAnimation();
	      this.lastDragPosition = false;
	      this.hasInteraction = true;
	      this.handleDrag(event);
	    },

	    /**
	     * Event handler for 'drag'
	     * @param event
	     */
	    handleDrag: function(event) {

	      if (this.zoomFactor > 1.0) {
	        var touch = this.getTouches(event)[0];
	        this.drag(touch, this.lastDragPosition);
	        this.offset = this.sanitizeOffset(this.offset);
	        this.lastDragPosition = touch;
	      }
	    },

	    handleDragEnd: function() {
	      this.el.trigger(this.options.dragEndEventName);
	      this.end();
	    },

	    /**
	     * Event handler for 'zoomstart'
	     * @param event
	     */
	    handleZoomStart: function(event) {
	      this.el.trigger(this.options.zoomStartEventName);
	      this.stopAnimation();
	      this.lastScale = 1;
	      this.nthZoom = 0;
	      this.lastZoomCenter = false;
	      this.hasInteraction = true;
	    },

	    /**
	     * Event handler for 'zoom'
	     * @param event
	     */
	    handleZoom: function(event, newScale) {

	      // a relative scale factor is used
	      var touchCenter = this.getTouchCenter(this.getTouches(event)),
	        scale = newScale / this.lastScale;
	      this.lastScale = newScale;

	      // the first touch events are thrown away since they are not precise
	      this.nthZoom += 1;
	      if (this.nthZoom > 3) {

	        this.scale(scale, touchCenter);
	        this.drag(touchCenter, this.lastZoomCenter);
	      }
	      this.lastZoomCenter = touchCenter;
	    },

	    handleZoomEnd: function() {
	      this.el.trigger(this.options.zoomEndEventName);
	      this.end();
	    },

	    /**
	     * Event handler for 'doubletap'
	     * @param event
	     */
	    handleDoubleTap: function(event) {
	      var center = this.getTouches(event)[0],
	        zoomFactor = this.zoomFactor > 1 ? 1 : this.options.tapZoomFactor,
	        startZoomFactor = this.zoomFactor,
	        updateProgress = (function(progress) {
	          this.scaleTo(startZoomFactor + progress * (zoomFactor - startZoomFactor), center);
	        }).bind(this);

	      if (this.hasInteraction) {
	        return;
	      }
	      if (startZoomFactor > zoomFactor) {
	        center = this.getCurrentZoomCenter();
	      }

	      this.animate(this.options.animationDuration, updateProgress, this.swing);
	      this.el.trigger(this.options.doubleTapEventName);
	    },

	    /**
	     * Max / min values for the offset
	     * @param offset
	     * @return {Object} the sanitized offset
	     */
	    sanitizeOffset: function(offset) {
	      var maxX = (this.zoomFactor - 1) * this.getContainerX(),
	        maxY = (this.zoomFactor - 1) * this.getContainerY(),
	        maxOffsetX = Math.max(maxX, 0),
	        maxOffsetY = Math.max(maxY, 0),
	        minOffsetX = Math.min(maxX, 0),
	        minOffsetY = Math.min(maxY, 0);

	      return {
	        x: Math.min(Math.max(offset.x, minOffsetX), maxOffsetX),
	        y: Math.min(Math.max(offset.y, minOffsetY), maxOffsetY)
	      };
	    },

	    /**
	     * Scale to a specific zoom factor (not relative)
	     * @param zoomFactor
	     * @param center
	     */
	    scaleTo: function(zoomFactor, center) {
	      this.scale(zoomFactor / this.zoomFactor, center);
	    },

	    /**
	     * Scales the element from specified center
	     * @param scale
	     * @param center
	     */
	    scale: function(scale, center) {
	      scale = this.scaleZoomFactor(scale);
	      this.addOffset({
	        x: (scale - 1) * (center.x + this.offset.x),
	        y: (scale - 1) * (center.y + this.offset.y)
	      });
	    },

	    /**
	     * Scales the zoom factor relative to current state
	     * @param scale
	     * @return the actual scale (can differ because of max min zoom factor)
	     */
	    scaleZoomFactor: function(scale) {
	      var originalZoomFactor = this.zoomFactor;
	      this.zoomFactor *= scale;
	      this.zoomFactor = Math.min(this.options.maxZoom, Math.max(this.zoomFactor, this.options.minZoom));
	      return this.zoomFactor / originalZoomFactor;
	    },

	    /**
	     * Drags the element
	     * @param center
	     * @param lastCenter
	     */
	    drag: function(center, lastCenter) {
	      if (lastCenter) {
	        if (this.options.lockDragAxis) {
	          // lock scroll to position that was changed the most
	          if (Math.abs(center.x - lastCenter.x) > Math.abs(center.y - lastCenter.y)) {
	            this.addOffset({
	              x: -(center.x - lastCenter.x),
	              y: 0
	            });
	          }
	          else {
	            this.addOffset({
	              y: -(center.y - lastCenter.y),
	              x: 0
	            });
	          }
	        }
	        else {
	          this.addOffset({
	            y: -(center.y - lastCenter.y),
	            x: -(center.x - lastCenter.x)
	          });
	        }
	      }
	    },

	    /**
	     * Calculates the touch center of multiple touches
	     * @param touches
	     * @return {Object}
	     */
	    getTouchCenter: function(touches) {
	      return this.getVectorAvg(touches);
	    },

	    /**
	     * Calculates the average of multiple vectors (x, y values)
	     */
	    getVectorAvg: function(vectors) {
	      return {
	        x: vectors.map(function(v) {
	          return v.x;
	        }).reduce(sum) / vectors.length,
	        y: vectors.map(function(v) {
	          return v.y;
	        }).reduce(sum) / vectors.length
	      };
	    },

	    /**
	     * Adds an offset
	     * @param offset the offset to add
	     * @return return true when the offset change was accepted
	     */
	    addOffset: function(offset) {
	      this.offset = {
	        x: this.offset.x + offset.x,
	        y: this.offset.y + offset.y
	      };
	    },

	    sanitize: function() {
	      if (this.zoomFactor < this.options.zoomOutFactor) {
	        this.zoomOutAnimation();
	      } else if (this.isInsaneOffset(this.offset)) {
	        this.sanitizeOffsetAnimation();
	      }
	    },

	    /**
	     * Checks if the offset is ok with the current zoom factor
	     * @param offset
	     * @return {Boolean}
	     */
	    isInsaneOffset: function(offset) {
	      var sanitizedOffset = this.sanitizeOffset(offset);
	      return sanitizedOffset.x !== offset.x ||
	        sanitizedOffset.y !== offset.y;
	    },

	    /**
	     * Creates an animation moving to a sane offset
	     */
	    sanitizeOffsetAnimation: function() {
	      var targetOffset = this.sanitizeOffset(this.offset),
	        startOffset = {
	          x: this.offset.x,
	          y: this.offset.y
	        },
	        updateProgress = (function(progress) {
	          this.offset.x = startOffset.x + progress * (targetOffset.x - startOffset.x);
	          this.offset.y = startOffset.y + progress * (targetOffset.y - startOffset.y);
	          this.update();
	        }).bind(this);

	      this.animate(
	        this.options.animationDuration,
	        updateProgress,
	        this.swing
	      );
	    },

	    /**
	     * Zooms back to the original position,
	     * (no offset and zoom factor 1)
	     */
	    zoomOutAnimation: function() {
	      var startZoomFactor = this.zoomFactor,
	        zoomFactor = 1,
	        center = this.getCurrentZoomCenter(),
	        updateProgress = (function(progress) {
	          this.scaleTo(startZoomFactor + progress * (zoomFactor - startZoomFactor), center);
	        }).bind(this);

	      this.animate(
	        this.options.animationDuration,
	        updateProgress,
	        this.swing
	      );
	    },

	    /**
	     * Updates the aspect ratio
	     */
	    updateAspectRatio: function() {
	      this.setContainerY(this.getContainerX() / this.getAspectRatio());
	    },

	    /**
	     * Calculates the initial zoom factor (for the element to fit into the container)
	     * @return the initial zoom factor
	     */
	    getInitialZoomFactor: function() {
	      // use .offsetWidth instead of width()
	      // because jQuery-width() return the original width but Zepto-width() will calculate width with transform.
	      // the same as .height()
	      return this.container[0].offsetWidth / this.el[0].offsetWidth;
	    },

	    /**
	     * Calculates the aspect ratio of the element
	     * @return the aspect ratio
	     */
	    getAspectRatio: function() {
	      return this.el[0].offsetWidth / this.el[0].offsetHeight;
	    },

	    /**
	     * Calculates the virtual zoom center for the current offset and zoom factor
	     * (used for reverse zoom)
	     * @return {Object} the current zoom center
	     */
	    getCurrentZoomCenter: function() {

	      // uses following formula to calculate the zoom center x value
	      // offset_left / offset_right = zoomcenter_x / (container_x - zoomcenter_x)
	      var length = this.container[0].offsetWidth * this.zoomFactor,
	        offsetLeft = this.offset.x,
	        offsetRight = length - offsetLeft - this.container[0].offsetWidth,
	        widthOffsetRatio = offsetLeft / offsetRight,
	        centerX = widthOffsetRatio * this.container[0].offsetWidth / (widthOffsetRatio + 1),

	      // the same for the zoomcenter y
	        height = this.container[0].offsetHeight * this.zoomFactor,
	        offsetTop = this.offset.y,
	        offsetBottom = height - offsetTop - this.container[0].offsetHeight,
	        heightOffsetRatio = offsetTop / offsetBottom,
	        centerY = heightOffsetRatio * this.container[0].offsetHeight / (heightOffsetRatio + 1);

	      // prevents division by zero
	      if (offsetRight === 0) {
	        centerX = this.container[0].offsetWidth;
	      }
	      if (offsetBottom === 0) {
	        centerY = this.container[0].offsetHeight;
	      }

	      return {
	        x: centerX,
	        y: centerY
	      };
	    },

	    canDrag: function() {
	      return !isCloseTo(this.zoomFactor, 1);
	    },

	    /**
	     * Returns the touches of an event relative to the container offset
	     * @param event
	     * @return array touches
	     */
	    getTouches: function(event) {
	      var position = this.container.offset();
	      return Array.prototype.slice.call(event.touches).map(function(touch) {
	        return {
	          x: touch.pageX - position.left,
	          y: touch.pageY - position.top
	        };
	      });
	    },

	    /**
	     * Animation loop
	     * does not support simultaneous animations
	     * @param duration
	     * @param framefn
	     * @param timefn
	     * @param callback
	     */
	    animate: function(duration, framefn, timefn, callback) {
	      var startTime = new Date().getTime(),
	        renderFrame = (function() {
	          if (!this.inAnimation) {
	            return;
	          }
	          var frameTime = new Date().getTime() - startTime,
	            progress = frameTime / duration;
	          if (frameTime >= duration) {
	            framefn(1);
	            if (callback) {
	              callback();
	            }
	            this.update();
	            this.stopAnimation();
	            this.update();
	          } else {
	            if (timefn) {
	              progress = timefn(progress);
	            }
	            framefn(progress);
	            this.update();
	            requestAnimationFrame(renderFrame);
	          }
	        }).bind(this);
	      this.inAnimation = true;
	      requestAnimationFrame(renderFrame);
	    },

	    /**
	     * Stops the animation
	     */
	    stopAnimation: function() {
	      this.inAnimation = false;
	    },

	    /**
	     * Swing timing function for animations
	     * @param p
	     * @return {Number}
	     */
	    swing: function(p) {
	      return -Math.cos(p * Math.PI) / 2 + 0.5;
	    },

	    getContainerX: function() {
	      return this.container[0].offsetWidth;
	    },

	    getContainerY: function() {
	      return this.container[0].offsetHeight;
	    },

	    setContainerY: function(y) {
	      return this.container.height(y);
	    },

	    /**
	     * Creates the expected html structure
	     */
	    setupMarkup: function() {
	      this.container = $('<div class="pinch-zoom-container"></div>');
	      this.el.before(this.container);
	      this.container.append(this.el);

	      this.container.css({
	        'overflow': 'hidden',
	        'position': 'relative'
	      });

	      // Zepto doesn't recognize `webkitTransform..` style
	      this.el.css({
	        '-webkit-transform-origin': '0% 0%',
	        '-moz-transform-origin': '0% 0%',
	        '-ms-transform-origin': '0% 0%',
	        '-o-transform-origin': '0% 0%',
	        'transform-origin': '0% 0%',
	        'position': 'absolute'
	      });
	    },

	    end: function() {
	      this.hasInteraction = false;
	      this.sanitize();
	      this.update();
	    },

	    /**
	     * Binds all required event listeners
	     */
	    bindEvents: function() {
	      detectGestures(this.container.get(0), this);
	      // Zepto and jQuery both know about `on`
	      $(window).on('resize', this.update.bind(this));
	      $(this.el).find('img').on('load', this.update.bind(this));
	    },

	    /**
	     * Updates the css values according to the current zoom factor and offset
	     */
	    update: function() {

	      if (this.updatePlaned) {
	        return;
	      }
	      this.updatePlaned = true;

	      setTimeout((function() {
	        this.updatePlaned = false;
	        this.updateAspectRatio();

	        var zoomFactor = this.getInitialZoomFactor() * this.zoomFactor,
	          offsetX = -this.offset.x / zoomFactor,
	          offsetY = -this.offset.y / zoomFactor,
	          transform3d = 'scale3d(' + zoomFactor + ', ' + zoomFactor + ',1) ' +
	            'translate3d(' + offsetX + 'px,' + offsetY + 'px,0px)',
	          transform2d = 'scale(' + zoomFactor + ', ' + zoomFactor + ') ' +
	            'translate(' + offsetX + 'px,' + offsetY + 'px)',
	          removeClone = (function() {
	            if (this.clone) {
	              this.clone.remove();
	              delete this.clone;
	            }
	          }).bind(this);

	        // Scale 3d and translate3d are faster (at least on ios)
	        // but they also reduce the quality.
	        // PinchZoom uses the 3d transformations during interactions
	        // after interactions it falls back to 2d transformations
	        if (!this.options.use2d || this.hasInteraction || this.inAnimation) {
	          this.is3d = true;
	          removeClone();
	          this.el.css({
	            '-webkit-transform': transform3d,
	            '-o-transform': transform2d,
	            '-ms-transform': transform2d,
	            '-moz-transform': transform2d,
	            'transform': transform3d
	          });
	        } else {

	          // When changing from 3d to 2d transform webkit has some glitches.
	          // To avoid this, a copy of the 3d transformed element is displayed in the
	          // foreground while the element is converted from 3d to 2d transform
	          if (this.is3d) {
	            this.clone = this.el.clone();
	            this.clone.css('pointer-events', 'none');
	            this.clone.appendTo(this.container);
	            setTimeout(removeClone, 200);
	          }
	          this.el.css({
	            '-webkit-transform': transform2d,
	            '-o-transform': transform2d,
	            '-ms-transform': transform2d,
	            '-moz-transform': transform2d,
	            'transform': transform2d
	          });
	          this.is3d = false;
	        }
	      }).bind(this), 0);
	    },

	    /**
	     * Enables event handling for gestures
	     */
	    enable: function() {
	      this.enabled = true;
	    },

	    /**
	     * Disables event handling for gestures
	     */
	    disable: function() {
	      this.enabled = false;
	    }
	  };

	  var detectGestures = function(el, target) {
	    var interaction = null,
	      fingers = 0,
	      lastTouchStart = null,
	      startTouches = null,

	      setInteraction = function(newInteraction, event) {
	        if (interaction !== newInteraction) {

	          if (interaction && !newInteraction) {
	            switch (interaction) {
	              case "zoom":
	                target.handleZoomEnd(event);
	                break;
	              case 'drag':
	                target.handleDragEnd(event);
	                break;
	            }
	          }

	          switch (newInteraction) {
	            case 'zoom':
	              target.handleZoomStart(event);
	              break;
	            case 'drag':
	              target.handleDragStart(event);
	              break;
	          }
	        }
	        interaction = newInteraction;
	      },

	      updateInteraction = function(event) {
	        if (fingers === 2) {
	          setInteraction('zoom');
	        } else if (fingers === 1 && target.canDrag()) {
	          setInteraction('drag', event);
	        } else {
	          setInteraction(null, event);
	        }
	      },

	      targetTouches = function(touches) {
	        return Array.prototype.slice.call(touches).map(function(touch) {
	          return {
	            x: touch.pageX,
	            y: touch.pageY
	          };
	        });
	      },

	      getDistance = function(a, b) {
	        var x, y;
	        x = a.x - b.x;
	        y = a.y - b.y;
	        return Math.sqrt(x * x + y * y);
	      },

	      calculateScale = function(startTouches, endTouches) {
	        var startDistance = getDistance(startTouches[0], startTouches[1]),
	          endDistance = getDistance(endTouches[0], endTouches[1]);
	        return endDistance / startDistance;
	      },

	      cancelEvent = function(event) {
	        event.stopPropagation();
	        event.preventDefault();
	      },

	      detectDoubleTap = function(event) {
	        var time = (new Date()).getTime();

	        if (fingers > 1) {
	          lastTouchStart = null;
	        }

	        if (time - lastTouchStart < 300) {
	          cancelEvent(event);

	          target.handleDoubleTap(event);
	          switch (interaction) {
	            case 'zoom':
	              target.handleZoomEnd(event);
	              break;
	            case 'drag':
	              target.handleDragEnd(event);
	              break;
	          }
	        }

	        if (fingers === 1) {
	          lastTouchStart = time;
	        }
	      },
	      firstMove = true;

	    el.addEventListener('touchstart', function(event) {
	      if (target.enabled) {
	        firstMove = true;
	        fingers = event.touches.length;
	        detectDoubleTap(event);
	      }
	    });

	    el.addEventListener('touchmove', function(event) {
	      if (target.enabled) {
	        if (firstMove) {
	          updateInteraction(event);
	          if (interaction) {
	            cancelEvent(event);
	          }
	          startTouches = targetTouches(event.touches);
	        } else {
	          switch (interaction) {
	            case 'zoom':
	              target.handleZoom(event, calculateScale(startTouches, targetTouches(event.touches)));
	              break;
	            case 'drag':
	              target.handleDrag(event);
	              break;
	          }
	          if (interaction) {
	            cancelEvent(event);
	            target.update();
	          }
	        }

	        firstMove = false;
	      }
	    });

	    el.addEventListener('touchend', function(event) {
	      if (target.enabled) {
	        fingers = event.touches.length;
	        updateInteraction(event);
	      }
	    });
	  };

	  return PinchZoom;
	};

	module.exports = UI.pichzoom = definePinchZoom($);


/***/ },
/* 15 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	var $w = $(window);

	/**
	 * @reference https://github.com/nolimits4web/Framework7/blob/master/src/js/modals.js
	 * @license https://github.com/nolimits4web/Framework7/blob/master/LICENSE
	 */

	var Popover = function(element, options) {
	  this.options = $.extend({}, Popover.DEFAULTS, options);
	  this.$element = $(element);
	  this.active = null;
	  this.$popover = (this.options.target && $(this.options.target)) || null;

	  this.init();
	  this._bindEvents();
	};

	Popover.DEFAULTS = {
	  theme: null,
	  trigger: 'click',
	  content: '',
	  open: false,
	  target: null,
	  position: 'top',
	  tpl: '<div class="am-popover">' +
	    '<div class="am-popover-inner"></div>' +
	    '<div class="am-popover-caret"></div></div>'
	};

	Popover.prototype.init = function() {
	  var _this = this;
	  var $element = this.$element;
	  var $popover;

	  if (!this.options.target) {
	    this.$popover = this.getPopover();
	    this.setContent();
	  }

	  $popover = this.$popover;

	  $popover.appendTo($('body'));

	  this.sizePopover();

	  function sizePopover() {
	    _this.sizePopover();
	  }

	  // TODO: 监听页面内容变化，重新调整位置

	  $element.on('open.popover.amui', function() {
	    $(window).on('resize.popover.amui', UI.utils.debounce(sizePopover, 50));
	  });

	  $element.on('close.popover.amui', function() {
	    $(window).off('resize.popover.amui', sizePopover);
	  });

	  this.options.open && this.open();
	};

	Popover.prototype.sizePopover = function sizePopover() {
	  var $element = this.$element;
	  var $popover = this.$popover;

	  if (!$popover || !$popover.length) {
	    return;
	  }

	  var popWidth = $popover.outerWidth();
	  var popHeight = $popover.outerHeight();
	  var $popCaret = $popover.find('.am-popover-caret');
	  var popCaretSizeY = ($popCaret.outerWidth() / 2) || 8;
	  var popCaretSizeX = $popCaret.outerWidth() || 8;
	  // 取不到 $popCaret.outerHeight() 的值，所以直接加 8
	  var popTotalHeight = popHeight + 8; // $popCaret.outerHeight();

	  var triggerWidth = $element.outerWidth();
	  var triggerHeight = $element.outerHeight();
	  var triggerOffset = $element.offset();
	  var triggerRect = $element[0].getBoundingClientRect();

	  var winHeight = $w.height();
	  var winWidth = $w.width();
	  var popTop = 0;
	  var popLeft = 0;
	  var diff = 0;
	  var spacing = 2;
	  var popPosition = 'top';

	  $popover.css({left: '', top: ''}).removeClass('am-popover-left ' +
	  'am-popover-right am-popover-top am-popover-bottom');

	  if (this.options.position) {
	    popPosition = this.options.position;
	  }

	  // 边界处理
	  if (popTotalHeight > triggerRect.top && popPosition == 'top') {
	    popPosition = 'bottom';
	  }

	  if (popTotalHeight > winHeight - triggerRect.bottom && popPosition == 'bottom') {
	    popPosition = 'top';
	  }

	  if (popWidth + popCaretSizeX > triggerRect.left - 5 && popPosition == 'left') {
	    popPosition = 'right';
	  }

	  if (popWidth + popCaretSizeX > winWidth - triggerRect.left - 5 && popPosition == 'right') {
	    popPosition = 'left';
	  }

	  // 确定显示的位置
	  if (popPosition == 'top') {
	    popTop = triggerOffset.top - popTotalHeight - spacing;
	    popLeft = triggerWidth / 2 + triggerOffset.left - popWidth / 2;
	    $popover.addClass('am-popover-top');
	  }

	  if (popPosition == 'bottom') {
	    popTop = triggerOffset.top + triggerHeight + popCaretSizeY + spacing;
	    popLeft = triggerWidth / 2 + triggerOffset.left - popWidth / 2;
	    $popover.addClass('am-popover-bottom');
	  }

	  if (popPosition == 'left') {
	    popTop = triggerHeight / 2 + triggerOffset.top - popHeight / 2;
	    popLeft = triggerOffset.left - popWidth - popCaretSizeX;
	    $popover.addClass('am-popover-left');

	    if (popLeft + popWidth > winWidth) {
	      popLeft = winWidth - popWidth - 5;
	      $popover.removeClass('am-popover-left').addClass('am-popover-right');
	    }
	  }

	  if (popPosition == 'right') {
	    popTop = triggerHeight / 2 + triggerOffset.top - popHeight / 2;
	    popLeft = triggerOffset.left + triggerWidth + popCaretSizeX;
	    $popover.addClass('am-popover-right');
	  }
	  // Apply position style
	  $popover.css({top: popTop + 'px', left: popLeft + 'px'});
	};

	Popover.prototype.toggle = function() {
	  return this[this.active ? 'close' : 'open']();
	};

	Popover.prototype.open = function() {
	  var $popover = this.$popover;

	  this.$element.trigger('open.popover.amui');
	  this.sizePopover();
	  $popover.show().addClass('am-active');
	  this.active = true;
	};

	Popover.prototype.close = function() {
	  var $popover = this.$popover;

	  this.$element.trigger('close.popover.amui');

	  $popover
	    .removeClass('am-active')
	    .trigger('closed.popover.amui')
	    .hide();

	  this.active = false;
	};

	Popover.prototype.getPopover = function() {
	  var uid = UI.utils.generateGUID('am-popover');
	  var theme = [];

	  if (this.options.theme) {
	    $.each(this.options.theme.split(' '), function(i, item) {
	      theme.push('am-popover-' + $.trim(item));
	    });
	  }

	  return $(this.options.tpl).attr('id', uid).addClass(theme.join(' '));
	};

	Popover.prototype.setContent = function(content) {
	  content = content || this.options.content;
	  this.$popover && this.$popover.find('.am-popover-inner')
	    .empty().html(content);
	};

	Popover.prototype._bindEvents = function() {
	  var eventNS = 'popover.amui';
	  var triggers = this.options.trigger.split(' ');

	  for (var i = triggers.length; i--;) {
	    var trigger = triggers[i];

	    if (trigger === 'click') {
	      this.$element.on('click.' + eventNS, $.proxy(this.toggle, this));
	    } else { // hover or focus
	      var eventIn = trigger == 'hover' ? 'mouseenter' : 'focusin';
	      var eventOut = trigger == 'hover' ? 'mouseleave' : 'focusout';

	      this.$element.on(eventIn + '.' + eventNS, $.proxy(this.open, this));
	      this.$element.on(eventOut + '.' + eventNS, $.proxy(this.close, this));
	    }
	  }
	};

	Popover.prototype.destroy = function() {
	  this.$element.off('.popover.amui').removeData('amui.popover');
	  this.$popover.remove();
	};

	UI.plugin('popover', Popover);

	// Init code
	UI.ready(function(context) {
	  $('[data-am-popover]', context).popover();
	});

	module.exports = Popover;

	// TODO: 允许用户定义位置


/***/ },
/* 16 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var UI = __webpack_require__(2);

	var Progress = (function() {
	  /**
	   * NProgress (c) 2013, Rico Sta. Cruz
	   * @via http://ricostacruz.com/nprogress
	   */

	  var NProgress = {};

	  NProgress.version = '0.2.0';

	  var Settings = NProgress.settings = {
	    minimum: 0.08,
	    easing: 'ease',
	    positionUsing: '',
	    speed: 200,
	    trickle: true,
	    trickleRate: 0.02,
	    trickleSpeed: 800,
	    showSpinner: true,
	    parent: 'body',
	    barSelector: '[role="nprogress-bar"]',
	    spinnerSelector: '[role="nprogress-spinner"]',
	    template: '<div class="nprogress-bar" role="nprogress-bar">' +
	    '<div class="nprogress-peg"></div></div>' +
	    '<div class="nprogress-spinner" role="nprogress-spinner">' +
	    '<div class="nprogress-spinner-icon"></div></div>'
	  };

	  /**
	   * Updates configuration.
	   *
	   *     NProgress.configure({
	   *       minimum: 0.1
	   *     });
	   */
	  NProgress.configure = function(options) {
	    var key, value;
	    for (key in options) {
	      value = options[key];
	      if (value !== undefined && options.hasOwnProperty(key)) Settings[key] = value;
	    }

	    return this;
	  };

	  /**
	   * Last number.
	   */

	  NProgress.status = null;

	  /**
	   * Sets the progress bar status, where `n` is a number from `0.0` to `1.0`.
	   *
	   *     NProgress.set(0.4);
	   *     NProgress.set(1.0);
	   */

	  NProgress.set = function(n) {
	    var started = NProgress.isStarted();

	    n = clamp(n, Settings.minimum, 1);
	    NProgress.status = (n === 1 ? null : n);

	    var progress = NProgress.render(!started),
	      bar      = progress.querySelector(Settings.barSelector),
	      speed    = Settings.speed,
	      ease     = Settings.easing;

	    progress.offsetWidth; /* Repaint */

	    queue(function(next) {
	      // Set positionUsing if it hasn't already been set
	      if (Settings.positionUsing === '') Settings.positionUsing = NProgress.getPositioningCSS();

	      // Add transition
	      css(bar, barPositionCSS(n, speed, ease));

	      if (n === 1) {
	        // Fade out
	        css(progress, {
	          transition: 'none',
	          opacity: 1
	        });
	        progress.offsetWidth; /* Repaint */

	        setTimeout(function() {
	          css(progress, {
	            transition: 'all ' + speed + 'ms linear',
	            opacity: 0
	          });
	          setTimeout(function() {
	            NProgress.remove();
	            next();
	          }, speed);
	        }, speed);
	      } else {
	        setTimeout(next, speed);
	      }
	    });

	    return this;
	  };

	  NProgress.isStarted = function() {
	    return typeof NProgress.status === 'number';
	  };

	  /**
	   * Shows the progress bar.
	   * This is the same as setting the status to 0%, except that it doesn't go backwards.
	   *
	   *     NProgress.start();
	   *
	   */
	  NProgress.start = function() {
	    if (!NProgress.status) NProgress.set(0);

	    var work = function() {
	      setTimeout(function() {
	        if (!NProgress.status) return;
	        NProgress.trickle();
	        work();
	      }, Settings.trickleSpeed);
	    };

	    if (Settings.trickle) work();

	    return this;
	  };

	  /**
	   * Hides the progress bar.
	   * This is the *sort of* the same as setting the status to 100%, with the
	   * difference being `done()` makes some placebo effect of some realistic motion.
	   *
	   *     NProgress.done();
	   *
	   * If `true` is passed, it will show the progress bar even if its hidden.
	   *
	   *     NProgress.done(true);
	   */

	  NProgress.done = function(force) {
	    if (!force && !NProgress.status) return this;

	    return NProgress.inc(0.3 + 0.5 * Math.random()).set(1);
	  };

	  /**
	   * Increments by a random amount.
	   */

	  NProgress.inc = function(amount) {
	    var n = NProgress.status;

	    if (!n) {
	      return NProgress.start();
	    } else {
	      if (typeof amount !== 'number') {
	        amount = (1 - n) * clamp(Math.random() * n, 0.1, 0.95);
	      }

	      n = clamp(n + amount, 0, 0.994);
	      return NProgress.set(n);
	    }
	  };

	  NProgress.trickle = function() {
	    return NProgress.inc(Math.random() * Settings.trickleRate);
	  };

	  /**
	   * Waits for all supplied jQuery promises and
	   * increases the progress as the promises resolve.
	   *
	   * @param $promise jQUery Promise
	   */
	  (function() {
	    var initial = 0, current = 0;

	    NProgress.promise = function($promise) {
	      if (!$promise || $promise.state() === "resolved") {
	        return this;
	      }

	      if (current === 0) {
	        NProgress.start();
	      }

	      initial++;
	      current++;

	      $promise.always(function() {
	        current--;
	        if (current === 0) {
	          initial = 0;
	          NProgress.done();
	        } else {
	          NProgress.set((initial - current) / initial);
	        }
	      });

	      return this;
	    };

	  })();

	  /**
	   * (Internal) renders the progress bar markup based on the `template`
	   * setting.
	   */

	  NProgress.render = function(fromStart) {
	    if (NProgress.isRendered()) return document.getElementById('nprogress');

	    addClass(document.documentElement, 'nprogress-busy');

	    var progress = document.createElement('div');
	    progress.id = 'nprogress';
	    progress.innerHTML = Settings.template;

	    var bar      = progress.querySelector(Settings.barSelector),
	      perc     = fromStart ? '-100' : toBarPerc(NProgress.status || 0),
	      parent   = document.querySelector(Settings.parent),
	      spinner;

	    css(bar, {
	      transition: 'all 0 linear',
	      transform: 'translate3d(' + perc + '%,0,0)'
	    });

	    if (!Settings.showSpinner) {
	      spinner = progress.querySelector(Settings.spinnerSelector);
	      spinner && removeElement(spinner);
	    }

	    if (parent != document.body) {
	      addClass(parent, 'nprogress-custom-parent');
	    }

	    parent.appendChild(progress);
	    return progress;
	  };

	  /**
	   * Removes the element. Opposite of render().
	   */

	  NProgress.remove = function() {
	    removeClass(document.documentElement, 'nprogress-busy');
	    removeClass(document.querySelector(Settings.parent), 'nprogress-custom-parent');
	    var progress = document.getElementById('nprogress');
	    progress && removeElement(progress);
	  };

	  /**
	   * Checks if the progress bar is rendered.
	   */

	  NProgress.isRendered = function() {
	    return !!document.getElementById('nprogress');
	  };

	  /**
	   * Determine which positioning CSS rule to use.
	   */

	  NProgress.getPositioningCSS = function() {
	    // Sniff on document.body.style
	    var bodyStyle = document.body.style;

	    // Sniff prefixes
	    var vendorPrefix = ('WebkitTransform' in bodyStyle) ? 'Webkit' :
	      ('MozTransform' in bodyStyle) ? 'Moz' :
	        ('msTransform' in bodyStyle) ? 'ms' :
	          ('OTransform' in bodyStyle) ? 'O' : '';

	    if (vendorPrefix + 'Perspective' in bodyStyle) {
	      // Modern browsers with 3D support, e.g. Webkit, IE10
	      return 'translate3d';
	    } else if (vendorPrefix + 'Transform' in bodyStyle) {
	      // Browsers without 3D support, e.g. IE9
	      return 'translate';
	    } else {
	      // Browsers without translate() support, e.g. IE7-8
	      return 'margin';
	    }
	  };

	  /**
	   * Helpers
	   */

	  function clamp(n, min, max) {
	    if (n < min) return min;
	    if (n > max) return max;
	    return n;
	  }

	  /**
	   * (Internal) converts a percentage (`0..1`) to a bar translateX
	   * percentage (`-100%..0%`).
	   */

	  function toBarPerc(n) {
	    return (-1 + n) * 100;
	  }


	  /**
	   * (Internal) returns the correct CSS for changing the bar's
	   * position given an n percentage, and speed and ease from Settings
	   */

	  function barPositionCSS(n, speed, ease) {
	    var barCSS;

	    if (Settings.positionUsing === 'translate3d') {
	      barCSS = { transform: 'translate3d('+toBarPerc(n)+'%,0,0)' };
	    } else if (Settings.positionUsing === 'translate') {
	      barCSS = { transform: 'translate('+toBarPerc(n)+'%,0)' };
	    } else {
	      barCSS = { 'margin-left': toBarPerc(n)+'%' };
	    }

	    barCSS.transition = 'all '+speed+'ms '+ease;

	    return barCSS;
	  }

	  /**
	   * (Internal) Queues a function to be executed.
	   */

	  var queue = (function() {
	    var pending = [];

	    function next() {
	      var fn = pending.shift();
	      if (fn) {
	        fn(next);
	      }
	    }

	    return function(fn) {
	      pending.push(fn);
	      if (pending.length == 1) next();
	    };
	  })();

	  /**
	   * (Internal) Applies css properties to an element, similar to the jQuery
	   * css method.
	   *
	   * While this helper does assist with vendor prefixed property names, it
	   * does not perform any manipulation of values prior to setting styles.
	   */

	  var css = (function() {
	    var cssPrefixes = [ 'Webkit', 'O', 'Moz', 'ms' ],
	      cssProps    = {};

	    function camelCase(string) {
	      return string.replace(/^-ms-/, 'ms-').replace(/-([\da-z])/gi, function(match, letter) {
	        return letter.toUpperCase();
	      });
	    }

	    function getVendorProp(name) {
	      var style = document.body.style;
	      if (name in style) return name;

	      var i = cssPrefixes.length,
	        capName = name.charAt(0).toUpperCase() + name.slice(1),
	        vendorName;
	      while (i--) {
	        vendorName = cssPrefixes[i] + capName;
	        if (vendorName in style) return vendorName;
	      }

	      return name;
	    }

	    function getStyleProp(name) {
	      name = camelCase(name);
	      return cssProps[name] || (cssProps[name] = getVendorProp(name));
	    }

	    function applyCss(element, prop, value) {
	      prop = getStyleProp(prop);
	      element.style[prop] = value;
	    }

	    return function(element, properties) {
	      var args = arguments,
	        prop,
	        value;

	      if (args.length == 2) {
	        for (prop in properties) {
	          value = properties[prop];
	          if (value !== undefined && properties.hasOwnProperty(prop)) applyCss(element, prop, value);
	        }
	      } else {
	        applyCss(element, args[1], args[2]);
	      }
	    }
	  })();

	  /**
	   * (Internal) Determines if an element or space separated list of class names contains a class name.
	   */

	  function hasClass(element, name) {
	    var list = typeof element == 'string' ? element : classList(element);
	    return list.indexOf(' ' + name + ' ') >= 0;
	  }

	  /**
	   * (Internal) Adds a class to an element.
	   */

	  function addClass(element, name) {
	    var oldList = classList(element),
	      newList = oldList + name;

	    if (hasClass(oldList, name)) return;

	    // Trim the opening space.
	    element.className = newList.substring(1);
	  }

	  /**
	   * (Internal) Removes a class from an element.
	   */

	  function removeClass(element, name) {
	    var oldList = classList(element),
	      newList;

	    if (!hasClass(element, name)) return;

	    // Replace the class name.
	    newList = oldList.replace(' ' + name + ' ', ' ');

	    // Trim the opening and closing spaces.
	    element.className = newList.substring(1, newList.length - 1);
	  }

	  /**
	   * (Internal) Gets a space separated list of the class names on the element.
	   * The list is wrapped with a single space on each end to facilitate finding
	   * matches within the list.
	   */

	  function classList(element) {
	    return (' ' + (element.className || '') + ' ').replace(/\s+/gi, ' ');
	  }

	  /**
	   * (Internal) Removes an element from the DOM.
	   */

	  function removeElement(element) {
	    element && element.parentNode && element.parentNode.removeChild(element);
	  }

	  return NProgress;
	})();

	module.exports = UI.progress = Progress;


/***/ },
/* 17 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	/**
	 * ProgressBar
	 */
	var ProgressBar = function(element, options) {
	  this.options = $.extend({}, ProgressBar.DEFAULTS, options);
	  this.$element = $(element);

	  this.init();
	};

	ProgressBar.DEFAULTS = {
	  width: '100%',
	  size: null, // String: sm
	  type: 'line', // String: line, circle
	  percentage: 0, // Number: 0 - 100
	  text: true,   // Boolean: show text, default true
	  textDes: '%', // String: text description
	  textInner: false, // String: text position
	  status: null, // String[set initial status]: success, warning, danger
	  iconDone: '<i class="am-icon-check am-icon-fw"></i>',
	  iconBreak: '<i class="am-icon-close am-icon-fw"></i>',
	};

	ProgressBar.prototype.init = function() {
	  var options = this.options;
	  this.type = options.type;
	  var $ele = this.$element;
	  var $outer = this.$outer = $('<div class="am-progress-outer"></div');
	  var $inner = this.$inner = $('<div class="am-progress-inner"></div');
	  var $bar = this.$bar = $('<div class="am-progress-bar"></div');
	  var $text = this.$text = $('<span class="am-progress-text"></span>');

	  if (this.type === 'circle') {
	    $ele.append(this.svg());
	    $ele.append(this.$text);

	    $ele.addClass('am-progress-svg');
	    this.$svgPath = $ele.find('.am-progress-svg-path');
	    this.width = 295; // use fixed perimeter

	  } else {
	    var $pb = $outer.append($inner.append($bar));
	    $ele.append($pb);
	    $ele.addClass('am-progress');
	    this.width = $inner.width();

	    if (!options.textInner) {
	      $ele.append($text);
	    } else {
	      $bar.append($text);

	      $outer.addClass('am-progress-outer-disable');
	      $ele.addClass('am-progress');
	    }
	  }

	  if (options.size)  $ele.addClass('am-progress-' + options.size);
	  if (options.status)  $ele.addClass('am-progress-' + options.status);
	  this.set(options.percentage);
	};

	ProgressBar.prototype.reinit = function() {
	  this.destory();
	  this.init();
	}

	/**
	 * [set percentage of progress]
	 * @param {[Number]} value [the percentage of progress width]
	 */
	ProgressBar.prototype.set = function(value) {
	  value = +value >=100 ? 100 : +value;

	  if (this.type === 'circle') {
	    var cir = (100 - value) / 100 * this.width;
	    this.$svgPath.css('strokeDashoffset', cir + 'px');
	  } else {
	    this.$bar.width(value + '%');
	  }

	  this.$text.html(value + '' + this.options.textDes);
	  value == 100 ? this.done() : '';
	};

	ProgressBar.prototype.step = function(value) {
	  var s = null;

	  if (this.type === 'circle') {
	    var w = this.width;
	    var curW = this.$svgPath.css('strokeDashoffset').slice(0,-2);
	    s = Math.round((w - curW) / w * 100 + value);
	  } else {
	    s = Math.round(this.$bar.width() * 100 / this.width + (+value || 2));
	  }

	  this.set(s);
	};

	ProgressBar.prototype.done = function() {
	  this.$text.html(this.options.iconDone);
	  this.$element.addClass('am-progress-success')
	};

	ProgressBar.prototype.break = function() {
	  this.$text.html(this.options.iconBreak);
	  this.$element.addClass('am-progress-danger');
	};

	ProgressBar.prototype.svg = function() {
	  // use fixed stroke-width and perimeter
	  this.$svg = $('<svg viewBox="0 0 100 100"><path d="M 50 50 m 0 -47 a 47 47 0 1 1 0 94 a 47 47 0 1 1 0 -94" stroke="#f0f0f0" stroke-width="6" fill="none"></path><path class="am-progress-svg-path" d="M 50 50 m 0 -47 a 47 47 0 1 1 0 94 a 47 47 0 1 1 0 -94" stroke-linecap="round" stroke-width="6" fill="none" style=" "></path></svg>');
	  return this.$svg;
	};

	// cal the perimeter based on stroke-width and viewbox
	// function perimeter(width) {
	//    return (50 - width/2) * Math.PI * 2;
	// }

	ProgressBar.prototype.destory = function() {
	  this.$text.remove();
	  if (this.$svg) {
	    this.$svg.remove()
	  };
	  this.$outer.remove();
	  this.$element.removeClass();
	};

	UI.plugin('progressbar', ProgressBar);

	UI.ready(function(context) {
	  $('[data-am-progressbar]', context).progressbar();
	});

	module.exports = ProgressBar;


/***/ },
/* 18 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	/**
	 * @via https://github.com/uikit/uikit/blob/master/src/js/scrollspy.js
	 * @license https://github.com/uikit/uikit/blob/master/LICENSE.md
	 */

	var ScrollSpy = function(element, options) {
	  if (!UI.support.animation) {
	    return;
	  }

	  this.options = $.extend({}, ScrollSpy.DEFAULTS, options);
	  this.$element = $(element);

	  var checkViewRAF = function() {
	    UI.utils.rAF.call(window, $.proxy(this.checkView, this));
	  }.bind(this);

	  this.$window = $(window).on('scroll.scrollspy.amui', checkViewRAF)
	    .on('resize.scrollspy.amui orientationchange.scrollspy.amui',
	    UI.utils.debounce(checkViewRAF, 50));

	  this.timer = this.inViewState = this.initInView = null;

	  checkViewRAF();
	};

	ScrollSpy.DEFAULTS = {
	  animation: 'fade',
	  className: {
	    inView: 'am-scrollspy-inview',
	    init: 'am-scrollspy-init'
	  },
	  repeat: true,
	  delay: 0,
	  topOffset: 0,
	  leftOffset: 0
	};

	ScrollSpy.prototype.checkView = function() {
	  var $element = this.$element;
	  var options = this.options;
	  var inView = UI.utils.isInView($element, options);
	  var animation = options.animation ?
	  ' am-animation-' + options.animation : '';

	  if (inView && !this.inViewState) {
	    if (this.timer) {
	      clearTimeout(this.timer);
	    }

	    if (!this.initInView) {
	      $element.addClass(options.className.init);
	      this.offset = $element.offset();
	      this.initInView = true;

	      $element.trigger('init.scrollspy.amui');
	    }

	    this.timer = setTimeout(function() {
	      if (inView) {
	        $element.addClass(options.className.inView + animation).width();
	      }
	    }, options.delay);

	    this.inViewState = true;
	    $element.trigger('inview.scrollspy.amui');
	  }

	  if (!inView && this.inViewState && options.repeat) {
	    $element.removeClass(options.className.inView + animation);

	    this.inViewState = false;

	    $element.trigger('outview.scrollspy.amui');
	  }
	};

	ScrollSpy.prototype.check = function() {
	  UI.utils.rAF.call(window, $.proxy(this.checkView, this));
	};

	// Sticky Plugin
	UI.plugin('scrollspy', ScrollSpy);

	// Init code
	UI.ready(function(context) {
	  $('[data-am-scrollspy]', context).scrollspy();
	});

	module.exports = ScrollSpy;


/***/ },
/* 19 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	__webpack_require__(20);

	/**
	 * @via https://github.com/uikit/uikit/
	 * @license https://github.com/uikit/uikit/blob/master/LICENSE.md
	 */

	// ScrollSpyNav Class
	var ScrollSpyNav = function(element, options) {
	  this.options = $.extend({}, ScrollSpyNav.DEFAULTS, options);
	  this.$element = $(element);
	  this.anchors = [];

	  this.$links = this.$element.find('a[href^="#"]').each(function(i, link) {
	    this.anchors.push($(link).attr('href'));
	  }.bind(this));

	  this.$targets = $(this.anchors.join(', '));

	  var processRAF = function() {
	    UI.utils.rAF.call(window, $.proxy(this.process, this));
	  }.bind(this);

	  this.$window = $(window).on('scroll.scrollspynav.amui', processRAF)
	    .on('resize.scrollspynav.amui orientationchange.scrollspynav.amui',
	    UI.utils.debounce(processRAF, 50));

	  processRAF();
	  this.scrollProcess();
	};

	ScrollSpyNav.DEFAULTS = {
	  className: {
	    active: 'am-active'
	  },
	  closest: false,
	  smooth: true,
	  offsetTop: 0
	};

	ScrollSpyNav.prototype.process = function() {
	  var scrollTop = this.$window.scrollTop();
	  var options = this.options;
	  var inViews = [];
	  var $links = this.$links;

	  var $targets = this.$targets;

	  $targets.each(function(i, target) {
	    if (UI.utils.isInView(target, options)) {
	      inViews.push(target);
	    }
	  });

	  // console.log(inViews.length);

	  if (inViews.length) {
	    var $target;

	    $.each(inViews, function(i, item) {
	      if ($(item).offset().top >= scrollTop) {
	        $target = $(item);
	        return false; // break
	      }
	    });

	    if (!$target) {
	      return;
	    }

	    if (options.closest) {
	      $links.closest(options.closest).removeClass(options.className.active);
	      $links.filter('a[href="#' + $target.attr('id') + '"]').
	        closest(options.closest).addClass(options.className.active);
	    } else {
	      $links.removeClass(options.className.active).
	        filter('a[href="#' + $target.attr('id') + '"]').
	        addClass(options.className.active);
	    }
	  }
	};

	ScrollSpyNav.prototype.scrollProcess = function() {
	  var $links = this.$links;
	  var options = this.options;

	  // smoothScroll
	  if (options.smooth && $.fn.smoothScroll) {
	    $links.on('click', function(e) {
	      e.preventDefault();

	      var $this = $(this);
	      var $target = $($this.attr('href'));

	      if (!$target) {
	        return;
	      }

	      var offsetTop = options.offsetTop &&
	        !isNaN(parseInt(options.offsetTop)) && parseInt(options.offsetTop) || 0;

	      $(window).smoothScroll({position: $target.offset().top - offsetTop});
	    });
	  }
	};

	// ScrollSpyNav Plugin
	UI.plugin('scrollspynav', ScrollSpyNav);

	// Init code
	UI.ready(function(context) {
	  $('[data-am-scrollspynav]', context).scrollspynav();
	});

	module.exports = ScrollSpyNav;

	// TODO: 1. 算法改进
	//       2. 多级菜单支持
	//       3. smooth scroll pushState


/***/ },
/* 20 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	var rAF = UI.utils.rAF;
	var cAF = UI.utils.cancelAF;

	/**
	 * Smooth Scroll
	 * @param position
	 * @via http://mir.aculo.us/2014/01/19/scrolling-dom-elements-to-the-top-a-zepto-plugin/
	 */

	// Usage: $(window).smoothScroll([options])

	// only allow one scroll to top operation to be in progress at a time,
	// which is probably what you want
	var smoothScrollInProgress = false;

	var SmoothScroll = function(element, options) {
	  options = options || {};

	  var $this = $(element);
	  var targetY = parseInt(options.position) || SmoothScroll.DEFAULTS.position;
	  var initialY = $this.scrollTop();
	  var lastY = initialY;
	  var delta = targetY - initialY;
	  // duration in ms, make it a bit shorter for short distances
	  // this is not scientific and you might want to adjust this for
	  // your preferences
	  var speed = options.speed ||
	      Math.min(750, Math.min(1500, Math.abs(initialY - targetY)));
	  // temp variables (t will be a position between 0 and 1, y is the calculated scrollTop)
	  var start;
	  var t;
	  var y;
	  var cancelScroll = function() {
	      abort();
	    };

	  // abort if already in progress or nothing to scroll
	  if (smoothScrollInProgress) {
	    return;
	  }

	  if (delta === 0) {
	    return;
	  }

	  // quint ease-in-out smoothing, from
	  // https://github.com/madrobby/scripty2/blob/master/src/effects/transitions/penner.js#L127-L136
	  function smooth(pos) {
	    if ((pos /= 0.5) < 1) {
	      return 0.5 * Math.pow(pos, 5);
	    }

	    return 0.5 * (Math.pow((pos - 2), 5) + 2);
	  }

	  function abort() {
	    $this.off('touchstart.smoothscroll.amui', cancelScroll);
	    smoothScrollInProgress = false;
	  }

	  // when there's a touch detected while scrolling is in progress, abort
	  // the scrolling (emulates native scrolling behavior)
	  $this.on('touchstart.smoothscroll.amui', cancelScroll);
	  smoothScrollInProgress = true;

	  // start rendering away! note the function given to frame
	  // is named "render" so we can reference it again further down
	  function render(now) {
	    if (!smoothScrollInProgress) {
	      return;
	    }
	    if (!start) {
	      start = now;
	    }

	    // calculate t, position of animation in [0..1]
	    t = Math.min(1, Math.max((now - start) / speed, 0));
	    // calculate the new scrollTop position (don't forget to smooth)
	    y = Math.round(initialY + delta * smooth(t));
	    // bracket scrollTop so we're never over-scrolling
	    if (delta > 0 && y > targetY) {
	      y = targetY;
	    }
	    if (delta < 0 && y < targetY) {
	      y = targetY;
	    }

	    // only actually set scrollTop if there was a change fromt he last frame
	    if (lastY != y) {
	      $this.scrollTop(y);
	    }

	    lastY = y;
	    // if we're not done yet, queue up an other frame to render,
	    // or clean up
	    if (y !== targetY) {
	      cAF(scrollRAF);
	      scrollRAF = rAF(render);
	    } else {
	      cAF(scrollRAF);
	      abort();
	    }
	  }

	  var scrollRAF = rAF(render);
	};

	SmoothScroll.DEFAULTS = {
	  position: 0
	};

	$.fn.smoothScroll = function(option) {
	  return this.each(function() {
	    new SmoothScroll(this, option);
	  });
	};

	// Init code
	$(document).on('click.smoothScroll.amui.data-api', '[data-am-smooth-scroll]',
	  function(e) {
	    e.preventDefault();
	    var options = UI.utils.parseOptions($(this).data('amSmoothScroll'));

	    $(window).smoothScroll(options);
	  });

	module.exports = SmoothScroll;


/***/ },
/* 21 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	// require('./ui.dropdown');

	// Make jQuery :contains Case-Insensitive
	$.expr[':'].containsNC = function(elem, i, match, array) {
	  return (elem.textContent || elem.innerText || '').toLowerCase().
	      indexOf((match[3] || '').toLowerCase()) >= 0;
	};

	/**
	 * Selected
	 * @desc HTML select replacer
	 * @via https://github.com/silviomoreto/bootstrap-select
	 * @license https://github.com/silviomoreto/bootstrap-select/blob/master/LICENSE
	 * @param element
	 * @param options
	 * @constructor
	 */

	var Selected = function(element, options) {
	  this.$element = $(element);
	  this.options = $.extend({}, Selected.DEFAULTS, {
	    placeholder: element.getAttribute('placeholder') ||
	    Selected.DEFAULTS.placeholder
	  }, options);
	  this.$originalOptions = this.$element.find('option');
	  this.multiple = element.multiple;
	  this.$selector = null;
	  this.initialized = false;
	  this.init();
	};

	Selected.DEFAULTS = {
	  btnWidth: null,
	  btnSize: null,
	  btnStyle: 'default',
	  dropUp: 0,
	  maxHeight: null,
	  maxChecked: null,
	  placeholder: '点击选择...',
	  selectedClass: 'am-checked',
	  disabledClass: 'am-disabled',
	  searchBox: false,
	  tpl: '<div class="am-selected am-dropdown ' +
	  '<%= dropUp ? \'am-dropdown-up\': \'\' %>" id="<%= id %>" data-am-dropdown>' +
	  '  <button type="button" class="am-selected-btn am-btn am-dropdown-toggle">' +
	  '    <span class="am-selected-status am-fl"></span>' +
	  '    <i class="am-selected-icon am-icon-caret-' +
	  '<%= dropUp ? \'up\' : \'down\' %>"></i>' +
	  '  </button>' +
	  '  <div class="am-selected-content am-dropdown-content">' +
	  '    <h2 class="am-selected-header">' +
	  '<span class="am-icon-chevron-left">返回</span></h2>' +
	  '   <% if (searchBox) { %>' +
	  '   <div class="am-selected-search">' +
	  '     <input autocomplete="off" class="am-form-field am-input-sm" />' +
	  '   </div>' +
	  '   <% } %>' +
	  '    <ul class="am-selected-list">' +
	  '      <% for (var i = 0; i < options.length; i++) { %>' +
	  '       <% var option = options[i] %>' +
	  '       <% if (option.header) { %>' +
	  '  <li data-group="<%= option.group %>" class="am-selected-list-header">' +
	  '       <%= option.text %></li>' +
	  '       <% } else { %>' +
	  '       <li class="<%= option.classNames%>" ' +
	  '         data-index="<%= option.index %>" ' +
	  '         data-group="<%= option.group || 0 %>" ' +
	  '         data-value="<%= option.value %>" >' +
	  '         <span class="am-selected-text"><%= option.text %></span>' +
	  '         <i class="am-icon-check"></i></li>' +
	  '      <% } %>' +
	  '      <% } %>' +
	  '    </ul>' +
	  '    <div class="am-selected-hint"></div>' +
	  '  </div>' +
	  '</div>',
	  listTpl:   '<% for (var i = 0; i < options.length; i++) { %>' +
	  '       <% var option = options[i] %>' +
	  '       <% if (option.header) { %>' +
	  '  <li data-group="<%= option.group %>" class="am-selected-list-header">' +
	  '       <%= option.text %></li>' +
	  '       <% } else { %>' +
	  '       <li class="<%= option.classNames %>" ' +
	  '         data-index="<%= option.index %>" ' +
	  '         data-group="<%= option.group || 0 %>" ' +
	  '         data-value="<%= option.value %>" >' +
	  '         <span class="am-selected-text"><%= option.text %></span>' +
	  '         <i class="am-icon-check"></i></li>' +
	  '      <% } %>' +
	  '      <% } %>'
	};

	Selected.prototype.init = function() {
	  var _this = this;
	  var $element = this.$element;
	  var options = this.options;

	  $element.hide();

	  var data = {
	    id: UI.utils.generateGUID('am-selected'),
	    multiple: this.multiple,
	    options: [],
	    searchBox: options.searchBox,
	    dropUp: options.dropUp,
	    placeholder: options.placeholder
	  };

	  this.$selector = $(UI.template(this.options.tpl, data));
	  // set select button styles
	  this.$selector.css({width: this.options.btnWidth});

	  this.$list = this.$selector.find('.am-selected-list');
	  this.$searchField = this.$selector.find('.am-selected-search input');
	  this.$hint = this.$selector.find('.am-selected-hint');

	  var $selectorBtn = this.$selector.find('.am-selected-btn');
	  var btnClassNames = [];

	  options.btnSize && btnClassNames.push('am-btn-' + options.btnSize);
	  options.btnStyle && btnClassNames.push('am-btn-' + options.btnStyle);
	  $selectorBtn.addClass(btnClassNames.join(' '));

	  this.$selector.dropdown({
	    justify: $selectorBtn
	  });

	  // disable Selected instance if <selected> is disabled
	  // should call .disable() after Dropdown initialed
	  if ($element[0].disabled) {
	    this.disable();
	  }

	  // set list height
	  if (options.maxHeight) {
	    this.$selector.find('.am-selected-list').css({
	      'max-height': options.maxHeight,
	      'overflow-y': 'scroll'
	    });
	  }

	  // set hint text
	  var hint = [];
	  var min = $element.attr('minchecked');
	  var max = $element.attr('maxchecked') || options.maxChecked;

	  this.maxChecked = max || Infinity;

	  if ($element[0].required) {
	    hint.push('必选');
	  }

	  if (min || max) {
	    min && hint.push('至少选择 ' + min + ' 项');
	    max && hint.push('至多选择 ' + max + ' 项');
	  }

	  this.$hint.text(hint.join('，'));

	  // render dropdown list
	  this.renderOptions();

	  // append $selector after <select>
	  this.$element.after(this.$selector);
	  this.dropdown = this.$selector.data('amui.dropdown');
	  this.$status = this.$selector.find('.am-selected-status');

	  // #try to fixes #476
	  setTimeout(function() {
	    _this.syncData();
	    _this.initialized = true;
	  }, 0);

	  this.bindEvents();
	};

	Selected.prototype.renderOptions = function() {
	  var $element = this.$element;
	  var options = this.options;
	  var optionItems = [];
	  var $optgroup = $element.find('optgroup');
	  this.$originalOptions = this.$element.find('option');

	  // 单选框使用 JS 禁用已经选择的 option 以后，
	  // 浏览器会重新选定第一个 option，但有一定延迟，致使 JS 获取 value 时返回 null
	  if (!this.multiple && ($element.val() === null)) {
	    this.$originalOptions.length &&
	    (this.$originalOptions.get(0).selected = true);
	  }

	  function pushOption(index, item, group) {
	    if (item.value === '') {
	      // skip to next iteration
	      // @see http://stackoverflow.com/questions/481601/how-to-skip-to-next-iteration-in-jquery-each-util
	      return true;
	    }

	    var classNames = '';
	    item.disabled && (classNames += options.disabledClass);
	    !item.disabled && item.selected && (classNames += options.selectedClass);

	    optionItems.push({
	      group: group,
	      index: index,
	      classNames: classNames,
	      text: item.text,
	      value: item.value
	    });
	  }

	  // select with option groups
	  if ($optgroup.length) {
	    $optgroup.each(function(i) {
	      // push group name
	      optionItems.push({
	        header: true,
	        group: i + 1,
	        text: this.label
	      });

	      $optgroup.eq(i).find('option').each(function(index, item) {
	        pushOption(index, item, i);
	      });
	    });
	  } else {
	    // without option groups
	    this.$originalOptions.each(function(index, item) {
	      pushOption(index, item, null);
	    });
	  }

	  this.$list.html(UI.template(options.listTpl, {options: optionItems}));
	  this.$shadowOptions = this.$list.find('> li').
	    not('.am-selected-list-header');
	};

	Selected.prototype.setChecked = function(item) {
	  var options = this.options;
	  var $item = $(item);
	  var isChecked = $item.hasClass(options.selectedClass);

	  if (this.multiple) {
	    // multiple
	    var checkedLength = this.$list.find('.' + options.selectedClass).length;

	    if (!isChecked && this.maxChecked <= checkedLength) {
	      this.$element.trigger('checkedOverflow.selected.amui', {
	        selected: this
	      });

	      return false;
	    }
	  } else {
	    // close dropdown whether item is checked or not
	    // @see #860
	    this.dropdown.close();

	    if (isChecked) {
	      return false;
	    }

	    this.$shadowOptions.not($item).removeClass(options.selectedClass);
	  }

	  $item.toggleClass(options.selectedClass);
	  this.syncData(item);
	};

	/**
	 * syncData
	 *
	 * @description if `item` set, only sync `item` related option
	 * @param {Object} [item]
	 */
	Selected.prototype.syncData = function(item) {
	  var _this = this;
	  var options = this.options;
	  var status = [];
	  var $checked = $([]);

	  this.$shadowOptions.filter('.' + options.selectedClass).each(function() {
	    var $this = $(this);
	    status.push($this.find('.am-selected-text').text());

	    if (!item) {
	      $checked = $checked.add(_this.$originalOptions
	        .filter('[value="' + $this.data('value') + '"]')
	        .prop('selected', true));
	    }
	  });

	  if (item) {
	    var $item = $(item);
	    this.$originalOptions
	      .filter('[value="' + $item.data('value') + '"]')
	      .prop('selected', $item.hasClass(options.selectedClass));
	  } else {
	    this.$originalOptions.not($checked).prop('selected', false);
	  }

	  // nothing selected
	  if (!this.$element.val()) {
	    status = [options.placeholder];
	  }

	  this.$status.text(status.join(', '));

	  // Do not trigger change event on initializing
	  this.initialized && this.$element.trigger('change');
	};

	Selected.prototype.bindEvents = function() {
	  var _this = this;
	  var header = 'am-selected-list-header';
	  var handleKeyup = UI.utils.debounce(function(e) {
	    _this.$shadowOptions.not('.' + header).hide().
	     filter(':containsNC("' + e.target.value + '")').show();
	  }, 100);

	  this.$list.on('click', '> li', function(e) {
	    var $this = $(this);
	    !$this.hasClass(_this.options.disabledClass) &&
	      !$this.hasClass(header) && _this.setChecked(this);
	  });

	  // simple search with jQuery :contains
	  this.$searchField.on('keyup.selected.amui', handleKeyup);

	  // empty search keywords
	  this.$selector.on('closed.dropdown.amui', function() {
	    _this.$searchField.val('');
	    _this.$shadowOptions.css({display: ''});
	  });

	  // work with Validator
	  // @since 2.5
	  this.$element.on('validated.field.validator.amui', function(e) {
	    if (e.validity) {
	      var valid = e.validity.valid;
	      var errorClassName = 'am-invalid';

	      _this.$selector[(!valid ? 'add' : 'remove') + 'Class'](errorClassName);
	    }
	  });

	  // observe DOM
	  if (UI.support.mutationobserver) {
	    this.observer = new UI.support.mutationobserver(function() {
	      _this.$element.trigger('changed.selected.amui');
	    });

	    this.observer.observe(this.$element[0], {
	      childList: true,
	      subtree: true,
	      characterData: true
	    });
	  }

	  // custom event
	  this.$element.on('changed.selected.amui', function() {
	    _this.renderOptions();
	    _this.syncData();
	  });
	};

	// @since: 2.5
	Selected.prototype.select = function(item) {
	  var $item;

	  if (typeof item === 'number') {
	    $item = this.$list.find('> li').not('.am-selected-list-header').eq(item);
	  } else if (typeof item === 'string') {
	    $item = this.$list.find(item);
	  } else {
	    $item = $(item);
	  }

	  $item.trigger('click');
	};

	// @since: 2.5
	Selected.prototype.enable = function() {
	  this.$element.prop('disable', false);
	  this.$selector.dropdown('enable');
	};

	// @since: 2.5
	Selected.prototype.disable = function() {
	  this.$element.prop('disable', true);
	  this.$selector.dropdown('disable');
	};

	Selected.prototype.destroy = function() {
	  this.$element.removeData('amui.selected').show();
	  this.$selector.remove();
	};

	UI.plugin('selected', Selected);

	// Conflict with jQuery form
	// https://github.com/malsup/form/blob/6bf24a5f6d8be65f4e5491863180c09356d9dadd/jquery.form.js#L1240-L1258
	// https://github.com/allmobilize/amazeui/issues/379
	// @deprecated: $.fn.selected = $.fn.selectIt = Plugin;

	// New way to resolve conflict:
	// @see https://github.com/amazeui/amazeui/issues/781#issuecomment-158873541

	UI.ready(function(context) {
	  $('[data-am-selected]', context).selected();
	});

	module.exports = Selected;


/***/ },
/* 22 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	/**
	 * Slider
	 * TODO： resize, touch, dynamic update
	 */

	var Slider = function(element, options) {

	  this.$element = $(element);
	  this.options = $.extend({}, Slider.DEFAULTS, options);

	  this.status = true;
	  this.init();

	};

	Slider.DEFAULTS = {
	  width: '100%',
	  height: '100%',
	  speed: 200, // transition time
	  infiniteLoop: true,
	  autoplay: false,
	  autoplaySpeed: 2000, // autoplay time
	  arrow: true,
	  arrowPre: '<span class="am-slider-arrow-prev am-icon-arrow-left"></span>',
	  arrowNext: '<span class="am-slider-arrow-next am-icon-arrow-right"></span>',
	  dots: true,
	  dotClass: null,
	  keyboard: true,
	  resize: null
	};

	Slider.prototype.init = function() {
	  var $ele = this.$element;
	  var options = this.options;
	  var $wrapper = this.$wrapper = $ele.find('.am-slider-wrapper');
	  var $child = this.$child = $ele.find('.am-slider-item');

	  $ele.addClass('am-slider');
	  $ele.css('width', options.width);

	  this.width = $ele.width(); // if use percentage, need get real width
	  this.length = $child.length;

	  // get width
	  this.$allWidth = this.width * $child.length;
	  this.$fullWidth = this.width * ($child.length + 2);

	  $child.css('width', this.width);
	  $wrapper.css('width', this.$fullWidth);

	  // infinite loop
	  $wrapper.append($wrapper.children().first().clone());
	  $wrapper.prepend($wrapper.children().eq(this.length - 1).clone());

	  // just judge once
	  if (this.prefix()) {
	    this.wheel = this.transformMove;
	  } else {
	    this.wheel = this.leftMove;
	  }

	  this.wheel('-' + this.width);
	  this.index = 0;
	  this.position = -this.width;

	  if (this.prefix()) {
	    this.transitionDuration(this.options.speed)
	    $wrapper.css('transition-timing-function', 'ease');
	  };

	  // simplify
	  if (options.keyboard) {
	    this.keyboard();
	  }
	  if (options.autoplay) {
	    this.autoplay();
	  }
	  if (options.arrow) {
	    this.arrow();
	  }
	  if (options.dots) {
	    this.dots();
	  }
	  // if (options.resize) {
	  //   this.resize();
	  // }
	};

	Slider.prototype.leftMove = function(value) {
	  this.$wrapper.css('marginLeft', value + 'px');
	};

	Slider.prototype.transformMove = function(value) {
	  var target  = 'translate3d(' + value + 'px,0,0)';
	  this.$wrapper.css(this.prop, target);
	  this.$wrapper.css('transform', target);
	};

	// Slider.prototype.destroy = function() {
	// };

	Slider.prototype.move = function(distance, type) {
	  var _this = this;

	  if (type === 'next') {
	    // avoid repeatedly move
	    if (this.index + 1 > this.length) {
	      return true;
	    }
	    this.position = this.position - this.width;
	    this.index = this.index + 1;
	  } else if (type === 'prev') {
	    if (this.index < 0) {
	      return true;
	    }
	    this.position = this.position + this.width;
	    this.index = this.index - 1;
	  }

	  this.wheel(this.position);
	  this.$element.trigger('change.slider.amui');

	  if (this.index === -1 || this.index === this.length) {

	    if (UI.support.transition) {
	      this.$wrapper.one(UI.support.transition.end, function() {

	        _this.transitionDuration(0);

	        if (_this.index === _this.length) {
	          _this.wheel(-_this.width);
	          _this.index = 0;
	          _this.position = -_this.width;
	        } else if (_this.index === -1) {
	          _this.wheel(-_this.length * _this.width);
	          _this.index = _this.length - 1;
	          _this.position = -_this.length * _this.width;
	        }
	        _this.dotActive(_this.index);
	      }).emulateTransitionEnd(_this.options.speed + 50)
	    }
	  }

	  this.transitionDuration(this.options.speed);
	  this.dotActive();
	  return true;
	};

	Slider.prototype.transitionDuration = function(value) {
	  this.$wrapper.css('-' + this.pfx + 'transition-duration', value + 'ms')
	  this.$wrapper.css('transition-duration', value + 'ms');
	}

	Slider.prototype.next = function() {
	  if (!this.options.infiniteLoop) {
	    if (this.index === this.length - 1 ) {
	      return true;
	    }
	  }
	  this.move(this.position, 'next');
	};

	Slider.prototype.prev = function() {
	  if (!this.options.infiniteLoop) {
	    if (this.index === 0 ) {
	      return true;
	    }
	  }
	  this.move(this.position, 'prev');
	};

	Slider.prototype.autoplay = function() {
	  var _this = this;

	  var timer = window.setInterval(function() {
	    _this.next();
	  }, _this.options.autoplaySpeed);

	  this.$element.on('mouseover.slider.amui', function() {
	    clearInterval(timer);
	  }).on('mouseout.slider.amui', function() {
	    timer = window.setInterval(function() {
	      _this.next();
	    }, _this.options.autoplaySpeed);
	  });
	};

	Slider.prototype.dots = function() {
	  var dotsTem = '<div class="am-slider-dots">';
	  var dotTem = '<span class="am-slider-dot"></span>';

	  for (var i = 0; i < this.length; i++) {
	    dotsTem += dotTem;
	  }
	  dotsTem += '</div>';

	  this.$element.append(dotsTem);
	  this.dotActive();
	  this.dotMove();
	};

	Slider.prototype.dotActive = function(i) {
	  i = i || this.index;
	  this.$element.find('.am-slider-dot').removeClass('active');
	  this.$element.find('.am-slider-dot').eq(i).addClass('active');
	};

	Slider.prototype.dotMove = function() {
	  var _this = this;

	  this.$element.find('.am-slider-dot').on('click.slider.amui', function() {
	    var i = _this.index;
	    var curI = _this.$element.find('.am-slider-dot').index(this)
	    var dis = (i - curI) * _this.width + _this.position;

	    _this.index = curI;
	    _this.position = dis;
	    _this.move(dis);
	  });

	};

	Slider.prototype.arrow = function() {
	  var _this = this;

	  this.$element.append(this.options.arrowPre, this.options.arrowNext);

	  this.$element.find('.am-slider-arrow-prev').on('click.slider.amui', function() {
	    _this.prev();
	  });
	  this.$element.find('.am-slider-arrow-next').on('click.slider.amui', function() {
	    _this.next();
	  });
	};

	Slider.prototype.keyboard = function() {
	  var _this = this;

	  $('body').on('keydown.slider.amui', function(e) {
	    var which = e.which;
	    if (which === 37 || which === 38) {
	      _this.prev();
	    } else if (which === 39 || which === 40) {
	      _this.next();
	    }
	  });
	};

	Slider.prototype.getIndex = function() {
	  return this.index;
	}

	Slider.prototype.prefix = function() {
	  var obj = document.createElement('div');
	  var props = ['perspectiveProperty', 'WebkitPerspective', 'MozPerspective', 'OPerspective', 'msPerspective'];
	  for (var i in props) {
	    if (obj.style[props[i]] !== undefined) {
	      this.pfx = props[i].replace('Perspective', '').toLowerCase();
	      this.prop = "-" + this.pfx + "-transform";
	      return true;
	    }
	  }
	  return false;
	};

	UI.plugin('slider', Slider);

	UI.ready(function(context) {
	  $('[data-am-slider]', context).slider();
	});

	module.exports = Slider;


/***/ },
/* 23 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	/**
	 * @via https://github.com/uikit/uikit/blob/master/src/js/addons/sticky.js
	 * @license https://github.com/uikit/uikit/blob/master/LICENSE.md
	 */

	// Sticky Class
	var Sticky = function(element, options) {
	  var _this = this;

	  this.options = $.extend({}, Sticky.DEFAULTS, options);
	  this.$element = $(element);
	  this.sticked = null;
	  this.inited = null;
	  this.$holder = undefined;

	  this.$window = $(window).
	    on('scroll.sticky.amui',
	    UI.utils.debounce($.proxy(this.checkPosition, this), 10)).
	    on('resize.sticky.amui orientationchange.sticky.amui',
	    UI.utils.debounce(function() {
	      _this.reset(true, function() {
	        _this.checkPosition();
	      });
	    }, 50)).
	    on('load.sticky.amui', $.proxy(this.checkPosition, this));

	  // the `.offset()` is diff between jQuery & Zepto.js
	  // jQuery: return `top` and `left`
	  // Zepto.js: return `top`, `left`, `width`, `height`
	  this.offset = this.$element.offset();

	  this.init();
	};

	Sticky.DEFAULTS = {
	  top: 0,
	  bottom: 0,
	  animation: '',
	  className: {
	    sticky: 'am-sticky',
	    resetting: 'am-sticky-resetting',
	    stickyBtm: 'am-sticky-bottom',
	    animationRev: 'am-animation-reverse'
	  }
	};

	Sticky.prototype.init = function() {
	  var result = this.check();

	  if (!result) {
	    return false;
	  }

	  var $element = this.$element;
	  var $elementMargin = '';

	  $.each($element.css(
	      ['marginTop', 'marginRight', 'marginBottom', 'marginLeft']),
	    function(name, value) {
	      return $elementMargin += ' ' + value;
	    });

	  var $holder = $('<div class="am-sticky-placeholder"></div>').css({
	    height: $element.css('position') !== 'absolute' ?
	      $element.outerHeight() : '',
	    float: $element.css('float') != 'none' ? $element.css('float') : '',
	    margin: $elementMargin
	  });

	  this.$holder = $element.css('margin', 0).wrap($holder).parent();
	  this.inited = 1;

	  return true;
	};

	Sticky.prototype.reset = function(force, cb) {
	  var options = this.options;
	  var $element = this.$element;
	  var animation = (options.animation) ?
	  ' am-animation-' + options.animation : '';
	  var complete = function() {
	    $element.css({position: '', top: '', width: '', left: '', margin: 0});
	    $element.removeClass([
	      animation,
	      options.className.animationRev,
	      options.className.sticky,
	      options.className.resetting
	    ].join(' '));

	    this.animating = false;
	    this.sticked = false;
	    this.offset = $element.offset();
	    cb && cb();
	  }.bind(this);

	  $element.addClass(options.className.resetting);

	  if (!force && options.animation && UI.support.animation) {

	    this.animating = true;

	    $element.removeClass(animation).one(UI.support.animation.end, function() {
	      complete();
	    }).width(); // force redraw

	    $element.addClass(animation + ' ' + options.className.animationRev);
	  } else {
	    complete();
	  }
	};

	Sticky.prototype.check = function() {
	  if (!this.$element.is(':visible')) {
	    return false;
	  }

	  var media = this.options.media;

	  if (media) {
	    switch (typeof(media)) {
	      case 'number':
	        if (window.innerWidth < media) {
	          return false;
	        }
	        break;

	      case 'string':
	        if (window.matchMedia && !window.matchMedia(media).matches) {
	          return false;
	        }
	        break;
	    }
	  }

	  return true;
	};

	Sticky.prototype.checkPosition = function() {
	  if (!this.inited) {
	    var initialized = this.init();
	    if (!initialized) {
	      return;
	    }
	  }

	  var options = this.options;
	  var scrollTop = this.$window.scrollTop();
	  var offsetTop = options.top;
	  var offsetBottom = options.bottom;
	  var $element = this.$element;
	  var animation = (options.animation) ?
	    ' am-animation-' + options.animation : '';
	  var className = [options.className.sticky, animation].join(' ');

	  if (typeof offsetBottom == 'function') {
	    offsetBottom = offsetBottom(this.$element);
	  }

	  var checkResult = (scrollTop > this.$holder.offset().top);

	  if (!this.sticked && checkResult) {
	    $element.addClass(className);
	  } else if (this.sticked && !checkResult) {
	    this.reset();
	  }

	  this.$holder.css({
	    height: $element.is(':visible') && $element.css('position') !== 'absolute' ?
	      $element.outerHeight() : ''
	  });

	  if (checkResult) {
	    $element.css({
	      top: offsetTop,
	      left: this.$holder.offset().left,
	      width: this.$holder.width()
	    });

	    /*
	     if (offsetBottom) {
	     // （底部边距 + 元素高度 > 窗口高度） 时定位到底部
	     if ((offsetBottom + this.offset.height > $(window).height()) &&
	     (scrollTop + $(window).height() >= scrollHeight - offsetBottom)) {
	     $element.addClass(options.className.stickyBtm).
	     css({top: $(window).height() - offsetBottom - this.offset.height});
	     } else {
	     $element.removeClass(options.className.stickyBtm).css({top: offsetTop});
	     }
	     }
	     */
	  }

	  this.sticked = checkResult;
	};

	// Sticky Plugin
	UI.plugin('sticky', Sticky);

	// Init code
	$(window).on('load', function() {
	  $('[data-am-sticky]').sticky();
	});

	module.exports = Sticky;


/***/ },
/* 24 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);
	var Hammer = __webpack_require__(3);
	var supportTransition = UI.support.transition;
	var animation = UI.support.animation;

	/**
	 * @via https://github.com/twbs/bootstrap/blob/master/js/tab.js
	 * @copyright 2011-2014 Twitter, Inc.
	 * @license MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
	 */

	/**
	 * Tabs
	 * @param {HTMLElement} element
	 * @param {Object} options
	 * @constructor
	 */
	var Tabs = function(element, options) {
	  this.$element = $(element);
	  this.options = $.extend({}, Tabs.DEFAULTS, options || {});
	  this.transitioning = this.activeIndex = null;

	  this.refresh();
	  this.init();
	};

	Tabs.DEFAULTS = {
	  selector: {
	    nav: '> .am-tabs-nav',
	    content: '> .am-tabs-bd',
	    panel: '> .am-tab-panel'
	  },
	  activeClass: 'am-active'
	};

	Tabs.prototype.refresh = function() {
	  var selector = this.options.selector;

	  this.$tabNav = this.$element.find(selector.nav);
	  this.$navs = this.$tabNav.find('a');

	  this.$content = this.$element.find(selector.content);
	  this.$tabPanels = this.$content.find(selector.panel);

	  var $active = this.$tabNav.find('> .' + this.options.activeClass);

	  // Activate the first Tab when no active Tab or multiple active Tabs
	  if ($active.length !== 1) {
	    this.open(0);
	  } else {
	    this.activeIndex = this.$navs.index($active.children('a'));
	  }
	};

	Tabs.prototype.init = function() {
	  var _this = this;
	  var options = this.options;

	  this.$element.on('click.tabs.amui', options.selector.nav + ' a', function(e) {
	    e.preventDefault();
	    _this.open($(this));
	  });

	  // TODO: nested Tabs touch events
	  if (!options.noSwipe) {
	    if (!this.$content.length) {
	      return this;
	    }

	    var hammer = new Hammer.Manager(this.$content[0]);
	    var swipe = new Hammer.Swipe({
	      direction: Hammer.DIRECTION_HORIZONTAL
	      // threshold: 40
	    });

	    hammer.add(swipe);

	    hammer.on('swipeleft', UI.utils.debounce(function(e) {
	      e.preventDefault();
	      _this.goTo('next');
	    }, 100));

	    hammer.on('swiperight', UI.utils.debounce(function(e) {
	      e.preventDefault();
	      _this.goTo('prev');
	    }, 100));

	    this._hammer = hammer;
	  }
	};

	/**
	 * Open $nav tab
	 * @param {jQuery|HTMLElement|Number} $nav
	 * @returns {Tabs}
	 */
	Tabs.prototype.open = function($nav) {
	  var activeClass = this.options.activeClass;
	  var activeIndex = typeof $nav === 'number' ? $nav : this.$navs.index($($nav));

	  $nav = typeof $nav === 'number' ? this.$navs.eq(activeIndex) : $($nav);

	  if (!$nav ||
	    !$nav.length ||
	    this.transitioning ||
	    $nav.parent('li').hasClass(activeClass)) {
	    return;
	  }

	  var $tabNav = this.$tabNav;
	  var href = $nav.attr('href');
	  var regexHash = /^#.+$/;
	  var $target = regexHash.test(href) && this.$content.find(href) ||
	    this.$tabPanels.eq(activeIndex);
	  var previous = $tabNav.find('.' + activeClass + ' a')[0];
	  var e = $.Event('open.tabs.amui', {
	    relatedTarget: previous
	  });

	  $nav.trigger(e);

	  if (e.isDefaultPrevented()) {
	    return;
	  }

	  // activate Tab nav
	  this.activate($nav.closest('li'), $tabNav);

	  // activate Tab content
	  this.activate($target, this.$content, function() {
	    $nav.trigger({
	      type: 'opened.tabs.amui',
	      relatedTarget: previous
	    });
	  });

	  this.activeIndex = activeIndex;
	};

	Tabs.prototype.activate = function($element, $container, callback) {
	  this.transitioning = true;

	  var activeClass = this.options.activeClass;
	  var $active = $container.find('> .' + activeClass);
	  var transition = callback && supportTransition && !!$active.length;

	  $active.removeClass(activeClass + ' am-in');

	  $element.addClass(activeClass);

	  if (transition) {
	    $element.redraw(); // reflow for transition
	    $element.addClass('am-in');
	  } else {
	    $element.removeClass('am-fade');
	  }

	  var complete = $.proxy(function complete() {
	    callback && callback();
	    this.transitioning = false;
	  }, this);



	  transition && !this.$content.is('.am-tabs-bd-ofv') ?
	    $active.one(supportTransition.end, complete) : complete();
	};

	/**
	 * Go to `next` or `prev` tab
	 * @param {String} direction - `next` or `prev`
	 */
	Tabs.prototype.goTo = function(direction) {
	  var navIndex = this.activeIndex;
	  var isNext = direction === 'next';
	  var spring = isNext ? 'am-animation-right-spring' :
	    'am-animation-left-spring';

	  if ((isNext && navIndex + 1 >= this.$navs.length) || // last one
	    (!isNext && navIndex === 0)) { // first one
	    var $panel = this.$tabPanels.eq(navIndex);

	    animation && $panel.addClass(spring).on(animation.end, function() {
	      $panel.removeClass(spring);
	    });
	  } else {
	    this.open(isNext ? navIndex + 1 : navIndex - 1);
	  }
	};

	Tabs.prototype.destroy = function() {
	  this.$element.off('.tabs.amui');
	  Hammer.off(this.$content[0], 'swipeleft swiperight');
	  this._hammer && this._hammer.destroy();
	  $.removeData(this.$element, 'amui.tabs');
	};

	// Plugin
	function Plugin(option) {
	  var args = Array.prototype.slice.call(arguments, 1);
	  var methodReturn;

	  this.each(function() {
	    var $this = $(this);
	    var $tabs = $this.is('.am-tabs') && $this || $this.closest('.am-tabs');
	    var data = $tabs.data('amui.tabs');
	    var options = $.extend({}, UI.utils.parseOptions($this.data('amTabs')),
	      $.isPlainObject(option) && option);

	    if (!data) {
	      $tabs.data('amui.tabs', (data = new Tabs($tabs[0], options)));
	    }

	    if (typeof option === 'string') {
	      if (option === 'open' && $this.is('.am-tabs-nav a')) {
	        data.open($this);
	      } else {
	        methodReturn = typeof data[option] === 'function' ?
	          data[option].apply(data, args) : data[option];
	      }
	    }
	  });

	  return methodReturn === undefined ? this : methodReturn;
	}

	$.fn.tabs = Plugin;

	// Init code
	UI.ready(function(context) {
	  $('[data-am-tabs]', context).tabs();
	});

	$(document).on('click.tabs.amui.data-api', '[data-am-tabs] .am-tabs-nav a',
	  function(e) {
	  e.preventDefault();
	  Plugin.call($(this), 'open');
	});

	module.exports = UI.tabs = Tabs;

	// TODO: 1. Ajax 支持
	//       2. touch 事件处理逻辑优化


/***/ },
/* 25 */
/***/ function(module, exports, __webpack_require__) {

	/*
	 * Amaze UI Tree
	 *
	 * via: https://github.com/ExactTarget/fuelux
	 *
	 * Copyright (c) 2014 ExactTarget
	 * Licensed under the BSD New license.
	 */

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	var old = $.fn.tree;

	// TREE CONSTRUCTOR AND PROTOTYPE

	var Tree = function Tree(element, options) {
	  this.$element = $(element);
	  this.options = $.extend({}, Tree.DEFAULTS, options);

	  // icons
	  var itemIcon = this.$element.data('itemIcon') || this.options.itemIcon;
	  var itemSelectedIcon = this.$element.data('itemSelectedIcon') ||
	    this.options.itemSelectedIcon;
	  this.folderIcon = this._getIconClass(this.options.folderIcon);
	  this.folderOpenIcon = this._getIconClass(this.options.folderOpenIcon);
	  this.itemIcon = this._getIconClass(itemIcon);
	  this.itemSelectedIcon = this._getIconClass(itemSelectedIcon);

	  // classes
	  this.classes = {};
	  $.each(this.options.classes, $.proxy(function(key, value) {
	    this.classes[key] = this._getClass(value);
	  }, this));

	  if (this.options.itemSelect) {
	    this.$element.on('click.tree.amui', '.' + this.classes.item, $.proxy(function(e) {
	      this.selectItem(e.currentTarget);
	    }, this));
	  }

	  this.$element.on('click.tree.amui', '.' + this.classes.branchName, $.proxy(function(e) {
	    this.toggleFolder(e.currentTarget);
	  }, this));

	  // folderSelect default is true
	  if (this.options.folderSelect) {
	    this.$element.addClass(this.options.classPrefix + '-folder-select');
	    this.$element.off('click.tree.amui', '.' + this.classes.branchName);
	    this.$element.on('click.tree.amui', '.' + this.classes.iconCaret, $.proxy(function(e) {
	      this.toggleFolder($(e.currentTarget).parent());
	    }, this));
	    this.$element.on('click.tree.amui', '.' + this.classes.branchName, $.proxy(function(e) {
	      this.selectFolder($(e.currentTarget));
	    }, this));
	  }

	  this.render();
	};

	Tree.prototype = {
	  constructor: Tree,

	  deselectAll: function deselectAll(nodes) {
	    var _this = this;
	    // clear all child tree nodes and style as deselected
	    nodes = nodes || this.$element;
	    var $selectedElements = $(nodes).find('.' + this.classes.selected);
	    $selectedElements.each(function(index, element) {
	      styleNodeDeselected(_this, $(element),
	        $(element).find('.' + _this.classes.icon));
	    });
	    return $selectedElements;
	  },

	  destroy: function destroy() {
	    // any external bindings [none]
	    // empty elements to return to original markup
	    this.$element.find('li:not([data-template])').remove();

	    this.$element.remove();
	    // returns string of markup
	    return this.$element[0].outerHTML;
	  },

	  render: function render() {
	    this.populate(this.$element);
	  },

	  populate: function populate($el) {
	    var _this = this;
	    var $parent = ($el.hasClass(this.options.classPrefix)) ? $el : $el.parent();
	    var $loader = $parent.find('.' + this.classes.loader).eq(0);
	    var treeData = $parent.data();

	    $loader.removeClass(this.options.hiddenClass);

	    // 根据模板和数据生成 tree
	    this.options.dataSource(treeData, function(items) {
	      $loader.addClass(_this.options.hiddenClass);

	      $.each(items.data, function(index, value) {
	        var $entity;

	        // 判断是否包含子级
	        if (value.type === 'folder') {
	          $entity = _this.$element.find('[data-template=treebranch]').first().
	            clone().
	            removeClass(_this.options.hiddenClass).removeData('template');
	          $entity.data(value);
	          // add folder icon class
	          $entity.find('.' + _this.classes.iconFolder).addClass(_this.folderIcon);
	          $entity.find('.' + _this.classes.branchName + ' > .' + _this.classes.label).html(value.title);
	        } else if (value.type === 'item') {
	          $entity = _this.$element.find('[data-template=treeitem]').first()
	            .clone().removeClass(_this.options.hiddenClass).removeData('template');
	          $entity.find('.' + _this.classes.itemName + ' > .' + _this.classes.label).html(value.title);
	          $entity.data(value);
	          $entity.find('.' + _this.classes.iconItem).addClass(_this.itemIcon);
	        }

	        // Decorate $entity with data or other attributes making the
	        // element easily accessable with libraries like jQuery.
	        //
	        // Values are contained within the object returned
	        // for folders and items as attr:
	        //
	        // {
	        //     title: "An Item",
	        //     type: 'item',
	        //     attr: {
	        //         'classNames': 'required-item red-text',
	        //         'icon': '',
	        //         'id': guid
	        //     }
	        // };
	        //

	        // add attributes to tree-branch or tree-item
	        var attr = value.attr || [];
	        $.each(attr, function(key, value) {
	          switch (key) {
	            case 'classNames':
	              $entity.addClass(value);
	              break;

	            // allow custom icons
	            case 'icon':
	              var classes = [_this.classes.icon, _this.classes.iconItem, value].join(' ');
	              $entity.find('.' + _this.classes.iconItem).removeClass().addClass(classes);
	              $entity.data(key, value);
	              break;

	            // ARIA support
	            case 'id':
	              $entity.attr(key, value);
	              $entity.attr('aria-labelledby', value + '-label');
	              $entity.find('.' + _this.classes.branchName + ' > .' + _this.classes.label).attr('id', value + '-label');
	              break;

	            // style, data-*
	            default:
	              $entity.attr(key, value);
	              break;
	          }
	        });

	        // add child nodes
	        if ($el.hasClass(_this.classes.branchHeader)) {
	          $parent.find('.' + _this.classes.branchChildren).eq(0).append($entity);
	        } else {
	          $el.append($entity);
	        }
	      });

	      // return newly populated folder
	      _this.$element.trigger('loaded.tree.amui', $parent);
	    });
	  },

	  selectTreeNode: function selectItem(clickedElement, nodeType) {
	    var clicked = {};	// object for clicked element
	    clicked.$element = $(clickedElement);

	    var selected = {}; // object for selected elements
	    selected.$elements = this.$element.find('.' + this.classes.selected);
	    selected.dataForEvent = [];

	    // determine clicked element and it's icon
	    if (nodeType === 'folder') {
	      // make the clicked.$element the container branch
	      clicked.$element = clicked.$element.closest('.' + this.classes.branch);
	      clicked.$icon = clicked.$element.find('.' + this.classes.iconFolder);
	    } else {
	      clicked.$icon = clicked.$element.find('.' + this.classes.iconItem);
	    }
	    clicked.elementData = clicked.$element.data();

	    // the below functions pass objects by copy/reference and use modified object in this function
	    if (this.options.multiSelect) {
	      multiSelectSyncNodes(this, clicked, selected);
	    } else {
	      singleSelectSyncNodes(this, clicked, selected);
	    }

	    // all done with the DOM, now fire events
	    this.$element.trigger(selected.eventType + '.tree.amui', {
	      target: clicked.elementData,
	      selected: selected.dataForEvent
	    });

	    clicked.$element.trigger('updated.tree.amui', {
	      selected: selected.dataForEvent,
	      item: clicked.$element,
	      eventType: selected.eventType
	    });
	  },

	  discloseFolder: function discloseFolder(el) {
	    var $el = $(el);
	    var $branch = $el.closest('.' + this.classes.branch);
	    var $treeFolderContent = $branch.find('.' + this.classes.branchChildren);
	    var $treeFolderContentFirstChild = $treeFolderContent.eq(0);

	    // take care of the styles
	    $branch.addClass(this.classes.open);
	    $branch.attr('aria-expanded', 'true');
	    $treeFolderContentFirstChild.removeClass(this.options.hiddenClass);
	    $branch.find('> .' + this.classes.branchHeader + ' .' + this.classes.iconFolder).eq(0)
	      .removeClass(this.folderIcon)
	      .addClass(this.folderOpenIcon);

	    // add the children to the folder
	    if (!$treeFolderContent.children().length) {
	      this.populate($treeFolderContent);
	    }

	    this.$element.trigger('disclosedFolder.tree.amui', $branch.data());
	  },

	  closeFolder: function closeFolder(el) {
	    var $el = $(el);
	    var $branch = $el.closest('.' + this.classes.branch);
	    var $treeFolderContent = $branch.find('.' + this.classes.branchChildren);
	    var $treeFolderContentFirstChild = $treeFolderContent.eq(0);

	    //take care of the styles
	    $branch.removeClass(this.classes.open);
	    $branch.attr('aria-expanded', 'false');
	    $treeFolderContentFirstChild.addClass(this.options.hiddenClass);
	    $branch.find('> .' + this.classes.branchHeader + ' .' + this.classes.iconFolder).eq(0)
	      .removeClass(this.folderOpenIcon)
	      .addClass(this.folderIcon);

	    // remove chidren if no cache
	    if (!this.options.cacheItems) {
	      $treeFolderContentFirstChild.empty();
	    }

	    this.$element.trigger('closed.tree.amui', $branch.data());
	  },

	  toggleFolder: function toggleFolder(el) {
	    var $el = $(el);

	    if ($el.find('.' + this.folderIcon).length) {
	      this.discloseFolder(el);
	    } else if ($el.find('.' + this.folderOpenIcon).length) {
	      this.closeFolder(el);
	    }
	  },

	  selectFolder: function selectFolder(el) {
	    if (this.options.folderSelect) {
	      this.selectTreeNode(el, 'folder');
	    }
	  },

	  selectItem: function selectItem(el) {
	    if (this.options.itemSelect) {
	      this.selectTreeNode(el, 'item');
	    }
	  },

	  selectedItems: function selectedItems() {
	    var $sel = this.$element.find('.' + this.classes.selected);
	    var data = [];

	    $.each($sel, function(index, value) {
	      data.push($(value).data());
	    });
	    return data;
	  },

	  // collapses open folders
	  closeAll: function collapse() {
	    var self = this;
	    var reportedClosed = [];

	    var closedReported = function closedReported(event, closed) {
	      reportedClosed.push(closed);

	      if (self.$element.find('.' + self.classes.branch + '.' + self.classes.open + ':not(".am-hide")').length === 0) {
	        self.$element.trigger('closedAll.tree.amui', {
	          tree: self.$element,
	          reportedClosed: reportedClosed
	        });
	        self.$element.off('loaded.tree.amui', self.$element, closedReported);
	      }
	    };

	    //trigger callback when all folders have reported closed
	    self.$element.on('closed.tree.amui', closedReported);

	    self.$element.find('.' + self.classes.branch + '.' + self.classes.open + ':not(".am-hide")').each(function() {
	      self.closeFolder(this);
	    });
	  },

	  //disclose visible will only disclose visible tree folders
	  discloseVisible: function discloseVisible() {
	    var self = this;

	    var $openableFolders = self.$element.find('.' + this.classes.branch + ':not(".' + this.classes.open + ', .am-hide")');
	    var reportedOpened = [];

	    var openReported = function openReported(event, opened) {
	      reportedOpened.push(opened);

	      if (reportedOpened.length === $openableFolders.length) {
	        self.$element.trigger('disclosedVisible.tree.amui', {
	          tree: self.$element,
	          reportedOpened: reportedOpened
	        });
	        /*
	         * Unbind the `openReported` event. `discloseAll` may be running and we want to reset this
	         * method for the next iteration.
	         */
	        self.$element.off('loaded.tree.amui', self.$element, openReported);
	      }
	    };

	    //trigger callback when all folders have reported opened
	    self.$element.on('loaded.tree.amui', openReported);

	    // open all visible folders
	    self.$element.find('.' + this.classes.branch + ':not(".' + this.classes.open + ', .am-hide")').each(function triggerOpen() {
	      self.discloseFolder($(this).find('.' + self.classes.branchHeader));
	    });
	  },

	  /**
	   * Disclose all will keep listening for `loaded.tree.amui` and if `$(tree-el).data('ignore-disclosures-limit')`
	   * is `true` (defaults to `true`) it will attempt to disclose any new closed folders than were
	   * loaded in during the last disclosure.
	   */
	  discloseAll: function discloseAll() {
	    var self = this;

	    //first time
	    if (typeof self.$element.data('disclosures') === 'undefined') {
	      self.$element.data('disclosures', 0);
	    }

	    var isExceededLimit = (self.options.disclosuresUpperLimit >= 1 && self.$element.data('disclosures') >= self.options.disclosuresUpperLimit);
	    var isAllDisclosed = self.$element.find('.' + this.classes.branch + ':not(".' + this.classes.open + ', .am-hide")').length === 0;


	    if (!isAllDisclosed) {
	      if (isExceededLimit) {
	        self.$element.trigger('exceededDisclosuresLimit.tree.amui', {
	          tree: self.$element,
	          disclosures: self.$element.data('disclosures')
	        });

	        /*
	         * If you've exceeded the limit, the loop will be killed unless you
	         * explicitly ignore the limit and start the loop again:
	         *
	         *    $tree.one('exceededDisclosuresLimit.tree.amui', function () {
	         *        $tree.data('ignore-disclosures-limit', true);
	         *        $tree.tree('discloseAll');
	         *    });
	         */
	        if (!self.$element.data('ignore-disclosures-limit')) {
	          return;
	        }
	      }

	      self.$element.data('disclosures', self.$element.data('disclosures') + 1);

	      /*
	       * A new branch that is closed might be loaded in, make sure those get handled too.
	       * This attachment needs to occur before calling `discloseVisible` to make sure that
	       * if the execution of `discloseVisible` happens _super fast_ (as it does in our QUnit tests
	       * this will still be called. However, make sure this only gets called _once_, because
	       * otherwise, every single time we go through this loop, _another_ event will be bound
	       * and then when the trigger happens, this will fire N times, where N equals the number
	       * of recursive `discloseAll` executions (instead of just one)
	       */
	      self.$element.one('disclosedVisible.tree.amui', function() {
	        self.discloseAll();
	      });

	      /*
	       * If the page is very fast, calling this first will cause `disclosedVisible.tree.amui` to not
	       * be bound in time to be called, so, we need to call this last so that the things bound
	       * and triggered above can have time to take place before the next execution of the
	       * `discloseAll` method.
	       */
	      self.discloseVisible();
	    } else {
	      self.$element.trigger('disclosedAll.tree.amui', {
	        tree: self.$element,
	        disclosures: self.$element.data('disclosures')
	      });

	      //if `cacheItems` is false, and they call closeAll, the data is trashed and therefore
	      //disclosures needs to accurately reflect that
	      if (!self.options.cacheItems) {
	        self.$element.one('closeAll.tree.amui', function() {
	          self.$element.data('disclosures', 0);
	        });
	      }
	    }
	  },

	  _getIconClass: function(icon, selector) {
	    return (selector ? '.' : '') + 'am-icon-' + icon;
	  },

	  _getClass: function(subClass, selector) {
	    return (selector ? '.' : '') + this.options.classPrefix + '-' + subClass;
	  }
	};


	// ALIASES

	Tree.prototype.openFolder = Tree.prototype.discloseFolder;


	// PRIVATE FUNCTIONS

	function styleNodeSelected(self, $element, $icon) {
	  $element.addClass(self.classes.selected);
	  if ($element.data('type') === 'item' && $icon.hasClass(self.itemIcon)) {
	    $icon.removeClass(self.itemIcon).addClass(self.itemSelectedIcon); // make checkmark
	  }
	}

	function styleNodeDeselected(self, $element, $icon) {
	  $element.removeClass(self.classes.selected);
	  if ($element.data('type') === 'item' && $icon.hasClass(self.itemSelectedIcon)) {
	    $icon.removeClass(self.itemSelectedIcon).addClass(self.itemIcon); // make bullet
	  }
	}

	function multiSelectSyncNodes(self, clicked, selected) {
	  // search for currently selected and add to selected data list if needed
	  $.each(selected.$elements, function(index, element) {
	    var $element = $(element);
	    if ($element[0] !== clicked.$element[0]) {
	      selected.dataForEvent.push($($element).data());
	    }
	  });

	  if (clicked.$element.hasClass(self.classes.selected)) {
	    styleNodeDeselected(self, clicked.$element, clicked.$icon);
	    // set event data
	    selected.eventType = 'deselected';
	  } else {
	    styleNodeSelected(self, clicked.$element, clicked.$icon);
	    // set event data
	    selected.eventType = 'selected';
	    selected.dataForEvent.push(clicked.elementData);
	  }
	}

	function singleSelectSyncNodes(self, clicked, selected) {
	  // element is not currently selected
	  if (selected.$elements[0] !== clicked.$element[0]) {
	    var clearedElements = self.deselectAll(self.$element);
	    styleNodeSelected(self, clicked.$element, clicked.$icon);
	    // set event data
	    selected.eventType = 'selected';
	    selected.dataForEvent = [clicked.elementData];
	  } else {
	    styleNodeDeselected(self, clicked.$element, clicked.$icon);
	    // set event data
	    selected.eventType = 'deselected';
	    selected.dataForEvent = [];
	  }
	}

	Tree.DEFAULTS = {
	  dataSource: function dataSource(options, callback) {
	  },
	  multiSelect: false,
	  cacheItems: true,
	  folderSelect: true,
	  itemSelect: true,
	  /*
	   * How many times `discloseAll` should be called before a stopping and firing
	   * an `exceededDisclosuresLimit` event. You can force it to continue by
	   * listening for this event, setting `ignore-disclosures-limit` to `true` and
	   * starting `discloseAll` back up again. This lets you make more decisions
	   * about if/when/how/why/how many times `discloseAll` will be started back
	   * up after it exceeds the limit.
	   *
	   *    $tree.one('exceededDisclosuresLimit.tree.amui', function () {
	   *        $tree.data('ignore-disclosures-limit', true);
	   *        $tree.tree('discloseAll');
	   *    });
	   *
	   * `disclusuresUpperLimit` defaults to `0`, so by default this trigger
	   * will never fire. The true hard the upper limit is the browser's
	   * ability to load new items (i.e. it will keep loading until the browser
	   * falls over and dies). On the Fuel UX `index.html` page, the point at
	   * which the page became super slow (enough to seem almost unresponsive)
	   * was `4`, meaning 256 folders had been opened, and 1024 were attempting to open.
	   */
	  disclosuresUpperLimit: 0,
	  folderIcon: 'plus-square-o',
	  folderOpenIcon: 'minus-square-o',
	  itemIcon: 'angle-right',
	  itemSelectedIcon: 'check',

	  hiddenClass: 'am-hide',

	  classPrefix: 'am-tree',

	  classes: {
	    branch: 'branch',
	    branchName: 'branch-name', // branch name
	    branchChildren: 'branch-children',
	    branchHeader: 'branch-header',
	    item: 'item', // tree item
	    itemName: 'item-name', // tree item name
	    label: 'label',
	    icon: 'icon', // tree icon
	    iconFolder: 'icon-folder', // tree icon
	    iconItem: 'icon-item', // tree icon,
	    iconCaret: 'icon-caret',
	    loader: 'loader', // tree loader
	    selected: 'selected',
	    open: 'open'
	  }
	};

	UI.plugin('tree', Tree);

	module.exports = Tree;


/***/ },
/* 26 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	/**
	 * UCheck
	 * @via https://github.com/designmodo/Flat-UI/blob/8ef98df23ba7f5033e596a9bd05b53b535a9fe99/js/radiocheck.js
	 * @license CC BY 3.0 & MIT
	 * @param {HTMLElement} element
	 * @param {object} options
	 * @constructor
	 */

	var UCheck = function(element, options) {
	  this.options = $.extend({}, UCheck.DEFAULTS, options);
	  // this.options = $.extend({}, UCheck.DEFAULTS, this.$element.data(), options);
	  this.$element = $(element);
	  this.init();
	};

	UCheck.DEFAULTS = {
	  checkboxClass: 'am-ucheck-checkbox',
	  radioClass: 'am-ucheck-radio',
	  checkboxTpl: '<span class="am-ucheck-icons">' +
	  '<i class="am-icon-unchecked"></i><i class="am-icon-checked"></i></span>',
	  radioTpl: '<span class="am-ucheck-icons">' +
	  '<i class="am-icon-unchecked"></i><i class="am-icon-checked"></i></span>'
	};

	UCheck.prototype.init = function() {
	  var $element = this.$element;
	  var element = $element[0];
	  var options = this.options;

	  if (element.type === 'checkbox') {
	    $element.addClass(options.checkboxClass)
	      .after(options.checkboxTpl);
	  } else if (element.type === 'radio') {
	    $element.addClass(options.radioClass)
	      .after(options.radioTpl);
	  }
	};

	UCheck.prototype.check = function() {
	  this.$element
	    .prop('checked', true)
	    .trigger('change.ucheck.amui')
	    .trigger('checked.ucheck.amui');
	},

	UCheck.prototype.uncheck = function() {
	  this.$element
	    .prop('checked', false)
	    // trigger `change` event for form validation, etc.
	    // @see https://forum.jquery.com/topic/should-chk-prop-checked-true-trigger-change-event
	    .trigger('change')
	    .trigger('unchecked.ucheck.amui');
	},

	UCheck.prototype.toggle = function() {
	  this.$element.
	    prop('checked', function(i, value) {
	      return !value;
	    })
	    .trigger('change.ucheck.amui')
	    .trigger('toggled.ucheck.amui');
	},

	UCheck.prototype.disable = function() {
	  this.$element
	    .prop('disabled', true)
	    .trigger('change.ucheck.amui')
	    .trigger('disabled.ucheck.amui');
	},

	UCheck.prototype.enable = function() {
	  this.$element.prop('disabled', false);
	  this.$element.trigger('change.ucheck.amui').trigger('enabled.ucheck.amui');
	},

	UCheck.prototype.destroy = function() {
	  this.$element
	    .removeData('amui.ucheck')
	    .removeClass(this.options.checkboxClass + ' ' + this.options.radioClass)
	    .next('.am-ucheck-icons')
	    .remove()
	  .end()
	    .trigger('destroyed.ucheck.amui');
	};

	UI.plugin('uCheck', UCheck, {
	  after: function() {
	    // Adding 'am-nohover' class for touch devices
	    if (UI.support.touch) {
	      this.parent().hover(function() {
	        $(this).addClass('am-nohover');
	      }, function() {
	        $(this).removeClass('am-nohover');
	      });
	    }
	  }
	});

	UI.ready(function(context) {
	  $('[data-am-ucheck]', context).uCheck();
	});

	module.exports = UCheck;

	// TODO: 与表单验证结合使用的情况


/***/ },
/* 27 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	/**
	 * Upload
	 */

	var Upload = function(element, options) {
	  this.options = $.extend({}, Upload.DEFAULTS, options);
	  this.$element = $(element);

	  this.init();
	  this.eventBind();
	};

	Upload.DEFAULTS = {
	  type: [
	    'image/png',
	    'image/jpg',
	    'image/gif',
	    'image/jpeg'
	  ], // [Object] set upload file type
	  size: null, // [Number] set max size of files
	  hideTime: 2000, // [Number] set time: if hide progressbar after upload success
	  header: null,  // [Object] set ajax setting
	  data: null, // [Object] key-value: extra form data
	  tpl: '<div class="am-form-group am-form-file">' +
	  '<button type="button" class="am-btn am-btn-primary am-upload-btn">' +
	      '<i class="am-icon-cloud-upload"></i> 选择要上传的文件</button>' +
	    '<input class="am-upload-input" type="file" multiple>' +
	  '</div>'
	};

	Upload.prototype.init = function() {
	  var $ele = this.$element;
	  $ele.addClass('am-upload');
	  $ele.append(this.options.tpl);

	  this.$input = $ele.find('.am-upload-input');
	  this.$button = $ele.find('.am-upload-btn');
	  this.$list = $('<ul class="am-upload-list"></ul>');
	  $ele.append(this.$list);

	  this.fileList = [];

	  this.test();
	  this.start();
	};

	// full test for all features
	Upload.prototype.test = function() {
	  if ($.fn.progressbar) {
	    this.isPB = true;
	  }
	};

	Upload.prototype.eventBind = function() {
	  this.delete();
	};

	Upload.prototype.start = function() {
	  var _this = this;

	  this.$input.on('change', function() {
	    $.each(this.files, function() {
	      var uuid = UI.utils.generateGUID('am-upload');

	      var size = _this.options.size;
	      if (size && (size * 1024) > this.size ) {
	        alert('文件大小最大为' + size);
	        return false;
	      }

	      function typeCheck(types, t) {
	        var s = false;
	        console.log(t)
	        types.forEach(function(type) {
	          if (type === t) {
	            s = true;
	            return;
	          }
	        })
	        return !s;
	      }

	      if (typeCheck(_this.options.type, this.type)) {
	        alert('不支持的类型 -- unsupported file type');
	        return false;
	      }

	      this.uuid = uuid;
	      var $pb = _this.tpl(this, uuid);
	      _this.formdata(this, $pb);
	    });
	  });
	};

	Upload.prototype.formdata = function(file, pb) {
	  var formData = new FormData(); // TODO: check compatility
	  var data = this.options.data;
	  formData.append("file", file);

	  // add extra form data
	  if ($.type(data) === 'object') {
	    for (var key in data) {
	      formData.append(key, data[key]);
	    }
	  }

	  AjaxUpload(formData, file, this, pb);
	};

	// TODO： check file type and size
	Upload.prototype.beforeUpload = function() {
	}

	// For every file to create a AjaxuUplaod instance
	function AjaxUpload(data, raw, that, pb) {
	  var header = {
	    url: '',
	    type: 'POST',
	    cache: false,
	    data: data,
	    timeout: 0,
	    processData: false,
	    contentType: false,
	    xhr: function() {
	      var myXhr = $.ajaxSettings.xhr();

	      if (myXhr.upload){
	        myXhr.upload.addEventListener('progress',  function(e) {
	          if (e.lengthComputable) {
	            var percent = e.loaded / e.total * 100;

	            if (that.isPB) {
	              $(pb).progressbar('set', percent);
	              if (percent === 100) {
	                // hide progressbar
	                if (that.options.hideTime) {
	                  window.setTimeout(function() {
	                    $(pb).progressbar('destory')
	                  }, that.options.hideTime);
	                }
	              };
	            };

	            if (percent === 100) {
	              that.updateFiles(raw);
	              that.$element.trigger('complete.upload.amui');
	            };
	          };
	        }, false);
	      };

	      return myXhr;
	    }
	  };
	  // set request headers
	  $.extend(header, that.options.header);

	  $.ajax(header);
	};

	Upload.prototype.tpl = function(file, uuid) {

	  var $t = $('<li class="am-upload-item"><div class="am-upload-name">' + file.name + '</div></li>');
	  var $del = $('<span class="am-upload-remove">删除</span>');
	  var $pb = $('<div id=' + file.name + '></div>');

	  $t.attr('uuid', uuid);
	  $t.append($del);
	  $t.append($pb);

	  this.$list.append($t);

	  if (this.isPB) {
	    $($pb).progressbar({
	      size:'xs'
	    });
	  }

	  return $pb;
	};

	Upload.prototype.delete = function() {
	  var _this = this;

	  this.$element.on('click.upload.amui', '.am-upload-remove', function() {

	    var par = $(this).parent();
	    var uuid = par.attr('uuid')
	    par.remove();
	    _this.deleteFiles(uuid);

	    _this.$element.trigger('delete.upload.amui'); // event hook
	  });
	};

	Upload.prototype.getFileList = function() {
	  return this.fileList;
	};

	// delete matched file from fileList
	Upload.prototype.deleteFiles = function(value) {
	  var _this = this;
	  this.fileList.forEach(function(file, i) {
	    if (file.uuid === value) {
	      _this.fileList.splice(i, 1);
	    }
	  })
	};

	Upload.prototype.updateFiles = function(data) {
	  this.fileList.push(data);
	};

	// TODO: preview the file if it's img
	Upload.prototype.preview = function() {

	};


	UI.plugin('upload', Upload);

	UI.ready(function(context) {
	  $('[data-am-upload]', context).upload();
	});

	module.exports = Upload;


/***/ },
/* 28 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var $ = __webpack_require__(1);
	var UI = __webpack_require__(2);

	var Validator = function(element, options) {
	  this.options = $.extend({}, Validator.DEFAULTS, options);
	  this.options.patterns = $.extend({}, Validator.patterns,
	    this.options.patterns);
	  var locales = this.options.locales;
	  !Validator.validationMessages[locales] && (this.options.locales = 'zh_CN');
	  this.$element = $(element);
	  this.init();
	};

	Validator.DEFAULTS = {
	  debug: false,
	  locales: 'zh_CN',
	  H5validation: false,
	  H5inputType: ['email', 'url', 'number'],
	  patterns: {},
	  patternClassPrefix: 'js-pattern-',
	  activeClass: 'am-active',
	  inValidClass: 'am-field-error',
	  validClass: 'am-field-valid',

	  validateOnSubmit: true,
	  alwaysRevalidate: false,
	  // Elements to validate with allValid (only validating visible elements)
	  // :input: selects all input, textarea, select and button elements.
	  // @since 2.5: move `:visible` to `ignore` option (became to `:hidden`)
	  allFields: ':input:not(:submit, :button, :disabled, .am-novalidate)',

	  // ignored elements
	  // @since 2.5
	  ignore: ':hidden:not([data-am-selected], .am-validate)',

	  // Custom events
	  customEvents: 'validate',

	  // Keyboard events
	  keyboardFields: ':input:not(:submit, :button, :disabled, .am-novalidate)',
	  keyboardEvents: 'focusout, change', // keyup, focusin

	  // bind `keyup` event to active field
	  activeKeyup: false,
	  textareaMaxlenthKeyup: true,

	  // Mouse events
	  pointerFields: 'input[type="range"]:not(:disabled, .am-novalidate), ' +
	  'input[type="radio"]:not(:disabled, .am-novalidate), ' +
	  'input[type="checkbox"]:not(:disabled, .am-novalidate), ' +
	  'select:not(:disabled, .am-novalidate), ' +
	  'option:not(:disabled, .am-novalidate)',
	  pointerEvents: 'click',

	  onValid: function(validity) {
	  },

	  onInValid: function(validity) {
	  },

	  markValid: function(validity) {
	    // this is Validator instance
	    var options = this.options;
	    var $field = $(validity.field);
	    var $parent = $field.closest('.am-form-group');

	    $field.addClass(options.validClass).removeClass(options.inValidClass);
	    $parent.addClass('am-form-success').removeClass('am-form-error');
	    options.onValid.call(this, validity);
	  },

	  markInValid: function(validity) {
	    var options = this.options;
	    var $field = $(validity.field);
	    var $parent = $field.closest('.am-form-group');

	    $field.addClass(options.inValidClass + ' ' + options.activeClass).
	      removeClass(options.validClass);
	    $parent.addClass('am-form-error').removeClass('am-form-success');
	    options.onInValid.call(this, validity);
	  },

	  validate: function(validity) {
	    // return validity;
	  },

	  submit: null
	};

	Validator.VERSION = '3.0.0-alpha.beta';

	/* jshint -W101 */
	Validator.patterns = {
	  email: /^((([a-zA-Z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-zA-Z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-zA-Z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-zA-Z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-zA-Z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-zA-Z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-zA-Z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-zA-Z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-zA-Z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-zA-Z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/,

	  url: /^(https?|ftp):\/\/(((([a-zA-Z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-zA-Z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-zA-Z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-zA-Z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-zA-Z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-zA-Z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-zA-Z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-zA-Z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-zA-Z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-zA-Z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-zA-Z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-zA-Z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-zA-Z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/,

	  // Number, including positive, negative, and floating decimal
	  number: /^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/,
	  dateISO: /^\d{4}[\/\-]\d{1,2}[\/\-]\d{1,2}$/,
	  integer: /^-?\d+$/
	};
	/* jshint +W101 */

	Validator.validationMessages = {
	  zh_CN: {
	    valueMissing: '请填写（选择）此字段',
	    customError: {
	      tooShort: '至少填写 %s 个字符',
	      checkedOverflow: '至多选择 %s 项',
	      checkedUnderflow: '至少选择 %s 项'
	    },
	    patternMismatch: '请按照要求的格式填写',
	    rangeOverflow: '请填写小于等于 %s 的值',
	    rangeUnderflow: '请填写大于等于 %s 的值',
	    stepMismatch: '',
	    tooLong: '至多填写 %s 个字符',
	    typeMismatch: '请按照要求的类型填写'
	  }
	};

	Validator.ERROR_MAP = {
	  tooShort: 'minlength',
	  checkedOverflow: 'maxchecked',
	  checkedUnderflow: 'minchecked',
	  rangeOverflow: 'max',
	  rangeUnderflow: 'min',
	  tooLong: 'maxlength'
	};

	// TODO: 考虑表单元素不是 form 子元素的情形
	// TODO: change/click/focusout 同时触发时处理重复
	// TODO: 显示提示信息

	Validator.prototype.init = function() {
	  var _this = this;
	  var $element = this.$element;
	  var options = this.options;

	  // using H5 form validation if option set and supported
	  if (options.H5validation && UI.support.formValidation) {
	    return false;
	  }

	  // disable HTML5 form validation
	  $element.attr('novalidate', 'novalidate');

	  function regexToPattern(regex) {
	    var pattern = regex.toString();
	    return pattern.substring(1, pattern.length - 1);
	  }

	  // add pattern to H5 input type
	  $.each(options.H5inputType, function(i, type) {
	    var $field = $element.find('input[type=' + type + ']');
	    if (!$field.attr('pattern') &&
	      !$field.is('[class*=' + options.patternClassPrefix + ']')) {
	      $field.attr('pattern', regexToPattern(options.patterns[type]));
	    }
	  });

	  // add pattern to .js-pattern-xx
	  $.each(options.patterns, function(key, value) {
	    var $field = $element.find('.' + options.patternClassPrefix + key);
	    !$field.attr('pattern') && $field.attr('pattern', regexToPattern(value));
	  });

	  $element.on('submit.validator.amui', function(e) {
	    // user custom submit handler
	    if (typeof options.submit === 'function') {
	      return options.submit.call(_this, e);
	    }

	    if (options.validateOnSubmit) {
	      var formValidity = _this.isFormValid();

	      // sync validate, return result
	      if ($.type(formValidity) === 'boolean') {
	        return formValidity;
	      }

	      if ($element.data('amui.checked')) {
	        return true;
	      } else {
	        $.when(formValidity).then(function() {
	          // done, submit form
	          $element.data('amui.checked', true).submit();
	        }, function() {
	          // fail
	          $element.data('amui.checked', false).
	            find('.' + options.inValidClass).eq(0).focus();
	        });
	        return false;
	      }
	    }
	  });

	  function bindEvents(fields, eventFlags, debounce) {
	    var events = eventFlags.split(',');
	    var validate = function(e) {
	      // console.log(e.type);
	      _this.validate(this);
	    };

	    if (debounce) {
	      validate = UI.utils.debounce(validate, debounce);
	    }

	    $.each(events, function(i, event) {
	      $element.on(event + '.validator.amui', fields, validate);
	    });
	  }

	  bindEvents(':input', options.customEvents);
	  bindEvents(options.keyboardFields, options.keyboardEvents);
	  bindEvents(options.pointerFields, options.pointerEvents);

	  if (options.textareaMaxlenthKeyup) {
	    bindEvents('textarea[maxlength]', 'keyup', 50);
	  }

	  if (options.activeKeyup) {
	    bindEvents('.am-active', 'keyup', 50);
	  }

	  /*if (options.errorMessage === 'tooltip') {
	    this.$tooltip = $('<div></div>', {
	      'class': 'am-validator-message',
	      id: UI.utils.generateGUID('am-validator-message')
	    });

	    $(document.body).append(this.$tooltip);
	  }*/
	};

	Validator.prototype.isValid = function(field) {
	  var $field = $(field);
	  var options = this.options;
	  // valid field not has been validated
	  if ($field.data('validity') === undefined || options.alwaysRevalidate) {
	    this.validate(field);
	  }

	  return $field.data('validity') && $field.data('validity').valid;
	};

	Validator.prototype.validate = function(field) {
	  var _this = this;
	  var $element = this.$element;
	  var options = this.options;
	  var $field = $(field);

	  // Validate equal, e.g. confirm password
	  var equalTo = $field.data('equalTo');
	  if (equalTo) {
	    $field.attr('pattern', '^' + $element.find(equalTo).val() + '$');
	  }

	  var pattern = $field.attr('pattern') || false;
	  var re = new RegExp(pattern);
	  var $radioGroup = null;
	  var $checkboxGroup = null;
	  // if checkbox, return `:chcked` length
	  // NOTE: checkbox and radio should have name attribute
	  var value = ($field.is('[type=checkbox]')) ?
	    ($checkboxGroup = $element.find('input[name="' + field.name + '"]')).
	      filter(':checked').length : ($field.is('[type=radio]') ?
	  ($radioGroup = this.$element.find('input[name="' + field.name + '"]')).
	    filter(':checked').length > 0 : $field.val());

	  // if checkbox, valid the first input of checkbox group
	  $field = ($checkboxGroup && $checkboxGroup.length) ?
	    $checkboxGroup.first() : $field;

	  var required = ($field.attr('required') !== undefined) &&
	    ($field.attr('required') !== 'false');
	  var maxLength = parseInt($field.attr('maxlength'), 10);
	  var minLength = parseInt($field.attr('minlength'), 10);
	  var min = Number($field.attr('min'));
	  var max = Number($field.attr('max'));
	  var validity = this.createValidity({field: $field[0], valid: true});

	  // Debug
	  if (options.debug && window.console) {
	    console.log('Validate: value -> [' + value + ', regex -> [' + re +
	    '], required -> ' + required);
	    console.log('Regex test: ' + re.test(value) + ', Pattern: ' + pattern);
	  }

	  // check value length
	  if (!isNaN(maxLength) && value.length > maxLength) {
	    validity.valid = false;
	    validity.tooLong = true;
	  }

	  if (!isNaN(minLength) && value.length < minLength) {
	    validity.valid = false;
	    validity.customError = 'tooShort';
	  }

	  // check minimum and maximum
	  // https://developer.mozilla.org/en-US/docs/Web/HTML/Element/Input
	  // TODO: 日期验证最小值和最大值 min/max
	  if (!isNaN(min) && Number(value) < min) {
	    validity.valid = false;
	    validity.rangeUnderflow = true;
	  }

	  if (!isNaN(max) && Number(value) > max) {
	    validity.valid = false;
	    validity.rangeOverflow = true;
	  }

	  // check required
	  if (required && !value) {
	    validity.valid = false;
	    validity.valueMissing = true;
	  } else if (($checkboxGroup || $field.is('select[multiple="multiple"]')) &&
	    value) {
	    // check checkboxes / multiple select with `minchecked`/`maxchecked` attr
	    // var $multipleField = $checkboxGroup ? $checkboxGroup.first() : $field;

	    // if is select[multiple="multiple"], return selected length
	    value = $checkboxGroup ? value : value.length;

	    // at least checked
	    var minChecked = parseInt($field.attr('minchecked'), 10);
	    // at most checked
	    var maxChecked = parseInt($field.attr('maxchecked'), 10);

	    if (!isNaN(minChecked) && value < minChecked) {
	      // console.log('At least [%d] items checked！', maxChecked);
	      validity.valid = false;
	      validity.customError = 'checkedUnderflow';
	    }

	    if (!isNaN(maxChecked) && value > maxChecked) {
	      // console.log('At most [%d] items checked！', maxChecked);
	      validity.valid = false;
	      validity.customError = 'checkedOverflow';
	    }
	  } else if (pattern && !re.test(value) && value) { // check pattern
	    validity.valid = false;
	    validity.patternMismatch = true;
	  }

	  var validateComplete = function(validity) {
	    this.markField(validity);

	    var event = $.Event('validated.field.validator.amui');
	    event.validity = validity;

	    $field.trigger(event).data('validity', validity);

	    // validate the radios/checkboxes with the same name
	    var $fields = $radioGroup || $checkboxGroup;
	    if ($fields) {
	      $fields.not($field).data('validity', validity).each(function() {
	        validity.field = this;
	        _this.markField(validity);
	      });
	    }

	    return validity;
	  };

	  // Run custom validate
	  // NOTE: async custom validate should return Deferred project
	  var customValidate;
	  (typeof options.validate === 'function') &&
	    (customValidate = options.validate.call(this, validity));

	  // Deferred
	  if (customValidate) {
	    var dfd = new $.Deferred();
	    $field.data('amui.dfdValidity', dfd.promise());
	    return $.when(customValidate).always(function(validity) {
	      dfd[validity.valid ? 'resolve' : 'reject'](validity);
	      validateComplete.call(_this, validity);
	    });
	  }

	  validateComplete.call(this, validity);
	};

	Validator.prototype.markField = function(validity) {
	  var options = this.options;
	  var flag = 'mark' + (validity.valid ? '' : 'In') + 'Valid';
	  options[flag] && options[flag].call(this, validity);
	};

	// check all fields in the form are valid
	Validator.prototype.validateForm = function() {
	  var _this = this;
	  var $element = this.$element;
	  var options = this.options;
	  var $allFields = $element.find(options.allFields).not(options.ignore);
	  var radioNames = [];
	  var valid = true;
	  var formValidity = [];
	  var $inValidFields = $([]);
	  var promises = [];
	  // for async validate
	  var async = false;

	  $element.trigger('validate.form.validator.amui');

	  // Filter radio with the same name and keep only one,
	  //   since they will be checked as a group by validate()
	  var $filteredFields = $allFields.filter(function(index) {
	    var name;
	    if (this.tagName === 'INPUT' && this.type === 'radio') {
	      name = this.name;
	      if (radioNames[name] === true) {
	        return false;
	      }
	      radioNames[name] = true;
	    }
	    return true;
	  });

	  $filteredFields.each(function() {
	    var $this = $(this);
	    var fieldValid = _this.isValid(this);
	    var fieldValidity = $this.data('validity');

	    valid = !!fieldValid && valid;
	    formValidity.push(fieldValidity);

	    if (!fieldValid) {
	      $inValidFields = $inValidFields.add($(this), $element);
	    }

	    // async validity
	    var promise = $this.data('amui.dfdValidity');

	    if (promise) {
	      promises.push(promise);
	      async = true;
	    } else {
	      // convert sync validity to Promise
	      var dfd = new $.Deferred();
	      promises.push(dfd.promise());
	      dfd[fieldValid ? 'resolve' : 'reject'](fieldValidity);
	    }
	  });

	  // NOTE: If there are async validity, the valid may be not exact result.
	  var validity = {
	    valid: valid,
	    $invalidFields: $inValidFields,
	    validity: formValidity,
	    promises: promises,
	    async: async
	  };

	  $element.trigger('validated.form.validator.amui', validity);

	  return validity;
	};

	Validator.prototype.isFormValid = function() {
	  var _this = this;
	  var formValidity = this.validateForm();
	  var triggerValid = function(type) {
	    _this.$element.trigger(type + '.validator.amui');
	  };

	  if (formValidity.async) {
	    var masterDfd = new $.Deferred();

	    $.when.apply(null, formValidity.promises).then(function() {
	      masterDfd.resolve();
	      triggerValid('valid');
	    }, function() {
	      masterDfd.reject();
	      triggerValid('invalid');
	    });

	    return masterDfd.promise();
	  } else {
	    if (!formValidity.valid) {
	      var $first = formValidity.$invalidFields.first();

	      // Selected plugin support
	      // @since 2.5
	      if ($first.is('[data-am-selected]')) {
	        $first = $first.next('.am-selected').find('.am-selected-btn');
	      }

	      $first.focus();
	      triggerValid('invalid');
	      return false;
	    }

	    triggerValid('valid');
	    return true;
	  }
	};

	// customErrors:
	//    1. tooShort
	//    2. checkedOverflow
	//    3. checkedUnderflow
	Validator.prototype.createValidity = function(validity) {
	  return $.extend({
	    customError: validity.customError || false,
	    patternMismatch: validity.patternMismatch || false,
	    rangeOverflow: validity.rangeOverflow || false, // higher than maximum
	    rangeUnderflow: validity.rangeUnderflow || false, // lower than  minimum
	    stepMismatch: validity.stepMismatch || false,
	    tooLong: validity.tooLong || false,
	    // value is not in the correct syntax
	    typeMismatch: validity.typeMismatch || false,
	    valid: validity.valid || true,
	    // Returns true if the element has no value but is a required field
	    valueMissing: validity.valueMissing || false
	  }, validity);
	};

	Validator.prototype.getValidationMessage = function(validity) {
	  var messages = Validator.validationMessages[this.options.locales];
	  var error;
	  var message;
	  var placeholder = '%s';
	  var $field = $(validity.field);

	  if ($field.is('[type="checkbox"]') || $field.is('[type="radio"]')) {
	    $field = this.$element.find('[name=' + $field.attr('name') + ']').first();
	  }

	  // get error name
	  $.each(validity, function(key, val) {
	    // skip `field` and `valid`
	    if (key === 'field' || key === 'valid') {
	      return key;
	    }

	    // Amaze UI custom error type
	    if (key === 'customError' && val) {
	      error = val;
	      messages = messages.customError;
	      return false;
	    }

	    // W3C specs error type
	    if (val === true) {
	      error = key;
	      return false;
	    }
	  });

	  message = messages[error] || undefined;

	  if (message && Validator.ERROR_MAP[error]) {
	    message = message.replace(placeholder,
	      $field.attr(Validator.ERROR_MAP[error]) || '规定的');
	  }

	  return message;
	};

	// remove valid mark
	Validator.prototype.removeMark = function() {
	  this.$element
	    .find('.am-form-success, .am-form-error, .' + this.options.inValidClass +
	      ', .' + this.options.validClass)
	    .removeClass([
	      'am-form-success',
	      'am-form-error',
	      this.options.inValidClass,
	      this.options.validClass
	    ].join(' '));
	};

	// @since 2.5
	Validator.prototype.destroy = function() {
	  this.removeMark();

	  // Remove data
	  // - Validator.prototype.init -> $element.data('amui.checked')
	  // - Validator.prototype.validateForm
	  // - Validator.prototype.isValid
	  this.$element.removeData('amui.validator amui.checked')
	    .off('.validator.amui')
	    .find(this.options.allFields).removeData('validity amui.dfdValidity');
	};

	UI.plugin('validator', Validator);

	// init code
	UI.ready(function(context) {
	  $('[data-am-validator]', context).validator();
	});

	module.exports = Validator;


/***/ },
/* 29 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var UI = __webpack_require__(2);

	var cookie = {
	  get: function(name) {
	    var cookieName = encodeURIComponent(name) + '=';
	    var cookieStart = document.cookie.indexOf(cookieName);
	    var cookieValue = null;
	    var cookieEnd;

	    if (cookieStart > -1) {
	      cookieEnd = document.cookie.indexOf(';', cookieStart);
	      if (cookieEnd == -1) {
	        cookieEnd = document.cookie.length;
	      }
	      cookieValue = decodeURIComponent(document.cookie.substring(cookieStart +
	      cookieName.length, cookieEnd));
	    }

	    return cookieValue;
	  },

	  set: function(name, value, expires, path, domain, secure) {
	    var cookieText = encodeURIComponent(name) + '=' +
	      encodeURIComponent(value);

	    if (expires instanceof Date) {
	      cookieText += '; expires=' + expires.toUTCString();
	    }

	    if (path) {
	      cookieText += '; path=' + path;
	    }

	    if (domain) {
	      cookieText += '; domain=' + domain;
	    }

	    if (secure) {
	      cookieText += '; secure';
	    }

	    document.cookie = cookieText;
	  },

	  unset: function(name, path, domain, secure) {
	    this.set(name, '', new Date(0), path, domain, secure);
	  }
	};

	UI.utils = UI.utils || {};

	module.exports = UI.utils.cookie = cookie;


/***/ },
/* 30 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	/**
	 * @see https://github.com/sindresorhus/screenfull.js
	 * @license MIT © Sindre Sorhus
	 */

	var UI = __webpack_require__(2);
	var screenfull = (function() {
	  var keyboardAllowed = typeof Element !== 'undefined' &&
	    'ALLOW_KEYBOARD_INPUT' in Element;

	  var fn = (function() {
	    var val;
	    var valLength;

	    var fnMap = [
	      [
	        'requestFullscreen',
	        'exitFullscreen',
	        'fullscreenElement',
	        'fullscreenEnabled',
	        'fullscreenchange',
	        'fullscreenerror'
	      ],
	      // new WebKit
	      [
	        'webkitRequestFullscreen',
	        'webkitExitFullscreen',
	        'webkitFullscreenElement',
	        'webkitFullscreenEnabled',
	        'webkitfullscreenchange',
	        'webkitfullscreenerror'

	      ],
	      // old WebKit (Safari 5.1)
	      [
	        'webkitRequestFullScreen',
	        'webkitCancelFullScreen',
	        'webkitCurrentFullScreenElement',
	        'webkitCancelFullScreen',
	        'webkitfullscreenchange',
	        'webkitfullscreenerror'

	      ],
	      [
	        'mozRequestFullScreen',
	        'mozCancelFullScreen',
	        'mozFullScreenElement',
	        'mozFullScreenEnabled',
	        'mozfullscreenchange',
	        'mozfullscreenerror'
	      ],
	      [
	        'msRequestFullscreen',
	        'msExitFullscreen',
	        'msFullscreenElement',
	        'msFullscreenEnabled',
	        'MSFullscreenChange',
	        'MSFullscreenError'
	      ]
	    ];

	    var i = 0;
	    var l = fnMap.length;
	    var ret = {};

	    for (; i < l; i++) {
	      val = fnMap[i];
	      if (val && val[1] in document) {
	        for (i = 0, valLength = val.length; i < valLength; i++) {
	          ret[fnMap[0][i]] = val[i];
	        }
	        return ret;
	      }
	    }

	    return false;
	  })();

	  var screenfull = {
	    request: function(elem) {
	      var request = fn.requestFullscreen;

	      elem = elem || document.documentElement;

	      // Work around Safari 5.1 bug: reports support for
	      // keyboard in fullscreen even though it doesn't.
	      // Browser sniffing, since the alternative with
	      // setTimeout is even worse.
	      if (/5\.1[\.\d]* Safari/.test(navigator.userAgent)) {
	        elem[request]();
	      } else {
	        elem[request](keyboardAllowed && Element.ALLOW_KEYBOARD_INPUT);
	      }
	    },
	    exit: function() {
	      document[fn.exitFullscreen]();
	    },
	    toggle: function(elem) {
	      if (this.isFullscreen) {
	        this.exit();
	      } else {
	        this.request(elem);
	      }
	    },
	    raw: fn
	  };

	  if (!fn) {
	    return false;
	  }

	  Object.defineProperties(screenfull, {
	    isFullscreen: {
	      get: function() {
	        return !!document[fn.fullscreenElement];
	      }
	    },
	    element: {
	      enumerable: true,
	      get: function() {
	        return document[fn.fullscreenElement];
	      }
	    },
	    enabled: {
	      enumerable: true,
	      get: function() {
	        // Coerce to boolean in case of old WebKit
	        return !!document[fn.fullscreenEnabled];
	      }
	    }
	  });

	  screenfull.VERSION = '3.0.0';

	  return screenfull;
	})();

	module.exports = UI.fullscreen = screenfull;


/***/ },
/* 31 */
/***/ function(module, exports, __webpack_require__) {

	/* WEBPACK VAR INJECTION */(function(global) {'use strict';

	var UI = __webpack_require__(2);

	/**
	 * store.js
	 * @see https://github.com/marcuswestin/store.js
	 * @license https://github.com/marcuswestin/store.js/blob/master/LICENSE
	 */

	var store = {};
	var win = (typeof window != 'undefined' ? window : global);
	var localStorageName = 'localStorage';
	var storage;

	store.disabled = false;
	store.version = '1.3.20';

	store.set = function(key, value) {
	};

	store.get = function(key, defaultVal) {
	};

	store.has = function(key) {
	  return store.get(key) !== undefined;
	};

	store.remove = function(key) {
	};

	store.clear = function() {
	};

	store.transact = function(key, defaultVal, transactionFn) {
	  if (transactionFn == null) {
	    transactionFn = defaultVal;
	    defaultVal = null;
	  }
	  if (defaultVal == null) {
	    defaultVal = {};
	  }

	  var val = store.get(key, defaultVal);
	  transactionFn(val);
	  store.set(key, val);
	};

	store.getAll = function() {
	};

	store.forEach = function() {
	};

	store.serialize = function(value) {
	  return JSON.stringify(value);
	};

	store.deserialize = function(value) {
	  if (typeof value != 'string') {
	    return undefined;
	  }

	  try {
	    return JSON.parse(value);
	  } catch (e) {
	    return value || undefined;
	  }
	};

	// Functions to encapsulate questionable FireFox 3.6.13 behavior
	// when about.config::dom.storage.enabled === false
	// See https://github.com/marcuswestin/store.js/issues#issue/13
	function isLocalStorageNameSupported() {
	  try {
	    return (localStorageName in win && win[localStorageName]);
	  } catch (err) {
	    return false;
	  }
	}

	if (isLocalStorageNameSupported()) {
	  storage = win[localStorageName];

	  store.set = function(key, val) {
	    if (val === undefined) {
	      return store.remove(key);
	    }
	    storage.setItem(key, store.serialize(val));
	    return val;
	  };

	  store.get = function(key, defaultVal) {
	    var val = store.deserialize(storage.getItem(key));
	    return (val === undefined ? defaultVal : val);
	  };

	  store.remove = function(key) {
	    storage.removeItem(key);
	  };

	  store.clear = function() {
	    storage.clear();
	  };

	  store.getAll = function() {
	    var ret = {};
	    store.forEach(function(key, val) {
	      ret[key] = val;
	    });
	    return ret;
	  };

	  store.forEach = function(callback) {
	    for (var i = 0; i < storage.length; i++) {
	      var key = storage.key(i);
	      callback(key, store.get(key));
	    }
	  };
	}

	try {
	  var testKey = '__storejs__';
	  store.set(testKey, testKey);
	  if (store.get(testKey) != testKey) {
	    store.disabled = true;
	  }
	  store.remove(testKey);
	} catch (e) {
	  store.disabled = true;
	}

	store.enabled = !store.disabled;

	module.exports = UI.store = store;

	/* WEBPACK VAR INJECTION */}.call(exports, (function() { return this; }())))

/***/ }
/******/ ])
});
;