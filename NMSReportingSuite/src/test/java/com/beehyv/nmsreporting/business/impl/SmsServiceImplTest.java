package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.dao.MACourseCompletionDao;
import com.beehyv.nmsreporting.entity.CourseCompletionDTO;
import com.beehyv.nmsreporting.model.MACourseCompletion;
import com.beehyv.nmsreporting.model.MACourseFirstCompletion;
import com.beehyv.nmsreporting.utils.Global;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.beehyv.nmsreporting.utils.Global.getProperty;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Global.class,FileUtils.class, HttpClientBuilder.class})
public class SmsServiceImplTest {

    @Mock
    private MACourseCompletionDao maCourseCompletionDao;

    @Mock
    private MACourseCompletion maCourseCompletion;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse httpResponse;

    private SmsServiceImpl smsService;

    @Before
    public void setup(){
        PowerMockito.mockStatic(Global.class);
        PowerMockito.mockStatic(FileUtils.class);

        PowerMockito.mockStatic(HttpClientBuilder.class);
        PowerMockito.when(Global.getProperty("sms.otp.templateId.default")).thenReturn("1007689146828763356");
//        PowerMockito.when(Global.getProperty("sms.templateId.default")).thenReturn("1007163065348946395");
        PowerMockito.when(Global.getProperty("sms.entityId.default")).thenReturn("1301159100860122510");
        PowerMockito.when(Global.getProperty("sms.telemarketerId.default")).thenReturn("1001096933494158");
        PowerMockito.when(Global.getProperty("senderid")).thenReturn("HEALTH");
        PowerMockito.when(Global.getProperty("sms.authentication.key")).thenReturn("don'tsaythemagicword");
        PowerMockito.when(Global.getProperty("endpoint")).thenReturn("http://stagesmsapi.nationalmhealth.in/smsmessaging/v1/outbound/nmssenderid/requests-dummy");

        smsService = new SmsServiceImpl();
        smsService.setMACourseCompletionDao(maCourseCompletionDao);
    }

    @Ignore
    @Test
    public void testSendSms_Success() throws IOException, ParseException {
        // Arrange
        String template = "{\"message\": \"Test SMS\"}";

        HttpClientBuilder httpClientBuilder = mock(HttpClientBuilder.class);

        when(HttpClientBuilder.create()).thenReturn(httpClientBuilder);
        when(httpClientBuilder.build()).thenReturn(httpClient);
        when(httpClient.execute(any(HttpPost.class))).thenReturn(httpResponse);

        // Mock response status line
        StatusLine statusLine = mock(StatusLine.class);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(statusLine.toString()).thenReturn("HTTP/1.1 200 success");

        // Act
        String result = smsService.sendSms(maCourseCompletion, template);

        // Assert
        assertEquals("success", result);
        verify(maCourseCompletion).setScheduleMessageSent(true);
        verify(maCourseCompletionDao).updateMACourseCompletion(maCourseCompletion);
    }

    @Test
    @Ignore
    public void testSendSms_HttpRequestError() throws IOException {
        // Arrange
        String template = "{\"message\": \"Test SMS\"}";

        HttpClientBuilder httpClientBuilder = mock(HttpClientBuilder.class);

        when(HttpClientBuilder.create()).thenReturn(httpClientBuilder);
        when(HttpClientBuilder.create().build()).thenReturn(httpClient);
        when(httpClient.execute(any(HttpPost.class))).thenThrow(new IOException("Network error"));

        // Act
        String result = smsService.sendSms(maCourseCompletion, template);

        // Assert
        assertEquals("Unable to send SMS", result);
        verify(maCourseCompletion, never()).setScheduleMessageSent(true);
        verify(maCourseCompletionDao, never()).updateMACourseCompletion(maCourseCompletion);
    }


    @Ignore
    @Test(expected = ParseException.class)
    public void testSendSms_ParseException() throws IOException, ParseException {
        // Arrange
        String template = "{\"message\": \"Test SMS\"}";

        HttpClientBuilder httpClientBuilder = mock(HttpClientBuilder.class);

        when(HttpClientBuilder.create()).thenReturn(httpClientBuilder);
        when(HttpClientBuilder.create().build()).thenReturn(httpClient);
        when(httpClient.execute(any(HttpPost.class))).thenReturn(httpResponse);

        StatusLine statusLine = mock(StatusLine.class);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpResponse.getStatusLine().toString()).thenReturn("HTTP/1.1 200 success");
        doThrow(new ParseException()).when(maCourseCompletion).setLastModifiedDate(any());

        // Act
        String result = smsService.sendSms(maCourseCompletion, template);

        // Assert
        assertEquals("Message sent but modification date could not be updated", result);
        verify(maCourseCompletion).setScheduleMessageSent(true);
        verify(maCourseCompletionDao).updateMACourseCompletion(maCourseCompletion);
    }

    @Test
    public void testBuildOTPSMS_ValidInput() throws Exception {
        // Mock dependencies
        MACourseFirstCompletion mockFirstCompletion = mock(MACourseFirstCompletion.class);
        when(mockFirstCompletion.getFlwId()).thenReturn(1L);

        long phoneNumber = 1234567890L;
        long languageId = 1L;
        String messageContent = "Test OTP Message";

        String jsonResponse = "{\n" +
                "  \"outboundSMSMessageRequest\": {\n" +
                "    \"address\": [\n" +
                "      \"tel: <phoneNumber>\"\n" +
                "    ],\n" +
                "    \"senderAddress\": \"tel: <senderId>\",\n" +
                "    \"outboundSMSTextMessage\": {\n" +
                "      \"message\": \"<messageContent>\"\n" +
                "    },\n" +
                "    \"clientCorrelator\": \"<correlationId>\",\n" +
                "    \"messageType\": \"<messageType>\",\n" +
                "    \"receiptRequest\": {\n" +
                "      \"notifyURL\": \"<notificationUrl>\",\n" +
                "      \"callbackData\": \"\"\n" +
                "    },\n" +
                "    \"templateId\": \"<smsTemplateId>\",\n" +
                "    \"entityId\": \"<smsEntityId>\",\n" +
                "    \"telemarketerId\": \"<smsTelemarketerId>\"\n" +
                "  }\n" +
                "}";

        PowerMockito.when(FileUtils.readFileToString(any(File.class), any(Charset.class))).thenReturn(jsonResponse);
        PowerMockito.when(Global.retrieveAshaSMSCallBackEndPoint(anyString())).thenReturn("https://kma.mohfw.gov.in/NMSReportingSuite/nms/deliveryNotification/otp");
        PowerMockito.when(maCourseCompletionDao.getAshaPhoneNo(anyLong())).thenReturn(phoneNumber);

        // Execute the method
        String result = smsService.buildOTPSMS(mockFirstCompletion, messageContent, languageId);

        // Validate the populated template
        assertNotNull(result);
        assertTrue(result.contains("tel: 1234567890"));
        assertTrue(result.contains("tel: HEALTH"));
        assertTrue(result.contains("Test OTP Message"));
        assertTrue(result.contains("https://kma.mohfw.gov.in/NMSReportingSuite/nms/deliveryNotification/otp"));
        assertTrue(result.contains("1007689146828763356"));
        assertTrue(result.contains("1301159100860122510"));
        assertTrue(result.contains("1001096933494158"));
    }

    @Test
    public void testBuildOTPSMS_PhoneNumberNotFound() {
        // Mock dependencies
        MACourseFirstCompletion mockFirstCompletion = mock(MACourseFirstCompletion.class);
        PowerMockito.when(mockFirstCompletion.getFlwId()).thenReturn(2L);

        // Simulate the exception when trying to get the phone number
        PowerMockito.when(maCourseCompletionDao.getAshaPhoneNo(2L)).thenThrow(new NullPointerException("Phone number not found"));

        // Call the method under test
        String result = smsService.buildOTPSMS(mockFirstCompletion, "Message Content", 1L);

        // Assert that the result is null
        assertNull(result);

    }

    @Test
    public void testBuildOTPSMS_TemplateReadError() throws Exception {
        // Arrange
        MACourseFirstCompletion mockFirstCompletion = mock(MACourseFirstCompletion.class);
        when(mockFirstCompletion.getFlwId()).thenReturn(3L);

        long phoneNumber = 9876543210L;
        when(maCourseCompletionDao.getAshaPhoneNo(3L)).thenReturn(phoneNumber);

        PowerMockito.when(FileUtils.readFileToString(any(File.class), eq(StandardCharsets.UTF_8)))
                .thenThrow(new IOException("Error reading template file"));

        // Act
        String result = smsService.buildOTPSMS(mockFirstCompletion, "Message Content", 1L);

        // Assert
        assertNull(result);

    }

    @Test
    public void testBuildOTPSMS_TemplatePopulationError() throws Exception {
        // Arrange
        MACourseFirstCompletion mockFirstCompletion = mock(MACourseFirstCompletion.class);
        when(mockFirstCompletion.getFlwId()).thenReturn(4L);

        long phoneNumber = 1122334455L;
        when(maCourseCompletionDao.getAshaPhoneNo(4L)).thenReturn(phoneNumber);

        String jsonTemplate = "Invalid Template";
        PowerMockito.when(FileUtils.readFileToString(any(File.class), eq(StandardCharsets.UTF_8)))
                .thenReturn(jsonTemplate);

        PowerMockito.when(Global.retrieveAshaSMSCallBackEndPoint("OTP"))
                .thenThrow(new RuntimeException("Callback endpoint error"));

        // Act
        String result = smsService.buildOTPSMS(mockFirstCompletion, "Message Content", 1L);

        // Assert
        assertNull(result);

    }


    @Test
    public void testBuildCertificateSMS_ValidInput() throws Exception {
        // Arrange
        CourseCompletionDTO courseCompletionDTO = mock(CourseCompletionDTO.class);
        when(courseCompletionDTO.getFlwId()).thenReturn(1L);

        long phoneNumber = 1122334455L;
        when(courseCompletionDTO.getMobileNumber()).thenReturn(phoneNumber);

        String messageContent = "Congratulations on completing the course!";
        String jsonResponse = "{\n" +
                "  \"outboundSMSMessageRequest\": {\n" +
                "    \"address\": [\n" +
                "      \"tel: <phoneNumber>\"\n" +
                "    ],\n" +
                "    \"senderAddress\": \"tel: <senderId>\",\n" +
                "    \"outboundSMSTextMessage\": {\n" +
                "      \"message\": \"<messageContent>\"\n" +
                "    },\n" +
                "    \"clientCorrelator\": \"<correlationId>\",\n" +
                "    \"messageType\": \"4\",\n" +
                "    \"receiptRequest\": {\n" +
                "      \"notifyURL\": \"<notificationUrl>\",\n" +
                "      \"callbackData\": \"\"\n" +
                "    },\n" +
                "    \"senderName\": \"\",\n" +
                "    \"category\": \"\",\n" +
                "    \"templateId\": \"<smsTemplateId>\",\n" +
                "    \"entityId\": \"<smsEntityId>\",\n" +
                "    \"telemarketerId\": \"<smsTelemarketerId>\"\n" +
                "  }\n" +
                "}";
        when(FileUtils.readFileToString(any(File.class), eq(StandardCharsets.UTF_8))).thenReturn(jsonResponse);

        PowerMockito.when(Global.retrieveAshaSMSCallBackEndPoint(anyString()))
                .thenReturn("https://kma.mohfw.gov.in/nms/deliveryNotification/certificateLink");
        PowerMockito.when(Global.retrieveAshaCourseCompletionTemplateId(anyLong()))
                .thenReturn("1007118129107537794");

        // Act
        String result = smsService.buildCertificateSMS(courseCompletionDTO, messageContent);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains(String.valueOf(phoneNumber)));
        assertTrue(result.contains(messageContent));
        assertTrue(result.contains("tel: HEALTH"));
        assertTrue(result.contains("Congratulations on completing the course!"));
        assertTrue(result.contains("4"));
        assertTrue(result.contains("1007118129107537794"));
        assertTrue(result.contains("1301159100860122510"));
        assertTrue(result.contains("1001096933494158"));
    }

    @Test
    public void testBuildCertificateSMS_NullPhoneNumber() {
        // Arrange
        CourseCompletionDTO courseCompletionDTO = mock(CourseCompletionDTO.class);
        when(courseCompletionDTO.getFlwId()).thenReturn(2L);
        when(courseCompletionDTO.getMobileNumber())
                .thenThrow(new NullPointerException("Phone number not found"));

        // Act
        String result = smsService.buildCertificateSMS(courseCompletionDTO, "Some message");

        // Assert
        assertNull(result);
    }

    @Test
    public void testBuildCertificateSMS_FileReadError() throws Exception {
        // Arrange
        CourseCompletionDTO courseCompletionDTO = mock(CourseCompletionDTO.class);
        when(courseCompletionDTO.getFlwId()).thenReturn(3L);
        when(courseCompletionDTO.getMobileNumber()).thenReturn(1234567890L);

        // Simulate file read error
        when(FileUtils.readFileToString(any(File.class), eq(StandardCharsets.UTF_8)))
                .thenThrow(new IOException("File not found"));

        // Act
        String result = smsService.buildCertificateSMS(courseCompletionDTO, "Some message");

        // Assert
        assertNull(result);


    }

    @Test
    public void testBuildCertificateSMS_PlaceholderReplacementError() throws Exception {
        // Arrange
        CourseCompletionDTO courseCompletionDTO = mock(CourseCompletionDTO.class);
        when(courseCompletionDTO.getFlwId()).thenReturn(4L);
        when(courseCompletionDTO.getLanguageId()).thenReturn(14L);
        when(courseCompletionDTO.getMobileNumber()).thenReturn(1234567890L);

        // Simulate invalid template and placeholder replacement error
        String jsonTemplate = "Invalid Template";
        PowerMockito.mockStatic(FileUtils.class);
        when(FileUtils.readFileToString(any(File.class), eq(StandardCharsets.UTF_8)))
                .thenReturn(jsonTemplate);

        PowerMockito.when(Global.retrieveAshaSMSCallBackEndPoint("certificateLink"))
                .thenReturn("Callback endpoint error");



        // Act
        String result = smsService.buildCertificateSMS(courseCompletionDTO, "Some message");


        // Assert
        assertNull(result);
    }

}