package cn.gfh.community.interceptor;

import cn.gfh.community.mapper.UserMapper;
import cn.gfh.community.model.User;
import cn.gfh.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-28 11:30
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {//判断是否有cookie
            for (Cookie cookie : cookies) {//循环遍历cookie
                if (cookie.getName().equals("token")) {//将登录的cookie和数据库中的cookie做对比，如果登于，保持登录状态
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    //User user = userMapper.findByToken(token);
                    if (users.size() != 0) {
                        request.getSession().setAttribute("user", users.get(0));//将用户的信息放到session中
                    }
                    break;
                }
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
