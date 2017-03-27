package cn.tianqu.libs.app.ui;

/**
 * 基础 View 接口
 * Created by Manfi
 */
public interface BaseV {

    /**
     * 显示Toast
     *
     * @param msg      ~
     * @param duration ~
     */
    void showToast(String msg, int duration);

    /**
     * 显示Toast（LENGTH_SHORT）
     *
     * @param msg ~
     */
    void showToast(String msg);

    /**
     * 显示Loading dialog
     *
     * @param msg            ~
     * @param cancelable     ~
     * @param cancelOutside  ~
     * @param dialogListener ~
     */
    void showLoadingDialog(String msg, boolean cancelable, boolean cancelOutside, BaseUI.DialogListener dialogListener);

    void showLoadingDialog(String msg, int progressColorRes, boolean cancelable, boolean cancelOutside, BaseUI.DialogListener dialogListener);

    /**
     * 隐藏Loading Dialog
     */
    void hideLoadingDialog();

    /**
     * 显示Msg Dialog
     *
     * @param title          ~
     * @param msg            ~
     * @param positiveText   ~
     * @param negativeText   ~
     * @param cancleable     ~
     * @param cancelOutside  ~
     * @param dialogListener ~
     */
    void showMsgDialog(String title, String msg, String positiveText, String negativeText, boolean cancleable, boolean cancelOutside, BaseUI.DialogListener dialogListener);

    /**
     * 没有网络
     */
    void onNetworkUnavailability();
}
