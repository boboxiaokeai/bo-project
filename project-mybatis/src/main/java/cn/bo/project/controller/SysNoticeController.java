package cn.bo.project.controller;

import cn.bo.project.Response.ResponseData;
import cn.bo.project.entity.SysNotice;
import cn.bo.project.service.ISysNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhangbo
 * @Date 2020/3/17 10:32
 * @Description 公告消息控制器
 * @PackageName cn.bo.project.admin.modules.system.controller
 **/
@Slf4j
@Api(tags="系统配置API")
@RestController
@RequestMapping("/sys/notice")
public class SysNoticeController {
    @Autowired
    private ISysNoticeService noticeService;


    @ApiOperation("通知公告列表")
    @GetMapping("/list")
    public ResponseData list(SysNotice notice)
    {
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return ResponseData.success(list);
    }


    @ApiOperation("根据通知公告编号获取详细信息")
    @GetMapping(value = "/{noticeId}")
    public ResponseData getInfo(@PathVariable Long noticeId)
    {
        return ResponseData.success(noticeService.selectNoticeById(noticeId));
    }


    @ApiOperation("新增通知公告")
    @PostMapping
    public ResponseData add(@Validated @RequestBody SysNotice notice)
    {
        notice.setCreateBy("admin");
        return ResponseData.success(noticeService.insertNotice(notice));
    }


    @ApiOperation("修改通知公告")
    @PutMapping
    public ResponseData edit(@Validated @RequestBody SysNotice notice)
    {
        notice.setUpdateBy("admin");
        return ResponseData.success(noticeService.updateNotice(notice));
    }


    @ApiOperation("删除通知公告")
    @DeleteMapping("/{noticeId}")
    public ResponseData remove(@PathVariable Long noticeId)
    {
        return ResponseData.success(noticeService.deleteNoticeById(noticeId));
    }
}
