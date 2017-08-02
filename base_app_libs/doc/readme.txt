--- 包含libraries ---
com.android.support:appcompat-v7
com.android.support:design
com.loopj.android:android-async-http(以为API23以后版本添加Apache Http)
com.alibaba:fastjson
com.afollestad.material-dialogs:core
org.androidannotations:androidannotations-api(需要在引用此库的model再次配置apt才能使用AA)

--- 权限检查功能例子 ---
在你的主程序module的build.gradle添加一下依赖
provided "com.github.hotchemi:permissionsdispatcher-processor:2.4.0"
provided "org.androidannotations:androidannotations:$AAVersion"
// 当permissionsdispatcher和AndroidAnnotation同时使用，需要引入这个
provided 'com.github.AleksanderMielczarek:AndroidAnnotationsPermissionsDispatcherPlugin:2.0.0'

Activity加入@RuntimePermissions注解

必须实现的注解方法
@NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
void doSomething() {
    showToast("授权成功！");
}

编译一次项目，IDE自动生成这个Activity的专用注解解析代码

实现下面可选注解或方法
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

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
    }
}

最后在需要调用[你的Activity]+ PermissionsDispatcher.[方法名]。