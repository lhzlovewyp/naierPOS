package com.joker.common.controller;

import com.joker.core.annotation.NotNull;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by crell on 2016/2/18.
 */
@RestController
public class OrderController extends AbstractController {


    @RequestMapping(value = {"/order"},method = RequestMethod.POST)
    @NotNull(value = "businessId",user = true)
    public ReturnBody add(@RequestBody ParamsBody paramsBody,HttpServletRequest request) throws Exception {
        ReturnBody rbody = new ReturnBody();
        Map<String, Object> body = paramsBody.getBody();

        rbody.setStatus(ResponseState.SUCCESS);
        return rbody;
    }
}
