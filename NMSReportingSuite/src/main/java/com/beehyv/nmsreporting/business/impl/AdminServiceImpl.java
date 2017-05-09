package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.AccessType;
import com.beehyv.nmsreporting.model.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by beehyv on 19/4/17.
 */

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private DistrictDao districtDao;

    @Autowired
    private BlockDao blockDao;

    @Autowired
    private LocationDao locationDao;
    @Override
    public HashMap startBulkDataImport(User loggedInUser) {
        Pattern pattern;
        Matcher matcher;
        Map<Integer,String> errorCreatingUsers=new HashMap<Integer,String>();
        createPropertiesFileForFileLocation();
        String fjilename = null;
        fjilename = retrievePropertiesFromFileLocationProties();

        if (fjilename == null) {
            fjilename = System.getProperty("user.home")
                    + "/Documents/BulkImportDatacr7ms10.csv";
            System.out.println("fileLocationproperties not working");
        }
        XSSFRow row;
        BufferedReader fis = null;
        try {
            fis = new BufferedReader(new FileReader(fjilename));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                String line = "";
                String cvsSplitBy = ",";
                try {

                    int linNumber=1;
                    if((line = fis.readLine()) != null){

                    }
                    while ((line = fis.readLine()) != null) {
                        linNumber++;

                        // use comma as separator
                        String[] Line = line.split(cvsSplitBy);

                        User user = new User();
                        Role role;
                        Location locaation;
                        State state;





                        String userName=Line[6];
                        if(userName==""){
                            Integer rowNum=linNumber;
                            String userNameError="Please specify the username for user";
                            errorCreatingUsers.put(rowNum,userNameError);
                            continue;
                        }
                        User UsernameExist=null;
                        UsernameExist=userDao.findByUserName(Line[6]);
                        if(UsernameExist==null){
                            user.setUsername(Line[6]);
                        }
                        else{
                            Integer rowNum=linNumber;
                            String userNameError="Username already exists.";
                            errorCreatingUsers.put(rowNum,userNameError);
                            continue;
                        }


                        int loggedUserRole=loggedInUser.getRoleId().getRoleId();

                        String loggedUserAccess=loggedInUser.getAccessLevel();
                        AccessLevel loggedUserAccessLevel=AccessLevel.getLevel(loggedUserAccess);
                        String userPhone=Line[4];
                        if(userPhone==""){
                            Integer rowNum=linNumber;
                            String userNameError="Please specify the phone number for user";
                            errorCreatingUsers.put(rowNum,userNameError);
                            continue;
                        }

                        String regexStr1 = "^[0-9]*$";
                        String regexStr2 = "^[0-9]{10}$";
                        if (!(userPhone.matches(regexStr1))||!(userPhone.matches(regexStr2))){
                            Integer rowNum=linNumber;
                            String userNameError="Please check the format of phone number for user";
                            errorCreatingUsers.put(rowNum,userNameError);
                            continue;
                        }
                        user.setPassword(Line[4]);
                       

                        user.setFullName(Line[0]);


                        user.setPhoneNumber(Line[4]);

                        String userEmail=Line[5];
                        if(userEmail==""){
                            Integer rowNum=linNumber;
                            String userNameError="Please specify the Email for user";
                            errorCreatingUsers.put(rowNum,userNameError);
                            continue;
                        }
                        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                        pattern = Pattern.compile(EMAIL_PATTERN);
                        matcher = pattern.matcher(userEmail);
                        boolean validate=matcher.matches();
                        if(validate) {
                            user.setEmailId(Line[5]);
                        }
                        else{
                            Integer rowNum=linNumber;
                            String userNameError="Please enter the valid Email for user";
                            errorCreatingUsers.put(rowNum,userNameError);
                            continue;
                        }
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = null;
                        try {
                            date = sdf1.parse(Line[7]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());

                        user.setCreationDate((sqlStartDate) );

                        /*user.setCreatedByUser(loggedInUser);*/

                        List<Role> userRole=roleDao.findByRoleDescription(Line[8]);

                        String State=Line[1];
                        String District=Line[2];
                        String Block=Line[3];



                        boolean isLevel= AccessLevel.isLevel(Line[8]);

                        if(!(isLevel)){
                            Integer rowNum=linNumber;
                            String userNameError="Please specify the access level for user";
                            errorCreatingUsers.put(rowNum,userNameError);
                            continue;
                        }

                        boolean isType= AccessType.isType(Line[9]);

                        if(!(isType)){
                            Integer rowNum=linNumber;
                            String userNameError="Please specify the role for user";
                            errorCreatingUsers.put(rowNum,userNameError);
                            continue;
                        }


                        if(userRole==null||userRole.size()==0){
                            Integer rowNum=linNumber;
                            String userNameError="Please specify the role of user";
                            errorCreatingUsers.put(rowNum,userNameError);
                            continue;
                        }

                        int userRoleId=userRole.get(0).getRoleId();

                        String UserRole=AccessType.getType(Line[9]);

                        AccessLevel accessLevel= AccessLevel.getLevel(Line[8]);

                        if(UserRole=="ADMIN"){
                            if((accessLevel==AccessLevel.NATIONAL)||(accessLevel==AccessLevel.STATE)){
                                Integer rowNum=linNumber;
                                String authorityError="You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum,authorityError);
                                continue;
                            }
                            else if(loggedUserAccessLevel==AccessLevel.DISTRICT){
                                Integer rowNum=linNumber;
                                String authorityError="You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum,authorityError);
                                continue;
                            }
                            else{
                                List<State> userStateList=stateDao.findByName(State);
                                List<District> userDistrictList=districtDao.findByName(District);
                                District userDistrict=null;
                                State userState=null;
                                if(userDistrictList.size()==1){

                                    userDistrict=userDistrictList.get(0);
                                    userState=userDistrict.getStateOfDistrict();
                                }
                                else{
                                    for(District district:userDistrictList){
                                        State parent=district.getStateOfDistrict();
                                        if((userStateList!=null)&&(userStateList.size()!=0)){
                                            if(parent.getStateId()==userStateList.get(0).getStateId()){
                                                userDistrict=district;
                                                userState=parent;
                                                break;
                                            }
                                        }

                                    }
                                }
                                if(userDistrict==null){
                                    Integer rowNum=linNumber;
                                    String authorityError="Please enter the valid district for this user.";
                                    errorCreatingUsers.put(rowNum,authorityError);
                                    continue;
                                }
                                else {
                                    if(loggedInUser.getStateId().getStateId()!=userState.getStateId()){
                                        Integer rowNum=linNumber;
                                        String authorityError="You don't have authority to create this user.";
                                        errorCreatingUsers.put(rowNum,authorityError);
                                        continue;
                                    }
                                    else {
                                        boolean isAdminAvailable = userDao.isAdminCreated(userDistrict);
                                        if (!(isAdminAvailable)) {
                                            user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                            user.setDistrictId(userDistrict);
                                            user.setStateId(userState);
                                        } else {
                                            Integer rowNum = linNumber;
                                            String authorityError = "Admin is available for this district.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                        if(UserRole=="USER"){
                            if(loggedUserAccessLevel.ordinal()>accessLevel.ordinal()){
                                Integer rowNum=linNumber;
                                String authorityError="You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum,authorityError);
                                continue;
                            }
                            else{
                                if(accessLevel==AccessLevel.NATIONAL){
                                    user.setAccessLevel(AccessLevel.NATIONAL.getAccessLevel());
                                }
                                else if(accessLevel==AccessLevel.STATE){
                                    user.setAccessLevel(accessLevel.getAccessLevel());
                                    List<State> userStateList=stateDao.findByName(State);
                                    if((userStateList==null)||(userStateList.size()==0)){
                                        Integer rowNum=linNumber;
                                        String authorityError="Please enter the valid State for this user.";
                                        errorCreatingUsers.put(rowNum,authorityError);
                                        continue;
                                    }
                                    else{
                                        if(loggedUserAccessLevel==AccessLevel.STATE){
                                            if(loggedInUser.getStateId().getStateId()!=userStateList.get(0).getStateId())
                                            {
                                                Integer rowNum=linNumber;
                                                String authorityError="You don't have authority to create this user.";
                                                errorCreatingUsers.put(rowNum,authorityError);
                                                continue;
                                            }
                                            else user.setStateId(userStateList.get(0));
                                        }

                                        else user.setStateId(userStateList.get(0));
                                    }
                                }
                                else if(accessLevel==AccessLevel.DISTRICT){
                                    List<State> userStateList=stateDao.findByName(State);
                                    List<District> userDistrictList=districtDao.findByName(District);
                                    District userDistrict=null;
                                    State userState=null;
                                    if(userDistrictList.size()==1){
                                        userDistrict=userDistrictList.get(0);
                                        userState=userDistrict.getStateOfDistrict();
                                    }
                                    else{
                                        for(District district:userDistrictList){
                                            State parent=district.getStateOfDistrict();
                                            if((userStateList!=null)&&(userStateList.size()!=0)){
                                                if(parent.getStateId()==userStateList.get(0).getStateId()){
                                                    userDistrict=district;
                                                    userState=parent;
                                                    break;
                                                }
                                            }

                                        }
                                    }
                                    if(userDistrict==null){
                                        Integer rowNum=linNumber;
                                        String authorityError="Please enter the valid district for this user.";
                                        errorCreatingUsers.put(rowNum,authorityError);
                                        continue;
                                    }
                                    else {
                                        if(((loggedUserAccessLevel==AccessLevel.STATE)&&(loggedInUser.getStateId().getStateId()!=userState.getStateId()))||((loggedUserAccessLevel==AccessLevel.DISTRICT)&&(loggedInUser.getDistrictId().getDistrictId()!=userDistrict.getDistrictId()))){
                                            Integer rowNum=linNumber;
                                            String authorityError="You don't have authority to create this user.";
                                            errorCreatingUsers.put(rowNum,authorityError);
                                            continue;
                                        }
                                        else {
                                                user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                                user.setDistrictId(userDistrict);
                                                user.setStateId(userState);

                                        }
                                    }


                                }
                                else {
                                    user.setAccessLevel(AccessLevel.BLOCK.getAccessLevel());
                                    List<State> userStateList=stateDao.findByName(State);
                                    List<District> userDistrictList=districtDao.findByName(District);
                                    List<Block> userBlockList=blockDao.findByName(Block);
                                    State userState=null;
                                    District userDistrict=null;
                                    Block userBlock=null;
                                    if(userBlockList.size()==1){
                                        userBlock= userBlockList.get(0);
                                        userDistrict=userBlock.getDistrictOfBlock();
                                        userState=userDistrict.getStateOfDistrict();

                                    }
                                    else if ((userBlockList.size()==0)||userBlockList==null){
                                        Integer rowNum=linNumber;
                                        String authorityError="Please enter the valid Block for this user.";
                                        errorCreatingUsers.put(rowNum,authorityError);
                                    }
                                    else{
                                        List<Block> commonDistrict = null;
                                        for (Block block:userBlockList){
                                            District parent=block.getDistrictOfBlock();
                                            if(userDistrictList.size()>0){
                                                for(District district:userDistrictList){
                                                    if(parent.getDistrictId()==district.getDistrictId()){
                                                        commonDistrict.add(block);
                                                    }
                                                }

                                            }
                                        }
                                        for(Block block:commonDistrict){
                                            State parent=block.getStateOfBlock();
                                            if(userState!=null){
                                                if(parent.getStateId()==userStateList.get(0).getStateId()){
                                                    userBlock=block;
                                                    userDistrict=userBlock.getDistrictOfBlock();
                                                    userState=userBlock.getStateOfBlock();
                                                    break;
                                                }

                                            }
                                        }
                                        if(userBlock==null){
                                            Integer rowNum=linNumber;
                                            String authorityError="Please enter the valid location for this user.";
                                            errorCreatingUsers.put(rowNum,authorityError);
                                            continue;
                                        }
                                        else{
                                            if(((loggedUserAccessLevel==AccessLevel.STATE)&&(loggedInUser.getStateId().getStateId()!=userState.getStateId()))||((loggedUserAccessLevel==AccessLevel.DISTRICT)&&(loggedInUser.getDistrictId().getDistrictId()!=userDistrict.getDistrictId())))
                                            {
                                                Integer rowNum=linNumber;
                                                String authorityError="You don't have authority to create this user.";
                                                errorCreatingUsers.put(rowNum,authorityError);
                                                continue;
                                            }
                                            else{
                                                user.setBlockId(userBlock);
                                                user.setStateId(userState);
                                                user.setDistrictId(userDistrict);
                                            }
                                        }
                                    }

                                }

                            }








                        }

                       /* if(userRoleId==1||userRoleId==3){
                            Integer rowNum=linNumber;
                            String authorityError="You don't have authority to create this user.";
                            errorCreatingUsers.put(rowNum,authorityError);
                            continue;
                        }


                        if(userRoleId==1||userRoleId==3){
                             Integer rowNum=linNumber;
                            String authorityError="You don't have authority to create this user.";
                            errorCreatingUsers.put(rowNum,authorityError);
                            continue;
                        }
                        user.setRoleId(userRole.get(0));

                        if((userRoleId==2)){
                            if(loggedUserRole<1){
                                 Integer rowNum=linNumber;
                                String authorityError="You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum,authorityError);
                                continue;
                            }
                            else {
                                Location userLocation = locationDao.findByLocationId(1);
                                user.setLocationId(userLocation);
                            }
                        }
                        if((userRoleId==4)){
                            List<Location> userLocation=locationDao.findByLocation(State);

                            if((userLocation==null)||(userLocation.size()==0)){
                                 Integer rowNum=linNumber;
                                String authorityError="Please enter the valid location for this user.";
                                errorCreatingUsers.put(rowNum,authorityError);
                                continue;
                            }
                            else{
                                if((loggedUserRole==1)||((loggedUserRole==3)&&(loggedInUser.getLocationId().getLocationId()==userLocation.get(0).getLocationId())))
                                {
                                    user.setLocationId(userLocation.get(0));

                                }
                                else{
                                     Integer rowNum=linNumber;
                                    String authorityError="You don't have authority to create this user.";
                                    errorCreatingUsers.put(rowNum,authorityError);
                                    continue;
                                }
                            }


                        }
                        if((userRoleId==5)){
                            List<Location> userState=locationDao.findByLocation(State);
                            List<Location> userDistrict=locationDao.findByLocation(District);
                            Location userlocation=null;
                            if(userDistrict.size()==1){
                                userlocation=userDistrict.get(0);
                            }
                            else{
                                for(Location location:userDistrict){
                                    Location parent=location.getReferenceId();
                                    if(userState!=null){
                                        if(parent.getLocationId()==userState.get(0).getLocationId()){
                                            userlocation=location;
                                            break;
                                        }
                                    }

                                }
                            }

                            if(userlocation==null){
                                 Integer rowNum=linNumber;
                                String authorityError="Please enter the valid location for this user.";
                                errorCreatingUsers.put(rowNum,authorityError);
                                continue;
                            }
                            else{

                                if((loggedUserRole==1)||((loggedUserRole==3)&&(loggedInUser.getLocationId().getLocationId()==userlocation.getReferenceId().getLocationId())))
                                {
                                    boolean isAdminAvailable=userDao.isAdminCreated(userlocation,userRole.get(0));
                                    if(!isAdminAvailable){
                                        user.setLocationId(userlocation);
                                    }
                                    else{
                                         Integer rowNum=linNumber;
                                        String authorityError="Admin is available for this District.";
                                        errorCreatingUsers.put(rowNum,authorityError);
                                        continue;
                                    }


                                }
                                else {
                                     Integer rowNum=linNumber;
                                    String authorityError="You don't have authority to create this user.";
                                    errorCreatingUsers.put(rowNum,authorityError);
                                    continue;
                                }
                            }


                        }
                        if((userRoleId==6)){
                            List<Location> userState=locationDao.findByLocation(State);
                            List<Location> userDistrict=locationDao.findByLocation(District);
                            Location userlocation=null;
                            if(userDistrict.size()==1){
                                userlocation=userDistrict.get(0);
                            }
                            else{
                                for(Location location:userDistrict){
                                    Location parent=location.getReferenceId();
                                    if(userState!=null){
                                        if(parent.getLocationId()==userState.get(0).getLocationId()){
                                            userlocation=location;
                                            break;
                                        }
                                    }

                                }
                            }

                            if(userlocation==null){
                                 Integer rowNum=linNumber;
                                String authorityError="Please enter the valid location for this user.";
                                errorCreatingUsers.put(rowNum,authorityError);
                                continue;
                            }
                            else{
                                if((loggedUserRole==1)||((loggedUserRole==3)&&(loggedInUser.getLocationId().getLocationId()==userlocation.getReferenceId().getLocationId()))||((loggedUserRole==5)&&(loggedInUser.getLocationId().getLocationId()==userlocation.getLocationId())))
                                {
                                    user.setLocationId(userlocation);

                                }
                                else {
                                     Integer rowNum=linNumber;
                                    String authorityError="You don't have authority to create this user.";
                                    errorCreatingUsers.put(rowNum,authorityError);
                                    continue;
                                }
                            }


                        }
                        if((userRoleId==7)){
                            List<Location> userState=locationDao.findByLocation(State);
                            List<Location> userDistrict=locationDao.findByLocation(District);
                            List<Location> userBlock=locationDao.findByLocation(Block);
                            Location userlocation=null;
                            if(userBlock.size()==1){
                                user.setLocationId(userBlock.get(0));
                            }
                            else if ((userBlock.size()==0)||userBlock==null){
                                 Integer rowNum=linNumber;
                                String authorityError="Please enter the valid Block for this user.";
                                errorCreatingUsers.put(rowNum,authorityError);
                            }
                            else{
                                List<Location> commonDistrict = null;
                                for (Location location:userBlock){
                                    Location parent=location.getReferenceId();
                                    if(userDistrict.size()>0){
                                        for(Location district:userDistrict){
                                            if(parent.getLocationId()==district.getLocationId()){
                                                commonDistrict.add(location);
                                            }
                                        }

                                    }
                                }
                                for(Location location:commonDistrict){
                                    Location parent=location.getReferenceId().getReferenceId();
                                    if(userState!=null){
                                        if(parent.getLocationId()==userState.get(0).getLocationId()){
                                            userlocation=location;
                                            break;
                                        }

                                    }
                                }
                                if(userlocation==null){
                                     Integer rowNum=linNumber;
                                    String authorityError="Please enter the valid location for this user.";
                                    errorCreatingUsers.put(rowNum,authorityError);
                                    continue;
                                }
                                else{
                                    if((loggedUserRole==1)||((loggedUserRole==3)&&(loggedInUser.getLocationId().getLocationId()==userlocation.getReferenceId().getReferenceId().getLocationId()))||((loggedUserRole==5)&&(loggedInUser.getLocationId().getLocationId()==userlocation.getReferenceId().getLocationId())))
                                    {
                                        user.setLocationId(userlocation);
                                    }
                                    else{
                                         Integer rowNum=linNumber;
                                        String authorityError="You don't have authority to create this user.";
                                        errorCreatingUsers.put(rowNum,authorityError);
                                        continue;
                                    }
                                }
                            }
                        }*/
                        userDao.saveUser(user);


                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                /*BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ",";
                XSSFWorkbook workbook = null;
                try {
                    workbook = new XSSFWorkbook(fis);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (workbook != null) {

                        XSSFSheet spreadsheet = workbook.getSheetAt(0);
                        Iterator<Row> rowIterator = spreadsheet.iterator();
                        if(rowIterator.hasNext()){
                            rowIterator.next();
                        }
                        while (rowIterator.hasNext()) {
                            User user = new User();
                            Role role;
                            Location locaation;


                            row = (XSSFRow) rowIterator.next();
                            Iterator<Cell> cellIterator = row.cellIterator();
                           *//* user.setUsername(row.getCell(6).getRawValue());*//*
                            String userName=row.getCell(6).getStringCellValue();
                            if(userName==""){
                                Integer rowNum=linNumber;
                                String userNameError="Please specify the username for user";
                                errorCreatingUsers.put(rowNum,userNameError);
                                continue;
                            }
                            User UsernameExist=null;
                            UsernameExist=userDao.findByUserName(row.getCell(6).getStringCellValue());
                            if(UsernameExist==null){
                                user.setUsername(row.getCell(6).getStringCellValue());
                            }
                            else{
                                Integer rowNum=linNumber;
                                String userNameError="Username already exists.";
                                errorCreatingUsers.put(rowNum,userNameError);
                                continue;
                            }

                            *//*UserDetails userdetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                            String loggedUserName=userdetails.getUsername();
                            User loggedInUser=userDao.findByUserName(loggedUserName);
                            int loggedUserRole=loggedInUser.getRoleId().getRoleId();*//*

                            String userPhone=row.getCell(4).getRawValue();
                            if(userPhone==""){
                                Integer rowNum=linNumber;
                                String userNameError="Please specify the phone number for user";
                                errorCreatingUsers.put(rowNum,userNameError);
                                continue;
                            }

                            String regexStr1 = "^[0-9]*$";
                            String regexStr2 = "^[0-9]{10}$";
                            if (!(userPhone.matches(regexStr1))||!(userPhone.matches(regexStr2))){
                                Integer rowNum=linNumber;
                                String userNameError="Please check the format of phone number for user";
                                errorCreatingUsers.put(rowNum,userNameError);
                                continue;
                            }
                            user.setPassword(row.getCell(4).getRawValue());
                            *//*String userFullName=row.getCell(1).getRawValue();*//*

                            user.setFullName(row.getCell(0).getStringCellValue());


                            user.setPhoneNumber(row.getCell(4).getRawValue());

                            String userEmail=row.getCell(5).getStringCellValue();
                            if(userEmail==""){
                                Integer rowNum=linNumber;
                                String userNameError="Please specify the Email for user";
                                errorCreatingUsers.put(rowNum,userNameError);
                                continue;
                            }
                            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


                            user.setEmailId(row.getCell(5).getStringCellValue());
                            java.sql.Date sqlDate = new java.sql.Date(row.getCell(7).getDateCellValue().getTime());
                            user.setCreationDate((sqlDate) );

                            *//*user.setCreatedByUser(loggedInUser);*//*

                            Role userRole=roleDao.findByRoleDescription(row.getCell(8).getStringCellValue());
                            if(userRole==null){
                                Integer rowNum=linNumber;
                                String userNameError="Please specify the role of user";
                                errorCreatingUsers.put(rowNum,userNameError);
                                continue;
                            }

                            int userRoleId=userRole.getRoleId();
                            if(userRoleId==1||userRoleId==3){
                                Integer rowNum=linNumber;
                                String authorityError="You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum,authorityError);
                                continue;
                            }
                            user.setRoleId(userRole);
                            String State=row.getCell(1).getStringCellValue();
                            String District=row.getCell(2).getStringCellValue();
                            String Block=row.getCell(3).getStringCellValue();
                            if((userRoleId==2)){
                                Location userLocation=locationDao.findByLocationId(1);
                                user.setLocationId(userLocation);
                            }
                            if((userRoleId==4)){
                                List<Location> userLocation=locationDao.findByLocation(State);
                                if((userLocation==null)||(userLocation.size()==0)){
                                    Integer rowNum=linNumber;
                                    String authorityError="Please enter the valid location for this user.";
                                    errorCreatingUsers.put(rowNum,authorityError);
                                    continue;
                                }
                                else{
                                    user.setLocationId(userLocation.get(0));
                                }


                            }
                            if((userRoleId==5)||(userRoleId==6)){
                                List<Location> userState=locationDao.findByLocation(State);
                                List<Location> userDistrict=locationDao.findByLocation(District);
                                Location userlocation=null;
                                if(userDistrict.size()==1){
                                    userlocation=userDistrict.get(0);
                                }
                                else{
                                    for(Location location:userDistrict){
                                        Location parent=location.getReferenceId();
                                        if(userState!=null){
                                            if(parent.getLocationId()==userState.get(0).getLocationId()){
                                                userlocation=location;
                                                break;
                                            }
                                        }

                                    }
                                }

                                if(userlocation==null){
                                    Integer rowNum=linNumber;
                                    String authorityError="Please enter the valid location for this user.";
                                    errorCreatingUsers.put(rowNum,authorityError);

                                }
                                else{
                                    user.setLocationId(userlocation);
                                }


                            }
                            if((userRoleId==7)){
                                List<Location> userState=locationDao.findByLocation(State);
                                List<Location> userDistrict=locationDao.findByLocation(District);
                                List<Location> userBlock=locationDao.findByLocation(Block);
                                Location userlocation=null;
                                if(userBlock.size()==1){
                                    user.setLocationId(userBlock.get(0));
                                }
                                else if ((userBlock.size()==0)||userBlock==null){
                                    Integer rowNum=linNumber;
                                    String authorityError="Please enter the valid Block for this user.";
                                    errorCreatingUsers.put(rowNum,authorityError);
                                }
                                else{
                                    List<Location> commonDistrict = null;
                                    for (Location location:userBlock){
                                        Location parent=location.getReferenceId();
                                        if(userDistrict.size()>0){
                                            for(Location district:userDistrict){
                                                if(parent.getLocationId()==district.getLocationId()){
                                                   commonDistrict.add(location);
                                                }
                                            }

                                        }
                                    }
                                    for(Location location:commonDistrict){
                                        Location parent=location.getReferenceId().getReferenceId();
                                        if(userState!=null){
                                            if(parent.getLocationId()==userState.get(0).getLocationId()){
                                                userlocation=location;
                                                break;
                                            }

                                        }
                                    }
                                    if(userlocation==null){
                                        Integer rowNum=linNumber;
                                        String authorityError="Please enter the valid location for this user.";
                                        errorCreatingUsers.put(rowNum,authorityError);

                                    }
                                    else{
                                        user.setLocationId(userlocation);
                                    }
                                }
                            }
                           userDao.saveUser(user);

                        }
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }*/
            }

    }
        return (HashMap) errorCreatingUsers;
    }

    public void getBulkDataImportCSV() {
      /*  //Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Employee Info ");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map < String, Object[] > empinfo =
                new TreeMap < String, Object[] >();
        empinfo.put( "1", new Object[] {
                "Full Name", "STATE", "DISTRICT", "BLOCK", "Phone number", "Email ID", "UserName", "Creation Date", "Role" });

        Set < String > keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid)
        {
            row = spreadsheet.createRow(rowid++);
            Object [] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr)
            {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("Writesheet.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        String NEW_LINE_SEPARATOR = "\n";



        //CSV file header

        String FILE_HEADER = "Full Name, STATE, DISTRICT, BLOCK, Phone number, Email ID, UserName, Creation Date, Access Level,Role";
        FileWriter fileWriter = null;
        try {

            fileWriter = new FileWriter("BulkImportData.csv");



            //Write the CSV file header

            fileWriter.append(FILE_HEADER.toString());



            //Add a new line separator after the header

            fileWriter.append(NEW_LINE_SEPARATOR);



            //Write a new student object list to the CSV file


            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {

            System.out.println("Error in CsvFileWriter !!!");

            e.printStackTrace();
                    } finally {



            try {

                fileWriter.flush();

                fileWriter.close();

            } catch (IOException e) {

                System.out.println("Error while flushing/closing fileWriter !!!");

                e.printStackTrace();

            }



        }


    }

    @Override
    public void getCumulativeCourseCompletionCSV(String State,String District,String Block) {
        UserDetails userdetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loggedUserName=userdetails.getUsername();
        User loggedInUser=userDao.findByUserName(loggedUserName);
        int loggedUserRole=loggedInUser.getRoleId().getRoleId();
        String rootPath = System.getProperty("user.home") + File.separator + "Documents/CumulativeCourseCompletionCSVs";
        File dir = new File(rootPath);
        if (!dir.exists())
            dir.mkdirs();
        if(loggedUserRole==1||loggedUserRole==2){
           List<Location> States=locationDao.getChildLocations(1);
            for (Location state:States)
                {
                    String stateName=state.getLocation();
                    int stateId=state.getLocationId();
                    List<Location> Districts=locationDao.getChildLocations(stateId);
                    for (Location district:Districts){
                        String districtName=district.getLocation();
                        int districtId=district.getLocationId();
                        List<Location> Blocks=locationDao.getChildLocations(districtId);
                        for (Location block:Blocks){
                            String blockName=block.getLocation();
                            String rootPath1 = System.getProperty("user.home") + File.separator + "Documents/CumulativeCourseCompletionCSVs/"+stateName+"/"+districtName+"/"+blockName;
                            File dir1 = new File(rootPath1);
                            if (!dir1.exists())
                                dir1.mkdirs();
                        }
                    }

                 }


        }
        //Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Employee Info ");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map < String, Object[] > empinfo =
                new TreeMap < String, Object[] >();
        empinfo.put( "1", new Object[] {
                "Full Name","Mobile Number", "STATE", "DISTRICT", "BLOCK", "Taluka", "Health Facility", "Health Sub Facility", "Creation Date", "Role" });

        Set < String > keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid)
        {
            row = spreadsheet.createRow(rowid++);
            Object [] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr)
            {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("Writesheet.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCumulativeCourseCompletionCSV1(Integer LocationId) {
        List<Location> States=locationDao.getChildLocations(LocationId);
        for (Location state:States)
        {
            String stateName=state.getLocation();
            String rootPathState = System.getProperty("user.home") + File.separator + "Documents/CumulativeCourseCompletionCSVs/"+stateName;
            File dirState = new File(rootPathState);
            if (!dirState.exists())
                dirState.mkdirs();
            int stateId=state.getLocationId();
            List<Location> Districts=locationDao.getChildLocations(stateId);
            for (Location district:Districts){
                String districtName=district.getLocation();

                String rootPathDistrict = rootPathState+"/"+districtName;
                File dirDistrict = new File(rootPathDistrict);
                if (!dirDistrict.exists())
                    dirDistrict.mkdirs();
                int districtId=district.getLocationId();
                List<Location> Blocks=locationDao.getChildLocations(districtId);
                for (Location block:Blocks){
                    String blockName=block.getLocation();
                    String rootPathblock = rootPathDistrict+"/"+blockName;
                    File dirBlock = new File(rootPathblock);
                    if (!dirBlock.exists())
                        dirBlock.mkdirs();

                }
            }

        }
    }

    private String retrievePropertiesFromFileLocationProties() {
        Properties prop = new Properties();
        InputStream input = null;
        String fileLocation =null;

        try {

            input = new FileInputStream("fileLocation.properties");

            // load a properties file
            prop.load(input);
            fileLocation = prop.getProperty("fileLocation");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileLocation;
    }

    private void createPropertiesFileForFileLocation() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("fileLocation.properties");


            prop.setProperty("fileLocation", System.getProperty("user.home")
                    + "/Documents/BulkImportDatacr7ms10.csv");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
