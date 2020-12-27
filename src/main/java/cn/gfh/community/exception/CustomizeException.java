package cn.gfh.community.exception;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-11-01 15:55
 * 自定义异常
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code ;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
