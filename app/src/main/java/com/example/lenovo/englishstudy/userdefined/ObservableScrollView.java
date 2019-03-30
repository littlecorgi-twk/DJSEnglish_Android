package com.example.lenovo.englishstudy.userdefined;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {
    private ScrollViewListener scrollViewListener = null;
    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ObservableScrollView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public void setOnScrollListener(ScrollViewListener scrollListener){
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(scrollViewListener != null) {
            Log.d("5555","1");
            if(oldy < y) {
                scrollViewListener.onScrollChanged(this, oldy, y, false);
            } else if(oldy > y) {
                scrollViewListener.onScrollChanged(this, oldy, y, true);//isUp是否回到顶部
            }
        }
    }

    public interface ScrollViewListener{
        void onScrollChanged(ObservableScrollView observableScrollView, int oldy, int dy, boolean isUp);
    }
}
