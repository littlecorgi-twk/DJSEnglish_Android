package com.example.lenovo.englishstudy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.lenovo.englishstudy.R;

public class ColorText extends View {
    //文本开始的位置
    private int mTextStartX;
    private int mTextStartY;

    //方向
    private int mDirection = DIRECTION_LEFT;
    private static final int DIRECTION_LEFT = 0;
    private static final int DIRECTION_RIGHT = 1;
    private static final int DIRECTION_TOP = 2;
    private static final int DIRECTION_BOTTOM = 3;

    private String mText = "";
    private Paint mPaint;
    private int mTextSize;

    private int mTextOriginColor = 0xff000000;
    private int mTextChangeColor = 0xffff0000;

    private Rect mTextBound = new Rect();
    private int mTextWidth;
    private int mTextHeight;
    private float mProgress;
    private int screenWidth;


    public ColorText(Context context) {
        this(context, null);
    }

    public ColorText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorText);
        mText = ta.getString(R.styleable.ColorText_text);
        mTextSize = ta.getDimensionPixelSize(R.styleable.ColorText_text_size, mTextSize);
        mTextOriginColor = ta.getColor(R.styleable.ColorText_text_origin_color, mTextOriginColor);
        mTextChangeColor = ta.getColor(R.styleable.ColorText_text_change_color, mTextChangeColor);
        mProgress = ta.getFloat(R.styleable.ColorText_progress, 0);
        mDirection = ta.getInt(R.styleable.ColorText_direction, mDirection);
        ta.recycle();

        getScreenWidth(context);
        initView();
    }


    public int getScreenWidth(Context c) {
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return screenWidth = size.x;

    }


    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);
    }

    //测量文本内容的宽高
    private void measureText() {
        mTextWidth = (int) mPaint.measureText(mText);
        //文本长度超过屏幕截取下
        if (mTextWidth > screenWidth) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) getLayoutParams();
            int length = (int) (((float) (screenWidth - mlp.bottomMargin - mlp.topMargin) / (float) mTextWidth) * mText.length());
            mText = mText.substring(0, length);
            mTextWidth = (int) mPaint.measureText(mText);
        }
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        mTextHeight = (int) Math.ceil(fm.descent - fm.top);
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
        mTextHeight = mTextBound.height();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureText();

        //从新测量控件的大小，确保在没有指定控件大小的情况下正常显示
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);

        mTextStartX = getMeasuredWidth() / 2 - mTextWidth / 2;
        mTextStartY = getMeasuredHeight() / 2 - mTextHeight / 2;
    }

    private int measureHeight(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextHeight;
                result += getPaddingTop() + getPaddingBottom();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }

    private int measureWidth(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int val = MeasureSpec.getSize(measureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = val;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mTextWidth;
                result += getPaddingLeft() + getPaddingRight();
                break;
        }
        result = mode == MeasureSpec.AT_MOST ? Math.min(result, val) : result;
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (mDirection) {
            case DIRECTION_LEFT:
                drawText_h(canvas,
                        mTextChangeColor,
                        mTextStartX,
                        (int) (mTextStartX + mProgress * mTextWidth));
                drawText_h(canvas,
                        mTextOriginColor,
                        (int) (mTextStartX + mProgress * mTextWidth),
                        mTextStartX + mTextWidth);
                break;
            case DIRECTION_RIGHT:
                drawText_h(canvas,
                        mTextChangeColor,
                        (int) (mTextStartX + (1 - mProgress) * mTextWidth),
                        mTextStartX + mTextWidth);
                drawText_h(canvas,
                        mTextOriginColor,
                        mTextStartX,
                        (int) (mTextStartX + (1 - mProgress) * mTextWidth));
                break;
            case DIRECTION_TOP:
                drawText_v(canvas,
                        mTextChangeColor,
                        mTextStartY,
                        (int) (mTextStartY + mProgress * mTextHeight));
                drawText_v(canvas,
                        mTextOriginColor,
                        (int) (mTextStartY + mProgress * mTextHeight),
                        mTextStartY + mTextHeight);
                break;
            case DIRECTION_BOTTOM:
                drawText_v(canvas,
                        mTextChangeColor,
                        (int) (mTextStartY + (1 - mProgress) * mTextHeight),
                        mTextStartY + mTextHeight);
                drawText_v(canvas,
                        mTextOriginColor,
                        mTextStartY,
                        (int) (mTextStartY + (1 - mProgress) * mTextHeight));
                break;
        }
    }


    private void drawText_h(Canvas canvas, int color, int startX, int endX) {
        mPaint.setColor(color);
        canvas.save();
        //设置画布的显示区域 ,默认交集显示
        canvas.clipRect(startX, 0, endX, getMeasuredHeight());// left, top,right, bottom
        canvas.drawText(mText,
                mTextStartX,
                //mPaint.descent() + mPaint.ascent()文本高度
                getMeasuredHeight() / 2 - ((mPaint.descent() + mPaint.ascent()) / 2),
                mPaint);
        canvas.restore();
    }

    private void drawText_v(Canvas canvas, int color, int startY, int endY) {
        mPaint.setColor(color);
        canvas.save();
        canvas.clipRect(0, startY, getMeasuredWidth(), endY);// left, top,right, bottom
        canvas.drawText(mText,
                mTextStartX,
                getMeasuredHeight() / 2 - ((mPaint.descent() + mPaint.ascent()) / 2),
                mPaint);
        canvas.restore();
    }


    public void setDirection(int direction) {
        mDirection = direction;
        invalidate();
    }

    public int getmDirection() {
        return mDirection;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        mPaint.setTextSize(mTextSize);
        requestLayout();
        invalidate();
    }

    public void setText(String text) {
        this.mText = text;
        requestLayout();
        invalidate();
    }

    public int getTextOriginColor() {
        return mTextOriginColor;
    }

    public void setTextOriginColor(int mTextOriginColor) {
        this.mTextOriginColor = mTextOriginColor;
        invalidate();
    }

    public int getTextChangeColor() {
        return mTextChangeColor;
    }

    public void setTextChangeColor(int mTextChangeColor) {
        this.mTextChangeColor = mTextChangeColor;
        invalidate();
    }

    private static final String PROGRESS = "progress";
    private static final String STATE = "state";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putFloat(PROGRESS, mProgress);
        bundle.putParcelable(STATE, super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mProgress = bundle.getFloat(PROGRESS);
            super.onRestoreInstanceState(bundle.getParcelable(STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

}
