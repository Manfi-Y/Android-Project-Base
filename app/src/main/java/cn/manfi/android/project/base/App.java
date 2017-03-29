package cn.manfi.android.project.base;

import cn.tianqu.libs.app.BaseApp;
import cn.tianqu.libs.app.common.log.LogConfig;

/**
 * Application
 * Created by Manfi on 2017/3/29.
 */

public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        LogConfig.DEBUG = BuildConfig.DEBUG;
    }
}
