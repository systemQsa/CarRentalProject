package com.myproject.service;

import com.myproject.exception.ServiceException;

public interface DriverService {

    boolean changeDriverPrice(double newPrice) throws ServiceException;
    double getDriverRentalPrice() throws ServiceException;
}
