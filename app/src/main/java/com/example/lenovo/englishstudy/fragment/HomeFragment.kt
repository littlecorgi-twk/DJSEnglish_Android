package com.example.lenovo.englishstudy.fragment

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast

import com.example.lenovo.englishstudy.R
import com.example.lenovo.englishstudy.Util.GetRequest_Interface
import com.example.lenovo.englishstudy.bean.ArticleList
import com.example.lenovo.englishstudy.pullextend.PullToRefreshLinearLayout
import com.example.lenovo.englishstudy.viewPageCard.CardItem
import com.example.lenovo.englishstudy.viewPageCard.CardPagerAdapter
import com.example.lenovo.englishstudy.viewPageCard.ShadowTransformer
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import butterknife.BindView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var mViewPager: ViewPager? = null
    private var activity: Activity? = null
    private var mCardAdapter: CardPagerAdapter? = null
    private var mCardShadowTransformer: ShadowTransformer? = null
    @BindView(R.id.lin_refresh)
    internal var linRefresh: PullToRefreshLinearLayout
    internal var handler = Handler()
    @BindView(R.id.sv_scrollView)
    internal var scrollView: ScrollView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.homefragment, container, false)
        scrollView = view.findViewById(R.id.sv_scrollView)
        mViewPager = view.findViewById(R.id.vp_WordCard)
        linRefresh = view.findViewById(R.id.lin_refresh)
        requestArticleList()
        initEvent()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initEvent() {
        // linRefresh.hideFooterView();
        linRefresh.setOnHeaderRefreshListener {
            handler.postDelayed({
                linRefresh.onHeaderRefreshComplete()
                // linRefresh.showFooterView();
                requestArticleList()
            }, 2000)
        }

        linRefresh.setOnFooterRefreshListener {
            handler.postDelayed({
                linRefresh.onFooterRefreshComplete()
                requestArticleList()
            }, 2000)
        }
    }

    fun requestArticleList() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://47.102.206.19:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val request = retrofit.create(GetRequest_Interface::class.java)

        val observable = request.articleList

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArticleList> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(articleList: ArticleList) {
                        showArticleList(articleList)
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context, "获取单词联想失败1", Toast.LENGTH_SHORT).show()
                    }

                    override fun onComplete() {}
                })
    }

    fun showArticleList(articleList: ArticleList?) {
        if (articleList != null) {
            activity = getActivity()
            mCardAdapter = CardPagerAdapter(activity)
            for (i in 0 until articleList.data.total) {
                mCardAdapter!!.addCardItem(CardItem(articleList.data.list[i]))
            }
            mCardShadowTransformer = ShadowTransformer(mViewPager!!, mCardAdapter)
            mCardShadowTransformer!!.enableScaling(true)
            mViewPager!!.adapter = mCardAdapter
            mViewPager!!.setPageTransformer(false, mCardShadowTransformer)
            mViewPager!!.offscreenPageLimit = 3
        }
    }
}
