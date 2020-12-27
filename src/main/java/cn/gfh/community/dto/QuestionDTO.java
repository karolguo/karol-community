package cn.gfh.community.dto;

import cn.gfh.community.model.User;
import lombok.Data;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-18 16:54
 */
@Data
public class QuestionDTO {
    private Long id ;
    private String title ;
    private String description ;
    private String tag ;
    private Long gmtCreate;
    private Long gmtModified ;
    private Long creator ;
    private Integer viewCount ;
    private Integer commentCount ;
    private Integer likeCount ;
    private User user;
}
