package com.example.lenovo.englishstudy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.MessageVerify;
import com.example.lenovo.englishstudy.bean.QQLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhoneActivity extends AppCompatActivity {
    private EditText e_phoneNumber, e_messageVerify;
    private TextView send_message;
    private ImageView p_back;
    private Button login;
    private TimeCount time;
    private Boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        e_phoneNumber = findViewById(R.id.p_phone);
        e_messageVerify = findViewById(R.id.p_message_verify);
        send_message = findViewById(R.id.p_send_verify);
        p_back = findViewById(R.id.p_cancel);
        login = findViewById(R.id.p_button_login);

        initView();
    }

    private void initView() {
        time = new TimeCount(60000, 1000);
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                String number = e_phoneNumber.getText().toString();
                for (int i = 0; i < number.length(); i++) {
                    if (number.charAt(i) < '0' || number.charAt(i) > '9') {
                        flag = false;
                        break;
                    }
                }
                if (number.length() == 11 && number.charAt(0) == '1' && flag) {
                    requestMessageVerify(e_phoneNumber.getText().toString());
                    time.start();
                } else {
                    Toast.makeText(PhoneActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                String name = intent.getStringExtra("name");
                String photo = intent.getStringExtra("photo");
                requestQQMessage(id, name, photo, e_phoneNumber.getText().toString(), e_messageVerify.getText().toString());
            }
        });
        p_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void requestMessageVerify(final String phoneNumber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<MessageVerify> call = request.getMessageVerifyCall(phoneNumber);
        call.enqueue(new retrofit2.Callback<MessageVerify>() {
            @Override
            public void onResponse(Call<MessageVerify> call, retrofit2.Response<MessageVerify> response) {
                final MessageVerify messageVerify = response.body();
                if (messageVerify != null) {
                    if (messageVerify.getStatus() == 0 && messageVerify.getMsg().equals("发送成功")) {
                        Toast.makeText(PhoneActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MessageVerify> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PhoneActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestQQMessage(final String id, final String name, final String img, final String phoneNumber, final String msgCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<QQLogin> call = request.getQQMessageCall(id, name, img, phoneNumber, msgCode);
        call.enqueue(new Callback<QQLogin>() {
            @Override
            public void onResponse(Call<QQLogin> call, Response<QQLogin> response) {
                final QQLogin qqLogin = response.body();
                if(qqLogin.getStatus() == 0 && qqLogin.getMsg().equals("验证成功")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_name", qqLogin.getData().getData().getUser().getName());
                    editor.putString("user_photo", qqLogin.getData().getData().getUser().getImg());
                    editor.commit();

                    Log.d("909090", qqLogin.getData().getData().getUser().getName());
                    Log.d("909090", qqLogin.getData().getData().getUser().getImg());
                    Toast.makeText(PhoneActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else if(qqLogin.getStatus() == 1) {
                    Toast.makeText(PhoneActivity.this, qqLogin.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QQLogin> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PhoneActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            send_message.setTextColor(Color.parseColor("#252525"));
            send_message.setClickable(false);
            send_message.setText(millisUntilFinished / 1000 + " 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            send_message.setText("重新获取验证码");
            send_message.setClickable(true);
            send_message.setTextColor(Color.parseColor("#FFAB40"));
        }
    }
}
