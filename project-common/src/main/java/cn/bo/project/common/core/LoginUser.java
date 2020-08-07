package cn.bo.project.common.core;

import lombok.Data;

import java.util.Date;

/**
 * @Author zhangbo
 * @Date 2020/1/3 9:31
 * @Description 用户信息model
 * @PackageName cn.bo.project.common.core.model
 **/
@Data
public class LoginUser {

	/**
	 * 登录人id
	 */
	private Long id;

	/**
	 * 登录人账号
	 */
	private String userName;

	/**
	 * 登录人名字
	 */
	private String realName;

	/**
	 * 登录人密码
	 */
	private String password;

     /**
      * 当前登录部门code
      */
    private String deptId;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 生日
	 */
	private Date birthday;

	/**
	 * 性别（1：男 2：女）
	 */
	private String sex;

	/**
	 * 电子邮件
	 */
	private String email;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 状态(1：正常 2：冻结 ）
	 */
	private String status;


	private String delFlag;

	/**
	 * 创建时间
	 */
	private Date createTime;
}
