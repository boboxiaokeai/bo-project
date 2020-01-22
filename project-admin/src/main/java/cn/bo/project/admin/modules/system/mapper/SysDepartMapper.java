package cn.bo.project.admin.modules.system.mapper;

import cn.bo.project.admin.modules.system.entity.SysDepart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author zhangbo
 * @Date 2020/1/10 11:01
 * @Description 部门mapper接口
 * @PackageName cn.bo.project.admin.modules.system.mapper
 **/
public interface SysDepartMapper extends BaseMapper<SysDepart> {
	
	/**
	 * 根据用户ID查询部门集合
	 */
	public List<SysDepart> queryUserDeparts(@Param("userId") String userId);

	/**
	 * 根据用户名查询部门
	 *
	 * @param username
	 * @return
	 */
	public List<SysDepart> queryDepartsByUsername(@Param("username") String username);

	@Select("select id from sys_depart where org_code=#{orgCode}")
	public String queryDepartIdByOrgCode(@Param("orgCode") String orgCode);

	@Select("select id,parent_id from sys_depart where id=#{departId}")
	public SysDepart getParentDepartId(@Param("departId") String departId);

}
