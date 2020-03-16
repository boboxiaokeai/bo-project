package cn.bo.project.base.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author zhangbo
 * @Date 2020/1/3 9:31
 * @Description 用户信息model
 * @PackageName cn.bo.project.base.core.model
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
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
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	/**
	 * 性别（1：男 2：女）
	 */
	private Integer sex;

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
     * 同步工作流引擎1同步0不同步
     */
    private String activitiSync;

	/**
	 * 创建时间
	 */
	private Date createTime;
}
