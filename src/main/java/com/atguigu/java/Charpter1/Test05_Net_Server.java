package com.atguigu.java.Charpter1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Shelly An
 * @create 2020/7/24 9:45
 */
public class Test05_Net_Server {
    public static void main(String[] args) throws IOException {
        //服务器
         //占用9999端口，等待访问
        final ServerSocket server = new ServerSocket(9999);
        while (true){
            System.out.println("等待客户端连接...");
            //接收客户端请求
            final Socket client = server.accept();
            //创建线程进程数据处理，有新的请求就创建一个线程进行处理，同时程序继续循环，等待下一个客户端请求
            new Thread(new Runnable() {
                public void run() {
                    try {
                        //此时client相当于客户端的socket 两者的方法是相反的
                        int data = client.getInputStream().read();
                        System.out.println(data);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
