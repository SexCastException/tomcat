package com.huazai.servlet;

import java.io.InputStream;
import java.io.OutputStream;

public interface Servlet {
    /**
     * servlet初始化方法
     */
    void init();

    /**
     * servlet服务方法
     */
    void service(InputStream inputStream, OutputStream outputStream) throws Exception;

    /**
     * servlet销毁方法
     */
    void destroy();
}
