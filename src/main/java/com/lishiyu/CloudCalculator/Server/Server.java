package com.lishiyu.CloudCalculator.Server;

import com.lishiyu.CloudCalculator.Common.Utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Utils.debug("serverSocket listening");

        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();
                Utils.debug("new clientSocket connected");

                ServerThread serverThread = new ServerThread(clientSocket);
                serverThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void halt() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
