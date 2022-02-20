package com.myproject.command;

import com.myproject.command.util.GeneralConstant;
import com.myproject.command.util.Route;
import com.myproject.controller.filter.LocaleStorage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class ChangeLocaleCommand implements Command{
    private static final Logger logger = LogManager.getLogger(ChangeLocaleCommand.class);
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter(GeneralConstant.LANGUAGE);
        String wantedLang = LocaleStorage.getLocaleFromLanguage(language).getLanguage();
        Locale locale = LocaleStorage.getLocaleFromLanguage(language).getLocale();

        Cookie cookie = new Cookie(GeneralConstant.LANG,wantedLang);
        cookie.setMaxAge(60*60*60);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);

        request.getSession().setAttribute(GeneralConstant.LANG,locale);
        response.setHeader(GeneralConstant.CACHE_CONTROL, GeneralConstant.NO_STORE_MUST_REVALIDATE);

        Route route = new Route();
        route.setPathOfThePage(getRedirectPage(request));
        route.setRoute(Route.RouteType.REDIRECT);

        return route;
    }
    private String getRedirectPage(HttpServletRequest request) {
        String redirectPage = request.getParameter("currentParameters");

        if (!isBlank(redirectPage)) {
             return request.getContextPath() + request.getServletPath() + "?" + redirectPage;
        }
        String path = request.getParameter("currentPageAbsoluteURL");
        if (path.contains(GeneralConstant.WEB_INF)){
            path = path.replaceAll(GeneralConstant.CHANGE_LOCALE_REGEX_WEB_INF,GeneralConstant.EMPTY_STRING);
        }else{
            path = path.replaceAll(GeneralConstant.CHANGE_LOCALE_REGEX_URL_CAR,GeneralConstant.EMPTY_STRING);
        }
        logger.info("LANGUAGE WAS CHANGED");
        return GeneralConstant.REDIRECT + path;
    }

    public  boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}