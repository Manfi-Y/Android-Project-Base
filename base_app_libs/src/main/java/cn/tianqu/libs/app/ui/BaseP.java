package cn.tianqu.libs.app.ui;

import android.content.DialogInterface;

import cn.tianqu.libs.app.common.net.SimpleApiCallback;

/**
 * 基础 Presenter
 * Created by Manfi
 */
public abstract class BaseP<V extends BaseV> {

    protected boolean DEBUG = true;
    protected final String TAG = getClass().getSimpleName();

    protected V view;

    /**
     * 没有网络
     */
    public void networkUnavailability() {
        view.onNetworkUnavailability();
    }

    /**
     * API 错误提示
     *
     * @param code     ~
     * @param msg      ~
     * @param callback ~
     */
    public void apiFailure(String code, String msg, final SimpleApiCallback callback) {
        view.showMsgDialog("提示", msg, "重试", "放弃了", false, false, new SimpleDialogListener() {

            @Override
            public void onPositive(DialogInterface dialog) {
                super.onPositive(dialog);
                callback.retry();
            }
        });
    }

    /**
     * Activity或Fragment销毁时候会调用，做一些回收操作
     */
    public void destroy() {
    }
}
