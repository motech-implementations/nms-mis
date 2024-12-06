package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.SmsNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.beehyv.nmsreporting.utils.Global.getPropertyValue;

@Service("smsNotificationService")
@Transactional
public class SmsNotificationServiceImpl implements SmsNotificationService {

    private Logger LOGGER = LoggerFactory.getLogger(AshaTargetFileServiceImpl.class);

    private static final String INITIAL_RETRY_DELAY = getPropertyValue("sms.initial_retry_delay");
    private static final int INITIAL_RETRY_DELAY_DEFAULT = 2;
    private static final int MAX_NOTIFICATION_RETRY_COUNT_DEFAULT = 3;
    public static final String SMS_TARGET_FILE_NOTIFICATION_URL = getPropertyValue("sms.target_file_notification_url");
    public static final int MILLIS_PER_SEC = 1000;
    private static final String HTTP_TIMEOUT_VALUE = getPropertyValue("sms.http_timeout_value");
    private static final int DEFAULT_HTTP_TIMEOUT_VALUE = 30000;



    @Override
    public void sendNotificationRequest(TargetFileNotification tfn) {
        String notificationUrl = SMS_TARGET_FILE_NOTIFICATION_URL;
        LOGGER.debug("Sending {} to {}", tfn, notificationUrl);

        HttpPost httpPost = new HttpPost(notificationUrl);
        ObjectMapper mapper = new ObjectMapper();

        try {
            String requestJson = mapper.writeValueAsString(tfn);
            LOGGER.info("Payload being sent: {}", requestJson);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(requestJson));

            this.sendWithRetries(httpPost, HttpStatus.SC_ACCEPTED);
        } catch (IOException e) {
            throw new InternalException(String.format("Unable to create sms targetFile notification request: %s", e.getMessage()));
        }
    }


    private boolean sendWithRetries(final HttpPost httpPost, final int expectedStatus) {
        LOGGER.debug("Sending {}", httpPost);

        int retryDelay;
        try{
            retryDelay = Integer.parseInt(INITIAL_RETRY_DELAY);
        }catch (NumberFormatException e){
            retryDelay = INITIAL_RETRY_DELAY_DEFAULT;
        }

        int  maxRetryCount = MAX_NOTIFICATION_RETRY_COUNT_DEFAULT;
        int count = 0;
        String error = "";

        int timeout = this.httpTimeoutValue();
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .build();
        httpPost.setConfig(requestConfig);

        while (count < maxRetryCount) {
            CloseableHttpClient httpClient = null;
            try {
                httpClient = HttpClients.createDefault();
                ResponseHandler<Boolean> responseHandler = new ResponseHandler<Boolean>() {
                    @Override
                    public Boolean handleResponse(HttpResponse response) throws IOException {
                        int responseCode = response.getStatusLine().getStatusCode();
                        if (responseCode == expectedStatus) {
                            LOGGER.debug("SUCCESS Sending httpPost {} (response {})", httpPost, responseCode);
                            return true;
                        } else {
                            String error = String.format("Expecting HTTP %d but received HTTP %d: %s", expectedStatus,
                                    responseCode, EntityUtils.toString(response.getEntity()));
                            LOGGER.warn(error);
                            return false;
                        }
                    }
                };

                if (httpClient.execute(httpPost, responseHandler)) {
                    return true;
                }
            } catch (IOException e) {
                error = String.format("Unable to send httpPost %s: %s", httpPost.toString(), e.getMessage());
                LOGGER.warn(error);
            } finally {
                if (httpClient != null) {
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        LOGGER.warn("Failed to close HttpClient: {}", e.getMessage());
                    }
                }
            }

            count++;
            try {
                Thread.sleep(retryDelay * MILLIS_PER_SEC);
            } catch (InterruptedException e) {
                LOGGER.warn("Retry sleep interrupted: {}", e.getMessage());
            }
            retryDelay *= retryDelay;
        }

        LOGGER.error(error);
        return false;
    }

    private int httpTimeoutValue() {
        int val;
        try {
            val = Integer.parseInt(HTTP_TIMEOUT_VALUE);
        } catch (NumberFormatException e) {
            val = DEFAULT_HTTP_TIMEOUT_VALUE;
        }

        return val;
    }

}
