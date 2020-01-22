package cn.bo.project.admin.modules.system.service;

import cn.bo.project.admin.modules.system.entity.SysUser;
import cn.bo.project.base.api.ResultBean;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * @Author zhangbo
 * @Date 2020/1/6 13:41
 * @Description 用户service接口
 * @PackageName cn.bo.project.admin.modules.system.service
 **/
public interface ISysUserService extends IService<SysUser> {
    SysUser getUserByName(String username);

    ResultBean checkUserIsEffective(SysUser sysUser);

}
