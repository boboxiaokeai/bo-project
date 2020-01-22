package cn.bo.project.admin.modules.system.mapper;

import cn.bo.project.admin.modules.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


/**
 * @Author zhangbo
 * @Date 2020/1/6 13:44
 * @Description 用户mapper接口
 * @PackageName cn.bo.project.admin.modules.system.mapper
 **/
public interface SysUserMapper extends BaseMapper<SysUser> {
    
    SysUser getUserByName(@Param("username") String username);

}
