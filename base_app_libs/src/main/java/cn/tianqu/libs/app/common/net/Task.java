package cn.tianqu.libs.app.common.net;

/**
 * 已发出请求操作接口
 * Created by Manfi
 */
public interface Task {

    boolean isFinished();

    /**
     * 取消请求
     *
     * @param mayInteruptIfRunning 是否运行过程中中断
     * @return   ~
     */
    boolean cancel(boolean mayInteruptIfRunning);

    boolean isCanceled();
}
