package cn.tianqu.libs.app.ui;

import android.app.Activity;

/**
 * 基础Activity Presenter
 * Created by Manfi
 */
public abstract class BaseActivityP<V extends BaseActivityV> extends BaseP<V> {

    protected Activity activity;

    public BaseActivityP(Activity activity, V view) {
        this.activity = activity;
        this.view = view;
    }

}
