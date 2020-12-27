package cn.gfh.community.dto;

import lombok.Data;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-15 16:41
 * dto 是数据传输模型（Data transfer model）
 * 获取github用户的信息的实体（模型）
 */
@Data
public class AccessTokenDTO {
    private String client_id ;
    private String client_secret ;
    private String code;
    private String redirect_uri ;
    private String state ;

}
