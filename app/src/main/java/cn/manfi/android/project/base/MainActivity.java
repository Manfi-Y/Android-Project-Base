package cn.manfi.android.project.base;

import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import cn.tianqu.libs.app.common.FileUtils;
import cn.tianqu.libs.app.common.net.MyAsyncHttpClient;
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
        FileUtils.deleteRecursive(new File(FileUtils.getSDCardPath() + "8684Metro/data", "guangzhou_20170330163457"));
    }
}
