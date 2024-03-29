package com.shopping.miaoshao.config;

import com.shopping.miaoshao.access.UserContent;
import com.shopping.miaoshao.domain.MiaoshaUser;
import com.shopping.miaoshao.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private MiaoshaUserService miaoshaUserService;

    /*@Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz== MiaoshaUser.class;
    }

    @Override
        public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

            String paramToken = request.getParameter(MiaoshaUserService.COOKI_NAME_TOKEN);
            String cookieToken = getCookieValue(request, MiaoshaUserService.COOKI_NAME_TOKEN);
            if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
                return null;
            }
            String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
            return miaoshaUserService.getByToken(response, token);
        }

        private String getCookieValue(HttpServletRequest request, String cookiName) {
            Cookie[]  cookies = request.getCookies();
            if(cookies!=null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(cookiName)) {
                        return cookie.getValue();
                    }
                }
            }
            return null;
    }*/

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz==MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return UserContent.getUser();
    }

}
