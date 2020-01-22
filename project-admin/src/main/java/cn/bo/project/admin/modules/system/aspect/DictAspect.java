package cn.bo.project.admin.modules.system.aspect;

import cn.bo.project.admin.modules.system.service.ISysDictService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author zhangbo
 * @Date 2020/1/13 17:51
 * @Description 字典切面处理类
 * @PackageName cn.bo.project.admin.modules.system.aspect
 **/
@Aspect
@Component
public class DictAspect {
    @Autowired
    private ISysDictService sysDictService;
}
