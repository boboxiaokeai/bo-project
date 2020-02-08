package cn.bo.project.admin.modules.system.model;


import lombok.Data;

/**
 * @Author zhangbo
 * @Date 2020/1/6 13:59
 * @Description 登录表单对象
 * @PackageName cn.bo.project.admin.modules.system.model
 **/
@Data
public class SysLoginModel {

    private String username;//账号

    private String password;//密码

    private String code;//验证码

    private String codekey;//验证码key
}