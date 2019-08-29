package com.huazai.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 响应部分（响应行/响应头/响应体）
 * 响应行格式：HTTP协议及版本（一个空格）响应状态码(一个空格)响应状态码信息描述(一个回车)    ---》HTTP/1.1 200 OK\n
 * 响应头格式：请求头名称(冒号)请求头值                                                   ---》content-type: application/json; charset=utf-8\n
 * 响应头和响应体之间必须空出一行：\n
 * 响应体格式：响应头头和响应体之间必须空出一行                                           ---》
 */
public class ServerTest {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        OutputStream outputStream = null;
        try {
            // 1_创建ServerSocket对象，监听本机的8080端口号
            serverSocket = new ServerSocket(8080);
            if (true) {
                // 2_等待来自客户端的请求获取和客户端对应的Socket对象
                socket = serverSocket.accept();
                // 3_通过获取到的Socket对象获取到输出流对象
                outputStream = socket.getOutputStream();
                // 4_通过获取到的输出流对象将HTTP协议的响应部分发送到客户端
                // 响应行
                outputStream.write("HTTP/1.9 200 OK\n".getBytes());
                // 响应头
                outputStream.write("Content-Type:text/html;charset=UTF-8\n".getBytes());
                outputStream.write("Server:华仔服务器\n".getBytes());
                // 响应体,响应头和响应体之间有一行空行
                outputStream.write("\n".getBytes());
                outputStream.write("hello world".getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }


        // 5_释放资源
    }
}
