package com.example.lenovo.englishstudy.userdefined;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.englishstudy.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static org.litepal.LitePalApplication.getContext;

public class MyView extends LinearLayout {
    //各各控件
    private View dividerBottom;
    private LinearLayout llRoot;
    private ImageView ivLeftIcon;
    private TextView tvTextContent, tvRightText;
    private ImageView ivRightIcon;
    private CircleImageView rightIcon;

    public MyView(Context context) {  //该构造函数使其，可以在Java代码中创建
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {//该构造函数使其可以在XML布局中使用
        super(context, attrs);
    }

    /**
     * 初始化各个控件
     */
    public MyView init() {
        //引入之前的xml布局
        LayoutInflater.from(getContext()).inflate(R.layout.item_view, this, true);
        llRoot = findViewById(R.id.ll_root);
        dividerBottom = findViewById(R.id.divider_bottom);
        ivLeftIcon = findViewById(R.id.iv_left_icon);
        tvTextContent = findViewById(R.id.tv_text_content);
        tvRightText = findViewById(R.id.tv_right_text);
        ivRightIcon = findViewById(R.id.iv_right_icon);
        rightIcon = findViewById(R.id.iv_rightIcon);
        return this;
    }

    public MyView init(String textContent, String rightText) {
        init();
        setLeftIconSize(0,0);
        setTextContentSize(20);
        setTextContent(textContent);
        setRightText(rightText);
        setRightIconSize(0,0);
        showArrow(true);
        return this;

    }

    public MyView init2(String textContent, String rightIcon) {
        init();
        setLeftIconSize(0,0);
        setTextContentSize(20);
        setTextContent(textContent);
        setRightIcon(rightIcon);
        showArrow(true);
        return this;

    }

    public MyView init(String textContent, Bitmap rightIcon) {
        init();
        setLeftIconSize(0,0);
        setTextContentSize(20);
        setTextContent(textContent);
        setRightIcon(rightIcon);
        showArrow(true);
        return this;

    }

    public MyView init(int iconRes, String textContent) {
        init();
        setLeftIcon(iconRes);
        setLeftIconSize(25, 25);
        setTextContent(textContent);
        setRightIconSize(0,0);
        showArrow(true);
        return this;

    }

    public MyView initMine(String textContent, String rightText, boolean showDivider) {
        init(textContent, rightText);
        showDivider(showDivider);
        return this;
    }

    public MyView initMine(int iconRes, String textContent, boolean showDivider) {
        init(iconRes, textContent);
        showDivider(showDivider);
        return this;
    }

    public MyView initMine(String textContent, Bitmap rightIcon, boolean showDivider) {
        init(textContent, rightIcon);
        showDivider(showDivider);
        return this;
    }

    public MyView initMine2(String textContent, String rightIcon, boolean showDivider) {
        init2(textContent, rightIcon);
        showDivider(showDivider);
        return this;
    }

    public MyView showDivider(Boolean showDivderBottom) {
        if (showDivderBottom) {
            dividerBottom.setVisibility(VISIBLE);
        } else {
            dividerBottom.setVisibility(GONE);
        }
        return this;
    }

    //右箭头
    public MyView showArrow(boolean showArrow) {
        if (showArrow) {
            ivRightIcon.setVisibility(VISIBLE);
        } else {
            ivRightIcon.setVisibility(GONE);
        }
        return this;

    }

    /**
     * 设置root的paddingTop 与 PaddingBottom 从而控制整体的行高
     * paddingLeft 与 paddingRight 保持默认 20dp
     */
    public MyView setRootPaddingTopBottom(int paddintTop, int paddintBottom) {
        llRoot.setPadding(DensityUtils.dp2px(getContext(), 20),
                DensityUtils.dp2px(getContext(), paddintTop),
                DensityUtils.dp2px(getContext(), 20),
                DensityUtils.dp2px(getContext(), paddintBottom));
        return this;
    }

    /**
     * 设置root的paddingLeft 与 PaddingRight 从而控制整体的行高
     * <p>
     * paddingTop 与 paddingBottom 保持默认 15dp
     */
    public MyView setRootPaddingLeftRight(int paddintTop, int paddintRight) {
        llRoot.setPadding(DensityUtils.dp2px(getContext(), paddintTop),
                DensityUtils.dp2px(getContext(), 15),
                DensityUtils.dp2px(getContext(), paddintRight),
                DensityUtils.dp2px(getContext(), 15));
        return this;
    }

    /**
     * 设置左边Icon
     *
     * @param iconRes
     */
    public MyView setLeftIcon(int iconRes) {
        ivLeftIcon.setImageResource(iconRes);
        return this;
    }

    /**
     * 设置左边Icon显示与否
     *
     * @param showLeftIcon
     */
    public MyView showLeftIcon(boolean showLeftIcon) {
        if (showLeftIcon) {
            ivLeftIcon.setVisibility(VISIBLE);
        } else {
            ivLeftIcon.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置左右边Icon 以及Icon的宽高
     */
    public MyView setLeftIconSize(int widthDp, int heightDp) {
        ViewGroup.LayoutParams layoutParams = ivLeftIcon.getLayoutParams();
        layoutParams.width = DensityUtils.dp2px(getContext(), widthDp);
        layoutParams.height = DensityUtils.dp2px(getContext(), heightDp);
        ivLeftIcon.setLayoutParams(layoutParams);
        return this;
    }

    public MyView setRightIconSize(int widthDp, int heightDp) {
        ViewGroup.LayoutParams layoutParams = rightIcon.getLayoutParams();
        layoutParams.width = DensityUtils.dp2px(getContext(), widthDp);
        layoutParams.height = DensityUtils.dp2px(getContext(), heightDp);
        rightIcon.setLayoutParams(layoutParams);
        return this;
    }


    /**
     * 设置中间的文字内容
     *
     * @param textContent
     * @return
     */
    public MyView setTextContent(String textContent) {
        tvTextContent.setText(textContent);
        return this;
    }

    /**
     * 设置中间的文字颜色
     *
     * @return
     */
    public MyView setTextContentColor(int colorRes) {
        tvTextContent.setTextColor(getResources().getColor(colorRes));
        return this;
    }

    /**
     * 设置中间的文字大小
     *
     * @return
     */
    public MyView setTextContentSize(int textSizeSp) {
        tvTextContent.setTextSize(textSizeSp);
        return this;
    }

    /**
     * 设置右边的文字内容
     *
     * @param textContent
     * @return
     */
    public MyView setRightText(String textContent) {
        tvRightText.setText(textContent);
        return this;
    }

    /**
     * 设置左边Icon
     *
     * @param iconRes
     */
    public MyView setRightIcon(String iconRes) {
        Log.d("55555", iconRes.toString());
        Glide.with(getContext()).load(iconRes).into(rightIcon);
        return this;
    }

    public MyView setRightIcon(Bitmap iconRes) {
        Log.d("55555", iconRes.toString());
        Glide.with(getContext()).load(iconRes).into(rightIcon);
        return this;
    }

    /**
     * 整个一行被点击
     */
    public static interface OnRootClickListener {
        void onRootClick(View view);
    }
    /**
     * 右边箭头的点击事件
     */
    public static interface OnArrowClickListener {
        void onArrowClick(View view);
    }
    public MyView setOnRootClickListener(final OnRootClickListener onRootClickListener, final int tag) {
        llRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                llRoot.setTag(tag);
                onRootClickListener.onRootClick(llRoot);
            }
        });
        return this;
    }
    public MyView setOnArrowClickListener(final OnArrowClickListener onArrowClickListener, final int tag) {

        ivRightIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ivRightIcon.setTag(tag);
                onArrowClickListener.onArrowClick(ivRightIcon);
            }
        });
        return this;
    }

}
