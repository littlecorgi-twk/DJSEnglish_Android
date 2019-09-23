package com.example.lenovo.englishstudy.chatlist;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class SocketService extends Service {

    private static String TAG = "SocketService1";
    private static WebSocketClient webSocketClient;
    private ServiceHandler serviceHandler = new ServiceHandler();
    private int ID;

    public SocketService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: 1");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: 1");
        ID = intent.getIntExtra("id", 0);
        Log.d("SocketService1", "onStartCommand: id=" + ID);
        connectToServer(null);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: 1");
        return new MyBinder();
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return super.bindService(service, conn, flags);
    }

    public class MyBinder extends Binder {
        public SocketService getService() {
            return SocketService.this;
        }
    }

    class ServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (socketListener != null) {
                socketListener.onMessageArrived((String) (msg.obj));
            }
        }
    }

    /**
     * 连接
     */
    int max = 5;
    int count = 0;

    private void connectToServer(final String str) {
        try {
            webSocketClient = new WebSocketClient(new URI("ws://www.zhangshuo.fun/chat/26")) {

                @Override
                public void onWebsocketHandshakeSentAsClient(WebSocket conn, ClientHandshake request) throws InvalidDataException {
                    Log.d(TAG, "onWebsocketHandshakeSentAsClient: 发送握手了");
                }

                @Override
                public void onWebsocketHandshakeReceivedAsClient(WebSocket conn, ClientHandshake request, ServerHandshake response) throws InvalidDataException {
                    super.onWebsocketHandshakeReceivedAsClient(conn, request, response);
                    Log.d(TAG, "onWebsocketHandshakeReceivedAsClient: 接受到握手了");
                }

                /**
                 * 连接开启时调用
                 */
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d(TAG, "onOpen: 1");
                }

                /**
                 * 接收到消息时调用
                 */
                @Override
                public void onMessage(String message) {
                    Message msg = Message.obtain();
                    msg.obj = message;
                    serviceHandler.sendMessage(msg);
                }

                /**
                 * 连接断开时调用
                 */
                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d(TAG, "onClose: 已经关闭" + code + " " + reason);
                    if (CloseFrame.ABNORMAL_CLOSE == code)
                        reConnect();
                }

                @Override
                public void onClosing(int code, String reason, boolean remote) {
                    Log.d(TAG, "onClosing: 关闭中");
                    super.onClosing(code, reason, remote);
                }

                /**
                 * 连接出错时调用
                 */
                @Override
                public void onError(Exception ex) {
                    Log.d(TAG, "onError: 连接失败");
                }
            };
            // 设置间隔检查
            webSocketClient.setConnectionLostTimeout(5000);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        webSocketClient.connect();
    }

    /**
     * 关闭
     */
    public void closeSocket() {
        if (webSocketClient != null) {
            webSocketClient.close();
        }
    }

    /**
     * 发送消息
     */
    public void sendMessage(String message) {
        if (webSocketClient != null && webSocketClient.isOpen()) {
            Log.d(TAG, "进来的觉得可以发送的:" + message);
            webSocketClient.send(message);
        } else {
            Log.d(TAG, "已经断线,需要重新。。。");
            reConnect();
        }
    }

    /**
     * 重新连接
     */
    private void reConnect() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (webSocketClient.isClosed() && !webSocketClient.reconnectBlocking()) {
                        Log.d(TAG, "第" + (count + 1) + "次重连");
                        count++;
                    }
                    if (count >= 5) {
                        timer.cancel();
                        count = 0;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2000);
    }

    private SocketListener socketListener = null;

    public void setSocketListener(SocketListener socketListener) {
        this.socketListener = socketListener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webSocketClient != null && webSocketClient.isOpen()) {
            webSocketClient.close();
            webSocketClient = null;
        }
    }
}
