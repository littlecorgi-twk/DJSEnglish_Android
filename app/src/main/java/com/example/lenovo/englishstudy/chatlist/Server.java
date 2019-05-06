package com.example.lenovo.englishstudy.chatlist;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        Vector<UserThread> vector = new Vector<>();
        ExecutorService es = Executors.newFixedThreadPool(5);
        try {
            ServerSocket server = new ServerSocket(8888);
            System.out.println("服务器已启动，正在等待连接...");
            while(true){
                Socket socket = server.accept();
                System.out.println("客户端"+ socket.getInetAddress().getHostAddress()+"已连接");
                UserThread userThread = new UserThread(socket,vector);
                es.execute(userThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static class UserThread implements Runnable{
        private Socket socket;
        private Vector<UserThread> vector;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        public UserThread() {
        }

        public UserThread(Socket socket, Vector<UserThread> vector) {
            this.socket = socket;
            this.vector = vector;
            vector.add(this);
        }

        // 定义读取客户端数据的方法
        public Object getContext() {
            try {
                return ois.readObject();
            } catch (IOException e) {
                vector.remove(this);
                System.out.println("ip为"+socket.getInetAddress() + "的客户退出了聊天室!");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        public void run() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                int n = 0;
                Msg message;

                while(true) {
                    if((message = (Msg) getContext())!=null) {
                        System.out.println(socket.getLocalAddress() + "+" + message.getContent());
                        for (UserThread ut : vector) {
                            if (ut.socket != socket) {
                                ut.oos.writeObject(message);
                            }
                        }
                    }else{
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
