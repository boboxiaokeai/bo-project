package cn.bo.project.system.entity;

import cn.bo.project.common.base.entity.BaseEntity;
import lombok.Data;

/**
 * @Author zhangbo
 * @Date 2020/1/13 17:54
 * @Description 字典类型实体类
 * @PackageName cn.bo.project.system.entity
 **/
@Data
public class SysDictType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    private Long dictId;

    /** 字典名称 */
    private String dictName;

    /** 字典类型 */
    private String dictType;

    /** 状态（0正常 1停用） */
    private String status;
}
