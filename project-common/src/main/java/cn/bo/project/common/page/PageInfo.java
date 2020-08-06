package cn.bo.project.common.page;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author zhangbo
 * @Date 2020/8/4 18:40
 * @Description 接口返回的分页数据对象
 * @PackageName cn.bo.project.Page
 **/
@Data
public class PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List data;

    private long count;

    private Integer code = 200;

    private String message = "请求成功";

}
