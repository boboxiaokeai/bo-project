package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysConfig;
import cn.bo.project.admin.modules.system.service.ISysConfigService;
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
 * @Date 2020/3/17 10:27
 * @Description 系统配置
 * @PackageName cn.bo.project.admin.modules.system.controller
 **/
@Slf4j
@Api(tags="系统配置API")
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {
    @Autowired
    private ISysConfigService configService;


    @ApiOperation("参数配置列表")
    @GetMapping("/list")
    public ResponseData list(SysConfig config) {
        List<SysConfig> list = configService.selectConfigList(config);
        return ResponseData.success(list);
    }


    @ApiOperation("参数配置详情")
    @GetMapping(value = "/{configId}")
    public ResponseData getInfo(@PathVariable Long configId) {
        return ResponseData.success(configService.selectConfigById(configId));
    }


    @ApiOperation("根据参数键名查询参数值")
    @GetMapping(value = "/configKey/{configKey}")
    public ResponseData getConfigKey(@PathVariable String configKey) {
        return ResponseData.success(configService.selectConfigByKey(configKey));
    }


    @ApiOperation("新增参数配置")
    @PostMapping
    public ResponseData add(@Validated @RequestBody SysConfig config) {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return ResponseData.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setCreateBy("admin");
        return ResponseData.success(configService.insertConfig(config));
    }

    @ApiOperation("修改参数配置")
    @PutMapping
    public ResponseData edit(@Validated @RequestBody SysConfig config) {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return ResponseData.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setUpdateBy("admin");
        return ResponseData.success(configService.updateConfig(config));
    }


    @ApiOperation("删除参数配置")
    @DeleteMapping("/{configIds}")
    public ResponseData remove(@PathVariable Long[] configIds) {
        return ResponseData.success(configService.deleteConfigByIds(configIds));
    }
}
