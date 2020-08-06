package cn.bo.project.system.aspect;

import cn.bo.project.common.annotation.Log;
import cn.bo.project.common.utils.HttpContextUtil;
import cn.bo.project.common.utils.IPUtils;
import cn.bo.project.common.utils.StringUtils;
import cn.bo.project.system.entity.SysLog;
import cn.bo.project.system.service.SysLogService;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;


/**
 * @Author zhangbo
 * @Date 2020/1/4 12:55
 * @Description 系统日志的切面处理类
 * @PackageName cn.bo.project.system.aspect
 **/
@Aspect
@Component
public class LogAspect {
	@Autowired
	private SysLogService sysLogService;

	@Pointcut("@annotation(cn.bo.project.common.annotation.Log)")
	public void logPointCut() {
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		// 保存日志
		saveSysLog(point, time);
		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLog sysLog = new SysLog();
		Log log = method.getAnnotation(Log.class);
		if(log != null){
			// 注解上的描述,操作日志内容
			sysLog.setLogContent(log.value());
			sysLog.setLogType(log.logType());
		}

		// 请求的方法名
		sysLog.setMethod(signature.getName());
		// 获取请求的request
		HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
		// 设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));
		// 设置请求方法类型
		sysLog.setRequestType(request.getMethod());
		// 设置请求地址
		sysLog.setRequestUrl(request.getRequestURI());

		// 请求的参数
		String params = setRequestParam(request,joinPoint.getArgs());
		sysLog.setRequestParam(StringUtils.substring(params, 0, 2000));


		// 设置操作人等信息
		sysLog.setUserid("1");
		sysLog.setUsername("admin");

		// 请求耗时
		sysLog.setCostTime(time);
		sysLog.setCreateTime(new Date());
		// 保存系统日志
		sysLogService.save(sysLog);
	}

	private String setRequestParam(HttpServletRequest request, Object[] args) {
		String requestMethod = request.getMethod();
		if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)){
			return arrayToString(args);
		}else {
			Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			return paramsMap.toString();
		}
	}

	private String arrayToString(Object[] paramsArray) {
		String params = "";
		if (paramsArray != null && paramsArray.length > 0) {
			for (int i = 0; i < paramsArray.length; i++) {
				if (!isFilterObject(paramsArray[i])) {
					Object jsonObj = JSON.toJSON(paramsArray[i]);
					params += jsonObj.toString() + " ";
				}
			}
		}
		return params.trim();
	}

	public boolean isFilterObject(final Object o) {
		return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
	}
}
