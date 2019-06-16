package com.example.lenovo.englishstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PhoneActivity extends AppCompatActivity {
    private EditText e_phoneNumber, e_messageVerify;
    private TextView send_message;
    private ImageView p_back;
    private Button login;

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
        p_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
