package com.shopping.miaoshao.result;

/**
 * @author dongcheng
 * create date 2019/3/21
 **/
public class CodeMsg {

    private int code;
    private String msg;

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg UN_LOGIN = new CodeMsg(500102,"用户未登录");
    public static CodeMsg ACCESS_LIMIT_REACHED= new CodeMsg(500103, "访问太频繁！");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    //登录模块 5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");

    //商品模块 5003XX

    //订单模块 5004XX

    //秒杀模块 5005XX
    public static CodeMsg GOODSSTOCKNULL = new CodeMsg(5005101, "商品已被抢完");
    public static CodeMsg REPEATMIAOSHA = new CodeMsg(5005102, "您已经参与过秒杀活动了");
    public static CodeMsg MIAOSHANOSTART = new CodeMsg(5005103, "秒杀活动还未开始");
    public static CodeMsg MIAOSHATIMEOUT = new CodeMsg(5005104, "秒杀活动已经结束");
    public static CodeMsg MIAOSHA_FAIL = new CodeMsg(5005105, "秒杀失败");

    private CodeMsg( ) {
    }

    private CodeMsg( int code,String msg ) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }
}
