package cn.tianqu.libs.app.common.log;


import cn.tianqu.libs.app.BuildConfig;

/**
 * Log 配置
 * Created by Manfi on
 */
public class LogConfig {

    // Log 开关（打包自动关闭）
    public static boolean DEBUG = BuildConfig.DEBUG;

    /**
     * 不允许初始化
     */
    private LogConfig() {
    }
}
