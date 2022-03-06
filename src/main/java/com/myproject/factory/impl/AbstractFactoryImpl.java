package com.myproject.factory.impl;

import com.myproject.factory.AbstractFactory;
import com.myproject.factory.Factory;

public class AbstractFactoryImpl implements AbstractFactory {
    private static  Factory factory;

    @Override
    public  Factory getFactory() {
        if (factory == null){
            factory = new FactoryImpl();
        }
        return factory;
    }
}
