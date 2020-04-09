package com.example.common.base.widget;


import com.example.common.R;

/**
 * Created by Administrator on 2018/2/26 0026.
 */

public class RvLoadMoreView extends com.chad.library.adapter.base.loadmore.LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.item_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
