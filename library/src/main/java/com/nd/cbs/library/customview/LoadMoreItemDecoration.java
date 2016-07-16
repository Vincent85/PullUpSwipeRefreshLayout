package com.nd.cbs.library.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nd.cbs.library.R;


/**
 * Created by cbs on 2016/7/10 0010.
 */
public class LoadMoreItemDecoration extends RecyclerView.ItemDecoration {

    public static final String TAG = "LoadMoreItemDecoration";

    private Bitmap mProgressBmp;

    private float mRotateDegree = 0f;

    @Override
    public void onDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.State state) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if(null == adapter) {
            return;
        }

        final int left = recyclerView.getPaddingLeft() ;
        final int right = recyclerView.getMeasuredWidth() - recyclerView.getPaddingRight() ;
        final int childSize = recyclerView.getChildCount() ;
        final View child = recyclerView.getChildAt( childSize - 1 ) ;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        final int top = child.getBottom() + layoutParams.bottomMargin ;
        final int bottom = top + dip2px(recyclerView.getContext(),50) ;

        Paint paint = new Paint();
        paint.setAntiAlias(true);// 抗锯齿
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);// 增强消除锯齿
        paint.setStrokeWidth(3);// 设置画笔的宽度
        paint.setTextSize(40);// 设置文字的大小
        paint.setColor(Color.BLACK);// 设置画笔颜色

        String text = "正在加载···";

        if(null == mProgressBmp) {
            mProgressBmp = BitmapFactory.decodeResource(recyclerView.getResources(), R.drawable.general_load_small);
        }

        //转动圆圈直径
        int diameter = mProgressBmp.getHeight();

        float textWidth = paint.measureText(text,0,text.length());
        //计算文本绘制坐标
        int x = (int) (left + (right-left-diameter-12-textWidth) / 2);
        int y = (int) (top + (bottom - top) / 2 - (paint.ascent() + paint.descent()) / 2);
        c.drawText(text,x,y,paint);

        float pivotX = x - 12 - diameter / 2;
        float pivotY = top + (bottom - top) / 2;
        c.save();
        c.rotate(mRotateDegree,pivotX,pivotY);
        c.drawBitmap(mProgressBmp,pivotX - diameter / 2,pivotY - diameter / 2,null);
        c.restore();

        if(mRotateDegree >= 360) {
            mRotateDegree = 0;
        }
        mRotateDegree += 5;
        recyclerView.invalidate(left,top,right,bottom);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        if(null == adapter) {
            return;
        }
        int lastVisibleIndex = parent.getChildAdapterPosition(view);
        if(lastVisibleIndex == (adapter.getItemCount()-1)) {
//            Log.d(TAG,"lastVisibleItemIndex is " + lastVisibleIndex + ",adapter last index is " + (adapter.getItemCount()-1));
            outRect.set(0,0,0,dip2px(parent.getContext(),50));
        }
    }

    public static int findLastVisibleItemIndex(RecyclerView.LayoutManager manager) {
        return ((LinearLayoutManager)manager).findLastVisibleItemPosition();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
