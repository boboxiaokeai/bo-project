package cn.bo.project.admin.modules.shiro;

import cn.bo.project.admin.modules.shiro.model.DefContants;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author zhangbo
 * @Date 2020/1/4 12:15
 * @Description 鉴权登录拦截器
 * @PackageName cn.bo.project.admin.modules.shiro
 **/
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

	//执行顺序：preHandle->isAccessAllowed->isLoginAttempt->sendChallenge

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
		if (isLoginAttempt(request, response)) {
			try {
				return executeLogin(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	//判断request中请求头是否包含token
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		String token = req.getHeader(DefContants.X_ACCESS_TOKEN);
		return token != null;
	}

	//request中请求头中包含token,提交给realm进行登入 doGetAuthenticationInfo校验token有效性
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		log.info("————————JwtFilter————————客户端请求的url:"+httpServletRequest.getRequestURL().toString());

		String token = httpServletRequest.getHeader(DefContants.X_ACCESS_TOKEN);

		JwtToken jwtToken = new JwtToken(token);
		// 提交给realm进行登入，如果错误他会抛出异常并被捕获
		getSubject(request, response).login(jwtToken);
		// 如果没有抛出异常则代表登入成功，返回true
		return true;
	}

	/**
	 * 对跨域提供支持
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}

	//重写sendChallenge方法 response返回JSON格式数据
	@Override
	protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
		log.debug("Authentication required: sending 401 Authentication challenge response.");
		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		httpResponse.setCharacterEncoding("utf-8");
		httpResponse.setContentType("application/json; charset=utf-8");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status","203");
		jsonObject.put("message","token丢失");
		try (PrintWriter out = httpResponse.getWriter()) {
			out.print(jsonObject.toJSONString());
		} catch (IOException e) {
			log.error("sendChallenge error：", e);
		}
		return false;
	}
}
