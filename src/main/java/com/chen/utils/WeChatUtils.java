package com.chen.utils;

import com.chen.utils.http.HttpUtil;

import java.util.Date;

public class WeChatUtils {
    public static String getUuid() {
        String qrcodeUrl = "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=" + new Date().getTime();
        String qrcodeResult = HttpUtil.getByUTF8(qrcodeUrl, null);
        qrcodeResult = qrcodeResult.split(";")[1];
        String uuid = qrcodeResult.substring(24, qrcodeResult.length() - 1);
        return uuid;
    }

    /**
     * 获取登录二维码地址
     * @return 二维码地址
     */
    public static  String getQrCodeUrl(){

        return  "https://login.weixin.qq.com/qrcode/"+getUuid();
    }
}
