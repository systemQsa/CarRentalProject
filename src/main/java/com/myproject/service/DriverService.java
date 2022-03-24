package com.myproject.service;

import com.myproject.exception.ServiceException;

public interface DriverService {

    boolean changeDriverPrice(int newPrice) throws ServiceException;
    double getDriverRentalPrice() throws ServiceException;
}
