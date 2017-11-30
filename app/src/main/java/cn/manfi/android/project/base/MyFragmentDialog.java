package cn.manfi.android.project.base;

import android.os.Bundle;
import android.view.View;

import cn.tianqu.libs.app.common.UnitUtil;
import cn.tianqu.libs.app.ui.BaseFragmentDialog;

/**
 * ~
 * Created by manfi on 2017/11/30.
 */

public class MyFragmentDialog extends BaseFragmentDialog {

    @Override
    public void onResume() {
        super.onResume();
        setDialogWidthAndHeight(UnitUtil.dp2px(getActivity(), 200), UnitUtil.dp2px(getActivity(), 300));
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_my;
    }

    @Override
    protected void loadData(Bundle arguments) {

    }

    @Override
    protected int getWindowAnimationsRes() {
        return 0;
    }

    @Override
    protected int getDialogStyleRes() {
        return R.style.MyFragmentDialog;
    }
}
