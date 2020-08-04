package cn.bo.project;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwaggerBootstrapUI
@EnableSwagger2
public class ProjectMybatisplusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectMybatisplusApplication.class, args);
    }

}
