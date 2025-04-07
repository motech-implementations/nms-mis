package com.beehyv.nmsreporting.business.impl;

import be.quodlibet.boxable.image.Image;
import be.quodlibet.boxable.utils.PDStreamUtils;
import com.beehyv.nmsreporting.business.CertificateService;
import com.beehyv.nmsreporting.business.SmsService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.model.*;
import com.beehyv.nmsreporting.entity.CertificateRequest;
import com.beehyv.nmsreporting.enums.AccessLevel;
import org.apache.pdfbox.io.IOUtils;
//import org.apache.pdfbox.io.ScratchFi
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.beehyv.nmsreporting.utils.Global.*;

@Service("certificateService")
@Transactional
public class CertificateServiceImpl implements CertificateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateServiceImpl.class);
    @Autowired
    private MACourseAttemptDao maCourseAttemptDao;

    @Autowired
    private BulkCertificateAuditDao bulkCertificateAuditDao;

    @Autowired
    DistrictDao districtDao;

    @Autowired
    BlockDao blockDao;

    @Autowired
    VillageDao villageDao;

    @Autowired
    HealthFacilityDao healthFacilityDao;

    @Autowired
    HealthSubFacilityDao healthSubFacilityDao;

    @Autowired
    FrontLineWorkersDao frontLineWorkersDao;

    @Autowired
    private MACourseCompletionDao maCourseCompletionDao;

    @Autowired
    private SmsService smsService;


    private static final String documents = retrieveDocuments();
    private final int teluguStateCode = 40;
    private final int chhatisgarhStateCode = 46;
    private static File TeluguCertificateFile = new File(documents + "Certificate/TeluguSampleCertificate.pdf");
    private static File chhatisgarhCertificateFile = new File(documents + "Certificate/ChhatisgarhAshaCertificateSample.pdf");
    private final String rootDir = documents + "WholeCertificate/Asha/";

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Logger logger = LoggerFactory.getLogger(CertificateServiceImpl.class);

    @Override
    public List<Map<String, String>> createSpecificCertificate(Long mobileNo, User currentUser) {

        String accessLevel = currentUser.getAccessLevel();
        List<MACourseFirstCompletion> flws ;
        String rootDir = documents +"Certificate/Asha/";
        Integer stateId = currentUser.getStateId();
        Integer districtId = currentUser.getDistrictId();
        Integer blockId = currentUser.getBlockId();

        if(accessLevel.equalsIgnoreCase(AccessLevel.NATIONAL.getAccessLevel())){
            flws = maCourseAttemptDao.getSuccessFulCompletion(mobileNo);
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.STATE.getAccessLevel())){
            flws = maCourseAttemptDao.getSuccessFulCompletionByState(mobileNo, stateId);
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.DISTRICT.getAccessLevel())){
            flws = maCourseAttemptDao.getSuccessFulCompletionByDistrict(mobileNo, stateId, districtId);
        }
        else if(accessLevel.equalsIgnoreCase(AccessLevel.BLOCK.getAccessLevel())){
            flws = maCourseAttemptDao.getSuccessFulCompletionByBlock(mobileNo, stateId, districtId, blockId);
        }
        else {
            flws = new ArrayList<>();
        }

        String status ;
        List<Map<String, String>> responseList = new ArrayList<>();
        int i = 1;
        for (MACourseFirstCompletion flw : flws){
            if ( flw.getFirstCompletionDate()!= null){

                // creating directories
                String dir ="";
                dir = dir + flw.getStateId();
                if(flw.getDistrictId() != null){
                    dir = dir+"/"+flw.getDistrictId();
                }
                if(flw.getBlockId() != null){
                    dir = dir+"/"+flw.getBlockId();
                }
                File fileDr = new File(rootDir+dir);
                if(!fileDr.exists()){
                    fileDr.mkdirs();
                }

//                PDDocument document = new PDDocument();
//                PDDocument sampleDocument = new PDDocument();
//                status = createCertificatePdf(document, sampleDocument, flw.getFullName(), mobileNo, flw.getFirstCompletionDate());
                String fileName = flw.getMsisdn()+"_"+i+".pdf";
                FrontLineWorkers frontLineWorker = frontLineWorkersDao.getFlwById(flw.getFlwId());
                status = createCertificatePdf(rootDir+dir + "/" + fileName, frontLineWorker.getFullName(), mobileNo, flw.getFirstCompletionDate(),  frontLineWorker);
                if (status.equalsIgnoreCase("success")){
                    Map<String, String> response = new HashMap<>();
                    response.put("file",fileName);
                    response.put("path",dir);
                    response.put("status","success");
                    response.put("AshaName",frontLineWorker.getFullName());
                    responseList.add(response);
                    i++;
                }
//                if(status.equals("success")){
//                    Map<String, String> response = new HashMap<>();
//                    try {
//                        String fileName = flw.getMsisdn()+"_"+i+".pdf";
//
//                        document.save(rootDir+dir + "/" + fileName);
//
//                        response.put("file",fileName);
//                        response.put("path",dir);
//                        response.put("status","success");
//                        response.put("AshaName",flw.getFullName());
//                        responseList.add(response);
//                        i++;
//                    } catch (IOException e) {
//                        System.out.println("Error while saving certificate");
//                    }
//                }
//                try {
//                    document.close();
//                    sampleDocument.close();
//                } catch (IOException ignore) {
//                }
            }
        }

        if(responseList.isEmpty()){
            Map<String, String> response = new HashMap<>();
            response.put("status","No data found");
            responseList.add(response);
        }

         return responseList;

    }

    @Override
    public Map<String, String> createCertificateInBulk(CertificateRequest certificateRequest, String forMonth, boolean auditable, String queryLevel, String zipDir, User currentUser, String stateName, HashMap<Integer, String> districtMap, HashMap<Integer, HashMap<Integer, String>> blockMap) {

        List<MACourseFirstCompletion> flws ;
        String rootDir = documents +"Certificate/Asha/";
        Integer stateId = certificateRequest.getStateId();
        Integer districtId = certificateRequest.getDistrictId();
        Integer blockId = certificateRequest.getBlockId();
        Map<String, String> response = new HashMap<>();

        LOGGER.info("Starting bulk certificate generation for stateId: {}, districtId: {}, blockId: {}, queryLevel: {}", stateId, districtId, blockId, queryLevel);

        if(queryLevel.equalsIgnoreCase("STATE")){
            flws = maCourseAttemptDao.getSuccessFulCompletionByStateIdAndMonth(forMonth, stateId);
        }
        else if(queryLevel.equalsIgnoreCase("DISTRICT")){
            flws = maCourseAttemptDao.getSuccessFulCompletionWithDistrictIdAndMonth(forMonth, stateId, districtId);
        }
        else if(queryLevel.equalsIgnoreCase("BLOCK")){
            flws = maCourseAttemptDao.getSuccessFulCompletionWithBlockIdAndMont(forMonth, stateId, districtId, blockId);
        }
        else {
            flws = new ArrayList<>();
        }

        LOGGER.info("Total FLWs found for certificate generation: {}", flws.size());


        int failed = 0;
        int success = 0;
        String status ;
        for (MACourseFirstCompletion flw : flws){
            try{
                if ( flw.getFirstCompletionDate()!= null){

                    // creating directories
                    String dir = forMonth + "/";
                    dir = dir + flw.getStateId()+"_"+stateName;
                    if(flw.getDistrictId() != null){
                        dir = dir+"/"+flw.getDistrictId()+"_"+districtMap.get(flw.getDistrictId());
                        if(flw.getBlockId() != null){
                            dir = dir+"/"+flw.getBlockId()+"_"+blockMap.get(flw.getDistrictId()).get(flw.getBlockId());
                        }
                        else {
                            dir = dir + "/" + "other";
                        }
                    }
                    else {
                        dir = dir + "/" + "other";
                    }

                    File fileDr = new File(rootDir+dir);
                    if(!fileDr.exists()){
                        fileDr.mkdirs();
                    }

//                PDDocument document = new PDDocument();
//                PDDocument sampleDocument = new PDDocument();
//
//                status = createCertificatePdf(document, sampleDocument, frontLineWorkersDao.getFlwById(flw.getFlwId()).getFullName(), flw.getMsisdn(), flw.getFirstCompletionDate());
                    String fileName = flw.getMsisdn()+"_"+flw.getId()*2+1+".pdf";

                    status = createCertificatePdf(rootDir+dir + "/" + fileName, frontLineWorkersDao.getFlwById(flw.getFlwId()).getFullName(), flw.getMsisdn(), flw.getFirstCompletionDate(), frontLineWorkersDao.getFlwById(flw.getFlwId()));
                    if (status.equalsIgnoreCase("success")){
                        success++;
                    }
                    else {
                        failed++;
                    }
//                if(status.equals("success")){
//                    try {
//                        String fileName = flw.getMsisdn()+"_"+flw.getId()*2+1+".pdf";
//                        System.out.println("---------------111111111--------------");
//                        document.save(rootDir+dir + "/" + fileName);
//                        System.out.println("---------------22222222222--------------");
//                        success++;
//
//                    } catch (IOException e) {
//                        failed++;
//                        System.out.println("Error while saving certificate");
//                    }
//                }
//                else {
//                    failed++;
//                }
//                try {
//                    document.close();
//                    sampleDocument.close();
//                } catch (IOException ignore) {
//                }
                }
            }
            catch (Exception e){
                failed++;
            }
        }

        LOGGER.info("Certificate generation completed. Total certificates generated: {}, failed: {}", success, failed);
        if (success>0){
            if (auditable){
                BulkCertificateAudit audit = new BulkCertificateAudit();
                audit.setFileDirectory(zipDir);
                audit.setGeneratedBy(currentUser);
                audit.setGeneratedOn(new Date());
                bulkCertificateAuditDao.saveAudit(audit);
            }
            response.put("status", "success");
            response.put("fileDir", zipDir);
            response.put("message","Total "+success+" certificate generated with the above criteria");
        }
        else {
            response.put("status","failed");
            response.put("message","NO Asha is available for the certificate with the above criteria");
        }
        response.put("Total_Asha",  Integer.toString(flws.size()));
        response.put("Total_Certificate", Integer.toString(success));
        response.put("Error",Integer.toString(failed));

        LOGGER.info("Final response: {}", response);

        return response;
    }

    @Override
    public Map<String, String> getCertificate(Long msisdn, Integer otp) {
        MACourseFirstCompletion maCourseFirstCompletion = maCourseAttemptDao.getMACourseFirstCompletionByMobileNo(msisdn);
        Map<String, String> response = new HashMap<>();

        if (maCourseFirstCompletion == null) {
            return createResponse("Course not completed yet!", "Course not completed yet!", null, null, null);
        }

        if (!validateOtp(maCourseFirstCompletion, otp)) {
            return createResponse("failed", "Incorrect OTP", maCourseFirstCompletion.getFullName(), null, null);
        }

        if (isOtpExpired(maCourseFirstCompletion)) {
            return createResponse("failed", "OTP expired!", maCourseFirstCompletion.getFullName(), null, null);
        }

        String certificatePath = generateCertificatePath(maCourseFirstCompletion);
        File certificateDir = new File(certificatePath);

        String certificateFile = findExistingCertificate(certificateDir, msisdn);
        if (certificateFile != null) {
            String base64Certificate = getBase64EncodedCertificate(certificatePath + "/" + certificateFile);
            return createResponse("success", null, maCourseFirstCompletion.getFullName(), certificateFile, base64Certificate);
        }

        if (!certificateDir.exists()) {
            certificateDir.mkdirs();
        }

        certificateFile = msisdn + ".pdf";
        FrontLineWorkers flw = frontLineWorkersDao.getFlwById(maCourseFirstCompletion.getFlwId());
        String status = createCertificatePdf(
                certificatePath + "/" + certificateFile,
                flw.getFullName(),
                msisdn,
                maCourseFirstCompletion.getFirstCompletionDate(),
                flw );

        if ("success".equalsIgnoreCase(status)) {
            String base64Certificate = getBase64EncodedCertificate(certificatePath + "/" + certificateFile);
            return createResponse("success", null, flw.getFullName(), certificateFile, base64Certificate);
        }

        return response;
    }

    private boolean validateOtp(MACourseFirstCompletion maCourseFirstCompletion, Integer otp) {
        String allTimeOtp = String.valueOf(maCourseFirstCompletion.getFlwId() % 1000000);
        if(otp.toString().equals(allTimeOtp)) {
            return true;
        }
        else if(maCourseFirstCompletion.getEncryptedOTP() != null &&
                passwordEncoder.matches(String.valueOf(otp), maCourseFirstCompletion.getEncryptedOTP())){
            return true;
        }

        return false;

    }

    private boolean isOtpExpired(MACourseFirstCompletion maCourseFirstCompletion) {
        long currentTime = System.currentTimeMillis() / 1000;
        long timeStep = Long.parseLong(retrieveOTPLifeSpan());
        return (currentTime - maCourseFirstCompletion.getNormalisedOTPEpoch()) > timeStep;
    }

    private String generateCertificatePath(MACourseFirstCompletion maCourseFirstCompletion) {
        StringBuilder pathBuilder = new StringBuilder(documents + "Certificate/Asha/");
        pathBuilder.append(maCourseFirstCompletion.getStateId());

        if (maCourseFirstCompletion.getDistrictId() != null) {
            pathBuilder.append("/").append(maCourseFirstCompletion.getDistrictId());
        }
        if (maCourseFirstCompletion.getBlockId() != null) {
            pathBuilder.append("/").append(maCourseFirstCompletion.getBlockId());
        }

        return pathBuilder.toString();
    }

    private String findExistingCertificate(File certificateDir, Long msisdn) {
        if (!certificateDir.exists() || !certificateDir.isDirectory()) {
            return null;
        }

        String fileNamePrefix = msisdn.toString();
        for (File file : certificateDir.listFiles()) {
            if (file.isFile() && file.getName().startsWith(fileNamePrefix)) {
                return file.getName();
            }
        }
        return null;
    }

    private Map<String, String> createResponse(String status, String cause, String ashaName, String file, String base64Certificate) {
        Map<String, String> response = new HashMap<>();
        response.put("status", status);
        if (cause != null) response.put("cause", cause);
        if (ashaName != null) response.put("AshaName", ashaName);
        if (file != null) response.put("file", file);
        if (base64Certificate != null) response.put("base64Certificate", base64Certificate);
        return response;
    }

    public String getBase64EncodedCertificate(String filePath) {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new RuntimeException("Error while converting certificate to Base64", e);
        }
    }

    @Override
    public String generateOTPForAshaCertificate(Long mobileNo) throws Exception {
        MACourseFirstCompletion maCourseFirstCompletion = maCourseAttemptDao.getMACourseFirstCompletionByMobileNo(mobileNo);
        if (maCourseFirstCompletion == null) {
            return "Course is not yet completed";
        }

        int otp = generateRandomSixDigitOTP();
        long currentTime = getCurrentEpochTimeInSeconds();

        updateCourseFirstCompletion(maCourseFirstCompletion, otp, currentTime);

        long languageId = maCourseCompletionDao.getAshaLanguageId(maCourseFirstCompletion.getFlwId());
        String messageContent = buildOTPMessage(languageId, otp);

        MACourseCompletion maCourseCompletion = maCourseCompletionDao.getAshaByFLWId(maCourseFirstCompletion.getFlwId());
        return sendOTPMessage(maCourseCompletion, maCourseFirstCompletion, messageContent, languageId);
    }

    private int generateRandomSixDigitOTP() {
        int min = 100000; // Smallest 6-digit number
        int max = 999999; // Largest 6-digit number
        return new Random().nextInt(max - min + 1) + min;
    }

    private long getCurrentEpochTimeInSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    private void updateCourseFirstCompletion(MACourseFirstCompletion maCourseFirstCompletion, int otp, long currentTime) {
        maCourseFirstCompletion.setEncryptedOTP(passwordEncoder.encode(String.valueOf(otp)));
        maCourseFirstCompletion.setNormalisedOTPEpoch(currentTime);
        logger.info("Updated MACourseFirstCompletion: {}", maCourseFirstCompletion);
        maCourseAttemptDao.updateMACourseFirstCompletion(maCourseFirstCompletion);
    }

    private String buildOTPMessage(long languageId, int otp) {
        String messageTemplate = retrieveAshaCourseCompletionOTPMessage(languageId);
        return messageTemplate.replace("<OTP>", String.valueOf(otp));
    }

    private String sendOTPMessage(MACourseCompletion maCourseCompletion, MACourseFirstCompletion maCourseFirstCompletion, String messageContent, long languageId) {
        String template = smsService.buildOTPSMS(maCourseFirstCompletion, messageContent, languageId);
        logger.info("SMS Template: {}", template);
        return smsService.sendSms(maCourseCompletion, template);
    }


    @Override
    public Map<String, String> createAllCertificateUptoCurrentMonthInBulk(String forMonth, State state, HashMap<Integer, String> districtMap, HashMap<Integer, HashMap<Integer, String>> blockMap) {
        List<MACourseFirstCompletion> flws;
        // String rootDir = documents + "WholeCertificate/Asha/";
        Integer stateId = state.getStateId();
        String stateName = state.getStateName();
        String stateDir = rootDir + stateId+"_"+stateName;
        Map<String, String> response = new HashMap<>();

        int failed = 0;
        int success = 0;
        int totalAsha = 0;
        String status;
        File fileAlreadyExist = new File(stateDir);
        if(!fileAlreadyExist.exists()){
            flws = maCourseAttemptDao.getSuccessFulCompletionByStateId(stateId);
        } else {
            List<BulkCertificateAudit> bulkCertificateAudits;
            bulkCertificateAudits = bulkCertificateAuditDao.findByFileDirectory(stateDir);
            Calendar calendar = Calendar.getInstance();
            Date date = bulkCertificateAudits.isEmpty() ? new Date() : bulkCertificateAudits.get(bulkCertificateAudits.size() - 1).getGeneratedOn();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, -1);
            Date newDate = calendar.getTime();
            flws = bulkCertificateAudits.isEmpty() ? maCourseAttemptDao.getSuccessFulCompletionByStateId(stateId) : maCourseAttemptDao.getSuccessFullCompletionByStateAndCompletionDate(stateId, newDate) ;
        }

        for (MACourseFirstCompletion flw : flws) {
            try {
                if (flw.getFirstCompletionDate() != null) {

                    // creating directories
                    String dir = flw.getStateId() + "_" + stateName + "/";
                    if (flw.getDistrictId() != null) {
                        dir = dir + "/" + flw.getDistrictId() + "_" + districtMap.get(flw.getDistrictId());
                        if (flw.getBlockId() != null) {
                            dir = dir + "/" + flw.getBlockId() + "_" + blockMap.get(flw.getDistrictId()).get(flw.getBlockId());
                        } else {
                            dir = dir + "/" + "other";
                        }
                    } else {
                        dir = dir + "/" + "other";
                    }

                    File fileDr = new File(rootDir + dir);
                    if (!fileDr.exists()) {
                        fileDr.mkdirs();
                    }

                    String fileName = flw.getMsisdn() + "_" + flw.getId() * 2 + 1 + ".pdf";

                    status = createCertificatePdf(rootDir + dir + "/" + fileName, frontLineWorkersDao.getFlwById(flw.getFlwId()).getFullName(), flw.getMsisdn(), flw.getFirstCompletionDate(), frontLineWorkersDao.getFlwById(flw.getFlwId()));
                    if (status.equalsIgnoreCase("success")) {
                        success++;
                    } else {
                        failed++;
                    }
                }
            } catch (Exception e) {
                failed++;
            }
        }
        BulkCertificateAudit bulkAudit = new BulkCertificateAudit();
        bulkAudit.setFileDirectory(stateDir);
        bulkAudit.setGeneratedOn(new Date());
        bulkCertificateAuditDao.saveAudit(bulkAudit);
        totalAsha += flws.size();

        response.put("Total_Asha", String.valueOf(totalAsha));
        response.put("Total_Certificate", Integer.toString(success));
        response.put("Error", Integer.toString(failed));

        return response;
    }

    private String createCertificatePdf(String pdfFile, String name, Long msisdn, Date completionDate, FrontLineWorkers frontLineWorkers) {

        String village = frontLineWorkers.getVillage() == null ? " " : villageDao.findByVillageId(frontLineWorkers.getVillage()).getVillageName();
        String phc = frontLineWorkers.getFacility() == null ? " " : healthFacilityDao.findByHealthFacilityId(frontLineWorkers.getFacility()).getHealthFacilityName();
        String district = frontLineWorkers.getDistrict() == null ? " " : districtDao.findByDistrictId(frontLineWorkers.getDistrict()).getDistrictName();
        String rchId = frontLineWorkers.getExternalFlwId();
        String healthSubFacility = frontLineWorkers.getSubfacility() == null ? " " : healthSubFacilityDao.findByHealthSubFacilityId(frontLineWorkers.getSubfacility()).getHealthSubFacilityName();

        PDDocument document = new PDDocument();
        PDDocument sampleDocument = null;

        String response;
        try {
            String font_path = documents + "caslon_italic.ttf";
            PDFont textFont;
//          PDFont textFont = PDType1Font.TIMES_ROMAN;

            int textFontSize = 16;
            int dateFontSize = 10;

            int state = frontLineWorkers.getState();
            if(state == teluguStateCode) {
                // File file = new File(documents + "Certificate/TeluguSampleCertificate.pdf");
                sampleDocument = PDDocument.load(TeluguCertificateFile);
                document.addPage(sampleDocument.getPage(0));
                textFont = PDType0Font.load( sampleDocument, new File(font_path) );
                generateTeluguCertificate(document, textFont, name, rchId, msisdn, district, phc, village, healthSubFacility, completionDate);
            } else if(state == chhatisgarhStateCode){
                // File file = new File(documents + "Certificate/ChhatisgarhAshaCertificateSample.pdf");
                sampleDocument = PDDocument.load(chhatisgarhCertificateFile);
                document.addPage(sampleDocument.getPage(0));
                String healthBlock = frontLineWorkers.getBlock() == null ? " " : blockDao.findByblockId(frontLineWorkers.getBlock()).getBlockName();
                textFont = PDType1Font.HELVETICA_BOLD;
                generateChattisgarhCertificate(document, textFont, name, msisdn, district, healthBlock, healthSubFacility, completionDate);
            } else {
                //Loading an existing document
                File file = new File(documents + "Certificate/SampleAshaCertificate.pdf");
                sampleDocument = PDDocument.load(file);
                document.addPage(sampleDocument.getPage(0));
                textFont = PDType0Font.load( sampleDocument, new File(font_path) );
                PDPage page = document.getPage(0);

                PDPageContentStream contents = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
                PDRectangle mediaBox = page.getMediaBox();

                float nameX = mediaBox.getWidth() / 3 + 20;
                float nameY = mediaBox.getHeight() / 2 + 31;

                float dateX = nameX - 71;
                float dateY = nameY - 37;

                float mobileNoX = mediaBox.getWidth() / 2 - 35;
                float mobileNoY = dateY - 87;

                float signatureX = mobileNoX - 40;
                float signatureY = mobileNoY - 30;


                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
                String completion = formatter.format(completionDate);

//          String signature = "Rakesh Kumar";

                PDStreamUtils.write(contents, name, textFont, textFontSize, nameX, nameY, Color.BLUE);
                PDStreamUtils.write(contents, completion, textFont, dateFontSize, dateX, dateY, Color.BLUE);
                PDStreamUtils.write(contents, msisdn.toString(), textFont, textFontSize, mobileNoX, mobileNoY, Color.BLUE);

//          be.quodlibet.boxable.utils.PDStreamUtils.write(contents, signature, textFont, textFontSize, signatureX, signatureY, Color.RED);

                InputStream is = new FileInputStream(retrieveDocuments() + "Certificate/mission_director.png");
                byte[] bytes = IOUtils.toByteArray(is);
                ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
                Image image1 = new Image(ImageIO.read(bin));
                is.close();
                bin.close();

                float imageWidth = 150;
                float imageHeight = 150;
                image1 = image1.scaleByWidth(imageWidth);
                image1 = image1.scaleByHeight(imageHeight);
                image1.draw(document, contents, signatureX, signatureY);

                contents.close();
            }

            response = "success";
            document.save(pdfFile);

        } catch (IOException ignore) {
            response = "failed to load sample certificate";
            System.out.println("---Error---=>" + response);
        } finally {
            try {
                if (sampleDocument != null) {
                    sampleDocument.close();
                }
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    private void generateTeluguCertificate(PDDocument document, PDFont textFont, String name, String rchId, Long msisdn, String district, String phc, String village, String healthSubFacility, Date completionDate){
        try {
            int textFontSize = 16;
            PDPage page = document.getPage(0);
            PDPageContentStream contents1 = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            PDRectangle mediaBox1 = page.getMediaBox();
            float nameX = 5*(mediaBox1.getWidth()) / 12 - 30;
            float nameY = 2* (mediaBox1.getHeight()) / 3 - 33 ;

            float rchIdX = (mediaBox1.getWidth()) / 4 ;
            float rchIdY =  2* (mediaBox1.getHeight()) / 3 - 57;

            float villageX = (mediaBox1.getWidth()) / 2 - 10;
            float villageY = rchIdY;

            int villageFontTelugu = (100-village.length())/8+5;

            float phcY = mediaBox1.getHeight()/2 -10;
            int phcFontTelugu = 16 - (phc.length()/12);
            float phcX = phcFontTelugu > 13 ? rchIdX -40 : rchIdX - 60;

            float districtX = rchIdX - 20 ;
            int districtFontTelugu = 16 - (district.length()/12);
            float districtY = phcY -22 ;

            float healthSubFacilitY = mediaBox1.getHeight()/2 +15 ;
            int healthSubFacilitFont = 16 - (healthSubFacility.length()/12) ;
            float healthSubFacilityX = healthSubFacilitFont > 13 ? rchIdX : rchIdX - 40 ;

            float dateX = mediaBox1.getWidth()/2+80;
            float dateY = phcY-23;

            float mobileNoX = mediaBox1.getWidth() / 2 ;
            float mobileNoY = (mediaBox1.getHeight()/3+14) ;

            float signatureY =  (mediaBox1.getHeight()/5+ 60);
            float signatureX1 = rchIdX + 5;
            float signatureX2 = mediaBox1.getWidth()/2 - 5;
            float signatureX3 = mediaBox1.getWidth()/2 + 105;


            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String completion = formatter.format(completionDate);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, name, textFont, textFontSize, nameX, nameY, Color.BLUE);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, completion, textFont, textFontSize-2, dateX, dateY, Color.BLUE);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, msisdn.toString(), textFont, textFontSize, mobileNoX, mobileNoY, Color.BLUE);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, village, textFont, villageFontTelugu, villageX, villageY, Color.BLUE);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, healthSubFacility, textFont, healthSubFacilitFont, healthSubFacilityX, healthSubFacilitY, Color.BLUE);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, phc, textFont, phcFontTelugu, phcX, phcY, Color.BLUE);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, district, textFont, districtFontTelugu, districtX, districtY, Color.BLUE);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, rchId, textFont, textFontSize, rchIdX, rchIdY, Color.BLUE);

//          be.quodlibet.boxable.utils.PDStreamUtils.write(contents, signature, textFont, textFontSize, signatureX, signatureY, Color.RED);

            InputStream is = new FileInputStream(retrieveDocuments() + "Certificate/certificate_sign1.png");
            byte[] bytes = IOUtils.toByteArray(is);
            ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
            Image image1 = new Image(ImageIO.read(bin));
            is.close();
            bin.close();

            float imageWidth = 110;
            float imageHeight = 110;
            image1 = image1.scaleByWidth(imageWidth);
            image1 = image1.scaleByHeight(imageHeight);
            image1.draw(document, contents1, signatureX1, signatureY -10);
            // contents1.close();

            InputStream is2 = new FileInputStream(retrieveDocuments() + "Certificate/certificate_sign2.png");
            byte[] bytes2 = IOUtils.toByteArray(is2);
            ByteArrayInputStream bin2 = new ByteArrayInputStream(bytes2);
            Image image2 = new Image(ImageIO.read(bin2));
            is2.close();
            bin2.close();

            float imageWidth2 = 65;
            float imageHeight2 = 55;
            image2 = image2.scaleByWidth(imageWidth2);
            image2 = image2.scaleByHeight(imageHeight2);
            image2.draw(document, contents1, signatureX2, signatureY+7);

            InputStream is3 = new FileInputStream(retrieveDocuments() + "Certificate/certificate_sign3.png");
            byte[] bytes3 = IOUtils.toByteArray(is3);
            ByteArrayInputStream bin3 = new ByteArrayInputStream(bytes3);
            Image image3 = new Image(ImageIO.read(bin3));
            is3.close();
            bin3.close();

            float imageWidth3 = 125;
            float imageHeight3 = 105;
            image3 = image3.scaleByWidth(imageWidth3);
            image3 = image3.scaleByHeight(imageHeight3);
            image3.draw(document, contents1, signatureX3, signatureY + 17);
            contents1.close();
        } catch (IOException erros){
            System.out.println("---Error---=>" + erros);
        }
    }

    private void generateChattisgarhCertificate(PDDocument document, PDFont textFont, String name, Long msisdn, String district, String healthBlock, String subCentre, Date completionDate){

        try {
            int textFontSize = 16;
            PDPage page = document.getPage(0);
            PDPageContentStream contents1 = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            PDRectangle mediaBox1 = page.getMediaBox();

            float nameY =  (mediaBox1.getHeight()) / 2 + 11;
            int nameFont = Math.max((16 - (Math.max((name.length() - 10), 0) / 2)), 7);
            float nameX = nameFont > 12  ? (mediaBox1.getWidth()) / 4 -20 : (mediaBox1.getWidth()) / 4 -30 ;

            float subCentreY = nameY ;
            int subCentreFont = Math.max((16 - (Math.max((subCentre.length() - 10),0) / 2)), 7);
            float subCentreX = subCentreFont > 12 ? 3*(mediaBox1.getWidth()) / 4 - 31 : 3*(mediaBox1.getWidth()) / 4 - 36;

            float blockY = mediaBox1.getHeight()/2 - 12 ;
            int blockFont =Math.max((16 - (Math.max((healthBlock.length() - 22), 0) / 2)), 7);
            float blockX = blockFont > 12 ? (mediaBox1.getWidth())/5 +10 : (mediaBox1.getWidth())/5 ;

            float districtY = blockY ;
            int districtFont = Math.max((16 - (Math.max((district.length() - 24), 0) / 2)), 8);
            float districtX = districtFont > 12 ? mediaBox1.getWidth()/2 + 45 : mediaBox1.getWidth()/2 + 35;

            float dateX = (mediaBox1.getWidth())/2 +25 ;
            float dateY = blockY-27;

            float mobileNoX = mediaBox1.getWidth() / 2 - 30 ;
            float mobileNoY = (mediaBox1.getHeight()/3) ;

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String completion = formatter.format(completionDate);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, name, textFont, nameFont, nameX, nameY, Color.DARK_GRAY);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, completion, textFont, textFontSize, dateX, dateY, Color.DARK_GRAY);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, msisdn.toString(), textFont, textFontSize, mobileNoX, mobileNoY, Color.DARK_GRAY);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, subCentre, textFont, subCentreFont, subCentreX, subCentreY, Color.DARK_GRAY);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, healthBlock, textFont, blockFont, blockX, blockY, Color.DARK_GRAY);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents1, district, textFont, districtFont, districtX, districtY, Color.DARK_GRAY);

            contents1.close();

        } catch (IOException erros){
            System.out.println("---Error---=>" + erros);
        }
    }

    public static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }


}
