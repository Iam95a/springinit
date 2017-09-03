package com.chen.filter;

import com.chen.utils.UuidUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            Cookie[] cookies = httpServletRequest.getCookies();
            String uniqueid = null;
            if(cookies!=null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equalsIgnoreCase("uniqueid")) {
                        uniqueid = cookie.getValue();
                    }
                }
            }
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            if (StringUtils.isEmpty(uniqueid) || StringUtils.isBlank(uniqueid)) {
                uniqueid = UuidUtils.generalUUID();

                Cookie mycookies = new Cookie("uniqueid", uniqueid);
                mycookies.setMaxAge(-1);
                mycookies.setPath("/");
                httpServletResponse.addCookie(mycookies);
            }
            chain.doFilter(httpServletRequest,
                    httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
