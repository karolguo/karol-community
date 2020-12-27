package cn.gfh.community.controller;

import cn.gfh.community.dto.PageDTO;
import cn.gfh.community.model.User;
import cn.gfh.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-25 10:58
 * 个人中心的controller
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size
    ){

        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        PageDTO pageDTO = questionService.findQuestionByUserId(user.getId(), page, size);
        model.addAttribute("pageDTO",pageDTO);
        return "profile";
    }
}
