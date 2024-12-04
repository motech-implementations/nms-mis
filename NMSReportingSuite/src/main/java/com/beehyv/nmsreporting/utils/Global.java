package com.beehyv.nmsreporting.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger LOGGER = LoggerFactory.getLogger(Global.class);

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

    private static Properties loadProperties() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties"))) {
            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

    public static String getProperty(String key) {
        Properties prop = loadProperties();
        return prop.getProperty(key);
    }

    public static String retrieveOTPLifeSpan() {
        return getProperty("otplifespan");
    }

    public static String retrieveAshaCourseCompletionMessage(long languageId) {
        String message = getProperty("sms.asha.default.message." + languageId);
        if (message == null) {
            message = getProperty("sms.asha.default.message");
        }
        return message;
    }

    public static String retrieveAshaCourseCompletionOTPMessage(long languageId) {
        String message = getProperty("sms.asha.default.otp.message." + languageId);
        if (message == null) {
            message = getProperty("sms.asha.default.otp.message");
        }
        return message;
    }

    public static String retrieveAshaCertificateDownloadPageUrl() {
        return getProperty("sms.asha.certificate.download.url");
    }

    public static String retrieveAshaSMSCallBackEndPoint(String entryPoint) {
        return getProperty("sms.asha.callbackEndpoint." + entryPoint);
    }

}
