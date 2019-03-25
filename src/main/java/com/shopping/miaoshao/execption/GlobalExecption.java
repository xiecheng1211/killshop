package com.shopping.miaoshao.execption;

import com.shopping.miaoshao.result.CodeMsg;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
public class GlobalExecption extends RuntimeException{

    private CodeMsg codeMsg;

    public GlobalExecption(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
