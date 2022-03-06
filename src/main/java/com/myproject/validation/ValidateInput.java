package com.myproject.validation;


import com.myproject.command.util.GeneralConstant;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInput {
    public static final String INPUT_REGEX_UKR = "^[А-ЯІЇЄ][а-яіїє']{1,20}$";
    public static final String INPUT_REGEX_LAT = "^[A-Z][a-z]{1,20}$";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String LOGIN_REGEX = "^[\\w+]{8,30}$";
    public static final String PASSWORD_REGEX = "^[\\w+]{3,20}$";
    public static final String EMAIL_REGEX = "^(([\\w-]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$";
    public static final String EMAIL_CYRILLIC = "^(([\\p{IsCyrillic}]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$";
    public static final String REGEX_ONLY_WORDS = "^(\\p{L}+)$";
    public static final String REGEX_PHONE = "^[+]?[\\d]{7,16}$";
    public static final String REGEX_PASSPORT = "^([A-Z]{2}[0-9]{2,8})$";
    private final UserService userService = new UserServiceImpl();
    private static final Logger logger = LogManager.getLogger(ValidateInput.class);

    public void validateLogin(String login, HttpServletRequest request, HttpServletResponse response) throws ValidationException {
        Pattern englishLogin = Pattern.compile(EMAIL_REGEX);
        Pattern cyrillicLogin = Pattern.compile(EMAIL_CYRILLIC);
        Matcher english = englishLogin.matcher(login);
        Matcher cyrillic = cyrillicLogin.matcher(login);
        System.out.println("\n\n Login regex " + cyrillic.matches() + " " + english.matches());

        if (cyrillic.matches() || english.matches()){
            logger.info("login validation passed");
        }else {
            request.setAttribute("err",2);
            request.setAttribute("errMSG", "incorrect login");
            logger.error("VALIDATION LOGIN WAS FAILED");
            throw new ValidationException("Invalid Login");
        }
     }


    public void validatePassword(char[] password, HttpServletRequest request) throws ValidationException {
        if (password == null || password.length == 0 || !String.valueOf(password).matches(PASSWORD_REGEX)) {
            logger.error("VALIDATION PASSWORD WAS FAILED");
            request.setAttribute("errMSG","Invalid password");
            request.setAttribute("err",3);
            throw new ValidationException("Invalid password!");
        }
    }


    public void validateInputNameSurname(String name, String surname, HttpServletRequest request) throws ValidationException {
        if (name.matches(REGEX_ONLY_WORDS) && surname.matches(REGEX_ONLY_WORDS)) {
            logger.info("Validation name and surname passed");
        }else {
            logger.error("VALIDATION NAME OR SURNAME WERE FAILED");
            throw new ValidationException("Please check your name or surname the input data were entered incorrect!");
        }
     }


    public void validatePhoneNumber(String phoneNumber, HttpServletRequest request) throws ValidationException {
        if (phoneNumber.matches(REGEX_PHONE)) {
            logger.info("Validation phone passed");
        }else {
            logger.warn("VALIDATION PHONE WAS FAILED");
            throw new ValidationException("The phone number were entered incorrect!");
        }
    }


    public boolean validateUserIsBlocked(String login, HttpServletRequest request) throws ValidationException {
        boolean isBlocked = false;
        String response;
        try {
            response = userService.checkUserStatus(login);
            if (response.equals(GeneralConstant.BLOCKED_STATUS)) {
                isBlocked = true;
            }
        } catch (ServiceException e) {
            logger.error("BLOCKED USER " + login + " TRIED TO ENTER INTO THE SYSTEM");
            throw new ValidationException("BY SOME REASON YOU WERE BLOCKED. PLEASE CONTACT OUR MANAGER!", e);
        }
        return isBlocked;
    }


    public static boolean validateDatesAndTime(LocalDateTime fromDate, LocalDateTime toDate,HttpServletRequest request) throws ValidationException {
        LocalDateTime now = LocalDateTime.now();
        if (fromDate.isEqual(now) || fromDate.isAfter(now) && (toDate.isAfter(now) && toDate.isAfter(fromDate))) {
            return true;
        }
        logger.warn("Attempt to enter incorrect date and time");
        request.setAttribute("err",10);
        request.setAttribute("errMSG","Please enter date and time properly!");
         throw new ValidationException("/WEB-INF/view/user/bookCar.jsp");
    }

    public static boolean validatePassport(String passport,HttpServletRequest request) throws ValidationException{
        Pattern pattern = Pattern.compile(REGEX_PASSPORT);
        Matcher pass = pattern.matcher(passport);
        if (pass.matches()){
            logger.info("Passport validation passed");
        }
        logger.info("Passport validation was failed");
        request.setAttribute("err",12);
        request.setAttribute("errMSG","Please enter passport data properly!");
        throw new ValidationException("/WEB-INF/view/user/bookCar.jsp");
    }
}
