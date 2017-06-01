package com.beehyv.nmsreporting.utils;

/**
 * Created by beehyv on 1/6/17.
 */
public class ServiceFunctions {

    public static String StReplace(String abc){
        abc = abc.replaceAll("[^\\w]", "_");
        return abc;
    }

}
