--- 包含libraries ---
com.android.support:appcompat-v7
com.android.support:design
com.loopj.android:android-async-http(以为API23以后版本添加Apache Http)
com.alibaba:fastjson
com.afollestad.material-dialogs:core
org.androidannotations:androidannotations-api(需要在引用此库的model再次配置apt才能使用AA)

--- 权限检查功能例子 ---
先继承BaseActivity然后加入如下代码（获取储存权限）：

private static final int PERMISSION_REQUEST_TEST = 101;
private static final String[] TEST_PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
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
            finish();
            break;
    }
}