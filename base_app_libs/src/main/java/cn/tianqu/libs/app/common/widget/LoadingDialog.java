package cn.tianqu.libs.app.common.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.tianqu.libs.app.R;

/**
 * 加载中Dailog
 * Created by Manfi on 16/8/4.
 */
public class LoadingDialog extends AlertDialog {

    private Context context;
    private String strLoading;
    private int colorRes;
    private boolean cancelable;
    private boolean cancelOutside;

    private TextView tvLoading;
    private ProgressBar progressBar;

    public LoadingDialog(@NonNull Context context, String msg, int colorRes, boolean cancelable, boolean cancelOutside, OnCancelListener cancelListener) {
        super(context, R.style.LoadingDialog);
        this.context = context;
        this.strLoading = msg;
        this.cancelable = cancelable;
        this.cancelOutside = cancelOutside;
        this.setOnCancelListener(cancelListener);
        this.colorRes = colorRes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.layout_dialog_loading, null);
        tvLoading = (TextView) layout.findViewById(R.id.tvLoading);
        if (TextUtils.isEmpty(strLoading)) {
            tvLoading.setVisibility(View.GONE);
        } else {
            tvLoading.setText(strLoading);
        }

        progressBar = (ProgressBar) layout.findViewById(R.id.progress);
        if (colorRes > 0) {
            progressBar.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(colorRes), PorterDuff.Mode.SRC_IN);
        }

        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelOutside);
        this.setContentView(layout);
    }

    @Override
    public void setOnCancelListener(OnCancelListener listener) {
        super.setOnCancelListener(listener);
    }
}
