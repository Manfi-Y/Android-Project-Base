package cn.tianqu.libs.app.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import cn.tianqu.libs.app.R;

/**
 * BaseFragmentDialog
 * Created by manfi on 2017/11/30.
 */

public abstract class BaseFragmentDialog extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getDialogStyleRes() == 0) {
            setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DefaultFragmentDialog);
        } else {
            setStyle(DialogFragment.STYLE_NO_FRAME, getDialogStyleRes());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 设置进场动画
        if (getWindowAnimationsRes() != 0) {
            getDialog().getWindow().setWindowAnimations(getWindowAnimationsRes());
        }
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(getArguments());
    }

    /**
     * 初始化
     *
     * @param view ~
     */
    protected abstract void initView(View view);

    /**
     * @return 自定义布局id
     */
    protected abstract int getLayoutId();

    /**
     * 可根据传入的arguments开始加载数据
     *
     * @param arguments ~
     */
    protected abstract void loadData(Bundle arguments);

    /**
     * 设置窗口转场动画
     *
     * @return ~
     */
    protected abstract int getWindowAnimationsRes();

    /**
     * 设置弹出框样式
     *
     * @return ~
     */
    protected abstract int getDialogStyleRes();

    /**
     * 创建视图
     *
     * @param context ~
     * @param resId   布局id
     * @param <T>     布局类型
     *
     * @return ~
     */
    protected <T extends View> T createView(Context context, int resId) {
        return (T) LayoutInflater.from(context).inflate(resId, null);
    }

    /**
     * 设置是否可以点击外部dismiss Dialog
     *
     * @param cancel ~
     */
    public void setCanceledOnTouchOutside(boolean cancel) {
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(cancel);
        }
    }

    /**
     * 设置方向
     *
     * @param gravity ~
     */
    public void setGravity(int gravity) {
        if (getDialog() != null) {
            Window mWindow = getDialog().getWindow();
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.gravity = gravity;
            mWindow.setAttributes(params);
        }
    }

    /**
     * 设置Dialog width
     *
     * @param width ~
     */
    public void setDialogWidth(int width) {
        setDialogWidthAndHeight(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置Dialog height
     *
     * @param height ~
     */
    public void setDialogHeight(int height) {
        setDialogWidthAndHeight(LinearLayout.LayoutParams.WRAP_CONTENT, height);
    }

    /**
     * 设置Dialog width，height
     * 需要在Dialog展示后设置，或者在Dialog的onResume方法内设置
     *
     * @param width  ~
     * @param height ~
     */
    public void setDialogWidthAndHeight(int width, int height) {
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(width, height);
        }
    }

    /**
     * 显示Dialog
     *
     * @param activity ~
     * @param tag      设置一个标签用来标记Dialog
     */
    public void show(Activity activity, String tag) {
        show(activity, null, tag);
    }

    /**
     * 显示Dialog
     *
     * @param activity
     * @param bundle   要传递给Dialog的Bundle对象
     * @param tag      设置一个标签用来标记Dialog
     */
    public void show(Activity activity, Bundle bundle, String tag) {
        if (activity == null && isShowing())
            return;
        FragmentTransaction mTransaction = activity.getFragmentManager().beginTransaction();
        Fragment mFragment = activity.getFragmentManager().findFragmentByTag(tag);
        if (mFragment != null) {
            //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
            mTransaction.remove(mFragment);
        }
        if (bundle != null) {
            setArguments(bundle);
        }
        show(mTransaction, tag);
    }

    /**
     * 是否显示
     *
     * @return false:isHidden  true:isShowing
     */
    public boolean isShowing() {
        if (this.getDialog() != null) {
            return this.getDialog().isShowing();
        } else {
            return false;
        }
    }
}
