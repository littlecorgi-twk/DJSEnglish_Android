package com.example.lenovo.englishstudy.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.activity.WordSuggestDetailActivity;
import com.example.lenovo.englishstudy.adapter.WordSuggestAdapter;
import com.example.lenovo.englishstudy.bean.WordSuggest;
import com.example.lenovo.englishstudy.searchHistory.SearchHistoryModel;
import com.example.lenovo.englishstudy.searchHistory.SpHistoryStorage;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.lv_searchList)
    ListView lvSearchList;
    Unbinder unbinder;
    private ArrayList<WordSuggest.DataBean.EntriesBean> mEntries = new ArrayList<>();
    private WordSuggestAdapter adapter;
    private ArrayList<SearchHistoryModel> mHistoryList = new ArrayList<>();
    private SpHistoryStorage spHistoryStorage;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private int flag;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchfragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        preferences = getActivity().getSharedPreferences("select", getActivity().MODE_PRIVATE);
        editor = preferences.edit();
        spHistoryStorage = SpHistoryStorage.getInstance(getContext(), 100);
        mHistoryList = spHistoryStorage.sortHistory();
        mEntries.clear();
        for (SearchHistoryModel searchHistoryModel : mHistoryList) {
            String[] str = new String[2];
            str = TextUtils.split(searchHistoryModel.getContent(), ",|\\-");
            mEntries.add(new WordSuggest.DataBean.EntriesBean(str[0], str[1]));
        }
        adapter = new WordSuggestAdapter(getContext(), mEntries, false);
        deleteItem();
        lvSearchList.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)) {
                    flag = 1;
                    requestWordSuggest(s);
                } else {
                    lvSearchList.clearTextFilter();
                    mHistoryList = spHistoryStorage.sortHistory();
                    mEntries.clear();
                    for (SearchHistoryModel searchHistoryModel : mHistoryList) {
                        String[] str = new String[2];
                        str = TextUtils.split(searchHistoryModel.getContent(), ",|\\-");
                        mEntries.add(new WordSuggest.DataBean.EntriesBean(str[0], str[1]));
                    }
                    adapter = new WordSuggestAdapter(getContext(), mEntries, false);
                    deleteItem();
                    flag = 0;
                    lvSearchList.setAdapter(adapter);
                    lvSearchList.setSelection(0);
                }
                return false;
            }
        });

        lvSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mEntries.get(position).getEntry().equals("查无此词！")) {
                    Intent intent = new Intent(getContext(), WordSuggestDetailActivity.class);
                    if (flag == 1) {
                        intent.putExtra("Word", mEntries.get(position).getEntry());
                        spHistoryStorage.save(mEntries.get(position).getEntry() + '-' + mEntries.get(position).getExplain());
                    } else {
                        mHistoryList = spHistoryStorage.sortHistory();
                        String[] str = new String[2];
                        str = TextUtils.split(mHistoryList.get(position).getContent(), ",|\\-");
                        intent.putExtra("Word", str[0]);
                    }
                    getActivity().startActivity(intent);
                }
            }
        });
        return view;
    }

    public void deleteItem() {
        adapter.setOnItemDeleteClickListener(new WordSuggestAdapter.OnItemDeleteListener() {
            @Override
            public void onDeleteClick(int i) {
                Toast.makeText(getContext(), "ClickButton", Toast.LENGTH_SHORT).show();
                spHistoryStorage.remove(mHistoryList.get(i).getTime());
                mEntries.remove(i);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void requestWordSuggest(final String word) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dict.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<WordSuggest> call = request.getWordSuggestCall(word);

        call.enqueue(new Callback<WordSuggest>() {
            @Override
            public void onResponse(Call<WordSuggest> call, final Response<WordSuggest> response) {
                showWordSuggest(response.body());
            }

            @Override
            public void onFailure(Call<WordSuggest> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showWordSuggest(WordSuggest wordSuggest) {
        mEntries.clear();
        if (!wordSuggest.getResult().getMsg().equals("success")) {
            mEntries.add(new WordSuggest.DataBean.EntriesBean("查无此词！", ""));
        } else {
            for (WordSuggest.DataBean.EntriesBean entriesBean : wordSuggest.getData().getEntries()) {
                mEntries.add(new WordSuggest.DataBean.EntriesBean(entriesBean.getEntry(), entriesBean.getExplain()));
            }
        }
        adapter = new WordSuggestAdapter(getContext(), mEntries, true);
        deleteItem();
        // adapter.notifyDataSetChanged();
        lvSearchList.setAdapter(adapter);
        lvSearchList.setSelection(0);
    }

    /**
     * 通过反射设置mTextFilter属性达到隐藏弹框
     *
     * @param listView ListView实例
     */
    private void changeSearch(ListView listView) {
        try {
            Field field = listView.getClass().getSuperclass().getDeclaredField("mTextFilter");
            field.setAccessible(true);
            EditText searchAutoComplete = (EditText) field.get(listView);
            searchAutoComplete.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

