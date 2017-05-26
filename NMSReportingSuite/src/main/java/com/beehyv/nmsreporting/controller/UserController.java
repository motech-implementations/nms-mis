package com.beehyv.nmsreporting.controller;

import com.beehyv.nmsreporting.business.*;
import com.beehyv.nmsreporting.dao.BlockDao;
import com.beehyv.nmsreporting.dao.DistrictDao;
import com.beehyv.nmsreporting.dao.StateDao;
import com.beehyv.nmsreporting.dto.PasswordDto;
import com.beehyv.nmsreporting.dto.UserDto;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.Role;
import com.beehyv.nmsreporting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by beehyv on 15/3/17.
 */
@Controller
@RequestMapping("/nms/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ModificationTrackerService modificationTrackerService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReportService reportService;

    private final Date bigBang = new Date(0);
    private final String documents = System.getProperty("user.home") +File.separator+ "Documents/";
    private final String reports = documents+"Reports/";
    private Calendar c =Calendar.getInstance();
    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public @ResponseBody List<User> getAllUsers() {
        return userService.findAllActiveUsers();
    }

//    @RequestMapping(value={"/list/{locationId}"})
//    public @ResponseBody List<User> getUsersByLocation(@PathVariable("locationId") Integer locationId) {
//        return userService.findAllActiveUsersByLocation(locationId);
//    }

    @RequestMapping(value={"/myUserList"})
    public @ResponseBody List<User> getMyUsers() {
        return userService.findMyUsers(userService.getCurrentUser());
    }

    @RequestMapping(value={"/roles"})
    public @ResponseBody List<Role> getRoles() {
        return roleService.getRoles();
    }

    @RequestMapping(value={"/currentUser"})
    public @ResponseBody User getCurrentUser() {
        return userService.getCurrentUser();
    }

    //To be changed
    @RequestMapping(value={"/tableList"})
    public @ResponseBody List<UserDto> getTableList() {
        List<UserDto> tabDto = new ArrayList<>();
        List<User> tabUsers = userService.findMyUsers(userService.getCurrentUser());
        String[] levels = {"National", "State", "District", "Block"};
        for(User user: tabUsers){
            UserDto user1 = new UserDto();
            user1.setId(user.getUserId());
            user1.setName(user.getFullName());
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmailId());
            user1.setPhoneNumber(user.getPhoneNumber());
            user1.setAccessLevel(user.getAccessLevel());
            try {
                user1.setState(user.getStateId().getStateName());
            } catch(NullPointerException e){
                user1.setState("");
            }
            try {
                user1.setDistrict(user.getDistrictId().getDistrictName());
            } catch(NullPointerException e){
                user1.setDistrict("");
            }
            try {
                user1.setBlock(user.getBlockId().getBlockName());
            } catch(NullPointerException e){
                user1.setBlock("");
            }
            user1.setAccessType(user.getRoleId().getRoleDescription());
            int a;
            try{
                a = user.getCreatedByUser().getUserId();
            } catch (NullPointerException e){
                a = 0;
            }
            int b = getCurrentUser().getUserId();
            user1.setCreatedBy(a == b || getCurrentUser().getRoleId().getRoleId() == 1);
            tabDto.add(user1);

        }
        return tabDto;
    }

    @RequestMapping(value={"/user/{userId}"})
    public @ResponseBody User getUserById(@PathVariable("userId") Integer userId) {
        return userService.findUserByUserId(userId);
    }

    @RequestMapping(value={"/dto/{userId}"})
    public @ResponseBody UserDto getUserDto(@PathVariable("userId") Integer userId) {
        User user = userService.findUserByUserId(userId);
        String[] levels = {"National", "State", "District", "Block"};
        UserDto user1 = new UserDto();
        user1.setId(user.getUserId());
        user1.setName(user.getFullName());
        user1.setUsername(user.getUsername());
        user1.setEmail(user.getEmailId());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setAccessLevel(user.getAccessLevel());
        try {
            user1.setState(user.getStateId().getStateId() + "");
        } catch(NullPointerException e){
            user1.setState("");
        }
        try {
            user1.setDistrict(user.getDistrictId().getDistrictId().toString());
        } catch(NullPointerException e){
            user1.setDistrict("");
        }
        try {
            user1.setBlock(user.getBlockId().getBlockId().toString());
        } catch(NullPointerException e){
            user1.setBlock("");
        }
        user1.setAccessType(user.getRoleId().getRoleId().toString());
        user1.setCreatedBy(true);
        return user1;
    }

    @RequestMapping(value = {"/createUser"}, method = RequestMethod.POST)
    @ResponseBody public Map<Integer, String> createNewUser(@RequestBody User user) {

//        ModificationTracker modification = new ModificationTracker();
//        modification.setModificationDate(new Date(System.currentTimeMillis()));
//        modification.setModificationDescription("Account creation");
//        modification.setModificationType(ModificationType.CREATE.getModificationType());
//        modification.setModifiedUserId(user);
//        modification.setModifiedByUserId(userService.findUserByUsername(getPrincipal()));
//        modificationTrackerService.saveModification(modification);

        return userService.createNewUser(user);
    }

    @RequestMapping(value = {"/updateUser"}, method = RequestMethod.POST)
    @ResponseBody public Map updateExistingUser(@RequestBody User user) {

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
        return userService.updateExistingUser(user);
    }

    @RequestMapping(value = {"/resetPassword"}, method = RequestMethod.POST)
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

    @RequestMapping(value = {"/deleteUser"}, method = RequestMethod.POST)
    public void deleteExistingUser(@RequestBody User user) {
        userService.deleteExistingUser(user);
//        ModificationTracker modification = new ModificationTracker();
//        modification.setModificationDate(new Date(System.currentTimeMillis()));
//        modification.setModificationType(ModificationType.DELETE.getModificationType());
//        modification.setModificationDescription("Account deletion");
//        modification.setModifiedUserId(user);
//        modification.setModifiedByUserId(userService.findUserByUsername(getPrincipal()));
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

   @RequestMapping(value = "/getReport", method = RequestMethod.POST,produces = "application/vnd.ms-excel")
   @ResponseBody
   public void getReports(@RequestBody ReportRequest reportRequest,HttpServletResponse response) throws ParseException, java.text.ParseException{

       String rootPath = "";
       String place = AccessLevel.NATIONAL.getAccessLevel();

       if(reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())){
            if(reportRequest.getCircleId()!=0){
                place=locationService.findCircleById(reportRequest.getCircleId()).getCircleName();
                rootPath+=place+"/";
            }
       }
       else {
           if (reportRequest.getStateId() != 0) {
               place = locationService.findStateById(reportRequest.getStateId()).getStateName();
               rootPath += place + "/";
           }

           if (reportRequest.getDistrictId() != 0) {
               place = locationService.findDistrictById(reportRequest.getDistrictId()).getDistrictName();
               rootPath += place + "/";
           }

           if (reportRequest.getBlockId() != 0) {
               place = locationService.findBlockById(reportRequest.getBlockId()).getBlockName();
               rootPath += place + "/";
           }
       }
       String filename= reportRequest.getReportType()+"_"+place+"_"+reportService.getMonthYear(reportRequest.getToDate())+".xlsx";
       rootPath = reports+reportRequest.getReportType()+"/"+rootPath+filename;

       response.setContentType("APPLICATION/OCTECT-STREAM");
       try {
           PrintWriter out=response.getWriter();
           response.setHeader("Content-Disposition","attachment;filename=\""+filename+"\"");
           File file=new File(rootPath);
           if(!(file.exists())){
               adminService.createSpecificReport(reportRequest);
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

//       if(reportRequest.getStateId()==0){
//           rootPath = "";
//       }else if(reportRequest.getDistrictId()==0){
//           rootPath = locationService.findStateById(reportRequest.getStateId()).getStateName();
//       }else if(reportRequest.getBlockId()==0){
//           rootPath = locationService.findStateById(reportRequest.getgetMonthYearStateId()).getStateName()+"/"+locationService.findDistrictById(reportRequest.getDistrictId()).getDistrictName();
//       }
//       if (reportRequest.getReportType().equals(ReportType.maCourse)) {
//
//           adminService.getCumulativeCourseCompletionFiles(bigBang,reportRequest.getToDate());
//       }

   }
}
