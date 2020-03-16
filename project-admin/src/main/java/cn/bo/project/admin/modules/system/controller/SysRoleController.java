package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysRole;
import cn.bo.project.admin.modules.system.service.ISysRoleService;
import cn.bo.project.base.api.ResultBean;
import cn.bo.project.base.constant.UserConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhangbo
 * @Date 2020/3/16 21:36
 * @Description 角色控制器
 * @PackageName cn.bo.project.admin.modules.system.controller
 **/
@Slf4j
@Api(tags="角色API")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @ApiOperation("用户角色列表")
    @GetMapping("/list")
    public ResultBean queryPageList(SysRole role)
    {
        List<SysRole> list = sysRoleService.selectRoleList(role);
        return ResultBean.ok(list);
    }


    @ApiOperation("角色详情")
    @GetMapping(value = "/{roleId}")
    public ResultBean getInfo(@PathVariable Long roleId)
    {
        return ResultBean.ok(sysRoleService.selectRoleById(roleId));
    }


    @ApiOperation("角色新增")
    @PostMapping
    public ResultBean add(@Validated @RequestBody SysRole role)
    {
        if (UserConstants.NOT_UNIQUE.equals(sysRoleService.checkRoleNameUnique(role)))
        {
            return ResultBean.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(sysRoleService.checkRoleKeyUnique(role)))
        {
            return ResultBean.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy("admin");
        return ResultBean.ok(sysRoleService.insertRole(role));
    }


    @ApiOperation("角色编辑")
    @PutMapping
    public ResultBean edit(@Validated @RequestBody SysRole role)
    {
        sysRoleService.checkRoleAllowed(role);
        if (UserConstants.NOT_UNIQUE.equals(sysRoleService.checkRoleNameUnique(role)))
        {
            return ResultBean.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(sysRoleService.checkRoleKeyUnique(role)))
        {
            return ResultBean.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy("admin");
        return ResultBean.ok(sysRoleService.updateRole(role));
    }


    @ApiOperation("修改保存数据权限")
    @PutMapping("/dataScope")
    public ResultBean dataScope(@RequestBody SysRole role)
    {
        sysRoleService.checkRoleAllowed(role);
        return ResultBean.ok(sysRoleService.authDataScope(role));
    }

    @ApiOperation("状态修改")
    @PutMapping("/changeStatus")
    public ResultBean changeStatus(@RequestBody SysRole role)
    {
        sysRoleService.checkRoleAllowed(role);
        role.setUpdateBy("admin");
        return ResultBean.ok(sysRoleService.updateRoleStatus(role));
    }


    @ApiOperation("删除角色")
    @DeleteMapping("/{roleIds}")
    public ResultBean remove(@PathVariable Long[] roleIds)
    {
        return ResultBean.ok(sysRoleService.deleteRoleByIds(roleIds));
    }


    @ApiOperation("获取角色选择框列表")
    @GetMapping("/select")
    public ResultBean select()
    {
        return ResultBean.ok(sysRoleService.selectRoleAll());
    }

}
