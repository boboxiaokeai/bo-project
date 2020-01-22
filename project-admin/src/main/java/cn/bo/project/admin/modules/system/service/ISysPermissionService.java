package cn.bo.project.admin.modules.system.service;

import cn.bo.project.admin.modules.system.entity.SysPermission;
import cn.bo.project.admin.modules.system.model.TreeModel;
import cn.bo.project.base.expection.BootProjectException;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * @Author zhangbo
 * @Date 2020/1/8 16:28
 * @Description 权限service接口
 * @PackageName cn.bo.project.admin.modules.system.service
 **/
public interface ISysPermissionService extends IService<SysPermission> {
	
	public List<TreeModel> queryListByParentId(String parentId);
	
	/**真实删除*/
	public void deletePermission(String id) throws BootProjectException;
	/**逻辑删除*/
	public void deletePermissionLogical(String id) throws BootProjectException;
	
	public void addPermission(SysPermission sysPermission) throws BootProjectException;
	
	public void editPermission(SysPermission sysPermission) throws BootProjectException;
	
	public List<SysPermission> queryByUser(String username);

	/**
	  * 查询出带有特殊符号的菜单地址的集合
	 * @return
	 */
	public List<String> queryPermissionUrlWithStar();
}
