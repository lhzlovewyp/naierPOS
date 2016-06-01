package com.joker.common.controller;

import com.joker.common.model.Cart;
import com.joker.common.model.User;
import com.joker.common.service.CartSer;
import com.joker.common.service.UserSer;
import com.joker.core.annotation.NotNull;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by crell on 2015/12/18.
 */
@RestController
public class CartController extends AbstractController {

    @Autowired
    CartSer cartSer;

    @Autowired
    UserSer userSer;

    @RequestMapping(value = {"/cart"},method = RequestMethod.POST)
    @NotNull(value = "businessId",user = true)
    public ReturnBody add(@RequestBody ParamsBody paramsBody,HttpServletRequest request) throws Exception {
        ReturnBody rbody = new ReturnBody();
        Map params = paramsBody.getBody();
        String businessId = String.valueOf(params.get("businessId"));
        String token = paramsBody.getToken();

        User user = userSer.selectByToken(token);

        cartSer.add(user.getUserId(), businessId);

        rbody.setStatus(ResponseState.SUCCESS);
        return rbody;
    }

    @RequestMapping(value = {"/cart/list"},method = RequestMethod.POST)
    @ResponseBody
    @NotNull(value = "",user = true)
    public ReturnBody getCart(@RequestBody ParamsBody paramsBody,HttpServletRequest request){
        ReturnBody rbody = new ReturnBody();

        String token = paramsBody.getToken();
        User user = userSer.selectByToken(token);

        List<Cart> cartList = cartSer.getCart(user.getUserId());

        rbody.setStatus(ResponseState.SUCCESS);
        rbody.setData(cartList);

        return rbody;
    }
}
