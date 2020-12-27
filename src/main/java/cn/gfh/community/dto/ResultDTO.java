package cn.gfh.community.dto;

import cn.gfh.community.exception.CustomizeErrorCode;
import cn.gfh.community.exception.CustomizeException;
import lombok.Data;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-11-04 16:56
 */
@Data
public class ResultDTO {
    private Integer code;
    private String message;

    /**
     * @return cn.gfh.community.dto.ResultDTO
     * @description: 错误来自原因
     * @author:Karol Guo
     * @date:2019/12/1
     * @param: code
     * @param: message
     */
    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException e) {

        return errorOf(e.getCode(), e.getMessage());
    }
}
