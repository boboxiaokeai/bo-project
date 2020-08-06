package cn.bo.project.common.response;

/**
 * @Author zhangbo
 * @Date 2020/6/23 23:10
 * @Description ResponseData
 * @PackageName cn.bo.project.base.response
 **/
public class ErrorResponseData extends ResponseData{

    public ErrorResponseData(){super(false,DEFAULT_ERROR_CODE,DEFAULT_ERROR_MESSAGE,null);}

    public ErrorResponseData(String message,Integer code){super(false,code,message,null);}

    public ErrorResponseData(String message){super(false,DEFAULT_ERROR_CODE,message,null);}
}
