/**
 * service就是比较底层的api，biz就是应用层的api
 * 跨模块调用需要通过biz层，比如我现在要在商城中，做一个下单功能 牵涉到商品，库存，活动等等，需要通过biz进行服务组合处理
 */
package tech.wetech.admin.modules.system.biz;