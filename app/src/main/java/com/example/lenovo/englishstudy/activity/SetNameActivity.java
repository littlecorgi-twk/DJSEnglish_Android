package com.example.lenovo.englishstudy.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.MessageVerify;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetNameActivity extends AppCompatActivity {
    private ImageView back;
    private EditText user_name;
    private Button change_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_set_name);
        back = findViewById(R.id.set_back);
        user_name = findViewById(R.id.e_set_name);
        change_button = findViewById(R.id.b_set_name);
        initView();
    }

    public void initView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("787878", "1");
                requestUserName(user_name.getText().toString());
            }
        });
    }

    public void requestUserName(final String userName) {
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.zhangshuo.fun/user/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<MessageVerify> call = request.getUserNameCall(userName);
        call.enqueue(new Callback<MessageVerify>() {
            @Override
            public void onResponse(Call<MessageVerify> call, Response<MessageVerify> response) {
                final MessageVerify messageVerify = response.body();
                if(messageVerify != null) {
                    if(messageVerify.getStatus() == 0 && messageVerify.getMsg().equals("昵称可用")) {
                        Toast.makeText(SetNameActivity.this, "昵称修改成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("userName", userName);
                        Log.d("787878", "1");
                        setResult(RESULT_OK, intent);
                        finish();
                    }else if(messageVerify.getStatus() == 1 && messageVerify.getMsg().equals("昵称重复")) {
                        Toast.makeText(SetNameActivity.this, messageVerify.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageVerify> call, Throwable t) {
                t.printStackTrace();
                Log.d("787878", t.toString());
                Toast.makeText(SetNameActivity.this, "昵称查重失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
