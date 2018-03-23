package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AdminService;
import com.beehyv.nmsreporting.dao.*;
import com.beehyv.nmsreporting.entity.ReportRequest;
import com.beehyv.nmsreporting.enums.*;
import com.beehyv.nmsreporting.model.*;
import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.beehyv.nmsreporting.utils.Global.retrieveDocuments;
import static com.beehyv.nmsreporting.utils.ServiceFunctions.StReplace;
import static java.lang.Integer.parseInt;

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
    private ChildImportRejectionDao childImportRejectionDao;

    @Autowired
    private MotherImportRejectionDao motherImportRejectionDao;

    @Autowired
    private FlwImportRejectionDao flwImportRejectionDao;

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

    @Autowired
    private ReportTypeDao reportTypeDao;


    @Autowired
    private ModificationTrackerDao modificationTrackerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private final String documents = retrieveDocuments();
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
                        user.setPassword(passwordEncoder.encode(Line[4]));
                        user.setFullName(Line[0]);
                        user.setPhoneNumber(Line[4]);
                        user.setAccountStatus(AccountStatus.ACTIVE.getAccountStatus());
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
                        user.setCreatedByUser(loggedInUser);
                        user.setCreationDate(new Date());
                        List<Role> userRole = roleDao.findByRoleDescription(Line[8]);
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
                            if ((accessLevel == AccessLevel.NATIONAL) ) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            } else if (loggedUserAccessLevel == AccessLevel.DISTRICT) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            }  else if (accessLevel == AccessLevel.STATE && loggedUserAccessLevel != AccessLevel.NATIONAL) {
                                Integer rowNum = lineNumber;
                                String authorityError = "You don't have authority to create this user.";
                                errorCreatingUsers.put(rowNum, authorityError);
                                continue;
                            }else if (accessLevel == AccessLevel.STATE && loggedUserAccessLevel == AccessLevel.NATIONAL) {
                                List<State> userStateList = stateDao.findByName(State);
                                if(userStateList==null || userStateList.size()==0){
                                    Integer rowNum = lineNumber;
                                    String authorityError = "Please enter the valid state for this admin";
                                    errorCreatingUsers.put(rowNum, authorityError);
                                    continue;
                                }
                                State userState=userStateList.get(0);
                                boolean isAdminAvailable = userDao.isAdminCreated(userState);
                                if (!(isAdminAvailable)) {
                                    user.setAccessLevel(AccessLevel.STATE.getAccessLevel());
                                    user.setStateId(userState.getStateId());
                                } else {
                                    Integer rowNum = lineNumber;
                                    String authorityError = "Admin is available for this state.";
                                    errorCreatingUsers.put(rowNum, authorityError);
                                    continue;
                                }
                            }
                            else {
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
                                    if ((loggedInUser.getStateId()!=null && !loggedInUser.getStateId().equals(userState.getStateId()))) {
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
                                        user.setBlockId(userBlock.getBlockId());
                                        user.setStateId(userState.getStateId());
                                        user.setDistrictId(userDistrict.getDistrictId());
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
                        user.setRoleId(userRoleId);
                        user.setRoleName(roleDao.findByRoleId(userRoleId).getRoleDescription());
                        userDao.saveUser(user);
                        ModificationTracker modification = new ModificationTracker();
                        modification.setModificationDate(new Date(System.currentTimeMillis()));
                        modification.setModificationType(ModificationType.CREATE.getModificationType());
                        modification.setModifiedUserId(userDao.findByUserName(user.getUsername()).getUserId());
                        modification.setModifiedByUserId(loggedInUser.getUserId());
                        modificationTrackerDao.saveModification(modification);

                    }
                    if(lineNumber == 1){
                        errorCreatingUsers.put(0,"fail");
                        errorCreatingUsers.put(1,"No records present in the uploaded file");
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
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(toDate);
                getCumulativeCourseCompletion(successFullcandidates, rootPath,AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<MACourseFirstCompletion> candidatesFromThisState = maCourseAttemptDao.getSuccessFulCompletionWithStateId(toDate,stateId);

                    getCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<MACourseFirstCompletion> candidatesFromThisDistrict = maCourseAttemptDao.getSuccessFulCompletionWithDistrictId(toDate,districtId);

                        getCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<MACourseFirstCompletion> candidatesFromThisBlock = maCourseAttemptDao.getSuccessFulCompletionWithBlockId(toDate,blockId);

                        getCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.maInactive.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<FrontLineWorkers> inactiveFrontLineWorkers = frontLineWorkersDao.getInactiveFrontLineWorkers(toDate);
                getCumulativeInactiveUsers(inactiveFrontLineWorkers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<FrontLineWorkers> candidatesFromThisState = frontLineWorkersDao.getInactiveFrontLineWorkersWithStateId(toDate,stateId);

                    getCumulativeInactiveUsers(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<FrontLineWorkers> candidatesFromThisDistrict = frontLineWorkersDao.getInactiveFrontLineWorkersWithDistrictId(toDate,districtId);

                        getCumulativeInactiveUsers(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<FrontLineWorkers> candidatesFromThisBlock = frontLineWorkersDao.getInactiveFrontLineWorkersWithBlockId(toDate,blockId);

                        getCumulativeInactiveUsers(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())){
            reportRequest.setFromDate(toDate);

            if(circleId==0){
                List<AnonymousUsers> anonymousUsersList = anonymousUsersDao.getAnonymousUsers(fromDate,toDate);
                getCircleWiseAnonymousUsers(anonymousUsersList,  rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String circleName=StReplace(circleDao.getByCircleId(circleId).getCircleName());
                String circleFullName = StReplace(circleDao.getByCircleId(circleId).getCircleFullName());
                String rootPathCircle=rootPath+circleFullName+"/";
                List<AnonymousUsers> anonymousUsersListCircle = anonymousUsersDao.getAnonymousUsersCircle(fromDate,toDate,StReplace(circleDao.getByCircleId(circleId).getCircleName()));
                getCircleWiseAnonymousUsers(anonymousUsersListCircle, rootPathCircle, circleFullName, toDate, reportRequest);
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.lowUsage.getReportType())){

            if(stateId==0){
                List<KilkariLowUsage> kilkariLowUsageList = kilkariLowUsageDao.getKilkariLowUsageUsers(getMonthYear(toDate));
                getKilkariLowUsage(kilkariLowUsageList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariLowUsage> candidatesFromThisState = kilkariLowUsageDao.getKilkariLowUsageUsersWithStateId(getMonthYear(toDate), stateId);

                    getKilkariLowUsage(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariLowUsage> candidatesFromThisDistrict = kilkariLowUsageDao.getKilkariLowUsageUsersWithDistrictId(getMonthYear(toDate), districtId);

                        getKilkariLowUsage(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariLowUsage> candidatesFromThisBlock = kilkariLowUsageDao.getKilkariLowUsageUsersWithBlockId(getMonthYear(toDate), blockId);

                        getKilkariLowUsage(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.sixWeeks.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<KilkariDeactivationOther> kilkariDeactivationOthers = kilkariSixWeeksNoAnswerDao.getKilkariUsers(fromDate, toDate);
                getKilkariSixWeekNoAnswer(kilkariDeactivationOthers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariDeactivationOther> candidatesFromThisState = kilkariSixWeeksNoAnswerDao.getKilkariUsersWithStateId(fromDate,toDate,stateId);

                    getKilkariSixWeekNoAnswer(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariDeactivationOther> candidatesFromThisDistrict = kilkariSixWeeksNoAnswerDao.getKilkariUsersWithDistrictId(fromDate,toDate,districtId);

                        getKilkariSixWeekNoAnswer(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariDeactivationOther> candidatesFromThisBlock = kilkariSixWeeksNoAnswerDao.getKilkariUsersWithBlockId(fromDate,toDate,blockId);

                        getKilkariSixWeekNoAnswer(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }

        else if(reportRequest.getReportType().equals(ReportType.lowListenership.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<KilkariDeactivationOther> kilkariDeactivationOthers = kilkariSixWeeksNoAnswerDao.getLowListenershipUsers(fromDate, toDate);
                getKilkariLowListenershipDeactivation(kilkariDeactivationOthers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariDeactivationOther> candidatesFromThisState = kilkariSixWeeksNoAnswerDao.getLowListenershipUsersWithStateId(fromDate,toDate,stateId);

                    getKilkariLowListenershipDeactivation(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariDeactivationOther> candidatesFromThisDistrict = kilkariSixWeeksNoAnswerDao.getLowListenershipUsersWithDistrictId(fromDate,toDate,districtId);

                        getKilkariLowListenershipDeactivation(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariDeactivationOther> candidatesFromThisBlock = kilkariSixWeeksNoAnswerDao.getLowListenershipUsersWithBlockId(fromDate,toDate,blockId);

                        getKilkariLowListenershipDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }

        else if(reportRequest.getReportType().equals(ReportType.selfDeactivated.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<KilkariSelfDeactivated> kilkariSelfDeactivatedList = kilkariSelfDeactivatedDao.getSelfDeactivatedUsers(fromDate, toDate);
                getKilkariSelfDeactivation(kilkariSelfDeactivatedList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<KilkariSelfDeactivated> candidatesFromThisState =kilkariSelfDeactivatedDao.getSelfDeactivatedUsersWithStateId(fromDate,toDate,stateId);

                    getKilkariSelfDeactivation(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<KilkariSelfDeactivated> candidatesFromThisDistrict = kilkariSelfDeactivatedDao.getSelfDeactivatedUsersWithDistrictId(fromDate,toDate,districtId);

                        getKilkariSelfDeactivation(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<KilkariSelfDeactivated> candidatesFromThisBlock = kilkariSelfDeactivatedDao.getSelfDeactivatedUsersWithBlockId(fromDate,toDate,blockId);

                        getKilkariSelfDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.motherRejected.getReportType())){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reportRequest.getFromDate());
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            toDate= calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH,-7);
            fromDate=calendar.getTime();
//            List<MotherImportRejection> motherImportRejections = motherImportRejectionDao.getAllRejectedMotherImportRecords(nextDay);
            reportRequest.setFromDate(toDate);
            String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            if(districtId==0){
                List<MotherImportRejection> candidatesFromThisState =
                        motherImportRejectionDao.getAllRejectedMotherImportRecordsWithStateId(fromDate, toDate,stateId);

                getCumulativeRejectedMotherImports(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
            }
            else{
                String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());;
                String rootPathDistrict = rootPathState+ districtName+ "/";
                if(blockId==0){
                    List<MotherImportRejection> candidatesFromThisDistrict =
                            motherImportRejectionDao.getAllRejectedMotherImportRecordsWithDistrictId(fromDate, toDate,districtId);

                    getCumulativeRejectedMotherImports(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                }
                else{
                    String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    List<MotherImportRejection> candidatesFromThisBlock =
                            motherImportRejectionDao.getAllRejectedMotherImportRecordsWithBlockId(fromDate, toDate,blockId);

                    getCumulativeRejectedMotherImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.childRejected.getReportType())){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reportRequest.getFromDate());
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            toDate=calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            fromDate=calendar.getTime();
//            List<ChildImportRejection> childImportRejections = childImportRejectionDao.getRejectedChildRecords(nextDay);
            reportRequest.setFromDate(toDate);
            String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            if(districtId==0){
                List<ChildImportRejection> candidatesFromThisState =
                        childImportRejectionDao.getRejectedChildRecordsWithStateId(fromDate, toDate, stateId);

                getCumulativeRejectedChildImports(candidatesFromThisState,rootPathState, stateName, toDate,reportRequest);
            }
            else{
                String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                String rootPathDistrict = rootPathState+ districtName+ "/";
                if(blockId==0){
                    List<ChildImportRejection> candidatesFromThisDistrict =
                            childImportRejectionDao.getRejectedChildRecordsWithDistrictId(fromDate, toDate, districtId);

                    getCumulativeRejectedChildImports(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                }
                else{
                    String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    List<ChildImportRejection> candidatesFromThisBlock =
                            childImportRejectionDao.getRejectedChildRecordsWithBlockId(fromDate, toDate,blockId);

                    getCumulativeRejectedChildImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }

        }
        else if(reportRequest.getReportType().equals(ReportType.flwRejected.getReportType())){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reportRequest.getFromDate());
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            toDate=calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH,-7);
            fromDate=calendar.getTime();
            //Date fromDate=calendar.getTime();
//            List<FlwImportRejection> childImportRejections = flwImportRejectionDao.getAllRejectedFlwImportRecords(nextDay);
            reportRequest.setFromDate(toDate);
            String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            if(districtId==0){
                List<FlwImportRejection> candidatesFromThisState =
                        flwImportRejectionDao.getAllRejectedFlwImportRecordsWithStateId(fromDate,toDate,stateId);

                getCumulativeRejectedFlwImports(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
            }
            else{
                String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                String rootPathDistrict = rootPathState+ districtName+ "/";
                if(blockId==0){
                    List<FlwImportRejection> candidatesFromThisDistrict =
                            flwImportRejectionDao.getAllRejectedFlwImportRecordsWithDistrictId(fromDate, toDate,districtId);

                    getCumulativeRejectedFlwImports(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                }
                else{
                    String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    List<FlwImportRejection> candidatesFromThisBlock =
                            flwImportRejectionDao.getAllRejectedFlwImportRecordsWithBlockId(fromDate, toDate,blockId);

                    getCumulativeRejectedFlwImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }

        }
    }

    @Override
    public void modifySpecificReport(ReportRequest reportRequest) {

        String rootPath = reports+reportRequest.getReportType()+ "/";
        Date startDate=new Date(0);
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(reportRequest.getFromDate());
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);

        aCalendar.set(Calendar.DATE, 1);

        Date fromDate = aCalendar.getTime();

        aCalendar.add(Calendar.MONTH, 1);

        Date toDate = aCalendar.getTime();
        int stateId=reportRequest.getStateId();
        int districtId=reportRequest.getDistrictId();
        int blockId=reportRequest.getBlockId();
        if(reportRequest.getReportType().equals(ReportType.maCourse.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(toDate);
                updateCumulativeCourseCompletion(successFullcandidates, rootPath,AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<MACourseFirstCompletion> candidatesFromThisState = maCourseAttemptDao.getSuccessFulCompletionWithStateId(toDate,stateId);

                    updateCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<MACourseFirstCompletion> candidatesFromThisDistrict = maCourseAttemptDao.getSuccessFulCompletionWithDistrictId(toDate,districtId);

                        updateCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<MACourseFirstCompletion> candidatesFromThisBlock = maCourseAttemptDao.getSuccessFulCompletionWithBlockId(toDate,blockId);

                        updateCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }
        else if(reportRequest.getReportType().equals(ReportType.maInactive.getReportType())){
            reportRequest.setFromDate(toDate);
            if(stateId==0){
                List<FrontLineWorkers> inactiveFrontLineWorkers = frontLineWorkersDao.getInactiveFrontLineWorkers(toDate);
                updateCumulativeInactiveUsers(inactiveFrontLineWorkers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
            }
            else{
                String stateName=StReplace(stateDao.findByStateId(stateId).getStateName());
                String rootPathState = rootPath+ stateName+ "/";
                if(districtId==0){
                    List<FrontLineWorkers> candidatesFromThisState = frontLineWorkersDao.getInactiveFrontLineWorkersWithStateId(toDate,stateId);

                    updateCumulativeInactiveUsers(candidatesFromThisState,rootPathState, stateName, toDate, reportRequest);
                }
                else{
                    String districtName=StReplace(districtDao.findByDistrictId(districtId).getDistrictName());
                    String rootPathDistrict = rootPathState+ districtName+ "/";
                    if(blockId==0){
                        List<FrontLineWorkers> candidatesFromThisDistrict = frontLineWorkersDao.getInactiveFrontLineWorkersWithDistrictId(toDate,districtId);

                        updateCumulativeInactiveUsers(candidatesFromThisDistrict,rootPathDistrict, districtName, toDate, reportRequest);
                    }
                    else{
                        String blockName=StReplace(blockDao.findByblockId(blockId).getBlockName());
                        String rootPathblock = rootPathDistrict + blockName+ "/";

                        List<FrontLineWorkers> candidatesFromThisBlock = frontLineWorkersDao.getInactiveFrontLineWorkersWithBlockId(toDate,blockId);

                        updateCumulativeInactiveUsers(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                    }
                }
            }
        }

    }

    @Override
    public void updateCDRDetailsInDatabase() throws IOException {
        CSVReader reader = new CSVReader(new FileReader("emps.csv"), ',');

        // read line by line
        String[] record = null;

//        while ((record = reader.readNext()) != null) {
//            Employee emp = new Employee();
//            emp.setId(record[0]);
//            emp.setName(record[1]);
//            emp.setAge(record[2]);
//            emp.setCountry(record[3]);
//            emps.add(emp);
//        }
//
//        System.out.println(emps);

        reader.close();
    }

    private void getCumulativeRejectedChildImports(List<ChildImportRejection> rejectedChildImports, String rootPath,
                                                   String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Child Import Rejected Details ");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])
        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "State Name",
                "District Name",
                "Health Block",
                "Health Facility",
                "Health Sub-Facility",
                "Taluka Name",
                "Village Name",
                "Child MCTS ID",
                "Child RCH ID",
                "Name",
                "Mobile Number",
                "Rejection Reason"
        });
        Integer counter = 2;
        if(rejectedChildImports.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (ChildImportRejection childRejection : rejectedChildImports) {
            empinfo.put((counter.toString()), new Object[]{
                    (childRejection.getStateId() == null) ? "No State Name": stateDao.findByStateId(childRejection.getStateId()).getStateName(),
                    (childRejection.getDistrictName() == null) ? "No District Name": childRejection.getDistrictName(),
                    (childRejection.getHealthBlockName() == null) ? "No Health Block Name": childRejection.getHealthBlockName(),
                    (childRejection.getPhcName() == null) ? "No Health Facility" : childRejection.getPhcName(),
                    (childRejection.getSubcentreName() == null) ? "No Health Sub-Facility": childRejection.getSubcentreName(),
                    (childRejection.getTalukaName() == null) ? "No Taluka Name" : childRejection.getTalukaName(),
                    (childRejection.getVillageName() == null) ? "No Village Name": childRejection.getVillageName(),
                    (childRejection.getIdNo() == null) ? "No MCTS ID": childRejection.getIdNo(),
                    (childRejection.getRegistrationNo() == null) ? "No RCH ID": childRejection.getRegistrationNo(),
                    (childRejection.getName() == null) ? "No Name": childRejection.getName(),
                    (childRejection.getMobileNo() == null) ? "No Mobile Number": childRejection.getMobileNo(),
                    (childRejection.getRejectionReason() == null) ? "No Rejection Reason": childRejection.getRejectionReason(),
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==8){
                cell.setCellStyle(style);}
                if(rowid == 9 && rejectedChildImports.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:L9"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.childRejected.getReportType() + "_" + place + "_" + getDateMonthYear(toDate) + ".xlsx"));
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

    private void getCumulativeRejectedMotherImports(List<MotherImportRejection> rejectedMotherImports, String rootPath,
                                                   String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Mother Import Rejected Details ");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "State Name",
                "District Name",
                "Health Block",
                "Health Facility",
                "Health Sub-Facility",
                "Taluka Name",
                "Village Name",
                "Mother MCTS ID",
                "Mother RCH ID",
                "Name",
                "Mobile Number",
                "Rejection Reason"
        });
        Integer counter = 2;
        if(rejectedMotherImports.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (MotherImportRejection motherRejection : rejectedMotherImports) {
            empinfo.put((counter.toString()), new Object[]{
                    (motherRejection.getStateId() == null) ? "No State Name": stateDao.findByStateId(motherRejection.getStateId()).getStateName(),
                    (motherRejection.getDistrictName() == null) ? "No District Name": motherRejection.getDistrictName(),
                    (motherRejection.getHealthBlockName() == null) ? "No Health Block": motherRejection.getHealthBlockName(),
                    (motherRejection.getPhcName() == null) ? "No Health Facility" : motherRejection.getPhcName(),
                    (motherRejection.getSubcentreName() == null) ? "No Health Sub-Facility": motherRejection.getSubcentreName(),
                    (motherRejection.getTalukaName() == null) ? "No Taluka Name" : motherRejection.getTalukaName(),
                    (motherRejection.getVillageName() == null) ? "No Village Name": motherRejection.getVillageName(),
                    (motherRejection.getIdNo() == null) ? "No MCTS ID": motherRejection.getIdNo(),
                    (motherRejection.getRegistrationNo() == null) ? "No RCH ID": motherRejection.getRegistrationNo(),
                    (motherRejection.getName() == null) ? "No Name": motherRejection.getName(),
                    (motherRejection.getMobileNo() == null) ? "No Mobile Number": motherRejection.getMobileNo(),
                    (motherRejection.getRejectionReason() == null) ? "No Rejection Reason": motherRejection.getRejectionReason(),
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==8){
                    cell.setCellStyle(style);}
                if(rowid == 9 && rejectedMotherImports.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:L9"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.motherRejected.getReportType() + "_" + place + "_" + getDateMonthYear(toDate) + ".xlsx"));
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

    private void getCumulativeRejectedFlwImports(List<FlwImportRejection> rejectedChildImports, String rootPath,
                                                    String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                "Asha Import Rejected Details");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "State Name",
                "District Name",
                "Health Block",
                "Health Facility",
                "Health Sub-Facility",
                "Taluka Name",
                "Village Name",
                "ASHA Id",
                "ASHA Name",
                "ASHA Job Status",
                "ASHA Mobile Number",
                "Reason For Rejection"
        });
        Integer counter = 2;
        if(rejectedChildImports.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (FlwImportRejection flwRejection : rejectedChildImports) {
            empinfo.put((counter.toString()), new Object[]{
                    (flwRejection.getStateId() == null) ? "No State Name": stateDao.findByStateId(flwRejection.getStateId()).getStateName(),
                    (flwRejection.getDistrictName() == null) ? "No District Name": flwRejection.getDistrictName(),
                    (flwRejection.getHealthBlockName() == null) ? "No Health Block": flwRejection.getHealthBlockName(),
                    (flwRejection.getPhcName() == null) ? "No Health Facility" : flwRejection.getPhcName(),
                    (flwRejection.getSubcentreName() == null) ? "No Health Sub-Facility" : flwRejection.getSubcentreName(),
                    (flwRejection.getTalukaName() == null) ? "No Taluka Name" : flwRejection.getTalukaName(),
                    (flwRejection.getVillageName() == null) ? "No Village Name": flwRejection.getVillageName(),
                    (flwRejection.getFlwId() == null) ? "No ASHA ID": flwRejection.getFlwId(),
                    (flwRejection.getGfName() == null) ? "No ASHA Name": flwRejection.getGfName(),
                    (flwRejection.getGfStatus() == null) ? "No ASHA Job Status": flwRejection.getGfStatus(),
                    (flwRejection.getMsisdn() == null) ? "No ASHA Mobile Number": flwRejection.getMsisdn(),
                    (flwRejection.getRejectionReason() == null) ? "No Rejection Reason": flwRejection.getRejectionReason(),
            });
            counter++;
//            System.out.println("Added "+counter);
        }
        Set<String> keyid = empinfo.keySet();
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==8){
                    cell.setCellStyle(style);}
                if(rowid == 9 && rejectedChildImports.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:L9"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.flwRejected.getReportType() + "_" + place + "_" + getDateMonthYear(toDate) + ".xlsx"));
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

    private void getCumulativeCourseCompletion(List<MACourseFirstCompletion> successfulCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {
        //Create blank workbook


        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " MA Course Completion Report ");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

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
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
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
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==8){
                    cell.setCellStyle(style);}
                if(rowid == 9 && successfulCandidates.isEmpty()){
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:N9"));
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

    private void updateCumulativeCourseCompletion(List<MACourseFirstCompletion> successfulCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {
        if(successfulCandidates != null && ! successfulCandidates.isEmpty()) {
            int stateId = successfulCandidates.get(0).getStateId();
            try {
                boolean create = false;
                FileInputStream file = new FileInputStream(rootPath + ReportType.maCourse.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx");

                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheetAt(0);
                Cell cell3, cell4, cell5, cell6, cell7, cell9 = null;

                for (Integer rowcount = 5; rowcount < successfulCandidates.size() + 5; rowcount++) {


                    //Retrieve the row and check for null
                    XSSFRow sheetrow = sheet.getRow(rowcount);
                    if (sheetrow == null) {
                        sheetrow = sheet.createRow(rowcount);
                    }
                    cell9 = sheetrow.getCell(9);
                    if (cell9 == null) {
                        workbook.removeSheetAt(0);
                        getCumulativeCourseCompletion(successfulCandidates,rootPath,place,toDate,reportRequest);
                        create = true;
                        break;
                    } else {
                        Long ext_flw_id = (long) parseInt(cell9.getStringCellValue());
                        MACourseFirstCompletion maCourseFirstCompletion = maCourseAttemptDao.getSuccessFulCompletionByExtrnalFlwId(toDate, ext_flw_id, stateId);

                        if (maCourseFirstCompletion == null) {
                            continue;
                        }
                        //Update the value of cell
                        cell3 = sheetrow.getCell(3);
                        if (cell3.getStringCellValue().equalsIgnoreCase("No Block") && maCourseFirstCompletion.getBlockId() != null) {
                            cell3.setCellValue(blockDao.findByblockId(maCourseFirstCompletion.getBlockId()).getBlockName());
                        }
                        cell4 = sheetrow.getCell(4);
                        if (cell4.getStringCellValue().equalsIgnoreCase("No Taluka") && maCourseFirstCompletion.getTalukaId() != null) {
                            cell4.setCellValue(talukaDao.findByTalukaId(maCourseFirstCompletion.getTalukaId()).getTalukaName());
                        }
                        cell5 = sheetrow.getCell(5);
                        if (cell5.getStringCellValue().equalsIgnoreCase("No Health Facility") && maCourseFirstCompletion.getHealthFacilityId() != null) {
                            cell5.setCellValue(healthFacilityDao.findByHealthFacilityId(maCourseFirstCompletion.getHealthFacilityId()).getHealthFacilityName());
                        }
                        cell6 = sheetrow.getCell(6);
                        if (cell6.getStringCellValue().equalsIgnoreCase("No Health Subfacility") && maCourseFirstCompletion.getHealthSubFacilityId() != null) {
                            cell6.setCellValue(healthSubFacilityDao.findByHealthSubFacilityId(maCourseFirstCompletion.getHealthSubFacilityId()).getHealthSubFacilityName());
                        }
                        cell7 = sheetrow.getCell(7);
                        if (cell7.getStringCellValue().equalsIgnoreCase("No Village") && maCourseFirstCompletion.getVillageId() != null) {
                            cell7.setCellValue(villageDao.findByVillageId(maCourseFirstCompletion.getVillageId()).getVillageName());
                        }
                    }
                }
                if(!create) {
                    file.close();

                    FileOutputStream outFile = new FileOutputStream(new File(rootPath + ReportType.maCourse.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
                    workbook.write(outFile);
                    outFile.close();
                    workbook = new XSSFWorkbook(new FileInputStream(rootPath + ReportType.maCourse.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
                }

                } catch(FileNotFoundException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }

            }
        }


    private void getCircleWiseAnonymousUsers(List<AnonymousUsers> anonymousUsersList, String rootPath, String place, Date toDate, ReportRequest reportRequest) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Circle-wise Anonymous Users Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        Map<String, Object[]> empinfo =
                new TreeMap<String, Object[]>();
        empinfo.put("1", new Object[]{
                "Circle Name",
                "Mobile Number",
                "Last Called Date"
        });
        Integer counter = 2;
        if(anonymousUsersList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
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
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==8){
                    cell.setCellStyle(style);}
                if(rowid==9 && anonymousUsersList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:C9"));
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

    private void getCumulativeInactiveUsers(List<FrontLineWorkers> inactiveCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                "Cumulative Inactive Users Report "+place+"_"+getMonthYear(toDate));
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

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
            empinfo.put(counter.toString(),new Object[]{"No Records to display"});
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
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==8){
                    cell.setCellStyle(style);}
                if(rowid==9 && inactiveCandidates.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:L9"));
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

    private void updateCumulativeInactiveUsers(List<FrontLineWorkers> inactiveCandidates, String rootPath, String place, Date toDate, ReportRequest reportRequest) {

        if(! inactiveCandidates.isEmpty())
        {
        try {
            boolean create =false;

            FileInputStream file = new FileInputStream(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Cell cell3,cell4,cell5,cell6,cell7, cell9 = null;

            for (Integer rowcount = 5; ; rowcount++) {


                //Retrieve the row and check for null
                XSSFRow sheetrow = sheet.getRow(rowcount);
                if (sheetrow == null) {
                    break;
                }

                cell9 = sheetrow.getCell(9);
                if (cell9 == null) {
                    continue;

                } else {
                    String ext_flw_id = cell9.getStringCellValue();
                    FrontLineWorkers frontLineWorker = frontLineWorkersDao.getINactiveFrontLineWorkerByExternalFlwID(toDate, ext_flw_id);
                    //Update the value of cell
                    cell3 = sheetrow.getCell(3);
                    if (cell3.getStringCellValue().equalsIgnoreCase("No Block") && frontLineWorker.getBlock() != null) {
                        String temp =blockDao.findByblockId(frontLineWorker.getBlock()).getBlockName();
                        cell3.setCellValue(temp);
                    }
                    cell4 = sheetrow.getCell(4);
                    if (cell4.getStringCellValue().equalsIgnoreCase("No Taluka") && frontLineWorker.getTaluka() != null) {
                        cell4.setCellValue(talukaDao.findByTalukaId(frontLineWorker.getTaluka()).getTalukaName());
                    }
                    cell5 = sheetrow.getCell(5);
                    if (cell5.getStringCellValue().equalsIgnoreCase("No Health Facility") && frontLineWorker.getFacility() != null) {
                        cell5.setCellValue(healthFacilityDao.findByHealthFacilityId(frontLineWorker.getFacility()).getHealthFacilityName());
                    }
                    cell6 = sheetrow.getCell(6);
                    if (cell6.getStringCellValue().equalsIgnoreCase("No Health Subfacility") && frontLineWorker.getSubfacility() != null) {
                        cell6.setCellValue(healthSubFacilityDao.findByHealthSubFacilityId(frontLineWorker.getSubfacility()).getHealthSubFacilityName());
                    }
                    cell7 = sheetrow.getCell(7);
                    if (cell7.getStringCellValue().equalsIgnoreCase("No Village") && frontLineWorker.getVillage() != null) {
                        cell7.setCellValue(villageDao.findByVillageId(frontLineWorker.getVillage()).getVillageName());
                    }
                }
            }
            if(!create) {
                file.close();

                FileOutputStream outFile = new FileOutputStream(new File(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
                workbook.write(outFile);
                outFile.close();
                workbook = new XSSFWorkbook(new FileInputStream(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       }
    }

    private void updateCumulativeInactiveUsersInExcel(HashMap<String, FrontLineWorkers> frontLineWorkersHashMap, Integer stateId, String rootPath, String place, Date toDate) {

        if(stateId != null) {
            State state = stateDao.findByStateId(stateId);
            String stateName = state.getStateName();
            try {
                boolean create =false;

                FileInputStream file = new FileInputStream(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx");

                System.out.println("Accessed File " + rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx");

                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheetAt(0);
                Cell cell1, cell3, cell4, cell5, cell6, cell7, cell9 = null;

                for (Integer rowcount = 5; ; rowcount++) {

                    //Retrieve the row and check for null
                    XSSFRow sheetrow = sheet.getRow(rowcount);
                    if (sheetrow == null) {
                        break;
                    }

                    cell1 = sheetrow.getCell(1);

                    cell9 = sheetrow.getCell(9);
                    if (cell9 == null || cell9.getStringCellValue() == null) {
                        continue;

                    } else if (cell1.getStringCellValue().equalsIgnoreCase(stateName)) {
                        String ext_flw_id = cell9.getStringCellValue();
                        FrontLineWorkers frontLineWorker = frontLineWorkersHashMap.get(ext_flw_id);
                        //Update the value of cell

                        if (frontLineWorker == null) {
                            continue;
                        }
                        cell3 = sheetrow.getCell(3);
                        if ( cell3.getStringCellValue() != null && cell3.getStringCellValue().equalsIgnoreCase("No Block") && frontLineWorker.getBlock()!=null) {
                            String temp =blockDao.findByblockId(frontLineWorker.getBlock()).getBlockName();
                            cell3.setCellValue(temp);
                        }
                        cell4 = sheetrow.getCell(4);
                        if (cell4.getStringCellValue() != null && cell4.getStringCellValue().equalsIgnoreCase("No Taluka") && frontLineWorker.getTaluka() != null) {
                            cell4.setCellValue(talukaDao.findByTalukaId(frontLineWorker.getTaluka()).getTalukaName());
                        }
                        cell5 = sheetrow.getCell(5);
                        if (cell5.getStringCellValue() != null && cell5.getStringCellValue().equalsIgnoreCase("No Health Facility") && frontLineWorker.getFacility() != null) {
                            cell5.setCellValue(healthFacilityDao.findByHealthFacilityId(frontLineWorker.getFacility()).getHealthFacilityName());
                        }
                        cell6 = sheetrow.getCell(6);
                        if (cell6.getStringCellValue() != null && cell6.getStringCellValue().equalsIgnoreCase("No Health Subfacility") && frontLineWorker.getSubfacility() != null) {
                            cell6.setCellValue(healthSubFacilityDao.findByHealthSubFacilityId(frontLineWorker.getSubfacility()).getHealthSubFacilityName());
                        }
                        cell7 = sheetrow.getCell(7);
                        if (cell7.getStringCellValue() != null && cell7.getStringCellValue().equalsIgnoreCase("No Village") && frontLineWorker.getVillage() != null) {
                            cell7.setCellValue(villageDao.findByVillageId(frontLineWorker.getVillage()).getVillageName());
                        }
                    }
                }
                if(!create) {
                    file.close();

                    FileOutputStream outFile = new FileOutputStream(new File(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
                    workbook.write(outFile);
                    outFile.close();
                    workbook = new XSSFWorkbook(new FileInputStream(rootPath + ReportType.maInactive.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getKilkariSixWeekNoAnswer(List<KilkariDeactivationOther> kilkariSixWeeksNoAnswersList, String rootPath, String place, Date toDate, ReportRequest reportRequest){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari Non-answering beneficiaries Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

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
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (KilkariDeactivationOther kilkari : kilkariSixWeeksNoAnswersList) {
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
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==8){
                    cell.setCellStyle(style);}
                if(rowid==9 && kilkariSixWeeksNoAnswersList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:L9"));
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

    private void getKilkariLowListenershipDeactivation(List<KilkariDeactivationOther> lowListenershipList, String rootPath, String place, Date toDate, ReportRequest reportRequest){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari Low Listenership deactivated beneficiaries Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

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
        if(lowListenershipList.isEmpty()) {
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
        }
        for (KilkariDeactivationOther kilkari : lowListenershipList) {
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
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==8){
                    cell.setCellStyle(style);}
                if(rowid==6 && lowListenershipList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:L9"));
                }
            }
        }
        //Write the workbook in file system
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(rootPath + ReportType.lowListenership.getReportType() + "_" + place + "_" + getMonthYear(toDate) + ".xlsx"));
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

    private void getKilkariSelfDeactivation(List<KilkariSelfDeactivated> kilkariSelfDeactivatedList, String rootPath,
                                            String place, Date toDate, ReportRequest reportRequest){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari self-deactivators Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

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
            empinfo.put(counter.toString(), new Object[]{"No Records to display"});
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
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==8){
                    cell.setCellStyle(style);}
                if(rowid==9 && kilkariSelfDeactivatedList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:O9"));
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

    private void getKilkariLowUsage(List<KilkariLowUsage> kilkariLowUsageList, String rootPath, String place, Date toDate,ReportRequest reportRequest){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet(
                " Kilkari Low Usage beneficiaries Report");
        //Create row object
        XSSFRow row;
        //This data needs to be written (Object[])

        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

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
            empinfo.put(counter.toString(),new Object[]{"No Records to display"});
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
        createHeadersForReportFiles(workbook, reportRequest);
        int rowid=7;
        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = empinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.toString());
                if(rowid==8){
                    cell.setCellStyle(style);}
                if(rowid==9 && kilkariLowUsageList.isEmpty()) {
                    CellUtil.setAlignment(cell, workbook, CellStyle.ALIGN_CENTER);
                    spreadsheet.addMergedRegion(CellRangeAddress.valueOf("A9:L9"));
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

    private void createHeadersForReportFiles(XSSFWorkbook workbook, ReportRequest reportRequest)  {
        int rowid = 0;
        XSSFSheet spreadsheet = workbook.getSheetAt(0);
        spreadsheet.createRow(rowid++);


            String encodingPrefix = "base64,";
            String pngImageURL = "data:image/png;base64,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";
            int contentStartIndex = pngImageURL.indexOf(encodingPrefix) + encodingPrefix.length();
            byte[] imageData = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL.substring(contentStartIndex));//workbook.addPicture can use this byte array

            String pngImageURL1 = "data:image/png;base64,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";
            int contentStartIndex1 = pngImageURL1.indexOf(encodingPrefix) + encodingPrefix.length();
            byte[] imageData1 = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL1.substring(contentStartIndex1));//workbook.addPicture can use this byte array

            String pngImageURL2 = "data:image/png;base64,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";
            int contentStartIndex2 = pngImageURL2.indexOf(encodingPrefix) + encodingPrefix.length();
            byte[] imageData2 = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL2.substring(contentStartIndex2));//workbook.addPicture can use this byte array


            final int pictureIndex = workbook.addPicture(imageData, Workbook.PICTURE_TYPE_PNG);
            final int pictureIndex1 = workbook.addPicture(imageData1, Workbook.PICTURE_TYPE_PNG);
            final int pictureIndex2 = workbook.addPicture(imageData2, Workbook.PICTURE_TYPE_PNG);


            final CreationHelper helper = workbook.getCreationHelper();
            final Drawing drawing = spreadsheet.createDrawingPatriarch();

            final ClientAnchor anchor = helper.createClientAnchor();
            anchor.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );
            final ClientAnchor anchor1 = helper.createClientAnchor();
            anchor1.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );
            final ClientAnchor anchor2 = helper.createClientAnchor();
            anchor2.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );


            anchor.setCol1( 0 );
            anchor.setRow1( 0 );
            anchor.setRow2( 3 );
            anchor.setCol2( 5 );
            drawing.createPicture( anchor, pictureIndex );

            anchor1.setCol1( 8 );
            anchor1.setRow1( 0 );
            anchor1.setRow2( 3 );
            anchor1.setCol2( 10 );
            anchor1.setDx1(5);
            final Picture pict1 =drawing.createPicture( anchor1, pictureIndex1 );
            pict1.resize(0.28);

            anchor2.setCol1( 10 );
            anchor2.setRow1( 0 );
            anchor2.setRow2( 3 );
            anchor2.setCol2( 11 );
            final Picture pict2 = drawing.createPicture( anchor2, pictureIndex2 );
            pict2.resize(0.24);


        spreadsheet.addMergedRegion(new CellRangeAddress(0,2,0,4));
        spreadsheet.addMergedRegion(new CellRangeAddress(0,2,8,9));
        spreadsheet.addMergedRegion(new CellRangeAddress(0,2,10,10));
        rowid = rowid+2;
        XSSFRow row=spreadsheet.createRow(rowid++);
        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        style.setFont(font);//set it to bold
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //vertical align
        style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_MEDIUM);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_MEDIUM);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_MEDIUM);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        Cell cell1=row.createCell(0);
        Cell cell2=row.createCell(1);
        Cell cell3=row.createCell(7);
        Cell cell4=row.createCell(8);
        spreadsheet.addMergedRegion(new CellRangeAddress(3,4,0,0));
        spreadsheet.addMergedRegion(new CellRangeAddress(3,4,1,5));
        spreadsheet.addMergedRegion(new CellRangeAddress(3,4,7,7));
        XSSFRow row1=spreadsheet.createRow(++rowid);
        Cell cell5=row1.createCell(0);
        Cell cell6=row1.createCell(1);
        Cell cell7=row1.createCell(4);
        Cell cell8=row1.createCell(5);
        Cell cell9=row1.createCell(8);
        Cell cell10=row1.createCell(9);
        cell1.setCellValue("Report:");
        cell2.setCellValue(ReportType.getType(reportRequest.getReportType()).getReportHeader());
        if(reportRequest.getReportType().equals(ReportType.maAnonymous.getReportType())){
            String circleName;
            if(reportRequest.getCircleId()!=0) {
                circleName=circleDao.getByCircleId(reportRequest.getCircleId()).getCircleFullName();
            }else {
                circleName = "ALL";
            }
            cell3.setCellValue("Month:");
            cell4.setCellValue(getMonthYearName(reportRequest.getFromDate()));
            cell5.setCellValue("Circle:");
            cell6.setCellValue(circleName);
        }else {
            if(reportRequest.getReportType().equals(ReportType.flwRejected.getReportType())||
                    reportRequest.getReportType().equals(ReportType.motherRejected.getReportType())||
                    reportRequest.getReportType().equals(ReportType.childRejected.getReportType())){
                cell3.setCellValue("Week:");
                cell4.setCellValue(getDateMonthYearName(reportRequest.getFromDate()));
                spreadsheet.addMergedRegion(new CellRangeAddress(3,4,8,11));
            } else {
                cell3.setCellValue("Month:");
                cell4.setCellValue(getMonthYearName(reportRequest.getFromDate()));
                spreadsheet.addMergedRegion(new CellRangeAddress(3,4,8,9));
            }
            String stateName;
            if(reportRequest.getStateId() !=0){
                stateName=stateDao.findByStateId(reportRequest.getStateId()).getStateName();
            }else {
                stateName="ALL";
            }
            cell5.setCellValue("State:");
            cell6.setCellValue(stateName);
            String districtName;
            if(reportRequest.getDistrictId() !=0){
                districtName =districtDao.findByDistrictId(reportRequest.getDistrictId()).getDistrictName();
            }else {
                districtName="ALL";
            }
            cell7.setCellValue("District:");
            cell8.setCellValue(districtName);
            String blockName;
            if(reportRequest.getBlockId() !=0){
                blockName =blockDao.findByblockId(reportRequest.getBlockId()).getBlockName();
            }else {
                blockName ="ALL";
            }
            cell9.setCellValue("Block:");
            cell10.setCellValue(blockName);
        }
        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);
        cell5.setCellStyle(style);
        cell6.setCellStyle(style);
        cell7.setCellStyle(style);
        cell8.setCellStyle(style);
        cell9.setCellStyle(style);
        cell10.setCellStyle(style);
        spreadsheet.addMergedRegion(new CellRangeAddress(5,5,0,0));
        spreadsheet.addMergedRegion(new CellRangeAddress(5,5,1,2));
        spreadsheet.addMergedRegion(new CellRangeAddress(5,5,5,6));
        spreadsheet.addMergedRegion(new CellRangeAddress(5,5,9,10));

    }
    @Override
    public void createChildImportRejectedFiles(Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.childRejected.getServiceType());
        String rootPath = reports +ReportType.childRejected.getReportType()+ "/";
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(toDate);
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DAY_OF_MONTH,1);
        toDate=aCalendar.getTime();
        aCalendar.add(Calendar.DAY_OF_MONTH, -7);
        Date fromDate=aCalendar.getTime();
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.childRejected.getReportType());
        List<ChildImportRejection> rejectedChildImports = childImportRejectionDao.getRejectedChildRecords(fromDate,toDate);

//        getCumulativeRejectedChildImports(rejectedChildImports, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<ChildImportRejection> candidatesFromThisState = new ArrayList<>();
            for (ChildImportRejection rejectedImport : rejectedChildImports) {
                if ((rejectedImport.getStateId()!=null)&&(rejectedImport.getStateId() == stateId)) {
                    candidatesFromThisState.add(rejectedImport);
                }
            }
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getCumulativeRejectedChildImports(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<ChildImportRejection> candidatesFromThisDistrict = new ArrayList<>();
                for (ChildImportRejection rejectedImport : candidatesFromThisState) {
                    if ((rejectedImport.getDistrictId()!=null)&&(rejectedImport.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(rejectedImport);
                    }
                }
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getCumulativeRejectedChildImports(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<ChildImportRejection> candidatesFromThisBlock = new ArrayList<>();
                    for (ChildImportRejection rejectedImport : candidatesFromThisDistrict) {
                        if ((rejectedImport.getHealthBlockId()!=null)&&(rejectedImport.getHealthBlockId() == blockId)) {
                            candidatesFromThisBlock.add(rejectedImport);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getCumulativeRejectedChildImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }

    }

    @Override
    public void createMotherImportRejectedFiles(Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.motherRejected.getServiceType());
        String rootPath = reports +ReportType.motherRejected.getReportType()+ "/";
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(toDate);
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DAY_OF_MONTH,1);
        toDate=aCalendar.getTime();
        aCalendar.add(Calendar.DAY_OF_MONTH, -7);
        Date fromDate=aCalendar.getTime();
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.motherRejected.getReportType());
        List<MotherImportRejection> rejectedMotherImports =
                motherImportRejectionDao.getAllRejectedMotherImportRecords(fromDate, toDate);
//        getCumulativeRejectedMotherImports(rejectedMotherImports, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<MotherImportRejection> candidatesFromThisState = new ArrayList<>();
            for (MotherImportRejection rejectedImport : rejectedMotherImports) {
                if ((rejectedImport.getStateId()!=null)&&(rejectedImport.getStateId() == stateId)) {
                    candidatesFromThisState.add(rejectedImport);
                }
            }
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getCumulativeRejectedMotherImports(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<MotherImportRejection> candidatesFromThisDistrict = new ArrayList<>();
                for (MotherImportRejection rejectedImport : candidatesFromThisState) {
                    if ((rejectedImport.getDistrictId()!=null)&&(rejectedImport.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(rejectedImport);
                    }
                }
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getCumulativeRejectedMotherImports(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<MotherImportRejection> candidatesFromThisBlock = new ArrayList<>();
                    for (MotherImportRejection rejectedImport : candidatesFromThisDistrict) {
                        if ((rejectedImport.getHealthBlockId()!=null)&&(rejectedImport.getHealthBlockId() == blockId)) {
                            candidatesFromThisBlock.add(rejectedImport);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getCumulativeRejectedMotherImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }

    }

    @Override
    public void createFlwImportRejectedFiles(Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.flwRejected.getServiceType());
        String rootPath = reports +ReportType.flwRejected.getReportType()+ "/";
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(toDate);
        aCalendar.set(Calendar.MILLISECOND, 0);
        aCalendar.set(Calendar.SECOND, 0);
        aCalendar.set(Calendar.MINUTE, 0);
        aCalendar.set(Calendar.HOUR_OF_DAY, 0);
        aCalendar.add(Calendar.DAY_OF_MONTH,1);
        toDate=aCalendar.getTime();
        aCalendar.add(Calendar.DAY_OF_MONTH, -7);
        Date fromDate=aCalendar.getTime();
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.flwRejected.getReportType());

        List<FlwImportRejection> rejectedFlwImports = flwImportRejectionDao.getAllRejectedFlwImportRecords(fromDate, toDate);
//        getCumulativeRejectedFlwImports(rejectedFlwImports, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<FlwImportRejection> candidatesFromThisState = new ArrayList<>();
            for (FlwImportRejection rejectedImport : rejectedFlwImports) {
                if ((rejectedImport.getStateId()!=null)&&(rejectedImport.getStateId() == stateId)) {
                    candidatesFromThisState.add(rejectedImport);
                }
            }
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getCumulativeRejectedFlwImports(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<FlwImportRejection> candidatesFromThisDistrict = new ArrayList<>();
                for (FlwImportRejection rejectedImport : candidatesFromThisState) {
                    if ((rejectedImport.getDistrictId()!=null)&&(rejectedImport.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(rejectedImport);
                    }
                }
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getCumulativeRejectedFlwImports(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<FlwImportRejection> candidatesFromThisBlock = new ArrayList<>();
                    for (FlwImportRejection rejectedImport : candidatesFromThisDistrict) {
                        if ((rejectedImport.getHealthBlockId()!=null)&&(rejectedImport.getHealthBlockId() == blockId)) {
                            candidatesFromThisBlock.add(rejectedImport);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getCumulativeRejectedFlwImports(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }

    }

    @Override
    public void getCumulativeCourseCompletionFiles(Date toDate) {

        List<State> states = stateDao.getStatesByServiceType(ReportType.maCourse.getServiceType());
        String rootPath = reports+ReportType.maCourse.getReportType()+ "/";
        List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.maCourse.getReportType());
        getCumulativeCourseCompletion(successFullcandidates, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
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
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);

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
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
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
                    reportRequest.setBlockId(blockId);
                    getCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate,reportRequest);
                }
            }
        }
    }

    @Override
    public void modifyCumulativeCourseCompletionFiles(Date toDate, Integer stateIdRequest) {

        String rootPath = reports+ReportType.maCourse.getReportType()+ "/";
//        String rootPath = "/home/grameen/home/MA_Cumulative_Course_Completion/";
        List<MACourseFirstCompletion> successFullcandidates = maCourseAttemptDao.getSuccessFulCompletion(toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.maCourse.getReportType());
        updateCumulativeCourseCompletion(successFullcandidates, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        State state = stateDao.findByStateId(stateIdRequest);
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath+ stateName+ "/";
            int stateId = state.getStateId();
            List<MACourseFirstCompletion> candidatesFromThisState = new ArrayList<>();
            for (MACourseFirstCompletion asha : successFullcandidates) {
                if (asha.getStateId() == stateId) {
                    candidatesFromThisState.add(asha);
                }
            }
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            updateCumulativeCourseCompletion(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);

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
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                updateCumulativeCourseCompletion(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
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
                    reportRequest.setBlockId(blockId);
                    updateCumulativeCourseCompletion(candidatesFromThisBlock, rootPathblock, blockName, toDate,reportRequest);
                }
            }

    }

    @Override
    public void getCircleWiseAnonymousFiles(Date startDate, Date toDate) {
        List<Circle> circleList = circleDao.getAllCircles();
        String rootPath = reports+ReportType.maAnonymous.getReportType()+ "/";
        List<AnonymousUsers> anonymousUsersList = anonymousUsersDao.getAnonymousUsers(startDate,toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setCircleId(0);
        reportRequest.setReportType(ReportType.maAnonymous.getReportType());
        getCircleWiseAnonymousUsers(anonymousUsersList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
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
            reportRequest.setCircleId(circle.getCircleId());
            getCircleWiseAnonymousUsers(anonymousUsersListCircle, rootPathCircle, circleFullName, toDate, reportRequest);
        }
    }

    @Override
    public void getCumulativeInactiveFiles(Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.maInactive.getServiceType());
        String rootPath = reports+ReportType.maInactive.getReportType()+ "/";
        List<FrontLineWorkers> inactiveFrontLineWorkers = frontLineWorkersDao.getInactiveFrontLineWorkers(toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.maInactive.getReportType());
        getCumulativeInactiveUsers(inactiveFrontLineWorkers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
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
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getCumulativeInactiveUsers(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
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
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getCumulativeInactiveUsers(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
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
                    reportRequest.setBlockId(blockId);
                    getCumulativeInactiveUsers(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }
    }

    @Override
    public void modifyCumulativeInactiveFiles(Date toDate, Integer stateIdRequest) {
        //List<State> states = stateDao.getStatesByServiceType(ReportType.maInactive.getServiceType());
        String rootPath = reports+ReportType.maInactive.getReportType()+ "/";

        List<FrontLineWorkers> allFrontLineWorkers = frontLineWorkersDao.getAllFrontLineWorkers(toDate, stateIdRequest);

        HashMap<String, FrontLineWorkers> frontLineWorkersMap = new HashMap<>();

        for (FrontLineWorkers frontLineWorkers : allFrontLineWorkers) {
            frontLineWorkersMap.put(frontLineWorkers.getExternalFlwId(), frontLineWorkers);
        }

        updateCumulativeInactiveUsersInExcel(frontLineWorkersMap, stateIdRequest, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate);
        State state = stateDao.findByStateId(stateIdRequest);

            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";

            updateCumulativeInactiveUsersInExcel(frontLineWorkersMap, stateIdRequest, rootPathState, stateName, toDate);
            List<District> districts = districtDao.getDistrictsOfState(stateIdRequest);
            for (District district : districts) {


                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState  + districtName+ "/";
                int districtId = district.getDistrictId();

               updateCumulativeInactiveUsersInExcel(frontLineWorkersMap, stateIdRequest, rootPathDistrict, districtName, toDate);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {

                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict  + blockName+ "/";

                    updateCumulativeInactiveUsersInExcel(frontLineWorkersMap, stateIdRequest, rootPathblock, blockName, toDate);
                }
            }

    }

    @Override
    public void getKilkariSixWeekNoAnswerFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.sixWeeks.getServiceType());
        String rootPath = reports +ReportType.sixWeeks.getReportType()+ "/";
        List<KilkariDeactivationOther> kilkariDeactivationOthers = kilkariSixWeeksNoAnswerDao.getKilkariUsers(fromDate, toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.sixWeeks.getReportType());
        getKilkariSixWeekNoAnswer(kilkariDeactivationOthers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate,reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<KilkariDeactivationOther> candidatesFromThisState = new ArrayList<>();
            for (KilkariDeactivationOther kilkari : kilkariDeactivationOthers) {
                if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                    candidatesFromThisState.add(kilkari);
                }
            }
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getKilkariSixWeekNoAnswer(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<KilkariDeactivationOther> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariDeactivationOther kilkari : candidatesFromThisState) {
                    if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getKilkariSixWeekNoAnswer(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<KilkariDeactivationOther> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariDeactivationOther kilkari : candidatesFromThisDistrict) {
                        if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getKilkariSixWeekNoAnswer(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }
    }

    @Override
    public void getKilkariLowListenershipDeactivationFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.lowListenership.getServiceType());
        String rootPath = reports +ReportType.lowListenership.getReportType()+ "/";
        List<KilkariDeactivationOther> kilkariDeactivationOthers = kilkariSixWeeksNoAnswerDao.getLowListenershipUsers(fromDate, toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.lowListenership.getReportType());
        getKilkariLowListenershipDeactivation(kilkariDeactivationOthers, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
        for (State state : states) {
            String stateName = StReplace(state.getStateName());
            String rootPathState = rootPath + stateName+ "/";
            int stateId = state.getStateId();
            List<KilkariDeactivationOther> candidatesFromThisState = new ArrayList<>();
            for (KilkariDeactivationOther kilkari : kilkariDeactivationOthers) {
                if ((kilkari.getStateId()!=null)&&(kilkari.getStateId() == stateId)) {
                    candidatesFromThisState.add(kilkari);
                }
            }
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getKilkariLowListenershipDeactivation(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
            List<District> districts = districtDao.getDistrictsOfState(stateId);

            for (District district : districts) {
                String districtName = StReplace(district.getDistrictName());
                String rootPathDistrict = rootPathState + districtName+ "/";
                int districtId = district.getDistrictId();
                List<KilkariDeactivationOther> candidatesFromThisDistrict = new ArrayList<>();
                for (KilkariDeactivationOther kilkari : candidatesFromThisState) {
                    if ((kilkari.getDistrictId()!=null)&&(kilkari.getDistrictId() == districtId)) {
                        candidatesFromThisDistrict.add(kilkari);
                    }
                }
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getKilkariLowListenershipDeactivation(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
                List<Block> Blocks = blockDao.getBlocksOfDistrict(districtId);
                for (Block block : Blocks) {
                    String blockName = StReplace(block.getBlockName());
                    String rootPathblock = rootPathDistrict + blockName+ "/";

                    int blockId = block.getBlockId();
                    List<KilkariDeactivationOther> candidatesFromThisBlock = new ArrayList<>();
                    for (KilkariDeactivationOther kilkari : candidatesFromThisDistrict) {
                        if ((kilkari.getBlockId()!=null)&&(kilkari.getBlockId() == blockId)) {
                            candidatesFromThisBlock.add(kilkari);
                        }
                    }
                    reportRequest.setBlockId(blockId);
                    getKilkariLowListenershipDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }
    }

    @Override
    public void getKilkariLowUsageFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.lowUsage.getServiceType());
        String rootPath = reports+ReportType.lowUsage.getReportType() + "/";
        List<KilkariLowUsage> kilkariLowUsageList = kilkariLowUsageDao.getKilkariLowUsageUsers(getMonthYear(toDate));
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.lowUsage.getReportType());
        getKilkariLowUsage(kilkariLowUsageList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate,reportRequest);
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
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getKilkariLowUsage(candidatesFromThisState, rootPathState, stateName, toDate,reportRequest);
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
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getKilkariLowUsage(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate, reportRequest);
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
                    reportRequest.setBlockId(blockId);
                    getKilkariLowUsage(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
                }
            }
        }
    }

    @Override
    public void getKilkariSelfDeactivationFiles(Date fromDate, Date toDate) {
        List<State> states = stateDao.getStatesByServiceType(ReportType.selfDeactivated.getServiceType());
        String rootPath = reports+ReportType.selfDeactivated.getReportType() + "/";
        List<KilkariSelfDeactivated> kilkariSelfDeactivatedList = kilkariSelfDeactivatedDao.getSelfDeactivatedUsers(fromDate, toDate);
        ReportRequest reportRequest=new ReportRequest();
        reportRequest.setFromDate(toDate);
        reportRequest.setBlockId(0);
        reportRequest.setDistrictId(0);
        reportRequest.setStateId(0);
        reportRequest.setReportType(ReportType.selfDeactivated.getReportType());
        getKilkariSelfDeactivation(kilkariSelfDeactivatedList, rootPath, AccessLevel.NATIONAL.getAccessLevel(), toDate, reportRequest);
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
            reportRequest.setStateId(stateId);
            reportRequest.setBlockId(0);
            reportRequest.setDistrictId(0);
            getKilkariSelfDeactivation(candidatesFromThisState, rootPathState, stateName, toDate, reportRequest);
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
                reportRequest.setDistrictId(districtId);
                reportRequest.setBlockId(0);
                getKilkariSelfDeactivation(candidatesFromThisDistrict, rootPathDistrict, districtName, toDate,reportRequest);
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
                    reportRequest.setBlockId(blockId);
                    getKilkariSelfDeactivation(candidatesFromThisBlock, rootPathblock, blockName, toDate, reportRequest);
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

    private String getDateMonthYear(Date toDate) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(toDate);
        calendar.add( Calendar.DAY_OF_WEEK, -(calendar.get(Calendar.DAY_OF_WEEK)-1));
        int date=calendar.get(Calendar.DATE);
        int month=calendar.get(Calendar.MONTH)+1;
        int year=(calendar.get(Calendar.YEAR))%100;
        String dateString;
        if(date<10) {
            dateString="0"+String.valueOf(date);
        }
        else dateString=String.valueOf(date);
        String monthString;
        if(month<10){
            monthString="0"+String.valueOf(month);
        }
        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return dateString + "_" + monthString+"_"+yearString;

    }


    private String getMonthYearName(Date toDate) {
        Calendar c =Calendar.getInstance();
        c.setTime(toDate);
        c.add(Calendar.MONTH, -1);
        int month=c.get(Calendar.MONTH);
//        String monthString = "";
        int year=(c.get(Calendar.YEAR))%100;
        String monthString = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
//        if(month<10){
//            monthString="0"+String.valueOf(month);
//        }
//        else monthString=String.valueOf(month);

        String yearString=String.valueOf(year);

        return monthString+" "+yearString;
    }

    /**
     * from date to todate in String format.
     * @param toDate
     * @return String containing fromdate to todate
     */
    private String getDateMonthYearName(Date toDate) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(toDate);
        int toDateValue =calendar.get(Calendar.DATE)-1;
        int toDateYear =(calendar.get(Calendar.YEAR))%100;
        String toDateString;
        if(toDateValue <10) {
            toDateString ="0"+String.valueOf(toDateValue);
        }
        else {
            toDateString =String.valueOf(toDateValue);
        }
        String toMonthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        String toYearString =String.valueOf(toDateYear);
        calendar.add(Calendar.DAY_OF_MONTH, -6);

        int fromDateValue=calendar.get(Calendar.DATE)-1;
        String fromStringDate;
        if(fromDateValue <10) {
            fromStringDate ="0"+String.valueOf(fromDateValue);
        }
        else {
            fromStringDate =String.valueOf(fromDateValue);
        }
        String fromMonthString = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );
        String fromYearString =String.valueOf((calendar.get(Calendar.YEAR))%100);

        return fromStringDate+"-"+fromMonthString+"-"+fromYearString+" to " +toDateString + "-" +toMonthString +"-"+ toYearString;
    }



}
