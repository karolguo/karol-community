package cn.gfh.community.controller;

import cn.gfh.community.dto.PageDTO;
import cn.gfh.community.mapper.UserMapper;
import cn.gfh.community.model.User;
import cn.gfh.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-11 17:08
 */
@Controller
public class IndexController {
   /* @Autowired
    private UserMapper userMapper;*/
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size

    ) {//访问首页
       /* //获取cookie
        Cookie[] cookies = request.getCookies();
        System.out.println("cookie的长度为："+cookies.length);
        if (cookies.length > 0 && cookies != null) {//判断cookie是否为空
            for(int i =0 ; i<cookies.length;i++){
                System.out.println("第"+i+"个为："+cookies[i]);
                String getCookie = cookies[i].getValue();//获取第i个cookie元素
                User user = userMapper.findByToken(getCookie);//
                if(user!=null){
                    System.out.println(user.toString());
                    request.getSession().setAttribute("user",user);
                }
            }
        }*/


        //查询所有内容
        PageDTO pageDTO = questionService.findAllQuestion(page, size);
        model.addAttribute("pageDTO", pageDTO);
        return "index";
    }


}
