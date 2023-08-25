package com.beehyv.nmsreporting.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by beehyv on 2/6/17.
 */

public  final class Global {

    public static String retrieveDocuments() {
        Properties prop = new Properties();
        InputStream input = null;
        String fileLocation = null;
        try {
            String pathname = "../webapps/NMSReportingSuite/WEB-INF/classes/app.properties";
            File file = new File(Paths.get(pathname).normalize().toString());
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            fileLocation = prop.getProperty("fileLocation");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileLocation;
    }

    public static int retrieveFileSizeInMB(){
        Properties prop = new Properties();
        InputStream input = null;
        int fileSize  = 1;
        try{
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/app.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            fileSize = Integer.parseInt(prop.getProperty("sizeOfFileInMB").trim());
        }
        catch (IOException ex){
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileSize;
    }
    public static String retrieveUiAddress() {
        Properties prop = new Properties();
        InputStream input = null;
        String uiAddress = null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/app.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            uiAddress = prop.getProperty("uiAdd");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uiAddress;
    }

    public static boolean isAutoGenerate() {
        Properties prop = new Properties();
        InputStream input = null;
        boolean uiAddress = false;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/app.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            uiAddress =  prop.getProperty("cron").equals("1");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uiAddress;
    }

    public static Properties retrieveExternalProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            File file = new File(retrieveDocuments() + "ExternalProperties/external.properties");
            input = new FileInputStream(file);
            // load external properties file
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

}
