package cn.bo.project.admin.modules.system.service.impl;

import cn.bo.project.admin.modules.system.entity.SysRole;
import cn.bo.project.admin.modules.system.entity.SysUser;
import cn.bo.project.admin.modules.system.entity.SysUserRole;
import cn.bo.project.admin.modules.system.mapper.SysUserRoleMapper;
import cn.bo.project.admin.modules.system.service.ISysRoleService;
import cn.bo.project.admin.modules.system.service.ISysUserRoleService;
import cn.bo.project.admin.modules.system.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author zhangbo
 * @Date 2020/1/8 16:29
 * @Description 用户角色service实现类
 * @PackageName cn.bo.project.admin.modules.system.service.impl
 **/
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

	@Autowired
	private ISysUserService userService;
	@Autowired
	private ISysRoleService roleService;
	
	/**
	 * 查询所有用户对应的角色信息
	 */
	@Override
	public Map<String,String> queryUserRole() {
		List<SysUserRole> uRoleList = this.list();
		List<SysUser> userList = userService.list();
		List<SysRole> roleList = roleService.list();
		Map<String,String> map = new IdentityHashMap<>();
		String userId = "";
		String roleId = "";
		String roleName = "";
		if(uRoleList != null && uRoleList.size() > 0) {
			for(SysUserRole uRole : uRoleList) {
				roleId = uRole.getRoleId();
				for(SysUser user : userList) {
					userId = user.getId();
					if(uRole.getUserId().equals(userId)) {
						roleName = this.searchByRoleId(roleList,roleId);
						map.put(userId, roleName);
					}
				}
			}
			return map;
		}
		return map;
	}
	
	/**
	 * queryUserRole调用的方法
	 * @param roleList
	 * @param roleId
	 * @return
	 */
	private String searchByRoleId(List<SysRole> roleList, String roleId) {
		while(true) {
			for(SysRole role : roleList) {
				if(roleId.equals(role.getId())) {
					return role.getRoleName();
				}
			}
		}
	}

}
