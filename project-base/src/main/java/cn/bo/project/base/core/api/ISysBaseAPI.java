package cn.bo.project.base.core.api;


import cn.bo.project.base.core.model.ComboModel;
import cn.bo.project.base.core.model.DictModel;
import cn.bo.project.base.core.model.LoginUser;
import cn.bo.project.base.core.model.SysDepartModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author zhangbo
 * @Date 2020/1/3 9:21
 * @Description 底层公共业务API
 * @PackageName cn.bo.project.base.core.base.entity
 **/
public interface ISysBaseAPI {

	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param logType 日志类型(0:操作日志;1:登录日志;2:定时任务)
	 * @param operatetype 操作类型(1:添加;2:修改;3:删除;)
	 */
	void addLog(String LogContent, Integer logType, Integer operatetype);
	
	/**
	  * 根据用户账号查询用户信息
	 * @param username
	 * @return
	 */
	public LoginUser getUserByName(String username);
	
	/**
	  * 根据用户id查询用户信息
	 * @param id
	 * @return
	 */
	public LoginUser getUserById(String id);
	
	/**
	 * 通过用户账号查询角色集合
	 * @param username
	 * @return
	 */
	public List<String> getRolesByUsername(String username);

	/**
	 * 通过用户账号查询部门集合
	 * @param username
	 * @return 部门 id
	 */
	List<String> getDepartIdsByUsername(String username);

	/**
	 * 通过用户账号查询部门 name
	 * @param username
	 * @return 部门 name
	 */
	List<String> getDepartNamesByUsername(String username);

	/**
	 * 获取当前数据库类型
	 * @return
	 * @throws Exception 
	 */
	public String getDatabaseType() throws SQLException;
	
	/**
	  * 获取数据字典
	 * @param code
	 * @return
	 */
	public List<DictModel> queryDictItemsByCode(String code);

	/** 查询所有的父级字典，按照create_time排序 */
	public List<DictModel> queryAllDict();

	/**
	  * 获取表数据字典
	 * @param table
	 * @param text
	 * @param code
	 * @return
	 */
    List<DictModel> queryTableDictItemsByCode(String table, String text, String code);
    
    /**
   	 * 查询所有部门 作为字典信息 id -->value,departName -->text
   	 * @return
   	 */
   	public List<DictModel> queryAllDepartBackDictModel();
   	
	/**
	 * 发送系统消息
	 * @param fromUser 发送人(用户登录账户)
	 * @param toUser  发送给(用户登录账户)
	 * @param title  消息主题
	 * @param msgContent  消息内容
	 */
	public void sendSysAnnouncement(String fromUser, String toUser, String title, String msgContent);

	/**
	 * 查询表字典 支持过滤数据
	 * @param table
	 * @param text
	 * @param code
	 * @param filterSql
	 * @return
	 */
	public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql);

	/**
	 * 获取所有有效用户
	 * @return
	 */
	public List<ComboModel> queryAllUser();

    /**
     * 获取所有有效用户 带参
     * userIds 默认选中用户
     * @return
     */
    public List<ComboModel> queryAllUser(String[] userIds);

	/**
	 * 获取所有角色
	 * @return
	 */
	public List<ComboModel> queryAllRole();

	/**
	 * 获取所有角色 带参
     * roleIds 默认选中角色
	 * @return
	 */
	public List<ComboModel> queryAllRole(String[] roleIds);

	/**
	 * 通过用户账号查询角色Id集合
	 * @param username
	 * @return
	 */
	public List<String> getRoleIdsByUsername(String username);

	/**
	 * 通过部门编号查询部门id
	 * @param orgCode
	 * @return
	 */
	public String getDepartIdsByOrgCode(String orgCode);

	/**
	 * 查询上一级部门
	 * @param departId
	 * @return
	 */
	public DictModel getParentDepartId(String departId);

	/**
	 * 查询所有部门
	 * @return
	 */
	public List<SysDepartModel> getAllSysDepart();
	
}
