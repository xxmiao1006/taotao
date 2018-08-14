package com.Alice.search.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器的类
 * @author Alice
 * @date 2018/8/14/014
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //1.日子写入到日志文件总
        System.out.println(ex.getMessage());
        //2.及时通知开发人员(通过第三方接口)
        //3.给用户一个友好的提示
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        modelAndView.addObject("message","您的网络异常，请重试");
        return modelAndView;

    }
}
