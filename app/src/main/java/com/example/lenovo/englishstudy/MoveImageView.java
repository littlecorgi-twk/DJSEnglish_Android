package com.example.lenovo.englishstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.widget.ImageView;


public class MoveImageView extends ImageView {


    public MoveImageView(Context context) {
            super(context);
        }

    public void setMPointF(PointF pointF) {
            setX(pointF.x);
            setY(pointF.y);
        }

}
