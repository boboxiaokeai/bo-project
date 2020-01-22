package cn.bo.project.base.annotation;

import cn.bo.project.base.constant.CommonConstant;
import cn.bo.project.base.enums.BusinessType;
import cn.bo.project.base.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author zhangbo
 * @Date 2020/1/2 16:11
 * @Description 操作日志注解
 * @PackageName cn.bo.project.base.annotation
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
     * @return 0:操作日志;1:登录日志;2:定时任务;
     */
    int logType() default CommonConstant.LOG_TYPE_2;

    /**
     * 操作日志类型
     *
     * @return （1查询，2添加，3修改，4删除）
     */
    int operateType() default 0;


}
