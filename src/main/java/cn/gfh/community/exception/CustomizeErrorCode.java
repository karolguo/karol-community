package cn.gfh.community.exception;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-11-01 16:07
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不存在，请换一个试试"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试"),
    SYSTEM_ERROR(2004,"系统错误"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在");

    private Integer code ;
    private String message;

    CustomizeErrorCode(Integer code,String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
