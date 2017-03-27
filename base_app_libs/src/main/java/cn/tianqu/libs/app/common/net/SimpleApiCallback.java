package cn.tianqu.libs.app.common.net;


import android.content.DialogInterface;
import android.support.annotation.Nullable;

import cn.tianqu.libs.app.ui.BaseP;
import cn.tianqu.libs.app.ui.BaseV;
import cn.tianqu.libs.app.ui.SimpleDialogListener;

/**
 * API请求回调抽象
 * Created by Manfi
 */
public abstract class SimpleApiCallback<T> implements ApiCallback<T> {

    protected BaseV baseV;
    protected BaseP baseP;
    protected SimpleApiCallback simpleApiCallback;
    protected Task task;

    public SimpleApiCallback() {
    }

    public SimpleApiCallback(SimpleApiCallback simpleApiCallback) {
        this.simpleApiCallback = simpleApiCallback;
    }

    public SimpleApiCallback(BaseV baseV) {
        this.baseV = baseV;
    }

    public SimpleApiCallback(BaseP baseP) {
        this.baseP = baseP;
    }

    @Override
    public void onStart() {
        if (simpleApiCallback != null) {
            simpleApiCallback.onStart();
        }
        if (baseV != null) {
            baseV.showLoadingDialog("正在获取数据", true, false, new SimpleDialogListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    super.onCancel(dialog);
                    if (task != null) {
                        task.cancel(false);
                    }
                }
            });
        }
    }

    @Override
    public void onFailure(String code, String msg) {
        if (simpleApiCallback != null) {
            simpleApiCallback.onFailure(code, msg);
        }
        if (baseV != null) {
            baseV.showMsgDialog("提示", msg, "再试", "放弃了", false, false, new SimpleDialogListener() {

                @Override
                public void onPositive(DialogInterface dialog) {
                    super.onPositive(dialog);
                    SimpleApiCallback.this.retry();
                }

                @Override
                public void onNegative(DialogInterface dialog) {
                    super.onNegative(dialog);
                    SimpleApiCallback.this.onCancel();
                }
            });
        }
        if (baseP != null) {
            baseP.apiFailure(code, msg, this);
        }
    }

    @Override
    public boolean onApiFailure(String code, String msg) {
        if (simpleApiCallback != null) {
            simpleApiCallback.onApiFailure(code, msg);
        }
        if (baseV != null) {
            baseV.showMsgDialog("提示", msg, "", "关闭", true, false, null);
        }
        if (baseP != null) {
            baseP.apiFailure(code, msg, this);
        }
        return true;
    }

    @Override
    public void onCancel() {
        if (simpleApiCallback != null) {
            simpleApiCallback.onCancel();
        }
    }

    @Override
    public void onFinish() {
        if (simpleApiCallback != null) {
            simpleApiCallback.onFinish();
        }
        if (baseV != null) {
            baseV.hideLoadingDialog();
        }
    }

    /**
     * 再次请求
     * <p>
     * onApiFailure 提示对话框“重试”会调用此方法
     * </p>
     */
    public void retry() {
        if (simpleApiCallback != null) {
            simpleApiCallback.retry();
        }
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
