package com.chen.controller.wx;

import com.chen.common.Result;
import com.chen.model.WXLoginParamModel;
import com.chen.model.WXUserModel;
import com.chen.service.redis.RedisService;
import com.chen.service.wx.WeChatService;
import com.chen.utils.WeChatUtils;
import org.apache.http.client.CookieStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("weChat")
public class WeChatController {

    @Resource
    private WeChatService weChatService;

    @Resource
    private RedisService redisService;


    /**
     * 获取微信登录二维码
     *
     * @return 接口返回二维码链接
     */
    @RequestMapping("/getQrCodeUrl")
    @ResponseBody
    public Result<String> getQrCodeUrl(@CookieValue("uniqueid") String cookie) {
        String qrCodeUrl = WeChatUtils.getQrCodeUrl();
        redisService.addKeyValueExpiredInThiryMinutes(cookie, qrCodeUrl);
        return new Result<>(true, 0, "", qrCodeUrl);
    }

    /**
     * 用户不断扫描是否登录
     *
     * @return 如果登录返回后续 TODO
     */
    @RequestMapping("/scanIfLogin")
    @ResponseBody
    public Result<Object> scanIfLogin(@CookieValue("uniqueid") String cookie) {
        try {
            String qrcodeUrl = (String) redisService.getByKey(cookie);
            //用户已经登录后可获取redirect_url  不然报错
            String redirectUrl = weChatService.scanThenGetRedirectUrl(qrcodeUrl);
            WXLoginParamModel model =weChatService.getLoginParamByRedirectUrl(redirectUrl);
            redisService.addKeyValueExpiredInThiryMinutes(cookie,model);
            String uin=weChatService.getInitInfo(model, model.getCookieStore());
            List<WXUserModel> wxUserModels = weChatService.listWXUserModel(model, model.getCookieStore(),uin);
            WXUserModel wxUserModel = weChatService.getByUserName(wxUserModels, "老婆");
            WXUserModel selfWXUserModel = weChatService.getByUserName(wxUserModels, "gold great");
            weChatService.sendWXMsg(selfWXUserModel, wxUserModel, "我爱你", model.getCookieStore(), model);
            return new Result<>(true, 0, "登录成功", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result<>(false, 0, "出现异常", null);

    }
}
