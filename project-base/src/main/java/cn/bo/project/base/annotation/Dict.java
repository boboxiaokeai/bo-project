package cn.bo.project.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author zhangbo
 * @Date 2020/1/2 16:11
 * @Description 字典注解
 * @PackageName cn.bo.project.base.annotation
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {

    String dicCode();//数据code

    String dicText() default "";//数据Text

    String dictTable() default ""; //数据字典表
}
