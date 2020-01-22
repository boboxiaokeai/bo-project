package cn.bo.project.admin.modules.system.service;

import cn.bo.project.admin.modules.system.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Author zhangbo
 * @Date 2020/1/8 16:28
 * @Description 用户角色service接口
 * @PackageName cn.bo.project.admin.modules.system.service
 **/
public interface ISysUserRoleService extends IService<SysUserRole> {
	
	/**
	 * 查询所有的用户角色信息
	 * @return
	 */
	Map<String,String> queryUserRole();
}
