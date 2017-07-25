package cn.tianqu.libs.app.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.List;

import cn.tianqu.libs.app.BaseApp;
import cn.tianqu.libs.app.common.net.MyAsyncHttpClient;
import cn.tianqu.libs.app.common.permission.PermissionUtilTool;
import pl.tajchert.nammu.Nammu;

/**
 * Activity 基础类
 * Created by Manfi
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity implements BaseActivityV {

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 判断权限是否有被拒绝的
     *
     * @param perms ~
     */
    protected boolean isPermissionsPermanentlyDenied(List<String> perms) {
        List<String> refusedPermission = new ArrayList<>();
        for (String perm : perms) {
            if (!Nammu.checkPermission(perm)) {
                refusedPermission.add(perm);
            }
        }
        // 用户勾选了“不在询问”后，要求去系统开启权限
        if (PermissionUtilTool.somePermissionsPermanentlyDenied(this, refusedPermission)) {
            List<String> needGrantPermissionGroupNameList = PermissionUtilTool.loadPermissionsGroupName(getApplicationContext(), refusedPermission);
            if (needGrantPermissionGroupNameList != null && !needGrantPermissionGroupNameList.isEmpty()) {
                PermissionUtilTool.onPermissionsPermanentlyDenied(this,
                        PermissionUtilTool.toPermisionsGroupString(needGrantPermissionGroupNameList),
                        "需要在系统权限设置授予以下权限",
                        getString(android.R.string.ok),
                        getString(android.R.string.cancel),
                        null,
                        PERMANENTLY_DENIED_REQUEST_CODE);
            }
            return true;
        } else {
            return false;
        }
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
