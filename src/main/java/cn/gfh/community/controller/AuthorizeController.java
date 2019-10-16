package cn.gfh.community.controller;

import cn.gfh.community.dto.AccessTokenDTO;
import cn.gfh.community.dto.GithubUser;
import cn.gfh.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
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
        System.out.println(githubUser.toString());
        return "index";
    }
}
