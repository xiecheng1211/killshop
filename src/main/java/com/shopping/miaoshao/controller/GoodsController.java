package com.shopping.miaoshao.controller;

import com.shopping.miaoshao.domain.MiaoshaUser;
import com.shopping.miaoshao.key.MiaoshaGoodsKey;
import com.shopping.miaoshao.result.CodeMsg;
import com.shopping.miaoshao.result.Result;
import com.shopping.miaoshao.service.GoodsService;
import com.shopping.miaoshao.vo.GoodsDetailVo;
import com.shopping.miaoshao.vo.GoodsVo;
import io.netty.util.Timeout;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
@Controller
@CrossOrigin
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;


    @RequestMapping(value = "/to_list",method = RequestMethod.GET)
    public String list(Model model, MiaoshaUser miaoshaUser) {
        if (miaoshaUser == null){
            return "";
        }
        model.addAttribute("user",miaoshaUser);
        List<GoodsVo> goodsVos = goodsService.goodsVoList();
        model.addAttribute("goodsList",goodsVos);
        return "goods_list";
    }

    @RequestMapping(value = "/to_list1",method = RequestMethod.GET)
    @ResponseBody
    public String list1(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser miaoshaUser) {
        if (miaoshaUser == null){
            return "";
        }
        model.addAttribute("user",miaoshaUser);
        String ht = (String) redisTemplate.opsForValue().get(MiaoshaGoodsKey.goodsList.getPrefix());
        if(ht != null){
            return ht;
        }
        List<GoodsVo> goodsVos = goodsService.goodsVoList();
        model.addAttribute("goodsList",goodsVos);

        IWebContext ctx =new WebContext(request,response,
                request.getServletContext(),request.getLocale(),model.asMap());
        //手动渲染页面
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
        //判断页面是否生成
        if(!StringUtils.isEmpty(html)){
            //将页面进行缓存  每分钟出新的页面
            redisTemplate.opsForValue().set(MiaoshaGoodsKey.goodsList.getPrefix(),html, TimeoutUtils.toSeconds(1, TimeUnit.MINUTES));
        }
        return html;
    }

    @RequestMapping(value = "/to_detail/{id}",method = RequestMethod.GET)
    public String getGoodsVoByGoodsId(@PathVariable String id, Model model, MiaoshaUser miaoshaUser){
        if (miaoshaUser == null){
            return "";
        }
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(id);
        model.addAttribute("user",miaoshaUser);
        model.addAttribute("goods",goodsVo);
        int miaoshaStatus = -1;
        Long start = goodsVo.getStartTime().getTime();
        Long end = goodsVo.getEndTime().getTime();
        Long now = System.currentTimeMillis();
        Long remainSeconds = -1l;
        if(start > now){
            miaoshaStatus = 0;
            remainSeconds = (start - now)/1000;
        }else if (start <= now && now <= end){
            miaoshaStatus = 1;
            remainSeconds = 0l;
        }else {
            miaoshaStatus = 2;
        }
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        return "goods_detail";
    }

    @RequestMapping(value = "/to_detail1/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Result<GoodsDetailVo> getGoodsVoByGoodsId2(@PathVariable String id, MiaoshaUser miaoshaUser){
        if (miaoshaUser == null){
            return new Result<GoodsDetailVo>(CodeMsg.UN_LOGIN);
        }
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(id);
        int miaoshaStatus = -1;
        Long start = goodsVo.getStartTime().getTime();
        Long end = goodsVo.getEndTime().getTime();
        Long now = System.currentTimeMillis();
        Long remainSeconds = -1l;
        if(start > now){
            miaoshaStatus = 0;
            remainSeconds = (start - now)/1000;
        }else if (start <= now && now <= end){
            miaoshaStatus = 1;
            remainSeconds = 0l;
        }else {
            miaoshaStatus = 2;
        }
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoods(goodsVo);
        goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVo.setRemainSecondes(remainSeconds);
        goodsDetailVo.setUser(miaoshaUser);
        return new Result<>(CodeMsg.SUCCESS,goodsDetailVo);
    }

    @RequestMapping(value = "/to_detail2/{id}",method = RequestMethod.GET)
    public String getGoodsVoByGoodsId1(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable String id, Model model, MiaoshaUser miaoshaUser){
        if (miaoshaUser == null){
            return "";
        }
        //首先从缓存中获取页面信息
        String html = (String) redisTemplate.opsForValue().get(MiaoshaGoodsKey.goodsList.getPrefix()+id);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        //未缓存当前页面 从数据库中获取页面信息
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(id);
        model.addAttribute("user",miaoshaUser);
        model.addAttribute("goods",goodsVo);

        int miaoshaStatus = -1;

        Long start = goodsVo.getStartTime().getTime();
        Long end = goodsVo.getEndTime().getTime();
        Long now = System.currentTimeMillis();
        Long remainSeconds = -1l;
        if(start > now){
            miaoshaStatus = 0;
            remainSeconds = (start - now)/1000;
        }else if (start <= now && now <= end){
            miaoshaStatus = 1;
            remainSeconds = 0l;
        }else {
            miaoshaStatus = 2;
        }
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("miaoshaStatus",miaoshaStatus);

        IContext iContext = new WebContext(httpServletRequest,httpServletResponse,httpServletRequest.getServletContext(),
                httpServletRequest.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",iContext);
        return "html";
    }

}
