package com.joker.common.third.pay.aliweixin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.joker.common.third.member.BaseService;
import com.joker.core.util.Configer;
import com.joker.core.util.EncryptUtil;

/**
 * 
 * 支付服务.
 * 
 * @author lvhaizhen
 *
 */
public class AliPayService extends BaseService {

	
	/**
	 * 支付宝微信发起支付请求.
	 * 
	 * @param payParamsVo
	 * @return
	 */
	public static PayReturnVo pay(PayParamsVo payParamsVo){
		
		String url=Configer.get("alipayUrl");
		
		String privatekey = "www.yuwin.com.cn";
		String sign;
		try {
			sign = EncryptUtil.md5Encode(payParamsVo.getPartner() + privatekey);
			payParamsVo.setSign(sign);
			JSONObject obj=(JSONObject) JSONObject.toJSON(payParamsVo);
			return processRequest(url, obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static PayReturnVo processRequest(String url, JSONObject request) {
		PayReturnVo payReturnVo = null;
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		try {

			BasicHttpContext localContext = new BasicHttpContext();

			HttpPost httpPost = new HttpPost(url);

			StringEntity entity = new StringEntity(JSON.toJSONString(request),
					"UTF-8");// 解决中文乱码问题

			entity.setContentEncoding("UTF-8");

			entity.setContentType("application/json");

			httpPost.setEntity(entity);

			HttpResponse response = httpclient.execute(httpPost, localContext);

			BufferedReader br = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(),
							Charset.forName("UTF-8")));
			StringBuffer str = new StringBuffer("");
			String tmp = br.readLine();
			while (tmp != null) {
				str.append(tmp);
				tmp = br.readLine();
			}
			System.out.println("返回结果:" + str.toString());
			payReturnVo = JSON.parseObject(str.toString(), PayReturnVo.class);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return payReturnVo;
	}
	
	
	
}
