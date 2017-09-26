package cn.manfi.android.project.base;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.tianqu.libs.app.common.net.MyAsyncHttpClient;
import cn.tianqu.libs.app.common.permission.AppSettingsDialog;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@EActivity(R.layout.activity_main)
@RuntimePermissions
public class MainActivity extends Main2Activity {

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
            doSomething();
        }
    }

    @Override
    protected void init() {
    }


    @Click(R.id.btn_Test)
    void clickTest() {
        doSomething();
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    @Override
    void doSomething() {
        super.doSomething();
    }

    /*@OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    @Override
    void doSomethingDenied() {
        super.doSomethingDenied();
    }*/

    /*@OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    @Override
    void doSomethingShowRationale(PermissionRequest request) {
        request.proceed();
    }*/

    /*@OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    @Override
    void doSomethingNeverAskPermission() {
        super.doSomethingNeverAskPermission();
    }*/

}
