package com.example.lenovo.englishstudy.userdefined;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;
    private float mDownPosX = 0;
    private float mDownPosY = 0;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ObservableScrollView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public void setOnScrollListener(ScrollViewListener scrollListener) {
        this.scrollViewListener = scrollListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            if (oldy < y) {
                scrollViewListener.onScrollChanged(this, oldy, y, false);
            } else if (oldy > y) {
                scrollViewListener.onScrollChanged(this, oldy, y, true);//isUp是否回到顶部
            }
        }
    }

    public interface ScrollViewListener {
        void onScrollChanged(ObservableScrollView observableScrollView, int oldy, int dy, boolean isUp);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();

        final int action = ev.getAction();
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - mDownPosX);
                final float deltaY = Math.abs(y - mDownPosY);
                //是否是左右滑动
                if(deltaX > deltaY) {
                    return false;
                }else {
                    return true;
                }

        }
        return super.onInterceptTouchEvent(ev);
    }

}
