package com.example.lenovo.englishstudy.fragment;

import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lenovo.englishstudy.MoveImageView;
import com.example.lenovo.englishstudy.PointFTypeEvaluator;
import com.example.lenovo.englishstudy.R;

public class SearchFragment extends Fragment{
    private ImageView hezi;
    private LinearLayout contain;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchfragment,container,false);
        button = view.findViewById(R.id.word1);
        contain = view.findViewById(R.id.search);
        hezi = view.findViewById(R.id.hezi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] childCoordinate = new int[2];
                int[] parentCoordinate = new int[2];
                int[] shopCoordinate = new int[2];
                //1.分别获取被点击View、父布局、购物车在屏幕上的坐标xy。
                view.getLocationInWindow(childCoordinate);
                contain.getLocationInWindow(parentCoordinate);
                hezi.getLocationInWindow(shopCoordinate);

                //2.自定义ImageView 继承ImageView
                MoveImageView img = new MoveImageView(getContext());
                img.setImageResource(R.mipmap.heart1);
                //3.设置img在父布局中的坐标位置
                img.setX(childCoordinate[0] - parentCoordinate[0]);
                img.setY(childCoordinate[1] - parentCoordinate[1]);
                //4.父布局添加该Img
                contain.addView(img);

                //5.利用 二次贝塞尔曲线 需首先计算出 MoveImageView的2个数据点和一个控制点
                PointF startP = new PointF();
                PointF endP = new PointF();
                PointF controlP = new PointF();
                //开始的数据点坐标就是 addV的坐标
                startP.x = childCoordinate[0] - parentCoordinate[0];
                startP.y = childCoordinate[1] - parentCoordinate[1];
                //结束的数据点坐标就是 shopImg的坐标
                endP.x = shopCoordinate[0] - parentCoordinate[0];
                endP.y = shopCoordinate[1] - parentCoordinate[1];
                //控制点坐标 x等于 购物车x；y等于 addV的y
                controlP.x = endP.x;
                controlP.y = startP.y;

                //启动属性动画
                ObjectAnimator animator = ObjectAnimator.ofObject(img, "mPointF",
                        new PointFTypeEvaluator(controlP), startP, endP);
                animator.setDuration(1000);
            //    animator.addListener(this);
                animator.start();
            }


        });
        return view;
    }


}




