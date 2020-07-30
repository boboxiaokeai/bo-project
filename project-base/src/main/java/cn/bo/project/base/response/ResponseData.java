package cn.bo.project.base.response;


import lombok.Data;

/**
 * @Author zhangbo
 * @Date 2020/6/23 23:10
 * @Description ResponseData
 * @PackageName cn.bo.project.base.response
 **/
@Data
public class ResponseData {

    public static final Integer DEFAULT_SUCCESS_CODE = 200;

    public static final Integer DEFAULT_UNAUTHORIZED = 401;

    public static final Integer DEFAULT_ERROR_CODE = 500;

    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    public static final String DEFAULT_ERROR_MESSAGE = "网络异常";

    public static final String DEFAULT_UNAUTHORIZED_MESSAGE = "未授权";

    /**
     * 请求是否成功
     */
    private Boolean success;

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应对象
     */
    private Object data;

    public ResponseData() {
    }

    public ResponseData(Boolean success, Integer code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static SuccessResponseData success() {
        return new SuccessResponseData();
    }

    public static SuccessResponseData success(Object object) {
        return new SuccessResponseData(object);
    }

    public static SuccessResponseData success(String message) {
        return new SuccessResponseData(message);
    }

    public static SuccessResponseData success(Object object, String message) {
        return new SuccessResponseData(object,message);
    }

    public static SuccessResponseData success(Object object, String message,Integer code) {
        return new SuccessResponseData(object,message,code);
    }

    public static ErrorResponseData error() {
        return new ErrorResponseData();
    }

    public static ErrorResponseData error(String message) {
        return new ErrorResponseData(message);
    }

    public static ErrorResponseData error(String message,Integer code) {
        return new ErrorResponseData(message,code);
    }

    @Override
    public String toString(){
        return "code:"+  code + "; message:" + message + "; data:"+  data == null ? "null" : data.toString();
    }
}
