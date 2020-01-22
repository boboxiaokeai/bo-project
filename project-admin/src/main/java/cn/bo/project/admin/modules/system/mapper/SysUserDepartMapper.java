package cn.bo.project.admin.modules.system.mapper;

import cn.bo.project.admin.modules.system.entity.SysUserDepart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @Author zhangbo
 * @Date 2020/1/10 11:04
 * @Description 用户部门mapper接口
 * @PackageName cn.bo.project.admin.modules.system.mapper
 **/
public interface SysUserDepartMapper extends BaseMapper<SysUserDepart>{
	
	List<SysUserDepart> getUserDepartByUid(@Param("userId") String userId);
}
