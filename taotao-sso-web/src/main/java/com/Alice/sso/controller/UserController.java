package com.Alice.sso.controller;

import com.Alice.common.pojo.TaotaoResult;
import com.Alice.common.utils.CookieUtils;
import com.Alice.common.utils.JsonUtils;
import com.Alice.pojo.TbUser;
import com.Alice.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 用户处理Controller
 * @author Alice
 * @date 2018/8/18/018
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkUserData(@PathVariable String param,@PathVariable int type){
        return userService.checkData(param, type);
    }

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user){
        return userService.register(user);
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        TaotaoResult result = userService.login(username, password);
        //把token写入cookie
        if(result.getStatus()==200){
            CookieUtils.setCookie(request,response,TOKEN_KEY,result.getData().toString());
        }
        return result;
    }

    @RequestMapping(value = "user/token/{token}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback) {
        TaotaoResult result = userService.getUserByToken(token);
        //判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            return callback + "(" + JsonUtils.objectToJson(result)+");";
        }
        return JsonUtils.objectToJson(result);
    }
}
