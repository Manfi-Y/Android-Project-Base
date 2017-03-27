package cn.tianqu.libs.app.model;

import android.content.Context;

import cn.tianqu.libs.app.common.NetworkUtil;
import cn.tianqu.libs.app.ui.BaseP;

/**
 * 基础 Model
 * Created by Manfi
 */
public class BaseModel<P extends BaseP> {

    protected String TAG = this.getClass().getSimpleName();
    protected boolean DEBUG = true;

    protected Context context;
    protected P presenter;

    public BaseModel(Context context, P presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    /**
     * 检查网络状态
     * 如果没有网络会调用BaseP的onNetworkUnavailable()；
     *
     * @return ~
     */
    public boolean checkNetworkAvailable() {
        if (NetworkUtil.isNetworkConnected(context)) {
            return true;
        } else {
            if (presenter != null) {
                presenter.networkUnavailability();
            }
            return false;
        }
    }
}
