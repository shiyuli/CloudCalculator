package com.lishiyu.CloudCalculator.Server;

import com.lishiyu.CloudCalculator.Common.SocketUtils;
import com.lishiyu.CloudCalculator.Common.Utils;

import java.io.IOException;
import java.net.Socket;

/**
 * A class for server Thread processing.
 * @author lishiyu
 */
public class ServerThread extends Thread {

    private Socket clientSocket;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            String[] messages = SocketUtils.readMessage(clientSocket);
            for(String message: messages) {
                Utils.debug("message from clientSocket: " + message);
            }

            SocketUtils.sendMessage("server message", clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.shutdownInput();
            } catch (IOException e) {
                return;
            }
        }
    }

}
