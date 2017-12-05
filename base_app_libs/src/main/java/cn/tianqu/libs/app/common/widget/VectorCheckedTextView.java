package cn.tianqu.libs.app.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import cn.tianqu.libs.app.R;

/**
 * 可以直接设置VectorDrawable和tint颜色的CheckedTextView
 * Created by manfi on 2017/12/5.
 */

public class VectorCheckedTextView extends AppCompatCheckedTextView {

    private boolean isTintDrawableInTextColor;
    private int mDrawableCompatColor;
    private boolean isDrawableAdjustTextWidth;
    private boolean isDrawableAdjustTextHeight;
    private int mDrawableWidth;
    private int mDrawableHeight;

    public VectorCheckedTextView(Context context) {
        this(context, null);
    }

    public VectorCheckedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VectorCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VectorCheckedTextView);

            Drawable dl = null;
            Drawable dt = null;
            Drawable dr = null;
            Drawable db = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                dl = a.getDrawable(R.styleable.VectorCheckedTextView_drawableLeftCompat);
                dt = a.getDrawable(R.styleable.VectorCheckedTextView_drawableTopCompat);
                dr = a.getDrawable(R.styleable.VectorCheckedTextView_drawableRightCompat);
                db = a.getDrawable(R.styleable.VectorCheckedTextView_drawableBottomCompat);
            } else {
                int dlId = a.getResourceId(R.styleable.VectorCheckedTextView_drawableLeftCompat, -1);
                int dtId = a.getResourceId(R.styleable.VectorCheckedTextView_drawableTopCompat, -1);
                int drId = a.getResourceId(R.styleable.VectorCheckedTextView_drawableRightCompat, -1);
                int dbId = a.getResourceId(R.styleable.VectorCheckedTextView_drawableBottomCompat, -1);

                if (dlId != -1)
                    dl = AppCompatResources.getDrawable(context, dlId);
                if (dtId != -1)
                    dt = AppCompatResources.getDrawable(context, dtId);
                if (drId != -1)
                    dr = AppCompatResources.getDrawable(context, drId);
                if (dbId != -1)
                    db = AppCompatResources.getDrawable(context, dbId);
            }

            isTintDrawableInTextColor = a.getBoolean(R.styleable.VectorCheckedTextView_tintDrawableInTextColor, false);
            mDrawableCompatColor = a.getColor(R.styleable.VectorCheckedTextView_drawableCompatColor, 0);
            isDrawableAdjustTextWidth = a.getBoolean(R.styleable.VectorCheckedTextView_drawableAdjustTextWidth, false);
            isDrawableAdjustTextHeight = a.getBoolean(R.styleable.VectorCheckedTextView_drawableAdjustTextHeight, false);
            mDrawableWidth = a.getDimensionPixelSize(R.styleable.VectorCheckedTextView_drawableWidth, 0);
            mDrawableHeight = a.getDimensionPixelSize(R.styleable.VectorCheckedTextView_drawableHeight, 0);
            a.recycle();

            if (mDrawableWidth < 0)
                mDrawableWidth = 0;
            if (mDrawableHeight < 0)
                mDrawableHeight = 0;

            initDrawables(dl, dt, dr, db);
        }
    }

    private void initDrawables(Drawable... drawables) {
        for (Drawable drawable : drawables) {
            tintDrawable(drawable);
        }

        if (!isDrawableAdjustTextWidth && !isDrawableAdjustTextHeight && mDrawableWidth == 0
                && mDrawableHeight == 0) {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
        } else {
            if (isDrawableAdjustTextWidth || isDrawableAdjustTextHeight) {
                boolean invalid = (isDrawableAdjustTextWidth && (drawables[0] != null || drawables[2] != null))
                        || (isDrawableAdjustTextHeight && (drawables[1] != null || drawables[3] != null));
                if (invalid) {
                    if (mDrawableWidth > 0 || mDrawableHeight > 0) {
                        resizeDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
                    } else {
                        setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawables[2], drawables[3]);
                    }
                } else {
                    adjustDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
                }
            } else if (mDrawableWidth > 0 || mDrawableHeight > 0) {
                resizeDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
            }
        }
    }

    private void tintDrawable(Drawable drawable) {
        if (drawable != null) {
            if (isTintDrawableInTextColor) {
                DrawableCompat.setTint(drawable.mutate(), getCurrentTextColor());
            } else if (mDrawableCompatColor != 0) {
                DrawableCompat.setTint(drawable.mutate(), mDrawableCompatColor);
            }
        }
    }

    private void resizeDrawables(Drawable... drawables) {
        for (Drawable drawable : drawables) {
            if (drawable == null) {
                continue;
            }

            if (mDrawableWidth > 0 && mDrawableHeight > 0) {
                drawable.setBounds(0, 0, mDrawableWidth, mDrawableHeight);
            } else if (mDrawableWidth > 0) {
                int h = mDrawableWidth * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
                drawable.setBounds(0, 0, mDrawableWidth, h);
            } else {
                int w = mDrawableHeight * drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
                drawable.setBounds(0, 0, w, mDrawableHeight);
            }
        }

        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    private void adjustDrawables(final Drawable dl, final Drawable dt, final Drawable dr, final Drawable db) {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                int measuredWidth = getMeasuredWidth();
                int measuredHeight = getMeasuredHeight();
                if (isDrawableAdjustTextWidth) {
                    int h = mDrawableHeight;

                    if (dt != null) {
                        if (h == 0)
                            h = measuredWidth * dt.getIntrinsicHeight() / dt.getIntrinsicWidth();
                        dt.setBounds(0, 0, measuredWidth, h);
                    }
                    if (db != null) {
                        if (h == 0)
                            h = measuredWidth * db.getIntrinsicHeight() / db.getIntrinsicWidth();
                        db.setBounds(0, 0, measuredWidth, h);
                    }
                }
                if (isDrawableAdjustTextHeight) {
                    int w = mDrawableWidth;

                    if (dl != null) {
                        if (w == 0)
                            w = measuredHeight * dl.getIntrinsicWidth() / dl.getIntrinsicHeight();
                        dl.setBounds(0, 0, w, measuredHeight);
                    }
                    if (dr != null) {
                        if (w == 0)
                            w = measuredHeight * dr.getIntrinsicWidth() / dr.getIntrinsicHeight();
                        dr.setBounds(0, 0, w, measuredHeight);
                    }
                }

                setCompoundDrawables(dl, dt, dr, db);
            }
        });
    }

    @Override
    public void setTextColor(@ColorInt int color) {
        super.setTextColor(color);

        refreshCompoundDrawables();
    }

    private void refreshCompoundDrawables() {
        Drawable[] drawables = getCompoundDrawables();
        for (Drawable drawable : drawables) {
            tintDrawable(drawable);
        }

        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    public boolean isTintDrawableInTextColor() {
        return isTintDrawableInTextColor;
    }

    public void setTintDrawableInTextColor(boolean tintDrawableInTextColor) {
        if (isTintDrawableInTextColor == tintDrawableInTextColor)
            return;

        isTintDrawableInTextColor = tintDrawableInTextColor;
        refreshCompoundDrawables();
    }

    public int getDrawableCompatColor() {
        return mDrawableCompatColor;
    }

    public void setDrawableCompatColor(@ColorInt int drawableCompatColor) {
        if (mDrawableCompatColor == drawableCompatColor)
            return;

        mDrawableCompatColor = drawableCompatColor;
        refreshCompoundDrawables();
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        Drawable[] drawables = getCompoundDrawables();
        initDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    @Override
    public void toggle() {
        super.toggle();

        Drawable[] drawables = getCompoundDrawables();
        initDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (isTintDrawableInTextColor) {
            Drawable[] drawables = getCompoundDrawables();

            boolean needRefresh = false;
            for (Drawable drawable : drawables) {
                if (drawable != null) {
                    needRefresh = true;
                    break;
                }
            }

            if (needRefresh) {
                refreshCompoundDrawables();
            }
        }
    }
}
