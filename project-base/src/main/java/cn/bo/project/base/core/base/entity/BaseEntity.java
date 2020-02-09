package cn.bo.project.base.core.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.Map;

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
    @TableId(type = IdType.AUTO)
    private String id;
    /** 创建人 */
    private String createBy;
    /** 创建时间 */
    private Date createTime;
    /** 更新人 */
    private String updateBy;
    /** 更新时间 */
    private java.util.Date updateTime;
    /** 备注 */
    private String remark;
    /** 数据权限 */
    private String dataScope;
    /** 开始时间 */
    @JsonIgnore
    private String beginTime;
    /** 结束时间 */
    @JsonIgnore
    private String endTime;
    /** 请求参数 */
    private Map<String, Object> params;
}
