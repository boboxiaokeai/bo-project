package cn.bo.project.admin.modules.system.service;


import cn.bo.project.admin.modules.system.entity.SysUser;
import cn.bo.project.admin.modules.system.entity.SysUserDepart;
import cn.bo.project.admin.modules.system.model.DepartIdModel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author zhangbo
 * @Date 2020/1/4 13:16
 * @Description 用户部门service接口
 * @PackageName cn.bo.project.admin.modules.system.service
 **/
public interface ISysUserDepartService extends IService<SysUserDepart> {
	

	/**
	 * 根据指定用户id查询部门信息
	 * @param userId
	 * @return
	 */
	List<DepartIdModel> queryDepartIdsOfUser(String userId);
	

	/**
	 * 根据部门id查询用户信息
	 * @param depId
	 * @return
	 */
	List<SysUser> queryUserByDepId(String depId);
}
