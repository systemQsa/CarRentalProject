package com.myproject.util;

import com.myproject.validation.ValidateInput;

import javax.swing.text.DateFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        String timing = "2022-04-22 17:00:00";
        DateFormatter dateFormatter = new DateFormatter();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ValidateInput.DATE_TIME_PATTERN);
        LocalDateTime time = LocalDateTime.parse(timing,formatter);

        System.out.println((time.getHour()>=9) && time.getHour()<19.00);
    }
}
