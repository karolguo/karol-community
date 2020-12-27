package cn.gfh.community.dto;

import lombok.Data;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-15 17:16
 * dto 是数据传输模型（Data transfer model）
  */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;//用户描述
    private String avatarUrl;//用户头像


}


