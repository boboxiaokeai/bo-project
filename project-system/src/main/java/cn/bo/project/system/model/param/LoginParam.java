package cn.bo.project.system.model.param;

import lombok.Data;

/**
 * @Author zhangbo
 * @Date 2020/8/7 17:19
 * @Description
 * @PackageName cn.bo.project.system.model.param
 **/
@Data
public class LoginParam {
    private String userName;
    private String passWord;
    private String code;
    private String codekey;
}
