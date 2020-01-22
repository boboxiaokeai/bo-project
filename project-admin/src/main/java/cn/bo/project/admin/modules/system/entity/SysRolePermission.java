package cn.bo.project.admin.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @Author zhangbo
 * @Date 2020/1/8 17:14
 * @Description 角色权限实体类
 * @PackageName cn.bo.project.admin.modules.system.entity
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    
    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限id
     */
    private String permissionId;
    
    /**
     * 数据权限
     */
    private String dataRuleIds;

    public SysRolePermission() {
   	}
       
   	public SysRolePermission(String roleId, String permissionId) {
   		this.roleId = roleId;
   		this.permissionId = permissionId;
   	}

}
