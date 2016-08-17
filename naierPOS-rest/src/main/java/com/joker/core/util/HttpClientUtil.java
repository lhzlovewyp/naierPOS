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
}