package com.myproject.controller.filter;


import com.myproject.command.util.GeneralConstant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;


public class FilterLocale implements Filter {
    private static final Logger logger = LogManager.getLogger(FilterLocale.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
     }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(GeneralConstant.CACHE_CONTROL, GeneralConstant.NO_STORE_MUST_REVALIDATE);
        response.setHeader(GeneralConstant.PRAGMA, GeneralConstant.NO_CACHE);
        response.setDateHeader(GeneralConstant.EXPIRES, 0);
        setLocalization(request, response);
        request.setCharacterEncoding(GeneralConstant.ENCODING);
        logger.info("FilterLocale work");
        filterChain.doFilter(request, response);

    }


    private void setLocalization(HttpServletRequest request, HttpServletResponse response) {
        Object langAttribute = request.getSession().getAttribute(GeneralConstant.LANG);
        if (langAttribute == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                setLocaleTForCookieAndSession(request, response);
            } else {
                String cookieLang = null;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(GeneralConstant.LANG)) {
                        cookieLang = cookie.getValue();
                        request.getSession().setAttribute(GeneralConstant.LANG, cookieLang);
                    }
                }
                if (cookieLang == null) {
                    setLocaleTForCookieAndSession(request, response);
                }
            }
        }
    }


    private void setLocaleTForCookieAndSession(HttpServletRequest request, HttpServletResponse response) {
        String currentLang = request.getLocale().getLanguage();
        Locale resultLocale = LocaleStorage.getLocaleFromLanguage(currentLang).getLocale();
        Cookie langCookie = new Cookie(GeneralConstant.LANG, resultLocale.getLanguage());
        response.addCookie(langCookie);
        request.getSession().setAttribute(GeneralConstant.LANG, currentLang);

    }

    @Override
    public void destroy() {
     }
}