package com.example.lenovo.englishstudy.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.englishstudy.ExplosionField;
import com.example.lenovo.englishstudy.MainActivity;
import com.example.lenovo.englishstudy.MoveImageView;
import com.example.lenovo.englishstudy.PointFTypeEvaluator;
import com.example.lenovo.englishstudy.R;


public class SearchFragment extends Fragment implements View.OnClickListener {
    private ImageView hezi;
    private RelativeLayout contain;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Toolbar toolbar;
    private ImageView end;
    private Boolean flag = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchfragment,container,false);
        end = view.findViewById(R.id.end);
        end.setOnClickListener(this);
        end.setVisibility(View.GONE);
        toolbar = view.findViewById(R.id.title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        button1 = view.findViewById(R.id.word1);
        button2 = view.findViewById(R.id.word2);
        button3 = view.findViewById(R.id.word3);
        button4 = view.findViewById(R.id.word4);

        contain = view.findViewById(R.id.search);
        hezi = view.findViewById(R.id.hezi);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int[] childCoordinate = new int[2];
//                int[] parentCoordinate = new int[2];
//                int[] shopCoordinate = new int[2];
//                //1.分别获取被点击View、父布局、购物车在屏幕上的坐标xy。
//                view.getLocationInWindow(childCoordinate);
//                contain.getLocationInWindow(parentCoordinate);
//                hezi.getLocationInWindow(shopCoordinate);
//
//                Log.d("1234",shopCoordinate[1] + " ");
//
//                //2.自定义ImageView 继承ImageView
//                MoveImageView img = new MoveImageView(getContext());
//                img.setImageResource(R.mipmap.ic_heart);
//                //3.设置img在父布局中的坐标位置
//                img.setX(childCoordinate[0] - parentCoordinate[0] );
//                img.setY(childCoordinate[1] - parentCoordinate[1] );
//                Log.d("12345",shopCoordinate[1] + " ");
//                //4.父布局添加该Img
//                contain.addView(img);
//
//                //5.利用 二次贝塞尔曲线 需首先计算出 MoveImageView的2个数据点和一个控制点
//                PointF startP = new PointF();
//                PointF endP = new PointF();
//                PointF controlP = new PointF();
//                //开始的数据点坐标就是 addV的坐标
//                startP.x = childCoordinate[0] - parentCoordinate[0] ;
//                startP.y = childCoordinate[1] - parentCoordinate[1];
//                //结束的数据点坐标就是 shopImg的坐标
//                endP.x = shopCoordinate[0] - parentCoordinate[0] + 150;
//                endP.y = shopCoordinate[1] - parentCoordinate[1] + 80;
//                //控制点坐标 x等于 购物车x；y等于 addV的y
//                controlP.x = endP.x;
//                controlP.y = startP.y;
//                Log.d("123456",shopCoordinate[1] + " ");
//
//                //启动属性动画
//                ObjectAnimator animator = ObjectAnimator.ofObject(img, "mPointF",
//                        new PointFTypeEvaluator(controlP), startP, endP);
//                animator.setDuration(1000);
//                Log.d("1234567",shopCoordinate[1] + " ");
//                animator.start();
//                animator.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        //动画结束后 父布局移除 img
//                        Object target = ((ObjectAnimator) animation).getTarget();
//                        contain.removeView((View) target);
//                        //shopImg 开始一个放大动画
//                        Animation scaleAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim);
//                        hezi.startAnimation(scaleAnim);
//                    }
//
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
//            }
//        });
        return view;
    }

    @Override
    public void onClick(View view) {
        end.setVisibility(View.VISIBLE);
        switch (view.getId()) {
            case R.id.word1:
            case R.id.word2:
            case R.id.word3:
            case R.id.word4:
                if(flag) {
                    beginAnimation1(view);
                }
                break;

            case R.id.end:
                flag = false;
                end.setVisibility(View.GONE);
                beginAnimation2();
                break;
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:
                break;
        }
        return true;
    }

    public void beginAnimation1(View view) {
        int[] childCoordinate = new int[2];
        int[] parentCoordinate = new int[2];
        int[] shopCoordinate = new int[2];
        //1.分别获取被点击View、父布局、购物车在屏幕上的坐标xy。
        view.getLocationInWindow(childCoordinate);
        contain.getLocationInWindow(parentCoordinate);
        hezi.getLocationInWindow(shopCoordinate);

        Log.d("1234",shopCoordinate[1] + " ");

        //2.自定义ImageView 继承ImageView
        MoveImageView img = new MoveImageView(getContext());
        img.setImageResource(R.mipmap.ic_heart);
        //3.设置img在父布局中的坐标位置
        img.setX(childCoordinate[0] - parentCoordinate[0] );
        img.setY(childCoordinate[1] - parentCoordinate[1] );
        Log.d("12345",shopCoordinate[1] + " ");
        //4.父布局添加该Img
        contain.addView(img);

        //5.利用 二次贝塞尔曲线 需首先计算出 MoveImageView的2个数据点和一个控制点
        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();
        //开始的数据点坐标就是 addV的坐标
        startP.x = childCoordinate[0] - parentCoordinate[0] ;
        startP.y = childCoordinate[1] - parentCoordinate[1];
        //结束的数据点坐标就是 shopImg的坐标
        endP.x = shopCoordinate[0] - parentCoordinate[0] + 150;
        endP.y = shopCoordinate[1] - parentCoordinate[1] + 80;
        //控制点坐标 x等于 购物车x；y等于 addV的y
        controlP.x = endP.x;
        controlP.y = startP.y;
        Log.d("123456",shopCoordinate[1] + " ");

        //启动属性动画
        ObjectAnimator animator = ObjectAnimator.ofObject(img, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        animator.setDuration(1000);
        Log.d("1234567",shopCoordinate[1] + " ");
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束后 父布局移除 img
                Object target = ((ObjectAnimator) animation).getTarget();
                contain.removeView((View) target);
                //shopImg 开始一个放大动画
                Animation scaleAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim);
                hezi.startAnimation(scaleAnim);
            }

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void beginAnimation2() {
        Animation rotateAnimation = new RotateAnimation(0, 5, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new CycleInterpolator(5));
        rotateAnimation.setDuration(1000);
        hezi.startAnimation(rotateAnimation);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setSelfAndChildDisappearOnClick(hezi);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /**
     * 为自己以及子View添加破碎动画，动画结束后，把View消失掉
     * @param view 可能是ViewGroup的view
     */
    private void setSelfAndChildDisappearOnClick(final View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setSelfAndChildDisappearOnClick(viewGroup.getChildAt(i));
            }
        } else {

            new ExplosionField(SearchFragment.this.getContext()).explode(view,
                    new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            view.setVisibility(View.GONE);
                        }
                    });
        }

    }
}




