package com.huazai.servlet;

import java.io.InputStream;
import java.io.OutputStream;

public class AAServlet implements Servlet {
    @Override
    public void init() {
        System.out.println("AAServlet init....");
    }

    @Override
    public void service(InputStream inputStream, OutputStream outputStream) throws Exception {
        outputStream.write("AAServlet service....".getBytes());
        System.out.println("AAServlet service....");
    }

    @Override
    public void destroy() {
        System.out.println("AAServlet destroy....");
    }
}
