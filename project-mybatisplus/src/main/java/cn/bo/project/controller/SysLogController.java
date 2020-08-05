package cn.bo.project.controller;

import cn.bo.project.Response.ResponseData;
import cn.bo.project.entity.SysLog;
import cn.bo.project.mapper.SysLogMapper;
import cn.bo.project.page.PageInfo;
import cn.bo.project.service.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author zhangbo
 * @Date 2020/1/4 13:07
 * @Description 系统日志控制器
 * @PackageName cn.bo.project.admin.modules.system.controller
 **/
@Slf4j
@Api(tags="日志API")
@RestController
@RequestMapping("/sys/log")
public class SysLogController {
	
	@Autowired
	private SysLogMapper sysLogMapper;
	@Autowired
	private SysLogService sysLogService;

	@ApiOperation("新增")
	@PostMapping("/add")
	public ResponseData add(@RequestBody SysLog syslog) {
		sysLogMapper.insert(syslog);
		Object id = syslog.getId();
		return ResponseData.success(id);
	}


	@ApiOperation("通过id查询")
	@GetMapping("/{id}")
	public ResponseData get(@PathVariable String id) {
		SysLog sysLog = sysLogMapper.selectById(id);
		return ResponseData.success(sysLog);
	}

	@ApiOperation("列表")
	@GetMapping
	public ResponseData list() {
		QueryWrapper sysLogQueryWrapper = new QueryWrapper<>().eq("method","1");
		List<SysLog> sysLogList = sysLogMapper.selectList(sysLogQueryWrapper);
		return ResponseData.success(sysLogList);
	}

	@ApiOperation("通过id删除")
	@DeleteMapping("/{id}")
	public ResponseData del(@PathVariable String id) {
		return ResponseData.success(sysLogMapper.deleteById(id));
	}

	@ApiOperation("列表-分页Mybatis-plus")
	@GetMapping("/page")
	public IPage<SysLog> page(@RequestParam("current") Long current,@RequestParam("pageSize") Long pageSize) {
		IPage<SysLog> page = new Page<>(current, pageSize);
		return sysLogMapper.selectPage(page,null);
	}

	@ApiOperation("列表-分页customer")
	@GetMapping("/pageList")
	public PageInfo pageList(SysLog sysLog) {
		return this.sysLogService.pageList(sysLog);
	}

}
