package com.example.lenovo.englishstudy.chatlist;

public interface SocketListener {
    void onMessageArrived(String msg);
    void onConnecting();
    void onOpened();
    void onClosed();
}
