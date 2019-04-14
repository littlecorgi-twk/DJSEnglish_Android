package com.example.lenovo.englishstudy.pullextend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.lenovo.englishstudy.R;

/**
 * @author littlecorgi
 * @Date 2019-02-14 14:48
 * @email a1203991686@126.com
 */
public class PullToRefreshLinearLayout extends LinearLayout {
    // private static final String TAG = "PullToRefreshLinearLayout"
    // refresh states
    private static final int RELEASE_TO_XCX = 5;
    private static final int PULL_TO_REFRESH = 2;
    private static final int RELEASE_TO_REFRESH = 3;
    private static final int REFRESHING = 4;

    //pull state
    private static final int PULL_UP_STATE = 0;
    private static final int PULL_DOWN_STATE = 1;
    private static final int PULL_UP_XCX = 1;

    /**
     * 最近按下时手指的位置
     */
    private int mLastMotionY;
    private int mLastMotionX;

    /**
     * 头布局（小程序和刷新提示）
     */
    private View mHeaderView;

    /**
     * 尾布局（加载更多提示）
     */
    private View mFooterView;

    /**
     * list or grid
     */
    private AdapterView<?> mAdapterView;

    /**
     * scrollView
     */
    private ScrollView mScrollView;

    /**
     * 头布局高度，1:1
     */
    private int mHeaderViewHeight;

    /**
     * footer view height
     */
    private int mFooterViewHeight;

    /**
     * footer view image
     */
    private ImageView mFooterImageView;

    /**
     * header tip text
     */
    private TextView mHeaderTextView;

    /**
     * footer tip text
     */
    private TextView mFooterTextView;

    /**
     * footer refresh time
     */
    // private TextView mFooterUpdateTextView;

    /**
     * header progress bar
     */
    private ProgressBar mHeaderProgressBar;

    /**
     * footer progress bar
     */
    private ProgressBar mFooterProgressBar;

    /**
     * layout inflater
     */
    private LayoutInflater mInflater;

    /**
     * 头布局状态，下拉刷新，松开刷新，在下拉显示xcx
     */
    private int mHeaderState;

    /**
     * footer view current state
     */
    private int mFooterState;

    /**
     * 需要拦截的滑动状态，可下拉刷新，可上啦加载，上推隐藏小程序
     */
    private int mPullState;

    /**
     * 变为向下的箭头，改变箭头方向
     */
    private RotateAnimation mFlipAnimation;

    /**
     * 变为逆向的箭头，旋转
     */
    private RotateAnimation mReverseFlipAnimation;

    /**
     * footer refresh listener
     */
    private OnFooterRefreshListener mOnFooterRefreshListener;

    /**
     * header refresh listener
     */
    private OnHeaderRefreshListener mOnHeaderRefreshListener;

    /**
     * 头部小程序列表
     */
    private LinearLayout mWeixcView;

    /**
     * last update time
     */
    // private String mLastUpdateTime;

    /**
     * 是否屏蔽下拉，假如中间实际数据不足一屏,可以选择隐藏尾布局
     */
    private boolean mHideFooter;

    /**
     * 是否屏蔽下拉
     */
    private boolean mHideHeader;

    public PullToRefreshLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshLinearLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        // 需要设置成vertical
        setOrientation(LinearLayout.VERTICAL);
        // Load all of the animations we need in code rather than through XML
        mFlipAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);

        mReverseFlipAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(250);
        mReverseFlipAnimation.setFillAfter(true);

        mInflater = LayoutInflater.from(getContext());
        // header view 在此添加，保证是第一个添加到LinearLayout的最上端
        addHeaderView();
    }

    private void addHeaderView() {
        // header view
        mHeaderView = mInflater.inflate(R.layout.refresh_header, this, false);

        mWeixcView = mHeaderView.findViewById(R.id.lin_wxxc);
        mHeaderTextView = mHeaderView.findViewById(R.id.pull_to_refresh_text);
        mHeaderProgressBar = mHeaderView.findViewById(R.id.pull_to_refresh_progress);
        // header layout
        measureView(mHeaderView);

        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mHeaderViewHeight);
        // 设置topMargin的值为负的header View高度，即将其隐藏在最上方
        params.topMargin = -mHeaderViewHeight;
        // mHeaderView.setLayoutParams(params1);
        addView(mHeaderView, params);
    }

    private void addFooterView() {
        // footer view
        mFooterView = mInflater.inflate(R.layout.refresh_footer, this, false);
        mFooterImageView = mFooterView.findViewById(R.id.pull_to_load_image);
        mFooterTextView = mFooterView.findViewById(R.id.pull_to_load_text);
        // footer layout
        measureView(mFooterView);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mFooterViewHeight);
        // int top = getHeight();
        // params.topMargin = getHeight();
        // 在这里getHeight()==0,但在onInterceptTouchEvent()方法里getHeight()已经有值了,不再是0;
        // getHeight()什么时候会赋值,稍候再研究一下
        // 由于是线性布局可以直接添加,只要AdapterView的高度是MATCH_PARENT,那么footer view就会被添加到最后,并隐藏
        addView(mFooterView, params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // footer view 在此添加保证添加到LinearLayout中的最后
        addFooterView();
        initContentAdapterView();
    }

    /**
     * init AdapterView like ListView, GridView and so on; or init ScrollView
     */
    private void initContentAdapterView() {
        int count = getChildCount();
        if (count < 3) {
            throw new IllegalArgumentException(
                    "This layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
        }
        View view = null;
        for (int i = 0; i < count - 1; i++) {
            view = getChildAt(1);
            if (view instanceof AdapterView<?>) {
                mAdapterView = ((AdapterView) view);
            }
            if (view instanceof ScrollView) {
                // finish later
                mScrollView = ((ScrollView) view);
            }
        }
        if (mAdapterView == null && mScrollView == null) {
            throw new IllegalArgumentException(
                    "must contain a AdapterView or ScrollView in this layout!");
        }
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = ((int) ev.getRawX());
        int y = ((int) ev.getRawY());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 首先拦截down事件， 记录y坐标
                mLastMotionY = y;
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                // deltaY > 0 是向下运动吗， < 0 是向上运动
                int deltaX = x - mLastMotionX;
                int deltaY = y - mLastMotionY;
                if (Math.abs(deltaY) > Math.abs(deltaX)) {
                    if (isRefreshViewScroll(deltaY)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }

    /*
     * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
     * false)则由PullToRefrshLinearLayout 的子View来处理;否则由下面的方法来处理(即由PullToRefrshLinearLayout自己来处理)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // if (mLock) {
        // return true;
        // }
        int y = ((int) event.getRawY());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // onInterceptTouchEvent已经记录
                // mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                if (mPullState == PULL_DOWN_STATE) {// 执行下拉
                    if (!mHideHeader)
                        headerPrepareToRefresh(deltaY);
                    // setHeaderPadding(-mHeaderViewHeight);
                } else if (mPullState == PULL_UP_STATE) {// 执行上拉
                    if (!mHideFooter)
                        footerPrepareToRefresh(deltaY);
                } else if (mPullState == PULL_UP_XCX) {
                    changingHeaderViewTopMargin(deltaY);
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int topMargin = getHeaderTopMargin();
                if (mPullState == PULL_DOWN_STATE) {
                    if (topMargin >= -mHeaderViewHeight / 2 && topMargin < -mHeaderViewHeight / 3) {
                        // 开始刷新
                        headerRefreshing();
                    } else if (topMargin >= -mHeaderViewHeight / 3) {
                        setHeaderTopMargin(0);
                    } else {
                        // 还没有执行刷新，重新隐藏
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                } else if (mPullState == PULL_UP_STATE) {
                    if (Math.abs(topMargin) >= mHeaderViewHeight
                            + mFooterViewHeight) {
                        // 开始执行footer 刷新
                        footerRefreshing();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 是否应该到了父View,即PullToRefreshLinearLayout滑动
     *
     * @param deltaY , deltaY > 0 是向下运动,< 0是向上运动
     * @return
     */
    private boolean isRefreshViewScroll(int deltaY) {
        if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
            return false;
        }
        // 对于ListView和GridView
        if (mAdapterView != null) {
            // 子view(ListView or GridView)滑动到最顶端
            if (deltaY > 0) {
//                if (mHeaderState == RELEASE_TO_XCX) {
//                    return false;
//                }

                View child = mAdapterView.getChildAt(0);
                if (child == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                int top = child.getTop();
                int padding = mAdapterView.getPaddingTop();
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && Math.abs(top - padding) <= 8) {// 这里之前用3可以判断,但现在不行,还没找到原因
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }

            } else if (deltaY < 0) {
                View lastChild = mAdapterView.getChildAt(mAdapterView
                        .getChildCount() - 1);
                if (lastChild == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                // 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
                // 等于父View的高度说明mAdapterView已经滑动到最后
                if (lastChild.getBottom() <= getHeight()
                        && mAdapterView.getLastVisiblePosition() == mAdapterView
                        .getCount() - 1) {
                    mPullState = PULL_UP_STATE;
                    return true;
                }
                if (mHeaderState == RELEASE_TO_XCX) {          //如果小程序栏在显示中
                    View child = mAdapterView.getChildAt(0);
                    if (child == null) {
                        // 如果mAdapterView中没有数据,不拦截
                        return false;
                    }
                    if (mAdapterView.getFirstVisiblePosition() == 0
                            && child.getTop() == 0) {
                        mPullState = PULL_DOWN_STATE;
                        return true;
                    }
                    int top = child.getTop();
                    int padding = mAdapterView.getPaddingTop();
                    if (mAdapterView.getFirstVisiblePosition() == 0
                            && Math.abs(top - padding) <= 8) {// 这里之前用3可以判断,但现在不行,还没找到原因
                        mPullState = PULL_DOWN_STATE;
                        return true;
                    }
                }

            }
        }
        // 对于ScrollView
        if (mScrollView != null) {
            // 子scroll view滑动到最顶端
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0) {
                mPullState = PULL_DOWN_STATE;
                return true;
            } else if (deltaY < 0
                    && child.getMeasuredHeight() <= getHeight()
                    + mScrollView.getScrollY()) {
                mPullState = PULL_UP_STATE;
                return true;
            }
        }
        return false;
    }

    /**
     * header 准备刷新,手指移动过程,还没有释放
     *
     * @param deltaY ,手指滑动的距离
     */
    private void headerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态
        if (newTopMargin >= -mHeaderViewHeight / 2 && newTopMargin < -mHeaderViewHeight / 3 && mHeaderState != RELEASE_TO_REFRESH) {
            mHeaderTextView.setText("松开后刷新");
            //mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderState = RELEASE_TO_REFRESH;
        } else if (newTopMargin < -mHeaderViewHeight / 2 && newTopMargin > -mHeaderViewHeight) {// 拖动时没有释放
            // mHeaderImageView.
            mHeaderTextView.setText("下拉刷新");
            mHeaderState = PULL_TO_REFRESH;
        } else if (newTopMargin >= -mHeaderViewHeight / 3) {
            mHeaderTextView.setText("下拉查看小程序");
            mHeaderState = RELEASE_TO_XCX;
        }
    }

    /**
     * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header view
     * 高度是一样，都是通过修改header view的topmargin的值来达到
     *
     * @param deltaY ,手指滑动的距离
     */
    private void footerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // 如果header view topMargin 的绝对值大于或等于header + footer 的高度
        // 说明footer view 完全显示出来了，修改footer view 的提示状态
        if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
                && mFooterState != RELEASE_TO_REFRESH) {
            mFooterTextView.setText("松开后加载");
            mFooterImageView.clearAnimation();
            mFooterImageView.startAnimation(mFlipAnimation);
            mFooterState = RELEASE_TO_REFRESH;
        } else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
            mFooterImageView.clearAnimation();
            mFooterImageView.startAnimation(mFlipAnimation);
            mFooterTextView.setText("上拉加载更多");
            mFooterState = PULL_TO_REFRESH;
        }
    }

    /**
     * 修改Header view top margin的值
     *
     * @param deltaY
     */
    private int changingHeaderViewTopMargin(int deltaY) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * 0.3f;
        // 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了,感谢网友yufengzungzhe的指出
        // 表示如果是在上拉后一段距离,然后直接下拉
        if (deltaY > 0 && mPullState == PULL_UP_STATE
                && Math.abs(params.topMargin) <= mHeaderViewHeight) {
            return params.topMargin;
        }
        // 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE
                && Math.abs(params.topMargin) >= mHeaderViewHeight) {
            return params.topMargin;
        }
        params.topMargin = (int) newTopMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
        return params.topMargin;
    }


    /**
     * header refreshing
     */
    private void headerRefreshing() {
        mHeaderState = REFRESHING;
        setHeaderTopMargin(-mHeaderViewHeight / 2);
        mHeaderProgressBar.setVisibility(View.VISIBLE);
        mHeaderTextView.setText("正在刷新...");
        if (mOnHeaderRefreshListener != null) {
            mOnHeaderRefreshListener.onHeaderRefresh(this);
        }
    }

    /**
     * footer refreshing
     */
    private void footerRefreshing() {
        mFooterState = REFRESHING;
        int top = mHeaderViewHeight + mFooterViewHeight;
        setHeaderTopMargin(-top);
        mFooterImageView.setVisibility(View.GONE);
        mFooterImageView.clearAnimation();
        mFooterImageView.setImageDrawable(null);
        mFooterProgressBar.setVisibility(View.VISIBLE);
        mFooterTextView.setText("加载中...");

        if (mOnFooterRefreshListener != null) {
            mOnFooterRefreshListener.onFooterRefresh(this);
        }
    }

    /**
     * 设置header view 的topMargin的值
     *
     * @param topMargin ，为0时，说明header view 刚好完全显示出来； 为-mHeaderViewHeight时，说明完全隐藏了
     */
    private void setHeaderTopMargin(int topMargin) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        params.topMargin = topMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
    }

    /**
     * header view 完成更新后恢复初始状态
     */
    public void onHeaderRefreshComplete() {
        setHeaderTopMargin(-mHeaderViewHeight);
        mHeaderTextView.setText("下拉刷新");
        mHeaderProgressBar.setVisibility(View.GONE);
        // mHeaderUpdateTextView.setText("");
        mHeaderState = PULL_TO_REFRESH;

    }

    /**
     * Resets the list to a normal state after a refresh.
     *
     * @param lastUpdated Last updated at.
     */
    public void onHeaderRefreshComplete(CharSequence lastUpdated) {
        setLastUpdated(lastUpdated);
        onHeaderRefreshComplete();
    }

    /**
     * footer view 完成更新后恢复初始状态
     */
    public void onFooterRefreshComplete() {
        setHeaderTopMargin(-mHeaderViewHeight);
        mFooterImageView.setVisibility(View.VISIBLE);
        mFooterImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
        mFooterTextView.setText("上拉加载更多");
        mFooterProgressBar.setVisibility(View.GONE);
        // mHeaderUpdateTextView.setText("");
        mFooterState = PULL_TO_REFRESH;
    }

    /**
     * Set a text to represent when the list was last updated.
     *
     * @param lastUpdated Last updated at.
     */
    public void setLastUpdated(CharSequence lastUpdated) {
        if (lastUpdated != null) {
            //mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            // mHeaderUpdateTextView.setText(lastUpdated);
        } else {
            //mHeaderUpdateTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 获取当前header view 的topMargin
     */
    private int getHeaderTopMargin() {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        return params.topMargin;
    }

    // /**
    // * lock
    // *
    // */
    // private void lock() {
    // mLock = true;
    // }
    //
    // /**
    // * unlock
    // *
    // */
    // private void unlock() {
    // mLock = false;
    // }

    /**
     * set headerRefreshListener
     *
     * @param headerRefreshListener
     */
    public void setOnHeaderRefreshListener(
            OnHeaderRefreshListener headerRefreshListener) {
        mOnHeaderRefreshListener = headerRefreshListener;
    }

    public void setOnFooterRefreshListener(
            OnFooterRefreshListener footerRefreshListener) {
        mOnFooterRefreshListener = footerRefreshListener;
    }

    /**
     * Interface definition for a callback to be invoked when list/grid footer
     * view should be refreshed.
     */
    public interface OnFooterRefreshListener {
        public void onFooterRefresh(PullToRefreshLinearLayout view);
    }

    /**
     * Interface definition for a callback to be invoked when list/grid header
     * view should be refreshed.
     */
    public interface OnHeaderRefreshListener {
        public void onHeaderRefresh(PullToRefreshLinearLayout view);
    }

    public void hideFooterView() {
        mHideFooter = true;
    }

    public void hideHeaderView() {
        mHideHeader = true;
    }

    public void showFooterView() {
        mHideFooter = false;
    }

    public void showHeaderView() {
        mHideHeader = false;
    }
}
