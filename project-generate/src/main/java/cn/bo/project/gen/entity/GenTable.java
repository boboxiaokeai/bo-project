package cn.bo.project.gen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author zhangbo
 * @Date 2020/7/27 10:11
 * @Description
 * @PackageName cn.bo.project.gen.entity
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenTable implements Serializable {
    /** 编号 */
    private Long tableId;
    /** 表名称 */
    private String tableName;
    /** 表描述 */
    private String tableComment;
    /** 实体类名称(首字母大写) */
    private String className;
    /** 使用的模板（crud单表操作 tree树表操作） */
    private String tplCategory;
    /** 生成包路径 */
    private String packageName;
    /** 生成模块名 */
    private String moduleName;
    /** 生成业务名 */
    private String businessName;
    /** 生成功能名 */
    private String functionName;
    /** 生成作者 */
    private String functionAuthor;
    /** 主键信息 */
    private GenTableColumn pkColumn;
    /** 表列信息 */
    private List<GenTableColumn> columns;
    /** 其它生成选项 */
    private String options;
    /** 树编码字段 */
    private String treeCode;
    /** 树父编码字段 */
    private String treeParentCode;
    /** 树名称字段 */
    private String treeName;
    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 请求参数 */
    private Map<String, Object> params;
}
