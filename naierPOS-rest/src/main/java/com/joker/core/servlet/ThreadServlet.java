package com.joker.core.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by chenqi on 16/5/2.
 */
public class ThreadServlet extends HttpServlet {

    public ThreadServlet() {
        super();
    }

    @Override
    public void init(){
        ThreadPool.initPool();
    }

    @Override
    public void destroy() {
        super.destroy();
        ThreadPool.destory();
    }
}
