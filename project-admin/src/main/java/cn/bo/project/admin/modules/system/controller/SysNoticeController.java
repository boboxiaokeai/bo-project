package cn.bo.project.admin.modules.system.controller;

import cn.bo.project.admin.modules.system.entity.SysNotice;
import cn.bo.project.admin.modules.system.service.ISysNoticeService;
import cn.bo.project.base.api.ResultBean;
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
    public ResultBean list(SysNotice notice)
    {
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return ResultBean.ok(list);
    }


    @ApiOperation("根据通知公告编号获取详细信息")
    @GetMapping(value = "/{noticeId}")
    public ResultBean getInfo(@PathVariable Long noticeId)
    {
        return ResultBean.ok(noticeService.selectNoticeById(noticeId));
    }


    @ApiOperation("新增通知公告")
    @PostMapping
    public ResultBean add(@Validated @RequestBody SysNotice notice)
    {
        notice.setCreateBy("admin");
        return ResultBean.ok(noticeService.insertNotice(notice));
    }


    @ApiOperation("修改通知公告")
    @PutMapping
    public ResultBean edit(@Validated @RequestBody SysNotice notice)
    {
        notice.setUpdateBy("admin");
        return ResultBean.ok(noticeService.updateNotice(notice));
    }


    @ApiOperation("删除通知公告")
    @DeleteMapping("/{noticeId}")
    public ResultBean remove(@PathVariable Long noticeId)
    {
        return ResultBean.ok(noticeService.deleteNoticeById(noticeId));
    }
}
