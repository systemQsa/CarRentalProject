package com.myproject.validation;

import com.myproject.command.util.ConstantPage;
import com.myproject.command.util.GeneralConstant;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ValidateInput class represents methods which validate income data from user by specific regexes
 */

public class ValidateInput implements Validate {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String CHECK_DATE_TIME_INPUT = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
    private static final String PASSWORD_REGEX = "^[\\w+]{3,20}$";
    private static final String EMAIL_REGEX = "^(([\\w-]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$";
    private static final String EMAIL_CYRILLIC = "^(([\\p{IsCyrillic}]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$";
    private static final String REGEX_ONLY_WORDS = "^(\\p{L}+){3,17}$";
    private static final String REGEX_PHONE = "^[+]?[\\d]{7,14}$";
    private static final String REGEX_PASSPORT = "^([A-Z]{2}[0-9]{2,8})$";
    private static final String REGEX_PRICE = "\\d{2}.\\d{2}";
    private static final String REGEX_CAR_INPUT = "^(\\p{L}+){1,16}$";
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
    public boolean datesAndTimeValidateCorrectness(LocalDateTime fromDate, LocalDateTime toDate)
            throws ValidationException {
        return validateDatesAndTime(fromDate, toDate);
    }

    @Override
    public boolean passportValidate(String passport) throws ValidationException {
        return validatePassport(passport);
    }

    @Override
    public boolean ifInputDatesAndTimeAreMatchToRequiredRegex(String fromDate,
                                                              String toDate) throws ValidationException {
        return validateIfInputDatesAndTimeAreMatchToRequiredRegex(fromDate, toDate);
    }

    @Override
    public boolean validatePrice(String price) throws ValidationException {
        return validatePriceInput(price);
    }

    @Override
    public boolean validateCarInput(String carName, String carClass, String brand) throws ValidationException {
        return validateCarDataIsEmpty(carName,carClass,brand);
    }

    /**
     * Accepts user login and checks if this login meets a certain standard
     *
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
     *
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
     *
     * @param name    - gets name
     * @param surname - gets user surname
     * @return in case if name and surname passes the validation return true else returns false
     * @throws ValidationException in case if name or surname validation fail
     */
    private boolean validateInputNameSurname(String name, String surname) throws ValidationException {
        if (name.matches(REGEX_ONLY_WORDS) && surname.matches(REGEX_ONLY_WORDS)) {
            logger.info("Validation name and surname passed");
            return true;
        } else {
            logger.error("VALIDATION NAME OR SURNAME WAS FAILED");
            throw new ValidationException("Please check your name or surname the input data were entered incorrect!");
        }
    }


    /**
     * Validates entered phone number
     *
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
     *
     * @param login - gets user login
     * @return if user is blocked returns true
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
     *
     * @param fromDate - gets Date and Time from which user wants to make the booking
     * @param toDate   = gets Date and Time to which date user wants to make the booking
     * @return if given dates and time pass the validation returns true else returns false
     * @throws ValidationException in case if dates are incorrect
     */
    private boolean validateDatesAndTime(LocalDateTime fromDate, LocalDateTime toDate) throws ValidationException {
        LocalDateTime now = LocalDateTime.now();

        if (fromDate.isEqual(now) || fromDate.isAfter(now)
                && (toDate.isAfter(now) && toDate.isAfter(fromDate))
                && ((fromDate.getHour() >= 9 && fromDate.getHour() < 19)
                && (toDate.getHour() >= 9 && toDate.getHour() <= 19))) {
            return true;
        }
        logger.warn("Attempt to enter incorrect date and time");
        throw new ValidationException(ConstantPage.BOOK_CAR_PAGE);
    }

    private boolean validateIfInputDatesAndTimeAreMatchToRequiredRegex(String fromDate,
                                                                       String toDate) throws ValidationException {
        Pattern pattern = Pattern.compile(CHECK_DATE_TIME_INPUT);
        Matcher matcherFromDate = pattern.matcher(fromDate);
        Matcher matcherToDate = pattern.matcher(toDate);
        if (matcherFromDate.matches() && matcherToDate.matches()) {
            return true;
        }
        logger.warn("Incorrect date and time input!");
        throw new ValidationException(ConstantPage.BOOK_CAR_PAGE);

    }

    /**
     * The method validates income user passport
     *
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

    /**
     * The method checks if the price were entered correctly
     * @param price - gets the input price
     * @return - if the price passed the validation returns true
     * @throws ValidationException - in case the validation was failed
     */
    private boolean validatePriceInput(String price) throws ValidationException{
         if (Pattern.compile(REGEX_PRICE).matcher(price).matches()) return true;
         logger.warn("Price was entered incorrect");
         throw new ValidationException("Price was entered incorrect ");
    }

    /**
     * The method checks if car name,car class and car brand were entered correctly
     * @param carName - gets the car name
     * @param carClass - gets the car class
     * @param brand - gets the car brand
     * @return - if the validation passed returns true
     * @throws ValidationException in case the validation was failed
     */
    private boolean validateCarDataIsEmpty(String carName, String carClass,
                                           String brand) throws ValidationException {
        Pattern pattern = Pattern.compile(REGEX_CAR_INPUT);
        if (!(pattern.matcher(carName).matches()
                && pattern.matcher(carClass).matches()
                && pattern.matcher(brand).matches())){
            logger.warn("Car data entered incorrectly by admin");
            throw new ValidationException("Car data entered incorrectly");
        }
        return true;
    }
}
