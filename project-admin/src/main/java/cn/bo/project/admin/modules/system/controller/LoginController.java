package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.shiro.model.DefContants;
import cn.bo.project.admin.modules.system.entity.SysMenu;
import cn.bo.project.admin.modules.system.entity.SysUser;
import cn.bo.project.admin.modules.system.model.RouterModel;
import cn.bo.project.admin.modules.system.model.SysLoginModel;
import cn.bo.project.admin.modules.system.service.*;
import cn.bo.project.base.api.ResultBean;
import cn.bo.project.base.constant.CacheConstant;
import cn.bo.project.base.constant.CommonConstant;
import cn.bo.project.base.core.api.ISysBaseAPI;
import cn.bo.project.base.core.model.LoginUser;
import cn.bo.project.base.utils.*;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;


@Log4j2
@RestController
@Api(tags="登录API")
@RequestMapping("/sys")
public class LoginController {
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysBaseAPI sysBaseAPI;
	@Autowired
    private RedisUtil redisUtil;
	@Autowired
	private ISysMenuService menuService;
	@Autowired
	private SysPermissionService permissionService;

	@ApiOperation("登录接口")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResultBean<JSONObject> login(@RequestBody SysLoginModel sysLoginModel){
		ResultBean<JSONObject> ResultBean = new ResultBean<JSONObject>();
		String username = sysLoginModel.getUsername();
		String password = sysLoginModel.getPassword();

		//1. 验证码校验
		String code = String.valueOf(redisUtil.get(CommonConstant.CAPTCHA_CODE_KEY+sysLoginModel.getCodekey()));
		redisUtil.del(CommonConstant.CAPTCHA_CODE_KEY+sysLoginModel.getCodekey());
		if (code==null){
			ResultBean.error500("验证码过期");
			return ResultBean;
		}
		if (!code.equals(sysLoginModel.getCode())){
			ResultBean.error500("验证码错误");
			return ResultBean;
		}
		//2. 校验用户是否有效
		SysUser sysUser = sysUserService.selectUserByUserName(username);
		ResultBean = sysUserService.checkUserIsEffective(sysUser);
		if(!ResultBean.isSuccess()) {
			return ResultBean;
		}
		//3. 用户名或密码校验
		String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
		String syspassword = sysUser.getPassword();
		if (!syspassword.equals(userpassword)) {
			ResultBean.error500("用户名或密码错误");
			return ResultBean;
		}
		//用户登录信息
		userInfo(sysUser, ResultBean);
		sysBaseAPI.addLog("用户: " + username + ",登录成功！", CommonConstant.LOG_TYPE_1, null);

		return ResultBean;
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation("退出登录接口")
	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	public ResultBean<Object> logout(HttpServletRequest request, HttpServletResponse response) {
		//用户退出逻辑
	    String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
		if(oConvertUtils.isEmpty(token)) {
			return ResultBean.error("退出登录失败！");
		}
		String username = JwtUtil.getUsername(token);
		LoginUser sysUser = sysBaseAPI.getUserByName(username);
	    if(sysUser!=null) {
	    	sysBaseAPI.addLog("用户名: "+sysUser.getRealName()+",退出成功！", CommonConstant.LOG_TYPE_1, null);
	    	log.info(" 用户名:  "+sysUser.getRealName()+",退出成功！ ");
	    	//清空用户登录Token缓存
	    	redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
	    	//清空用户登录Shiro权限缓存
			redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + sysUser.getId());
			//清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
			redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUserName()));
			//调用shiro的logout
			SecurityUtils.getSubject().logout();
	    	return ResultBean.ok("退出登录成功！");
	    }else {
	    	return ResultBean.error("Token无效!");
	    }
	}
	

	/**
	 * 用户信息
	 * @param sysUser
	 * @param ResultBean
	 * @return
	 */
	private ResultBean<JSONObject> userInfo(SysUser sysUser, ResultBean<JSONObject> ResultBean) {
		String syspassword = sysUser.getPassword();
		String username = sysUser.getUserName();
		// 生成token
		String token = JwtUtil.sign(username, syspassword);
        // 设置token缓存有效时间
		redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
		redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);

		JSONObject obj = new JSONObject();
		obj.put("token", token);
		obj.put("userInfo", sysUser);
		ResultBean.setData(obj);
		ResultBean.success("登录成功");
		return ResultBean;
	}

	/**
	 * 获取验证码
	 * @return
	 */
	@ApiOperation("获取验证码接口")
	@RequestMapping(value = "/captchaImage", method = RequestMethod.GET)
	private ResultBean<Map<String,String>> getCodeImage() throws IOException {
		ResultBean<Map<String,String>> resultBean = new ResultBean<>();
		Map<String,String> map = new HashMap<String,String>();
		// 生成随机字串
		String verifyCode = RandomUtil.randomString(4);
		// 唯一标识
		String codekey = IdUtil.simpleUUID();
		redisUtil.set(CommonConstant.CAPTCHA_CODE_KEY+codekey,verifyCode,120);
		try {
			// 生成图片
			int w = 111, h = 36;
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
			map.put("codekey", codekey);
			map.put("img", Base64.encode(stream.toByteArray()));
			map.put("code", verifyCode);
			resultBean.setData(map);
			resultBean.success("获取成功");
		} catch (IOException e) {
			e.printStackTrace();
			return resultBean.error404("获取验证码失败");
		}
		return  resultBean;
	}

	@RequestMapping(value = "/getInfo", method = RequestMethod.GET)
	public ResultBean getInfo(HttpServletRequest request) {
		ResultBean<Map<String,Set<String>>> resultBean = new ResultBean<>();
		Map<String, Set<String>> map = new HashMap<>();
		String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
		if(oConvertUtils.isEmpty(token)) {
			resultBean.error500("验证码过期");
			return resultBean;
		}
		String username = JwtUtil.getUsername(token);
		SysUser sysUser = sysUserService.selectUserByUserName(username);
		// 角色集合
		Set<String> roles = permissionService.getRolePermission(sysUser);
		// 权限集合
		Set<String> permissions = permissionService.getMenuPermission(sysUser);
		map.put("roles", roles);
		map.put("permissions", permissions);
		resultBean.setData(map);
		resultBean.setCode(200);
		resultBean.setSuccess(true);
		return resultBean;
	}


	/**
	 * 获取路由信息
	 * @return 路由信息
	 */
	@RequestMapping(value = "/getRouters", method = RequestMethod.GET)
	public ResultBean getRouters(HttpServletRequest request, HttpServletResponse response)
	{
		String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
		if(oConvertUtils.isEmpty(token)) {
			return ResultBean.error("token不能为空！");
		}
		String username = JwtUtil.getUsername(token);
		LoginUser sysUser = sysBaseAPI.getUserByName(username);
		// 用户信息
		List<SysMenu> menus = menuService.selectMenuTreeByUserId(sysUser.getId());
		List<RouterModel> RouterModel = menuService.buildMenus(menus);
		JSONObject obj = new JSONObject();
		obj.put("menu", RouterModel);
		return ResultBean.ok(obj);
	}

	public static void main(String[] args) {
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("id","1");
		jsonObject1.put("name","玫瑰花");
		jsonObject1.put("price","10");
		jsonObject1.put("img","http://keytest.oss-cn-zhangjiakou.aliyuncs.com/upload/store/1/2020/02/3b2a8f1d-b9 a6-430f-9589-27bab5a9e3ff.png");
		jsonObject1.put("gifimg","https://zmmapp.oss-cn-beijing.aliyuncs.com/upload/store/1/2020/06/424d4c4caf414f2eb03901d4eb354fbb_600-452.gif");
		jsonObject1.put("contribute","100");


		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("id","2");
		jsonObject2.put("name","戒指");
		jsonObject2.put("price","100");
		jsonObject2.put("img","http://keytest.oss-cn-zhangjiakou.aliyuncs.com/upload/store/1/2020/02/3b2a8f1d-b9 a6-430f-9589-27bab5a9e3ff.png");
		jsonObject2.put("gifimg","https://zmmapp.oss-cn-beijing.aliyuncs.com/upload/store/1/2020/06/6849d189604949d3a2cfa28fbd1245a8_500-498.gif");
		jsonObject1.put("contribute","1000");

		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("id","3");
		jsonObject3.put("name","巧克力");
		jsonObject3.put("price","200");

		jsonObject3.put("img","https://zmmapp.oss-cn-beijing.aliyuncs.com/upload/store/1/2020/06/e8979d33d48648d685b9916b9fddc471_500-316.jpg");
		jsonObject3.put("gifimg","https://zmmapp.oss-cn-beijing.aliyuncs.com/upload/store/1/2020/06/2ef9b1a11c5b42e897356f6cdea21062_400-219.gif");
		jsonObject3.put("contribute","2000");

		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);
		jsonArray.add(jsonObject3);

		System.out.println(jsonArray.toJSONString());

	}

}