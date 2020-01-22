package cn.bo.project.base.core.base.controller;

import cn.bo.project.base.utils.ServletUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author zhangbo
 * @Date 2020/1/2 17:17
 * @Description 控制器基类
 * @PackageName cn.bo.project.base.core.base.controller
 **/
public class BaseController<T, S extends IService<T>> {
    @Autowired S service;
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private String getId(T item) {
        try {
            return PropertyUtils.getProperty(item, "id").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public HttpServletRequest getRequest()
    {
        return ServletUtils.getRequest();
    }

    public HttpServletResponse getResponse()
    {
        return ServletUtils.getResponse();
    }

    public HttpSession getSession()
    {
        return getRequest().getSession();
    }
}
