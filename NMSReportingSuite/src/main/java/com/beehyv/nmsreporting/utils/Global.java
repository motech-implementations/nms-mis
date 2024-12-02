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

    public static String retrieveOTPLifeSpan() {
        Properties prop = new Properties();
        InputStream input = null;
        String otpLifeSpan = null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            otpLifeSpan = prop.getProperty("otplifespan");
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
        return otpLifeSpan;
    }


    public static String retrieveSMSEndPoint() {
        Properties prop = new Properties();
        InputStream input = null;
        String smsEndPoint = null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            smsEndPoint = prop.getProperty("endpoint");
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
        return smsEndPoint;
    }

    public static String retrieveSenderId() {
        Properties prop = new Properties();
        InputStream input = null;
        String senderId = null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            senderId = prop.getProperty("senderid");
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
        return senderId;
    }

    public static String retrieveAshaCourseCompletionMessage(int languageId) {
        Properties prop = new Properties();
        InputStream input = null;
        String ashaStaticMessage  = null;
        LOGGER.info("Retrieving message content");
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            LOGGER.info(String.valueOf(prop));
            ashaStaticMessage = prop.getProperty("sms.asha.default.message."+"2");
            LOGGER.info("There is no exception in this!");
            if(ashaStaticMessage == null){
                LOGGER.info("Inside if as content is null");
                ashaStaticMessage = prop.getProperty("sms.asha.default.message");
            }
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
        return ashaStaticMessage;
    }

    public static String retrieveAshaCourseCompletionOTPMessage(int languageId) {
        Properties prop = new Properties();
        InputStream input = null;
        String ashaOTPStaticMessage  = null;
        LOGGER.info("Retrieving message content");
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            ashaOTPStaticMessage = prop.getProperty("sms.asha.default.otp.message."+languageId);
            LOGGER.info("There is no exception in this!");
            if(ashaOTPStaticMessage  == null){
                LOGGER.info("Inside if as content is null");
                ashaOTPStaticMessage  = prop.getProperty("sms.asha.default.otp.message");
            }

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
        return ashaOTPStaticMessage;
    }

    public static String retrieveAuthKey() {
        Properties prop = new Properties();
        InputStream input = null;
        String  authKey = null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            authKey = prop.getProperty("sms.authentication.key");
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
        return authKey;
    }

    public static String retrieveCallBackEndPointForgotPassword() {
        Properties prop = new Properties();
        InputStream input = null;
        String  callBackEndPoint= null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            callBackEndPoint = prop.getProperty("callbackEndpoint");
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
        return callBackEndPoint;
    }

    public static String retrieveForgotPasswordStaticMessage() {
        Properties prop = new Properties();
        InputStream input = null;
        String  message= null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            message = prop.getProperty("sms.default.message");
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
        return message;
    }
    public static String retrieveAshaCertificateDownloadPageUrl() {
        Properties prop = new Properties();
        InputStream input = null;
        String  message= null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            message = prop.getProperty("sms.asha.certificate.download.url");
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
        return message;
    }

    public static String retrieveTemplateId() {
        Properties prop = new Properties();
        InputStream input = null;
        String templateId = null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            templateId = prop.getProperty("sms.templateId.default");
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
        return templateId;
    }

    public static String retrieveEntityId() {
        Properties prop = new Properties();
        InputStream input = null;
        String  id = null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            id = prop.getProperty("sms.entityId.default");
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
        return id;
    }

    public static String retrieveTelemarketerId() {
        Properties prop = new Properties();
        InputStream input = null;
        String id = null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            id = prop.getProperty("sms.telemarketerId.default");
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
        return id;
    }

    public static String retrieveAshaSMSCallBackEndPoint(String entryPoint) {
        Properties prop = new Properties();
        InputStream input = null;
        String endPoint = null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            LOGGER.info("Inside sms.properties");
            // load a properties file
            prop.load(input);
            endPoint = prop.getProperty("sms.asha.callbackEndpoint." + entryPoint);
            LOGGER.info("Got Endpoint as {}", endPoint);
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
        return endPoint;
    }

    public static String retrieveAshaCertificateDownloadUrl() {
        Properties prop = new Properties();
        InputStream input = null;
        String endPoint = null;
        try {
            File file = new File("../webapps/NMSReportingSuite/WEB-INF/classes/sms.properties");
            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
            endPoint = prop.getProperty("sms.asha.certificate.download.url");
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
        return endPoint;
    }

}
