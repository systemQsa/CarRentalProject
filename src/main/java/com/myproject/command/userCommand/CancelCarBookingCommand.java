package com.myproject.command.userCommand;

import com.myproject.command.Command;
import com.myproject.command.util.Route;
import com.myproject.exception.CommandException;
import com.myproject.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CancelCarBookingCommand implements Command {
    @Override
    public Route execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ValidationException {
        return null;
    }
}
