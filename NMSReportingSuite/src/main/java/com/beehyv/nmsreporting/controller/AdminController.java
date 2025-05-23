package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.business.LocationService;
import com.beehyv.nmsreporting.business.ModificationTrackerService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dao.StateServiceDao;
import com.beehyv.nmsreporting.entity.PasswordDto;
import com.beehyv.nmsreporting.enums.ModificationType;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.ModificationTracker;
import com.beehyv.nmsreporting.model.State;
import com.beehyv.nmsreporting.model.User;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import static com.beehyv.nmsreporting.enums.ReportType.maCourse;
import static com.beehyv.nmsreporting.utils.Global.retrieveDocuments;
import static com.beehyv.nmsreporting.utils.Global.retrieveFileSizeInMB;

//import com.sun.corba.se.impl.orbutil.closure.Constant;


/**
 * Created by beehyv on 15/3/17.
 */
@Controller
@RequestMapping("/nms/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private StateServiceDao stateServiceDao;

    @Autowired
    private ModificationTrackerService modificationTrackerService;

    private final String documents = retrieveDocuments();
    private final String reports = documents+"Reports/";

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    @RequestMapping(value = "/uploadFile",headers=("content-type=multipart/*"), method = RequestMethod.POST)
    @ResponseBody
    public Map uploadFileHandler(@RequestParam("bulkCsv") MultipartFile file) {
        String name = "BulkImportDatacr7ms10.csv";

        final String CSV_CONTENT_TYPE = "text/csv";

        int n = retrieveFileSizeInMB();

        long MAX_FILE_SIZE = n*1024*1024;

        Map<Integer, String> responseMap = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                //validating file type and content type
                boolean fileCheck =  file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")- 1).contains(".");
                if(!(file.getOriginalFilename().endsWith(".csv")) || fileCheck){
                    responseMap.put(0 , "fail");
                    responseMap.put(1 , "Upload csv file only " + file.getOriginalFilename());
                    return responseMap;
                }

                if( !(CSV_CONTENT_TYPE.equals(file.getContentType()) || "application/vnd.ms-excel".equals(file.getContentType())) ){
                    responseMap.put(0, "fail");
                    responseMap.put(1 , "The uploaded file does not have a valid CSV content type");
                    LOGGER.debug("The uploaded file does not have a valid CSV content type");
                    return responseMap;
                }

                if(file.getSize() >= MAX_FILE_SIZE){
                    responseMap.put(0 , "fail");
                    responseMap.put(1 , "Size of file should be less than " + retrieveFileSizeInMB() + " MB");
                    LOGGER.debug("file size exceeding limit " + retrieveFileSizeInMB() + " MB");
                    return  responseMap;
                }
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = documents;
                File dir = new File(rootPath);
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                User user = userService.getCurrentUser();
                if (user == null) {
                    responseMap.put(0, "fail");
                    responseMap.put(1, "No user logged in");
                    return responseMap;
                }
                HashMap object= adminService.startBulkDataImport(user);
                return object;

                /*return "You successfully uploaded file=" + name;*/
            } catch (Exception e) {
                System.out.println(e);
                responseMap.put(0, "fail");
                responseMap.put(1, "You failed to upload " + file.getOriginalFilename());
                return responseMap;
            }
        } else {
            responseMap.put(0, "fail");
            responseMap.put(1, "You failed to upload " + file.getOriginalFilename() + " because the file was empty.");
            return responseMap;
        }

    }

    @RequestMapping(value = "/getBulkDataImportCSV", method = RequestMethod.GET,produces = "application/vnd.ms-excel")
    @ResponseBody
    public String getBulkDataImportCSV(HttpServletResponse response) throws ParseException, java.text.ParseException{

        User user = userService.getCurrentUser();
        if(user==null||!(user.getRoleName().equals("MASTER ADMIN"))&&!(user.getRoleName().equals("ADMIN"))){
            return "Not Authorized";
        }

        response.setContentType("APPLICATION/OCTECT-STREAM");
        try {
            PrintWriter out=response.getWriter();
            String filename="BulkImportData.csv";
            response.setHeader("Content-Disposition","attachment;filename=\""+filename+"\"");
            String rootPath=documents+"BulkImportData.csv";
            File file=new File(rootPath);
            if(!(file.exists())){
                adminService.getBulkDataImportCSV();
            }
            FileInputStream fl=new FileInputStream(rootPath);
            int i;
            while ((i=fl.read())!=-1){
                out.write(i);
            }
            fl.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "Bulkimport";
    }

   /* @RequestMapping(value = "/getCumulativeCourseCompCSV", method = RequestMethod.GET)
    @ResponseBody
    public String getCumulativeCourseCompletion(@PathVariable("state") String State,@PathVariable("district") String District,@PathVariable("block") String Block) throws ParseException, java.text.ParseException{
        adminService.getCumulativeCourseCompletion(State,District,Block);
        return "Bulkimport";
    }*/

    @RequestMapping(value = {"/changePassword"}, method = RequestMethod.POST)
    @ResponseBody public Map resetPassword(@RequestBody PasswordDto passwordDto) throws Exception{
        //        String trackModification = mapper.convertValue(node.get("modification"), String.class);
//
//        ModificationTracker modification = new ModificationTracker();
//        modification.setModificationDate(new Date(System.currentTimeMillis()));
//        modification.setModificationType(ModificationType.UPDATE.getModificationType());
//        modification.setModifiedByUserId(userService.findUserByUsername(getPrincipal()));
//        modification.setModifiedUserId(user);
//        modification.setModifiedField(trackModification);
//        modificationTrackerService.saveModification(modification);

        Map<Integer, String> map= userService.updatePassword(passwordDto);
        if(map.get(0).equals("Password changed successfully")){
            String password = map.get(1);
            String email = map.get(2);
            byte[] encoded = Base64.encodeBase64((email + "||" + password + "||admin").getBytes());
            String encrypted = new String(encoded);
            String url = "http://192.168.200.4:8080/NMSReportingSuite/nms/mail/sendPassword/" + encrypted;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            ModificationTracker modification = new ModificationTracker();
            modification.setModificationDate(new Date(System.currentTimeMillis()));
            modification.setModificationType(ModificationType.UPDATE.getModificationType());
            modification.setModifiedUserId(passwordDto.getUserId());
            modification.setModifiedField("password");
            modification.setModifiedByUserId(userService.getCurrentUser().getUserId());
            modificationTrackerService.saveModification(modification);
        }
        Map<Integer, String> requiredmap=new HashMap<>();
        requiredmap.put(0,map.get(0));
        return requiredmap;
    }
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public String createFolders() throws ParseException, java.text.ParseException{
        createAllFiles();
        return "Created Folders";
    }

    public void createAllFiles(){
        adminService.createFiles(maCourse.getReportType());
        adminService.createFolders(ReportType.maAnonymous.getReportType());
        adminService.createFiles(ReportType.maInactive.getReportType());
        adminService.createFiles(ReportType.maFreshActive.getReportType());
        adminService.createFiles(ReportType.lowUsage.getReportType());
        adminService.createFiles(ReportType.selfDeactivated.getReportType());
        adminService.createFiles(ReportType.sixWeeks.getReportType());
        adminService.createFiles(ReportType.lowListenership.getReportType());
        adminService.createFiles(ReportType.childRejected.getReportType());
        adminService.createFiles(ReportType.motherRejected.getReportType());
        adminService.createFiles(ReportType.flwRejected.getReportType());
    }


    @RequestMapping(value = "/generateMaCourseReports/{reportType}", method = RequestMethod.GET)
    @ResponseBody
    public String getReportsByNameAndMonth(@PathVariable("reportType") String reportType) throws ParseException, java.text.ParseException{

        ReportType tempReportType = ReportType.valueOf(reportType);

        for (int i =1; i < 33 ; i++ ) {
            Calendar aCalendar = Calendar.getInstance();
            Date toDate;

            aCalendar.add(Calendar.MONTH, (-1) * i);
            aCalendar.set(Calendar.DATE, 1);
            aCalendar.set(Calendar.MILLISECOND, 0);
            aCalendar.set(Calendar.SECOND, 0);
            aCalendar.set(Calendar.MINUTE, 0);
            aCalendar.set(Calendar.HOUR_OF_DAY, 0);

            aCalendar.add(Calendar.MONTH, 1);

            toDate = aCalendar.getTime();


            switch (tempReportType) {
                case maCourse: {
                    adminService.getCumulativeCourseCompletionFiles(toDate);
                    break;
                }

            }
        }
        return "Reports Generated";
    }



    @RequestMapping(value = "/generateReports/{reportType}/{relativeMonth}", method = RequestMethod.GET)
    @ResponseBody
    public String getReportsByNameAndMonth(@PathVariable("reportType") String reportType, @PathVariable("relativeMonth") Integer relativeMonth) throws ParseException, java.text.ParseException{
//        User user=userService.getCurrentUser();
//        if(user==null || ! (user.getRoleName().equals(AccessType.MASTER_ADMIN.getAccessType()))) {
//            return "You are not authorised";
//        }

        ReportType tempReportType = ReportType.valueOf(reportType);
        Calendar aCalendar = Calendar.getInstance();
        Date fromDate=new Date();
        Date toDate;
        if(tempReportType.getReportType().equals(ReportType.motherRejected.getReportType()) ||
                tempReportType.getReportType().equals(ReportType.childRejected.getReportType())){
            if(tempReportType.getReportType().equals(ReportType.motherRejected.getReportType())) {

                adminService.createMotherImportRejectedFiles(relativeMonth, true);
                adminService.createMotherImportRejectedFiles(relativeMonth, false);
            }
            else {
                adminService.createChildImportRejectedFiles(relativeMonth, true);
                adminService.createChildImportRejectedFiles(relativeMonth, false);
            }

            aCalendar.add( Calendar.DAY_OF_WEEK, -(aCalendar.get(Calendar.DAY_OF_WEEK)-1));
            aCalendar.add(Calendar.DATE,-(7*(relativeMonth-1)));
            toDate=aCalendar.getTime();
        }else {
            aCalendar.add(Calendar.MONTH, (-1) * relativeMonth);
            aCalendar.set(Calendar.DATE, 1);
            aCalendar.set(Calendar.MILLISECOND, 0);
            aCalendar.set(Calendar.SECOND, 0);
            aCalendar.set(Calendar.MINUTE, 0);
            aCalendar.set(Calendar.HOUR_OF_DAY, 0);

            fromDate = aCalendar.getTime();

            aCalendar.add(Calendar.MONTH, 1);

            toDate = aCalendar.getTime();
        }

        switch (tempReportType) {
            case maCourse: {
                adminService.getCumulativeCourseCompletionFiles(toDate);
                break;
            }
            case maAnonymous: {
                adminService.getCircleWiseAnonymousFiles(fromDate, toDate);
                break;
            }
            case maInactive:{
                adminService.getCumulativeInactiveFiles(toDate);
                break;
            }
            case lowUsage:{
                adminService.processKilkariLowUsageFiles(fromDate, toDate);
                break;
            }
            case selfDeactivated:{
                adminService.getKilkariSelfDeactivationFiles(fromDate, toDate);
                break;
            }
            case sixWeeks:{
                adminService.porcessKilkariSixWeekNoAnswerFiles(fromDate, toDate);
                break;
            }
            case lowListenership:{
                adminService.processKilkariLowListenershipDeactivationFiles(fromDate,toDate);
                break;
            }
            case flwRejected:{
                adminService.createFlwImportRejectedFiles(toDate);
                break;
            }

        }
        return "Reports Generated";
    }

    @RequestMapping(value = "/updateReports/{reportType}", method = RequestMethod.GET)
    @ResponseBody
    public String updateReportsByNameStateAndMonth(@PathVariable("reportType") String reportType) throws ParseException, java.text.ParseException{
//        User user=userService.getCurrentUser();
//        if(user==null || ! (user.getRoleName().equals(AccessType.MASTER_ADMIN.getAccessType()))) {
//            return "You are not authorised";
//        }

        ReportType tempReportType = ReportType.valueOf(reportType);

        List<State> states= locationService.getStatesByServiceType("MOBILE_ACADEMY");
//
        for (State state : states) {

            for (int i =1; i < 33 ; i++ ) {


                Calendar aCalendar = Calendar.getInstance();
                Date toDate;

                aCalendar.add(Calendar.MONTH, (-1) * i);
                aCalendar.set(Calendar.DATE, 1);
                aCalendar.set(Calendar.MILLISECOND, 0);
                aCalendar.set(Calendar.SECOND, 0);
                aCalendar.set(Calendar.MINUTE, 0);
                aCalendar.set(Calendar.HOUR_OF_DAY, 0);
                aCalendar.add(Calendar.MONTH, 1);

                toDate = aCalendar.getTime();


                switch (tempReportType) {
//            case maCourse: {
//                adminService.modifyCumulativeCourseCompletionFiles(toDate,stateId);
//                break;
//            }

                    case maInactive: {
                        adminService.modifyCumulativeInactiveFiles(toDate, state.getStateId());
                        break;
                    }

                    default: {
                        return "Incorrect Report Type";
                    }

                }

            }
        }

        return "Reports Updated";
    }


    @RequestMapping(value = {"/state/{serviceType}"}, method = RequestMethod.GET)
    public @ResponseBody
    List<State> getStatesByServiceType(@PathVariable("serviceType") String serviceType) {

        User currentUser = userService.getCurrentUser();

        if(currentUser.getUserId() != null) {
            return locationService.getStatesByServiceType(serviceType);
        } else
            return null;

    }

    @RequestMapping(value = {"/state/{serviceType}/{stateId}"}, method = RequestMethod.GET)
    public @ResponseBody
    Date getStateServiceStartDate(@PathVariable("serviceType") String serviceType,@PathVariable("stateId") Integer stateId) {


        User currentUser = userService.getCurrentUser();
        if(currentUser.getUserId() != null) {
            return locationService.getServiceStartdateForState(stateId, serviceType);
        } else
            return null;

    }

}
