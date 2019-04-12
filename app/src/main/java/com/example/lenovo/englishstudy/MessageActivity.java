package com.example.lenovo.englishstudy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.lenovo.englishstudy.userdefined.MyView;

import de.hdodenhof.circleimageview.CircleImageView;

import static org.litepal.LitePalApplication.getContext;

public class MessageActivity extends AppCompatActivity implements MyView.OnRootClickListener{
    private LinearLayout oneItem;
    private String user_name;
    private String user_photo;
    private ImageView back_button;
 //   private CircleImageView imageView;
    private int CROP_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        oneItem = findViewById(R.id.m_one_item);
        back_button = findViewById(R.id.m_back);
        user_name = getIntent().getStringExtra("u_name");
        user_photo = getIntent().getStringExtra("u_photo");
        initView();
    }

    public void initView() {
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        oneItem.addView(new MyView(this)
                .initMine2("头像", user_photo, true)
                .setOnRootClickListener(this, 1));
        oneItem.addView(new MyView(this)
                .initMine("昵称", user_name, true)
                .setOnRootClickListener(this, 2));
        oneItem.addView(new MyView(this)
                .initMine("性别", "女", true)
                .setOnRootClickListener(this, 3));
        oneItem.addView(new MyView(this)
                .initMine("生日", "", true)
                .setOnRootClickListener(this, 4));

    }

    @Override
    public void onRootClick(View view) {
        switch ((int) view.getTag()) {
            case 1:
                Log.d("77777","1");
                if (ContextCompat.checkSelfPermission(MessageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MessageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    choosePhoto();
                }
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
        }
    }

    private void choosePhoto(){
        finish();
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case 1:
                    if(null != data.getData()) {
                       Uri localUri = data.getData();
                       cropPhoto(localUri);
                    }
                    break;

                case 3:
                    Bundle bundle = data.getExtras();
                    if(bundle != null) {
                        Bitmap image = bundle.getParcelable("data");
      //                  oneItem.removeViewAt(1);
//                        View view = oneItem.getChildAt(1);


                    }
                    break;

            }
        }
    }

    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", "true");

        startActivityForResult(intent, 3);
    }
}
