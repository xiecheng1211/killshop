package com.shopping.miaoshao.controller;

import com.shopping.miaoshao.access.AccessLimit;
import com.shopping.miaoshao.domain.MiaoshaOrder;
import com.shopping.miaoshao.domain.MiaoshaUser;
import com.shopping.miaoshao.domain.OrderInfo;
import com.shopping.miaoshao.key.GoodsKey;
import com.shopping.miaoshao.key.MiaoshaKey;
import com.shopping.miaoshao.key.OrderKey;
import com.shopping.miaoshao.rabbitmq.MiaoshaMessage;
import com.shopping.miaoshao.result.CodeMsg;
import com.shopping.miaoshao.result.Result;
import com.shopping.miaoshao.service.GoodsService;
import com.shopping.miaoshao.service.MiaoshaService;
import com.shopping.miaoshao.service.OrderService;
import com.shopping.miaoshao.util.VerifyCodeUtil;
import com.shopping.miaoshao.vo.GoodsVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
@Controller
@RequestMapping("/miaosha")
@CrossOrigin
public class MiaoshaController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService miaoshaOrderService;
    @Autowired
    private MiaoshaService miaoshaService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Map<String, Boolean> localOverMap =  new HashMap<String, Boolean>();

    @AccessLimit(seconds=5, maxCount=5, needLogin=true)
    @RequestMapping(method = RequestMethod.POST,value = "/do_miaosha")
    public String domiaosha(String goodsId, MiaoshaUser miaoshaUser, Model model){
        if (miaoshaUser == null){
            return "";
        }
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stockcount = goodsVo.getStockCount();
        int goodsstock = goodsVo.getGoodsstock();

        //判断是否已经有过秒杀记录
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.getMiaoshaOrderByUseridAndGoodsID(miaoshaUser.getId(),goodsId);
        if (miaoshaOrder!=null){
             model.addAttribute("errmsg", CodeMsg.REPEATMIAOSHA.getMsg());
            return "miaosha_fail";
        }
        Long now = System.currentTimeMillis();
        Long start = goodsVo.getStartTime().getTime();
        if (start > now){
            model.addAttribute("errmsg", CodeMsg.MIAOSHANOSTART.getMsg());
            return "miaosha_fail";
        }
        Long end = goodsVo.getEndTime().getTime();
        if (end < now){
            model.addAttribute("errmsg", CodeMsg.REPEATMIAOSHA.getMsg());
            return "miaosha_fail";
        }
        //秒殺庫存不足
        if (stockcount<1){
            model.addAttribute("errmsg", CodeMsg.GOODSSTOCKNULL.getMsg());
            return "miaosha_fail";
        }
        //秒杀成功
        OrderInfo orderInfo = miaoshaService.miaosha(miaoshaUser,goodsVo);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("user",miaoshaUser);
        model.addAttribute("goods",goodsVo);
        return "order_detail";
    }

    @RequestMapping(value="/{path}/do_miaosha", method=RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(Model model,MiaoshaUser user,
                                   @RequestParam("goodsId")String goodsId,
                                   @PathVariable("path") String path) {
        model.addAttribute("user", user);
        if(user == null) {
            return new Result(CodeMsg.SESSION_ERROR);
        }
        //验证path
        boolean check = miaoshaService.checkPath(user, goodsId, path);
        if(!check){
            return new Result(CodeMsg.REQUEST_ILLEGAL);
        }
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if(over) {
            return new Result(CodeMsg.GOODSSTOCKNULL);
        }
        //预减库存
        long stock = redisTemplate.opsForValue().decrement(GoodsKey.getMiaoshaGoodsStock+goodsId);//10
        if(stock < 0) {
            localOverMap.put(goodsId, true);
            return new Result(CodeMsg.MIAOSHATIMEOUT);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = miaoshaOrderService.getMiaoshaOrderByUseridAndGoodsID(user.getId(), goodsId);
        if(order != null) {
            return new Result(CodeMsg.REPEATMIAOSHA);
        }
        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setMiaoshaUser(user);
        mm.setGoodsid(goodsId);
        rabbitTemplate.convertAndSend("miaosha_queue",mm);
        return new Result(CodeMsg.SUCCESS,"0");//排队中
    }

    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.goodsVoList();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisTemplate.opsForValue().set(GoodsKey.getMiaoshaGoodsStock+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisTemplate.delete(OrderKey.getMiaoshaOrderByUidGid);
        redisTemplate.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsList);
        return new Result(CodeMsg.SUCCESS,true);
    }

    @AccessLimit(seconds=5, maxCount=5, needLogin=true)
    @RequestMapping(value="/verifyCode", method=RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaVerifyCod(HttpServletResponse response, MiaoshaUser user,@RequestParam("goodsId")long goodsId) {
        if(user == null) {
            return new Result(CodeMsg.SESSION_ERROR);
        }
        try {
            VerifyCodeUtil verifyCodeUtil = new VerifyCodeUtil();
            BufferedImage image = verifyCodeUtil.createVerifyCode(redisTemplate, user,goodsId,"VG");
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        }catch(Exception e) {
            e.printStackTrace();
            return new Result(CodeMsg.MIAOSHA_FAIL);
        }
    }

    @AccessLimit(seconds=5, maxCount=5, needLogin=true)
    @RequestMapping(value="/path", method=RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaPath(HttpServletRequest request, MiaoshaUser user,
                                         @RequestParam("goodsId")String goodsId,
                                         @RequestParam(value="verifyCode", defaultValue="0")int verifyCode
    ) {
        if(user == null) {
            return new Result(CodeMsg.SESSION_ERROR);
        }
        boolean check = VerifyCodeUtil.checkVerifyCode(redisTemplate, user, goodsId, verifyCode,"VG");
        if(!check) {
            return new Result(CodeMsg.ACCESS_LIMIT_REACHED);
        }
        String path  =miaoshaService.createMiaoshaPath(user, goodsId);
        return new Result(CodeMsg.SUCCESS,path);
    }

    /**
     * 采用rabbitmq进行异步下单
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     * */
    @RequestMapping(value="/result", method=RequestMethod.GET)
    @ResponseBody
    public Result<String> miaoshaResult(Model model,MiaoshaUser user,
                                      @RequestParam("goodsId")String goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return new Result(CodeMsg.SESSION_ERROR);
        }
        String result  =miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return new Result(CodeMsg.SUCCESS,result);
    }

}
