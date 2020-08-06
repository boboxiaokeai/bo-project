package cn.bo.project.system.controller;


import cn.bo.project.common.annotation.Log;
import cn.bo.project.common.enums.OperateTypeEnum;
import cn.bo.project.common.page.PageInfo;
import cn.bo.project.common.response.ResponseData;
import cn.bo.project.system.entity.SysLog;
import cn.bo.project.system.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @Author zhangbo
 * @Date 2020/1/4 13:07
 * @Description 系统日志控制器
 * @PackageName cn.bo.project.system.controller
 **/
@Slf4j
@Api(tags="系统日志API")
@RestController
@RequestMapping("/sys/log")
public class SysLogController {
	
	@Autowired
	private SysLogService sysLogService;

	/**
	 * 系统日志列表
	 * @param sysLog
	 * @return
	 */
	@Log(value = "系统日志列表",operateType = OperateTypeEnum.SELECT)
	@GetMapping("/list")
	@ApiOperation("系统日志列表")
	public PageInfo pageList(SysLog sysLog) {
		return this.sysLogService.pageList(sysLog);
	}

	/**
	 * 系统日志详情
	 * @param id
	 * @return
	 */
	@Log(value = "系统日志详情",operateType = OperateTypeEnum.SELECT)
	@GetMapping("/detail/{id}")
	@ApiOperation("系统日志详情")
	public ResponseData detail(@PathVariable("id") String id) {
		return ResponseData.success(sysLogService.getById(id));
	}

	/**
	 * 系统日志删除
	 * @param id
	 * @return
	 */
	@Log(value = "删除系统日志",operateType = OperateTypeEnum.DELETE)
	@DeleteMapping("/del/{id}")
	@ApiOperation("删除系统日志")
	public ResponseData del(@PathVariable("id") String id) {
		sysLogService.removeById(id);
		return ResponseData.success();
	}

	/**
	 * 系统日志批量删除
	 * @param ids
	 * @return
	 */
	@Log(value = "批量删除系统日志",operateType = OperateTypeEnum.DELETE)
	@DeleteMapping("/batchDel/{ids}")
	@ApiOperation("批量删除系统日志")
	public ResponseData batchDel(@PathVariable("ids")String[] ids) {
		sysLogService.removeByIds(Arrays.asList(ids));
		return ResponseData.success();
	}
}
