package cn.bo.project.gen;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableSwaggerBootstrapUI
@MapperScan(basePackages = "cn.bo.project.gen.mapper")
public class ProjectGenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectGenerateApplication.class, args);
    }

}
