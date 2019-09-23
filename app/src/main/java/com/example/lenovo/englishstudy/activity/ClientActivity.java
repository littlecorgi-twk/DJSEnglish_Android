package com.example.lenovo.englishstudy.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.chatlist.Msg;
import com.example.lenovo.englishstudy.chatlist.MsgAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "ClientActivity1";
    private static WebSocket mWebSocket = null;
    private List<Msg> list = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private int userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_client);

        SharedPreferences sharedPreferences = getSharedPreferences("user_token", Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt("userID", 2);
        Log.d("ClientActivity_userID", userID + "");

        connectAndStart();
        findView();
        initRecyclerView();
    }

    private void findView() {
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        send.setOnClickListener(this);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        //调用构造方法创造实例，参数消息集合
        adapter = new MsgAdapter(list);
        msgRecyclerView.setAdapter(adapter);
    }

    private void connectAndStart() {
        String wsUrl = "ws://47.102.206.19:8080/chat/27";

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url(wsUrl)
                .build();

        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
                Log.d(TAG, "onClosed: " + "code:" + code + " reason:" + reason);
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
                Log.d(TAG, "onClosing: code:" + code + " reason:" + reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @org.jetbrains.annotations.Nullable Response response) {
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
                Log.d(TAG, "onMessage: " + text);
                // try {
                //     JSONObject jsonObject = new JSONObject(text);
                //     Message message = new Gson().fromJson(jsonObject.toString(), Message.class);
                //     Msg msg1 = new Msg(message.getText(), Msg.TYPE_RECEIVED);
                //     list.add(msg1);
                //     //当有新消息时，刷新RecyclerView中的显示
                //     adapter.notifyItemInserted(list.size() - 1);
                //     msgRecyclerView.scrollToPosition(list.size() - 1);
                // } catch (JSONException e) {
                //     e.printStackTrace();
                // }
                Msg msg1 = new Msg("1", Msg.TYPE_RECEIVED);
                list.add(msg1);
                //当有新消息时，刷新RecyclerView中的显示
                adapter.notifyItemInserted(list.size() - 1);
                msgRecyclerView.scrollToPosition(list.size() - 1);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                mWebSocket = webSocket;
                Log.d(TAG, "onOpen: client onOpen");
                Log.d(TAG, "client request header:" + response.request().headers());
                Log.d(TAG, "client response header:" + response.headers());
                Log.d(TAG, "client response:" + response);
            }
        });
        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send) {
            Log.d("ClientActivity1", "onClick: 0");
            if (mWebSocket != null) {
                String content = inputText.getText().toString();
                Log.d("ClientActivity1", "onClick: 1");
                if (!"".equals(content)) {
                    Log.d("ClientActivity1", "onClick: 2");
                    JSONObject jsonObject = new JSONObject();
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    list.add(msg);
                    try {
                        jsonObject.put("senderId", userID);
                        jsonObject.put("to", 27);
                        jsonObject.put("text", content);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("ClientActivity1", "onClick: 3");
                    final String result = jsonObject.toString();
                    mWebSocket.send(result);
                    adapter.notifyItemInserted(list.size() - 1);//有新消息刷新显示
                    msgRecyclerView.scrollToPosition(list.size() - 1);//将RecyclerView定义到最后一行
                    inputText.setText("");//清空输入框
                }
            }
        }
    }

    // /*****************************************************************
    //  * 通过service以及java-websocket来连接websocket
    //  *****************************************************************/
    //
    // private void connectAndStart() {
    //     Intent intent = new Intent(this, SocketService.class);
    //     Log.d("ClientActivity1", "connectAndStart: 1");
    //     startService(intent);
    //     intent.putExtra("id", 26);
    //     conn = new Connection();
    //     bindService(intent, conn, BIND_AUTO_CREATE);
    //     Log.d("ClientActivity1", "connectAndStart: 3");
    // }
    //
    // @Override
    // public void onClick(View v) {
    //     if (v.getId() == R.id.send) {
    //         Log.d("ClientActivity1", "onClick: 0");
    //         if (socketService != null) {
    //             String content = inputText.getText().toString();
    //             Log.d("ClientActivity1", "onClick: 1");
    //             if (!"".equals(content)) {
    //                 Log.d("ClientActivity1", "onClick: 2");
    //                 JSONObject jsonObject = new JSONObject();
    //                 Msg msg = new Msg(content, Msg.TYPE_SEND);
    //                 list.add(msg);
    //                 try {
    //                     jsonObject.put("senderId", userID);
    //                     jsonObject.put("to", 26);
    //                     jsonObject.put("text", content);
    //                 } catch (JSONException e) {
    //                     e.printStackTrace();
    //                 }
    //                 Log.d("ClientActivity1", "onClick: 3");
    //                 final String result = jsonObject.toString();
    //                 socketService.sendMessage(result);
    //                 adapter.notifyItemInserted(list.size() - 1);//有新消息刷新显示
    //                 msgRecyclerView.scrollToPosition(list.size() - 1);//将RecyclerView定义到最后一行
    //                 inputText.setText("");//清空输入框
    //             }
    //         }
    //     }
    // }
    //
    // private class Connection implements ServiceConnection {
    //     @Override
    //     public void onServiceConnected(ComponentName name, IBinder service) {
    //         Log.d("ClientActivity1", "onServiceConnected: 1");
    //         socketService = ((SocketService.MyBinder) service).getService();
    //         socketService.setSocketListener(new SocketListener() {
    //             @Override
    //             public void onMessageArrived(String msg) {
    //                 Log.d("ClientActivity1", "onMessageArrived: msg");
    //                 if (msg != null) {
    //                     try {
    //                         JSONObject jsonObject = new JSONObject(msg);
    //                         Message message = new Gson().fromJson(jsonObject.toString(), Message.class);
    //                         Msg msg1 = new Msg(message.getText(), Msg.TYPE_RECEIVED);
    //                         list.add(msg1);
    //                         //当有新消息时，刷新RecyclerView中的显示
    //                         adapter.notifyItemInserted(list.size() - 1);
    //                         msgRecyclerView.scrollToPosition(list.size() - 1);
    //                     } catch (JSONException e) {
    //                         e.printStackTrace();
    //                     }
    //                 }
    //             }
    //
    //             @Override
    //             public void onConnecting() {
    //                 Log.d("ClientActivity1", "onConnecting: 连接中。。。");
    //             }
    //
    //             @Override
    //             public void onOpened() {
    //                 Log.e("ClientActivity1", "onOpened: 处于打开状态");
    //             }
    //
    //             @Override
    //             public void onClosed() {
    //                 Log.e("ClientActivity1", "onClosed: 已经关闭");
    //             }
    //         });
    //     }
    //
    //     @Override
    //     public void onServiceDisconnected(ComponentName name) {
    //     }
    // }
    //
    // @Override
    // protected void onDestroy() {
    //     super.onDestroy();
    //     socketService.closeSocket();
    //     unbindService(conn);
    //     stopService(new Intent(this, SocketService.class));
    // }

    // /*****************************************************************
    //  * 连接本地服务器
    //  *****************************************************************/
    //
    // private List<Msg> list = new ArrayList<>();
    // private EditText inputText;
    // private Button send;
    // private RecyclerView msgRecyclerView;
    // private MsgAdapter adapter;
    // private Handler handler;
    // private ClientThread clientThread;
    //
    // @Override
    // protected void onCreate(Bundle savedInstanceState) {
    //     super.onCreate(savedInstanceState);
    //     setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    //     setContentView(R.layout.activity_client);
    //     Intent intent = getIntent();
    //     String ID = intent.getStringExtra("id");
    //     inputText = findViewById(R.id.input_text);
    //     send = findViewById(R.id.send);
    //     msgRecyclerView = findViewById(R.id.msg_recycler_view);
    //     LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    //     msgRecyclerView.setLayoutManager(layoutManager);
    //     //调用构造方法创造实例，参数消息集合
    //     adapter = new MsgAdapter(list);
    //     msgRecyclerView.setAdapter(adapter);
    //
    //     handler = new Handler() {
    //         @Override
    //         public void handleMessage(Message msg) {
    //             if (msg.what == 0) {
    //                 Msg msg1 = new Msg(msg.obj.toString(), Msg.TYPE_RECEIVED);
    //                 list.add(msg1);
    //                 //当有新消息时，刷新RecyclerView中的显示
    //                 adapter.notifyItemInserted(list.size() - 1);
    //                 msgRecyclerView.scrollToPosition(list.size() - 1);
    //             }
    //         }
    //     };
    //     clientThread = new ClientThread(handler);
    //     new Thread(clientThread).start();
    //     send.setOnClickListener(new View.OnClickListener() {
    //         @Override
    //         public void onClick(View view) {
    //             String content = inputText.getText().toString();
    //             if (!"".equals(content) && content != null) {
    //                 Msg msg = new Msg(content, Msg.TYPE_SEND);
    //                 list.add(msg);
    //                 Message message = new Message();
    //                 message.what = 1;
    //                 message.obj = content;
    //                 clientThread.receiveHandler.sendMessage(message);
    //                 adapter.notifyItemInserted(list.size() - 1);//有新消息刷新显示
    //                 msgRecyclerView.scrollToPosition(list.size() - 1);//将RecyclerView定义到最后一行
    //                 inputText.setText("");//清空输入框
    //             }
    //         }
    //     });
    // }
}
