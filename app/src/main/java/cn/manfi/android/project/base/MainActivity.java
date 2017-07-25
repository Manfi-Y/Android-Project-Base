package cn.manfi.android.project.base;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Arrays;

import cn.tianqu.libs.app.common.net.MyAsyncHttpClient;
import cn.tianqu.libs.app.ui.BaseActivity;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import pl.tajchert.nammu.PermissionListener;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById
    Button btn_Test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Nammu.init(getApplicationContext());
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
    protected void init() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Nammu.permissionCompare(new PermissionListener() {

            @Override
            public void permissionsChanged(String permissionRevoke) {
                System.out.println("MainActivity.permissionsChanged");
            }

            @Override
            public void permissionsGranted(String permissionGranted) {
                System.out.println("MainActivity.permissionsGranted " + permissionGranted);
            }

            @Override
            public void permissionsRemoved(String permissionRemoved) {
                System.out.println("MainActivity.permissionsRemoved");
            }
        });
    }

    @Click(R.id.btn_Test)
    void clickTest() {
//        FileUtils.deleteRecursive(new File(FileUtils.getSDCardPath() + "8684Metro/data", "guangzhou_20170330163457"));
        /*Nammu.askForPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {

            @Override
            public void permissionGranted() {
                System.out.println("MainActivity.permissionGranted   1");
            }

            @Override
            public void permissionRefused() {
                List<String> perms = new ArrayList<>();
                perms.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                isPermissionsPermanentlyDenied(perms);
            }

        });*/

        final String[] checkPermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
        Nammu.askForPermission(activity, checkPermission, new PermissionCallback() {

            @Override
            public void permissionGranted() {
                System.out.println("MainActivity.permissionGranted   1");
            }

            @Override
            public void permissionRefused() {
                isPermissionsPermanentlyDenied(Arrays.asList(checkPermission));
            }

        });
    }
}
