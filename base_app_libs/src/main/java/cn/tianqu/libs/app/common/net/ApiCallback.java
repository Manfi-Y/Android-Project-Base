package cn.tianqu.libs.app.common.net;

/**
 * API回调接口
 * Created by Manfi
 */
public interface ApiCallback<T> {

    void onStart();

    void onSuccess(T result);

    void onFailure(String code, String msg);

    /**
     * 数据接口业务失败调用
     *
     * @param code ~
     * @param msg  ~
     * @return 是否处理完成
     */
    boolean onApiFailure(String code, String msg);

    void onCancel();

    /**
     * 请求完成（不论错误还是成功都会调用）
     */
    void onFinish();
}
