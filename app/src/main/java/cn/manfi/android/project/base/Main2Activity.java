package cn.manfi.android.project.base;

import android.Manifest;

import org.androidannotations.annotations.EActivity;

import java.util.List;

import cn.tianqu.libs.app.ui.BaseActivity;
import permissions.dispatcher.PermissionRequest;

/**
 * ~
 * Created by manfi on 2017/7/27.
 */
@EActivity
public abstract class Main2Activity extends BaseActivity {

    //    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void doSomething() {
        showToast("授权成功！");
    }

    //    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void doSomethingDenied() {
        System.out.println("MainActivity.doSomethingDenied");
    }

    //    @OnShowRationale({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
    void doSomethingShowRationale(PermissionRequest request) {
        System.out.println("MainActivity.doSomethingShowRationale");
        request.proceed();
    }

    //    @OnNeverAskAgain({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE})
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
