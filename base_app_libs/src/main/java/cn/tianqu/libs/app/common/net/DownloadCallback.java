package cn.tianqu.libs.app.common.net;

import java.io.File;

/**
 * 下载回调
 * Created by Manfi on 15/12/11.
 */
public interface DownloadCallback<T> {

    void onStart(T t);

    void onProgress(T t, long bytesWritten, long totalSize);

    void onSuccess(T t, File file);

    void onFinish(T t);

    void onCancel(T t);

    void onFailure(T t, File file);
}
