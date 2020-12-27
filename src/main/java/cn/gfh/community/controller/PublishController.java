package cn.gfh.community.controller;

import cn.gfh.community.dto.QuestionDTO;
import cn.gfh.community.model.Question;
import cn.gfh.community.model.User;
import cn.gfh.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-18 11:50
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Long id,Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @GetMapping("/publish")
    public String toPublish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(//发布问题
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("id") Long id,
            @RequestParam("tag") String tag,
                                HttpServletRequest request, Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //获取用户信息
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        if(title==null || title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null || description==""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if(tag==null || tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCommentCount(0);
        question.setLikeCount(0);
        question.setViewCount(0);
        question.setId(id);

        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
