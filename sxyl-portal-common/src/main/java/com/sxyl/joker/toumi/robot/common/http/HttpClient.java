package com.demon.joker.toumi.robot.common.http;

import org.apache.commons.lang.ArrayUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by wangweiyf on 2016/4/14.
 */
public class HttpClient {

    private static final Logger LOG = LoggerFactory.getLogger(HttpClient.class);

    private static final DefaultHttpClient httpClient = newHttpClient();

    static CookieStore cookieStore = null;

    /**
     * http请求异常
     */
    public static final String HTTP_CLIENT_ERROR = "ERROR";
    public static final String HTTP_UNKNOWN_HOST_ERROR = "UNKNOWN_HOST_ERROR";
    public static final String HTTP_SOCKET_TIMEOUT_ERROR = "UNKNOWN_SOCKET_TIMEOUT_ERROR";


    private static final DefaultHttpClient newHttpClient() {
        // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9
        HttpParams params = new BasicHttpParams();
        //设置连接超时时间
        Integer CONNECTION_TIMEOUT = 200;    //设置请求超时2秒钟
        Integer SO_TIMEOUT = 2 * 1000;        //设置等待数据超时时间5秒钟
        Long CONN_MANAGER_TIMEOUT = 1L * 1000;        //定义了当从ClientConnectionManager中检索ManagedClientConnection实例时使用的毫秒级的超时时间
        params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
        //在提交请求之前 测试连接是否可用
        params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
        //这个参数期望得到一个java.lang.Long类型的值。如果这个参数没有被设置，连接请求就不会超时（无限大的超时时间）
        params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);

        //不做重定向
        params.setBooleanParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
        params.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, false);

        PoolingClientConnectionManager conMgr = new PoolingClientConnectionManager();
        conMgr.setMaxTotal(5000);//设置最大连接数
        //是路由的默认最大连接（该值默认为2），限制数量实际使用DefaultMaxPerRoute并非MaxTotal。
        //设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for connection from pool)，路由是对maxTotal的细分。
        conMgr.setDefaultMaxPerRoute(conMgr.getMaxTotal() / 20);//（目前只有一个路由，因此让他等于最大值）

        //设置访问协议
        conMgr.getSchemeRegistry().register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        conMgr.getSchemeRegistry().register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        DefaultHttpClient httpClient = new DefaultHttpClient(conMgr, params);
        httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, true));
        return httpClient;
    }


    /**
     * 向指定URI POST数据
     *
     * @param url
     * @param entity
     * @param encoding
     * @param timeout
     * @param cookieFlag
     * @return
     */
    public static String postDataToUri(String url, HttpEntity entity, Header[] headers, String encoding, Integer timeout, boolean cookieFlag) {
        //解决POST中文乱码问题
        HttpResponse response = null;
        try {
            HttpPost post = new HttpPost(url);
            if(org.apache.commons.lang.StringUtils.isBlank(encoding)) {
                encoding = "UTF-8";
            }

            if(timeout != null) {
                post.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout / 2);
                post.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
            }

            post.setHeader("Accept-Charset", encoding);
            if (entity != null) {
                post.setEntity(entity);
            }
            if (ArrayUtils.isNotEmpty(headers)) {
                for(Header header : headers) {
                    post.addHeader(header);
                }
            }
            if (cookieFlag && cookieStore == null) {
                httpClient.setCookieStore(cookieStore);
            }
            response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                LOG.error("Post Data Failure: url : {}, data : {}, headers : {} ,status line : {}", url, entityToString(entity), headers, response.getStatusLine());


                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e1) {
                }
                return HTTP_CLIENT_ERROR;
            }
            if(cookieFlag) {
                try {
                    setCookieStore(response);
                }catch (Exception e) {
                    LOG.error( "解析获取cookie失败",e);
                }
            }
            return EntityUtils.toString(response.getEntity(), Charset.forName(encoding));
        } catch (Exception ex) {
            LOG.error("Post Data Exception: url : {}, data : {}, headers : {}", url, entityToString(entity), headers, ex);
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e1) {
                }
            }
            //如果域名解析错误等
            if(ex instanceof UnknownHostException || ex instanceof PortUnreachableException || ex instanceof NoRouteToHostException) {
                return HTTP_UNKNOWN_HOST_ERROR;
            }
            //如果连接失败
            if(ex instanceof ConnectException) {
                return HTTP_UNKNOWN_HOST_ERROR;
            }
            //超时异常
            if(ex instanceof SocketTimeoutException) {
                return HTTP_SOCKET_TIMEOUT_ERROR;
            }
            return HTTP_CLIENT_ERROR;
        }
    }



    private static String entityToString(HttpEntity entity) {
        if (entity == null) {
            return null;
        }
        try {
            return EntityUtils.toString(entity);
        }catch (Exception e) {
            return e.getMessage();
        }
    }




    public static void setCookieStore(HttpResponse httpResponse) {
        cookieStore = new BasicCookieStore();
        // JSESSIONID
        for (Header header  :  httpResponse.getAllHeaders()) {

            if ("Set-Cookie" .equals(header.getName())) {
                String value = header.getValue() ;
                String name = header.getName() ;

                String[] key = value.split(";") ;
                if(key.length > 0 ) {
                    String[] details = key[0].split("=") ;

                    if(details.length > 0)  {
                        BasicClientCookie cookie = new BasicClientCookie(details[0],
                                details[1]);
                        cookie.setVersion(0);
                        cookie.setDomain("www.itoumi.com");
                        cookie.setPath("/");
                        cookieStore.addCookie(cookie);
                    }

                }


                String d  = header.getValue();
            }

        }
        // 新建一个Cookie
    }

}
