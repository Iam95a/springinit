package com.chen.controller;

import com.chen.entity.UserAccount;
import com.chen.service.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/chen")
public class ChenController {
    private  static Logger Log= LoggerFactory.getLogger(ChenController.class);

    @RequestMapping("/getChen")
    @ResponseBody
    public String getChen() {
        return "chen";
    }

    @Resource
    private UserAccountService userAccountService;

    @RequestMapping("/getUser")
    @ResponseBody
    public UserAccount getUser() {
        return userAccountService.getUser();
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public Map<String, String> addUser() {
        Map<String, String> map = new HashMap<>();
        try {
            boolean insertStatus = userAccountService.addUser();
            map.put("jieguo", "成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("jieguo", "发生异常");

        }

        return map;
    }

}
