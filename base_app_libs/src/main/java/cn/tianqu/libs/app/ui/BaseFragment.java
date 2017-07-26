package cn.tianqu.libs.app.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.List;

import cn.tianqu.libs.app.common.permission.AppSettingsDialog;
import cn.tianqu.libs.app.common.permission.PermissionUtils;

/**
 * Fragment 基础类
 * Created by Manfi
 */
@EFragment
public abstract class BaseFragment extends Fragment implements BaseFragmentV {

    protected boolean DEBUG = true;
    protected final String TAG = getClass().getSimpleName();

    protected Activity activity;
    private BaseUI baseUI;

    private String witchOptionNeedPermission = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        baseUI = new BaseUI(activity);
    }

    @Override
    public void onDestroy() {
        baseUI.dismissAllDialog();
        super.onDestroy();
    }

    @AfterViews
    protected abstract void initView();

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

    public void askPermanentlyDeniedPermission(String... perms) {
        final List<String> permanentlyDeniedPerms = new ArrayList<>();
        for (String perm : perms) {
            if (!permissions.dispatcher.PermissionUtils.hasSelfPermissions(activity, perm)) {
                permanentlyDeniedPerms.add(perm);
            }
        }
        if (permanentlyDeniedPerms.size() > 0) {
            List<String> needGrantPermissionGroupName = PermissionUtils.loadPermissionsGroupName(activity.getApplicationContext(), permanentlyDeniedPerms);
            if (needGrantPermissionGroupName != null && !needGrantPermissionGroupName.isEmpty()) {
                PermissionUtils.onPermissionsPermanentlyDenied(this,
                        PermissionUtils.toPermisionsGroupString(needGrantPermissionGroupName),
                        "需要在系统权限设置授予以下权限",
                        getString(android.R.string.ok),
                        getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                permanentlyDeniedPermissionDenied(permanentlyDeniedPerms);
                            }
                        },
                        AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE);
            }
        }
    }

    protected void permanentlyDeniedPermissionDenied(List<String> permanentlyDeniedPerms) {

    }
}
