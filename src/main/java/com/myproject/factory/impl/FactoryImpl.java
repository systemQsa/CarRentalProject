package com.myproject.factory.impl;

import com.myproject.factory.DaoFactory;
import com.myproject.factory.Factory;
import com.myproject.factory.ServiceFactory;

public class FactoryImpl implements Factory {
    @Override
    public DaoFactory getDaoFactory() {
        return new DaoFactoryImpl();
    }

    @Override
    public ServiceFactory getServiceFactory() {
        return new ServiceFactoryImpl();
    }
}
