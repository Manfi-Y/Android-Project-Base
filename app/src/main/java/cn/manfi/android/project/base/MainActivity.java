package cn.manfi.android.project.base;

import android.os.Environment;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import cn.tianqu.libs.app.common.net.BaseApi;
import cn.tianqu.libs.app.common.net.MyAsyncHttpClient;
import cn.tianqu.libs.app.common.net.SimpleDownloadCallback;
import cn.tianqu.libs.app.ui.BaseActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

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
        BaseApi api = new BaseApi();
        api.download(activity, "http://update4.8684.cn/db/guangzhou_20170329152210.data", new File(Environment.getExternalStorageDirectory().getAbsolutePath()), new SimpleDownloadCallback<Object>(null, null) {
            @Override
            public void onSuccess(Object o, File file) {

            }
        });
    }
}
