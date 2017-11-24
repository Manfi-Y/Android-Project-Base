package cn.tianqu.libs.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.androidannotations.annotations.EBean;

import cn.tianqu.libs.app.common.widget.LoadingDialog;

/**
 * 基础界面实现类
 * <p/>
 * 实现BaseActivity和BaseFragment的BaseV接口
 * 内部已实现部分BaseV接口
 * <p/>
 * Created by Manfi
 */
@EBean
public class BaseUI implements BaseV {

    private Context context;
    private Toast toast;
    private LoadingDialog loadingDialog;
    private MaterialDialog msgDialog;

    public BaseUI(Context context) {
        this.context = context;
    }

    @Override
    public synchronized void showToast(String msg, int duration) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    @Override
    public void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showLoadingDialog(String msg, boolean cancelable, boolean cancelOutside, final DialogListener dialogListener) {
        showLoadingDialog(msg, 0, cancelable, cancelOutside, dialogListener);
    }

    @Override
    public void showLoadingDialog(String msg, int progressColorRes, boolean cancelable, boolean cancelOutside, final DialogListener dialogListener) {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        loadingDialog = new LoadingDialog(context, msg, progressColorRes, cancelable, cancelOutside, null);
        if (dialogListener != null) {
            loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    dialogListener.onCancel(dialogInterface);
                }
            });
            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    dialogListener.onDismiss(dialogInterface);
                }
            });
        }
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.hide();
        }
    }

    @Override
    public void showMsgDialog(String title, String msg, String positiveText, String negativeText, boolean cancleable, boolean cancelOutside, final DialogListener dialogListener) {
        if (!((Activity) context).isFinishing()) {
            if (msgDialog != null) {
                msgDialog.dismiss();
            }
            MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(context);
            dialogBuilder.positiveText(positiveText);
            dialogBuilder.negativeText(negativeText);
            dialogBuilder.title(title);
            dialogBuilder.content(msg);
            if (dialogListener != null) {
                dialogBuilder.callback(new MaterialDialog.ButtonCallback() {

                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialogListener.onPositive(dialog);
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialogListener.onNegative(dialog);
                    }
                });
                dialogBuilder.cancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialogListener.onCancel(dialog);
                    }
                });
                dialogBuilder.dismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialogListener.onDismiss(dialog);
                    }
                });
            }
            msgDialog = dialogBuilder.build();
            msgDialog.setCancelable(cancleable);
            msgDialog.setCanceledOnTouchOutside(cancelOutside);
            msgDialog.show();
        }
    }

    @Override
    public void onNetworkUnavailability() {
        showToast("没有网络", Toast.LENGTH_SHORT);
    }

    /**
     * 释放所有Dialog
     * <p>
     * 如果Activity销毁未释放Dialog会警报
     * </p>
     */
    protected void dismissAllDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        if (msgDialog != null) {
            msgDialog.dismiss();
        }
    }

    public interface DialogListener {

        void onPositive(DialogInterface dialog);

        void onNegative(DialogInterface dialog);

        /**
         * dialog里面onCancel会调用dismiss
         *
         * @param dialog ~
         */
        void onCancel(DialogInterface dialog);

        void onDismiss(DialogInterface dialog);
    }
}
