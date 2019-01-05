package com.beehyv.nmsreporting.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by beehyv on 1/6/17.
 */
public class ServiceFunctions {

    public static String StReplace(String abc){
        abc = abc.replaceAll("[^\\w]", "_");
        return abc;
    }

    public static String getMonthYear(Date toDate) {
        Calendar c=Calendar.getInstance();
        c.setTime(toDate);
        c.add(Calendar.MONTH,-1);
        int month=c.get(Calendar.MONTH)+1;
        int year=(c.get(Calendar.YEAR))%100;
        String monthString;
        if(month<10){
            monthString="0"+String.valueOf(month);
        }
        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return monthString+"_"+yearString;
    }


    public static Date dateAdder(Date testDate, int value){
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(testDate);
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);


        aCalendar.add(Calendar.DATE, value);
        testDate = aCalendar.getTime();
        return testDate;
    }
    public String generatePassword(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 1) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String SALTCHARS1 = "abcdefghijklmnopqrstuvwxyz";
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS1.length());
            salt.append(SALTCHARS1.charAt(index));
        }
        String SALTCHARS2 = "0123456789";
        while (salt.length() < 7) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS2.length());
            salt.append(SALTCHARS2.charAt(index));
        }
        String SALTCHARS3 = "!@#$%^&*";
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS3.length());
            salt.append(SALTCHARS3.charAt(index));
        }

        String password = salt.toString();
        return password;
    }
    public String generateCaptcha(){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String captchaCode = salt.toString();
        return captchaCode;
    }

}
