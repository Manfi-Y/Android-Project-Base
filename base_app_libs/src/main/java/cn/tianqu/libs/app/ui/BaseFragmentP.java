package cn.tianqu.libs.app.ui;

import android.app.Activity;

/**
 * 基础 Fragment Presenter
 * Created by Manfi
 */
public abstract class BaseFragmentP<V extends BaseFragmentV> extends BaseP<V> {

    protected Activity activity;

    public BaseFragmentP(Activity activity, V view) {
        this.activity = activity;
        this.view = view;
    }
}
