package com.myproject.validation;


import com.myproject.command.util.GeneralConstant;
import com.myproject.exception.ServiceException;
import com.myproject.exception.ValidationException;
import com.myproject.service.UserService;
import com.myproject.service.impl.UserServiceImpl;

public class ValidateInput {
    public static final String INPUT_REGEX_UKR = "^[А-ЯІЇЄ][а-яіїє']{1,20}$";
    public static final String INPUT_REGEX_LAT  = "^[A-Z][a-z]{1,20}$";
    public static final String LOGIN_REGEX = "^[\\w+]{8,30}$";
    public static final String PASSWORD_REGEX = "^[\\w+]{3,20}$";
    public static final String EMAIL_REGEX = "^(([\\w-]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$";
    public static final String REGEX_ONLY_WORDS ="^(\\p{L}+)$";
    public static final String REGEX_PHONE = "^[+]?[\\d]{7,16}$";
    public static final String REGEX_PASSPORT ="^([A-Z]{2}[0-9]{2,8})$";
    private final UserService userService = new UserServiceImpl();
    public void validateLogin(String login) throws ValidationException{
        if (login == null || login.equals(GeneralConstant.EMPTY_STRING) || (!login.matches(EMAIL_REGEX))){
            throw new ValidationException("Invalid Login");
        }
        System.out.println("validation login work");
    }
    public void validatePassword(char[] password)throws ValidationException{
        if(password == null || password.length == 0 || !String.valueOf(password).matches(PASSWORD_REGEX)){
          throw new ValidationException("Invalid password!");
      }
        System.out.println("validation password work");
    }
    public void validateInputNameSurname(String name,String surname) throws ValidationException{
        if(!name.matches(REGEX_ONLY_WORDS) && surname.matches(REGEX_ONLY_WORDS)){
            throw new ValidationException("Please check your name or surname the input data were entered incorrect!");
        }
        System.out.println("validation name surname work");
    }
    public void validatePhoneNumber(String phoneNumber) throws ValidationException{
        System.out.println("phone " + phoneNumber);
       if(!phoneNumber.matches(REGEX_PHONE)){
           throw new ValidationException("The phone number were entered incorrect!");
       }
        System.out.println("validation PHONE work");
    }

    public boolean validateUserIsBlocked(String login) throws ValidationException{
        boolean isBlocked = false;
        String response;
        try {
           response = userService.checkUserStatus(login);
           if (response.equals("Y")){
               isBlocked = true;
           }
        } catch (ServiceException e) {
            throw new ValidationException("USER IS BLOCKED!",e);
        }
        return isBlocked;
    }
}