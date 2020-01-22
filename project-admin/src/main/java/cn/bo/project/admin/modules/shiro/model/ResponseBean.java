package cn.bo.project.admin.modules.shiro.model;

import lombok.Data;

@Data
public class ResponseBean {
    
    // http 状态码
    private int code;
 
    // 返回信息
    private String msg;
 
    // 返回的数据
    private Object data;
 
    public ResponseBean(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}