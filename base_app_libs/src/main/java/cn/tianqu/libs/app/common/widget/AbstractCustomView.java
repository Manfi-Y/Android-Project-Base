package cn.tianqu.libs.app.common.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义布局封装类
 * <p>
 * 会先初始化View再初始化数据
 * </p>
 */
public abstract class AbstractCustomView {

    protected Context context;
    protected LayoutInflater inflater;
    protected ViewGroup parentView;
    protected View rootView;

    public AbstractCustomView(Context context, ViewGroup parentView) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.parentView = parentView;
    }

    /**
     * init方法需要在继承后自行在构造函数调用，或者在外部调用，
     * 因为有些时候需要延迟初始化，这样可以灵活一点。
     */
    protected void init() {
        rootView = initView(inflater, parentView);
        initData();
    }

    /**
     * 初始化View
     *
     * @param inflater   ~
     * @param parentView ~
     * @return ~
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup parentView);

    /**
     * 初始化成员变量
     */
    protected  abstract void initData();

    public View getRootView() {
        return rootView;
    }
}
