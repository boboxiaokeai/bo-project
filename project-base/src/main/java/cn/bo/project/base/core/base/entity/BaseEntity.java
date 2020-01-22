package cn.bo.project.base.core.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author zhangbo
 * @Date 2020/1/3 9:21
 * @Description entity基类
 * @PackageName cn.bo.project.base.core.base.entity
 **/
@Data
public class BaseEntity {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /** 创建人 */
    private String createBy;
    /** 创建时间 */
    private java.util.Date createTime;
    /** 更新人 */
    private String updateBy;
    /** 更新时间 */
    private java.util.Date updateTime;
}
