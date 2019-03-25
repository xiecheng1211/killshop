package com.shopping.miaoshao.service;

import com.shopping.miaoshao.dao.MiaoshaUserDao;
import com.shopping.miaoshao.domain.MiaoshaUser;
import com.shopping.miaoshao.execption.GlobalExecption;
import com.shopping.miaoshao.key.MiaoshaUserKey;
import com.shopping.miaoshao.result.CodeMsg;
import com.shopping.miaoshao.util.MD5Util;
import com.shopping.miaoshao.util.UUIDUtil;
import com.shopping.miaoshao.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
@Service
@Transactional
public class MiaoshaUserService {
    @Autowired
    private MiaoshaUserDao miaoshaUserDao;
    @Autowired
    private RedisTemplate redisTemplate;

    public static final String COOKI_NAME_TOKEN = "token";

    /**
     * 根据秒杀用户id获取秒杀用户对象
     * @param id 秒杀用户id
     * */
    public MiaoshaUser getById(String id){
        if (StringUtils.isEmpty(id)){
            return null;
        }
        return miaoshaUserDao.getById(id);
    }

    public boolean login(HttpServletResponse httpServletResponse, LoginVo loginVo){
        if (loginVo == null){
            throw new GlobalExecption(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        MiaoshaUser user = getById(mobile);
        if (user == null){
            throw new GlobalExecption(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String saltdb = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(password,saltdb);
        if (!calcPass.equals(dbPass)){
            throw new GlobalExecption(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(httpServletResponse,token,user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user){
        redisTemplate.opsForValue().set(MiaoshaUserKey.token.getPrefix()+token,user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setPath("/");
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        response.addCookie(cookie);
    }

    public MiaoshaUser getByToken(HttpServletResponse response,String token){
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = (MiaoshaUser) redisTemplate.opsForValue().get(MiaoshaUserKey.token.getPrefix()+token);
        //延长有效期
        if(user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

}
