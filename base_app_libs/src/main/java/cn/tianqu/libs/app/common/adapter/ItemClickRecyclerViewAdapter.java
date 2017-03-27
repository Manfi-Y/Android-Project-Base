package cn.tianqu.libs.app.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 带Item点击接口RecyclerView Adapter
 * Created by Manfi
 */
public abstract class ItemClickRecyclerViewAdapter<VH extends android.support.v7.widget.RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected RecyclerOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyclerOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Recycler Item Click
     */
    public interface RecyclerOnItemClickListener {

        /**
         * Item点击，通常设给ViewHolder.itemView
         *
         * @param view     view
         * @param o        数据
         * @param position 下标
         */
        void onItemClick(View view, Object o, int position);

        /**
         * Item内控件点击
         *
         * @param view     ~
         * @param o        ~
         * @param position ~
         */
        void onViewClick(View view, Object o, int position);
    }
}
