package cn.bo.project.common.constant;

/**
 * @Author zhangbo
 * @Date 2020/2/9 16:42
 * @Description 用户常量
 * @PackageName cn.bo.project.common.constant
 **/
public interface UserConstants {
    /**
     * 平台内系统用户的唯一标志
     */
    String SYS_USER = "SYS_USER";

    /** 正常状态 */
    String NORMAL = "0";

    /** 异常状态 */
    String EXCEPTION = "1";

    /** 用户封禁状态 */
    String USER_BLOCKED = "1";

    /** 角色封禁状态 */
    String ROLE_BLOCKED = "1";

    /** 部门正常状态 */
    String DEPT_NORMAL = "0";

    /** 字典正常状态 */
    String DICT_NORMAL = "0";

    /** 是否为系统默认（是） */
    String YES = "Y";

    /** 校验返回结果码 */
    String UNIQUE = "0";

    String NOT_UNIQUE = "1";
}
