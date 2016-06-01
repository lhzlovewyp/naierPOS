package com.joker.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.joker.weixin.constant.Context;
import com.joker.weixin.model.Button;
import com.joker.weixin.model.Menu;
import com.joker.weixin.model.SuperButton;
import com.joker.weixin.model.ViewButton;
import com.joker.weixin.util.SignUtil;
import com.joker.weixin.util.WeixinUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class WeixinController{

	@Autowired
	@Qualifier("weixinUtil")
	private WeixinUtil weixinUtil;

	/**
	 * 确认请求来自微信服务器
	 */
	@RequestMapping(value = {"/wechat"},method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(Context.TOKEN, signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	/**
	 * 处理微信服务器发来的消息
	 */
	@RequestMapping(value = {"/wechat"},method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		WechatService wechatService = new WechatService();
//		// 调用核心业务类接收消息、处理消息
//		String respMessage = wechatService.coreService(request);
//		// 响应消息
//		PrintWriter out = response.getWriter();
//		out.print(respMessage);
//		out.close();
	}


	/**
	 * 获取accessToken
	 */
	@RequestMapping(value = {"/wechat/accessToken"},method = RequestMethod.GET)
	public void accessToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		out.print(weixinUtil.getAccessToken());
		out.close();
		out = null;
	}

	/**
	 * 获取微信服务器IP
	 */
	@RequestMapping(value = {"/wechat/getip"},method = RequestMethod.GET)
	public void getip(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		out.print(weixinUtil.getIp());
		out.close();
		out = null;
	}

	/**
	 * 创建菜单
	 */
	@RequestMapping(value = {"/wechat/createMenu"},method = RequestMethod.GET)
	public void createMenu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();

		Menu menu = new Menu();
		Button[] firstButton = new Button[3];

		SuperButton superButton = new SuperButton();
		superButton.setName("项目");
		Button[] secondButton = new Button[1];
		ViewButton button = new ViewButton();
		button.setName("首页");
		button.setType("view");
		button.setUrl("http://crell.nat123.net");
		secondButton[0] = button;
		superButton.setSub_button(secondButton);
		firstButton[0] = superButton;

		SuperButton superButton1 = new SuperButton();
		superButton1.setName("个人中心");
		Button[] secondButton1 = new Button[2];
		ViewButton button11 = new ViewButton();
		button11.setName("注册");
		button11.setType("view");
		button11.setUrl("http://crell.nat123.net/register");
		secondButton1[0] = button11;
		ViewButton button22 = new ViewButton();
		button22.setName("我的");
		button22.setType("view");
		button22.setUrl("http://crell.nat123.net/my");
		secondButton1[1] = button22;
		superButton1.setSub_button(secondButton1);
		firstButton[1] = superButton1;

		SuperButton superButton2 = new SuperButton();
		superButton2.setName("关于");
		Button[] secondButton2 = new Button[1];
		ViewButton button2 = new ViewButton();
		button2.setName("联系我们");
		button2.setType("view");
		button2.setUrl("http://crell.nat123.net");
		secondButton2[0] = button2;
		superButton2.setSub_button(secondButton2);
		firstButton[2] = superButton2;

		menu.setButton(firstButton);

		JSONObject jsonObject = weixinUtil.createMenu(JSON.toJSONString(menu));
		out.print(jsonObject.get("errcode") + ":" + jsonObject.get("errmsg"));
		out.close();
		out = null;
	}

	/**
	 * 获取自动回复规则
	 */
	@RequestMapping(value = {"/wechat/getAutoreply"},method = RequestMethod.GET)
	public void getAutoreply(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		out.print(weixinUtil.getAutoreply());
		out.close();
		out = null;
	}
}
