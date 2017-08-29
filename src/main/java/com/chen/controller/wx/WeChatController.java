package com.chen.controller.wx;

import com.chen.common.Result;
import com.chen.utils.WeChatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("weChat")
public class WeChatController {


    /**
     * 获取微信登录二维码
     * @return 接口返回二维码链接
     */
    @RequestMapping("/getQrCodeUrl")
    @ResponseBody
    public Result<String> getQrCodeUrl(@CookieValue("uniqueid")String cookie) {
        return new Result<>(true, 0, "", WeChatUtils.getQrCodeUrl());
    }

    /**
     * 用户不断扫描是否登录
     * @return 如果登录返回后续 TODO
     */
    @RequestMapping("/scanIfLogin")
    @ResponseBody
    public Result<Object> scanIfLogin(){
        return null;
    }
}
