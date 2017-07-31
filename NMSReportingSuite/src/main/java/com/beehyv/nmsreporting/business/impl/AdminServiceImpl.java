package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.AccessLevel;
import com.beehyv.nmsreporting.enums.AccessType;
import com.beehyv.nmsreporting.enums.ReportType;
import com.beehyv.nmsreporting.model.*;
import com.beehyv.nmsreporting.utils.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.beehyv.nmsreporting.utils.ServiceFunctions.StReplace;

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
    private VillageDao villageDao;

    @Autowired
    private HealthFacilityDao healthFacilityDao;

    @Autowired
    private HealthSubFacilityDao healthSubFacilityDao;

    @Autowired
    private TalukaDao talukaDao;

    @Autowired
    private MACourseAttemptDao maCourseAttemptDao;

    @Autowired
    private KilkariSixWeeksNoAnswerDao kilkariSixWeeksNoAnswerDao;

    @Autowired
    private KilkariLowUsageDao kilkariLowUsageDao;

    @Autowired
    private KilkariSelfDeactivatedDao kilkariSelfDeactivatedDao;

    @Autowired
    private FrontLineWorkersDao frontLineWorkersDao;

    @Autowired
    private AnonymousUsersDao anonymousUsersDao;

    @Autowired
    private CircleDao circleDao;

    private final String documents = System.getProperty("user.home") +File.separator+ "Documents/";
    private final String reports = documents+"Reports/";
    private Calendar c =Calendar.getInstance();
    @Override
    public HashMap startBulkDataImport(User loggedInUser) {
        Pattern pattern;
        Matcher matcher;
        Map<Integer, String> errorCreatingUsers = new HashMap<Integer, String>();
        createPropertiesFileForFileLocation();
        String fileName = null;
        fileName = retrievePropertiesFromFileLocationProperties();
        if (fileName == null) {
            fileName = documents+"BulkImportDatacr7ms10.csv";
            System.out.println("fileLocationproperties not working");
        }
        XSSFRow row;
        BufferedReader fis = null;
        try {
            fis = new BufferedReader(new FileReader(fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                String line = "";
                String cvsSplitBy = ",";
                try {
                    int lineNumber = 1;
                    if ((line = fis.readLine()) != null) {
                    }
                    while ((line = fis.readLine()) != null) {
                        lineNumber++;
                        // use comma as separator
                        String[] Line = line.split(cvsSplitBy);

                        User user = new User();
                        Role role;
                        State state;
                        String userName = Line[6];
                        if (userName.equals("")) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the username for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        User UsernameExist = null;
                        UsernameExist = userDao.findByUserName(Line[6]);
                        if (UsernameExist == null) {
                            user.setUsername(Line[6]);
                        } else {
                            Integer rowNum = lineNumber;
                            String userNameError = "Username already exists.";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        int loggedUserRole = loggedInUser.getRoleId();
                        String loggedUserAccess = loggedInUser.getAccessLevel();
                        AccessLevel loggedUserAccessLevel = AccessLevel.getLevel(loggedUserAccess);
                        String userPhone = Line[4];
                        if (userPhone.equals("")) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the phone number for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        String regexStr1 = "^[0-9]*$";
                        String regexStr2 = "^[0-9]{10}$";
                        if (!(userPhone.matches(regexStr1)) || !(userPhone.matches(regexStr2))) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please check the format of phone number for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        user.setPassword(Line[4]);
                        user.setFullName(Line[0]);
                        user.setPhoneNumber(Line[4]);
                        String userEmail = Line[5];
                        if (userEmail.equals("")) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the Email for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                        pattern = Pattern.compile(EMAIL_PATTERN);
                        matcher = pattern.matcher(userEmail);
                        boolean validate = matcher.matches();
                        if (validate) {
                            user.setEmailId(Line[5]);
                        } else {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please enter the valid Email for user";
                            errorCreatingUsers.put(rowNum, userNameError);
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
                        user.setCreationDate((sqlStartDate));
                        /*user.setCreatedByUser(loggedInUser);*/
                        List<Role> userRole = roleDao.findByRoleDescription(Line[7]);
                        String State = Line[1];
                        String District = Line[2];
                        String Block = Line[3];
                        boolean isLevel = AccessLevel.isLevel(Line[7]);
                        if (!(isLevel)) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the access level for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        boolean isType = AccessType.isType(Line[8]);
                        if (!(isType)) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the role for user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        if (userRole == null || userRole.size() == 0) {
                            Integer rowNum = lineNumber;
                            String userNameError = "Please specify the role of user";
                            errorCreatingUsers.put(rowNum, userNameError);
                            continue;
                        }
                        int userRoleId = userRole.get(0).getRoleId();
                        String UserRole = AccessType.getType(Line[8]);
                        AccessLevel accessLevel = AccessLevel.getLevel(Line[7]);
                        if (UserRole.equalsIgnoreCase("ADMIN")) {
                            if ((accessLevel == AccessLevel.NATIONAL) || (accessLevel == AccessLevel.STATE)) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            } else if (loggedUserAccessLevel == AccessLevel.DISTRICT) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            } else {
                                List<State> userStateList = stateDao.findByName(State);
                                List<District> userDistrictList = districtDao.findByName(District);
                                District userDistrict = null;
                                State userState = null;
                                if (userDistrictList.size() == 1) {

                                    userDistrict = userDistrictList.get(0);
                                    userState = stateDao.findByStateId(userDistrict.getStateOfDistrict());
                                } else {
                                    for (District district : userDistrictList) {
                                        State parent = stateDao.findByStateId(district.getStateOfDistrict());
                                        if ((userStateList != null) && (userStateList.size() != 0)) {
                                            if (parent.getStateId().equals(userStateList.get(0).getStateId())) {
                                                userDistrict = district;
                                                userState = parent;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (userDistrict == null) {
                                    Integer rowNum = lineNumber;
                                    String authorityError = "Please enter the valid district for this user.";
                                    errorCreatingUsers.put(rowNum, authorityError);
                                    continue;
                                } else {
                                    if (!loggedInUser.getStateId().equals(userState.getStateId())) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "You don't have authority to create this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        boolean isAdminAvailable = userDao.isAdminCreated(userDistrict);
                                        if (!(isAdminAvailable)) {
                                            user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                            user.setDistrictId(userDistrict.getDistrictId());
                                            user.setStateId(userState.getStateId());
                                        } else {
                                            Integer rowNum = lineNumber;
                                            String authorityError = "Admin is available for this district.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                        if (UserRole.equalsIgnoreCase("USER")) {
                            if (loggedUserAccessLevel.ordinal() > accessLevel.ordinal()) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            } else {
                                if (accessLevel == AccessLevel.NATIONAL) {
                                    user.setAccessLevel(AccessLevel.NATIONAL.getAccessLevel());
                                } else if (accessLevel == AccessLevel.STATE) {
                                    user.setAccessLevel(accessLevel.getAccessLevel());
                                    List<State> userStateList = stateDao.findByName(State);
                                    if ((userStateList == null) || (userStateList.size() == 0)) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "Please enter the valid State for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        if (loggedUserAccessLevel == AccessLevel.STATE) {
                                            if (!loggedInUser.getStateId().equals(userStateList.get(0).getStateId())) {
                                                Integer rowNum = lineNumber;
                                                String authorityError = "You don't have authority to create this user.";
                                                errorCreatingUsers.put(rowNum, authorityError);
                                                continue;
                                            } else user.setStateId(userStateList.get(0).getStateId());
                                        } else user.setStateId(userStateList.get(0).getStateId());
                                    }
                                } else if (accessLevel == AccessLevel.DISTRICT) {
                                    List<State> userStateList = stateDao.findByName(State);
                                    List<District> userDistrictList = districtDao.findByName(District);
                                    District userDistrict = null;
                                    State userState = null;
                                    if (userDistrictList.size() == 1) {
                                        userDistrict = userDistrictList.get(0);
                                        userState = stateDao.findByStateId(userDistrict.getStateOfDistrict());
                                    } else {
                                        for (District district : userDistrictList) {
                                            State parent = stateDao.findByStateId(district.getStateOfDistrict());
                                            if ((userStateList != null) && (userStateList.size() != 0)) {
                                                if (parent.getStateId().equals(userStateList.get(0).getStateId())) {
                                                    userDistrict = district;
                                                    userState = parent;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (userDistrict == null) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "Please enter the valid district for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                        continue;
                                    } else {
                                        if (((loggedUserAccessLevel == AccessLevel.STATE) &&
                                                (!loggedInUser.getStateId().equals(userState.getStateId()))) ||
                                                ((loggedUserAccessLevel == AccessLevel.DISTRICT) &&
                                                        (!loggedInUser.getDistrictId().equals(userDistrict.getDistrictId())))) {
                                            Integer rowNum = lineNumber;
                                            String authorityError = "You don't have authority to create this user.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        } else {
                                            user.setAccessLevel(AccessLevel.DISTRICT.getAccessLevel());
                                            user.setDistrictId(userDistrict.getDistrictId());
                                            user.setStateId(userState.getStateId());
                                        }
                                    }
                                } else {
                                    user.setAccessLevel(AccessLevel.BLOCK.getAccessLevel());
                                    List<State> userStateList = stateDao.findByName(State);
                                    List<District> userDistrictList = districtDao.findByName(District);
                                    List<Block> userBlockList = blockDao.findByName(Block);
                                    State userState = null;
                                    District userDistrict = null;
                                    Block userBlock = null;
                                    if (userBlockList.size() == 1) {
                                        userBlock = userBlockList.get(0);
                                        userDistrict = districtDao.findByDistrictId(userBlock.getDistrictOfBlock());
                                        userState = stateDao.findByStateId(userDistrict.getStateOfDistrict());
                                    } else if ((userBlockList.size() == 0) || userBlockList == null) {
                                        Integer rowNum = lineNumber;
                                        String authorityError = "Please enter the valid Block for this user.";
                                        errorCreatingUsers.put(rowNum, authorityError);
                                    } else {
                                        List<Block> commonDistrict = null;
                                        for (Block block : userBlockList) {
                                            District parent = districtDao.findByDistrictId(block.getDistrictOfBlock());
                                            if (userDistrictList.size() > 0) {
                                                for (District district : userDistrictList) {
                                                    if (parent.getDistrictId().equals(district.getDistrictId())) {
                                                        commonDistrict.add(block);
                                                    }
                                                }
                                            }
                                        }
                                        for (Block block : commonDistrict) {
                                            State parent = stateDao.findByStateId(block.getStateOfBlock());
                                            if (userState != null) {
                                                if (parent.getStateId().equals(userStateList.get(0).getStateId())) {
                                                    userBlock = block;
                                                    userDistrict = districtDao.findByDistrictId(userBlock.getDistrictOfBlock());
                                                    userState = stateDao.findByStateId(userBlock.getStateOfBlock());
                                                    break;
                                                }
                                            }
                                        }
                                        if (userBlock == null) {
                                            Integer rowNum = lineNumber;
                                            String authorityError = "Please enter the valid location for this user.";
                                            errorCreatingUsers.put(rowNum, authorityError);
                                            continue;
                                        } else {
                                            if (((loggedUserAccessLevel == AccessLevel.STATE) &&
                                                    (!loggedInUser.getStateId().equals(userState.getStateId()))) ||
                                                    ((loggedUserAccessLevel == AccessLevel.DISTRICT) &&
                                                            (!loggedInUser.getDistrictId().equals(userDistrict.getDistrictId())))) {
                                                Integer rowNum = lineNumber;
                                                String authorityError = "You don't have authority to create this user.";
                                                errorCreatingUsers.put(rowNum, authorityError);
                                                continue;
                                            } else {
                                                user.setBlockId(userBlock.getBlockId());
                                                user.setStateId(userState.getStateId());
                                                user.setDistrictId(userDistrict.getDistrictId());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        user.setStateName(user.getStateId()==null ? "" : stateDao.findByStateId(user.getStateId()).getStateName());
                        user.setDistrictName(user.getDistrictId()==null? "" : districtDao.findByDistrictId(user.getDistrictId()).getDistrictName());
                        user.setBlockName(user.getBlockId()==null ? "" :  blockDao.findByblockId(user.getBlockId()).getBlockName());
                        user.setRoleName(user.getRoleId()==null ? "" : roleDao.findByRoleId(user.getRoleId()).getRoleDescription());
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
            }
        }
        return (HashMap) errorCreatingUsers;
    }

    public void getBulkDataImportCSV() {
        String NEW_LINE_SEPARATOR = "\n";

        //CSV file header
        String FILE_HEADER = "Full Name, State, District, Block, Phone number, Email ID, UserName, Access Level,Role";
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(documents+"BulkImportData.csv");

            //Write the CSV file header
            fileWriter.append(FILE_HEADER);
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
    public void createFiles(String reportType) {
        String serviceType=ReportType.getType(reportType).getServiceType();
        List<State> states = stateDao.getStatesByServiceType(serviceType);
        String rootPath = reports;
        File dir = new File(rootPath + reportType);
        if (!dir.exists())
            dir.mkdirs();
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + reportType + "/" + stateName;
            File dirState = new File(rootPathState);
            if (!dirState.exists())
                dirState.mkdirs();
            int stateId = state.getStateId();
            List<District> districts = districtDao.getDistrictsOfState(stateId);
            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + "/" + districtName;
                File dirDistrict = new File(rootPathDistrict);
                if (!dirDistrict.exists())
                    dirDistrict.mkdirs();
                int districtId = district.getDistrictId();
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + "/" + blockName;
                    File dirBlock = new File(rootPathblock);
                    if (!dirBlock.exists())
                        dirBlock.mkdirs();
                }
            }
        }
    }

    @Override
    public void createFolders(String reportType) {

        List<Circle> circleList = circleDao.getAllCircles();
        String rootPath = reports;
        File dir = new File(rootPath + reportType);
        if (!dir.exists())
            dir.mkdirs();
        for (Circle circle : circleList) {
            String circleName = StReplace(circle.getCircleFullName());
            String rootPathCircle = rootPath + reportType + "/" + circleName;
            File dirCircle = new File(rootPathCircle);
            if (!dirCircle.exists())
                dirCircle.mkdirs();
        }
    }

    @Override
    public void createSpecificReport(ReportRequest reportRequest) {

        String rootPath = reports+reportRequest.getReportType()+ "/";
//        Date toDate=reportRequest.getToDate();
        Date startDate=new Date(0);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);

//        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DATE, 1);

        Date fromDate = aCalendar.getTime();

        aCalendar.add(Calendar.MONTH, 1);

        Date toDate = aCalendar.getTime();
        int stateId=reportRequest.getStateId();
        int districtId=reportRequest.getDistrictId();
        int blockId=reportRequest.getBlockId();
        int circleId=reportRequest.getCircleId();

        if(reportRequest.getReportType().equals(ReportType.maCourse.getReportType())){

            List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(toDate);

            if(stateId==0){
                getCumulativeCourseCompletion(successFullcandidates, rootPath,AccessLevel.NATIONAL.getAccessLevel(), toDate);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<MACourseFirstCompletion> candidatesFromThisState = new ArrayList<>();
                    for (MACourseFirstCompletion asha : successFullcandidates) {
                        if (asha.getStateId() == stateId) {
                            candidatesFromThisState.add(asha);
                        }
                    }
                    getCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<MACourseFirstCompletion> candidatesFromThisDistrict = new ArrayList<>();
                        for (MACourseFirstCompletion asha : successFullcandidates) {
                            if (asha.getDistrictId() == districtId) {
                                candidatesFromThisDistrict.add(asha);
                            }
                        }
                        getCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<MACourseFirstCompletion> candidatesFromThisBlock = new ArrayList<>();
                        for (MACourseFirstCompletion asha : successFullcandidates) {
                            if ((asha.getBlockId()!=null)&&(asha.getBlockId() == blockId)) {
                                candidatesFromThisBlock.add(asha);
                            }
                        }
                        getCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                    }
                }
            }
        }
        if(reportRequest.getReportType().equals(ReportType.maInactive.getReportType())){

            List<FrontLineWorkers> inactiveFrontLineWorkers = frontLineWorkersDao.getInactiveFrontLineWorkers(toDate);

            if(stateId==0){
                getCumulativeInactiveUsers(inactiveFrontLineWorkers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<FrontLineWorkers> candidatesFromThisState = new ArrayList<>();
                    for (FrontLineWorkers asha : inactiveFrontLineWorkers) {
                        if (asha.getState() == stateId) {
                            candidatesFromThisState.add(asha);
                        }
                    }
                    getCumulativeInactiveUsers(candidatesFromThisState,rootPathState, stateName, toDate);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<FrontLineWorkers> candidatesFromThisDistrict = new ArrayList<>();
                        for (FrontLineWorkers asha : inactiveFrontLineWorkers) {
                            if (asha.getDistrict() == districtId) {
                                candidatesFromThisDistrict.add(asha);
                            }
                        }
                        getCumulativeInactiveUsers(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<FrontLineWorkers> candidatesFromThisBlock = new ArrayList<>();
                        for (FrontLineWorkers asha : inactiveFrontLineWorkers) {
                            if ((asha.getBlock()!=null)&&(asha.getBlock() == blockId)) {
                                candidatesFromThisBlock.add(asha);
                            }
                        }
                        getCumulativeInactiveUsers(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                    }
                }
            }
        }
        if(reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())){

            List<AnonymousUsers> anonymousUsersList = anonymousUsersDao.getAnonymousUsers(fromDate,toDate);

            if(circleId==0){
                getCircleWiseAnonymousUsers(anonymousUsersList,  rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
            }
            else{
                String circleName=StReplace(circleDao.getByCircleId(circleId).getCircleName());
                String circleFullName = StReplace(circleDao.getByCircleId(circleId).getCircleFullName());
                String rootPathCircle=rootPath+circleFullName+"/";
                List<AnonymousUsers> anonymousUsersListCircle = anonymousUsersDao.getAnonymousUsersCircle(fromDate,toDate,StReplace(circleDao.getByCircleId(circleId).getCircleName()));
                getCircleWiseAnonymousUsers(anonymousUsersListCircle, rootPathCircle, circleName, toDate);
            }
        }
        if(reportRequest.getReportType().equals(ReportType.lowUsage.getReportType())){

            List<KilkariLowUsage> kilkariLowUsageList = kilkariLowUsageDao.getKilkariLowUsageUsers(getMonthYear(toDate));
            if(stateId==0){
                getKilkariLowUsage(kilkariLowUsageList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariLowUsage> candidatesFromThisState = new ArrayList<>();
                    for (KilkariLowUsage kilkari : kilkariLowUsageList) {
                        if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                            candidatesFromThisState.add(kilkari);
                        }
                    }
                    getKilkariLowUsage(candidatesFromThisState,rootPathState, stateName, toDate);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariLowUsage> candidatesFromThisDistrict = new ArrayList<>();
                        for (KilkariLowUsage kilkari : kilkariLowUsageList) {
                            if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                                candidatesFromThisDistrict.add(kilkari);
                            }
                        }
                        getKilkariLowUsage(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariLowUsage> candidatesFromThisBlock = new ArrayList<>();
                        for (KilkariLowUsage kilkari : kilkariLowUsageList) {
                            if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                                candidatesFromThisBlock.add(kilkari);
                            }
                        }
                        getKilkariLowUsage(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                    }
                }
            }
        }
        if(reportRequest.getReportType().equals(ReportType.sixWeeks.getReportType())){

            List<KilkariSixWeeksNoAnswer> kilkariSixWeeksNoAnswers = kilkariSixWeeksNoAnswerDao.getKilkariUsers(fromDate, toDate);

            if(stateId==0){
                getKilkariSixWeekNoAnswer(kilkariSixWeeksNoAnswers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariSixWeeksNoAnswer> candidatesFromThisState = new ArrayList<>();
                    for (KilkariSixWeeksNoAnswer kilkari : kilkariSixWeeksNoAnswers) {
                        if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                            candidatesFromThisState.add(kilkari);
                        }
                    }
                    getKilkariSixWeekNoAnswer(candidatesFromThisState,rootPathState, stateName, toDate);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariSixWeeksNoAnswer> candidatesFromThisDistrict = new ArrayList<>();
                        for (KilkariSixWeeksNoAnswer kilkari : kilkariSixWeeksNoAnswers) {
                            if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                                candidatesFromThisDistrict.add(kilkari);
                            }
                        }
                        getKilkariSixWeekNoAnswer(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariSixWeeksNoAnswer> candidatesFromThisBlock = new ArrayList<>();
                        for (KilkariSixWeeksNoAnswer kilkari : kilkariSixWeeksNoAnswers) {
                            if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                                candidatesFromThisBlock.add(kilkari);
                            }
                        }
                        getKilkariSixWeekNoAnswer(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                    }
                }
            }
        }

        if(reportRequest.getReportType().equals(ReportType.selfDeactivated.getReportType())){

            List<KilkariSelfDeactivated> kilkariSelfDeactivatedList = kilkariSelfDeactivatedDao.getSelfDeactivatedUsers(fromDate, toDate);

            if(stateId==0){
                getKilkariSelfDeactivation(kilkariSelfDeactivatedList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariSelfDeactivated> candidatesFromThisState = new ArrayList<>();
                    for (KilkariSelfDeactivated kilkari : kilkariSelfDeactivatedList) {
                        if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                            candidatesFromThisState.add(kilkari);
                        }
                    }
                    getKilkariSelfDeactivation(candidatesFromThisState,rootPathState, stateName, toDate);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariSelfDeactivated> candidatesFromThisDistrict = new ArrayList<>();
                        for (KilkariSelfDeactivated kilkari : kilkariSelfDeactivatedList) {
                            if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                                candidatesFromThisDistrict.add(kilkari);
                            }
                        }
                        getKilkariSelfDeactivation(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariSelfDeactivated> candidatesFromThisBlock = new ArrayList<>();
                        for (KilkariSelfDeactivated kilkari : kilkariSelfDeactivatedList) {
                            if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                                candidatesFromThisBlock.add(kilkari);
                            }
                        }
                        getKilkariSelfDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                    }
                }
            }
        }
    }

    private void getCumulativeCourseCompletion(List<MACourseFirstCompletion> successfulCandidates, String rootPath, String place, Date toDate) {
        //Create blank workbook


        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " MA Course Completion Report ");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "Mobile Number",
                "State",
                "District",
                "Health Block",
                "Taluka",
                "Health Facility",
                "Health Sub Facility",
                "Village",
                "ASHA Name",
                "ASHA MCTS/RCH ID",
                "ASHA Creation Date",
                "ASHA Job Status",
                "First Completion Date",
                "SMS Sent Notification"
        });
        Integer counter = 2;
        if(successfulCandidates.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Data Available"});
        }
        for (MACourseFirstCompletion maCourseFirstCompletion : successfulCandidates) {
            empinfo.put((counter.toString()), new Object[]{
                    (maCourseFirstCompletion.getMsisdn() == null) ? "No Phone":maCourseFirstCompletion.getMsisdn(),
                    (maCourseFirstCompletion.getStateId() == null) ? "No State":stateDao.findByStateId(maCourseFirstCompletion.getStateId()).getStateName(),
                    (maCourseFirstCompletion.getDistrictId() == null) ? "No District":districtDao.findByDistrictId(maCourseFirstCompletion.getDistrictId()).getDistrictName(),
                    (maCourseFirstCompletion.getBlockId() == null) ? "No Block" : blockDao.findByblockId(maCourseFirstCompletion.getBlockId()).getBlockName(),
                    (maCourseFirstCompletion.getTalukaId() == null) ? "No Taluka" : talukaDao.findByTalukaId(maCourseFirstCompletion.getTalukaId()).getTalukaName(),
                    (maCourseFirstCompletion.getHealthFacilityId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(maCourseFirstCompletion.getHealthFacilityId()).getHealthFacilityName(),
                    (maCourseFirstCompletion.getHealthSubFacilityId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(maCourseFirstCompletion.getHealthSubFacilityId()).getHealthSubFacilityName(),
                    (maCourseFirstCompletion.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(maCourseFirstCompletion.getVillageId()).getVillageName(),
                    (maCourseFirstCompletion.getFullName() == null) ? "No Name":maCourseFirstCompletion.getFullName(),
                    (maCourseFirstCompletion.getExternalFlwId() == null) ? "No FLW_ID":maCourseFirstCompletion.getExternalFlwId(),
                    (maCourseFirstCompletion.getCreationDate() == null) ? "No Creation_date":maCourseFirstCompletion.getCreationDate(),
                    (maCourseFirstCompletion.getJobStatus() == null) ? "No Designation":maCourseFirstCompletion.getJobStatus(),
                    (maCourseFirstCompletion.getFirstCompletionDate() == null) ? "No Phone":maCourseFirstCompletion.getFirstCompletionDate(),
                    (maCourseFirstCompletion.getSentNotification() == null) ? "No Details": maCourseFirstCompletion.getSentNotification()
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid == 2 && successfulCandidates.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A2:N2"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.maCourse.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
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

    private void getCircleWiseAnonymousUsers(List<AnonymousUsers> anonymousUsersList, String rootPath, String place, Date toDate) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Circle-wise Anonymous Users Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "Circle Name",
                "Mobile Number",
                "Last Called Date"
        });
        Integer counter = 2;
        if(anonymousUsersList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Data Available"});
        }
        for (AnonymousUsers anonymousUser : anonymousUsersList) {
            empinfo.put((counter.toString()), new Object[]{
                    anonymousUser.getCircleName(),
                    anonymousUser.getMsisdn(),
                    anonymousUser.getLastCalledDate()
            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==2 && anonymousUsersList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A2:C2"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.maAnonymous.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
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

    private void getCumulativeInactiveUsers(List<FrontLineWorkers> inactiveCandidates, String rootPath, String place, Date toDate) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                "Cumulative Inactive Users Report "+place+"_"+getMonthYear(toDate));
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "Mobile Number",
                "State",
                "District",
                "Health Block",
                "Taluka",
                "Health Facility",
                "Health Sub Facility",
                "Village",
                "ASHA Name",
                "ASHA MCTS/RCH ID",
                "ASHA Creation Date",
                "ASHA Job Status"
        });
        Integer counter = 2;
        if(inactiveCandidates.isEmpty()) {
            empinfo.put(counter.toString(),new Object[]{"No Data Available"});
        }
        for (FrontLineWorkers frontLineWorker : inactiveCandidates) {
            empinfo.put((counter.toString()), new Object[]{
                    (frontLineWorker.getMobileNumber() == null) ? "No Phone":frontLineWorker.getMobileNumber(),
                    (frontLineWorker.getState() == null) ? "No State":stateDao.findByStateId(frontLineWorker.getState()).getStateName(),
                    (frontLineWorker.getDistrict() == null) ? "No District":districtDao.findByDistrictId(frontLineWorker.getDistrict()).getDistrictName(),
                    (frontLineWorker.getBlock() == null) ? "No Block" : blockDao.findByblockId(frontLineWorker.getBlock()).getBlockName(),
                    (frontLineWorker.getTaluka() == null) ? "No Taluka" : talukaDao.findByTalukaId(frontLineWorker.getTaluka()).getTalukaName(),
                    (frontLineWorker.getFacility() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(frontLineWorker.getFacility()).getHealthFacilityName(),
                    (frontLineWorker.getSubfacility() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(frontLineWorker.getSubfacility()).getHealthSubFacilityName(),
                    (frontLineWorker.getVillage() == null) ? "No Village" : villageDao.findByVillageId(frontLineWorker.getVillage()).getVillageName(),
                    (frontLineWorker.getFullName() == null) ? "No Name":frontLineWorker.getFullName(),
                    (frontLineWorker.getExternalFlwId() == null) ? "No FLW_ID":frontLineWorker.getExternalFlwId(),
                    (frontLineWorker.getCreationDate() == null) ? "No Creation_date":frontLineWorker.getCreationDate(),
                    (frontLineWorker.getJobStatus() == null) ? "No Designation":frontLineWorker.getJobStatus()
            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==2 && inactiveCandidates.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A2:L2"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
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

    private void getKilkariSixWeekNoAnswer(List<KilkariSixWeeksNoAnswer> kilkariSixWeeksNoAnswersList, String rootPath, String place, Date toDate){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari Non-answering beneficiaries Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "State",
                "District",
                "Health block",
                "Taluka",
                "Health Facility",
                "Health SubFacility",
                "Village",
                "Beneficiary MCTS Id",
                "Beneficiary RCH Id",
                "Benificiary Name",
                "Mobile Number",
                "Age On Service In Weeks"});
        Integer counter = 2;
        if(kilkariSixWeeksNoAnswersList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Data Available"});
        }
        for (KilkariSixWeeksNoAnswer kilkari : kilkariSixWeeksNoAnswersList) {
            empinfo.put((counter.toString()), new Object[]{
                    (kilkari.getStateId() == null) ? "No State" : stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    (kilkari.getDistrictId() == null) ? "No District" : districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    (kilkari.getBlockId() == null) ? "No Block" : blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    (kilkari.getVillageId() == null) ? "No Taluka" : talukaDao.findByTalukaId(villageDao.findByVillageId(kilkari.getVillageId()).getTalukaOfVillage()).getTalukaName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthFacilityOfHealthSubFacility()).getHealthFacilityName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    (kilkari.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(kilkari.getVillageId()).getVillageName(),
                    (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
                    (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
                    (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
                    (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
                    (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService()

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==2 && kilkariSixWeeksNoAnswersList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A2:L2"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.sixWeeks.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
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

    private void getKilkariSelfDeactivation(List<KilkariSelfDeactivated> kilkariSelfDeactivatedList, String rootPath, String place, Date toDate){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari self-deactivators Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "State",
                "District",
                "Health block",
                "Taluka",
                "Health Facility",
                "Health SubFacility",
                "Village",
                "Beneficiary MCTS Id",
                "Beneficiary RCH Id",
                "Benificiary Name",
                "Mobile Number",
                "Age On Service In Weeks",
                "Date of activation",
                "Date when beneficiary self-deactivated",
                "Number of calls answered when subscribed to Kilkari"
        });
        Integer counter = 2;
        if(kilkariSelfDeactivatedList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Data Available Available"});
        }
        for (KilkariSelfDeactivated kilkari : kilkariSelfDeactivatedList) {
            empinfo.put((counter.toString()), new Object[]{
                    (kilkari.getStateId() == null) ? "No State" : stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    (kilkari.getDistrictId() == null) ? "No District" : districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    (kilkari.getBlockId() == null) ? "No Block" : blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    (kilkari.getVillageId() == null) ? "No Taluka" : talukaDao.findByTalukaId(villageDao.findByVillageId(kilkari.getVillageId()).getTalukaOfVillage()).getTalukaName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthFacilityOfHealthSubFacility()).getHealthFacilityName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    (kilkari.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(kilkari.getVillageId()).getVillageName(),
                    (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
                    (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
                    (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
                    (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
                    (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService(),
                    (kilkari.getPackActivationDate() == null) ? "No Activation_date" :kilkari.getPackActivationDate(),
                    (kilkari.getDeactivationDate() == null) ? "No Deactivation_date" :kilkari.getDeactivationDate(),
                    (kilkari.getCallsAnswered() == null) ? "No Call_Data" :kilkari.getCallsAnswered()

            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==2 && kilkariSelfDeactivatedList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A2:O2"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.selfDeactivated.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
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

    private void getKilkariLowUsage(List<KilkariLowUsage> kilkariLowUsageList, String rootPath, String place, Date toDate){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari Low Usage beneficiaries Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "State",
                "District",
                "Health block",
                "Taluka",
                "Health Facility",
                "Health SubFacility",
                "Village",
                "Beneficiary MCTS Id",
                "Beneficiary RCH Id",
                "Benificiary Name",
                "Mobile Number",
                "Age On Service In Weeks"
        });
        Integer counter = 2;
        if(kilkariLowUsageList.isEmpty()) {
            empinfo.put(counter.toString(),new Object[]{"No Data Available"});
        }
        for (KilkariLowUsage kilkari : kilkariLowUsageList) {
            empinfo.put((counter.toString()), new Object[]{
                    (kilkari.getStateId() == null) ? "No State" : stateDao.findByStateId(kilkari.getStateId()).getStateName(),
                    (kilkari.getDistrictId() == null) ? "No District" : districtDao.findByDistrictId(kilkari.getDistrictId()).getDistrictName(),
                    (kilkari.getBlockId() == null) ? "No Block" : blockDao.findByblockId(kilkari.getBlockId()).getBlockName(),
                    (kilkari.getVillageId() == null) ? "No Taluka" : talukaDao.findByTalukaId(villageDao.findByVillageId(kilkari.getVillageId()).getTalukaOfVillage()).getTalukaName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Facility" : healthFacilityDao.findByHealthFacilityId(healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthFacilityOfHealthSubFacility()).getHealthFacilityName(),
                    (kilkari.getHsubcenterId() == null) ? "No Health Subfacility" : healthSubFacilityDao.findByHealthSubFacilityId(kilkari.getHsubcenterId()).getHealthSubFacilityName(),
                    (kilkari.getVillageId() == null) ? "No Village" : villageDao.findByVillageId(kilkari.getVillageId()).getVillageName(),
                    (kilkari.getMctsId() == null) ? "No MCTS Id" : kilkari.getMctsId(),
                    (kilkari.getRchId() == null) ? "No RCH Id" : kilkari.getRchId(),
                    (kilkari.getName() == null) ? "No Name" : kilkari.getName(),
                    (kilkari.getMsisdn() == null) ? "No MSISDN" : kilkari.getMsisdn(),
                    (kilkari.getAgeOnService() == null) ? "No Age_Data" : kilkari.getAgeOnService(),
            });
            counter++;
        }
        Set<String> keyid = empinfo.keySet();
        int rowid = 0;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==2 && kilkariLowUsageList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A2:L2"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.lowUsage.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
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
    public void getCumulativeCourseCompletionFiles(Date toDate) {

        List<State> states = stateDao.getStatesByServiceType(ReportType.maCourse.getServiceType());
        String rootPath = reports+ReportType.maCourse.getReportType()+ "/";
        List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(toDate);
        getCumulativeCourseCompletion(successFullcandidates, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            int stateId = state.getStateId();
            List<MACourseFirstCompletion> candidatesFromThisState = new ArrayList<>();
            for (MACourseFirstCompletion asha : successFullcandidates) {
                if (asha.getStateId() == stateId) {
                    candidatesFromThisState.add(asha);
                }
            }
            getCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate);
            List<District> districts = districtDao.getDistrictsOfState(stateId);
            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<MACourseFirstCompletion> candidatesFromThisDistrict = new ArrayList<>();
                for (MACourseFirstCompletion asha : candidatesFromThisState) {
                    if (asha.getDistrictId() == districtId) {
                        candidatesFromThisDistrict.add(asha);
                    }
                }
                getCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";
                    int blockId = block.getBlockId();
                    List<MACourseFirstCompletion> candidatesFromThisBlock = new ArrayList<>();
                    for (MACourseFirstCompletion asha : candidatesFromThisDistrict) {
                        if ((asha.getBlockId()!=null)&&(asha.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(asha);
                        }
                    }
                    getCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                }
            }
        }
    }

    @Override
    public void getCircleWiseAnonymousFiles(Date startDate, Date toDate) {
        List<Circle> circleList = circleDao.getAllCircles();
        String rootPath = reports+ReportType.maAnonymous.getReportType()+ "/";
        List<AnonymousUsers> anonymousUsersList = anonymousUsersDao.getAnonymousUsers(startDate,toDate);
        getCircleWiseAnonymousUsers(anonymousUsersList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
        for (Circle circle : circleList) {
            String circleName = StReplace(circle.getCircleName());
            String circleFullName = StReplace(circle.getCircleFullName());
            String rootPathCircle=rootPath+circleFullName+"/";
            List<AnonymousUsers> anonymousUsersListCircle = new ArrayList<>();
            for(AnonymousUsers anonymousUser : anonymousUsersList){
                if(anonymousUser.getCircleName().equalsIgnoreCase(circleName)){
                    anonymousUsersListCircle.add(anonymousUser);
                }
            }
            getCircleWiseAnonymousUsers(anonymousUsersListCircle, rootPathCircle, circleFullName, toDate);
        }
    }

    @Override
    public void getCumulativeInactiveFiles(Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.maInactive.getServiceType());
        String rootPath = reports+ReportType.maInactive.getReportType()+ "/";
        List<FrontLineWorkers> inactiveFrontLineWorkers = frontLineWorkersDao.getInactiveFrontLineWorkers(toDate);
        getCumulativeInactiveUsers(inactiveFrontLineWorkers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<FrontLineWorkers> candidatesFromThisState = new ArrayList<>();
            for (FrontLineWorkers asha : inactiveFrontLineWorkers) {
                if (asha.getState() == stateId) {
                    candidatesFromThisState.add(asha);
                }
            }
            getCumulativeInactiveUsers(candidatesFromThisState, rootPathState, stateName, toDate);
            List<District> districts = districtDao.getDistrictsOfState(stateId);
            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState  + districtName+ "/";
                int districtId = district.getDistrictId();
                List<FrontLineWorkers> candidatesFromThisDistrict = new ArrayList<>();
                for (FrontLineWorkers asha : candidatesFromThisState) {
                    if (asha.getDistrict() == districtId) {
                        candidatesFromThisDistrict.add(asha);
                    }
                }
                getCumulativeInactiveUsers(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict  + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<FrontLineWorkers> candidatesFromThisBlock = new ArrayList<>();
                    for (FrontLineWorkers asha : candidatesFromThisDistrict) {
                        if ((asha.getBlock()!=null)&&(asha.getBlock() == blockId)) {
                            candidatesFromThisBlock.add(asha);
                        }
                    }
                    getCumulativeInactiveUsers(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                }
            }
        }
    }

    @Override
    public void getKilkariSixWeekNoAnswerFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.sixWeeks.getServiceType());
        String rootPath = reports +ReportType.sixWeeks.getReportType()+ "/";
        List<KilkariSixWeeksNoAnswer> kilkariSixWeeksNoAnswers = kilkariSixWeeksNoAnswerDao.getKilkariUsers(fromDate, toDate);
        getKilkariSixWeekNoAnswer(kilkariSixWeeksNoAnswers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<KilkariSixWeeksNoAnswer> candidatesFromThisState = new ArrayList<>();
            for (KilkariSixWeeksNoAnswer kilkari : kilkariSixWeeksNoAnswers) {
                if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                    candidatesFromThisState.add(kilkari);
                }
            }
            getKilkariSixWeekNoAnswer(candidatesFromThisState, rootPathState, stateName, toDate);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<KilkariSixWeeksNoAnswer> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariSixWeeksNoAnswer kilkari : candidatesFromThisState) {
                    if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }
                getKilkariSixWeekNoAnswer(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<KilkariSixWeeksNoAnswer> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariSixWeeksNoAnswer kilkari : candidatesFromThisDistrict) {
                        if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    getKilkariSixWeekNoAnswer(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                }
            }
        }
    }

    @Override
    public void getKilkariLowUsageFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.lowUsage.getServiceType());
        String rootPath = reports+ReportType.lowUsage.getReportType() + "/";
        List<KilkariLowUsage> kilkariLowUsageList = kilkariLowUsageDao.getKilkariLowUsageUsers(getMonthYear(toDate));
        getKilkariLowUsage(kilkariLowUsageList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<KilkariLowUsage> candidatesFromThisState = new ArrayList<>();
            for (KilkariLowUsage kilkari : kilkariLowUsageList) {
                if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                    candidatesFromThisState.add(kilkari);
                }
            }
            getKilkariLowUsage(candidatesFromThisState, rootPathState, stateName, toDate);
            List<District> districts = districtDao.getDistrictsOfState(stateId);
            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<KilkariLowUsage> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariLowUsage kilkari : candidatesFromThisState) {
                    if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }
                getKilkariLowUsage(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";
                    int blockId = block.getBlockId();
                    List<KilkariLowUsage> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariLowUsage kilkari : candidatesFromThisDistrict) {
                        if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    getKilkariLowUsage(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                }
            }
        }
    }

    @Override
    public void getKilkariSelfDeactivationFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.selfDeactivated.getServiceType());
        String rootPath = reports+ReportType.selfDeactivated.getReportType() + "/";
        List<KilkariSelfDeactivated> kilkariSelfDeactivatedList = kilkariSelfDeactivatedDao.getSelfDeactivatedUsers(fromDate, toDate);
        getKilkariSelfDeactivation(kilkariSelfDeactivatedList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<KilkariSelfDeactivated> candidatesFromThisState = new ArrayList<>();
            for (KilkariSelfDeactivated kilkari : kilkariSelfDeactivatedList) {
                if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                    candidatesFromThisState.add(kilkari);
                }
            }
            getKilkariSelfDeactivation(candidatesFromThisState, rootPathState, stateName, toDate);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<KilkariSelfDeactivated> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariSelfDeactivated kilkari : candidatesFromThisState) {
                    if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }
                getKilkariSelfDeactivation(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";
                    int blockId = block.getBlockId();
                    List<KilkariSelfDeactivated> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariSelfDeactivated kilkari : candidatesFromThisDistrict) {
                        if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    getKilkariSelfDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate);
                }
            }
        }
    }


    private String retrievePropertiesFromFileLocationProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        String fileLocation = null;
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
            prop.setProperty("fileLocation", documents+"BulkImportDatacr7ms10.csv");
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


    private String getMonthYear(Date toDate){
        c.setTime(toDate);
        c.add(Calendar.MONTH,-1);
        int month=c.get(Calendar.MONTH)+1;
        int year=(c.get(Calendar.YEAR))%100;
        String monthString;
        if(month<10){
            monthString="0"+String.valueOf(month);
        }
       else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return monthString+"_"+yearString;
    }

}
