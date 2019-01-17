package com.bootdo.system.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * Copyright (C) 2014-2016 天津紫藤科技有限公司. Co. Ltd. All Rights Reserved.
 * @description HttpClientUtil
 * @serve
 * @module
 * @author Andy
 * @date 2016年11月20日
 * @version v1
 * @code
 */
public class HttpClientUtil {
    private static PoolingHttpClientConnectionManager cm;
    private static String EMPTY_STR = "";
    private static String UTF_8 = "UTF-8";

    private static void init() {
        if (cm == null) {
            cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(50);// 整个连接池最大连接数
            cm.setDefaultMaxPerRoute(5);// 每路由最大连接数，默认值是2
        }
    }

    /**
     * 通过连接池获取HttpClient
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     *
     * @param url
     * @return
     */
    public static String httpGetRequest(String url) {
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params)
            throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return getResult(httpGet);
    }

    public static String httpPutRequest(String url){
        HttpPut httpPut = new HttpPut(url);
        return getResult(httpPut);
    }

    public static String httpPutRequest(String httpUrl,String jsonStr) throws Exception{
        String result = "";
        URL url = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (url != null) {
            try {
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setRequestProperty("content-type", "application/json");
                urlConn.setDoInput(true);
                urlConn.setDoOutput(true);
                urlConn.setConnectTimeout(5 * 1000);
                //设置请求方式为 PUT
                urlConn.setRequestMethod("PUT");

                urlConn.setRequestProperty("Content-Type", "application/json");
                urlConn.setRequestProperty("Accept", "application/json");

                urlConn.setRequestProperty("Charset", "UTF-8");


                DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
                //写入请求参数
                //这里要注意的是，在构造JSON字符串的时候，实践证明，最好不要使用单引号，而是用“\”进行转义，否则会报错
                // 关于这一点在上面给出的参考文章里面有说明
                //String jsonParam = "{\"appid\":6,\"appkey\":\"0cf0vGD/ClIrVmvVT/r5hEutH5M=\",\"openid\":200}";
                dos.writeBytes(jsonStr);
                dos.flush();
                dos.close();

                if (urlConn.getResponseCode() == 200) {
                    InputStreamReader isr = new InputStreamReader(urlConn.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    String inputLine = null;
                    while ((inputLine = br.readLine()) != null) {
                        result += inputLine;
                    }
                    isr.close();
                    urlConn.disconnect();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static String httpPostRequest(String url) {
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params)
            throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);

        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));

        return getResult(httpPost);
    }

    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }

        return pairs;
    }

    /**
     * 处理Http请求
     *
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request) {
        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = getHttpClient();
        try {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
            request.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(request);
            // response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity);
                response.close();
                // httpClient.close();
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return EMPTY_STR;
    }
//
//    public static void main(String[] args) {
//        String result = HttpClientUtil.httpPostRequest("https://www.baidu.com/link?url=ChjcIYsriiC1wxmaoIP_O1Wcz3qxwk8UAlwf8CsyE7UNDcU15ovQQWIolXZVPtIB&wd=&eqid=b4573a290002784d000000025ae2c8cf");
//        System.out.println(result);
//    }
}