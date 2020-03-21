package cn.bo.project.admin.modules.system.service;

import cn.bo.project.admin.modules.system.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author zhangbo
 * @Date 2020/1/4 13:16
 * @Description 系统日志service接口
 * @PackageName cn.bo.project.admin.modules.system.service
 **/
public interface ISysLogService extends IService<SysLog> {

	/**
	 * @功能：清空所有日志记录
	 */
	public void removeAll();
	
	/**
	 * 获取系统总访问次数
	 *
	 * @return Long
	 */
	Long findTotalVisitCount();

	//update-begin--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
	/**
	 * 获取系统今日访问次数
	 *
	 * @return Long
	 */
	Long findTodayVisitCount(Date dayStart, Date dayEnd);

	/**
	 * 获取系统今日访问 IP数
	 *
	 * @return Long
	 */
	Long findTodayIp(Date dayStart, Date dayEnd);
	//update-end--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
	
	/**
	 *   首页：根据时间统计访问数量/ip数量
	 * @param dayStart
	 * @param dayEnd
	 * @return
	 */
	List<Map<String,Object>> findVisitCount(Date dayStart, Date dayEnd);

	List<SysLog> selectUserLog(String userName);
}
