package com.example.lenovo.englishstudy.activity;

import android.content.pm.ActivityInfo;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.MessageVerify;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindPasswordActivity extends AppCompatActivity {
    private EditText e_phoneNumber, e_password, e_verify_password, e_messageVerify;
    private Button changePassword;
    private ImageButton back;
    private ToggleButton f_toggleButton, v_toggleButton;
    private TextView send_messageVerify;
    private Boolean flag, flag2 = true, flag3 = false;
    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_find_password);
        changePassword = findViewById(R.id.button_change_password);
        e_phoneNumber = findViewById(R.id.f_phoneNumber);
        e_password = findViewById(R.id.f_password);
        e_verify_password = findViewById(R.id.verify_password);
        e_messageVerify = findViewById(R.id.f_message_verify);
        f_toggleButton = findViewById(R.id.f_toggle_button);
        v_toggleButton = findViewById(R.id.v_toggle_button);
        send_messageVerify = findViewById(R.id.f_send_verify);
        back = findViewById(R.id.f_back);
        time = new TimeCount(60000, 1000);
        initView();

    }

    public void initView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        send_messageVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestMessageVerify(e_phoneNumber.getText().toString());
                time.start();
            }
        });
        f_toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        v_toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    e_verify_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    e_verify_password.setSelection(e_verify_password.getText().toString().length());
                } else {
                    e_verify_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    e_verify_password.setSelection(e_verify_password.getText().toString().length());
                }
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               VerifyPassword();
            }
        });
    }

    public void requestForgetPassword(final String phoneNumber, final String msgCode, final String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<MessageVerify> call = request.forgetPasswordCall(phoneNumber, msgCode, password);
        call.enqueue(new Callback<MessageVerify>() {
            @Override
            public void onResponse(Call<MessageVerify> call, Response<MessageVerify> response) {
                final MessageVerify messageVerify = response.body();
                if(messageVerify != null) {
                    if(messageVerify.getStatus() == 0 && messageVerify.getMsg().equals("修改成功")) {
                        Toast.makeText(FindPasswordActivity.this, messageVerify.getMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else if(messageVerify.getStatus() == 1) {
                        Toast.makeText(FindPasswordActivity.this, messageVerify.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageVerify> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(FindPasswordActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(FindPasswordActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MessageVerify> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(FindPasswordActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
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
            if(e_password.getText().toString().equals(e_verify_password.getText().toString())) {
                requestForgetPassword(e_phoneNumber.getText().toString(), e_messageVerify.getText().toString(), e_password.getText().toString());
            } else {
                Toast.makeText(FindPasswordActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(FindPasswordActivity.this, "密码为6~20位的数字字母组合", Toast.LENGTH_SHORT).show();
            flag3 = true;
        }
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            send_messageVerify.setTextColor(Color.parseColor("#252525"));
            send_messageVerify.setClickable(false);
            send_messageVerify.setText(millisUntilFinished / 1000 + " 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            send_messageVerify.setText("重新获取验证码");
            send_messageVerify.setClickable(true);
            send_messageVerify.setTextColor(Color.parseColor("#FFAB40"));
        }
    }
}
