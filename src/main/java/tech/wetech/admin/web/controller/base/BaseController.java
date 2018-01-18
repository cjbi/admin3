package tech.wetech.admin.web.controller.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wetech.admin.web.dto.JsonResult;

public class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	public BaseController() {

	}

	/**
	 * 渲染失败数据
	 *
	 * @return result
	 */
	protected JsonResult renderError() {
		JsonResult result = new JsonResult();
		result.setSuccess(false);
		result.setStatus("500");
		return result;
	}

	/**
	 * 渲染失败数据（带消息）
	 *
	 * @param msg 需要返回的消息
	 * @return result
	 */
	protected JsonResult renderError(String msg) {
		JsonResult result = renderError();
		result.setMsg(msg);
		return result;
	}

	/**
	 * 渲染成功数据
	 *
	 * @return result
	 */
	protected JsonResult renderSuccess() {
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		result.setMsg("处理成功");
		result.setStatus("200");
		return result;
	}

	/**
	 * 渲染成功数据（带信息）
	 *
	 * @param msg 需要返回的信息
	 * @return result
	 */
	protected JsonResult renderSuccess(String msg) {
		JsonResult result = renderSuccess();
		result.setMsg(msg);
		return result;
	}

	/**
	 * 渲染成功数据（带数据）
	 *
	 * @param obj 需要返回的对象
	 * @return result
	 */
	protected JsonResult renderSuccess(Object obj) {
		JsonResult result = renderSuccess();
		result.setObj(obj);
		return result;
	}



}
