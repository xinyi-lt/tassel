package com.zuoquan.lt.basic.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 可配置超时时间的httpclient封装。
 * 默认调用 ： httpGet /httpPost  默认超时时间是30s
 * 如果要实现可配置的超时时间的实现。  使用  httpGetTimeout /httpPostTimeout
 */

@SuppressWarnings("deprecation")
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 定义超时配置
     */
    public enum Timeouts {
        /**
         * 设置60s超时
         */
        SECONDS_60(60000, 60000, 60000),
        /**
         * 设置30s超时
         */
        SECONDS_30(30000, 30000, 30000),
        /**
         * 设置10s超时
         */
        SECONDS_10(10000, 10000, 10000),
        /**
         * 设置5s超时
         */
        SECONDS_5(5000, 5000, 5000),
        /**
         * 设置2s超时
         */
        SECONDS_2(2000, 2000, 2000),
        /**
         * 设置0.5秒
         */
        MILLS_500(500, 500, 500);

        private RequestConfig requestConfig;

        Timeouts(int socketTimeout, int connectTimeout, int requestTimeout) {
            this.requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(requestTimeout).build();

        }
    }

    private static final CloseableHttpClient client;
    public static final String UTF8 = "UTF-8";
    public static final String GBK = "GBK";
    public static final String GB2312 = "GB2312";

    static {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(10);
        client = HttpClients.custom().setConnectionManager(connManager).build();

    }

    /**
     * 可修改超时时间http get请求
     *
     * @param url 请求url
     * @param headers 头
     * @param params 请求参数
     * @param timeouts 设置超时时间类型
     * @return
     */
    public static String httpGetTimeout(String url, Map<String, String> headers, Map<String, String> params, Timeouts timeouts) {
        // Create an instance of HttpClient.

        // Create a method instance.
        if (params != null) {
            StringBuilder builder = new StringBuilder(url).append('?');
            for (Map.Entry<String, String> e : params.entrySet()) {
                builder.append(e.getKey()).append('=').append(e.getValue()).append('&');
            }
            url = builder.toString();
        }

        HttpGet get = new HttpGet(url);
        get.setConfig(timeouts.requestConfig);

        String response = null;

        get.addHeader("Content-Type", "text/html;charset=UTF-8");
        if (headers != null) {
            Set<String> headersSet = headers.keySet();
            for (Iterator<String> ite = headersSet.iterator(); ite.hasNext();) {
                String headerKey = ite.next();
                get.addHeader(headerKey, headers.get(headerKey));
            }
        }

        try {
            HttpEntity entity = client.execute(get).getEntity();
            response = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            logger.error("error post data to " + url, e);
            return null;
        } finally {
            get.releaseConnection();
        }

        return response;

    }

    /**
     *
     * @param url
     * @param headers http 头
     * @param params 请求参数，，放map里面
     * @return
     */
    public static String httpPost(String url, Map<String, String> headers, Map<String, String> params) {
        return httpPostTimeout(url, headers, params, Timeouts.SECONDS_30);

    }

    /**
     * 可修改超时时间http post请求
     *
     * @param url 请求url
     * @param headers 头
     * @param params 请求参数
     * @param timeouts 设置超时时间类型
     * @return
     */
    public static String httpPostTimeout(String url, Map<String, String> headers, Map<String, String> params, Timeouts timeouts) {
        HttpPost post = new HttpPost(url);

        post.setConfig(timeouts.requestConfig);

        List<BasicNameValuePair> httpParams = null;
        if (params != null && !params.isEmpty()) {
            httpParams = new ArrayList<BasicNameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String k = entry.getKey();
                String v = entry.getValue();
                if (v == null) {
                    httpParams.add(new BasicNameValuePair(k, null));
                } else {
                    httpParams.add(new BasicNameValuePair(k, v));
                }
            }
            if (headers != null) {
                for (Map.Entry<String, String> e : headers.entrySet()) {
                    post.addHeader(e.getKey(), e.getValue());
                }
            }
            try {
                post.setEntity(new UrlEncodedFormEntity(httpParams, UTF8));
                post.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
            } catch (UnsupportedEncodingException impossiable) {
                // shouldn't happen
                throw new RuntimeException("UTF-8 is not surportted", impossiable);
            }
        }
        String response = null;
        try {
            HttpEntity entity = client.execute(post).getEntity();
            response = EntityUtils.toString(entity, UTF8);
        } catch (Exception e) {
            throw new RuntimeException("error post data to " + url, e);
        } finally {
            post.releaseConnection();
        }

        return response;

    }

    /**
     * Post Json body
     *
     * @param url
     * @param headers 请求头 map
     * @param jsonString jsonstring
     * @return
     */
    public static String httpPost(String url, Map<String, String> headers, String jsonString) {
        return httpPostTimeout(url, headers, jsonString, Timeouts.SECONDS_30);

    }

    public static String httpPostTimeout(String url, Map<String, String> headers, String jsonString, Timeouts timeouts) {
        HttpPost post = new HttpPost(url);
        post.setConfig(timeouts.requestConfig);

        if (headers != null) {
            for (Map.Entry<String, String> e : headers.entrySet()) {
                post.addHeader(e.getKey(), e.getValue());
            }
        }
        if (jsonString != null) {
            StringEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
            post.setEntity(entity);
        }
        String response = null;
        try {
            HttpEntity entity = client.execute(post).getEntity();
            response = EntityUtils.toString(entity, UTF8);
        } catch (Exception e) {
            throw new RuntimeException("error post data to " + url, e);
        } finally {
            post.releaseConnection();
        }

        return response;

    }

    /**
     * @param url 请求的url，不包括请求参数
     * @param values 传递的参数map
     * @return
     */
    public static String httpGet(String url, Map<String, String> values,Map<String,String> headers) {
        return httpGetTimeout(url,values,headers, Timeouts.SECONDS_30);

    }

    public static String uploadFile(String url, File targetFile, Map<String, String> headers, Map<String, String> params) {

        FileEntity entity = new FileEntity(targetFile, ContentType.create("text/plain", "UTF-8"));

        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "text/html;charset=UTF-8");
        post.setConfig(Timeouts.SECONDS_10.requestConfig);
        List<BasicNameValuePair> httpParams = null;
        if (params != null) {
            httpParams = new ArrayList<BasicNameValuePair>(params.size());

            Set<String> set = params.keySet();
            for (Iterator<String> ite = set.iterator(); ite.hasNext();) {
                String key = ite.next();
                String value = params.get(key);
                if (value == null) {
                    continue;
                }

                BasicNameValuePair pair = new BasicNameValuePair(key, value);
                httpParams.add(pair);
            }
        }

        if (headers != null) {
            Set<String> headersSet = headers.keySet();
            for (Iterator<String> ite = headersSet.iterator(); ite.hasNext();) {
                String headerKey = ite.next();
                post.addHeader(headerKey, headers.get(headerKey));
            }
        }

        try {
            post.setEntity(new UrlEncodedFormEntity(httpParams, UTF8));
            post.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
        } catch (UnsupportedEncodingException impossiable) {
            // shouldn't happen
            throw new RuntimeException("UTF-8 is not surportted", impossiable);
        }

        post.setEntity(entity);
        try {
            HttpEntity entityResp = client.execute(post).getEntity();
            return EntityUtils.toString(entityResp, "UTF-8");
        } catch (Exception e) {
            logger.error("error post data to " + url, e);
            return null;
        } finally {
            post.releaseConnection();
        }

    }

    public static void main(String[] args) {


    }
}
