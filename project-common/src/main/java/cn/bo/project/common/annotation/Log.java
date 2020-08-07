package cn.bo.project.common.annotation;

import cn.bo.project.common.constant.CommonConstant;
import cn.bo.project.common.enums.OperateTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Author zhangbo
 * @Date 2020/1/2 16:11
 * @Description 操作日志注解
 * @PackageName cn.bo.project.common.annotation
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 日志内容
     *
     * @return
     */
    String value() default "";

    /**
     * 日志类型
     *
     * @return 0:登录日志;1:操作日志;
     */
    int logType() default CommonConstant.LOG_TYPE_LOGIN;

    /**
     * 操作日志类型
     *
     * @return OperateTypeEnum
     */
    OperateTypeEnum operateType() default OperateTypeEnum.OTHER;

}
