package cn.bo.project.service;

import cn.bo.project.entity.SysLog;
import cn.bo.project.page.PageInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author zhangbo
 * @Date 2020/8/4 18:13
 * @Description
 * @PackageName cn.bo.project.service
 **/
public interface SysLogService extends IService<SysLog> {

    /**
     * 查询列表分页数据
     * @param sysLog
     * @return
     */
    PageInfo pageList(SysLog sysLog);
}
