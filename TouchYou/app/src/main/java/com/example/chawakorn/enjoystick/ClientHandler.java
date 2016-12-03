package com.example.chawakorn.enjoystick;

/**
 * Created by winza on 03-Dec-16.
 */

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {

    String target_ip;
    int port;
    Socket server;
    DataOutputStream dos;

    public ClientHandler(String target_ip, int port) {
        this.target_ip = target_ip;
        this.port = port;
    }

    public void send(String text) throws IOException {
        dos.writeUTF(text);
        dos.flush();
    }


    @Override
    public void run() {
        Log.d("d", "runnign");
        try {
            server = new Socket(target_ip, port);
            server.setKeepAlive(true);
            dos = new DataOutputStream(server.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
