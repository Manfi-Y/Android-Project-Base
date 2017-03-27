package cn.tianqu.libs.app.ui;

import android.content.DialogInterface;

/**
 * Dialog 监听器
 * Created by Manfi
 */
public abstract class SimpleDialogListener implements BaseUI.DialogListener {

    @Override
    public void onPositive(DialogInterface dialog) {

    }

    @Override
    public void onNegative(DialogInterface dialog) {

    }

    /**
     * dialog里面onCancel会调用dismiss
     *
     * @param dialog ~
     */
    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }
}
