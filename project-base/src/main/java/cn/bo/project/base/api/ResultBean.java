package cn.bo.project.base.api;

import cn.bo.project.base.constant.CommonConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author zhangbo
 * @Date 2020/1/2 18:14
 * @Description 接口返回数据
 * @PackageName cn.bo.project.base.api
 **/
@Data
public class ResultBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	private String message = "操作成功！";

	/**
	 * 返回代码
	 */
	private Integer code = 0;
	
	/**
	 * 返回数据对象 data
	 */
	private T result;
	
	/**
	 * 时间戳
	 */
	private long timestamp = System.currentTimeMillis();

	public ResultBean() {
		
	}
	
	public ResultBean<T> success(String message) {
		this.message = message;
		this.code = CommonConstant.SC_OK_200;
		this.success = true;
		return this;
	}
	
	
	public static ResultBean<Object> ok() {
		ResultBean<Object> r = new ResultBean<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage("成功");
		return r;
	}
	
	public static ResultBean<Object> ok(String msg) {
		ResultBean<Object> r = new ResultBean<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage(msg);
		return r;
	}
	
	public static ResultBean<Object> ok(Object data) {
		ResultBean<Object> r = new ResultBean<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(data);
		return r;
	}
	
	public static ResultBean<Object> error(String msg) {
		return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
	}
	
	public static ResultBean<Object> error(int code, String msg) {
		ResultBean<Object> r = new ResultBean<Object>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}

	public ResultBean<T> error500(String message) {
		this.message = message;
		this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
		this.success = false;
		return this;
	}
	public ResultBean<T> error404(String message) {
		this.message = message;
		this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_404;
		this.success = false;
		return this;
	}
	/**
	 * 无权限访问返回结果
	 */
	public static ResultBean<Object> noauth(String msg) {
		return error(CommonConstant.SC_JEECG_NO_AUTHZ, msg);
	}
}