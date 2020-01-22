package cn.bo.project.admin.modules.system.aspect;

import cn.bo.project.admin.modules.system.entity.SysLog;
import cn.bo.project.admin.modules.system.service.ISysLogService;
import cn.bo.project.base.annotation.Log;
import cn.bo.project.base.constant.CommonConstant;
import cn.bo.project.base.core.model.LoginUser;
import cn.bo.project.base.utils.IPUtils;
import cn.bo.project.base.utils.SpringContextUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * @Author zhangbo
 * @Date 2020/1/4 12:55
 * @Description 系统日志的切面处理类
 * @PackageName cn.bo.project.admin.modules.system.aspect
 **/
@Aspect
@Component
public class LogAspect {
	@Autowired
	private ISysLogService sysLogService;
	
	@Pointcut("@annotation(cn.bo.project.base.annotation.Log)")
	public void logPointCut() {
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLog sysLog = new SysLog();
		Log syslog = method.getAnnotation(Log.class);
		if(syslog != null){
			//注解上的描述,操作日志内容
			sysLog.setLogContent(syslog.value());
			sysLog.setLogType(syslog.logType());
			
		}

		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		
		
		//设置操作类型
		if (sysLog.getLogType() == CommonConstant.LOG_TYPE_2) {
			sysLog.setOperateType(getOperateType(methodName, syslog.operateType()));
		}

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSONObject.toJSONString(args);
			sysLog.setRequestParam(params);
		}catch (Exception e){

		}

		//获取request
		HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
		//设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));

		//获取登录用户信息
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		if(sysUser!=null){
			sysLog.setUserid(sysUser.getUsername());
			sysLog.setUsername(sysUser.getRealname());

		}
		//耗时
		sysLog.setCostTime(time);
		sysLog.setCreateTime(new Date());
		//保存系统日志
		sysLogService.save(sysLog);
	}
	/**
	 * 获取操作类型
	 */
	private int getOperateType(String methodName,int operateType) {
		if (operateType > 0) {
			return operateType;
		}
        if (methodName.startsWith("list")) {
        	return CommonConstant.OPERATE_TYPE_1;
		}
        if (methodName.startsWith("add")) {
        	return CommonConstant.OPERATE_TYPE_2;
		}
        if (methodName.startsWith("edit")) {
        	return CommonConstant.OPERATE_TYPE_3;
		}
        if (methodName.startsWith("delete")) {
        	return CommonConstant.OPERATE_TYPE_4;
		}
        if (methodName.startsWith("import")) {
        	return CommonConstant.OPERATE_TYPE_5;
		}
        if (methodName.startsWith("export")) {
        	return CommonConstant.OPERATE_TYPE_6;
		}
		return CommonConstant.OPERATE_TYPE_1;
	}
}
