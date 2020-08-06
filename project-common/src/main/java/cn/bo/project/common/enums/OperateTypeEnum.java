package cn.bo.project.common.enums;

/**
 * @Author zhangbo
 * @Date 2020/8/5 9:19
 * @Description 系统操作类型枚举类
 * @PackageName cn.bo.project.common.enums
 **/
public enum OperateTypeEnum {

    /**
     * 查询
     */
    SELECT,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,
    
    /**
     * 清空数据
     */
    CLEAN,

    /**
     * 其它
     */
    OTHER,
}
