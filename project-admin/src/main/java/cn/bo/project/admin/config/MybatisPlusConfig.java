package cn.bo.project.admin.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author zhangbo
 * @Date 2020/1/3 13:56
 * @Description mybatis-plus配置类
 * @PackageName cn.bo.project.admin.config
 **/
@Configuration
@MapperScan(value={"cn.bo.project.admin.modules.**.mapper*"})
public class MybatisPlusConfig {

    /**
     *  mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // 设置sql的limit为无限制，默认是500
        return new PaginationInterceptor().setLimit(-1);
    }

}
