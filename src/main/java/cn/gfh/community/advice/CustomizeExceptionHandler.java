package cn.gfh.community.advice;

import cn.gfh.community.dto.ResultDTO;
import cn.gfh.community.exception.CustomizeErrorCode;
import cn.gfh.community.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Karol
 * @project_name community_demo
 * @create_date 2019-11-01 15:26
 * 异常页面处理，跳转error页面
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {

        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            //返回json
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException io) {

            }
            return null;
        } else {
            //返回错误页面
            if (e instanceof CustomizeException) {
                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            } else {
                model.addAttribute("message", "太火爆啦，一会再来吧！");
            }
            return new ModelAndView("error");
        }


    }

}
