package com.myproject.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String dateTime = "01/10/2020 06:43:21";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(dateTime);
        //2022-02-16 23:45:38
      //  pstmt.setDate(5,dateFormat.format(date));
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");

        System.out.println(localTime.format(formatter));

        System.out.println(Double.parseDouble("30.0"));

    }
}
