package cn.gfh.community.controller;

import cn.gfh.community.dto.CommentDTO;
import cn.gfh.community.dto.ResultDTO;
import cn.gfh.community.exception.CustomizeErrorCode;
import cn.gfh.community.mapper.CommentMapper;
import cn.gfh.community.model.Comment;
import cn.gfh.community.model.User;
import cn.gfh.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-11-04 16:27
 * 评论controller
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * @return java.lang.Object
     * @description:
     * @author:Karol Guo
     * @date:2019/12/1
     * @param: request
     * @param: commentDTO
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(HttpServletRequest request, @RequestBody CommentDTO commentDTO) {//接收json格式传输数据，通过requestbody反序列化成java的对象
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);

        return ResultDTO.okOf();
    }
}
