package cn.tianqu.libs.app.common.net;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.tianqu.libs.app.common.log.LogUtil;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

/**
 * Api 基础类
 * Created by Manfi
 */
public class BaseApi {

    public static final boolean DEBUG = true;
    public static final String TAG = BaseApi.class.getSimpleName();

    /**
     * JSON反序列化为对象
     *
     * @param jsonStr ~
     * @param tClass  ~
     * @param <T>     ~
     * @return ~
     */
    public static <T> Object parse2Obj(String jsonStr, Class<T> tClass) throws JSONException {
        try {
            if (TextUtils.isEmpty(jsonStr)) {
                jsonStr = "{}";
            }
            return JSON.parseObject(jsonStr, tClass);
        } catch (JSONException e) {
            throw new JSONException(e.getMessage());
        }
    }

    /**
     * JSON反序列化为集合
     *
     * @param jsonStr ~
     * @param tClass  ~
     * @param <T>     ~
     * @return ~
     */
    public static <T> List<T> parse2List(String jsonStr, Class<T> tClass) throws JSONException {
        try {
            if (TextUtils.isEmpty(jsonStr)) {
                jsonStr = "[]";
            }
            return JSON.parseArray(jsonStr, tClass);
        } catch (JSONException e) {
            throw new JSONException(e.getMessage());
        }
    }

    private TextHttpResponseHandler createResponseHandler(final ApiCallback<String> callback) {
        return new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onFailure(String.valueOf(statusCode), responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                LogUtil.d(DEBUG, TAG, "请求成功:" + responseString);
                callback.onSuccess(responseString);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish();
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
                LogUtil.d(DEBUG, TAG, "再次请求:" + retryNo);
            }

            @Override
            public void onStart() {
                super.onStart();
                callback.onStart();
            }

            @Override
            public void onCancel() {
                super.onCancel();
                callback.onCancel();
            }
        };
    }

    protected Task post(final Context context, final String url, Map<String, String> headerMap, HttpEntity entity, String contentType, final ApiCallback<String> callback) {
        LogUtil.d(DEBUG, TAG, "接口POST请求开始：" + url + " 参数：" + entity.toString());
        final RequestHandle rh = MyAsyncHttpClient.post(context, url, headerMap, entity, contentType, createResponseHandler(callback));
        return new Task() {

            @Override
            public boolean isFinished() {
                return rh.isFinished();
            }

            @Override
            public boolean cancel(boolean mayInteruptIfRunning) {
                return rh.cancel(mayInteruptIfRunning);
            }

            @Override
            public boolean isCanceled() {
                return rh.isCancelled();
            }
        };
    }

    protected Task post(final Context context, final String url, Map<String, String> headerMap, final RequestParams params, final ApiCallback<String> callback) {
        LogUtil.d(DEBUG, TAG, "接口POST请求开始：" + url + " 参数：" + params.toString());
        final RequestHandle rh = MyAsyncHttpClient.post(context, url, headerMap, params, createResponseHandler(callback));
        return new Task() {

            @Override
            public boolean isFinished() {
                return rh.isFinished();
            }

            @Override
            public boolean cancel(boolean mayInteruptIfRunning) {
                return rh.cancel(mayInteruptIfRunning);
            }

            @Override
            public boolean isCanceled() {
                return rh.isCancelled();
            }
        };
    }

    protected Task post(Context context, String url, Map<String, String> paramsMap, ApiCallback<String> callback) {
        return post(context, url, new RequestParams(paramsMap), callback);
    }

    protected Task post(Context context, String url, RequestParams params, final ApiCallback<String> callback) {
        return post(context, url, null, params, callback);
    }

    protected Task post(Context context, String url, Map<String, String> headerMap, Map<String, String> paramsMap, ApiCallback<String> callback) {
        return post(context, url, headerMap, new RequestParams(paramsMap), callback);
    }

    protected Task delete(final Context context, final String url, Map<String, String> headerMap, HttpEntity entity, String contentType, final ApiCallback<String> callback) {
        LogUtil.d(DEBUG, TAG, "接口DELETE请求开始：" + url + (entity == null ? "" : " 参数：" + entity.toString()));
        final RequestHandle rh = MyAsyncHttpClient.delete(context, url, headerMap, entity, contentType, createResponseHandler(callback));
        return new Task() {

            @Override
            public boolean isFinished() {
                return rh.isFinished();
            }

            @Override
            public boolean cancel(boolean mayInteruptIfRunning) {
                return rh.cancel(mayInteruptIfRunning);
            }

            @Override
            public boolean isCanceled() {
                return rh.isCancelled();
            }
        };
    }


    protected Task get(Context context, final String url, Map<String, String> headerMap, HttpEntity entity, String contentType, final ApiCallback<String> callback) {
        LogUtil.d(DEBUG, TAG, "接口GET请求开始：" + url + (entity == null ? "" : " 参数：" + entity.toString()));
        final RequestHandle rh = MyAsyncHttpClient.get(context, url, headerMap, entity, contentType, createResponseHandler(callback));
        return new Task() {

            @Override
            public boolean isFinished() {
                return rh.isFinished();
            }

            @Override
            public boolean cancel(boolean mayInteruptIfRunning) {
                return rh.cancel(mayInteruptIfRunning);
            }

            @Override
            public boolean isCanceled() {
                return rh.isCancelled();
            }
        };
    }

    protected Task get(Context context, final String url, Map<String, String> headerMap, final RequestParams params, final ApiCallback<String> callback) {
        LogUtil.d(DEBUG, TAG, "接口GET请求开始：" + url + (params == null ? "" : " 参数：" + params.toString()));
        final RequestHandle rh = MyAsyncHttpClient.get(context, url, headerMap, params, createResponseHandler(callback));
        return new Task() {

            @Override
            public boolean isFinished() {
                return rh.isFinished();
            }

            @Override
            public boolean cancel(boolean mayInteruptIfRunning) {
                return rh.cancel(mayInteruptIfRunning);
            }

            @Override
            public boolean isCanceled() {
                return rh.isCancelled();
            }
        };
    }

    protected Task get(Context context, String url, ApiCallback<String> callback) {
        return get(context, url, null, new RequestParams(), callback);
    }

    protected Task get(Context context, String url, Map<String, String> paramsMap, ApiCallback<String> callback) {
        return get(context, url, new RequestParams(paramsMap), callback);
    }

    protected Task get(Context context, String url, Map<String, String> headerMap, Map<String, String> paramsMap, ApiCallback<String> callback) {
        return get(context, url, headerMap, new RequestParams(paramsMap), callback);
    }

    protected Task get(Context context, String url, RequestParams params, final ApiCallback<String> callback) {
        return get(context, url, null, params, callback);
    }

    public Task download(Context context, String url, File downloadPath, final SimpleDownloadCallback callback) {
        final RequestHandle rh = MyAsyncHttpClient.get(context, url, null, null, new FileAsyncHttpResponseHandler(downloadPath) {

            @Override
            public void onStart() {
                super.onStart();
                callback.onStart(callback.getT());
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                callback.onProgress(callback.getT(), bytesWritten, totalSize);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                callback.onFailure(callback.getT(), file);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                callback.onSuccess(callback.getT(), file);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                callback.onFinish(callback.getT());
            }

            @Override
            public void onCancel() {
                super.onCancel();
                callback.onCancel(callback.getT());
            }
        });
        return new Task() {

            @Override
            public boolean isFinished() {
                return rh.isFinished();
            }

            @Override
            public boolean cancel(boolean mayInteruptIfRunning) {
                return rh.cancel(mayInteruptIfRunning);
            }

            @Override
            public boolean isCanceled() {
                return rh.isCancelled();
            }
        };
    }
}
