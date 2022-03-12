package com.myproject.controller.listener;


import com.myproject.command.util.GeneralConstant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener("/*")
public class SessionListener implements HttpSessionListener {
    private static final Logger logger = LogManager.getLogger(SessionListener.class);
    @Override
    public void sessionCreated(HttpSessionEvent session) {
       // boolean user = session.getSession().getAttribute(GeneralConstant.ROLE).equals("user");
        System.out.println("\nSession working!!!");
        session.getSession().setMaxInactiveInterval(1200);
        logger.info("time session is set");

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent session) {
        Object userName = session.getSession().getAttribute("userName");
        if (userName!= null){
            session.getSession().setAttribute("role",null);
            session.getSession().setAttribute("userName",null);
            logger.info("Time out for session");
        }

    }
}
