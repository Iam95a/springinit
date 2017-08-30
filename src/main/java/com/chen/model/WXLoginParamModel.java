package com.chen.model;


import org.apache.http.client.CookieStore;

public class WXLoginParamModel {
    private String ret;
    private String pass_ticket;
    private String wxsid;
    private String skey;
    private String message;
    private String wxuin;
    private String isgrayscale;

    private CookieStore cookieStore;

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getPass_ticket() {
        return pass_ticket;
    }

    public void setPass_ticket(String pass_ticket) {
        this.pass_ticket = pass_ticket;
    }

    public String getWxsid() {
        return wxsid;
    }

    public void setWxsid(String wxsid) {
        this.wxsid = wxsid;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWxuin() {
        return wxuin;
    }

    public void setWxuin(String wxuin) {
        this.wxuin = wxuin;
    }

    public String getIsgrayscale() {
        return isgrayscale;
    }

    public void setIsgrayscale(String isgrayscale) {
        this.isgrayscale = isgrayscale;
    }

    @Override
    public String toString() {
        return "WXLoginParamModel{" +
                "ret='" + ret + '\'' +
                ", pass_ticket='" + pass_ticket + '\'' +
                ", wxsid='" + wxsid + '\'' +
                ", skey='" + skey + '\'' +
                ", message='" + message + '\'' +
                ", wxuin='" + wxuin + '\'' +
                ", isgrayscale='" + isgrayscale + '\'' +
                '}';
    }
}
