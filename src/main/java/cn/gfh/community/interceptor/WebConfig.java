package cn.gfh.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-10-28 11:23
 */
@Configuration
//@EnableWebMvc千万不能加，谁加谁傻逼 还有！application.yml也别配置！！
public class WebConfig implements WebMvcConfigurer {

    //private static final List<String> EXCLUDE_PATH= Arrays.asList("/","/css/**","/js/**","/fonts/**");

    @Autowired
    private SessionInterceptor sessionInterceptor;

    /*
     *  对根目录和静态文件不需要进行拦截，如果对根目录（即登录页面）进行拦截，将会导致循环重定向
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //patterns指请求的时候，拦截的请求。excludePathPatterns表示不拦截的请求
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");//.excludePathPatterns(EXCLUDE_PATH);

    }

}
