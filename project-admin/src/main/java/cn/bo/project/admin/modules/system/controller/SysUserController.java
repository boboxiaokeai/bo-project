package cn.bo.project.admin.modules.system.controller;


import cn.bo.project.admin.modules.shiro.model.DefContants;
import cn.bo.project.admin.modules.system.entity.SysUser;
import cn.bo.project.admin.modules.system.service.ISysLogService;
import cn.bo.project.admin.modules.system.service.ISysPostService;
import cn.bo.project.admin.modules.system.service.ISysRoleService;
import cn.bo.project.admin.modules.system.service.ISysUserService;
import cn.bo.project.base.api.ResultBean;
import cn.bo.project.base.constant.UserConstants;
import cn.bo.project.base.core.api.ISysBaseAPI;
import cn.bo.project.base.core.model.LoginUser;
import cn.bo.project.base.core.query.QueryGenerator;
import cn.bo.project.base.utils.JwtUtil;
import cn.bo.project.base.utils.RedisUtil;

import cn.bo.project.base.utils.StringUtils;
import cn.bo.project.base.utils.oConvertUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author zhangbo
 * @Date 2020/1/6 10:25
 * @Description 用户控制器
 * @PackageName cn.bo.project.admin.modules.system.controller
 **/
@Slf4j
@Api(tags="用户API")
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

	@Autowired
	private ISysBaseAPI sysBaseAPI;
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private ISysRoleService roleService;
	@Autowired
	private ISysPostService postService;
	@Autowired
	private ISysLogService logService;

	@ApiOperation("系统用户列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResultBean<Object> queryPageList(SysUser user, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											@RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req) {
		/*QueryWrapper<SysUser> queryWrapper = QueryGenerator.initQueryWrapper(user, req.getParameterMap());
		Page<SysUser> page = new Page<SysUser>(pageNo, pageSize);
		IPage<SysUser> pageList = sysUserService.page(page, queryWrapper);*/
		List<SysUser> list = sysUserService.selectUserList(user);
		return ResultBean.ok(list);
	}

	@ApiOperation("用户详情")
	@GetMapping(value = { "/", "/{userId}" })
	public ResultBean getInfo(@PathVariable(value = "userId", required = false) Long userId)
	{
		Map<String, Object> map = new HashMap<>();
		map.put("roles", roleService.selectRoleAll());
		map.put("posts", postService.selectPostAll());
		if (StringUtils.isNotNull(userId))
		{
			map.put("data", sysUserService.selectUserById(userId));
			map.put("postIds", postService.selectPostListByUserId(userId));
			map.put("roleIds", roleService.selectRoleListByUserId(userId));
		}
		return ResultBean.ok(map);
	}


	@ApiOperation("用户编辑")
	@PutMapping
	public ResultBean edit(@Validated @RequestBody SysUser user)
	{
		sysUserService.checkUserAllowed(user);
		if (UserConstants.NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(user)))
		{
			return ResultBean.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
		}
		else if (UserConstants.NOT_UNIQUE.equals(sysUserService.checkEmailUnique(user)))
		{
			return ResultBean.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
		}
		user.setUpdateBy("admin");
		return ResultBean.ok(sysUserService.updateUser(user));
	}


	@ApiOperation("用户删除")
	@DeleteMapping("/{userIds}")
	public ResultBean remove(@PathVariable Long[] userIds)
	{
		return ResultBean.ok(sysUserService.deleteUserByIds(userIds));
	}



	@ApiOperation("用户状态修改")
	@PutMapping("/changeStatus")
	public ResultBean changeStatus(@RequestBody SysUser user)
	{
		sysUserService.checkUserAllowed(user);
		user.setUpdateBy("admin");
		return ResultBean.ok(sysUserService.updateUserStatus(user));
	}

	@ApiOperation("个人信息")
	@GetMapping("/profile")
	public ResultBean profile(HttpServletRequest request, HttpServletResponse response){
		String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
		if(oConvertUtils.isEmpty(token)) {
			return ResultBean.error("token不能为空！");
		}
		String username = JwtUtil.getUsername(token);
		LoginUser sysUser = sysBaseAPI.getUserByName(username);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user", sysUser);
		map.put("logs", logService.selectUserLog(sysUser.getUserName()));
		map.put("roleGroup", sysUserService.selectUserRoleGroup(sysUser.getUserName()));
		map.put("postGroup", sysUserService.selectUserPostGroup(sysUser.getUserName()));
		return ResultBean.ok(map);
	}

}
