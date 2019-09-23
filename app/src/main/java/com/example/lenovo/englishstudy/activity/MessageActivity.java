package com.example.lenovo.englishstudy.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.ImageMessage;
import com.example.lenovo.englishstudy.bean.MessageVerify;
import com.example.lenovo.englishstudy.bean.UserMessage;
import com.example.lenovo.englishstudy.userdefined.MyView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageActivity extends AppCompatActivity implements MyView.OnRootClickListener {
    private LinearLayout oneItem;
    private String user_name, name;
    private String user_photo;
    private ImageView back_button;
    //   private CircleImageView imageView;
    private int SET_REQUEST_CODE = 4;
    private String sex1, stage1;
    private MyView myView1, myView2, myView3, myView4, myView5;
    private TimePickerView pickerView;
    private int choose = 0;
    private String time;
    private TextView finish;
    private String token ;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_message);
        oneItem = findViewById(R.id.m_one_item);
        back_button = findViewById(R.id.m_back);
        finish = findViewById(R.id.message_finish);
        user_name = getIntent().getStringExtra("u_name");
        user_photo = getIntent().getStringExtra("u_photo");
        SharedPreferences sharedPreferences = getSharedPreferences("user_token", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        getMessage(token);
    }

    public void initView() {
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myView1 = new MyView(this)
                .initMine2("头像", user_photo, true)
                .setOnRootClickListener(this, 1);
        oneItem.addView(myView1);
        myView2 = new MyView(this)
                .initMine("昵称", name, true)
                .setOnRootClickListener(this, 2);
        oneItem.addView(myView2);
        myView3 = new MyView(this)
                .initMine("性别", sex1, true)
                .setOnRootClickListener(this, 3);
        oneItem.addView(myView3);
        myView4 = new MyView(this)
                .initMine("生日", "", true)
                .setOnRootClickListener(this, 4);
        oneItem.addView(myView4);
        myView5 = new MyView(this)
                .initMine("学习阶段", stage1, false)
                .setOnRootClickListener(this, 5);
        oneItem.addView(myView5);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMessage(name,"",sex1,stage1,token);
                SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user_name", name);
                editor.commit();
                imageUrl = sharedPreferences.getString("user_photo", "");
                Intent intent = new Intent();
                if(imageUrl!="") {
                    intent.putExtra("user_photo", imageUrl);
                }else{
                    intent.putExtra("user_photo", user_photo);
                }
                intent.putExtra("user_name", name);
                setResult(RESULT_OK, intent);
                finish();
            }

        });
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
                Intent intent = new Intent(MessageActivity.this, SetNameActivity.class);
                startActivityForResult(intent, 4);
                break;
            case 3:
                AlertDialog.Builder builder = new AlertDialog.Builder(MessageActivity.this);
                builder.setTitle("请选择性别");
                final String[] sex = {"男", "女"};
                //    设置一个单项选择下拉框
                /**
                 * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                 * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
                 * 第三个参数给每一个单选项绑定一个监听器
                 */
                builder.setSingleChoiceItems(sex, choose, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        choose = which;
                        dialog.dismiss();
                        sex1 = sex[which];
                        myView3.setRightText(sex1);
                        oneItem.removeViewAt(2);
                        oneItem.addView(myView3,2);
                    }
                });
                builder.show();

                //    linearLayout.removeViewAt(2);
                //    textView.setText(sex1);
                break;
            case 4:
                Calendar calendar = Calendar.getInstance();
                pickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        time = getTime(date);
                        myView4.setRightText(time);
                        oneItem.removeViewAt(3);
                        oneItem.addView(myView4, 3);
                    }
                })
                        .setRange(1900, calendar.get(Calendar.YEAR))
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setLabel("年", "月", "日", "", "", "")
                        .isCenterLabel(true)
                        .build();
                myView4.setRightText(time);
//                oneItem.removeViewAt(3);
//                oneItem.addView(myView4, 3);
                oneItem.invalidate();
                pickerView.show();
                break;

            case 5:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MessageActivity.this);
                builder2.setTitle("请选择学习阶段");
                final String[] stage = {"高考", "四级", "六级", "考研", "托福/雅思"};
                //    设置一个单项选择下拉框
                /**
                 * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                 * 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女' 会被勾选上
                 * 第三个参数给每一个单选项绑定一个监听器
                 */
                builder2.setSingleChoiceItems(stage, choose, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        choose = which;
                        dialog.dismiss();
                        stage1 = stage[which];
                        myView5.setRightText(stage1);
                        oneItem.removeViewAt(4);
                        oneItem.addView(myView5,4);
                    }
                });
                builder2.show();

                //    linearLayout.removeViewAt(2);
                //    textView.setText(sex1);
                break;


        }
    }

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
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
                        myView1.setRightIcon(image);
                        oneItem.removeViewAt(0);
                        oneItem.addView(myView1,0);
                        File file = compressImage(image);
                        String filePath = file.getPath();
                        uploadImage(filePath, token);
                    }
                    break;
                case 4:
                    Log.d("787878", "2");
                    name = data.getStringExtra("userName");
                    myView2.setRightText(name);
                    oneItem.removeViewAt(1);
                    oneItem.addView(myView2, 1);
                    break;

            }
        }
    }

    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
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
        //    recycleBitmap(bitmap);
        return file;
    }

    public void getMessage(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<UserMessage> call = request.getMessageCall(token);
        call.enqueue(new Callback<UserMessage>() {
            @Override
            public void onResponse(Call<UserMessage> call, Response<UserMessage> response) {
                final UserMessage userMessage = response.body();
                if(userMessage != null) {
                    if(userMessage.getStatus() == 0) {
                        name = userMessage.getData().getName();
                        stage1 = userMessage.getData().getStage();
                        sex1 = userMessage.getData().getSex();
                        initView();
                        Log.d("222222", name+" "+stage1+" "+sex1);
                    } else if(userMessage.getStatus() == 1) {
                        Toast.makeText(MessageActivity.this, "用户未登录, 无法获取当前用户信息", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserMessage> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MessageActivity.this, "获取用户信息失败", Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void uploadImage(String filePath, String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload_file", file.getName(), requestFile);
        Call<ImageMessage> call = request.upload(body, token);
        call.enqueue(new Callback<ImageMessage>() {
            @Override
            public void onResponse(Call<ImageMessage> call, Response<ImageMessage> response) {
                final ImageMessage imageMessage = response.body();
                Log.d("878787", "0");
                if (imageMessage != null) {
                    Log.d("878787", imageMessage.toString());
                    if (imageMessage.getStatus() == 0) {
                        Log.d("878787", "1");
                        Log.d("878787", imageMessage.getData().getUrl());
                        imageUrl = imageMessage.getData().getUrl();
                        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user_photo", imageUrl);
                        editor.commit();
                        Toast.makeText(MessageActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    } else if (imageMessage.getStatus() == 1) {
                        Log.d("878787", "2");
                        Toast.makeText(MessageActivity.this, "用户未登录", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<ImageMessage> call, Throwable t) {
                t.printStackTrace();
                Log.d("878787","3");
                Toast.makeText(MessageActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateMessage(String name, String msg, String sex, String stage, String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<MessageVerify> call = request.updateMessageCall(name, msg, sex, stage, token);
        call.enqueue(new Callback<MessageVerify>() {
            @Override
            public void onResponse(Call<MessageVerify> call, Response<MessageVerify> response) {
                final MessageVerify message = response.body();
                if(message != null) {
                    if(message.getStatus() == 0 && message.getMsg().equals("更新信息成功")) {
                        Toast.makeText(MessageActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    } else if(message.getStatus() == 1 && message.getMsg().equals("更新失败")) {
                        Toast.makeText(MessageActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageVerify> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MessageActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}