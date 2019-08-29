package com.huazai.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // 定义一个变量，存放服务端WebContent目录的绝对路劲
    public static String WEB_ROOT = System.getProperty("user.dir") + "\\WebContent";

    // 定义一个变量，用于存放本次请求的静态页面名称
    private static String url = "";

    public static void main(String[] args) throws Exception {
//        System.out.println(WEB_ROOT);
        ServerSocket serverSocket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // 1.建立serverSocket监听8080端口
            serverSocket = new ServerSocket(8080);
            while (true) {
                Socket socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                // 2.获取HTTP协议的请求部分，截取客户端要访问的资源名称，将这个资源名称赋值给url变量
                parse(inputStream);

                // 3.发送资源
                sendStaticResource(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    /**
     * 获取HTTP协议的请求部分，截取客户端要访问的资源名称，将这个资源名称赋值给url变量
     *
     * @param inputStream
     */
    private static void parse(InputStream inputStream) throws IOException {
        StringBuilder content = new StringBuilder(2048);
        byte[] bytes = new byte[2048];

        /*
            此种方式导致程序断开连接，异常信息：java.net.SocketException: Software caused connection abort: socket write error
            while (inputStream.read(bytes) != -1) {
                content.append(new String(bytes));
            }
         */

        int len = -1;
        len = inputStream.read(bytes);
        for (int i = 0; i < len; i++) {
            content.append((char) bytes[i]);
        }
        System.out.println(content);
        parseUrl(content.toString());
    }

    /**
     * 解析客户端请求的资源路径，解析内容的格式：GET /index.html HTTP/1.1
     *
     * @param content
     */
    private static void parseUrl(String content) {
        // 第一个空格的索引
        int index0;
        // 第二个空格的索引
        int index1;
        index0 = content.indexOf(" ");
        if (index0 != -1) {
            index1 = content.indexOf(" ", index0 + 1);
            if (index1 > index0) {
                url = content.substring(index0 + 1, index1);
            }
        }
        System.out.println(url);
    }

    /**
     * 发送资源
     *
     * @param outputStream
     */
    private static void sendStaticResource(OutputStream outputStream) {
        byte[] bytes = new byte[2048];
        FileInputStream fileInputStream = null;
        try {
            File file = new File(WEB_ROOT, url);
            // 请求的资源文件存在
            if (file.exists()) {
                outputStream.write("HTTP/1.1 200 OK\n".getBytes());
                outputStream.write("Server:apache-Coyote/1.1\n".getBytes());
                outputStream.write("Content-Type:text/html;charset=utf-8\n".getBytes());
                outputStream.write("\n".getBytes());

                fileInputStream = new FileInputStream(file);
                int len = -1;
                while ((len = fileInputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
            } else {    // 请求的资源文件不存在
                outputStream.write("HTTP/1.1 404 NOT FOUND \n".getBytes());
                outputStream.write("Server:apache-Coyote/1.1\n".getBytes());
                outputStream.write("Content-Type:text/html;charset=utf-8\n".getBytes());
                outputStream.write("\n".getBytes());
                outputStream.write((url + " NOT FOUND").getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}
