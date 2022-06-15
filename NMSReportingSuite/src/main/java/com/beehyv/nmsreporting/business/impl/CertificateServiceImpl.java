package com.beehyv.nmsreporting.business.impl;

import be.quodlibet.boxable.image.Image;
import com.beehyv.nmsreporting.business.CertificateService;
import com.beehyv.nmsreporting.dao.MACourseAttemptDao;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.model.MACourseFirstCompletion;
import com.beehyv.nmsreporting.model.User;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

import static com.beehyv.nmsreporting.utils.Global.retrieveDocuments;

@Service("certificateService")
@Transactional
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private MACourseAttemptDao maCourseAttemptDao;

    @Override
    public List<Map<String, String>> createSpecificCertificate(Long mobileNo, User currentUser) {

        String accessLevel = currentUser.getAccessLevel();
        List<MACourseFirstCompletion> flws ;
        String rootDir = retrieveDocuments()+"Certificate/Asha/";
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

                PDDocument document = new PDDocument();
                PDDocument sampleDocument = new PDDocument();
                status = createCertificatePdf(document, sampleDocument, flw.getFullName(), mobileNo, flw.getFirstCompletionDate());

                if(status.equals("success")){
                    Map<String, String> response = new HashMap<>();
                    try {
                        String fileName = flw.getMsisdn()+"_"+i+".pdf";

                        document.save(rootDir+dir + "/" + fileName);

                        response.put("file",fileName);
                        response.put("path",dir);
                        response.put("status","success");
                        response.put("AshaName",flw.getFullName());
                        responseList.add(response);
                        i++;
                    } catch (IOException e) {
                        System.out.println("Error while saving certificate");
                    }
                }
                try {
                    document.close();
                    sampleDocument.close();
                } catch (IOException ignore) {
                }
            }
        }

        if(responseList.isEmpty()){
            Map<String, String> response = new HashMap<>();
            response.put("status","No data found");
            responseList.add(response);
        }

         return responseList;

    }

    private String createCertificatePdf(PDDocument document, PDDocument sampleDocument, String name, Long msisdn, Date completionDate) {

        String response;
        try {
            String font_path = retrieveDocuments() + "caslon_italic.ttf";
            PDFont textFont = PDType0Font.load( sampleDocument, new File(font_path) );
//          PDFont textFont = PDType1Font.TIMES_ROMAN;

            int textFontSize = 16;
            int dateFontSize = 14;

            //Loading an existing document
            File file = new File( retrieveDocuments() + "Certificate/SampleAshaCertificate.pdf");
            sampleDocument = PDDocument.load(file);
            document.addPage(sampleDocument.getPage(0));
            PDPage page = document.getPage(0);

            PDPageContentStream contents = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
            PDRectangle mediaBox = page.getMediaBox();

            float nameX = mediaBox.getWidth() / 3 + 20;
            float nameY = mediaBox.getHeight() / 2 + 31;

            float dateX = nameX - 71;
            float dateY = nameY - 37;

            float mobileNoX = mediaBox.getWidth() / 2 - 35;
            float mobileNoY = dateY - 87;

            float signatureX = mobileNoX-40;
            float signatureY = mobileNoY - 30;


            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");
            String completion = formatter.format(completionDate);

//          String signature = "Rakesh Kumar";

            be.quodlibet.boxable.utils.PDStreamUtils.write(contents, name, textFont, textFontSize, nameX, nameY, Color.BLUE);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents, completion, textFont, dateFontSize, dateX, dateY, Color.BLUE);
            be.quodlibet.boxable.utils.PDStreamUtils.write(contents, msisdn.toString(), textFont, textFontSize, mobileNoX, mobileNoY, Color.BLUE);

//          be.quodlibet.boxable.utils.PDStreamUtils.write(contents, signature, textFont, textFontSize, signatureX, signatureY, Color.RED);

            InputStream is = new FileInputStream(retrieveDocuments()+"Certificate/mission_director.png");
            byte[] bytes = IOUtils.toByteArray(is);
            ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
            Image image1 = new Image(ImageIO.read(bin));
            is.close();
            bin.close();

            float imageWidth =150;
            float imageHeight =150;
            image1 = image1.scaleByWidth(imageWidth);
            image1 = image1.scaleByHeight(imageHeight);
            image1.draw(document, contents, signatureX, signatureY);


            contents.close();
            response = "success";
        }
       catch (IOException ignore) {
            response = "failed to load sample certificate";
            System.out.println("---Error---=>" + response);
       }

        return response;

    }

}
