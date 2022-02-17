package com.myproject.command;

import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ControllerException;
import com.myproject.exception.DaoException;
import com.myproject.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException,ValidationException;
}
