package com.myproject.controller.filter;

import com.myproject.command.util.GeneralConstant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LangFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LangFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(GeneralConstant.CACHE_CONTROL, GeneralConstant.NO_STORE_MUST_REVALIDATE);
        response.setHeader(GeneralConstant.PRAGMA, GeneralConstant.NO_CACHE);
        response.setDateHeader(GeneralConstant.EXPIRES, 0);

        ResourceBundle bundle;
        if (request.getParameter(GeneralConstant.LANG) == null
                || Objects.equals(request.getParameter(GeneralConstant.LANG), GeneralConstant.LANGUAGE_ENG)) {
            bundle = ResourceBundle.getBundle(GeneralConstant.RESOURCES,
                    new Locale(GeneralConstant.LANGUAGE_ENG, GeneralConstant.COUNTRY_US));
            request.getSession().setAttribute(GeneralConstant.LANGUAGE, bundle);
        }

        if (Objects.equals(request.getParameter(GeneralConstant.LANG), GeneralConstant.LANGUAGE_UKR)) {
            bundle = ResourceBundle.getBundle(GeneralConstant.RESOURCES,
                    new Locale(GeneralConstant.LANGUAGE_UKR, GeneralConstant.COUNTRY_UA));
            request.getSession().setAttribute(GeneralConstant.LANGUAGE, bundle);
        }

        logger.info("LangFilter WORKING");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

