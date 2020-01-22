package cn.bo.project.base.enums;

/**
 * @Author zhangbo
 * @Date 2020/1/2 16:25
 * @Description 业务操作类型枚举类
 * @PackageName cn.bo.project.base.enums
 **/
public enum BusinessType {
    /**
     * 其它
     */
    OTHER,

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
     * 清空
     */
    CLEAN,
}
