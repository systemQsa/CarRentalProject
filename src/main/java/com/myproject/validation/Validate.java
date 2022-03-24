package com.myproject.validation;

import com.myproject.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public interface Validate {

    boolean loginValidate(String login) throws ValidationException;

    boolean passwordValidate(char[] password) throws ValidationException;

    boolean nameSurnameValidate(String name, String surname) throws ValidationException;

    boolean phoneValidate(String phoneNumber) throws ValidationException;

    boolean userIsBlockedValidate(String login) throws ValidationException;

    boolean datesAndTimeValidate(LocalDateTime fromDate, LocalDateTime toDate, HttpServletRequest request) throws ValidationException;

    boolean passportValidate(String passport) throws ValidationException;
}
