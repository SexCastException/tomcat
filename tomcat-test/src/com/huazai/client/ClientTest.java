package com.huazai.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 请求部分（请求行/请求头/请求体）
 * 请求行格式：请求方法(一个空格)请求路径(一个空格)HTTP协议及版本（一个换行）          ----》GET /xxx/xxx.html HTTP/1.1\n    请求路径：域名之后的那串
 * 请求头格式：请求头名称(冒号)请求头值                                            ----》HOST:www.baidu.com\n
 * 请求头和请求体之间必须空出一行：\n
 * 请求体格式：请求头和请求体之间必须空出一行，请求体内容可以为空，但是回车不能空     ----》
 */
public class ClientTest {
    public static void main(String[] args) {
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // 1_建立一个Socket对象,连接百度域名的80端口
            socket = new Socket("www.baidu.com", 80);
            // 2获取到输出流对象
            outputStream = socket.getOutputStream();
            // 3_获取到输入流对象
            inputStream = socket.getInputStream();
            // 4_将HTTP协议的请求部分发送到服务端 /newspage/data/landingsuper?context=%7B"nid"%3A"news_9426657133829062462"%7D&n_type=0&p_from=1
            // 请求行
            outputStream.write("GET /img/bd_logo1.png?where=super HTTP/1.1\n".getBytes());
            // 请求头
            outputStream.write("HOST:www.baidu.com\n".getBytes());
            // 请求体，虽然请求体为空，但是按照HTTP规范，换行也是必不可少
            outputStream.write("\n".getBytes());
            // 5_读取来自服务端的数据打印到控制台
            char c = (char) inputStream.read();
            while (c != -1) {
                System.out.print(c);
                c = (char) inputStream.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6_释放资源
            try {
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
                if (socket != null) {
                    socket.close();
                    socket = null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
