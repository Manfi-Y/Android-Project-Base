package cn.manfi.android.project.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import cn.tianqu.libs.app.common.net.MyAsyncHttpClient;
import cn.tianqu.libs.app.common.permission.AfterPermissionGranted;
import cn.tianqu.libs.app.common.permission.PermissionUtils;
import cn.tianqu.libs.app.ui.BaseActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private static final int PERMISSION_REQUEST_TEST = 101;

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

    @AfterPermissionGranted(PERMISSION_REQUEST_TEST)
    void checkPermission() {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String permissionName = null;
        try {
            PermissionInfo permissionInfo = getPackageManager().getPermissionInfo(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.GET_META_DATA);
            PermissionGroupInfo permissionGroupInfo = getPackageManager().getPermissionGroupInfo(permissionInfo.group, PackageManager.GET_META_DATA);
            permissionName = (String) permissionGroupInfo.loadLabel(getPackageManager());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (PermissionUtils.hasPermissions(this, permission)) {
            showToast("授权成功");
        } else {
            PermissionUtils.requestPermissions(this, "需要\"" + permissionName + "\"权限", PERMISSION_REQUEST_TEST, permission);
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
        finish();
    }
}
