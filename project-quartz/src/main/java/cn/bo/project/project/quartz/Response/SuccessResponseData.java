package cn.bo.project.project.quartz.Response;


public class SuccessResponseData extends ResponseData{


    public SuccessResponseData(){super(true,DEFAULT_SUCCESS_CODE,DEFAULT_SUCCESS_MESSAGE,null);}

    public SuccessResponseData(Object object,String message,Integer code){super(true,code,message,object);}

    public SuccessResponseData(Object object,String message){super(true,DEFAULT_SUCCESS_CODE,message,object);}

    public SuccessResponseData(Object object){super(true,DEFAULT_SUCCESS_CODE,DEFAULT_SUCCESS_MESSAGE,object);}

    public SuccessResponseData(String message){super(true,DEFAULT_SUCCESS_CODE,message,null);}


}
