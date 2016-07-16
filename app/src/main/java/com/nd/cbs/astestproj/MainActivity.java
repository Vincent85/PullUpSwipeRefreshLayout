package com.nd.cbs.astestproj;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.nd.cbs.library.customview.PullUpRefreshLayout;


public class MainActivity extends Activity implements PullUpRefreshLayout.OnLoadMoreListener {

        public static final String TAG = "MainActivity";

    public PullUpRefreshLayout mRefreshLayout;
    public MyAdapter mAdapter;

    private static int REFRESH = 1;
    private static int LOAD_MORE = 2;

    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == REFRESH) {
                Log.d(TAG,"SwipeRefreshLayout trigger");
                mRefreshLayout.setRefreshing(false);
                mAdapter = new MyAdapter(12);
                mRefreshLayout.setAdapter(mAdapter);
                return;
            }else if(msg.what == LOAD_MORE) {
                mAdapter.loadMore();
                mRefreshLayout.loadMoreDataFinish(true);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mRefreshLayout = (PullUpRefreshLayout) findViewById(R.id.prl);
        mAdapter = new MyAdapter(14);
        mRefreshLayout.setAdapter(mAdapter);
        mRefreshLayout.setOnLoadMoreListener(this);

        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Message msg = updateHandler.obtainMessage();
                msg.what = REFRESH;
                updateHandler.sendMessageDelayed(msg,2000);
            }
        });
    }

    @Override
    public void onLoadMoreData(RecyclerView recyclerView) {
        updateHandler.sendEmptyMessageDelayed(LOAD_MORE,2000);
    }

}
