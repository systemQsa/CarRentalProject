package com.myproject.util;

import com.myproject.dao.entity.Order;
import com.myproject.exception.CommandException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) throws ParseException, CommandException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String dateTime = "01/10/2020 06:43:21";
      //  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(dateTime);
        //2022-02-16 23:45:38
      //  pstmt.setDate(5,dateFormat.format(date));
        LocalTime localTime = LocalTime.now();
       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");

       // System.out.println(localTime.format(formatter));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
       String fromDate = "2022-02-19 09:00:11";
//        String time = fromDate.split("\\s")[1].split("\\.")[0];
//        System.out.println(time);
//
//        String time1 = "16:00:00";
//        String time2 = "00:00:00";
//
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//        Date date1 = format.parse(time1);
//        Date date2 = format.parse(time2);
//        long difference = date2.getTime() - date1.getTime();
//        long diffHours = difference / (60 * 60 * 1000) % 24;
//        System.out.println("difference " + diffHours);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//        LocalDateTime dateTime1= LocalDateTime.parse("2022-02-20 19:00:00", formatter);
//        LocalDateTime dateTime2= LocalDateTime.parse("2022-02-21 16:00:00", formatter);
//        LocalDateTime now = LocalDateTime.now();
////        System.out.println(formatter.format(now));
//        System.out.println(dateTime1.isEqual(now));
//        System.out.println(dateTime1.isBefore(dateTime2));
//        System.out.println(dateTime2.isAfter(now));
//        long diffInMinutes = java.time.Duration.between(dateTime1, dateTime2).toMinutes();
       // System.out.println(diffInMinutes/60);

//        System.out.println(dateTime1.isBefore(dateTime2));


//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(dtf.format(now));

      String regex = "^(([\\p{L}]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$";
        String s = "саша@gmail.com";
        Pattern pattern = Pattern.compile("^(([\\p{IsCyrillic}]+)@([\\w]+)\\.([\\p{Lower}]{2,8}))$");
        Matcher matcher = pattern.matcher(s);
        System.out.println(matcher.matches());

    }

    private static Order parseIncomeOrder(String freshOrder) throws CommandException {
        return Stream.of(freshOrder).map(value -> value.substring(6, value.length() - 1).replaceAll("\\S+\\=","")
                        .replaceAll("","")
                        .replaceAll("", ""))
                .map(str -> str.split(","))
                .map(elements -> {
                            Order.OrderBuilder orderBuilder = new Order.OrderBuilder();
                            orderBuilder.setUserId(Integer.parseInt(elements[1].trim()))
                                    .setCar(Integer.parseInt(elements[2].trim()))
                                    .setPassport(elements[3].trim())
                                    .setWithDriver(elements[4].trim())
                                    .setFromDate(elements[5].trim())
                                    .setToDate(elements[6].trim())
                                    .setReceipt(Double.parseDouble(elements[7].trim()))
                                    .setUserLogin(elements[10].trim());
                            return orderBuilder.build();
                        }
                ).findFirst().orElseThrow(() -> new CommandException("CANT PARSE THE OBJECT"));

    }
}
