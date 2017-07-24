package cn.tianqu.libs.app.ui;

import java.util.List;

/**
 * Activity 基础 View 接口
 * Created by Manfi
 */
public interface BaseActivityV extends BaseV {

    /**
     * 网络状态改变
     *
     * @param isNetworkConnect ~
     */
    void onNetworkChange(boolean isNetworkConnect);
}
