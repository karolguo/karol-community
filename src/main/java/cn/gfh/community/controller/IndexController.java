package cn.gfh.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-11 17:08
 */
@Controller
public class IndexController {

    @RequestMapping("/hello")
    public String toIndex(@RequestParam(name = "name") String name, Model model){
        model.addAttribute("name",name);
        return "index";
    }
}
