package com.nd.cbs.library.customview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cbs on 2016/7/12 0012.
 */
public class PullUpRefreshLayout extends SwipeRefreshLayout {

    public static final String TAG = "PullRefreshLayout";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private PullRefreshOnScrollListener mOnScrollListener;
    //加载更多数据监听器
    private OnLoadMoreListener mOnLoadMoreListener;

    private boolean mIsDecorItemAdded = false;

    public PullUpRefreshLayout(Context context) {
        super(context);
        initView();
    }

    public PullUpRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mOnScrollListener = new PullRefreshOnScrollListener();
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        addView(mRecyclerView);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        mRecyclerView.setAdapter(this.mAdapter);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void loadMoreDataFinish(boolean isLoadFinish) {
        if(isLoadFinish && null != mOnScrollListener) {
            mOnScrollListener.removeLoadMoreItem(mRecyclerView);
        }
    }

    class PullRefreshOnScrollListener extends RecyclerView.OnScrollListener {
        private RecyclerView.ItemDecoration mDecoration;

        public PullRefreshOnScrollListener() {
            mDecoration = new LoadMoreItemDecoration();
        }

        public PullRefreshOnScrollListener(RecyclerView.ItemDecoration itemDecoration) {
            mDecoration = itemDecoration;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                int index = findLastVisibleItemIndex(recyclerView.getLayoutManager());
                if(index == (adapter.getItemCount()-1)) {
                    View subView = recyclerView.getLayoutManager().findViewByPosition(index);
                    if(null == subView) {
                        return;
                    }
                    if(subView.getBottom() >= recyclerView.getHeight() && !mIsDecorItemAdded) {
                        recyclerView.addItemDecoration(mDecoration);
                        recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView,null,adapter.getItemCount()-1);
                        if(null != mOnLoadMoreListener) {
                            mOnLoadMoreListener.onLoadMoreData(recyclerView);
                        }
                        mIsDecorItemAdded = true;
                    }
                }
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        }

        public void removeLoadMoreItem(RecyclerView recyclerView) {
            if(null != mDecoration) {
                recyclerView.removeItemDecoration(mDecoration);
                mIsDecorItemAdded = false;
            }
        }
    }

    public static int findLastVisibleItemIndex(RecyclerView.LayoutManager manager) {
        return ((LinearLayoutManager)manager).findLastCompletelyVisibleItemPosition();
    }

    public interface OnLoadMoreListener {
        void onLoadMoreData(RecyclerView recyclerView);
    }
}
