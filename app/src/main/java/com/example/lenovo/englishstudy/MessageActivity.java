package com.example.lenovo.englishstudy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.ImageMessage;
import com.example.lenovo.englishstudy.userdefined.MyView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.litepal.LitePalApplication.getContext;

public class MessageActivity extends AppCompatActivity implements MyView.OnRootClickListener {
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

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    if (null != data.getData()) {
                        Uri localUri = data.getData();
                        cropPhoto(localUri);
                    }
                    break;

                case 3:
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap image = bundle.getParcelable("data");
                        oneItem.removeAllViews();
                        oneItem.addView(new MyView(getContext())
                                .initMine("头像", image, true)
                                .setOnRootClickListener(this, 1));
                        oneItem.addView(new MyView(getContext())
                                .initMine("昵称", user_name, true)
                                .setOnRootClickListener(this, 2));
                        oneItem.addView(new MyView(getContext())
                                .initMine("性别", "女", true)
                                .setOnRootClickListener(this, 3));
                        oneItem.addView(new MyView(getContext())
                                .initMine("生日", "", true)
                                .setOnRootClickListener(this, 4));
//                        View view = oneItem.getChildAt(1);
                        File file = compressImage(image);
                        uploadImage(file);


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
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 3);
    }


    /**
     * 压缩图片（质量压缩）
     *
     * @param bitmap
     */

    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(), filename + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }


    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps == null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }

    public void uploadImage(File file) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload_file", file.getName(), requestFile);
        Call<ImageMessage> call = request.upload(body);
        call.enqueue(new Callback<ImageMessage>() {
            @Override
            public void onResponse(Call<ImageMessage> call, Response<ImageMessage> response) {
                final ImageMessage imageMessage = response.body();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (imageMessage != null) {
                            if (imageMessage.getStatus() == 0) {
                                Toast.makeText(MessageActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<ImageMessage> call, Throwable t) {
                t.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MessageActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}
