package com.huazai.servlet;

import java.io.InputStream;
import java.io.OutputStream;

public class BBServlet implements Servlet {
    @Override
    public void init() {
        System.out.println("BBServlet init....");
    }

    @Override
    public void service(InputStream inputStream, OutputStream outputStream) throws Exception {
        outputStream.write("BBServlet service....".getBytes());
        System.out.println("BBServlet service....");
    }

    @Override
    public void destroy() {
        System.out.println("BBServlet destroy....");
    }
}
