package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Created by beehyv on 15/3/17.
 */
@Controller
@RequestMapping("/nms/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFileHandler(@RequestParam("file") MultipartFile file) {
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


                return "You successfully uploaded file=" + name;
            } catch (Exception e) {
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
       HashMap object= adminService.startBulkDataImport();
       return object;
    }

    @RequestMapping(value = "/getBulkDataImportCSV", method = RequestMethod.GET)
    @ResponseBody
    public String getBulkDataImportCSV() throws ParseException, java.text.ParseException{
        adminService.getBulkDataImportCSV();
        return "Bulkimport";
    }


}
