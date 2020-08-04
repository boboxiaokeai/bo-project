package cn.bo.project.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
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
@MapperScan(value={"cn.bo.project.mapper*"})
public class MybatisPlusConfig {

    /**
     *  mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量 ，默认500条 ，-1不受限制
        paginationInterceptor.setLimit(-1);
        // 开启count的join优化,只针对部分left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

}
