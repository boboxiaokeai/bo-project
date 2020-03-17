package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysDictType;
import cn.bo.project.admin.modules.system.service.ISysDictTypeService;
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
 * @Date 2020/3/17 10:06
 * @Description 字典类型控制器
 * @PackageName cn.bo.project.admin.modules.system.controller
 **/
@Slf4j
@Api(tags="字典类型API")
@RestController
@RequestMapping("/sys/dict/type")
public class SysDictTypeController {
    @Autowired
    private ISysDictTypeService dictTypeService;

    @ApiOperation("字典类型列表")
    @GetMapping("/list")
    public ResultBean list(SysDictType dictType)
    {
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return ResultBean.ok(list);
    }


    @ApiOperation("字典类型详情")
    @GetMapping(value = "/{dictId}")
    public ResultBean getInfo(@PathVariable Long dictId)
    {
        return ResultBean.ok(dictTypeService.selectDictTypeById(dictId));
    }


    @ApiOperation("字典类型新增")
    @PostMapping
    public ResultBean add(@Validated @RequestBody SysDictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return ResultBean.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy("admin");
        return ResultBean.ok(dictTypeService.insertDictType(dict));
    }


    @ApiOperation("字典类型更新")
    @PutMapping
    public ResultBean edit(@Validated @RequestBody SysDictType dict)
    {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict)))
        {
            return ResultBean.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy("admin");
        return ResultBean.ok(dictTypeService.updateDictType(dict));
    }


    @ApiOperation("字典类型删除")
    @DeleteMapping("/{dictIds}")
    public ResultBean remove(@PathVariable Long[] dictIds)
    {
        return ResultBean.ok(dictTypeService.deleteDictTypeByIds(dictIds));
    }


    @ApiOperation("获取字典选择框列表")
    @GetMapping("/select")
    public ResultBean select()
    {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return ResultBean.ok(dictTypes);
    }
}
