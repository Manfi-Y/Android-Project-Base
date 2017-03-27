package cn.tianqu.libs.app.common.net;

import java.io.File;

/**
 * 下载回调抽象
 * Created by Manfi on 15/12/11.
 */
public abstract class SimpleDownloadCallback<T> implements DownloadCallback<T> {

    private T t;
    private SimpleDownloadCallback<T> callback;

    public SimpleDownloadCallback(T t, SimpleDownloadCallback<T> callback) {
        this.t = t;
        this.callback = callback;
    }

    @Override
    public void onStart(T t) {
        if (callback != null) {
            callback.onStart(t);
        }
    }

    @Override
    public void onProgress(T t, long bytesWritten, long totalSize) {
        if (callback != null) {
            callback.onProgress(t, bytesWritten, totalSize);
        }
    }

    @Override
    public void onFinish(T t) {
        if (callback != null) {
            callback.onFinish(t);
        }
    }

    @Override
    public void onCancel(T t) {
        if (callback != null) {
            callback.onCancel(t);
        }
    }

    @Override
    public void onFailure(T t, File file) {
        if (callback != null) {
            callback.onFailure(t, file);
        }
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
