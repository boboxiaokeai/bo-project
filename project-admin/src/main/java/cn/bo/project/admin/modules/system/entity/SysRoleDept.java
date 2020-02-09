package cn.bo.project.admin.modules.system.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author zhangbo
 * @Date 2020/2/9 22:34
 * @Description 角色和部门关联表实体类
 * @PackageName cn.bo.project.admin.modules.system.entity
 **/
public class SysRoleDept {
    /** 角色ID */
    private Long roleId;
    
    /** 部门ID */
    private Long deptId;

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("deptId", getDeptId())
            .toString();
    }
}
