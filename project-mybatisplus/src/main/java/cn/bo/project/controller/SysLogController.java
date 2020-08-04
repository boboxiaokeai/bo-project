package cn.bo.project.controller;

import cn.bo.project.Response.ResponseData;
import cn.bo.project.entity.SysLog;
import cn.bo.project.mapper.SysLogMapper;
import cn.bo.project.page.PageInfo;
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
		List<SysLog> sysLogList = sysLogMapper.selectList(null);
		return ResponseData.success(sysLogList);
	}

	@ApiOperation("通过id删除")
	@DeleteMapping("/{id}")
	public ResponseData del(@PathVariable String id) {
		return ResponseData.success(sysLogMapper.deleteById(id));
	}

	@ApiOperation("列表-分页")
	@DeleteMapping
	public IPage<SysLog> page(@RequestParam("current") Long current,@RequestParam("pageSize") Long pageSize) {
		IPage<SysLog> page = new Page<>(current, pageSize);
		PageInfo pageInfo = new PageInfo(1,10);
		return sysLogMapper.selectPage(page,null);
	}

	/*public IPage<User> selectUserPage(Page<User> page, Integer state) {
		// 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
		// page.setOptimizeCountSql(false);
		// 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
		// 要点!! 分页返回的对象与传入的对象是同一个
		return userMapper.selectPageVo(page, state);
	}*/
	
}
