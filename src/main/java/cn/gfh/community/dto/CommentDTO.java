package cn.gfh.community.dto;

import lombok.Data;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-11-04 16:35
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
