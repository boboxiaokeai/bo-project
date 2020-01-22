package cn.bo.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableAutoConfiguration
public class ProjectAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectAdminApplication.class, args);
    }

}
