package cn.bo.project.admin.modules.system.service.impl;

import cn.bo.project.admin.modules.system.model.SysUserOnline;
import cn.bo.project.admin.modules.system.service.ISysUserOnlineService;
import cn.bo.project.base.core.model.LoginUser;
import cn.bo.project.base.utils.StringUtils;
import org.springframework.stereotype.Service;


/**
 * @Author zhangbo
 * @Date 2020/1/4 13:16
 * @Description 在线用户service实现类
 * @PackageName cn.bo.project.admin.modules.system.service.impl
 **/
@Service
public class SysUserOnlineServiceImpl  implements ISysUserOnlineService {


    @Override
    public SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user) {
        return null;
    }

    /**
     * 通过用户名称查询信息
     * 
     * @param userName 用户名称
     * @param user 用户信息
     * @return 在线用户信息
     */
    @Override
    public SysUserOnline selectOnlineByUserName(String userName, LoginUser user)
    {
        if (StringUtils.equals(userName, user.getUserName()))
        {
            return loginUserToUserOnline(user);
        }
        return null;
    }

    @Override
    public SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user) {
        return null;
    }

    @Override
    public SysUserOnline loginUserToUserOnline(LoginUser user) {
        return null;
    }

}
