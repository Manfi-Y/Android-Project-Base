--- 包含libraries ---
com.android.support:appcompat-v7
com.android.support:design
com.loopj.android:android-async-http(以为API23以后版本添加Apache Http)
com.alibaba:fastjson
com.afollestad.material-dialogs:core
org.androidannotations:androidannotations-api(需要在引用此库的model再次配置apt才能使用AA)

--- 权限检查功能例子 ---
先继承BaseActivity然后加入如下代码（发送短信例子）：
@AfterPermissionGranted(REQUEST_CAMERA_PERMISSION)
void sendMessageIfAllow() {
    if (PermissionUtils.hasPermissions(this, Manifest.permission.SEND_SMS)) {
        SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("13630144344", null, "哈哈", null, null);
    } else {
        PermissionUtils.requestPermissions(this, "需要短信权限", REQUEST_CAMERA_PERMISSION, Manifest.permission.SEND_SMS);
    }
}