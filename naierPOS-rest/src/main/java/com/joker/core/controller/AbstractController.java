package com.joker.core.controller;

import com.joker.core.constant.ExceptionCode;
import com.joker.core.constant.ResponseState;
import com.joker.core.dto.ReturnBody;
import com.joker.core.expection.ErrorException;
import com.joker.core.expection.FailedException;
import com.joker.core.expection.InvalidUserException;
import com.joker.core.expection.ParamsNullException;
import com.joker.core.util.LogUtil;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础controller
 * 统一异常处理
 *
 * @Author crell
 * @Date 2015/12/1
 */
public class AbstractController {

    /**
     * 统一异常处理
     * @param request
     * @param exception
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public ReturnBody exp(HttpServletRequest request, Exception exception){

        //Logger logger = LoggerFactory.getLogger(this.getClass());

        ReturnBody rbody = new ReturnBody();
        if(exception instanceof InvalidUserException){
            rbody.setStatus(ResponseState.INVALID_USER);
        }else if(exception instanceof FailedException){
            rbody.setStatus(ResponseState.FAILED);
            rbody.setMsg(exception.getMessage());
        }else if(exception instanceof ParamsNullException){
            LogUtil.error(request.getServletPath() + ":" + exception.getMessage(), exception);
            rbody.setStatus(ResponseState.FAILED);
            rbody.setMsg(ExceptionCode.PARAM_IS_NULL);
        }else if(exception instanceof ErrorException){
            LogUtil.error(request.getServletPath() + ":" + exception.getMessage(),exception);
            rbody.setStatus(ResponseState.ERROR);
            rbody.setMsg(ExceptionCode.SYSTEM_ERROR);
        }else if(exception instanceof DuplicateKeyException){
        	rbody.setStatus(ResponseState.ERROR);
            rbody.setMsg("唯一编码重复");
        }else{
            LogUtil.error(request.getServletPath() + ":" + exception.getMessage(),exception);
            rbody.setStatus(ResponseState.ERROR);
            rbody.setMsg(ExceptionCode.SYSTEM_ERROR);
        }

        return  rbody;
    }
    
}
