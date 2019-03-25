package com.shopping.miaoshao.handler;

import com.shopping.miaoshao.execption.GlobalExecption;
import com.shopping.miaoshao.result.CodeMsg;
import com.shopping.miaoshao.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
@ControllerAdvice
@ResponseBody
public class GlobalExecptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExecptionHandler.class);

    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        logger.error(e.getMessage());
        if (e instanceof GlobalExecption){
            GlobalExecption globalExecption = (GlobalExecption)e;
            return new Result(globalExecption.getCodeMsg());
        }else if (e instanceof BindException){
            BindException execption = (BindException)e;
            List<ObjectError> errors = execption.getAllErrors();
            ObjectError objectError = errors.get(0);
            String msg = objectError.getDefaultMessage();
            return new Result(CodeMsg.BIND_ERROR.getCode(),msg);
        }
        return new Result(CodeMsg.SERVER_ERROR);
    }

}
