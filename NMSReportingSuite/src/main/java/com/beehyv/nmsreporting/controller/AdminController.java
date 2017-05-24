package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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

    final String documents = System.getProperty("user.home") +File.separator+ "Documents/";
    final String reports = documents+"Reports/";

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
        adminService.getBulkDataImportCSV();
       response.setContentType("APPLICATION/OCTECT-STREAM");
        try {
            PrintWriter out=response.getWriter();
            String filename="BulkImportData.csv";
            response.setHeader("Content-Disposition","attachment;filename=\""+filename+"\"");
            FileInputStream fl=new FileInputStream(documents+"BulkImportData.csv");
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

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public String createFolders() throws ParseException, java.text.ParseException{
        createAllFiles();
        return "Created Folders";
    }

    public void createAllFiles(){
        adminService.createFiles(ReportType.maCourse.getReportType());
        adminService.createFolders(ReportType.maAnonymous.getReportType());
        adminService.createFiles(ReportType.maInactive.getReportType());
        adminService.createFiles(ReportType.lowUsage.getReportType());
        adminService.createFiles(ReportType.selfDeactivated.getReportType());
        adminService.createFiles(ReportType.sixWeeks.getReportType());
    }
    @RequestMapping(value = "/getCumulativeCourseCompCSV1/{fromDate}/{toDate}", method = RequestMethod.GET)
    @ResponseBody
    public String getCumulativeCourseCompletionExcels(@PathVariable("fromDate") Date fromDate, @PathVariable("toDate") Date toDate) throws ParseException, java.text.ParseException{
        /*User loggedInUser = userService.getCurrentUser();
        String loggedUserAccess=loggedInUser.getAccessLevel();
        AccessLevel loggedUserAccessLevel=AccessLevel.getLevel(loggedUserAccess);
        int Locationid=0;
        if(loggedUserAccess=="STATE"){
            Locationid=loggedInUser.getStateId().getStateId();
        }
        else if(loggedUserAccess=="DISTRICT"){
            Locationid=loggedInUser.getDistrictId().getDistrictId();
        }*/
        adminService.getCumulativeCourseCompletionFiles(fromDate,toDate);
        return "Bulkimport";
    }
}
