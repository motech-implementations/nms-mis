package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;

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

    @RequestMapping(value = "/uploadFile",headers=("content-type=multipart/*"), method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileHandler(@RequestParam("bulkCsv") MultipartFile file) {
        String name = "BulkImportDatacr7ms10.csv";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("user.home") + File.separator + "Documents";
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

                return "redirect:/nms/admin/startBulkDataImportProcess";
                /*return "You successfully uploaded file=" + name;*/
            } catch (Exception e) {
                System.out.println(e);
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name
                    + " because the file was empty.";
        }

    }

    @RequestMapping(value = "/startBulkDataImportProcess", method = RequestMethod.GET)
    @ResponseBody
    public HashMap startBulkDataImportProcess() throws ParseException, java.text.ParseException{
        User user = userService.getCurrentUser();
        HashMap object= adminService.startBulkDataImport(user);
       return object;
    }

    @RequestMapping(value = "/getBulkDataImportCSV", method = RequestMethod.GET)
    @ResponseBody
    public String getBulkDataImportCSV() throws ParseException, java.text.ParseException{
        adminService.getBulkDataImportCSV();
        return "Bulkimport";
    }

   /* @RequestMapping(value = "/getCumulativeCourseCompCSV", method = RequestMethod.GET)
    @ResponseBody
    public String getCumulativeCourseCompletionCSV(@PathVariable("state") String State,@PathVariable("district") String District,@PathVariable("block") String Block) throws ParseException, java.text.ParseException{
        adminService.getCumulativeCourseCompletionCSV(State,District,Block);
        return "Bulkimport";
    }*/

    @RequestMapping(value = "/getCumulativeCourseCompCSV1/{fromDate}/{toDate}", method = RequestMethod.GET)
    @ResponseBody
    public String getCumulativeCourseCompletionCSV1(@PathVariable("fromDate") Long fromDateMillis, @PathVariable("toDate") Long toDateMillis) throws ParseException, java.text.ParseException{
        Date fromDate = new Date(fromDateMillis);
        Date toDate = new Date(toDateMillis);
        User loggedInUser = userService.getCurrentUser();
        String loggedUserAccess=loggedInUser.getAccessLevel();
        AccessLevel loggedUserAccessLevel=AccessLevel.getLevel(loggedUserAccess);
        int Locationid=0;
        if(loggedUserAccess.equals("STATE")){
            Locationid=loggedInUser.getStateId().getStateId();
        }
        else if(loggedUserAccess.equals("DISTRICT")){
            Locationid=loggedInUser.getDistrictId().getDistrictId();
        }
        adminService.getCumulativeCourseCompletionCSV1(Locationid,loggedUserAccess,fromDate,toDate);
        return "Bulkimport";
    }
}
