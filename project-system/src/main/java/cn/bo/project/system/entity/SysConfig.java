package cn.bo.project.system.entity;

import cn.bo.project.common.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author zhangbo
 * @Date 2020/2/9 22:34
 * @Description 参数配置表实体类
 * @PackageName cn.bo.project.system.entity
 **/
@Data
public class SysConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    private Long configId;

    /** 参数名称 */
    private String configName;

    /** 参数键名 */
    private String configKey;

    /** 参数键值 */
    private String configValue;

    /** 系统内置（Y是 N否） */
    private String configType;
}
