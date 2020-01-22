package cn.bo.project.admin.modules.system.service.impl;

import cn.bo.project.admin.modules.system.entity.SysUser;
import cn.bo.project.admin.modules.system.mapper.SysLogMapper;
import cn.bo.project.admin.modules.system.mapper.SysUserMapper;
import cn.bo.project.admin.modules.system.service.ISysUserService;

import cn.bo.project.base.api.ResultBean;
import cn.bo.project.base.constant.CommonConstant;
import cn.bo.project.base.core.api.ISysBaseAPI;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author zhangbo
 * @Date 2020/1/6 13:42
 * @Description 用户service实现类
 * @PackageName cn.bo.project.admin.modules.system.service.impl
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public SysUser getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public ResultBean<?> checkUserIsEffective(SysUser sysUser) {
        ResultBean<?> result = new ResultBean<Object>();
        //情况1：根据用户信息查询，该用户不存在
        if (sysUser == null) {
            result.error500("该用户不存在，请注册");
            sysBaseAPI.addLog("用户登录失败，用户不存在！", CommonConstant.LOG_TYPE_1, null);
            return result;
        }
        //情况2：根据用户信息查询，该用户已注销
        if (CommonConstant.DEL_FLAG_1.toString().equals(sysUser.getDelFlag())) {
            sysBaseAPI.addLog("用户登录失败，用户名:" + sysUser.getUsername() + "已注销！", CommonConstant.LOG_TYPE_1, null);
            result.error500("该用户已注销");
            return result;
        }
        //情况3：根据用户信息查询，该用户已冻结
        if (CommonConstant.USER_FREEZE.equals(sysUser.getStatus())) {
            sysBaseAPI.addLog("用户登录失败，用户名:" + sysUser.getUsername() + "已冻结！", CommonConstant.LOG_TYPE_1, null);
            result.error500("该用户已冻结");
            return result;
        }
        return result;
    }
}
