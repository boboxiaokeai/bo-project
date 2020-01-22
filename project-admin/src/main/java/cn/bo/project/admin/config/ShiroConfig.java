package cn.bo.project.admin.config;

import cn.bo.project.admin.modules.shiro.JwtFilter;
import cn.bo.project.admin.modules.shiro.ShiroRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author zhangbo
 * @Date 2020/1/3 13:56
 * @Description shiro配置类
 * @PackageName cn.bo.project.admin.config
 **/
@Slf4j
@Configuration
public class ShiroConfig {

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 设置securityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/sys/getCheckCode", "anon"); //登录验证码接口排除
		filterChainDefinitionMap.put("/sys/login", "anon"); //登录接口排除
		filterChainDefinitionMap.put("/sys/mLogin", "anon"); //登录接口排除
		filterChainDefinitionMap.put("/sys/logout", "anon"); //登出接口排除
		filterChainDefinitionMap.put("/sys/getEncryptedString", "anon"); //获取加密串
		filterChainDefinitionMap.put("/sys/sms", "anon");//短信验证码
		filterChainDefinitionMap.put("/sys/phoneLogin", "anon");//手机登录
		filterChainDefinitionMap.put("/sys/user/checkOnlyUser", "anon");//校验用户是否存在
		filterChainDefinitionMap.put("/sys/user/register", "anon");//用户注册
		filterChainDefinitionMap.put("/sys/user/querySysUser", "anon");//根据手机号获取用户信息
		filterChainDefinitionMap.put("/sys/user/phoneVerification", "anon");//用户忘记密码验证手机号
		filterChainDefinitionMap.put("/sys/user/passwordChange", "anon");//用户更改密码
		filterChainDefinitionMap.put("/auth/2step-code", "anon");//登录验证码
		filterChainDefinitionMap.put("/sys/common/view/**", "anon");//图片预览不限制token
		filterChainDefinitionMap.put("/sys/common/download/**", "anon");//文件下载不限制token
		filterChainDefinitionMap.put("/sys/common/pdf/**", "anon");//pdf预览
		filterChainDefinitionMap.put("/sys/log/**", "anon");//ceshi
		filterChainDefinitionMap.put("/log/**", "anon");//ceshi
		filterChainDefinitionMap.put("/sys/user/list", "anon");//ceshi
		filterChainDefinitionMap.put("/generic/**", "anon");//pdf预览需要文件
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/doc.html", "anon");
		filterChainDefinitionMap.put("/**/*.js", "anon");
		filterChainDefinitionMap.put("/**/*.css", "anon");
		filterChainDefinitionMap.put("/**/*.html", "anon");
		filterChainDefinitionMap.put("/**/*.svg", "anon");
		filterChainDefinitionMap.put("/**/*.pdf", "anon");
		filterChainDefinitionMap.put("/**/*.jpg", "anon");
		filterChainDefinitionMap.put("/**/*.png", "anon");
		filterChainDefinitionMap.put("/**/*.ico", "anon");

		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/swagger-ui.html", "anon");
		filterChainDefinitionMap.put("/swagger**/**", "anon");
		filterChainDefinitionMap.put("/webjars/**", "anon");
		filterChainDefinitionMap.put("/v2/**", "anon");

		// 添加自己的过滤器并且取名为jwt
		Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
		filterMap.put("jwt", new JwtFilter());
		shiroFilterFactoryBean.setFilters(filterMap);
		// <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
		filterChainDefinitionMap.put("/**", "jwt");

		// 未授权界面返回JSON
		shiroFilterFactoryBean.setUnauthorizedUrl("/sys/common/403");
		shiroFilterFactoryBean.setLoginUrl("/sys/common/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	@Bean
	public DefaultWebSecurityManager securityManager(ShiroRealm myRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myRealm);
		/*
		 * 关闭shiro自带的session，详情见文档
		 * http://shiro.apache.org/session-management.html#SessionManagement-
		 * StatelessApplications%28Sessionless%29
		 */
		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		securityManager.setSubjectDAO(subjectDAO);
        //自定义缓存实现,使用redis
        securityManager.setCacheManager(redisCacheManager());
		return securityManager;
	}

	/**
	 * 下面的代码是添加注解支持
	 * @return
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	@Bean
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		return redisManager;
	}

	public RedisCacheManager redisCacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		//redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
		redisCacheManager.setPrincipalIdFieldName("id");
		//用户权限信息缓存时间
		redisCacheManager.setExpire(200000);
		return redisCacheManager;
	}

}
