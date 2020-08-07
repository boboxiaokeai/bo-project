package cn.bo.project.system.service;

import cn.bo.project.common.page.PageInfo;
import cn.bo.project.system.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * @Author zhangbo
 * @Date 2020/1/4 13:16
 * @Description 系统日志service接口
 * @PackageName cn.bo.project.system.service
 **/
public interface SysLogService extends IService<SysLog> {

    /**
     * 查询列表分页数据
     * @param sysLog
     * @return
     */
    PageInfo pageList(SysLog sysLog);

}
