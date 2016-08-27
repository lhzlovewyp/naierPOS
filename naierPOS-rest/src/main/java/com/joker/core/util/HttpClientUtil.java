package com.joker.core.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtil
{
//    private static Logger log = Logger.getLogger(HttpClientUtil.class);

    public static String httpPost(String url, Map<String, String> params, String charset)
            throws Exception
    {
        long beginTime = new Date().getTime();
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            httpPost = new HttpPost(url);
            if ((params == null) || (params.isEmpty())) return null;
            List ps = new ArrayList();
            for (Iterator localIterator = params.entrySet().iterator(); localIterator.hasNext(); ) {
                Map.Entry entry = (Map.Entry)localIterator.next();
                ps.add(new BasicNameValuePair((String)entry.getKey(),(String)entry.getValue()));
            }
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new UrlEncodedFormEntity(ps, charset));
            httpPost.addHeader("Content-type",
                    "application/x-www-form-urlencoded");
            response = httpClient.execute(httpPost);

           result = EntityUtils.toString(response.getEntity(), charset);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new Exception("调用接口异常!");
        } finally {
            if (response != null)
                response.close();

            if (httpClient != null)
                httpClient.close();
        }

        //log.debug("调用接口：" + url + "正常。总花费：" + (new Date().getTime() - beginTime) + "毫秒、" + "总花费：" + ((new Date().getTime() - beginTime) / 1000L) + "秒。");
        return result;
    }

    public static String httpPost(String url, String json, String charset)
            throws Exception
    {
        long beginTime = new Date().getTime();
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            if (StringUtils.isNotBlank(json)) {
                httpPost.setEntity(new StringEntity(json, charset));
                httpPost.addHeader("Content-type", "application/json");
            }
            response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), charset);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new Exception("调用接口异常!");
        } finally {
            if (response != null)
                response.close();

            if (httpClient != null)
                httpClient.close();
        }

        //log.debug("调用接口：" + url + "正常。总花费：" + (new Date().getTime() - beginTime) + "毫秒、" + "总花费：" + ((new Date().getTime() - beginTime) / 1000L) + "秒。");
        return result;
    }

    public static String httpPost(String url, String json)
            throws Exception
    {
        return httpPost(url, json, "UTF-8");
    }

    public static String httpPost(String url, Map<String, String> params)
            throws Exception
    {
        return httpPost(url, params, "UTF-8");
    }

    public static JSONObject httpPost2Json(String url, String json)
            throws Exception
    {
        return JSONObject.parseObject(httpPost(url, json, "UTF-8"));
    }

    public static JSONObject httpPost2Json(String url, Map<String, String> params)
            throws Exception
    {
        return JSONObject.parseObject(httpPost(url, params, "UTF-8"));
    }

    public static String httpGet(String url, Map<String, Object> params, String charset)
            throws Exception
    {
        long beginTime = new Date().getTime();
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            StringBuilder uri = new StringBuilder(url);
            if ((params != null) && (!(params.isEmpty()))) {
                if (url.indexOf(63) > 0)
                    uri.append("&");
                else
                    uri.append("?");

                for (Iterator localIterator = params.entrySet().iterator(); localIterator.hasNext(); ) { Map.Entry entry = (Map.Entry)localIterator.next();
                    if (entry.getValue() == null) break;
                    String value = entry.getValue().toString();
                    uri.append((String)entry.getKey()).append("=");
                    if (CommonUtil.isContainsChinese(value))
                        value = URLEncoder.encode(value, "UTF-8");

                    uri.append(value).append("&");
                }

                uri.deleteCharAt(uri.length() - 1);
            }
            HttpGet httpGet = new HttpGet(uri.toString());
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);
            result = EntityUtils.toString(response.getEntity(), charset);
        } catch (UnsupportedEncodingException e) {
            throw new Exception("调用接口异常!");
        } finally {
            if (response != null)
                response.close();

            if (httpClient != null)
                httpClient.close();
        }

        //log.debug("调用接口：" + url + "正常。总花费：" + (new Date().getTime() - beginTime) + "毫秒、" + "总花费：" + ((new Date().getTime() - beginTime) / 1000L) + "秒。");
        return result;
    }

    public static String httpGet(String url, Map<String, Object> params)
            throws Exception
    {
        return httpGet(url, params, "UTF-8");
    }

    public static JSONObject httpGet2Json(String url, Map<String, Object> params)
            throws Exception
    {
        return JSONObject.parseObject(httpGet(url, params, "UTF-8"));
    }
    
    public static String httpGetNew(String url, Map<String, Object> params)
            throws Exception
    {
        return sendGet(url, params);
    }
    
    /**
	 * http get
	 * @param url 请求url
	 * @param parameters 参数
	 * @return
	 */
	public static String sendGet(String url, Map<String, Object> parameters) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		StringBuffer sb = new StringBuffer();// 存储参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if (parameters.size() == 1) {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(
							java.net.URLEncoder.encode(parameters.get(name).toString(),
									"UTF-8"));
				}
				params = sb.toString();
			} else {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(
							java.net.URLEncoder.encode(parameters.get(name).toString(),
									"UTF-8")).append("&");
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			String full_url = url + "?" + params;
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(full_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
					.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 建立实际的连接
			httpConn.connect();
			// 响应头部获取
			Map<String, List<String>> headers = httpConn.getHeaderFields();
			// 遍历所有的响应头字段
			//for (String key : headers.keySet()) {
				//System.out.println(key + "\t：\t" + headers.get(key));
			//}
			// 定义BufferedReader输入流来读取URL的响应,并设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn
					.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
    
    
}