package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysDept;
import cn.bo.project.admin.modules.system.service.ISysDeptService;
import cn.bo.project.base.constant.UserConstants;
import cn.bo.project.base.response.ResponseData;
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
    public ResponseData list(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return ResponseData.success(deptService.buildDeptTree(depts));
    }


    @ApiOperation("部门详情")
    @GetMapping(value = "/{deptId}")
    public ResponseData getInfo(@PathVariable Long deptId) {
        return ResponseData.success(deptService.selectDeptById(deptId));
    }


    @ApiOperation("部门下拉树列表")
    @GetMapping("/treeSelect")
    public ResponseData treeSelect(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return ResponseData.success(deptService.buildDeptTreeSelect(depts));
    }


    @ApiOperation("角色部门列表树")
    @GetMapping(value = "/roleDeptTreeSelect/{roleId}")
    public ResponseData roleDeptTreeSelect(@PathVariable("roleId") Long roleId) {
        return ResponseData.success(deptService.selectDeptListByRoleId(roleId));
    }


    @ApiOperation("新增部门")
    @PostMapping
    public ResponseData add(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return ResponseData.error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy("admin");
        return ResponseData.success(deptService.insertDept(dept));
    }


    @ApiOperation("部门编辑")
    @PutMapping
    public ResponseData edit(@Validated @RequestBody SysDept dept) {
        if (UserConstants.NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
            return ResponseData.error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        } else if (dept.getParentId().equals(dept.getDeptId())) {
            return ResponseData.error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        dept.setUpdateBy("admin");
        return ResponseData.success(deptService.updateDept(dept));
    }


    @ApiOperation("部门删除")
    @DeleteMapping("/{deptId}")
    public ResponseData remove(@PathVariable Long deptId) {
        if (deptService.hasChildByDeptId(deptId)) {
            return ResponseData.error("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            return ResponseData.error("部门存在用户,不允许删除");
        }
        return ResponseData.success(deptService.deleteDeptById(deptId));
    }
}
