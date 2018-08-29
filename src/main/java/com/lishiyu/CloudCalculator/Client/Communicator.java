package com.lishiyu.CloudCalculator.Client;

import com.lishiyu.CloudCalculator.Common.SocketUtils;
import com.lishiyu.CloudCalculator.Common.Utils;

import java.io.*;
import java.net.Socket;

/**
 * A class that contains methods to communicate with server.
 * @author lishiyu
 */
public class Communicator {

    private Socket socket = null;

    public static Communicator instance;

    private Communicator() {
        connectServer();
    }

    public static Communicator getInstance() {
        if(instance == null) {
            instance = new Communicator();
        }

        return instance;
    }

    public String test(String message) {
        connectServer();

        sendMessage(message);
        String recvMessage = readMessage();

        disconnectServer();
        return recvMessage;
    }

    public void sendMessage(String message) {
        try {
            SocketUtils.sendMessage(message, socket);
        } catch (IOException e) {
            Utils.debug(e.getMessage());
        }
    }

    public String readMessage() {
        try {
            String[] messageList = SocketUtils.readMessage(socket);
            Utils.debug("messageList.length: " + String.valueOf(messageList.length));
            if(messageList.length == 1) {
                return messageList[0];
            }
        } catch (IOException e) {
            Utils.debug("Communicator.readMessage: " + e.getMessage());
        } finally {
//            disconnectServer();
        }

        return null;
    }

    private void connectServer() {
        try {
            socket = new Socket("localhost", 8080);
        } catch (IOException e) {
            Utils.debug(e.getMessage());
        }
    }

    private void disconnectServer() {
        try {
            socket.shutdownOutput();
            socket.close();
        } catch (IOException e) {
            Utils.debug(e.getMessage());
        } finally {
            socket = null;
        }
    }
}
