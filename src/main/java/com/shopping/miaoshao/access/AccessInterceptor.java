package com.shopping.miaoshao.access;

import com.alibaba.fastjson.JSON;
import com.shopping.miaoshao.domain.MiaoshaUser;
import com.shopping.miaoshao.key.AccessKey;
import com.shopping.miaoshao.result.CodeMsg;
import com.shopping.miaoshao.result.Result;
import com.shopping.miaoshao.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author dongcheng
 * create date 2019/3/25
 **/
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MiaoshaUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if(handler instanceof HandlerMethod) {
            MiaoshaUser user = getUser(request, response);
            UserContent.setUser(user);
            HandlerMethod hm = (HandlerMethod)handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if(needLogin) {
                if(user == null) {
                    render(response, CodeMsg.SESSION_ERROR);
                    return false;
                }
                key += "_" + user.getId();
            }else {
                //do nothing
            }
            AccessKey ak = AccessKey.withExpire(seconds);
            Integer count = (Integer) redisTemplate.opsForValue().get(ak.getPrefix()+key);
            if(count  == null) {
                redisTemplate.opsForValue().set(ak.getPrefix()+key,maxCount,seconds);
            }else if(count < maxCount) {
                redisTemplate.opsForValue().increment(ak.getPrefix()+key);
            }else {
                render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response, CodeMsg cm)throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        String str  = JSON.toJSONString(new Result(cm));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }


    /**
     * 从请求中获取用户token
     * 然后通过token获取到用户信息
     * */
    private MiaoshaUser getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(MiaoshaUserService.COOKI_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MiaoshaUserService.COOKI_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }


}
