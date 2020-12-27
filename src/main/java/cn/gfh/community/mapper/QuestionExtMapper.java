package cn.gfh.community.mapper;

import cn.gfh.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question question);
    int incCommentCount(Question question);
}