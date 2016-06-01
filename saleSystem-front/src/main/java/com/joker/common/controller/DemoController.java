package com.joker.common.controller;


import com.joker.core.controller.AbstractController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DemoController extends AbstractController {

    @RequestMapping(value = {"/demo"},method = RequestMethod.GET)
    public ModelAndView demo(HttpServletRequest request){
        return new ModelAndView("demo");
    }

}
