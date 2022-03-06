package com.myproject.factory;

public interface Factory {
    DaoFactory getDaoFactory();
    ServiceFactory getServiceFactory();
}
