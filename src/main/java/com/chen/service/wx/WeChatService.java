package com.chen.service.wx;

import com.chen.model.WXUserModel;
import com.chen.utils.http.HttpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.client.CookieStore;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class WeChatService {
    /**
     * 登录后获取跳转url
     *
     * @param qrcodeUrl 二维码地址
     * @return
     */
    public String scanThenGetRedirectUrl(String qrcodeUrl) {
        long timeStramp = new Date().getTime();
        int timeStrampReverse = (int) ~timeStramp;
        String uuid = qrcodeUrl.substring(35, qrcodeUrl.length());
        String loginUrl = "https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid="
                + uuid + "&tip=0&r=" + timeStrampReverse + "&_=" + timeStramp;
        String loginResult = HttpUtil.getByUTF8(loginUrl, null);
        String loginCode = loginResult.substring(12, 15);
        if (loginCode.equals("200")) {
            System.out.println(loginResult);
            return loginResult.substring(38, loginResult.length() - 2);
        } else {
            System.out.println("扫描失败");
            throw new RuntimeException("扫描失败，尚未登录");
        }
    }

    public Map<String, Object> getLoginParamByRedirectUrl(String redirectUrl) {
        Map<String, Object> resultAndCookieStore = HttpUtil.getByUTF8AndStoreCookie(redirectUrl + "&fun=new&version=v2");
        return resultAndCookieStore;
    }
    public  Map<String, String> parseLoginParamStr2Map(String loginParamStr)throws  Exception {
        try {
            Document document = DocumentHelper.parseText(loginParamStr);
            Element xml = document.getRootElement();
            Iterator<Element> iter = xml.elementIterator();
            Map<String, String> loginParam = Maps.newHashMap();
            while (iter.hasNext()) {
                try {
                    Element ele = iter.next();
                    loginParam.put(ele.getName(), ele.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return loginParam;
        } catch (DocumentException e) {
            throw  e;
        }

    }
    public List<WXUserModel> listWXUserModel(Map<String, String> loginParam,CookieStore cookieStore) {
        String wxContactStr = listWxContact(loginParam, cookieStore);
        return listWXUserModelByWXContactStr(wxContactStr);
    }
    public  String listWxContact(Map<String, String> loginParam, CookieStore cookieStore) {
        String url = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?lang=zh_CN&pass_ticket=" +
                loginParam.get("pass_ticket") +
                "&r=" + new Date().getTime() +
                "&seq=0&skey=" + loginParam.get("skey");
        System.out.println(url);
        return HttpUtil.getByUTF8(url, cookieStore);
    }

    public List<WXUserModel> listWXUserModelByWXContactStr(String wxContactStr) {
        Gson gson = new Gson();
        Map<String, Object> contactMap = gson.fromJson(wxContactStr, Map.class);
        List<Map<String, Object>> contactList = (List<Map<String, Object>>) contactMap.get("MemberList");
        List<WXUserModel> wxUserModels = Lists.newArrayList();
        for (Map<String, Object> userMap : contactList) {
            try {
                WXUserModel wxUserModel = getWCUserModelByContactMap(userMap);
                wxUserModels.add(wxUserModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return wxUserModels;
    }

    public  WXUserModel getWCUserModelByContactMap(Map<String, Object> contactMap) throws Exception{

        Set<String> keys = contactMap.keySet();
        WXUserModel wxUserModel = new WXUserModel();
//        BeanUtils.copyProperties(wxUserModel,contactMap);
        for (String key : keys) {
            Class clazz = wxUserModel.getClass();
            try {
                Method method = clazz.getMethod("set" + key, String.class);
                String val = null;
                try {
                    Double douVal = (Double) contactMap.get(key);
                    Long longVal = douVal.longValue();
                    val = longVal.toString();
                } catch (Exception e) {
                    val = (String) contactMap.get(key);
                }
                method.invoke(wxUserModel, val);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return wxUserModel;
    }

    public static WXUserModel getByUserName(List<WXUserModel> wxUserModelList, String nickName) {
        try {
            for (WXUserModel wxUserModel : wxUserModelList) {
                if (wxUserModel.getNickName().equals(nickName) || wxUserModel.getRemarkName().equals(nickName)) {
                    return wxUserModel;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public  String sendWXMsg(WXUserModel selfUserModel, WXUserModel wxUserModel, String content, CookieStore cookieStore, Map<String, String> loginParam) {
        String url = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxsendmsg?lang=zh_CN&pass_ticket=" +
                loginParam.get("pass_ticket");
        Map<String, Object> requestParam = Maps.newHashMap();
        Map<String, Object> param = null;
         Map<String, String> baseRequest=Maps.newHashMap();
        baseRequest=setBaseRequest(loginParam,baseRequest);
        requestParam.put("BaseRequest", baseRequest);
        param = Maps.newHashMap();
        String clientMsgId = ((Long) ((new Date().getTime()) << 4)).toString() + (((Double) (Math.random() * 10000)).longValue());
        param.put("ClientMsgId", clientMsgId);
        param.put("Content", content);
        param.put("FromUserName", selfUserModel.getUserName());
        param.put("LocalID", clientMsgId);
        param.put("ToUserName", wxUserModel.getUserName());
        param.put("Type", 1);

        requestParam.put("Msg", param);
        requestParam.put("Scene", 0);

        String result = HttpUtil.postJsonWithCookies(url, new Gson().toJson(requestParam),
                null, cookieStore);
        return result;
    }
    public  Map<String, String> setBaseRequest(Map<String, String> loginParam,Map<String,String> baseRequest) {
        baseRequest = Maps.newHashMap();
        String DeviceID = "e" + ((Double) (Math.random() * (1000000000000000L))).longValue();

        baseRequest.put("DeviceID", DeviceID);
        baseRequest.put("Sid", loginParam.get("wxsid"));
        baseRequest.put("Skey", loginParam.get("skey"));
        baseRequest.put("Uin", loginParam.get("wxuin"));
        return baseRequest;
    }

}
