package com.example.lenovo.englishstudy;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.lenovo.englishstudy.Util.HttpUtil;
import com.example.lenovo.englishstudy.Util.Utility;
import com.example.lenovo.englishstudy.bean.MessageVerify;
import com.example.lenovo.englishstudy.bean.WordTranslate;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private ImageView r_back;
    private TextView send_verify;
    private EditText e_phoneNumber, e_messageVerify, e_password;
    private Boolean flag;
    private TimeCount time;
    private Button register;
    private ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.button_register);
        e_password = findViewById(R.id.e_password);
        toggleButton = findViewById(R.id.toggle_button);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
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
                requestVmessageVerify(e_phoneNumber.getText().toString(), e_messageVerify.getText().toString());
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
                for(int i = 0; i < number.length(); i++){
                    if(number.charAt(i)<'0'||number.charAt(i)>'9') {
                        flag = false;
                        break;
                    }
                }
                if(number.length() == 11 && number.charAt(0) == '1' && flag) {
                    Log.d("1234567",e_phoneNumber.getText().toString());
                    requestMessageVerify(e_phoneNumber.getText().toString());
                    time.start();
                } else {
                    Log.d("33223", "1");
                    Toast.makeText(RegisterActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void requestMessageVerify(final String phoneNumber) {
        final String messageVerifyUrl = "http://47.102.206.19:8080/user/get_msgcode.do";
      //  MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody  requestBody = new FormBody.Builder().add("phoneNumber",phoneNumber).build();
        //RequestBody requestBody = RequestBody.create(mediaType, phoneNumber);
        HttpUtil.sendHttpRequest(messageVerifyUrl, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final MessageVerify messageVerify = Utility.handleMessageVerifyResponse(responseText);
                Log.d("34567",messageVerify.getMsg());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (messageVerify != null) {
                            if (messageVerify.getStatus() == 0 && messageVerify.getMsg().equals("发送成功")) {
                                Toast.makeText(RegisterActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }

    public void requestVmessageVerify(final String phone, final String msgCode) {
        final String messageVerifyUrl = "http://47.102.206.19:8080/user/check_msg.do";
        //  MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody  requestBody = new FormBody.Builder().add("phone",phone).add("msgCode",msgCode).build();
        //RequestBody requestBody = RequestBody.create(mediaType, phoneNumber);
        HttpUtil.sendHttpRequest(messageVerifyUrl, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final MessageVerify messageVerify = Utility.handleMessageVerifyResponse(responseText);
                Log.d("34567",messageVerify.getMsg());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (messageVerify != null) {
                            if (messageVerify.getStatus() == 0 && messageVerify.getMsg().equals("验证码正确")) {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(RegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            send_verify.setTextColor(Color.parseColor("#252525"));
            send_verify.setClickable(false);
            send_verify.setText( millisUntilFinished / 1000 + " 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            send_verify.setText("重新获取验证码");
            send_verify.setClickable(true);
            send_verify.setTextColor(Color.parseColor("#FFAB40"));
        }
    }


}
