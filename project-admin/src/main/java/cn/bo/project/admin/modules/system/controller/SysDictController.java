package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysDict;
import cn.bo.project.admin.modules.system.service.ISysDictService;
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
 * @Date 2020/3/17 9:27
 * @Description 字典控制器
 * @PackageName cn.bo.project.admin.modules.system.controller
 **/
@Slf4j
@Api(tags="字典API")
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {
    @Autowired
    private ISysDictService dictDataService;

    @ApiOperation("字典列表")
    @GetMapping("/list")
    public ResponseData list(SysDict sysDict) {
        List<SysDict> list = dictDataService.selectDictDataList(sysDict);
        return ResponseData.success(list);
    }


    @ApiOperation("字典详情")
    @GetMapping(value = "/{dictCode}")
    public ResponseData getInfo(@PathVariable Long dictCode) {
        return ResponseData.success(dictDataService.selectDictDataById(dictCode));
    }

    @ApiOperation("根据字典类型查询字典数据信息")
    @GetMapping(value = "/dictType/{dictType}")
    public ResponseData dictType(@PathVariable String dictType) {
        return ResponseData.success(dictDataService.selectDictDataByType(dictType));
    }

    @ApiOperation("字典新增")
    @PostMapping
    public ResponseData add(@Validated @RequestBody SysDict dict) {
        dict.setCreateBy("admin");
        return ResponseData.success(dictDataService.insertDictData(dict));
    }

    @ApiOperation("字典更新")
    @PutMapping
    public ResponseData edit(@Validated @RequestBody SysDict dict) {
        dict.setUpdateBy("admin");
        return ResponseData.success(dictDataService.updateDictData(dict));
    }

    @ApiOperation("字典删除")
    @DeleteMapping("/{dictCodes}")
    public ResponseData remove(@PathVariable Long[] dictCodes) {
        return ResponseData.success(dictDataService.deleteDictDataByIds(dictCodes));
    }
}
