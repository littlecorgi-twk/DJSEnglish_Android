package com.example.lenovo.englishstudy.pullextend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author littlecorgi
 * @Date 2019-02-17 10:28
 * @email a1203991686@126.com
 */
public class VerticalScrollView extends ScrollView {
    private float xDistance, yDistance, xLast, yLast;

    public VerticalScrollView(Context context) {
        super(context);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xDistance = x;
                yDistance = y;

                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - xDistance);
                final float deltaY = Math.abs(y - yDistance);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (deltaX > deltaY) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }
}
