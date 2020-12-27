package cn.gfh.community.controller;

import cn.gfh.community.dto.AccessTokenDTO;
import cn.gfh.community.dto.GithubUser;
import cn.gfh.community.model.User;
import cn.gfh.community.provider.GithubProvider;
import cn.gfh.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-12 15:59
 * Github登录认证
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    //使用@Value注解从application.yml文件读取数据
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        //调用github的access_token接口获取accessToken
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //使用accesssToken从github取出github用户的信息
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            System.out.println(githubUser.toString());

            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.updateUser(user);
            //登录成功，写入cookie和session
            //request.getSession().setAttribute("user",user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response) {//登出
        System.out.println("logout 开始移除session");
        //移除session
        request.getSession().removeAttribute("user");
        System.out.println("logout 移除session完毕");
        System.out.println("logout 开始移除cookie");
        //移除cookie
        //先将cookie重写，value赋值为null
        Cookie cookie = new Cookie("token",null);
        //立即执行
        cookie.setMaxAge(0);
        //项目所有目录均有效
        cookie.setPath("/");
        //添加重名的cookie到cookie中，覆盖掉原有的cookie
        response.addCookie(cookie);
        System.out.println("logout 移除cookie完毕");
        return "redirect:/";
    }

}
