package cn.tianqu.libs.app;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

import cn.tianqu.libs.app.common.NetworkUtil;
import cn.tianqu.libs.app.common.PrefUtil;
import cn.tianqu.libs.app.ui.BaseActivity;
import pl.tajchert.nammu.Nammu;

/**
 * Base Application
 * Created by Manfi on 16/5/27.
 */
public class BaseApp extends Application {

    protected List<Activity> activityList = new ArrayList<>();
    protected NetworkBroadcast networkBroadcast;
    private int activityCount;

    @Override
    public void onCreate() {
        super.onCreate();

        PrefUtil.init(this);
        activityList.clear();
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
        if (networkBroadcast == null && !activityList.isEmpty()) {
            networkBroadcast = new NetworkBroadcast(this);
            registerReceiver(networkBroadcast, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
        if (networkBroadcast != null && activityList.isEmpty()) {
            unregisterReceiver(networkBroadcast);
            networkBroadcast = null;
        }
    }

    public void exitApp() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        if (networkBroadcast != null) {
            unregisterReceiver(networkBroadcast);
            networkBroadcast = null;
        }
    }

    public int getActivityCount() {
        return activityCount;
    }

    class NetworkBroadcast extends BroadcastReceiver {

        private boolean isHasNetwork = false;

        public NetworkBroadcast(Context context) {
            isHasNetwork = NetworkUtil.isNetworkConnected(context);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                if (NetworkUtil.isNetworkConnected(context) != isHasNetwork) {
                    isHasNetwork = NetworkUtil.isNetworkConnected(context);
                    for (Activity activity : activityList) {
                        if (activity instanceof BaseActivity) {
                            ((BaseActivity) activity).onNetworkChange(isHasNetwork);
                        }
                    }
                }
            }
        }
    }

}
