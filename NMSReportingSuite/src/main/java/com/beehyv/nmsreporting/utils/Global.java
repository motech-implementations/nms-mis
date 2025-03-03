package com.beehyv.nmsreporting.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

    public static String retrieveTargetfileLocation() {
        Properties prop = new Properties();
        InputStream input = null;
        String fileLocation = null;
        try {
            String pathname = "../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties";
            File file = new File(Paths.get(pathname).normalize().toString());
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            fileLocation = prop.getProperty("smsfilepath");
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

    public static String getPropertyValue(String key) {
        Properties properties = new Properties();
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties"), "UTF-8")) {
            properties.load(reader);
            return properties.getProperty(key);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
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

    public static String getPropertyValueApp(String key) {
        Properties prop = new Properties();
        InputStream input = null;
        String propertyValue = null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/app.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            propertyValue = prop.getProperty(key);
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
        return propertyValue;
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
        String message = getPropertyValue("sms.asha.default.message." + languageId);
        if (message == null) {
            message = getPropertyValue("sms.asha.default.message");
        }
        return message;
    }

    public static String retrieveAshaCourseCompletionOTPMessage(long languageId) {
        String message = getPropertyValue("sms.asha.default.otp.message." + languageId);
        if (message == null) {
            message = getPropertyValue("sms.asha.default.otp.message");
        }
        return message;
    }

    public static String retrieveAshaCourseCompletionTemplateId(long languageId) {
        String message = getProperty("sms.templateId.default." + languageId);
        if (message == null) {
            message = getProperty("sms.templateId.default");
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
