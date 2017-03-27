package cn.tianqu.libs.app.common.widget;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * SimpleTextWatcher
 * Created by Manfi
 */
public abstract class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
