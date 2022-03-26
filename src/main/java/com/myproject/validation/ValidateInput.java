package com.myproject.validation;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ValidateInput class represents methods which validate income data from user by specific regexes
 */

public class ValidateInput implements Validate {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String LOGIN_REGEX = "^[\\w+]{8,30}$";
    public static final String PASSWORD_REGEX = "^[\\w+]{3,20}$";
    public static final String EMAIL_REGEX = "^(([\\w-]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$";
    public static final String EMAIL_CYRILLIC = "^(([\\p{IsCyrillic}]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$";
    public static final String REGEX_ONLY_WORDS = "^(\\p{L}+){3,17}$";
    public static final String REGEX_PHONE = "^[+]?[\\d]{7,14}$";
    public static final String REGEX_PASSPORT = "^([A-Z]{2}[0-9]{2,8})$";
    private final UserService userService;
    private static final Logger logger = LogManager.getLogger(ValidateInput.class);

    public ValidateInput() {
        userService = new UserServiceImpl();
    }

    public ValidateInput(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean loginValidate(String login) throws ValidationException {
        return validateLogin(login);
    }

    @Override
    public boolean passwordValidate(char[] password) throws ValidationException {
        return validatePassword(password);
    }

    @Override
    public boolean nameSurnameValidate(String name, String surname) throws ValidationException {
        return validateInputNameSurname(name, surname);
    }

    @Override
    public boolean phoneValidate(String phoneNumber) throws ValidationException {
        return validatePhoneNumber(phoneNumber);
    }

    @Override
    public boolean userIsBlockedValidate(String login) throws ValidationException {
        return validateUserIsBlocked(login);
    }

    @Override
    public boolean datesAndTimeValidate(LocalDateTime fromDate, LocalDateTime toDate, HttpServletRequest request)
            throws ValidationException {
        return validateDatesAndTime(fromDate, toDate, request);
    }

    @Override
    public boolean passportValidate(String passport) throws ValidationException {
        return validatePassport(passport);
    }


    /**
     * Accepts user login and checks if this login meets a certain standard
     * @param login - gets user login
     * @return in case the login is ok returns true else returns false
     * @throws ValidationException in case login check fail
     */
    private boolean validateLogin(String login) throws ValidationException {
        Pattern englishLogin = Pattern.compile(EMAIL_REGEX);
        Pattern cyrillicLogin = Pattern.compile(EMAIL_CYRILLIC);
        Matcher english = englishLogin.matcher(login);
        Matcher cyrillic = cyrillicLogin.matcher(login);

        if (cyrillic.matches() || english.matches()) {
            logger.info("login validation passed");
            return true;
        } else {
            logger.error("VALIDATION LOGIN WAS FAILED");
            throw new ValidationException("Login was entered incorrect!");
        }
    }


    /**
     * Gets user login and check if entered password were entered correctly
     * according to specific regex
     * @param password - gets user password
     * @return in case password passes the validation returns true else returns false
     * @throws ValidationException in case if password validation fail
     */
    private boolean validatePassword(char[] password) throws ValidationException {
        if (password == null || password.length == 0 || !String.valueOf(password).matches(PASSWORD_REGEX)) {
            logger.error("VALIDATION PASSWORD WAS FAILED");
            throw new ValidationException("Password is invalid!");
        }
        return true;
    }


    /**
     * Validates entered Name and Surname by user checks if this data meets the required standards
     * @param name - gets name
     * @param surname - gets user surname
     * @return in case if name and surname passes the validation return true else returns false
     * @throws ValidationException in case if name or surname validation fail
     */
    private boolean validateInputNameSurname(String name, String surname) throws ValidationException {
        if (name.matches(REGEX_ONLY_WORDS) && surname.matches(REGEX_ONLY_WORDS)) {
            logger.info("Validation name and surname passed");
            return true;
        } else {
            logger.error("VALIDATION NAME OR SURNAME WERE FAILED");
            throw new ValidationException("Please check your name or surname the input data were entered incorrect!");
        }
    }


    /**
     * Validates entered phone number
     * @param phoneNumber - gets user phone number
     * @return in case if entered phone pass the validation returns true else returns false
     * @throws ValidationException in case if phone number validation fail
     */
    private boolean validatePhoneNumber(String phoneNumber) throws ValidationException {
        if (phoneNumber.matches(REGEX_PHONE)) {
            logger.info("Validation phone passed");
            return true;
        } else {
            logger.warn("VALIDATION PHONE WAS FAILED");
            throw new ValidationException("The phone number were entered incorrect!");
        }
    }

    /**
     * Retrieves response from DataBase about given user by given login
     * and checks if this user is blocked or not
     * @param login - gets user login
     * @return  if user is blocked returns true
     * @throws ValidationException in case if the user blocked
     */
    private boolean validateUserIsBlocked(String login) throws ValidationException {
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

    /**
     * The method gets input dates from user and checks if the dates are not the same and
     * if the dates and time meets the required time
     * @param fromDate - gets Date and Time from which user wants to make the booking
     * @param toDate = gets Date and Time to which date user wants to make the booking
     * @param request - in case the input dates are not meet the requirements
     *                formed informative messages and put it into the request
     *                therefore user will be informed about this
     * @return if given dates and time pass the validation returns true else returns false
     * @throws ValidationException in case if dates are incorrect
     */
    private boolean validateDatesAndTime(LocalDateTime fromDate, LocalDateTime toDate, HttpServletRequest request) throws ValidationException {
        LocalDateTime now = LocalDateTime.now();

        if (fromDate.isEqual(now) || fromDate.isAfter(now) && (toDate.isAfter(now) && toDate.isAfter(fromDate))
                && ((fromDate.getHour() >= 9 && fromDate.getHour() < 19) && (toDate.getHour() >= 9 && toDate.getHour() <= 19))) {
            return true;
        }
        logger.warn("Attempt to enter incorrect date and time");
        request.setAttribute("err", 10);
        request.setAttribute("errMSG", "Please enter date and time properly!");
        throw new ValidationException(ConstantPage.BOOK_CAR_PAGE);
    }

    /**
     * The method validates income user passport
     * @param passport - gets user passport
     * @return in case passport pass the validation returns true else returns false
     * @throws ValidationException in case if passport validation fail
     */
    private boolean validatePassport(String passport) throws ValidationException {
        Pattern pattern = Pattern.compile(REGEX_PASSPORT);
        Matcher pass = pattern.matcher(passport);
        if (!pass.matches()) {
            logger.info("Passport validation was failed");
            throw new ValidationException("Passport was entered incorrect");
        }
        logger.info("Passport validation passed");
        return true;

    }
}
