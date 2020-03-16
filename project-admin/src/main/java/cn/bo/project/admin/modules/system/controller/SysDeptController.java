package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysDept;
import cn.bo.project.admin.modules.system.service.ISysDeptService;
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
 * @Date 2020/3/16 22:07
 * @Description 部门控制器
 * @PackageName cn.bo.project.admin.modules.system.controller
 **/
@Slf4j
@Api(tags="部门API")
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private ISysDeptService deptService;

    @ApiOperation("部门列表")
    @GetMapping("/list")
    public ResultBean list(SysDept dept)
    {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return ResultBean.ok(deptService.buildDeptTree(depts));
    }


    @ApiOperation("部门详情")
    @GetMapping(value = "/{deptId}")
    public ResultBean getInfo(@PathVariable Long deptId)
    {
        return ResultBean.ok(deptService.selectDeptById(deptId));
    }


    @ApiOperation("部门下拉树列表")
    @GetMapping("/treeSelect")
    public ResultBean treeSelect(SysDept dept)
    {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return ResultBean.ok(deptService.buildDeptTreeSelect(depts));
    }


    @ApiOperation("角色部门列表树")
    @GetMapping(value = "/roleDeptTreeSelect/{roleId}")
    public ResultBean roleDeptTreeSelect(@PathVariable("roleId") Long roleId)
    {
        return ResultBean.ok(deptService.selectDeptListByRoleId(roleId));
    }


    @ApiOperation("新增部门")
    @PostMapping
    public ResultBean add(@Validated @RequestBody SysDept dept)
    {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return ResultBean.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy("admin");
        return ResultBean.ok(deptService.insertDept(dept));
    }


    @ApiOperation("部门编辑")
    @PutMapping
    public ResultBean edit(@Validated @RequestBody SysDept dept)
    {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return ResultBean.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        else if (dept.getParentId().equals(dept.getDeptId()))
        {
            return ResultBean.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        dept.setUpdateBy("admin");
        return ResultBean.ok(deptService.updateDept(dept));
    }


    @ApiOperation("部门删除")
    @DeleteMapping("/{deptId}")
    public ResultBean remove(@PathVariable Long deptId)
    {
        if (deptService.hasChildByDeptId(deptId))
        {
            return ResultBean.error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            return ResultBean.error("部门存在用户,不允许删除");
        }
        return ResultBean.ok(deptService.deleteDeptById(deptId));
    }
}
