package com.myproject.controller;

import com.myproject.dao.entity.Car;
import com.myproject.dao.impl.CarDaoImpl;
import com.myproject.dao.impl.UserDao;
import com.myproject.exception.DaoException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        CarDaoImpl carDao = new CarDaoImpl();

        List<Car> all;
        try {
            all = carDao.findAll();
            resp.getWriter().println(all);
        } catch (DaoException e) {
            e.printStackTrace();
        }

//        Connection connection = ConnectionPool.getInstance().getConnection();
//        resp.getWriter().println(connection);
//        ConnectionPool.closeConnection(connection);
    }
}
