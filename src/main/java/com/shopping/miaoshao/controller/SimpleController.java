package com.shopping.miaoshao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
@Controller
public class SimpleController {

    @RequestMapping("/")
    public String login(){
        return "login";
    }

}
