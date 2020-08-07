package cn.bo.project.common.response;

/**
 * @Author zhangbo
 * @Date 2020/6/23 23:10
 * @Description ResponseData
 * @PackageName cn.bo.project.common.response
 **/
public class SuccessResponseData extends ResponseData{


    public SuccessResponseData(){super(true,DEFAULT_SUCCESS_CODE,DEFAULT_SUCCESS_MESSAGE,null);}

    public SuccessResponseData(Object object,String message,Integer code){super(true,code,message,object);}

    public SuccessResponseData(Object object,String message){super(true,DEFAULT_SUCCESS_CODE,message,object);}

    public SuccessResponseData(Object object){super(true,DEFAULT_SUCCESS_CODE,DEFAULT_SUCCESS_MESSAGE,object);}

    public SuccessResponseData(String message){super(true,DEFAULT_SUCCESS_CODE,message,null);}


}
