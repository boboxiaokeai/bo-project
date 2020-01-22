package cn.bo.project.admin.modules.system.controller;


import cn.bo.project.admin.modules.system.entity.SysUser;
import cn.bo.project.admin.modules.system.service.ISysUserService;
import cn.bo.project.base.api.ResultBean;
import cn.bo.project.base.core.api.ISysBaseAPI;
import cn.bo.project.base.core.query.QueryGenerator;
import cn.bo.project.base.utils.RedisUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author zhangbo
 * @Date 2020/1/6 10:25
 * @Description 用户控制器
 * @PackageName cn.bo.project.admin.modules.system.controller
 **/
@Slf4j
@Api("系统用户API")
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

	@Autowired
	private ISysBaseAPI sysBaseAPI;
	
	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private RedisUtil redisUtil;

	@ApiOperation("系统用户列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResultBean<IPage<SysUser>> queryPageList(SysUser user, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req) {
        ResultBean<IPage<SysUser>> ResultBean = new ResultBean<IPage<SysUser>>();
		QueryWrapper<SysUser> queryWrapper = QueryGenerator.initQueryWrapper(user, req.getParameterMap());
		Page<SysUser> page = new Page<SysUser>(pageNo, pageSize);
		IPage<SysUser> pageList = sysUserService.page(page, queryWrapper);
		ResultBean.setSuccess(true);
		ResultBean.setResult(pageList);
		return ResultBean;
	}
}
