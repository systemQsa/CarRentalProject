package com.myproject.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String dateTime = "01/10/2020 06:43:21";
      //  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(dateTime);
        //2022-02-16 23:45:38
      //  pstmt.setDate(5,dateFormat.format(date));
        LocalTime localTime = LocalTime.now();
       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");

       // System.out.println(localTime.format(formatter));
       String fromDate = "2022-02-19 09:00:11";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parseFromDate = null;
        try {
            parseFromDate = dateFormat.parse(fromDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp fromTime = new Timestamp(parseFromDate.getTime());
        System.out.println(fromTime);

    }
}
