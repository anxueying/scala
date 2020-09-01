package com.atguigu.java.Charpter1;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Shelly An
 * @create 2020/7/24 9:46
 */
public class Test05_Net_Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4701);

        //output
        socket.getOutputStream().write(10);
        socket.close();
    }
}
