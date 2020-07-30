package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysMenu;
import cn.bo.project.admin.modules.system.service.ISysMenuService;
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
 * @Date 2020/3/16 21:57
 * @Description 菜单控制器
 * @PackageName cn.bo.project.admin.modules.system.controller
 **/
@Slf4j
@Api(tags="菜单API")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {
    @Autowired
    private ISysMenuService menuService;


    @ApiOperation("菜单列表")
    @GetMapping("/list")
    public ResponseData list(SysMenu menu) {
        List<SysMenu> menus = menuService.selectMenuList(menu);
        return ResponseData.success(menuService.buildMenuTree(menus));
    }

    @ApiOperation("菜单详情")
    @GetMapping(value = "/{menuId}")
    public ResponseData getInfo(@PathVariable Long menuId) {
        return ResponseData.success(menuService.selectMenuById(menuId));
    }

    @ApiOperation("菜单下拉树列表")
    @GetMapping("/treeselect")
    public ResponseData treeselect(SysMenu dept) {
        List<SysMenu> menus = menuService.selectMenuList(dept);
        return ResponseData.success(menuService.buildMenuTreeSelect(menus));
    }

    @ApiOperation("角色菜单列表树")
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public ResponseData roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        return ResponseData.success(menuService.selectMenuListByRoleId(roleId));
    }

    @ApiOperation("菜单新增")
    @PostMapping
    public ResponseData add(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return ResponseData.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        menu.setCreateBy("admin");
        return ResponseData.success(menuService.insertMenu(menu));
    }


    @ApiOperation("菜单编辑")
    @PutMapping
    public ResponseData edit(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return ResponseData.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        menu.setUpdateBy("admin");
        return ResponseData.success(menuService.updateMenu(menu));
    }


    @ApiOperation("菜单删除")
    @DeleteMapping("/{menuId}")
    public ResponseData remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return ResponseData.error("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return ResponseData.error("菜单已分配,不允许删除");
        }
        return ResponseData.success(menuService.deleteMenuById(menuId));
    }
}
