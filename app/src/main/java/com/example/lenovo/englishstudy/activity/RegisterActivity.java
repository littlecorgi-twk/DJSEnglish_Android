package com.example.lenovo.englishstudy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.MessageVerify;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private ImageView r_back;
    private TextView send_verify;
    private EditText e_phoneNumber, e_messageVerify, e_password;
    private Boolean flag, flag2 = true, flag3 = false;
    private TimeCount time;
    private Button register;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.button_register);
        e_password = findViewById(R.id.e_password);
        toggleButton = findViewById(R.id.r_toggle_button);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    e_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    e_password.setSelection(e_password.getText().toString().length());
                } else {
                    e_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    e_password.setSelection(e_password.getText().toString().length());
                }
            }
        });

        e_messageVerify = findViewById(R.id.message_verify);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyPassword();

            }
        });
        r_back = findViewById(R.id.r_back);
        send_verify = findViewById(R.id.send_verify);
        e_phoneNumber = findViewById(R.id.phoneNumber);
        r_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        time = new TimeCount(60000, 1000);
        send_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    Toast.makeText(RegisterActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void requestRegister(final String phone, final String password, final String msgCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<MessageVerify> call = request.getRegisterCall(phone, password, msgCode);
        call.enqueue(new retrofit2.Callback<MessageVerify>() {
            @Override
            public void onResponse(Call<MessageVerify> call, retrofit2.Response<MessageVerify> response) {
                final MessageVerify messageVerify = response.body();
                Log.d("0000", messageVerify.toString());
                if (messageVerify != null) {
                    if (messageVerify.getStatus() == 0 && messageVerify.getMsg().equals("注册成功")) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (!flag3 && messageVerify.getStatus() == 1) {
                        Log.d("00000","3");
                        Toast.makeText(RegisterActivity.this, messageVerify.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageVerify> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(RegisterActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MessageVerify> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RegisterActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void VerifyPassword() {
        flag2 = true;
        flag3 = false;
        String password = e_password.getText().toString();
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) < '0' || (password.charAt(i) > '9' && password.charAt(i) < 'A') || (password.charAt(i) > 'Z' && password.charAt(i) < 'a') || password.charAt(i) > 'z') {
                flag2 = false;
                break;
            }
        }
        if (password.length() >= 6 && password.length() <= 20 && flag2) {
            Log.d("000000", e_messageVerify.getText().toString());
            requestRegister(e_phoneNumber.getText().toString(), password, e_messageVerify.getText().toString());
        } else {
            Toast.makeText(RegisterActivity.this, "密码为6~20位的数字字母组合", Toast.LENGTH_SHORT).show();
            flag3 = true;
        }
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            send_verify.setTextColor(Color.parseColor("#252525"));
            send_verify.setClickable(false);
            send_verify.setText(millisUntilFinished / 1000 + " 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            send_verify.setText("重新获取验证码");
            send_verify.setClickable(true);
            send_verify.setTextColor(Color.parseColor("#FFAB40"));
        }
    }


}
