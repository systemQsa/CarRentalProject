package com.myproject.controller.filter;


import java.util.Locale;

public enum LocaleStorage {
    ENG(new Locale("en","US")),
    UKR(new Locale("uk", "UA"));

    private final Locale locale;

    LocaleStorage(Locale locale) {
        this.locale = locale;
    }

    public static LocaleStorage getLocaleFromLanguage(String language) {
        for (LocaleStorage currentLocale: LocaleStorage.values()) {
            if (currentLocale.locale.getLanguage().equals(language)) {
                return currentLocale;
            }
        }
        return ENG;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public Locale getLocale() {
        return locale;
    }
}