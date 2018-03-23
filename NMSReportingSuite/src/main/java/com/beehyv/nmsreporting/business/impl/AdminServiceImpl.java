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
            String pngImageURL = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAABQ0AAABoCAIAAADU50GAAAAAiHpUWHRSYXcgcHJvZmlsZSB0eXBlIGV4aWYAAHjaVY7RDcNACEP/maIjcMAZGKdKE6kbdPyCLlWa9wGWhWxo/7wPejSDhWx6IAEuLC3lWSJ4ocxDePSuuTi3jlJy2aSyBDKc7Tq00/8xFYHDzR0TGzapdNlVVGp2UKdyv5F/rS+eq/buI+7h9AVIiixSCQcvRwAACghpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+Cjx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IlhNUCBDb3JlIDQuNC4wLUV4aXYyIj4KIDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+CiAgPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIKICAgIHhtbG5zOmV4aWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vZXhpZi8xLjAvIgogICAgeG1sbnM6dGlmZj0iaHR0cDovL25zLmFkb2JlLmNvbS90aWZmLzEuMC8iCiAgIGV4aWY6UGl4ZWxYRGltZW5zaW9uPSIxMjkzIgogICBleGlmOlBpeGVsWURpbWVuc2lvbj0iMTA0IgogICB0aWZmOkltYWdlV2lkdGg9IjEyOTMiCiAgIHRpZmY6SW1hZ2VIZWlnaHQ9IjEwNCIKICAgdGlmZjpPcmllbnRhdGlvbj0iMSIvPgogPC9yZGY6UkRGPgo8L3g6eG1wbWV0YT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAg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";
            int contentStartIndex = pngImageURL.indexOf(encodingPrefix) + encodingPrefix.length();
            byte[] imageData = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL.substring(contentStartIndex));//workbook.addPicture can use this byte array

//            String pngImageURL1 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAZAAAADRCAIAAABtrnPmAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAIolJREFUeNrsnW1sVUd6xy8BQ27AQEBJXEpMXghGsYQjrQthG1CgCo4K+EPVuB/AiVaqKeoSaZOV+wWULcio1braRIJtWYia3Zh8WG/7xU5WG9ICsqkSs1TCaKE4SxLsZYnZLI5NAAcT2P65Tzx7mJlz7py362vz/wldmXvPy5w5M/95nplnZiYNDw9nCCFkPHAXs4AQQsEihBAKFiGEgkUIIRQsQgihYBFCKFiEEELBIoQQChYhhIJFCCEULEIIoWARQihYhBBCwSKEEAoWIYSCRQghFCxCCKFgEUIoWIQQQsEihBAKFiGEgkUIIRQsQgihYBFCKFiEEELBIoQQChYhhIJFCCEULEIIBYsQQihYhBBCwSKEULAIIYSCRQghFCxCCAWLEEIoWIQQQsEihFCwCCGEgkUIIRQsQggFixBCKFiEEELBIoRQsAghhIJFCCEULEIIBYsQQihYhBBCwSKEULAIIYSCRQghFCxCCAWLEEIoWIQQQsEihIwzpkyYJ/nqw2P4vHnx/M2B87dJ8px5d82dhz8mP1gxKVvKV04IBavQ/GH4CyjU9Q+P3TjXc+M3Pfiv6wMvqp48v2IK/i2qFiEjhIwXJg0PD4+j5MKAGvmgbeT4IehUAv7w3HklVatKFlXjk0WBEApWYvbU9eOHvjz4ViI6ZcmFbOnU5bV3r95Am4sQClYsqbp28K0v//std6cvloe8qDq7djM+WTIIoWAVr1RRtgihYEVk5P22qz9rLrxUeSmpWnXPc410EgmhYPly8+L5K2++IjEKY5872dLsus3TVm9gQSGEgqVzvfvQlZ+8MraGldXUmv7CDsZwEULB+iPwAa8dfKs4swmO4YzNr06eX8ESQ8idLlgwqS7vealI3MAA97D05depWYSMpelQDGr1xQ/+tsjVSqUzpUAwQsg4EKzxpQLULELuXMEaj/Vf0nzz4nkWHUIKz5j1YeVVq7vm3lploWRR9aRsqfQcyTfeY3D6H67eGlL86tfHblw8Dx0pjGuJ9JS+/DrHDQm5UwTri1ct/VZTFlVDoaY8Vh1nKRhcFvqV1ARpP6Yur53+/A4WIEImvmB5IxjSWzJBlna49n5bSh7cjM2vcpkHQia4YF3vPnR5z0uyQMK05bUFCBQYeb9t+J09icsWpHbm1p/SMSRkwgoWVAPmFXSq8LZJGrKVXbf57rWbU0rw0NBQd3e3y5ELcrA0p5ftkXPYerXZs2cvWbKkGB62N4f25cqVKylYRcEfhr8YfntPgvH0MK9gZKU0QXrnzp1NTU3ux6MOoKitX7++mAtc8bN79+7Gxkbty3fffTdarm7atKmlpUX7ctu2bVu3bi2Gh128eLEmWChFXV1dRft27qxNKKAv9zzXWPpSYgN8txTwnT0ppXbXrl2hjj9x4gQqW01NDUphe3u7+4moUTirrKwMn6FODEtHR0ddXR2ShxuZ1bh4BMs0r6KpFcwr62Nu3LixGJ4Ur8M0r7Zs2WItHvLWcAotrLExtZIKAYP2zdr588R7slBK0DjHuQJMrX379s2aNSusCXD69Ok0HEzTYIxstqRah1EttS/37t1bX1+flLGG99La2loMD4vGQ2ufUFp6enq8ZUYrHvgJ9tcY9j/codt8JTgxENo38n5b4incv39/zCugLKJE5pVF0wQwW934wPoz3dvOzs5iKxhmtqOK1tbWJmWsgWjalzh4y6Y1jSf1qhXSrxUP2IxpFA93infXnKGRK90DZ/F5YuCs+Wv5jPsXzLhvQe4zjmYlYmdde78t2TWzUCZM2xslqaqqymsLuNgLsGsCukusDuDs2bMTf5vWG5WXlxdVkUO2m/Kt1eGYDhdsE1hYxSnNQCsq1rc2tsM7xSVY0KaO/pP4hz96L//O8ayVZZVL5jyEz/XlS8dEs3D6zYvnE+x6t7bMqDnwTUwhAAH9QbBrNm7c6FfITG1C+5/GAJZZ53EX09ZAA25ahXAbC9NFba3DZp/OmFwtccwyg3zWyon51pD+sRWsoujDgjy1nDnU3vdLd5Hy7bgpX4p/9QufDiU30KyYqwbe81xjUkYWKm1FRQU+te8DupbQkqOew+2y/opC1tzc7HfismXL1L0Cjoz/UGvWrFEp9Otfsw6MFqyrK8EhM1wHVzO/7+/vj2avJW7wmg2D2VWH94W3popHMQxujrGF1XLm8P4zh2BSJfYm+o7iX+PRN158fO2Wx9fOmjo97ymT51dAbq68+Uqc+450H0pKsNra2ky1Mls/zUo/cOCAVxE03yTgRFQhOQD+Znp1STpr5UYBMU3WZr8waoU67DJkFse8ghwUg1pZ8xkJMw1e6HVPT4/EkaVaPMaBhZWTlR/HN6mCKsnU6e6ydXnPS9e7D8W53b3/djyRZMPkMXXHZaDKr1UH42Is2DowGnmELizWITOoeVLGWqZoRkWt5SQ943rcCxYcQFhACVpVeWWreem38jqJNy+eH9r2l3FuVPrS6/F3BoNUQbDM1s+x5ljDFK2ChcP6+vq0LwMMfhkHME/xQ3Wc4SxzNBDVAw+Fa3otESTJrORwQ/7o769fH9y/htzD7fAp1/GaljhROuwgGWJNeO2FZOuw1eFCbsCpN11g7Zvy8nIRaDwFMkSeBSC1SDl+cuyzh5EuWSGZoLICFxkcHDRbRK3DIWzx0G4Kowx/e2+khozkjxUrVkTT7kIL1s7jrU3HxyAIZX350n1PfTvY1PrynT3Db0ePAp3+/I6py2tjptOqOO41xxr4YwqWtZvMLz4I6UG9CjWY7a2cpq2hfkVSrcMLfvj14qGSQPisrnTe5AnWlESOR7MGWJqmojXmS140EuOX4Xk7kiQ3QsXlaq8e2VhWVmb6s+aYj7eQyPiP4x2Dr1YUfVhDI1fqDn6/YIaV6YEuazvbuvoflsx5yO+Yaas3xBGsmwNxZymioKDWmd+796Q4DvBZ67Z5FxR9CGiEuBtVMwNiqf2iwAOuacqHxG2EDb82fUyXITN3h8sakmIGc1nFuqmpKVh2cYDYp9ZbQ3kjzFXQMsSaML9CGKE9y8ToHCxQ4CjcwIr/+PuxUquvX+fl3635xfesUV1fW5u5BSQiX/967LUD8e7DdrdrWEMxzcLtMvtEZvlEixJUU0+scZhSPawPG4BmVuBcVM5ok0W0mTHWlESuUX4hKdpbsMZtynPlvYV1SjyeYtmyZRHUygwNM+V7SQ5r90WEJg0lLXLoTCEECzoFpYCFNeY9dkhDsGbdPaZ7poZq2fyad/NLb7ipWCVmF4Z2F6triZKNw+CSBAsoSr8cYLWhVNUN5Qxqqi1BEqGuYCZPYapqnPBOq9lovsTgmQyozzjFvZcHNg6EI1QD4GdeOY6W4jC/UemwDUZxuYQtZw5vOrI7UzSIZh14drvVN5w8v+KuufPGZMn2RAKjreaGdgW/EXfvRUy1am5uVqUWf6Bp1VJrDoEF66/qRbJ2qAX0cWghXWZ2iSQB67CjVvesHlzkcUmrsWY1T/zcYYmSVTlpHW3UBDegK1AMZ5UAHGnmm2lv5vVnAya6ynohuJ2EQZhD3tb4iWIRLNgyRaVWSrMajuyGZln74EuqVkVbfybmcvJ5dSSa5GmCZbV6tCEzc+hqSw5vmUNNyOsIOHoWqGxmJffrV/ZTKyQJpp9pJ+b1fM2HjWMCOEa3W8dDrY9gdfC9ggVLx6pWOAZ56C0/1mmAWs+g1VHVigcy36pWeLNo1bzZi6uZbypmkMqUVNUKtkymKEHamo63Ni/9lkWwFlUXfgPqRNYhsdY9FCNtuDr4LlaLw6xFedXKPQ7TXEXH9NoUTU1NVrU6cOCAJoUncgQnwDrKEXD3PIVqNIYgr0Fh7dozV0Gwrq7nNXaQ/oaGBms7B+3Qes2sIy1aAcsruNa5UxmfqQvxuzgKJ1gyJujXb7Vgxv3wyKrmPFQ+Ont5ZVmlJiiDI1dk5nP3wNlQUwtdO4xOvbO+fKl230xuI4zCC6hVR0LVnI4cecuHWYZk2b8AJTJ7/U0xkiihsJ5FJmSHNx7QTL9VrTJuKyU4jpbG6YI01craJLz44ovmu84rH3DxzPSj2Fi9abM9M+1Ns/HQ3j4aDLOEIPOtE63MMhC5MUhdsBqO/FCTGJmfLP/yhp6rDiY1n7n38mcd/Sdl5k1Sidx5vHXls9u1LydlS/EvwtTCOEtixVyHBKXWaqWjcGidU3mtnrwL5uJe5nQ/TYlcPAu/WhQwF8dqQsKNMtXKxfPNJL1Wn2NIivVdW03pYJ/aekc8ILTDsbtAS5u18fAmDL9aG4zW1larWplXiz9pIRXBgvGiZAXSU79wFXQn8jowo0bZffULn8Y/mF1tfb+E1sS3uWRlCNPImvxgRYQOKZwVLRlW7ylUdzusdKuPprW01tKmaY3ZAKLkecPWzY5bXESLa3X0BawP7ucFWw0TGbh0tFi1K7v4jO44zgC1KqnV7sjrUyM3rIpgDdFy6SENiEEJyFVrfJxfYxB/aZ3kBQuC0nS8FTYUxGXL4+ti6pSl0zF3ZfyD1mwy7LhEjKyU1mgP5Q861hzpU/BzBjVfL++CbZnRGBlvTZZpK3697OKRaRdxjMO0lmm/RtgaYeSXS3k930zSq+tZrT8zeVZds940r09tHSe1KoKLvennqEZ7BdbGIJE5ocnHYbWcOdy89Fs9f/2v+ExcrW6rA2WVp3N3cZnbHGBkwdnUbaVIghXNJfQLIHR5u7L6h1WtUD+9s/AyYZZnsvZHWNVKVonQPLK8noVKvJnygBbY+phW983F/XH0Gd07EB1tZEe7w8Wndo+YdWkRXUZLrRauo3mVSWgtsOQFa8vja2H+xBGRsLfrqv2XgAk3+T2yhDrFpkRyCaOtQyJDy9Z1HUStHK0ea+9Pd3e3FmtqNawgiF1dXeYVrHGYLrNhMsbUIq8KWKMirQHTLsscJ7s3hGNIirvdEbnCW1NitTe9WecyWuoX+ObYnZfU0jrFu0RyqO6tA89ubzz645Yzh6IJFlQvAe2fE8Uus9YclANr+ymBLdZQ9bxq5dJbhJIdPJdNPLsVK1b4LRzsHodpfQSYFdDKwcFBmUmL2wVPkcUxXvMEd4el4LLMsYvP6NoH4hyS4i5DLj41EmxmNU6Ufj31vC7xJS6OKl6K9XXjRM3uwytwMbHvXMGSjq29T30755CG1qykZjhG6HS36kgmN3gcLQ1+K3nm7U/N2Fb7xNWam5tDjUPHjMPUZvxDGYOPb2hokBSKjvvNpNPCUB23t4rT5Jj96O52h6NPDQnzmz4NlPhaI1Q1e9PFUbUWA1nqVvSxs7MTD+hniCW1EFjygtV+8sKJ85c6Pr54W2vwJzNXPjp3feUDqcoWNGtw5EoEF+/EwNk4TuXXghV+D574W+NoPpq11jlaPWYMjpzoXtqSjcPUuqisBoVfPEewbeIyWuqOo93kKEOOrUtmdHUwP1s7oIdLszcdR0ulu8qUP78AwMR7r5Lvw2o5dm7xPx2s+8mxpvc+7Pjo4uDw9VuW5PB1/L37yCf4vux7B3a+9+uh3PcpkXfRKyuDtwe4Rlh3IUK4qVVHooHS3NXV5T5kZpYh6xi59JTV1NRks1nZRzN4LZdQcZh5RVCL0Y9c6DVRcBwtdTcJrd3t5tM5xny5jNYpWltbIzQGeWOJ/bz4aMN8MScPpiJYMKmWvda5qbW79/Ph+ur5rS9Uv7v5Sfyx8pG5tZVl+Bv/tjz18C179b0PK/75kGZ/Jesb7sv5hqHojO0VloQXrGiLDZhF4fTp03v37g0ouC6BP3l72aUiwdeQPaKtrl+oOExUm+A1RrQTcang/ixZSjRvbXHZ3iqmP2hdWczRCQ3lUyNP0FCFCm7SRlpCjZYil4JfmTUlkRuDFF1CCBA0C07f1mce23/sHIyp235+L/cwlQ/sq6sSa6v9VxegZSlplky4idMzFSFqtKRqVQQLK5pjH2qRWVQVU4zMqoJrQg4c95qWYHek36sg+O/s2bO1JAX0XkkAV2Njo1ZhZJYPir7po6Ei4RY4xVv5xZvDjfCY5jQ3a8SAKYXRnNahHOZbsI4PuhyWyfVta0dqlqaZjbCzZD3lvKMxyD3TsjYTFvDW8MrQLO3atctrSkuHF66Muyc41clKYkskQ7C6z19qbD8Fj29WtqS28oEl82biH77Hv7aTF8QT3PbMovW5n1LtzAq7ps22J+q2PvF1WY+wuPukbOnsHxTdJsYRvBuUxVBealK7KqibOm7NomYFR9aaCQxUwzuiB5FKY6NJdZcC76aTWKe70iC4fnAGW46daz95obHtFMyuBfdmW1/4RudHA7uOfFJ+bzZttbrVdi18uvHoG9GWDIywqWrJE6vGdRE3V7kVI866W8FtrnRnZyKCFfYi1Kngvr8Jc5cUBUs0q3/7GujUstdumRvQKaiVdLpDvyBk+LVgDwavMFpEaIQe92lPOg0waZvEFBI//9FcXgqHwYb3elLiaARsizC2j0bGFwH7kBdasL4u8Y/MgYW1sXp+78Bw3+fDs7JTIGT7j53b9sxjhcyXqjkPRRSskLsT3jV3nuMQIWp15ACrmGzbts0qWJpaoSTByzPNH9lgytxJRTWzY/hoZHyBtrO4BGtWtmRvXRVMqk2t3cpJxDeFzhdjDQaXg+EPhl0feZrzvhVmn3TBKC8vt3qCmscXUJKsK5koQ2wMH42ML2R3yCISLAFGFj6hWVCr5trHiz0TR0O3rr3fFtofdN63AvaIab+MIeZOmfD+li1bVltbC4ET8ZJteKwRzN7J1cX2aGSikuLUHGhWbeUDMLjG5MGqwkSuqzD3sP7g1OW1cdbtG1usZpc17tnycuvrkx2uJqRwguV1AF3o+s6KtMcK3ePdvWoV1h/Mrt08ft89RGf//v1ho+1lub4EY5cJcSeZSPfdRz5xP3jlo3MLENkQsPmgYYs9LH98GXLvCZhXBV7qL3HgxwVHyWsuLaSqp6eHakXGsYUloaHux8scnbQZdA7Ckh532FZhA9zHtXnltbOALFxjdQZhUqlt5lhhyLgXrFDm1YJ7s2mv2SC4R43Wlv8ZPoff2RPq+tNWbxjv5tVt7yVH/CW3CSl2wWo7ecH94K3PLCrMgzm6hLKFz41zPSNhxgcnZUuz6+KaV+b6U5nREHPZqfwOCeaGWWduNB0A3NJEwqw7OjpkDDTI+nabsEnGjWC1j04SdEHmGBbmwbrdBGvjwluzaq7+rDmcM7huc0qDg0NDQ7LA0O7du6FZ2m40E5LBwcFQHf/WpS8jALXKG+zqF3BLxqtgtRz7jfvB9d+YX7AoB0cLC/7g9e5DoXqvpiyqdo+9csE7/1wESyYhy2otE16zoAjmDPyamhpZSKAA4V0Bt+CMxQklWLCt2sP4g1tWPFyYp+q9/JnL9l/1C1fNvHHzUhjzCobV9Od3pJdyCR8HUmPFzmK1SVsxmQl3hGCF6r2SZRsK81SOswg3Lnx6+O09oWKv4AwWpq8dOiWOUm9vbwTBknP9VjWQtUH8KqqcG3lY0Hu6DD5KShKf348rq+vLDLVCKnvA+ioRVr9RTrHLKbKzkSw+VV5eLp2eYXsecIWUckxyRpa1kfVdJZ3WvSkLKlidYRYO3fiN+QUrTC6r960sq/zmpeEvwsRelVStStYZDDa1rOUMNpc2UUZ66M0dJQJcKtkpeu/evWZEFc6CcYc/Tp8+rdIgu9F4V0DGTWtra727s3i9uUxu9d6WlhbvYsTwbZMKjsdltWX8lK2UVJe8ifJSYf9qa1eo3TpkAMHbJYcv8ZM2/KoyGT+Ze2fIrmjWRVCR/+aSh5nRHbC92YtjZDlGvEfrbt64DlKF12TeRbaPQxq8CzTikb2TT2XdROuysZIDsrSptght/NX3YgWOultYs7IlMruwMP6gi4W14U+rr7z5Soicmjtv+gs7Cqa5agBLTRZFWamoqGhqalI2i4iFdY9CWTTSugSlWoY8YA877xrKKKa4uLaHAi6LlKxZs8ZvT7CGhgatHgbsHhYK1AcRXFEo2XpDqo3MhbQuW5xYW9jRIVopqz9LLuFJcV9kHT5xgCyaKkmSfWX8koRLqVxSGS5LX+BSWo5JAZBLiajh2UUKcQou5V0wVi1MHPCWrQvSqzKjFBPJQGJwffleLitrKwfktuqEldxIKogvumCdOH/JfXywvoDmlYtaLZhx/1/9z8FQzuCMza8WbNqg7A+Y8Wx4KZs8yx5wKKb9/f1oOeVT2lUcgIqhijhKs1QAc5119Y0pZ2qDBtVWixrKfdEaD+fAfWXms9zUz+mQxWrklK6urrx7djnmjKQf2YJnx/VRr2Cn4Pr4W2oFEuwyHdJbtaz4KaxkhdwdnzIqIraPZF1PTw9+6sohrw8/+V1N1qpHFskLRcaqdsg7iCkrl8lFcEdZyB/PjpTgLJEtsZtUImXrCm05Y02n/IqHt8XCK5b8lIIHkFo8oBwQkNsyiwvH42B8Wq25wglWcXa338rxU+/kPWbXyOxQ85ynP78jwi5e7r6Gl7KyMil2KBD79u1TTbGoxoEDB1BMVXslHoeY7tLMqstKC6ltfag2ZZEraOVVdkCQBbDUfTOj668rvwb/FZnI+O/yhGNQXdV1Im9TqqF0HOnRPB1cX+0gG2pxrhofuru7rc/lzQpRKOVZN+dQb0cmM2VGB3/Nq+EA5JI6XTJWVWyv44m/RXfwrjXPWgRUkuQ9Raxsc+81sYkkkZp9ZLZYslS8PJq34Elvg7UUKVB6vUlNJCw5umB1n3e18JfMm1nI7va844N//tWU6s6fu19z2uoNU5fXppdms2FHVfRuBK9UBm2mtYNGptdkbl+1yuoUSNmSvp7M7ZvlqZKt9iBQ1QzHS7SUF5USa/ilt94mmFGSWmWGmPVf8sFvR1Ur23yw9hBbe7hVjpmddEqmrWaINZdkuUT5W7079SL8JnKq8BevjykHe3fiUZIksqjtmmO2WHKwTM/SCoDazsNcK03eRRoTJ6J3und8PODqD1YXzh90Ma/+8f9CzCWCVN3zXGOqadb6xc2xG63NtIKfRH1QMaRGiVMAWwNOgfSOq9KJgyFnYrXhG6lm0oarUn6rTRo1MbQNmfWmy2aJpDH8pPIhoCZIj3gmzOhq5D2+0gNPIW9K5a0SLN9ejtw4rLZvDR5NTGy8PmWCKYMXLxq/4ht542aLpXoe8SmjBH7uv/llzIX6Ehas3s+H3Tuw1leWFeY1d/SfzDs++N3fDlReveZ4wZKqValGXWmNcP6uN4ca6A0EhxJBsKQgolDKMJ+SJHxKl5AIltQQa+31rtVnYl1Xi8RBmV2hRipMjZCRAen/Vv1cyhLEpwz8yaCe2WJ5i2iRRKtFFqyrRegP7jyep1fvwWtfNXzqOrFj8vyKQg4LuqCspwDrw7sLoWwjKpviiDxlPNvhobxKlweaX8gc/tB2bFcVICXzPixKr5W9YOkTMAbdxiPKf1QyIboD/OxBrXfSa2TJibgm7DXpCZUyIEM6+B6OJ64f0GJJ51ox5EzEPqzOj4rOH2zvO5rXvHrt4wszb9x0VKvSl18vktVEVWhiQF+yiJGKddD8HQmkkmqg+lnU8gyiaNJH5j0dpVndN6CpD9hNJ3FTNDg9sp9oJqH+3bFCou3UK/AqF3QnIELC6i+rAAtcU3rEvBvfK+Nami6txVIJQDMQMN8zqYCVFAVrsMj8waGRK41Hf5zXGVx+ySluDZ5g8ahVxrPfOsqNuTkziosaWjabQaVKInbaLuTqsiI6Zp+xFG4VVGG9teN+0YmgwimQHk0oUaNUIoN92OKhrq7OjFZTj+Y1bFWMLnJbG5XDKeo61olcSpVEdLxdVKo8iN5pLZb3vriFVbNw2YqKioLlWESX8MSnTiv2FcwfbDreGjw4+M1Lw98952QVTl1eW4B+qwgVVax6KXYqVFK6w6V8q7FCs7yqWqF120u4jTrdHLGCAuJX6elAuUTzK5NgOjs74V/IZQvZuyH7oUt6Fi9eLH0rMoKpwhrDBrt7B9HM7rlUl1cVrZFGBQ44slSNhOJBVFBLZjScQhQZ4gLNkrBV9RYyowH3Fi+nvl5tlKsC9LxtkrLczRZL4mYkFq+mpkYyHGVA5kVZQ0+LUbBcbfhH5hbgGeAJBg8OPnjtq3//8FOXS01bvSHtMcHIRhbKKwqWmO7aAlIyIOjXyyBOgfR/meKCMipX8ztd1lAWL6wlh3Zx7wSOAoDboWZKuLkWBRZtak6Aox0QRpAIuLjEB2hGk/VB8N+enh6ZmiOtiFdWtKk5fm/ZHGjGN1br25tIXB+aZWa4UrSCvf1J0Wb31Pzog46P8k8kbH2hOu31ReEMLmtrDDCvZt64+Z+nfusyMgjDKtV4K9OWlj6FUCuoSLOm2mEZzPYran/s4Gtvl9FAs3NHNfLBuiNqJe2qqlS4mqkOapZcnDl9uFdfX1+wgRNz8rPLAn7eBHjn9JqZI/EHVmPTnIiu5hLi1eMU7/rU3mk9Ae8i7OTn4LcsM0/zru4vBU8NCEj7Z5Yol3dXpILVv31N2gtg1R38fvBcHNhWz36eZ7nkSdnS0pdfTy+WnRBNwryCxQwpCpdwybyZaasVPMFgtXrt49/lVauiGhAkhIyNYFWlvJdXy5nDjUffCFarus/yDA5k122+e0JsfkMIBcsXl7G/Bffek166Twyc3XRkdxy1urVczPM7piyqZiEgZMILVn4xWvHonPTUas0vvhdHraat3pDeRhKEBGNdxp6kKFi3xOi9sUlxe9/RhiM/9Nt2cOaNmzt6fx+gVpPnV9zzXCMNK0LuIMFa+cjcWdmS4PnPabiELWcOB3iCwREMsplgwdY4JoQUi2CB2soHWo6dCxSshGPcG4++ERAgCp2CWvlNFRSpog9IyB0qWFufWRQsWL2fDyelWb2XP4NhFTC3uaF/cHvv760/TV1em127eSJtK08IBSs0EKMtTz28+8gn/oJ1NRHBCu60evDaV699fMGc1Qxj6u6/2DD1yVpKFSEUrFtse+axjo8vnjhv7+HG9zHnEroYVt89N6C5gVCou3OLGtMBJGSCMSnm8OrQ8PU1P/rAqlnrKx9ofSHiYBzsqabjrQE9Vt+8NAwfUOtfh0hNe7KWI4CEULCCNKvuzf+1Ti3s+s6KJSHj3WFV7T71dsuZwwE+4Pbez7wTbkqqVk2tWlXyxCqaVIRQsJxoOXZu53sf9n5+29WgVgf+7knHGYXtfUehUwFzA2FV1f3+C4mxgjbBkqJOEULBiiFbvzqx84P/6v00m7lalrkxNZPrm9/7N1V+nVkd/SdPDJzFZ4BOzbxx89mBKw39g3AAIVIl+Fe1iisrEELBSgYI0P4zh9s+OjV0ZVLmyzmZG9MgWysfnSuDht0DZ+HuwfULXiNUdGrdfYvXTp8/ZX7F5AcrKFKEULBSnNMkG0OIDeVy/IIZ9y+Z89DKssoVs8rxB309QkjhBMsLZGswZ1j13W5Ylc+4f8GM+2ZPnQ6F4vsghBSFYBFCSEzuYhYQQihYhBBCwSKEULAIIYSCRQghFCxCCAWLEEIoWIQQQsEihFCwCCGEgkUIIRQsQggFixBCKFiEEELBIoRQsAghhIJFCCEULEIIBYsQQihYhBBCwSKEULAIIYSCRQghFCxCCAWLEEIoWIQQQsEihFCwCCGEgkUIoWARQggFixBCKFiEEAoWIYRQsAghhIJFCKFgEUIIBYsQQihYhBAKFiGEULAIIYSCRQihYBFCCAWLEEIoWIQQChYhhFCwCCGEgkUIoWARQggFixBCKFiEEAoWIYRQsAghhIJFCKFgEUIIBYsQQihYhJBxxv8LMABU9yEvyYVhtwAAAABJRU5ErkJggg==";
//            int contentStartIndex1 = pngImageURL1.indexOf(encodingPrefix) + encodingPrefix.length();
//            byte[] imageData1 = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL1.substring(contentStartIndex1));//workbook.addPicture can use this byte array
//
//            String pngImageURL2 = "data:image/png;base64,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";
//            int contentStartIndex2 = pngImageURL2.indexOf(encodingPrefix) + encodingPrefix.length();
//            byte[] imageData2 = org.apache.commons.codec.binary.Base64.decodeBase64(pngImageURL2.substring(contentStartIndex2));//workbook.addPicture can use this byte array


            final int pictureIndex = workbook.addPicture(imageData, Workbook.PICTURE_TYPE_PNG);
//            final int pictureIndex1 = workbook.addPicture(imageData1, Workbook.PICTURE_TYPE_PNG);
//            final int pictureIndex2 = workbook.addPicture(imageData2, Workbook.PICTURE_TYPE_PNG);


            final CreationHelper helper = workbook.getCreationHelper();
            final Drawing drawing = spreadsheet.createDrawingPatriarch();

            final ClientAnchor anchor = helper.createClientAnchor();
            anchor.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );
//            final ClientAnchor anchor1 = helper.createClientAnchor();
//            anchor1.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );
//            final ClientAnchor anchor2 = helper.createClientAnchor();
//            anchor2.setAnchorType( ClientAnchor.MOVE_AND_RESIZE );


            anchor.setCol1( 0 );
            anchor.setRow1( 0 );
            anchor.setRow2( 3 );
            anchor.setCol2( 12 );
            drawing.createPicture( anchor, pictureIndex );

//            anchor1.setCol1( 8 );
//            anchor1.setRow1( 0 );
//            anchor1.setRow2( 3 );
//            anchor1.setCol2( 10 );
//            anchor1.setDx1(5);
//            final Picture pict1 =drawing.createPicture( anchor1, pictureIndex1 );
//            pict1.resize(0.27);
//
//            anchor2.setCol1( 10 );
//            anchor2.setRow1( 0 );
//            anchor2.setRow2( 3 );
//            anchor2.setCol2( 11 );
//            final Picture pict2 = drawing.createPicture( anchor2, pictureIndex2 );
//            pict2.resize(0.24);

        XSSFCellStyle style1 = workbook.createCellStyle();//Create style
        style1.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //vertical align
        style1.setBorderBottom(CellStyle.BORDER_MEDIUM);

        spreadsheet.addMergedRegion(new CellRangeAddress(0,2,0,11));
//        CellRangeAddress range01 =new CellRangeAddress(0,2,8,9);
//            cleanBeforeMergeOnValidCells(spreadsheet,range01,style1 );
//            spreadsheet.addMergedRegion(range01);
//        CellRangeAddress range02 =new CellRangeAddress(0,2,10,10);
//            cleanBeforeMergeOnValidCells(spreadsheet,range02,style1 );
//            spreadsheet.addMergedRegion(range02);
        rowid = rowid+2;
        XSSFRow row=spreadsheet.createRow(rowid++);
        XSSFCellStyle style = workbook.createCellStyle();//Create style
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
        CellRangeAddress range1 =new CellRangeAddress(3,4,0,0);
            cleanBeforeMergeOnValidCells(spreadsheet,range1,style );
            spreadsheet.addMergedRegion(range1);
        CellRangeAddress range2 = new CellRangeAddress(3,4,1,6);
            cleanBeforeMergeOnValidCells(spreadsheet,range2,style );
            spreadsheet.addMergedRegion(range2);
        CellRangeAddress range3 = new CellRangeAddress(3,4,7,7);
            cleanBeforeMergeOnValidCells(spreadsheet,range3,style );
            spreadsheet.addMergedRegion(range3);
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
                CellRangeAddress range4 = new CellRangeAddress(3,4,8,11);
                    cleanBeforeMergeOnValidCells(spreadsheet,range4,style );
                    spreadsheet.addMergedRegion(range4);
            } else {
                cell3.setCellValue("Month:");
                cell4.setCellValue(getMonthYearName(reportRequest.getFromDate()));
                CellRangeAddress range4 = new CellRangeAddress(3,4,8,11);
                cleanBeforeMergeOnValidCells(spreadsheet,range4,style );
                spreadsheet.addMergedRegion(range4);
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

        CellRangeAddress range5 =new CellRangeAddress(5,5,1,3);
            cleanBeforeMergeOnValidCells(spreadsheet,range5,style );
            spreadsheet.addMergedRegion(range5);
        CellRangeAddress range6 =new CellRangeAddress(5,5,5,7);
            cleanBeforeMergeOnValidCells(spreadsheet,range6,style );
            spreadsheet.addMergedRegion(range6);
        CellRangeAddress range7 =new CellRangeAddress(5,5,9,11);
            cleanBeforeMergeOnValidCells(spreadsheet,range7,style );
            spreadsheet.addMergedRegion(range7);

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

    private void cleanBeforeMergeOnValidCells(XSSFSheet sheet,CellRangeAddress region, XSSFCellStyle cellStyle )
    {
        for(int rowNum =region.getFirstRow();rowNum<=region.getLastRow();rowNum++){
            XSSFRow row= sheet.getRow(rowNum);
            if(row==null){
                row = sheet.createRow(rowNum);
            }
            for(int colNum=region.getFirstColumn();colNum<=region.getLastColumn();colNum++){
                XSSFCell currentCell = row.getCell(colNum);
                if(currentCell==null){
                    currentCell = row.createCell(colNum);

                }

                currentCell.setCellStyle(cellStyle);

            }
        }


    }

}
