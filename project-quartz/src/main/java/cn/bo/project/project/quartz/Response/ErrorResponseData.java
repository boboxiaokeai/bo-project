package cn.bo.project.project.quartz.Response;

public class ErrorResponseData extends ResponseData{

    public ErrorResponseData(){super(false,DEFAULT_ERROR_CODE,DEFAULT_ERROR_MESSAGE,null);}

    public ErrorResponseData(String message,Integer code){super(false,code,message,null);}

    public ErrorResponseData(String message){super(false,DEFAULT_ERROR_CODE,message,null);}
}
