package cn.bo.project.base.core.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author zhangbo
 * @Date 2020/1/3 9:31
 * @Description 菜单权限规则model
 * @PackageName cn.bo.project.base.core.model
 **/
@Data
public class SysPermissionDataRuleModel {

    /**
     * id
     */
    private String id;

    /**
     * 对应的菜单id
     */
    private String permissionId;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 字段
     */
    private String ruleColumn;

    /**
     * 条件
     */
    private String ruleConditions;

    /**
     * 规则值
     */
    private String ruleValue;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateBy;
}
