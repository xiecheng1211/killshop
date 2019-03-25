package com.shopping.miaoshao.controller;

import com.shopping.miaoshao.result.CodeMsg;
import com.shopping.miaoshao.result.Result;
import com.shopping.miaoshao.service.MiaoshaUserService;
import com.shopping.miaoshao.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author dongcheng
 * create date 2019/3/21
 **/
@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private HttpServletResponse httpServletResponse;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @RequestMapping(method = RequestMethod.POST,value = "/do_login")
    public Result doLogin(@Valid LoginVo loginVo){
        logger.info(loginVo.toString());
        miaoshaUserService.login(httpServletResponse,loginVo);
        return new Result(CodeMsg.SUCCESS);
    }
}
