package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.business.LocationService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dao.StateServiceDao;
import com.beehyv.nmsreporting.entity.PasswordDto;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.State;
import com.beehyv.nmsreporting.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static com.beehyv.nmsreporting.enums.ReportType.maCourse;


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

    private final String documents = System.getProperty("user.home") +File.separator+ "Documents/";
    private final String reports = documents+"Reports/";

    @RequestMapping(value = "/uploadFile",headers=("content-type=multipart/*"), method = RequestMethod.POST)
    @ResponseBody
    public Map uploadFileHandler(@RequestParam("bulkCsv") MultipartFile file) {
        String name = "BulkImportDatacr7ms10.csv";

        Map<Integer, String> responseMap = new HashMap<>();
        if (!file.isEmpty()) {
            try {
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
                HashMap object= adminService.startBulkDataImport(user);
                return object;

                /*return "You successfully uploaded file=" + name;*/
            } catch (Exception e) {
                System.out.println(e);
                responseMap.put(0, "fail");
                responseMap.put(1, "You failed to upload " + name);
                return responseMap;
            }
        } else {
            responseMap.put(0, "fail");
            responseMap.put(1, "You failed to upload " + name + " because the file was empty.");
            return responseMap;
        }

    }

    @RequestMapping(value = "/getBulkDataImportCSV", method = RequestMethod.GET,produces = "application/vnd.ms-excel")
    @ResponseBody
    public String getBulkDataImportCSV(HttpServletResponse response) throws ParseException, java.text.ParseException{

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
    @ResponseBody public Map resetPassword(@RequestBody PasswordDto passwordDto){
        //        String trackModification = mapper.convertValue(node.get("modification"), String.class);
//
//        ModificationTracker modification = new ModificationTracker();
//        modification.setModificationDate(new Date(System.currentTimeMillis()));
//        modification.setModificationType(ModificationType.UPDATE.getModificationType());
//        modification.setModifiedByUserId(userService.findUserByUsername(getPrincipal()));
//        modification.setModifiedUserId(user);
//        modification.setModificationDescription(trackModification);
//        modificationTrackerService.saveModification(modification);

//        return "redirect:http://localhost:8080/app/#!/";
        return userService.updatePassword(passwordDto);
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
        adminService.createFiles(ReportType.lowUsage.getReportType());
        adminService.createFiles(ReportType.selfDeactivated.getReportType());
        adminService.createFiles(ReportType.sixWeeks.getReportType());
    }
    @RequestMapping(value = "/generateReports/{reportType}/{relativeMonth}", method = RequestMethod.GET)
    @ResponseBody
    public String getReportsByNameAndMonth(@PathVariable("reportType") String reportType, @PathVariable("relativeMonth") Integer relativeMonth) throws ParseException, java.text.ParseException{

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, (-1)*relativeMonth);
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);

        Date fromDate = aCalendar.getTime();

        aCalendar.add(Calendar.MONTH, 1);

        Date toDate = aCalendar.getTime();

        Date startDate=new Date(0);
        ReportType tempReportType = ReportType.valueOf(reportType);
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
                adminService.getKilkariLowUsageFiles(fromDate, toDate);
                break;
            }
            case selfDeactivated:{
                adminService.getKilkariSelfDeactivationFiles(fromDate, toDate);
                break;
            }
            case sixWeeks:{
                adminService.getKilkariSixWeekNoAnswerFiles(fromDate, toDate);
                break;
            }
        }
        return "Reports Generated";
    }

    @RequestMapping(value = {"/state/{serviceType}"}, method = RequestMethod.GET)
    public @ResponseBody
    List<State> getStatesByServiceType(@PathVariable("serviceType") String serviceType) {

        List<State> states= locationService.getStatesByServiceType(serviceType);

        return states;
    }

    @RequestMapping(value = {"/state/{serviceType}/{stateId}"}, method = RequestMethod.GET)
    public @ResponseBody
    Date getStateServiceStartDate(@PathVariable("serviceType") String serviceType,@PathVariable("stateId") Integer stateId) {


        return locationService.getServiceStartdateForState(stateId, serviceType);
    }


}
