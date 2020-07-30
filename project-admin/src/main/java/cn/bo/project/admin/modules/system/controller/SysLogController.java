package cn.bo.project.admin.modules.system.controller;


import cn.bo.project.admin.modules.system.entity.SysLog;
import cn.bo.project.admin.modules.system.service.ISysLogService;
import cn.bo.project.base.core.query.QueryGenerator;
import cn.bo.project.base.response.ResponseData;
import cn.bo.project.base.utils.oConvertUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
	private ISysLogService sysLogService;
	
	/**
	 * @功能：查询日志记录
	 * @param syslog
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation("系统日志列表")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseData queryPageList(SysLog syslog, @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize, HttpServletRequest req) {
		QueryWrapper<SysLog> queryWrapper = QueryGenerator.initQueryWrapper(syslog, req.getParameterMap());
		Page<SysLog> page = new Page<SysLog>(pageNo, pageSize);
		//日志关键词
		String keyWord = req.getParameter("keyWord");
		if(oConvertUtils.isNotEmpty(keyWord)) {
			queryWrapper.like("log_content",keyWord);
		}
		IPage<SysLog> pageList = sysLogService.page(page, queryWrapper);
		return ResponseData.success(pageList);
	}
	
	/**
	 * @功能：删除单个日志记录
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseData delete(@RequestParam(name="id",required=true) String id) {
		SysLog sysLog = sysLogService.getById(id);
		if(sysLog==null) {
			ResponseData.error("未找到对应实体");
		}else {
			boolean ok = sysLogService.removeById(id);
			if(ok) {
				ResponseData.success("删除成功!");
			}
		}
		return ResponseData.success();
	}
	
}
