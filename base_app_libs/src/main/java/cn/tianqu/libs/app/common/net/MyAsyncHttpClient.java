package cn.tianqu.libs.app.common.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import java.util.Map;

import cn.tianqu.libs.app.common.NetworkUtil;
import cz.msebera.android.httpclient.HttpEntity;


/**
 * 自定义网络请求客户端，设定基本配置
 * Created by Manfi
 */
public class MyAsyncHttpClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

    /*static {
        // 默认超时时间
        client.setTimeout(10 * 1000);
        // 设置请求失败重连次数和间隔
        client.setMaxRetriesAndTimeout(2, 1000);
    }*/

    public static void init() {
        // 默认超时时间
        client.setTimeout(30 * 1000);
        // 设置请求失败重连次数和间隔
        client.setMaxRetriesAndTimeout(2, 1000);
    }

    public static void initProxy(Context context) {
        NetworkInfo netInfo = NetworkUtil.getNetWorkInfo(context, 0);
        if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            String netExtraInfo = netInfo.getExtraInfo();
            if ("cmwap".equalsIgnoreCase(netExtraInfo) || "uniwap".equalsIgnoreCase(netExtraInfo) || "3gwap".equalsIgnoreCase(netExtraInfo)) {
                client.setProxy("10.0.0.172", 80);
            } else if ("ctwap".equalsIgnoreCase(netExtraInfo)) {
                client.setProxy("10.0.0.200", 80);
            }
        }
    }

    /**
     * POST 请求
     *
     * @param context    上下文
     * @param url        请求url
     * @param params     请求参数
     * @param rpsHandler 回调处理接口
     * @return ~
     */
    public static RequestHandle post(Context context, String url, Map<String, String> headerMap, @NonNull RequestParams params, AsyncHttpResponseHandler rpsHandler) {
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                client.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return client.post(context, url, params, rpsHandler);
    }

    /**
     * 自定义请求格式
     *
     * @param context     上下文
     * @param url         请求url
     * @param httpEntity  请求参数
     * @param contentType @see {{@link cz.msebera.android.httpclient.entity.ContentType}}
     * @param rpsHandler  回调处理接口
     * @return ~
     */
    public static RequestHandle post(Context context, String url, Map<String, String> headerMap, HttpEntity httpEntity, String contentType, AsyncHttpResponseHandler rpsHandler) {
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                client.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return client.post(context, url, httpEntity, contentType, rpsHandler);
    }

    /**
     * GET 请求
     *
     * @param context    上下文
     * @param url        请求url
     * @param rpsHandler 回调处理接口
     * @return ~
     */
    public static RequestHandle get(Context context, String url, Map<String, String> headerMap, RequestParams params, AsyncHttpResponseHandler rpsHandler) {
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                client.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (params == null) {
            return client.get(context, url, rpsHandler);
        } else {
            return client.get(context, url, params, rpsHandler);
        }
    }

    /**
     * 自定义请求格式
     *
     * @param context     上下文
     * @param url         请求url
     * @param httpEntity  请求参数
     * @param contentType @see {{@link cz.msebera.android.httpclient.entity.ContentType}}
     * @param rpsHandler  回调处理接口
     * @return ~
     */
    public static RequestHandle get(Context context, String url, Map<String, String> headerMap, HttpEntity httpEntity, String contentType, AsyncHttpResponseHandler rpsHandler) {
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                client.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return client.get(context, url, httpEntity, contentType, rpsHandler);
    }

    /**
     * 删除请求
     *
     * @param context     上下文
     * @param url         请求url
     * @param httpEntity  请求参数
     * @param contentType @see {{@link cz.msebera.android.httpclient.entity.ContentType}}
     * @param rpsHandler  回调处理接口
     * @return
     */
    public static RequestHandle delete(Context context, String url, Map<String, String> headerMap, HttpEntity httpEntity, String contentType, AsyncHttpResponseHandler rpsHandler) {
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                client.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return client.delete(context, url, httpEntity, contentType, rpsHandler);
    }

    /**
     * 取消指定上下文发起的请求
     *
     * @param context               上下文
     * @param mayInterruptIfRunning 是否在运行过程中断
     */
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        client.cancelRequests(context, mayInterruptIfRunning);
    }

    /**
     * 取消应用所有发起的请求
     *
     * @param mayInterruptIfRunning 是否在运行过程中断
     */
    public static void cancelAllRequests(boolean mayInterruptIfRunning) {
        client.cancelAllRequests(mayInterruptIfRunning);
    }
}
