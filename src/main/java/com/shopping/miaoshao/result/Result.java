package com.shopping.miaoshao.result;

/**
 * @author dongcheng
 * create date 2019/3/21
 **/
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(CodeMsg codeMsg){
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(CodeMsg codeMsg, T data){
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
        this.data = data;
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
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }


}
