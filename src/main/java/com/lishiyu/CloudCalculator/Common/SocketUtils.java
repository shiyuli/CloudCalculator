package com.lishiyu.CloudCalculator.Common;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * A static util class for Socket communication.
 * @author lishiyu
 */
public class SocketUtils {

    private SocketUtils() {}

    /**
     * Read message from target Socket.
     * @param target target Socket
     * @return String[]
    */
    public static String[] readMessage(Socket target) throws IOException {
        List<String> messageList = new ArrayList<String>();
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            is = target.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            String message;
            while((message = br.readLine()) != null) {
                messageList.add(message);
            }
        } catch(IOException e) {
            throw e;
        } finally {
            if(br != null) br.close();
            if(isr != null) isr.close();
            if(is != null) is.close();
        }

        return (String[])messageList.toArray();
    }

    /**
     * Send message to target Socket.
     * @param message message to send
     * @param target target Socket
     */
    public static void sendMessage(String message, Socket target) throws IOException {
        OutputStream os = null;
        PrintWriter pw = null;

        try {
            os = target.getOutputStream();
            pw = new PrintWriter(os); //output Stream --> print Stream(将输出流包装为打印流)
            pw.write(message);
            pw.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            if(pw != null) pw.close();
            if(os != null) os.close();
        }
    }

}
