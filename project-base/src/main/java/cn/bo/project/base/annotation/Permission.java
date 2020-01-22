package cn.bo.project.base.annotation;

import java.lang.annotation.*;

/**
 * @Author zhangbo
 * @Date 2020/1/2 16:11
 * @Description 数据权限注解
 * @PackageName cn.bo.project.base.annotation
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface Permission {

	String value() default "";

	String pageComponent() default "";//配置菜单的组件路径,用于数据权限
}