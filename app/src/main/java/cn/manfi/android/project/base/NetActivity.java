package cn.manfi.android.project.base;

import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.tianqu.libs.app.common.net.SimpleApiCallback;
import cn.tianqu.libs.app.ui.BaseActivity;

/**
 * 测试网络
 * Created by manfi on 2017/11/7.
 */

@EActivity(R.layout.activity_net)
public class NetActivity extends BaseActivity {

    @ViewById
    TextView tv_Response;

    @Override
    protected void init() {

    }

    @Click(R.id.btn_Start)
    void startClick() {
        AppApi.getInstance().test1(activity, new SimpleApiCallback<String>() {

            @Override
            public void onSuccess(String result) {
                tv_Response.setText(result);
            }
        });
    }
}
