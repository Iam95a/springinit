package com.chen.controller;

import com.chen.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/login")
    @ResponseBody
    public Result<Object> login(HttpServletRequest request,
                                @RequestParam(required = false) String username,@RequestParam (required = false)
                                            String pwd) throws Exception{
        if("chen".equals(username)&&"123".equals(pwd)){
            return new Result<>(true,0,"登录成功",null);
        }

        return new Result<>(false,0,"登录失败",null);

    }
}
