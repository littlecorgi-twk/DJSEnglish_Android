package com.example.lenovo.englishstudy.chatlist;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ClientThread implements Runnable{

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    public Handler receiveHandler;
    private Handler sendHandler;


    public ClientThread(Handler handler){
        this.sendHandler = handler;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        try {
            socket = new Socket("192.168.43.171", 8888);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            //接受子线程
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Msg msg1;
                    //接收消息
                    try {
                        while (null != (msg1 = (Msg) ois.readObject())) {
                            String content = msg1.getContent();
                            Message message = new Message();
                            message.what = 0;
                            message.obj = content;
                            sendHandler.sendMessage(message);
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

            Looper.prepare();

            receiveHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        try {
                            Msg msg1 = new Msg((String) msg.obj, Msg.TYPE_SEND);
                            oos.writeObject(msg1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Looper.loop();
        }catch (SocketTimeoutException e) {
            System.out.println("网络连接请求超时！！！");
        }catch (SocketException e) {
            System.out.println("连接服务器失败！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
