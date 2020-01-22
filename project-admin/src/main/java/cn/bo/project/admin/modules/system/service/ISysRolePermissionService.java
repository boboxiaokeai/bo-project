package cn.bo.project.admin.modules.system.service;

import cn.bo.project.admin.modules.system.entity.SysRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author zhangbo
 * @Date 2020/1/8 16:28
 * @Description 角色权限service接口
 * @PackageName cn.bo.project.admin.modules.system.service
 **/
public interface ISysRolePermissionService extends IService<SysRolePermission> {
	
	/**
	 * 保存授权/先删后增
	 * @param roleId
	 * @param permissionIds
	 */
	public void saveRolePermission(String roleId, String permissionIds);

	/**
	 * 保存授权 将上次的权限和这次作比较 差异处理提高效率
	 * @param roleId
	 * @param permissionIds
	 * @param lastPermissionIds
	 */
	public void saveRolePermission(String roleId, String permissionIds, String lastPermissionIds);

}
