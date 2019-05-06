package com.example.lenovo.englishstudy;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenovo.englishstudy.chatlist.ClientThread;
import com.example.lenovo.englishstudy.chatlist.Msg;
import com.example.lenovo.englishstudy.chatlist.MsgAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {

    private List<Msg> list = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private Handler handler;
    private ClientThread clientThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Intent intent = getIntent();
        String ID = intent.getStringExtra("id");
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        //调用构造方法创造实例，参数消息集合
        adapter = new MsgAdapter(list);
        msgRecyclerView.setAdapter(adapter);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==0){
                    Msg msg1 = new Msg(msg.obj.toString(),Msg.TYPE_RECEIVED);
                    list.add(msg1);
                    //当有新消息时，刷新RecyclerView中的显示
                    adapter.notifyItemInserted(list.size()-1);
                    msgRecyclerView.scrollToPosition(list.size()-1);
                }
            }
        };
        clientThread = new ClientThread(handler);
        new Thread(clientThread).start();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!"".equals(content)&&content!=null){
                    Msg msg = new Msg(content,Msg.TYPE_SEND);
                    list.add(msg);
                    Message message = new Message();
                    message.what = 1;
                    message.obj = content;
                    clientThread.receiveHandler.sendMessage(message);
                    adapter.notifyItemInserted(list.size()-1);//有新消息刷新显示
                    msgRecyclerView.scrollToPosition(list.size()-1);//将RecyclerView定义到最后一行
                    inputText.setText("");//清空输入框
                }
            }
        });
    }
}
