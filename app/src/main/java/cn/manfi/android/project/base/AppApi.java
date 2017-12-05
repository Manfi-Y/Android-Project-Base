package cn.manfi.android.project.base;

import android.content.Context;

import com.loopj.android.http.RequestParams;

import cn.tianqu.libs.app.common.net.BaseApi;
import cn.tianqu.libs.app.common.net.SimpleApiCallback;
import cn.tianqu.libs.app.common.net.Task;

/**
 * 网络数据接口
 * Created by manfi on 2017/11/7.
 */

public class AppApi extends BaseApi {

    private static AppApi instance;

    private AppApi() {

    }

    public synchronized static AppApi getInstance() {
        if (instance == null) {
            instance = new AppApi();
        }
        return instance;
    }

    public Task test1(Context context, SimpleApiCallback<String> callback) {
//        Map<String, String> params = new HashMap<>();
//        params.put("appver", "1");
        return post(context, "https://update2.8684.cn/checkupdate2.php", (RequestParams) null, callback);
    }
}
