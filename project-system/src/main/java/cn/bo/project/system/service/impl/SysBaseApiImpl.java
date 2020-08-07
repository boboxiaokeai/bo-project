package cn.bo.project.system.service.impl;

import cn.bo.project.common.core.LoginUser;
import cn.bo.project.common.core.api.CommonServiceApi;
import cn.bo.project.common.utils.HttpContextUtil;
import cn.bo.project.common.utils.IPUtils;
import cn.bo.project.common.utils.StringUtils;
import cn.bo.project.system.entity.SysLog;
import cn.bo.project.system.entity.SysUser;
import cn.bo.project.system.mapper.SysLogMapper;
import cn.bo.project.system.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
public class SysBaseApiImpl implements CommonServiceApi {
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
			HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
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
		if(StringUtils.isEmpty(username)) {
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
	public List<String> getRoleIdsByUsername(String username) {
		return null;
	}

	@Override
	public String getDepartIdsByOrgCode(String orgCode) {
		return null;
	}

}
