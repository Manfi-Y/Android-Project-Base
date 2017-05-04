--- 包含libraries ---
com.android.support:appcompat-v7
com.android.support:design
com.loopj.android:android-async-http(以为API23以后版本添加Apache Http)
com.alibaba:fastjson
com.afollestad.material-dialogs:core
org.androidannotations:androidannotations-api(需要在引用此库的model再次配置apt才能使用AA)

--- 权限检查功能例子 ---
先继承BaseActivity然后加入如下代码（获取储存权限）：
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