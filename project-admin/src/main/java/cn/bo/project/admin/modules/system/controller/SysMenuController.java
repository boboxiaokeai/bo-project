package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysMenu;
import cn.bo.project.admin.modules.system.service.ISysMenuService;
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
    public ResultBean list(SysMenu menu)
    {
        List<SysMenu> menus = menuService.selectMenuList(menu);
        return ResultBean.ok(menuService.buildMenuTree(menus));
    }

    @ApiOperation("菜单详情")
    @GetMapping(value = "/{menuId}")
    public ResultBean getInfo(@PathVariable Long menuId)
    {
        return ResultBean.ok(menuService.selectMenuById(menuId));
    }

    @ApiOperation("菜单下拉树列表")
    @GetMapping("/treeselect")
    public ResultBean treeselect(SysMenu dept)
    {
        List<SysMenu> menus = menuService.selectMenuList(dept);
        return ResultBean.ok(menuService.buildMenuTreeSelect(menus));
    }

    @ApiOperation("角色菜单列表树")
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public ResultBean roleMenuTreeselect(@PathVariable("roleId") Long roleId)
    {
        return ResultBean.ok(menuService.selectMenuListByRoleId(roleId));
    }

    @ApiOperation("菜单新增")
    @PostMapping
    public ResultBean add(@Validated @RequestBody SysMenu menu)
    {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu)))
        {
            return ResultBean.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        menu.setCreateBy("admin");
        return ResultBean.ok(menuService.insertMenu(menu));
    }


    @ApiOperation("菜单编辑")
    @PutMapping
    public ResultBean edit(@Validated @RequestBody SysMenu menu)
    {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu)))
        {
            return ResultBean.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        menu.setUpdateBy("admin");
        return ResultBean.ok(menuService.updateMenu(menu));
    }


    @ApiOperation("菜单删除")
    @DeleteMapping("/{menuId}")
    public ResultBean remove(@PathVariable("menuId") Long menuId)
    {
        if (menuService.hasChildByMenuId(menuId))
        {
            return ResultBean.error("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId))
        {
            return ResultBean.error("菜单已分配,不允许删除");
        }
        return ResultBean.ok(menuService.deleteMenuById(menuId));
    }
}
