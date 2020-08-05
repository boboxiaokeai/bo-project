package cn.bo.project.service.impl;

import cn.bo.project.entity.SysLog;
import cn.bo.project.mapper.SysLogMapper;
import cn.bo.project.page.PageFactory;
import cn.bo.project.page.PageInfo;
import cn.bo.project.service.SysLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhangbo
 * @Date 2020/8/5 8:57
 * @Description
 * @PackageName cn.bo.project.service.impl
 **/
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper,SysLog> implements SysLogService{
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public PageInfo pageList(SysLog sysLog) {
        IPage page = this.sysLogMapper.pageList(PageFactory.defaultPage(), sysLog);
        return PageFactory.createPageInfo(page);
    }
}
