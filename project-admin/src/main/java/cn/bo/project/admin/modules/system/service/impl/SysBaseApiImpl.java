package cn.bo.project.admin.modules.system.service.impl;

import cn.bo.project.admin.modules.system.entity.SysLog;
import cn.bo.project.admin.modules.system.entity.SysUser;
import cn.bo.project.admin.modules.system.mapper.SysLogMapper;
import cn.bo.project.admin.modules.system.mapper.SysUserMapper;
import cn.bo.project.base.core.api.ISysBaseAPI;
import cn.bo.project.base.core.model.ComboModel;
import cn.bo.project.base.core.model.DictModel;
import cn.bo.project.base.core.model.LoginUser;
import cn.bo.project.base.core.model.SysDepartModel;
import cn.bo.project.base.utils.IPUtils;
import cn.bo.project.base.utils.SpringContextUtils;
import cn.bo.project.base.utils.oConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @Description: 底层共通业务API，提供其他独立模块调用
 * @Author: scott
 * @Date:2019-4-20 
 * @Version:V1.0
 */
@Slf4j
@Service
public class SysBaseApiImpl implements ISysBaseAPI {
	/** 当前系统数据库类型 */
	public static String DB_TYPE = "";
	
	@Resource
	private SysLogMapper sysLogMapper;
	@Autowired
	private SysUserMapper userMapper;
	
	@Override
	public void addLog(String LogContent, Integer logType, Integer operatetype) {
		SysLog sysLog = new SysLog();
		//注解上的描述,操作日志内容
		sysLog.setLogContent(LogContent);
		sysLog.setLogType(logType);
		sysLog.setOperateType(operatetype);

		//请求的方法名
		//请求的参数

		try {
			//获取request
			HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
			//设置IP地址
			sysLog.setIp(IPUtils.getIpAddr(request));
		} catch (Exception e) {
			sysLog.setIp("127.0.0.1");
		}

		//获取登录用户信息
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser!=null){
			sysLog.setUserid(sysUser.getUserName());
			sysLog.setUsername(sysUser.getRealName());
		}
		sysLog.setCreateTime(new Date());
		//保存系统日志
		sysLogMapper.insert(sysLog);
	}

	@Override
	public LoginUser getUserByName(String username) {
		if(oConvertUtils.isEmpty(username)) {
			return null;
		}
		LoginUser loginUser = new LoginUser();
		SysUser sysUser = userMapper.selectUserByUserName(username);
		if(sysUser==null) {
			return null;
		}
		BeanUtils.copyProperties(sysUser, loginUser);
		return loginUser;
	}

	@Override
	public LoginUser getUserById(String id) {
		return null;
	}

	@Override
	public List<String> getRolesByUsername(String username) {
		return null;
	}

	@Override
	public List<String> getDepartIdsByUsername(String username) {
		return null;
	}

	@Override
	public List<String> getDepartNamesByUsername(String username) {
		return null;
	}

	@Override
	public String getDatabaseType() throws SQLException {
		return null;
	}

	@Override
	public List<DictModel> queryDictItemsByCode(String code) {
		return null;
	}

	@Override
	public List<DictModel> queryAllDict() {
		return null;
	}

	@Override
	public List<DictModel> queryTableDictItemsByCode(String table, String text, String code) {
		return null;
	}

	@Override
	public List<DictModel> queryAllDepartBackDictModel() {
		return null;
	}

	@Override
	public void sendSysAnnouncement(String fromUser, String toUser, String title, String msgContent) {

	}

	@Override
	public List<DictModel> queryFilterTableDictInfo(String table, String text, String code, String filterSql) {
		return null;
	}

	@Override
	public List<ComboModel> queryAllUser() {
		return null;
	}

	@Override
	public List<ComboModel> queryAllUser(String[] userIds) {
		return null;
	}

	@Override
	public List<ComboModel> queryAllRole() {
		return null;
	}

	@Override
	public List<ComboModel> queryAllRole(String[] roleIds) {
		return null;
	}

	@Override
	public List<String> getRoleIdsByUsername(String username) {
		return null;
	}

	@Override
	public String getDepartIdsByOrgCode(String orgCode) {
		return null;
	}

	@Override
	public DictModel getParentDepartId(String departId) {
		return null;
	}

	@Override
	public List<SysDepartModel> getAllSysDepart() {
		return null;
	}
}
