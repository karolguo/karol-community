package cn.gfh.community.service;

import cn.gfh.community.enums.CommentTypeEnum;
import cn.gfh.community.exception.CustomizeErrorCode;
import cn.gfh.community.exception.CustomizeException;
import cn.gfh.community.mapper.CommentMapper;
import cn.gfh.community.mapper.QuestionExtMapper;
import cn.gfh.community.mapper.QuestionMapper;
import cn.gfh.community.model.Comment;
import cn.gfh.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-11-04 17:05
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    /**
     * @return void
     * @description: 插入评论
     * @author:Karol Guo
     * @date:2019/12/1
     * @param: comment
     */
    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
        }
    }

}
