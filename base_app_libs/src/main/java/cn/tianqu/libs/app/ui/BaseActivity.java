package cn.tianqu.libs.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.List;

import cn.tianqu.libs.app.BaseApp;
import cn.tianqu.libs.app.common.log.LogUtil;
import cn.tianqu.libs.app.common.net.MyAsyncHttpClient;
import cn.tianqu.libs.app.common.permission.PermissionUtils;

/**
 * Activity 基础类
 * Created by Manfi
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity implements BaseActivityV, PermissionUtils.PermissionCallbacks {

    protected boolean DEBUG = true;
    protected final String TAG = getClass().getSimpleName();

    protected static final int PERMANENTLY_DENIED_REQUEST_CODE = 428;

    protected Activity activity;
    private BaseUI baseUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        baseUI = new BaseUI(activity);

        ((BaseApp) getApplication()).addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((BaseApp) getApplication()).removeActivity(this);
        baseUI.dismissAllDialog();
        // 取消Activity产生的网络请求
        MyAsyncHttpClient.cancelRequests(activity, true);
    }

    @AfterViews
    protected abstract void init();

    /**
     * 在这里初始化Toolbar
     */
    protected void initToolbar() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("BaseActivity.onActivityResult:" + requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {
        LogUtil.d(DEBUG, TAG, perms.size() + " permissions granted.");
    }

    @Override
    public void onPermissionDenied(final int requestCode, final List<String> perms) {
        LogUtil.d(DEBUG, TAG, perms.size() + " permissions denied.");
        // 用户勾选了“不在询问”后，要求去系统开启权限
        if (PermissionUtils.somePermissionsPermanentlyDenied(this, perms)) {
            List<String> needGrantPermissionGroupName = PermissionUtils.loadPermissionsGroupName(getApplicationContext(), perms);
            if (needGrantPermissionGroupName != null && !needGrantPermissionGroupName.isEmpty()) {
                PermissionUtils.onPermissionsPermanentlyDenied(this,
                        PermissionUtils.toPermisionsGroupString(needGrantPermissionGroupName),
                        "需要在系统权限设置授予以下权限",
                        getString(android.R.string.ok),
                        getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onPermissionNotAllow(requestCode, perms);
                            }
                        },
                        requestCode);
            }
        }
    }

    @Override
    public void onPermissionNotAllow(int requestCode, List<String> perms) {
    }

    /**
     * 显示Toast（LENGTH_SHORT）
     *
     * @param msg ~
     */
    public void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    @UiThread
    @Override
    public void showToast(String msg, int duration) {
        baseUI.showToast(msg, duration);
    }

    @UiThread
    @Override
    public void showLoadingDialog(String msg, boolean cancelable, boolean cancelOutside, BaseUI.DialogListener dialogListener) {
        baseUI.showLoadingDialog(msg, cancelable, cancelOutside, dialogListener);
    }

    @UiThread
    @Override
    public void showLoadingDialog(String msg, int progressColorRes, boolean cancelable, boolean cancelOutside, BaseUI.DialogListener dialogListener) {
        baseUI.showLoadingDialog(msg, progressColorRes, cancelable, cancelOutside, dialogListener);
    }

    @UiThread
    @Override
    public void hideLoadingDialog() {
        baseUI.hideLoadingDialog();
    }

    @UiThread
    @Override
    public void showMsgDialog(String title, String msg, String positiveText, String negativeText, boolean cancleable, boolean cancelOutside, BaseUI.DialogListener dialogListener) {
        baseUI.showMsgDialog(title, msg, positiveText, negativeText, cancleable, cancelOutside, dialogListener);
    }

    @UiThread
    @Override
    public void onNetworkUnavailability() {
        baseUI.onNetworkUnavailability();
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View v = this.getCurrentFocus();
            if (v == null) {
                return;
            }

            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onNetworkChange(boolean isNetworkConnect) {
    }
}
