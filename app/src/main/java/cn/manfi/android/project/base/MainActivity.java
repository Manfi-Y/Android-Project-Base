package cn.manfi.android.project.base;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.tianqu.libs.app.common.net.MyAsyncHttpClient;
import cn.tianqu.libs.app.common.permission.AppSettingsDialog;
import cn.tianqu.libs.app.ui.BaseActivity;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById
    Button btn_Test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyAsyncHttpClient.cancelAllRequests(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            MainActivityPermissionsDispatcher.doSomethingWithCheck(this);
        }
    }

    @Override
    protected void init() {
    }


    @Click(R.id.btn_Test)
    void clickTest() {
        MainActivityPermissionsDispatcher.doSomethingWithCheck(this);
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void doSomething() {
        showToast("授权成功！");
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void doSomethingDenied() {
        System.out.println("MainActivity.doSomethingDenied");
    }

    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void doSomethingShowRationale(PermissionRequest request) {
        System.out.println("MainActivity.doSomethingShowRationale");
        request.proceed();
    }

    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void doSomethingNeverAskPermission() {
        System.out.println("MainActivity.doSomethingNeverAskPermission");
        askPermanentlyDeniedPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE);
    }

    @Override
    protected void permanentlyDeniedPermissionDenied(List<String> permanentlyDeniedPerms) {
        super.permanentlyDeniedPermissionDenied(permanentlyDeniedPerms);
        System.out.println("MainActivity.permanentlyDeniedPermissionDenied");
    }
}
