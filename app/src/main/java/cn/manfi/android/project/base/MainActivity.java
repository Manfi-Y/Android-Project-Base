package cn.manfi.android.project.base;

import android.Manifest;
import android.content.Intent;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;
import java.util.List;

import cn.tianqu.libs.app.common.net.MyAsyncHttpClient;
import cn.tianqu.libs.app.common.permission.AfterPermissionGranted;
import cn.tianqu.libs.app.common.permission.PermissionUtils;
import cn.tianqu.libs.app.ui.BaseActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private static final int PERMISSION_REQUEST_TEST = 101;
    private static final String[] TEST_PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

    @ViewById
    Button btn_Test;

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
    protected void init() {

    }

    @Click(R.id.btn_Test)
    void clickTest() {
//        FileUtils.deleteRecursive(new File(FileUtils.getSDCardPath() + "8684Metro/data", "guangzhou_20170330163457"));
        checkPermission();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQUEST_TEST) {
            String[] perms = TEST_PERMISSIONS;
            perms = PermissionUtils.hasPermissions(this, perms);
            if (perms != null) {
                finish();
            }
        }
    }

    @AfterPermissionGranted(PERMISSION_REQUEST_TEST)
    void checkPermission() {
        String[] perms = TEST_PERMISSIONS;
        perms = PermissionUtils.hasPermissions(this, perms);
        if (perms == null) {
            showToast("授权成功");
        } else {
            List<String> permsGroupName = PermissionUtils.loadPermissionsGroupName(getApplicationContext(), Arrays.asList(perms));
            PermissionUtils.requestPermissions(this, "需要授予\"" + PermissionUtils.toPermisionsGroupString(permsGroupName) + "\"权限。", PERMISSION_REQUEST_TEST, perms);
        }
    }

    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {
        super.onPermissionDenied(requestCode, perms);
        switch (requestCode) {
            case PERMISSION_REQUEST_TEST:
                if (!PermissionUtils.somePermissionsPermanentlyDenied(this, perms)) {
                    finish();
                }
                break;
        }
    }

    @Override
    public void onPermissionNotAllow(int requestCode, List<String> perms) {
        super.onPermissionNotAllow(requestCode, perms);
        switch (requestCode) {
            case PERMISSION_REQUEST_TEST:
                if (!PermissionUtils.somePermissionsPermanentlyDenied(this, perms)) {
                    finish();
                }
                break;
        }
    }
}
