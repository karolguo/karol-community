package cn.gfh.community.service;

import cn.gfh.community.dto.PageDTO;
import cn.gfh.community.dto.QuestionDTO;
import cn.gfh.community.exception.CustomizeErrorCode;
import cn.gfh.community.exception.CustomizeException;
import cn.gfh.community.mapper.QuestionExtMapper;
import cn.gfh.community.mapper.QuestionMapper;
import cn.gfh.community.mapper.UserMapper;
import cn.gfh.community.model.Question;
import cn.gfh.community.model.QuestionExample;
import cn.gfh.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-18 16:56
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;

    public PageDTO findAllQuestion(Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        //查询总条数
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        pageDTO.setPageDTO(totalCount, page, size);
        //判断页数越界
        if (page < 1) {
            page = 1;
        }
        if (page > pageDTO.getTotalPage()) {
            page = pageDTO.getTotalPage();
        }

        Integer offset = size * (page - 1);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //mybatis分页插件
        List<Question> questionList = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //spring提供工具类，将前面的变量赋值给后面的
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        pageDTO.setQuestion(questionDTOList);


        return pageDTO;
    }

    public PageDTO findQuestionByUserId(Long userId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        //查询总条数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        pageDTO.setPageDTO(totalCount, page, size);
        //判断页数越界
        if (page < 1) {
            page = 1;
        }
        if (page > pageDTO.getTotalPage()) {
            page = pageDTO.getTotalPage();
        }

        Integer offset = size * (page - 1);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //spring提供工具类，将前面的变量赋值给后面的
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        pageDTO.setQuestion(questionDTOList);


        return pageDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(questionDTO.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            //创建问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtModified());
            questionMapper.insert(question);
        }else{
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated != 1){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {//增加阅读数
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}