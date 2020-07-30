package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysDictType;
import cn.bo.project.admin.modules.system.service.ISysDictTypeService;
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
    public ResponseData list(SysDictType dictType) {
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return ResponseData.success(list);
    }


    @ApiOperation("字典类型详情")
    @GetMapping(value = "/{dictId}")
    public ResponseData getInfo(@PathVariable Long dictId) {
        return ResponseData.success(dictTypeService.selectDictTypeById(dictId));
    }


    @ApiOperation("字典类型新增")
    @PostMapping
    public ResponseData add(@Validated @RequestBody SysDictType dict) {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return ResponseData.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy("admin");
        return ResponseData.success(dictTypeService.insertDictType(dict));
    }


    @ApiOperation("字典类型更新")
    @PutMapping
    public ResponseData edit(@Validated @RequestBody SysDictType dict) {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return ResponseData.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy("admin");
        return ResponseData.success(dictTypeService.updateDictType(dict));
    }


    @ApiOperation("字典类型删除")
    @DeleteMapping("/{dictIds}")
    public ResponseData remove(@PathVariable Long[] dictIds) {
        return ResponseData.success(dictTypeService.deleteDictTypeByIds(dictIds));
    }


    @ApiOperation("获取字典选择框列表")
    @GetMapping("/select")
    public ResponseData select() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return ResponseData.success(dictTypes);
    }
}
