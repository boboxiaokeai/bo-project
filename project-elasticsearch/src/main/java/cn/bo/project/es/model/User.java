package cn.bo.project.es.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author zhangbo
 * @Date 2020/7/26 22:21
 * @Description
 * @PackageName cn.bo.project.es.model
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private int age;
}
